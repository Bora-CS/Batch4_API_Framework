package data_objects;

public class Education {
	public String school;
	public String degree;
	public String fieldofstudy;
	public String from;
	public String to;
	public boolean current;
	public String description;

	public Education(String school, String degree, String fieldofstudy, String from, String to, boolean current,
			String description) {
		this.school = school;
		this.degree = degree;
		this.fieldofstudy = fieldofstudy;
		this.from = from;
		this.to = to;
		this.current = current;
		this.description = description;
	}
	
	public Education () {}

}
