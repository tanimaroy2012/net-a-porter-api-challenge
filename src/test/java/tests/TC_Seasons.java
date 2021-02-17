package tests;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

public class TC_Seasons {
    
    @Test(description = " to list number of seasons where a specific driver has driven for a particular constructor")
    public void validatingSeriesName() {
        //Convert ResponseBody to String
        String responseBody=given().when().get("http://ergast.com/api/f1/drivers/alonso/constructors/renault/seasons.json").getBody().asString();
        //Create JsonPath Object by Passing the Response Body as a string
        JsonPath resJson=new JsonPath(responseBody);
        //Fetch the attribute value series under MRData
        String seriesName=resJson.getString("MRData.total");
        // User TestNg Assertion
        assertEquals("6",seriesName);
    }

    @Test(description = " to list number of seasons where a specific driver has driven for a particular constructor")
    public void validatingDriverVictory() {
        //Convert ResponseBody to String
        String responseBody=given().when().get("http://ergast.com/api/f1/drivers/alonso/driverStandings/1/seasons.json").getBody().asString();
        //Create JsonPath Object by Passing the Response Body as a string
        JsonPath resJson=new JsonPath(responseBody);
        //Fetch the attribute value series under MRData
        String seriesName=resJson.getString("MRData.total");
        // User TestNg Assertion
        assertEquals("2",seriesName);
    }

    @Test(description = " to list number of seasons where a specific driver has driven for a particular constructor")
    public void validatingrenaultVictory() {
        //Convert ResponseBody to String
        String responseBody=given().when().get("http://ergast.com/api/f1/constructors/renault/constructorStandings/1/seasons.json").getBody().asString();
        //Create JsonPath Object by Passing the Response Body as a string
        JsonPath resJson=new JsonPath(responseBody);
        //Fetch the attribute value series under MRData
        String seriesName=resJson.getString("MRData.total");
        // User TestNg Assertion
        assertEquals("2",seriesName);
    }

    @Test(description = " to list number of seasons where a specific driver has driven for a particular constructor")
    public void validatingCurrent() {
        //Convert ResponseBody to String
        String responseBody=given().when().get("http://ergast.com/api/f1/2010/1/fastest/1/results.json").getBody().asString();
        //Create JsonPath Object by Passing the Response Body as a string
        JsonPath resJson=new JsonPath(responseBody);
        //Fetch the attribute value series under MRData
        String seriesName=resJson.getString("MRData.RaceTable.Races.raceName");
        // User TestNg Assertion
        assertEquals("[Bahrain Grand Prix]",seriesName);
    }
}
