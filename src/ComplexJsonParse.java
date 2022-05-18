import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	
	public static void main(String[] args) {
		
		JsonPath js= new JsonPath(payload.CoursePrice());
		//Print no. of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//print title of first course
		
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//print title of second course
		
		String titleSecondCourse = js.getString("courses[2].title");
		System.out.println(titleSecondCourse);
		
		//Print All course titles and their respective Prices
		
		for(int i=0 ; i<count ; i++) {
			String CourseTitle = js.getString("courses["+i+"].title");
			String CoursePrice = js.getString("courses["+i+"].price");
			System.out.println("Course Tile: "+CourseTitle+" || Course Price: "+CoursePrice);
			
		}
		
		//Print no of copies sold by RPA Course
		
		for(int i=0 ; i<count ; i++) 
		{
			String CourseTitle = js.getString("courses["+i+"].title");
			if (CourseTitle.equalsIgnoreCase("RPA")) 
			{
				String CourseCopies = js.getString("courses["+i+"].copies"); 
				System.out.println("no of copies sold by RPA Course: "+CourseCopies);
				break;
			}			
		}
		
		// Verify if Sum of all Course prices matches with Purchase Amount
		int Price = 0;
		for(int i=0 ; i<count ; i++) 
		{
			int CoursePrice = js.getInt("courses["+i+"].price");
			int Copies = js.getInt("courses["+i+"].copies"); 
			Price = Price + (CoursePrice * Copies);
		}
		System.out.println("Sum of all Course prices"+Price);
		Assert.assertEquals(totalAmount, Price);
		
	}
	
}
