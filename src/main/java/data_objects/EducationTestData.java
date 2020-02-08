package data_objects;

public class EducationTestData {

	public Education education;
	public int expectedStatusCode;
	public String[] errors;

	public EducationTestData(Education education, int expectedStatusCode, String[] errors) {
		this.education = education;
		this.expectedStatusCode = expectedStatusCode;
		this.errors = errors;
	}
	
	public EducationTestData() {}
}
