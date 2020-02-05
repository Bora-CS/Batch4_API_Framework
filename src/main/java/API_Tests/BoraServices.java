package API_Tests;

import java.util.List;

import org.json.simple.JSONObject;

import data_objects.Error;

import API_Resource.Constants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BoraServices {

	public static String register(String email, String name, String password) {
		String endpoint = "/api/users";
		RestAssured.baseURI = Constants.APPLICATION_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "Application/json");

		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("email", email);
		body.put("password", password);

		request.body(body);

		Response response = request.post(endpoint);
		int statusCode = response.getStatusCode();
		switch (statusCode) {
		case 200:
			System.out.println("Registration Successful!");
			JsonPath jp = response.jsonPath();
			return jp.get("token");
		case 400:
			System.out.println("Registration Failed!");
			JsonPath jp1 = response.jsonPath();
//			// First way
//			ArrayList<Object> errors = jp1.get("errors");
//			int numberOfErrors = errors.size();
//			String finalMessage = "";
//			for (int i = 0; i < numberOfErrors; i++) {
//				String errorMessage = jp1.get("errors[" + i + "].msg");
//				finalMessage = finalMessage + errorMessage + "\n";
//			}
//			return finalMessage;
			// Second way
			List<Error> errors = jp1.getList("errors", Error.class);
			String finalMessage = "";
			for (Error error : errors) {
				finalMessage += (error.msg + "\n");
			}
			return finalMessage;
		default:
			return null;
		}
	}
	
	public static void register_test(String email, String name, String password, int expectedStatusCode) {
		String endpoint = "/api/users";
		RestAssured.baseURI = Constants.APPLICATION_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "Application/json");

		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("email", email);
		body.put("password", password);

		request.body(body);

		Response response = request.post(endpoint);
		int statusCode = response.getStatusCode();
		if (statusCode == expectedStatusCode) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed:");
			System.out.println("Expected Status Code:\t" + expectedStatusCode);
			System.out.println("Actual Status Code:\t" + statusCode);
		}
	}

	public static String login(String email, String password) {
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

	public static void checkStatus(Response response) throws Exception {
		int statusCode = response.getStatusCode();
		if (statusCode != 200) {
			String errorMessage = "Test Failed:\n" + "Expected Status Code:\t" + 200 + "\n" + "Actual Status Code:\t"
					+ statusCode;
			throw new Exception(errorMessage);
		}
	}

}
