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

import java.net.URI;

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
		assertThat(id).isEqualTo(99);

		Double amount = documentContext.read("$.amount");
		assertThat(amount).isEqualTo(123.45);
	}

	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldCrateANewCashCard() {
		CashCard newCard = new CashCard(null, 250.0);

		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/cashcards", newCard, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	
		/**Get the location of the new location from the response header  of  the Post request made*/
		URI locationOfTheNewCashCard = createResponse.getHeaders().getLocation();
	
	ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfTheNewCashCard, String.class);//get the response in the located api endpoints got from location header
	
	//confirm that the response status code is equal to ok
	assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
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