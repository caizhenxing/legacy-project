/**
 * 
 */
package et.test.testspring;

import java.io.IOException;

import excellence.common.util.Constants;
import excellence.framework.base.container.SpringContainer;

/**
 * @author Administrator
 *
 */
public class TestConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Constants c = (Constants)SpringContainer.getInstance().getBean("constants");
		System.out.println(c.getProperty("WORK_BEGIN_TIME"));
	}

}
