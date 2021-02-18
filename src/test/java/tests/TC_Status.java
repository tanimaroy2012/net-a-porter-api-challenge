package tests;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_Status {
    @DataProvider(name="yearandrank")
    public Object[][] testDatauri() {
        return new Object[][] {
                {"current","Finished","hamilton","16"},
                {"2008","Engine","alonso","1"}
        };
    }
    @Test(description = " verify the number of finished races along with driver name and year", dataProvider = "yearandrank")
    public void validatingStandingsInfo(String seasonYear,String position,String name,String count) {
     
        String dataPath = "MRData.StatusTable";
        String responseBody=given().pathParam("year",seasonYear)
        .pathParam("driver",name).when().get("http://ergast.com/api/f1/{year}/drivers/{driver}/status.json").getBody()
        .asString();
       
        JsonPath resJson=new JsonPath(responseBody);
       
        List<Map<String, String>> status = resJson.getList(dataPath+".Status");
        System.out.println(status);
            for (int i =0;i<status.size();i++){
                if (status.get(i).get("status").contains(position)){
                    //System.out.println(seriesName.get(i)+"_"+status.get(i).get("status"));
                    assertEquals(count, status.get(i).get("count"));
                    
                }
            }
    }
}
