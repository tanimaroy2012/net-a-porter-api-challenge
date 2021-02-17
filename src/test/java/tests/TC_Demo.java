package tests;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class TC_Demo {

    public String uri = "http://ergast.com/api/f1/drivers.json";

    @Test(description = "Total Number of drivers Should Be 20")
    public void validatingNumberOfDrivers() {
        given().when().get(uri).
           then().assertThat().body("MRData.DriverTable.Drivers.driverId", hasSize(20));
    }
    
    @Test(description = "Verify adams nationality is Belgian")
    public void testWithPathParams() {
    
       String responseBody = given().when().get(uri).getBody().asString();
        
        JsonPath resJson = new JsonPath(responseBody);
    
        List<Map<String, String>> drivers = resJson.getList("MRData.DriverTable.Drivers");
        
        for (int i =0;i<drivers.size();i++){
            if (drivers.get(i).get("driverId").contains("adams")){
                assertEquals("Belgian", drivers.get(i).get("nationality"));
            }
        }
    }

    @Test(description = "Store all the drivers with british nationality")
        public void testCaptureAllBritishDrivers() {
            
            String responseBody = given().when().get(uri).getBody().asString();
            JsonPath resJson = new JsonPath(responseBody);
            List<Map<String, String>> drivers = resJson.getList("MRData.DriverTable.Drivers");
            List<String> britishDriverInfo = new ArrayList<String>();
            for (int i =0;i<drivers.size();i++){
                if (drivers.get(i).get("nationality").contains("British")){
                    britishDriverInfo.add(drivers.get(i).get("driverId"));
                        
                }
            }
            
        }
    @Test(description = "Store all the drivers with birth year between 1920 and 1925.")
        public void testCaptureAllBirthDatesDrivers() {
            String s = "1920-01-01";
            String e = "1925-12-31";
            LocalDate start = LocalDate.parse(s);
            LocalDate end = LocalDate.parse(e);
            List<String> totalDates = new ArrayList<>();
            String responseBody = given().when().get(uri).getBody().asString();
            JsonPath resJson = new JsonPath(responseBody);
            List<Map<String, String>> drivers = resJson.getList("MRData.DriverTable.Drivers");
            List<String> birthdates= new ArrayList<String>();
            while (!start.isAfter(end)) {
                totalDates.add(start.toString());
                start = start.plusDays(1);
            }
            for (int i =0;i<drivers.size();i++){
                if (drivers.get(i).get("dateOfBirth").startsWith("192")){
                    if(totalDates.contains(drivers.get(i).get("dateOfBirth"))){
                        birthdates.add(drivers.get(i).get("dateOfBirth"));
                    }
                        
                }
            }
        }
}