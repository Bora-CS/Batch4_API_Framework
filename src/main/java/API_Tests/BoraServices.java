package API_Tests;

import java.util.List;

import org.json.simple.JSONObject;

import data_objects.Education;
import data_objects.Error;
import data_objects.Experience;
import API_Resource.Constants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BoraServices {

	public static Response addEducation(Education education, String token) {
		String endPoint = "/api/profile/education";
		RestAssured.baseURI = Constants.APPLICATION_URI;
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "Application/json");
		request.header("x-auth-token", token);

		JSONObject body = new JSONObject();
		body.put("school", education.school);
		body.put("degree", education.degree);
		body.put("fieldofstudy", education.fieldofstudy);
		body.put("from", education.from);
		body.put("to", education.to);
		body.put("current", education.current);
		body.put("description", education.description);

		request.body(body);

		Response response = request.put(endPoint);
		return response;
	}

	public static void addEducation_test(Education education, String token, int expectedStatusCode) {
		Response response = addEducation(education, token);
		int actualStatusCode = response.getStatusCode();
		if (actualStatusCode == expectedStatusCode) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed:");
			System.out.println("Expected Status Code:\t" + expectedStatusCode);
			System.out.println("Actual Status Code:\t" + actualStatusCode);
		}
	}

	public static Response addExperience(Experience experience, String token) {
		String endpoint = "/api/profile/experience";
		RestAssured.baseURI = Constants.APPLICATION_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "Application/json");
		request.header("x-auth-token", token);

		JSONObject body = new JSONObject();

		body.put("title", experience.title);
		body.put("company", experience.company);
		body.put("location", experience.location);
		body.put("from", experience.from);
		body.put("to", experience.to);
		body.put("current", experience.current);
		body.put("description", experience.description);

		request.body(body);
		Response response = request.put(endpoint);
		return response;
	}

	public static void addExperience_test(Experience experience, String token, int expectedStatusCode) {
		Response response = addExperience(experience, token);
		int actualStatusCode = response.getStatusCode();
		if (actualStatusCode == expectedStatusCode) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed:");
			System.out.println("Expected Status Code:\t" + expectedStatusCode);
			System.out.println("Actual Status Code:\t" + actualStatusCode);
		}
	}

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
