package et.test.police.business;

import et.test.callcenter.server.SvltTesting;
import excellence.framework.base.container.SpringContainer;

public class TT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		new SvltTesting().initServer();
	}

}
