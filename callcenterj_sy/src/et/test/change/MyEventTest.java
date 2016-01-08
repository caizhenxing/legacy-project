/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.change;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author zhang feng
 * 
 */
public class MyEventTest extends TestCase implements MyChangeListener {

	private MyClass myclass;

	private String testname;

	/**
	 * Constructor for MyEventTest.
	 * 
	 * @param arg0
	 */
	public MyEventTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(MyEventTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		myclass = new MyClass();
		myclass.addMyChangeListener(this);
		this.testname = "yz21.org";
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		myclass = null;
	}

	/*
	 * @see TestCase#suite()
	 */
	public static Test suite() {
		return new TestSuite(MyEventTest.class); // 以类为参数
	}

	public void MyChanged(MyChangeEvent event) {
		this.testname = event.getMyname();
	}

	public void testMyEvent() {
		myclass.testMyEvent("www.yz21.org");
		this.assertEquals("测试事件", "www.yz21.org", testname);
	}
}
