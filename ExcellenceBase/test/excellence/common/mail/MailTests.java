package excellence.common.mail;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MailTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for excellence.common.mail");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestReceiveMail.class);
		suite.addTestSuite(TestSendMail.class);
		//$JUnit-END$
		return suite;
	}

}
