package API_Tests;

import java.util.ArrayList;

import data_objects.RegisterTestData;

public class Tests {

	public static void main(String[] args) {

		ArrayList<RegisterTestData> testData = new ArrayList<RegisterTestData>();
		testData.add(new RegisterTestData("murad@test.com", "Murad Test", "murad001", 400));
		testData.add(new RegisterTestData("", "Murad Test", "murad001", 400));
		testData.add(new RegisterTestData("", "", "murad001", 400));
		testData.add(new RegisterTestData("", "", "", 400));
		testData.add(new RegisterTestData("murad3@test.com", "Murad3 Test", "murad001", 200));

		int testCaseId = 1;
		for (RegisterTestData td : testData) {
			System.out.println("Test Case - " + testCaseId++ + ":");
			BoraServices.register_test(td.email, td.name, td.password, td.expectedStatusCode);
			System.out.println();
		}

	}

}
