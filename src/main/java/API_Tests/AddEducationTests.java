package API_Tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data_objects.Education;
import data_objects.EducationTestData;

public class AddEducationTests {

	public static void main(String[] args) {

		String token = BoraServices.login("murad@test.com", "murad001");
		ArrayList<EducationTestData> testData = new ArrayList<EducationTestData>();
		testData.add(
				new EducationTestData(
						new Education("Nova", "Associate of Science", "Art and Science", "09/01/1998", "09/01/2000",
								false, "Such a waste of time & money, could have just went to BoraTech"),
						200, new String[] {}));
		testData.add(
				new EducationTestData(
						new Education("", "Associate of Science", "Art and Science", "09/01/1998", "09/01/2000", false,
								"Such a waste of time & money, could have just went to BoraTech"),
						400,	 new String[] {"School is required"}));
		testData.add(
				new EducationTestData(
						new Education("Nova", "", "Art and Science", "09/01/1998", "09/01/2000",
				false, "Such a waste of time & money, could have just went to BoraTech"), 400, new String[] {"Degree is required"}));
		testData.add(
				new EducationTestData(
						new Education("Nova", "Associate of Science", "", "09/01/1998", "09/01/2000",
				false, "Such a waste of time & money, could have just went to BoraTech"), 400, new String[] {"Field of study is required"}));
		testData.add(
				new EducationTestData(
						new Education("", "", "", "09/01/1998", "09/01/2000", false,
				"Such a waste of time & money, could have just went to BoraTech"), 400, new String[] {"School is required", "Degree is required", "Field of study is required"}));

		int testCaseID = 1;
		for (EducationTestData td : testData) {
			System.out.println("Test Case ID: " + testCaseID++);
			BoraServices.addEducation_test(td.education, token, td.expectedStatusCode, td.errors);
			System.out.println();
		}

	}

}
