package tests;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_Circuits {

    String season = "2017";
    int numberOfRaces = 20;

    @Test(description = "Number Of Circuits In 2017 Season Should Be 20")
    public void validatingContentLengthOfCircuits() {
        given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().assertThat()
        .header("Content-Length",equalTo("4551")).and().statusCode(200);
    }
    
    @Test(description = "Validation of series Name which is f1")
    public void validatingSeriesName() {
        String responseBody=given().when().get("http://ergast.com/api/f1/2017/circuits.json").getBody().asString();
        
        JsonPath resJson=new JsonPath(responseBody);
        
        String seriesName=resJson.getString("MRData.series");
        
        assertEquals("f1",seriesName);
    }
    @DataProvider(name="seasonsAndRaceNumbers")
    public Object[][] testDataFeed() {
        return new Object[][] {
                {"2017",20},
                {"2016",21}
        };
    }
    @Test(description = "Number Of Circuits validation in different Seasons",dataProvider = "seasonsAndRaceNumbers")
    public void circuitNumberValidation(String seasonYear,int raceNumbers) {
    given().pathParam("season",seasonYear).when().get("http://ergast.com/api/f1/{season}/circuits.json")
    .then().assertThat().body("MRData.CircuitTable.Circuits.circuitId",hasSize(raceNumbers));
    }
    
}
