/**
 * 	@(#)TestSpring.java   2006-12-4 ÏÂÎç01:39:13
 *	 ¡£ 
 *	 
 */
package test.client;

import org.codehaus.xfire.XFire;
import org.springframework.context.ApplicationContext;

import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author ddddd
 * @version 2006-12-4
 * @see
 */
public class TestSpring {

	/**
	 * @param
	 * @version 2006-12-4
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
        ApplicationContext appContext =SpringRunningContainer.getInstance().getContext();
        String xfireBeanName = "xfire";
        XFire xfire=(XFire) appContext.getBean(xfireBeanName, XFire.class);
        System.out.println(xfire==null);
	}

}
