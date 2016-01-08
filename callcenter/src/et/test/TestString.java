/**
 * 	@(#)TestString.java   Oct 31, 2006 1:16:56 PM
 *	 ¡£ 
 *	 
 */
package et.test;

/**
 * @author zhang
 * @version Oct 31, 2006
 * @see
 */
public class TestString {

	/**
	 * @param
	 * @version Oct 31, 2006
	 * @return
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String idcard = "210102195404252539";
		// String first = idcard.substring(0,6);
		// String middle = idcard.substring(8,17);
		// idcard = first + middle;
		// System.out.println(idcard);
		int k = 1;
		for (int i = 1; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.println("i "+ j+" k "+k);
			}
			k++;
		}
	}

}
