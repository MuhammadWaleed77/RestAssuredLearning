import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddJsonfromFile {
  //convert content of file to String -> for this method (converts into Byte) -> Byte to String
	@Test
	public void addBook() throws IOException
	{

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resp = given().
				queryParam("key", "qaclick123").
				header("Content-Type", "application/json").
				body(GenerateStringFromResource("/Users/numuqa/eclipse-workspace/RestAssuredLearn/src/addPlace.json")).
				when().
				post("maps/api/place/add/json").
				then().assertThat().statusCode(200).
				extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(resp);
		String id = js.get("id");
		System.out.println(id);
		// deleteBOok

	}
	//convert content of file to String -> for this method (converts into Byte) -> Byte to String
	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}

}
