import static io.restassured.RestAssured.*;
import java.io.File;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {

// JIRA COOKIE BASED AUTHENTICATION

		RestAssured.baseURI = "http://localhost:8080";

//**************************Login Scenario******************************************

		SessionFilter session = new SessionFilter(); // class to extract value from result json and pass in other test(Other way is by JsonPath)
           //relaxedHTTPSValidation bypasses https ssl certificate on website
		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{\r\n" +
				"    \"username\": \"RahulShetty\",\r\n" +
				"    \"password\": \"XXXX11\"\r\n" +
				"}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract() //filter will store res 
				.response().asString();

		

//**************************Add comment******************************************
		String expectedMessage = "Hi How are you?";
		
		String addCommentResponse = given().pathParam("key", "10101").log().all()    // {} means queryParameter
				.header("Content-Type", "application/json").body("{\r\n" +
						"    \"body\": \"" + expectedMessage + "\",\r\n" +
						"    \"visibility\": {\r\n" +
						"        \"type\": \"role\",\r\n" +
						"        \"value\": \"Administrators\"\r\n" +
						"    }\r\n" +
						"}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all() // {} means queryParameter, treed as var
				.assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");  //get if of added comment to verify i get method

//**************************Add Attachment ******************************************

		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10101")
				.header("Content-Type", "multipart/form-data")   // when multipart is used header will always be as we are sending multipart no json
				.multiPart("file", new File("jira.txt")).when(). //Adding Attachment use multipart
				post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

//**************************Get Issue******************************************

		String issueDetails = given().filter(session).pathParam("key", "10101")
				.queryParam("fields", "comment") //get only comments
				.log().all().when().get("/rest/api/2/issue/{key}").then()
				.log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		JsonPath js1 = new JsonPath(issueDetails); 
		   //verify the comment which we added for that get the id of comment and then verify its body
		int commentsCount = js1.getInt("fields.comment.comments.size()");  //get size of comments array

		for (int i = 0; i < commentsCount; i++)
		{
			String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();

			if (commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
	}
}