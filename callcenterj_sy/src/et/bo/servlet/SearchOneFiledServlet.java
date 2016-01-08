/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import et.bo.agentDb.AgentInfoBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>ajax提供数据的主要有座席面板用</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class SearchOneFiledServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SearchOneFiledServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 根据不同参数调用不同类 为js提供不同数据
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		
		String cName = request.getParameter("cName");
		String agent = request.getParameter("agentNum");
		String threeCall = request.getParameter("threeCall");
		String threePort = request.getParameter("threePort");
		String expertType = request.getParameter("expertType");
		String incommingCall = request.getParameter("incommingCall");
		String lineNum = request.getParameter("lineNum");
		Map paramMap = new HashMap();
		paramMap.put("threeCall", threeCall);
		paramMap.put("threePort", threePort);
		paramMap.put("incommingCall", incommingCall);
		paramMap.put("lineNum", lineNum);
		if(expertType!=null)
		{
			paramMap.put("expertType", expertType);
		}
		if(cName != null)
		{
			AgentInfoBean bean = (AgentInfoBean)SpringRunningContainer.getInstance().getBean(cName+"Service");
			if(agent != null)
			{
				String ymd = TimeUtil.getTheTimeStr(new Date(), "yyyy-MM-dd");
				String info = bean.getAgentInfo(ymd, agent,paramMap);
				out.print(info);
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
