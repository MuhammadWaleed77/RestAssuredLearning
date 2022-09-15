import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.CoursePrice()); // get responce from a dummy API mocked (this is done when
															// real api is not present and tc needs to be written)
		
//Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("Size is :"+count);
		
//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchse Amount  is :"+totalAmount);
		
//Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println("Title for first course is :"+titleFirstCourse);

// Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			System.out.println(js.get("courses[" + i + "].price").toString());

			System.out.println(courseTitles);

		}
// Print no of copies sold by RPA Course
		System.out.println("Print no of copies sold by RPA Course");

		for (int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			if (courseTitles.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[" + i + "].copies");
				System.out.println(copies);
				break;
			}

		}

	}

}
