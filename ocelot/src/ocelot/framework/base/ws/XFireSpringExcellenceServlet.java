/**
 * 	@(#)XFireSpringExcellenceServlet.java   2006-12-4 ÏÂÎç01:20:24
 *	 ¡£ 
 *	 
 */
package ocelot.framework.base.ws;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ocelot.framework.base.container.SpringContainer;
import ocelot.framework.base.container.SpringRunningContainer;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.transport.http.XFireServlet;
import org.springframework.context.ApplicationContext;


 /**
 * @author yifei zhao
 * @version 2006-12-4
 * @see
 */
public class XFireSpringExcellenceServlet extends XFireServlet {

	private String xfireBeanName = "xfire";

    private XFire xfire;

    public void init(ServletConfig servletConfig)
        throws ServletException
    {
		try {
			SpringContainer sc = SpringContainer.getInstance();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}    	
		ApplicationContext appContext =SpringRunningContainer.getInstance().getContext();
        String configBeanName = servletConfig.getInitParameter("XFireBeanName");

        xfireBeanName = ((configBeanName != null) && (!"".equals(configBeanName.trim()))) ? configBeanName
                : xfireBeanName;
        
        xfire = (XFire) appContext.getBean(xfireBeanName, XFire.class);
        
        super.init(servletConfig);
    }

    public XFire createXFire()
    {
        return xfire;
    }
}
