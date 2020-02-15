package end_to_end_tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import API_Tests.BoraServices;
import UI_Resource.Constants;
import data_objects.Post;

public class Posts_end_to_end_test {

	private static WebDriver driver;

	public static void main(String[] args) {

		try {
			System.out.println("Test Started!");
			String username = "murad@test.com";
			String password = "murad001";

			String token = BoraServices.login(username, password);
			List<Post> posts = BoraServices.getAllPosts(token);
			int numberOfPostsFromAPI = posts.size();

			System.setProperty("webdriver.chrome.driver", "src/main/java/UI_Resource/chromedriver");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.get(Constants.APPLICATION_URL);

			driver.findElement(By.xpath("//a[text()='Login']")).click();
			driver.findElement(By.name("email")).sendKeys(username);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.xpath("//input[@type='submit']")).click();

			WebElement posts_link = driver.findElement(By.xpath("//a[text()='Posts']"));
			posts_link.click();

			String parentXpath = "//div[@class='post bg-white p-1 my-1']";
			List<WebElement> ui_posts_elements = driver.findElements(By.xpath(parentXpath));
			int numberOfPostsFromUI = ui_posts_elements.size();

			if (numberOfPostsFromAPI != numberOfPostsFromUI) {
				String errorMessage = "Number of Posts from API: " + numberOfPostsFromAPI + "\n"
						+ "Number of Posts from UI: " + numberOfPostsFromUI;
				throw new Exception(errorMessage);
			}

			for (int i = 1; i <= numberOfPostsFromUI; i++) {
				String userNameXpath = parentXpath + "[" + i + "]//h4";
				String contentXpath = parentXpath + "[" + i + "]//p[@class='my-1']";
				String discussionLinkXpath = parentXpath + "[" + i + "]//a[@class='btn btn-primary']";
				String ui_name = driver.findElement(By.xpath(userNameXpath)).getText();
				String ui_text = driver.findElement(By.xpath(contentXpath)).getText();
				String ui_id = driver.findElement(By.xpath(discussionLinkXpath)).getAttribute("href")
						.replace("https://lit-mesa-27064.herokuapp.com/posts/", "");
				
				boolean found = false;
				Post currentPost = null;
				for (Post api_post : posts) {
					if (api_post._id.equals(ui_id)) {
						found = true;
						currentPost = api_post;
					}
				}

				System.out.println("Testing Post with ID: " + ui_id);
				if (!found) {
					String errorMessage = "Post with ID <" + ui_id + "> is not found in API response.";
					throw new Exception(errorMessage);
				} else {
					if (!currentPost.name.equals(ui_name)) {
						String errorMessage = "Expected Name(UI): " + ui_name + "\n" + "Actual Name(API): "
								+ currentPost.name;
						throw new Exception(errorMessage);
					}
					if (!currentPost.text.replace("\n", "").replace(",  ", ", ").equals(ui_text)) {
						System.out.println();
						System.out.println();
						String errorMessage = "Expected Text(UI): " + ui_text + "\n" + "Actual Text(API): "
								+ currentPost.text;
						throw new Exception(errorMessage);
					}
				}

			}
			
			System.out.println("Test Passed!");
		} catch (Exception e) {
			System.out.println("Test Failed:");
			System.out.println(e.getMessage());
		} finally {
			driver.close();
			driver.quit();
		}

	}

}
