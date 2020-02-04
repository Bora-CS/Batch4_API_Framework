package API_Tests;

import java.util.ArrayList;

import io.restassured.response.Response;

public class Tests {

	public static void main(String[] args) {
		
		String token = BoraServices.login("murad@test.com", "murad001");
		
		if (token.isEmpty()) {
			System.out.println("Token not available");
		} else {
			Response userProfile = BoraServices.getCurrentUserProfile(token);
			ArrayList<String> skills = userProfile.jsonPath().get("skills");
			for (String skill : skills) {
				System.out.println(skill);
			}
			
		}
		
	}

}
