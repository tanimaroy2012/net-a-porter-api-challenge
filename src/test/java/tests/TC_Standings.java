package tests;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_Standings {
    @DataProvider(name="yearandrank")
    public Object[][] testDatauri() {
        return new Object[][] {
                {"current","1","hamilton"},
                {"2008","5","alonso"}
        };
    }
    @Test(description = " verify various ranks and driverid against year", dataProvider = "yearandrank")
    public void validatingStandingsInfo(String seasonYear,String position,String name) {
        //Convert ResponseBody to String
        String dataPath = "MRData.StandingsTable.StandingsLists.DriverStandings";
        String responseBody=given().pathParam("raceSeason",seasonYear).when().get("http://ergast.com/api/f1/{raceSeason}/driverStandings.json").getBody().asString();
        //Create JsonPath Object by Passing the Response Body as a string
        JsonPath resJson=new JsonPath(responseBody);
        //Fetch the attribute value series under MRData
        List<String> seriesName=resJson.getList(dataPath+".position[0]");
        //System.out.println(seriesName);
        // User TestNg Assertion
        List<Map<String, String>> drivers = resJson.getList(dataPath+".Driver[0]");
            for (int i =0;i<drivers.size();i++){
                if (seriesName.get(i).equals(position)){
                    System.out.println(seriesName.get(i)+"_"+drivers.get(i).get("driverId"));
                    assertEquals(name, drivers.get(i).get("driverId"));
                    
                }
            }
    }

    @DataProvider(name="yearwisecount")
    public Object[][] testDataconstructor() {
        return new Object[][] {
                {"2010",1,"German"},
                {"1990",8,"British"}
        };
    }
    @Test(description = " verify various ranks and driverid against year", dataProvider = "yearwisecount")
    public void validatingConstructorInfo(String seasonYear,int count,String nationality) {
        
        String dataPath = "MRData.ConstructorTable.Constructors";
        String responseBody=given().pathParam("raceSeason",seasonYear).when().get("http://ergast.com/api/f1/{raceSeason}/constructors.json").getBody().asString();
        
        JsonPath resJson=new JsonPath(responseBody);
        List<String> seriesName=resJson.getList(dataPath);

        int noOfConstructors=0;
        List<Map<String, String>> drivers = resJson.getList(dataPath);
            for (int i =0;i<seriesName.size();i++){
                if (drivers.get(i).get("nationality").equals(nationality)){
                    noOfConstructors+=1;
                }
            }
            assertEquals(count,noOfConstructors);
    }
}
