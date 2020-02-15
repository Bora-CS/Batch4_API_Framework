package data_objects;

public class Comment {

	public String _id;
	public String text;
	public String name;
	public String avatar;
	public String user;
	public String date;

	public Comment(String _id, String text, String name, String avatar, String user, String date) {
		this._id = _id;
		this.text = text;
		this.name = name;
		this.avatar = avatar;
		this.user = user;
		this.date = date;
	}

	public Comment() {
	}

}
