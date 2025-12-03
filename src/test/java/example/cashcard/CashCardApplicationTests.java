package example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test

	void shouldReturnACashCardWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();
	}

}

/**
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORn)
 *                                This will start our Spring Boot application
 *                                and make it available for our test to perform
 *                                requests to it.
 * 
 * @Autowired
 *            TestRestTemplate restTemplate;
 *            We've asked Spring to inject a test helper
 *            that’ll allow us to make HTTP requests to the locally running
 *            application.
 *            Note: Even though @Autowired is a form of Spring dependency
 *            injection,
 *            it’s best used only in tests.
 * 
 *            ResponseEntity<String> response =
 *            restTemplate.getForEntity("/cashcards/99", String.class);
 *            Here we use restTemplate to make an HTTP GET request to our
 *            application endpoint /cashcards/99.
 *            restTemplate will return a ResponseEntity,
 *            which we've captured in a variable we've named response.
 *            ResponseEntity is another helpful Spring object that provides
 *            valuable information
 *            about what happened with our request.
 * 
 *            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
 *            We can inspect many aspects of the response,
 *            including the HTTP Response Status code, which we expect to be 200
 *            OK.
 * 
 *            DocumentContext documentContext =
 *            JsonPath.parse(response.getBody());
 *            This converts the response String into a JSON-aware object with
 *            lots of helper methods.
 * 
 *            Number id = documentContext.read("$.id");
 *            assertThat(id).isNotNull();
 *            We expect that when we request a Cash Card with id of 99 a JSON
 *            object will be returned with something in the id field. 
 * 
 */