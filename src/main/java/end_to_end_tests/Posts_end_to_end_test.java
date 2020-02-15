package end_to_end_tests;

import java.util.List;

import API_Tests.BoraServices;
import data_objects.Post;

public class Posts_end_to_end_test {

	public static void main(String[] args) {
		
		String token = BoraServices.login("murad@test.com", "murad001");
		List<Post> posts = BoraServices.getAllPosts(token);
		int numberOfPostsFromAPI = posts.size();
		
		System.out.println(numberOfPostsFromAPI);

	}

}
