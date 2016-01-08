/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import et.common.tableExl.CommonExcel;
import et.common.tableExl.ImgExcel;
import excellence.common.util.time.TimeUtil;
/**
 * <p>各案例库下统计表格转excel</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class CommonExcelServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CommonExcelServlet() {
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
	 * 各案例库下统计解析前台数据转换成excel返回<br>
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/vnd.ms-excel");
		//response.setContentType(contentType);   
		response.setHeader("Content-disposition", "attachment; filename="+TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd-hh-mm-ss")+".xls");   
		String tblCells = request.getParameter("hiddenV");
		String sheetName = request.getParameter("sheetName");
		if(sheetName==null)
		{
			sheetName=request.getAttribute("sheetName")==null?"":request.getAttribute("sheetName").toString();
		}
		String imgName = request.getParameter("imgName");
		String type = request.getAttribute("type")==null?"":request.getAttribute("type").toString();
		//System.out.println(new String(sheetName.getBytes("iso8859-1"),"GBK"));
		if("List<List<String>>".equals(type))
		{
			List<List<String>> cells = (List<List<String>>)request.getAttribute("cells");
			//int cols = request.getAttribute("cols")==null?0:Integer.parseInt(request.getAttribute("cols").toString());
			CommonExcel ce = new CommonExcel();

			ce.createExcel(response.getOutputStream(), sheetName, cells);
		}
		if(tblCells!=null&&"".equals(tblCells.trim())==false)
		{
			if(sheetName!=null)
			{
				sheetName = new String(sheetName.getBytes("iso8859-1"),"GBK");
			}
			tblCells = new String(tblCells.getBytes("iso8859-1"),"GBK");
			CommonExcel ce = new CommonExcel();
			ce.createExcel(response.getOutputStream(), sheetName, tblCells);
		}
		else if(imgName!=null&&!"".equals(imgName.trim()))
		{
			String basePath =  this.getServletContext().getRealPath("/");
			int index = basePath.indexOf("\\webapps");
			basePath = basePath.substring(0,index);
			ImgExcel imgExcel = new ImgExcel();
			imgExcel.createExcel(basePath, imgName, response.getOutputStream());
		}
		
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
