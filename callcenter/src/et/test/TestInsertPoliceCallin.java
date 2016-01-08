/**
 * 	@(#)TestInsertPoliceCallin.java   Dec 7, 2006 9:57:44 AM
 *	 ¡£ 
 *	 
 */
package et.test;

import junit.framework.TestCase;
import et.bo.pcc.policeinfo.PoliceInfoService;
import excellence.framework.base.container.SpringContainer;

/**
 * @author zhang
 * @version Dec 7, 2006
 * @see
 */
public class TestInsertPoliceCallin extends TestCase {
	
	public void testInsert(){
		SpringContainer sc=SpringContainer.getInstance();
		PoliceInfoService info = (PoliceInfoService)sc.getBean("PoliceInfoService");
		info.transactBefore();
		System.out.println("²Ù×÷³É¹¦");
	}

}
