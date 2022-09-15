import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Web {

	public static void main(String[] args) {
	
		
		System.setProperty("webdriver.chrome.driver", "/Users/numuqa/Downloads/ch3");
		WebDriver driver = new ChromeDriver();
    
		

	}

}
