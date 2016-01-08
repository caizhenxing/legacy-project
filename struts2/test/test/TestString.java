package test;

public class TestString {

	/**
	 * ¹¦ÄÜÃèÊö
	 * @param args
	 * May 26, 2009 3:13:24 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "a'b'c'd";
		System.out.println(s.replaceAll("\\'", "\\\\'"));
	}

}
