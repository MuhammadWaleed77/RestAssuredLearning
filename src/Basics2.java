import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// validate if Add Place API is working as expected
		// given all input details
		// when Submit the API (resource,http method)
		// Then - validate the response

		// 1. Add place-> 2. Update Place with New Address -> 3. Get Place to validate if New  address is present in response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String responce = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")

				.body(payload.AddPlace())
				.when().post("maps/api/place/add/json")
				.then().log().all().statusCode(200).body("scope", equalTo("APP")) // Verify if responce body has scope
																					// as APP or not
				.header("server", "Apache/2.4.41 (Ubuntu)")
				
				.extract().response().asString(); /// store responce in stirng form to make it resulable in other
													/// methods del,put
		System.out.println(responce);

		
		
		
		// Get place if from responce, which is json so use json method to extract it
		
		JsonPath js= new JsonPath(responce); // for parcing JSON
		String placeId=js.getString("place_id");  //place_id enclosed in "" so it is string. make path from parent to child

		System.out.println("PLACE ID is :" + placeId);        
		
	//********************************2. Update Place********************************
		
				String newAddress = "Summer Walk, Africa";
				
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body("{\r\n" + 
						"\"place_id\":\""+placeId+"\",\r\n" + 
						"\"address\":\""+newAddress+"\",\r\n" + 
						"\"key\":\"qaclick123\"\r\n" + 
						"}").
				when().put("maps/api/place/update/json")
				.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));  
				//Assertion can be done in both ways string or JSon Path
				
		//********************************3. Get Place********************************
				
			String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id",placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
			
			JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse); //resuable method
			String actualAddress =js1.getString("address");
			System.out.println("ADDRESS IS : "+actualAddress);
			Assert.assertEquals(actualAddress, "Summer Walk, Africa"); //use testing framwork assertion as now we are out of rest assured given block
			  //download jar file and add manually
			//Cucumber Junit, Testng
			
	
				
				
			}

		}
