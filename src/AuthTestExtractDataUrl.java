import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.webAutomation;

public class AuthTestExtractDataUrl {
	
	public static void main(String[] args ) throws InterruptedException {
	
//GET AUTHORIZATION CODE (hit url in browser login and new get url, extract code from it ) use selenium to hit url
		
		//1. Hit Authorization Code Url and get code
		//2. Use Code , Hit AccessToekn Url get Access Token
		//3. Hit actual api with access token to get list of cources
		
		//STEP 1
		String [] courseTitle = {"Selenium","Java"};  //List of courses in json
		
	System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
	
	//NOTE : THIS WILL NOT WORK NOW AS GOOGLE HAS BLOCKED AUTOMATION TESTING ON GMAIL so perform this step manuallu and place the 
	     //resultent url. ALSO ASK DEV to uncreasse lifetime of this url to 4-5 daya so it doesn't expire as we r in test env only
	
	WebDriver driver= new ChromeDriver();
	driver.get("https://accounts.google.com/signin/oauth/identifier?client_id=692183103107-p0m7ent........");
	driver.findElement(By.cssSelector("input[type='email']")).sendKeys("srinath19830");
	driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
	Thread.sleep(3000);
	driver.findElement(By.cssSelector("input[type='password']")).sendKeys("12233");
	driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
	Thread.sleep(4000);
	String url=driver.getCurrentUrl(); //get the new url
	   //Get code 
	String partialcode=url.split("code=")[1];
	String code=partialcode.split("&scope")[0];
	System.out.println(code);
	
	
	String accessTokenResponse= given().
			queryParams("code",code)
			.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type", "authorization_code")
			.when().log().all()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();
	
			JsonPath js=new JsonPath(accessTokenResponse);
			String accessToken=js.getString("access_token");
			
			
			// 2nd METHOD to extract JSon better way use POJO CLASSES (DeSerelization) instead of (JsonPath)
			
			//1. Instread of converting into String use pojo classes and change return type to class
			//2. Tell rest assured in which format should it scan the responce (GetCourse class objects), 
			  // tell it which format is being given to pojo class (json,xml) which it has to serelize and convert to java obj
			
			
			GetCourse gc = given().
					queryParams("code",code).expect().defaultParser(Parser.JSON)  //telling format passed
					.when()
					//.log().all() //remove log as it will not supporrt
			        .post("https://www.googleapis.com/oauth2/v4/token").as(GetCourse.class);
			
			System.out.println(gc.getLinkedIn()); //get linkedin url from json
			gc.getInstructor();
			
			//1. Get cource title of 1st cource from api section in json 
			   gc.getCourses().getApi().get(1).getCouseTitle();
			
		   //2. Get cource xyz title of from list of cource from api section in json and its price from all the cources
			
			   List<Api> apiCourses=gc.getCourses().getApi();
			   
			   for(int i=0;i<apiCourses.size();i ++)
			   {
			      if(apiCourses.get(i).getCouseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			         {
			             System.out.println(apiCourses.get(i).getPrice()); 
			         }
			   }
			   
			//3. Get the course names of WebAutomation and apply assertion
			   
			   //tomorrow array size can be increased so use ArrayList so data can be added in it for assertion
			   ArrayList<String> a = new ArrayList<String>();
			   
			   List<webAutomation> w=gc.getCourses().getWebAutomation();
			   
			   for(int j =0;j<w.size();j ++)
			   { 
			     // System.out.println(w.get(j).getCourseTitle());
				   a.add(w.get(j).getCourseTitle()); //add titles in ArrayList
			   }
			   
			   // For comparion both should be arrayList so convert getting array into arraylist   
			       List<String> expectedList= Arrays.asList(courseTitle);
			       
			       Assert.assertTrue(a.equals(expectedList));

}
}
