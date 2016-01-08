/**
 * @name ParameterServlet
 * load parameter from file which type is xml or property.
 * <load-on-startup>2</load-on-startup>
 */
package excellence.framework.base.action;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import excellence.common.util.Constants;
/**
 * @author zhao yifei
 * 处理参数配置路径
 * @version 2.0 2006-8-16 
 */
public class ParameterServlet extends HttpServlet {

	//private static Log log = LogFactory.getLog(ParameterServlet.class);
	public void init() throws ServletException {
		 StringBuffer path=new StringBuffer();
		 ServletContext context = getServletContext();
		 path.append(context.getRealPath(""));
		 path.append(this.getServletConfig().getInitParameter("path"));
		 Constants.setFilePath(path.toString());
		 super.init();
	}
}
