/**
 * 	@(#)TestValue.java   2006-12-22 ÏÂÎç01:58:44
 *	 ¡£ 
 *	 
 */
package et.test.callcenter;

 /**
 * @author ddddd
 * @version 2006-12-22
 * @see
 */
public class TestValue {

	public void setSt(StringBuilder s)
	{
		s.append("aaaaaaaaa");
	}
	/**
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder s=new StringBuilder();
		TestValue tv=new TestValue();
		tv.setSt(s);
		System.out.println(s.toString());
	}

}
