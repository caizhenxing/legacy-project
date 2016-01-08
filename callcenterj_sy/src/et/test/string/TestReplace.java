/**
 * 沈阳卓越科技有限公司
 * 2008-4-21
 */
package et.test.string;

/**
 * @author zhang feng
 * 
 */
public class TestReplace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "INIT:;";
		// str.replaceAll(";", " ");
		int i = str.indexOf(";");
		System.out.println(i);
		str = str.substring(0, i);
		System.out.println(str);
	}

}
