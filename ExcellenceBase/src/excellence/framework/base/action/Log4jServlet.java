/**
 * 加载log4j的配置文件
 * <load-on-startup>0</load-on-startup>
 */
package excellence.framework.base.action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
/**
 * @author zhao yifei
 * 加载log4j的配置文件
 * @version 2.0 2005-8-10 
 */

public class Log4jServlet extends HttpServlet {

	public void init() throws ServletException {
		 StringBuffer path=new StringBuffer();
		 ServletContext context = getServletContext();
		 path.append(context.getRealPath(""));
		 path.append(this.getServletConfig().getInitParameter("log4j"));
		 System.out.println("加在log配置文件，路径："+path.toString());
		 DOMConfigurator.configure(path.toString());
		 Logger log = Logger.getLogger(Log4jServlet.class);
		 log.info("log properties file is success load");
		 log.info("log4j started!");
		 super.init();
		
	}

}
