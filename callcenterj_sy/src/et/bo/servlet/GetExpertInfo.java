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
		// ר�����
		String expertType = request.getParameter("experttype");
		
		if (expertType!=null) {
//			 ����ֵ
			String responseText = "";
			// �õ�����ͨ���Ķ�Ӧ����Ϣ
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
