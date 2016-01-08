/*
 * @(#)QuestionAction.java	 2008-03-19
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

import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.container.SpringRunningContainer;
/**
 * <p>tomcat����ʱ��������Ϣ</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class ClassTreeLoadServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ClassTreeLoadServlet() {
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
		PrintWriter out = response.getWriter();
		
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
	 * Initialization of the servlet. load tree<br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
		super.init();
		//ClassTreeService classTree = new ClassTreeServiceImpl();
		//classTree.loadParamTree();
		//׼��������
		ClassTreeService classTreeService = (ClassTreeService)SpringRunningContainer.getInstance().getBean("ClassTreeService");
		classTreeService.reloadParamTree();
		ClassTreeService ivrClassTreeService = (ClassTreeService)SpringRunningContainer.getInstance().getBean("IvrClassTreeService");
		ivrClassTreeService.reloadParamTree();
		//System.out.println("���������");
		
	}

}
