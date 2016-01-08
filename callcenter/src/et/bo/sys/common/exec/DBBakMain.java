/**
 * 	@(#)DBBakMain.java   2006-12-14 ÉÏÎç09:37:31
 *	 ¡£ 
 *	 
 */
package et.bo.sys.common.exec;

import java.util.Timer;

import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author ddddd
 * @version 2006-12-14
 * @see
 */
public class DBBakMain {

	/**
	 * @param
	 * @version 2006-12-14
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer s=SpringContainer.getInstance();
		Timer timer=(Timer)SpringRunningContainer.getInstance().getBean("timerFactory");
	}

}
