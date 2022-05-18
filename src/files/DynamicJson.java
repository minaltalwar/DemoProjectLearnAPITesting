package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider= "BooksData")
	public void addBook (String isbn ,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().header("Content-Type", "application/json").
		body(payload.Addbook(isbn, aisle)).
		when().
		post("/Library/Addbook.php").
		then().assertThat().statusCode(200).
		extract().response().asString();
		
		JsonPath js = reUsableMethods.rawToJson(resp);
		String ID = js.get("ID");
		System.out.println("ID: "+ID+" Added");
		
		given().header("Content-Type", "application/json").
		body(ID).
		when().
		post("/Library/DeleteBook.php").
		then().assertThat().statusCode(200);
		System.out.println("ID: "+ID+" Deleted");
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"aaaa","1111"} , {"bbbb","2222"} , {"cccc","3333"}, {"dddd","4444"}};
	}
	
	
	
}
