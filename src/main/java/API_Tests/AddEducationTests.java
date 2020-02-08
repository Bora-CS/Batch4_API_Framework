package API_Tests;

import java.util.HashMap;
import java.util.Map;

import data_objects.Education;

public class AddEducationTests {

	public static void main(String[] args) {

		String token = BoraServices.login("murad@test.com", "murad001");
		Map<Education, Integer> testData = new HashMap<Education, Integer>();
		testData.put(new Education("Nova", "Associate of Science", "Art and Science", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 200);
		testData.put(new Education("", "Associate of Science", "Art and Science", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 400);
		testData.put(new Education("Nova", "", "Art and Science", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 400);
		testData.put(new Education("Nova", "Associate of Science", "", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 400);
		testData.put(new Education("", "", "", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 400);
		
		int testCaseID = 1;
		for (Education edu : testData.keySet()) {
			System.out.println("Test Case ID: " + testCaseID++);
			BoraServices.addEducation_test(edu, token, testData.get(edu));
			System.out.println();
		}
		
	}

}
