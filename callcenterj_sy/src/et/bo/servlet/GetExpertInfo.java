/**
 * 
 */
package et.bo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import et.bo.custinfo.service.PhoneinfoService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * @author zhang feng
 * 
 */
public class GetExpertInfo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response, String method)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		//System.out.println("experttype...................");
		// 专家类别
		String expertType = request.getParameter("experttype");
		
		if (expertType!=null) {
//			 返回值
			String responseText = "";
			// 得到三方通话的对应的信息
			PhoneinfoService pis = (PhoneinfoService) SpringRunningContainer
					.getInstance().getBean("PhoneinfoService");
			
			responseText = pis.getExpertList(expertType);
			
			//System.out.println(responseText+"????????????????????????????????????????????????????????");

			PrintWriter out = response.getWriter();
			out.println(responseText);

			out.close();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response, "GET");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response, "POST");
	}
}
