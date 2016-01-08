package et.test.string;

import junit.framework.TestCase;

public class TestString extends TestCase {

	public void testSubString(){
		String a = "c:/dat.bat$";
		a = a.substring(0, a.length()-1);
		System.out.println(a);
	}
	
}
