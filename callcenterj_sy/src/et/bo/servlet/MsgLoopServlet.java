/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import et.bo.messages.messageLoop.MessageCollection;
import et.bo.messages.messageLoop.MessageInfo;
import et.bo.messages.messageLoop.MessageLoop;
/**
 * <p>��Ϣ��ѯ�� ǰ̨js���������õ���Ϣ</p>
 * 
 * @version 2008-12-03
 * @author wangwenquan
 */
public class MsgLoopServlet extends HttpServlet {
	private String separateSign = "[#^%#]";
	/**
	 * Constructor of the object.
	 */
	public MsgLoopServlet() {
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
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
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
		String receiveMan = request.getParameter("receiveMan");
		if(receiveMan==null||"".equals(receiveMan.trim()))
		{
			receiveMan = "";
			out.print("noMsg");

		}
		else
		{
			MessageCollection cols = MessageLoop.getMsgCols(receiveMan);
			if(cols == null)
			{
				out.print("noMsg");

			}
			else
			{
				//��ǰ��Ϣ��������Ϣ
				MessageInfo info = cols.getCurMsgInfo();
				out.print(info.getSend_id()+info.getSendTimeStr()+this.separateSign+info.getMessage_content()+this.separateSign+cols.getMessageSize());
				
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
		MessageLoop.InitMessageLoop();
	}

}
