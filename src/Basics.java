import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	// validate if Add Place API is working as expected
		// given all input details
		// when Submit the API (resource,http method)
		// Then - validate the response 
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\n"
				+ "  \"location\": {\n"
				+ "    \"lat\": -38.383494,\n"
				+ "    \"lng\": 33.427362\n"
				+ "  },\n"
				+ "  \"accuracy\": 50,\n"
				+ "  \"name\": \"MY house\",\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\n"
				+ "  \"types\": [\n"
				+ "    \"shoe park\",\n"
				+ "    \"shop\"\n"
				+ "  ],\n"
				+ "  \"website\": \"http://google.com\",\n"
				+ "  \"language\": \"French-IN\"\n"
				+ "}\n"
				+ "").when().post("maps/api/place/add/json")
		.then().log().all().statusCode(200).body("scope", equalTo("APP"))   //Verify if responce body has scope as APP or not 
		.header("server", "Apache/2.4.41 (Ubuntu)");//Verify responces in header (imp verify if req is from valid server)
		
		 //import manually static package for equal
    
		

	}

}
