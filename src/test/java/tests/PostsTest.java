package tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.Matchers.greaterThan;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostsTest extends RAConfig {

	private static final Logger LOGGER = Logger.getLogger(PostsTest.class.getName());
    
	@Test
	public void getPosts() {
		get("/posts").then().assertThat().body("size()", greaterThan(10)).body("id", hasItems(1, 2, 3));
		LOGGER.info(String.format("get::posts verified"));	
	}

	@Test
	public void shouldRetrievePost() {
		getPostTest(1);
		getPostTest(2);
		getPostTest(3);
	}

	public void getPostTest(int id) {
		get("/posts/{id}", id).then().assertThat().body("id", equalTo(id)).body("title", notNullValue())
				.body("body", notNullValue()).body("unknownKey", notNullValue());
		LOGGER.info(String.format("get::post [%s] verified", id));		
	}
	@Test
	public void createPost() {
		Map<String,String> post = new HashMap<>();
        post.put("title", "RA tst Post");
        post.put("body", "RA tst Post");
        post.put("userId", "13");

       String id= given()
        .contentType("application/json")
        .body(post)
        .when().post("/posts").then().statusCode(201).extract().body().jsonPath().get("id").toString();
		LOGGER.info(String.format("post::post [%s] created",id));		
	}
}
