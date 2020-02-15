package API_Tests;

import java.util.List;
import data_objects.Post;

public class temp_api_test {

	public static void main(String[] args) {
		
		String token = BoraServices.login("murad@test.com", "murad001");
		List<Post> posts = BoraServices.getAllPosts(token);
		BoraServices.printAllPosts(posts);
		
	}

}
