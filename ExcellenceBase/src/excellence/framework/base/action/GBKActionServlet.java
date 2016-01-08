/**
 * 创建日期 2005-7-1
 * 对所有action请求进行中文转码
 * <load-on-startup>1</load-on-startup>
 */
package excellence.framework.base.action;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import excellence.framework.base.container.SpringRunningContainer;

/**
 * @author zhao yifei
 * @version 2.0 2005-8-10 
 */
public class GBKActionServlet extends ActionServlet
{
	@Override
    protected void process(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        request.setCharacterEncoding("GB2312");
        response.setCharacterEncoding("GB2312");
        super.process(request, response);
    }  

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		WebApplicationContext wac = (WebApplicationContext) getServletContext().getAttribute(
				ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
        SpringRunningContainer.loadContext(wac);
        
        
    }
	
    
}