/**
 * 	@(#)TestDate.java   Oct 31, 2006 3:47:24 PM
 *	 ¡£ 
 *	 
 */
package et.test.callcenter;

import java.util.Date;

/**
 * @author zhang
 * @version Oct 31, 2006
 * @see
 */
public class TestDate {

	/**
	 * @param
	 * @version Oct 31, 2006
	 * @return
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format(d);
		System.out.println(sdf.format(d));
	}

}
