/**
 * 
 */
package et.test.string;

/**
 * @author Administrator
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = 0;
		for (int i = 0; i < 10; i++) {
			//k = k++;
			int f = k++;
			System.out.println(f);
		}
		String str = "adfkjskdfjsdklfjsdkfjsdklfjsdkfjsdl";
		System.out.println(str.contains("sd"));
	}

}
