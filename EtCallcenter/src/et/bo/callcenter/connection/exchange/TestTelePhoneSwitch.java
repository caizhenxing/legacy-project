/**
 * 	@(#)TestTelePhoneSwitch.java   2007-1-29 ÏÂÎç01:55:46
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.connection.exchange;

import java.util.Timer;

import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author zhaoyifei
 * @version 2007-1-29
 * @see
 */
public class TestTelePhoneSwitch {

	/**
	 * @param
	 * @version 2007-1-29
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		Timer timer=(Timer)sc.getBean("timerFactory");
		TelephoneSwitchService tss=(TelephoneSwitchService)sc.getBean("TelephoneSwitch"); 
		tss.start();
	}

}
