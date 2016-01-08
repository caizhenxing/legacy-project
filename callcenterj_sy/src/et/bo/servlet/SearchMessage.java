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

import et.bo.messages.service.MessagesService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * @author Administrator
 * 
 */
public class SearchMessage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response, String method)
			throws ServletException, IOException {
		response.setContentType("text/xml");

		// String firstName = request.getParameter("firstName");
		// String middleName = request.getParameter("middleName");
		// String birthday = request.getParameter("birthday");

		// String responseText = "hello:" + firstName + "-" + middleName
		// + "。your birthday is " + birthday + "。" + "[method:" + method
		// + "]";

		String responseText = "";

		MessagesService ms = (MessagesService) SpringRunningContainer
				.getInstance().getBean("MessagesService");

		UserBean ub = (UserBean) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String user = "";
		if(ub!=null){
			user = ub.getUserId();
		}
		
		
		// 检查是否有信息
		if (ms.isHaveMessage(user)) {
			responseText = "1";
		}else{
			responseText = "0";
		}
		
		String stateCount = ms.getStateCount(user);
		
		PrintWriter out = response.getWriter();
		out.println(responseText + "state" +stateCount);//最终合成的串应该是1state2
		
		out.close();

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
