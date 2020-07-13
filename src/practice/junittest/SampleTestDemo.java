package practice.junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SampleTestDemo {

	private static final Logger log = Logger.getLogger(SampleTestDemo.class.getName());

	@BeforeAll
	static void setup() {
		log.info("@Before all -executes once before all test method in the class");
	}

	@BeforeEach
	void init() {
		log.info("@Before Each - executes before each test method in the class");
	}

	@DisplayName("Single Test Successful")
	@Test
	void testSingleSuccessTest() {
		log.info("Success");
	}

	@Test
	@Disabled("Not implemented yet")
	void testShowSomething() {
	}

	@AfterEach
	void tearDown() {
		log.info("@AfterEach - executed after each test method.");
	}

	@AfterAll
	static void done() {
		log.info("@AfterAll - executed after all test methods.");
	}

	@Test
	void groupAssertions() {
		int[] numbers = { 0, 1, 2, 3, 4 };
		assertAll("numbers", () -> assertEquals(numbers[0], 0), () -> assertEquals(numbers[3], 3),
				() -> assertEquals(numbers[4], 4));
	}
	
	@Test
	public void whenExceptionThrown_thenAssertionSucceeds() {
	    Exception exception = assertThrows(NumberFormatException.class, () -> {
	        Integer.parseInt("1a");
	    });
	 
	    String expectedMessage = "For input string";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
