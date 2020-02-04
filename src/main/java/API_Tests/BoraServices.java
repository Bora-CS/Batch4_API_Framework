package API_Tests;

import org.json.simple.JSONObject;

import API_Resource.Constants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BoraServices {

	public static String login (String email, String password) {
		String endpoint = "/api/auth";
		RestAssured.baseURI = Constants.APPLICATION_URL;
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-type", "application/json");
		
		JSONObject body = new JSONObject();
		body.put("email", email);
		body.put("password", password);	
		request.body(body);
		
		Response response = request.post(endpoint);
		
		try {
			checkStatus(response);
			JsonPath jp = response.jsonPath();
			String token = jp.get("token");
			return token;	
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Response getCurrentUserProfile(String token) {
		String endpoint = "/api/profile/me";
		RestAssured.baseURI = Constants.APPLICATION_URL;
		RequestSpecification request = RestAssured.given();
		request.header("x-auth-token", token);
		Response response = request.get(endpoint);
		try {
			checkStatus(response);
			JsonPath jp = response.jsonPath();
			String userName = jp.get("user.name");
			System.out.println("User profile for <" + userName + "> is retrieved successfully!");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void checkStatus (Response response) throws Exception {
		int statusCode = response.getStatusCode();
		if (statusCode != 200) {
			String errorMessage = "Test Failed:\n" + "Expected Status Code:\t" + 200 + "\n" + "Actual Status Code:\t" + statusCode;
			throw new Exception(errorMessage);
		}
	}

}
