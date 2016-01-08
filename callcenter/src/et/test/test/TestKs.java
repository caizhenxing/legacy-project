package et.test.test;

import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

public class TestKs {

	/**
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		SpringRunningContainer src=SpringRunningContainer.getInstance();
		KeyService ks=(KeyService)src.getBean("KeyService");
		System.out.println(ks.getNext("CC_LOG"));
	}

}
