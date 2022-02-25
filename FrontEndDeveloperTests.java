// --== CS400 File Header Information ==--
// Name: Axel Agelii
// Email: agelii@wisc.edu
// Team: GD Blue
// Role: Frontend Developer
// TA: Surabhi
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * This class contains a set of junit tests for the front end of the Plane Mapper project.
 *
 * @author Axel
 */
public class FrontEndDeveloperTests {

    /**
		 * This test runs the front end and redirects its output to a string. It passes "x" as a command
		 * expecting the program to exit. If the front end exits, the test will succeed. If "x" doesn't
		 * cause the program to exit, the test will fail.
	  */
    @Test
		public void pressXToExitTest() {
			PrintStream standardOut = System.out;
			InputStream standardIn = System.in;
			try {
				String input = "x";
				// set the input stream to our input (with an x)
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));

				Object frontend = new Frontend();
				((Frontend) frontend).run(new Backend(new StringReader("name,edges\n" +
					"IAH,\"SFO,6,240\"\"TPE,16,800\"\n" +
					"SFO,\"IAH,6,240\"\"TPE,14,500\"\n" +
					"TPE,\"IAH,16,800\"\"SFO,14,500\"\n")));

				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				assertNotEquals(null, frontend);
				// test passes
			} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				fail("Exception occurred");
				// test failed
			}
		}

  /**
   * This test runs the front end and redirects its output to a string. It passes "s","x", and "x"
   * as commands. The expected behavior is to enter search mode, exit search mode returning to base
   * mode, then exiting the program. So, the test passes if the program goes to search mode. It fails
   * if it doesn't visit search mode.
   */
  @Test
  public void pressSToEnterSearchTest() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "s" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
      // set the input stream to our input (with an s,x, and x)
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));

      Object frontend = new Frontend();
      ((Frontend) frontend).run(new Backend(new StringReader("name,edges\n" +
        "IAH,\"SFO,6,240\"\"TPE,16,800\"\n" +
        "SFO,\"IAH,6,240\"\"TPE,14,500\"\n" +
        "TPE,\"IAH,16,800\"\"SFO,14,500\"\n")));

      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      String appOutput = outputStreamCaptor.toString();
      assertTrue(appOutput.contains("Search Mode"));
      // test passes
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      fail("Exception occured");
      // test failed
    }
  }

	/**
	 * This test runs the front end and redirects its output to a string. It passes "i","x", and "x"
	 * as commands. The expected behavior is to enter insert mode, exit insert mode returning to base
	 * mode, then exiting the program. So, the test passes if the program goes to insert mode. It fails
	 * if it doesn't visit insert mode.
	 */
	@Test
	public void pressIToEnterInsertTest() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "i" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
			// set the input stream to our input (with an i,x, and x)
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));

			Object frontend = new Frontend();
			((Frontend) frontend).run(new Backend(new StringReader("name,edges\n" +
				"IAH,\"SFO,6,240\"\"TPE,16,800\"\n" +
				"SFO,\"IAH,6,240\"\"TPE,14,500\"\n" +
				"TPE,\"IAH,16,800\"\"SFO,14,500\"\n")));

			// set the output back to standard out for running the test
			System.setOut(standardOut);
			// same for standard in
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertEquals(true, appOutput.contains("Insert Mode"));
			// test passes
		}
		 catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail("Exception occurred");
			// test failed
		}
	}
}
