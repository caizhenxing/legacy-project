/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.test.string;

import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringContainer;

/**
 * @author zhang feng
 * 
 */
public class TestKs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyService ks = (KeyService) SpringContainer.getInstance().getBean(
				"KeyService");
		String value = ks.getNextPhone("cc_main", "1", "22511711");
		System.out.println(value);
	}

}
