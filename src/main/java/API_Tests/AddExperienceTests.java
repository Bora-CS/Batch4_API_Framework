package API_Tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data_objects.Experience;

public class AddExperienceTests {

	public static void main(String[] args) {

		String token = BoraServices.login("murad@test.com", "murad001");
		Map<Experience, Integer> testData = new HashMap<Experience, Integer>();
		testData.put(new Experience("Test Automation Engineer", "Geico", "Chevy Chase", "2014-07-01", "2016-07-01",
				false, "Means like the commute is kinda long"), 200);
		testData.put(new Experience("", "Geico", "Chevy Chase", "2014-07-01", "2016-07-01",
				false, "Means like the commute is kinda long"), 400);
		testData.put(new Experience("Test Automation Engineer", "", "Chevy Chase", "2014-07-01", "2016-07-01",
				false, "Means like the commute is kinda long"), 400);
		testData.put(new Experience("Test Automation Engineer", "Geico", "Chevy Chase", "", "2016-07-01",
				false, "Means like the commute is kinda long"), 400);
		testData.put(new Experience("", "", "Chevy Chase", "", "2016-07-01",
				false, "Means like the commute is kinda long"), 400);

		int testCaseId = 1;
		for (Experience exp : testData.keySet()) {
			System.out.println("Test Case - " + testCaseId++ + ":");
			int expectedStatusCode = testData.get(exp);
			BoraServices.addExperience_test(exp, token, expectedStatusCode);
			System.out.println();
		}

	}

}
