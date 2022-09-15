import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import files.ReUsableMethods;
import files.payload;


public class DynamicJson {
   //Send data from test to JSON  TEST PARAmeterization
	@Test(dataProvider = "BooksData")

	public void addBook(String isbn, String aisle) //dataProvider
	{

		RestAssured.baseURI = "http://216.10.245.166";
		Response resp = given().
				header("Content-Type", "application/json").
				body(payload.Addbook(isbn, aisle)).   //send values on runtime parameterization
				when().
				post("/Library/Addbook.php").
				then().assertThat().statusCode(404).
				extract().response();
		
			
		JsonPath js = ReUsableMethods.rawToJson(resp.asString());  //string to json reusable function
		 
		String placeId=js.getString("place_id"); 
		
		String id = js.get("ID");
		System.out.println(id);
		
		
		// deleteBOok when add test is writen so db doesn't get filled 
	}

	@DataProvider(name="BooksData")
	public Object[][]  getData()
	{
		return new Object[][] {{"ojfwty","9363"},{"okmfet","533"},{"cwetee","4253"}};
	}

}