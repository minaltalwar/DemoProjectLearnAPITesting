package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class JiraTest {

	public static void main(String[] args) {
		
		
		RestAssured.baseURI = "https://jira.platform.dev.sdt.ericsson.se";
		SessionFilter session = new SessionFilter();
		String sessionRes = given().relaxedHTTPSValidation().header("Content-Type","application/json")
		.body("{ \"username\": \"etlwmnl\", \"password\": \"Ericsson@123\" }").log().all().filter(session)
		.when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		
		JsonPath js = reUsableMethods.rawToJson(sessionRes);
		String name = js.getString("session.name");
		String value = js.getString("session.value");
		System.out.println("Session Name: "+name);
		System.out.println("Session Value: "+value);
		
		//Get Worklog(return specific worklog)
		// "GET /rest/api/2/issue/{issueIdOrKey}/worklog/{id}"
		
		given().pathParam("issueIdOrKey","771661").pathParam("id", "1632226").filter(session)
		.when().get("/rest/api/2/issue/{issueIdOrKey}/worklog/{id}")
		.then().assertThat().log().all().statusCode(200);
		
		System.out.println("Get Worklog(return specific worklog) completed");
		
		//Get issue
		// "GET /rest/api/2/issue/{issueIdOrKey}"
	
		String issueDetails = given().filter(session).pathParam("issueIdOrKey", "789209")
		.queryParam("fields", "summary, comment")
		.when().get("/rest/api/2/issue/{issueIdOrKey}")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.getInt("fields.comment.comments.size()");
		for(int i=0 ; i<commentCount ; i++)
		{
			String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString() ;
			System.out.println("Comment Id: "+commentIdIssue);
			String commentBody = js1.get("fields.comment.comments["+i+"].body").toString() ;
			System.out.println("Comment Body: "+commentBody);
		}
		
		
		
		
	}

}

