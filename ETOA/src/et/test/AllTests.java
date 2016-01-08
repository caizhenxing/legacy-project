package et.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for et.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(Tes.class);
		//$JUnit-END$
		return suite;
	}

}
