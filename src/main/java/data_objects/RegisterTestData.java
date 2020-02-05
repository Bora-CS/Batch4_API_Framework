package data_objects;

public class RegisterTestData {
	
	public String email;
	public String name;
	public String password;
	public int expectedStatusCode;
	
	public RegisterTestData (String email, String name, String password, int expectedStatusCode) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.expectedStatusCode = expectedStatusCode;
	}
	
	public RegisterTestData () {}
	
}
