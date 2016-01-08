package et.bo.fileUpload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import et.bo.addressMaintenance.service.AddressMainService;
import excellence.framework.base.container.SpringRunningContainer;

public class FileUploadServlet extends HttpServlet {
	private Map<String,String> kvMap = new HashMap<String,String>();
	/**
	 * Constructor of the object.
	 */
	public FileUploadServlet() {
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

		this.doPost(request, response);
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
		kvMap.clear();
		boolean isFile=false;
		String uploadPath=request.getParameter("uploadPath")==null?this.getServletContext().getRealPath("/")+"UploadFile"+System.getProperty("file.separator"):request.getParameter("uploadPath");
		String newFileName = "";
		String oldName = "";
		String dbNewName = "";
		String id = "";
		request.setCharacterEncoding("gb2312");
		if(ServletFileUpload.isMultipartContent(request))
		{
			ServletFileUpload fUpload = new ServletFileUpload();
			try {
				FileItemIterator fIt = fUpload.getItemIterator(request);
				while(fIt.hasNext())
				{
					FileItemStream item = fIt.next();
					String name = item.getFieldName();
					InputStream stream = item.openStream();
					if(item.isFormField())
					{
						
						String value = Streams.asString(stream);
						kvMap.put(name,value==null?"":value);
						stream.close();
					}
					else
					{
						File file = new File(item.getName());
						String fileName = file.getName();
						oldName = this.getFileName(fileName);
						//kvMap.put("oldName", oldName);
						
						BufferedInputStream bis = new BufferedInputStream(stream);
						FileOutputStream fOut = new FileOutputStream(new File(uploadPath+this.getUniqueFileName(fileName)));
						BufferedOutputStream bos = new BufferedOutputStream(fOut);
						dbNewName = this.getUniqueFileName(fileName);
						newFileName = this.getDBFiledFileName(uploadPath+dbNewName, request);
						//kvMap.put("newName", newFileName);
						//System.out.println(this.getFileName(fileName)+"#@#@#"+newFileName);
						Streams.copy(bis, bos, true);
						
						fOut.close();
						bos.flush();
						
						stream.close();
						bis.close();
						bos.close();
						id=this.addFile(oldName, newFileName, dbNewName, getExtendName(oldName));
						
						isFile = true;
					}
					
					
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		response.setContentType("text/html; charset=gb2312");
		if(isFile)
		response.sendRedirect("./fileUpload/fileUpload.jsp?newName="+newFileName+"&oldName="+oldName+"&uploadPath="+uploadPath+"&dbid="+id);
		else
		response.sendRedirect("./fileUpload/fileUpload.jsp");
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	private String addFile(String oldName, String filePath, String newName, String type)
	{
		UploadFileService fileOper = (UploadFileService)SpringRunningContainer.getInstance().getBean("UploadFileService");
		return fileOper.addFile(oldName, filePath, newName, type);
	}
	private String getFileName(String fileName)
	{
		if(fileName==null||"".equals(fileName.trim()))
			return "";
		else
		{
			String sep = System.getProperty("file.separator");
			int index = fileName.lastIndexOf(sep);
			fileName = fileName.substring(index+1);
			return fileName;
		}
	}
	/**
	 * 上传文件时防止文件名字重复 以这个名字
	 * @param fileName
	 * @return
	 */
	private String getUniqueFileName(String fileName)
	{
		if(fileName==null||"".equals(fileName.trim()))
			return "";
		int begin = fileName.indexOf("/")!=-1?fileName.lastIndexOf("/")+1:0;
		String tempName = fileName.substring(0,fileName.indexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
		String timeStr = sdf.format(new Date())+"."+getExtendName(fileName);
		return timeStr;
	}
	/**
	 * 取文件扩展名
	 * @param fileName
	 * @return
	 */
	private String getExtendName(String fileName)
	{
		if(fileName==null||"".equals(fileName.trim()))
			return "";
		return fileName.substring(fileName.indexOf(".")+1);
	}
	/**
	 * 将kvMap里值变成超链接参数返回tblOldNameFiled=noField&dbFieldFileName=callcenterj_sy\UploadFile\09-03-20-14-15-06-281.jpg&tblName=noTbl&Submit2= 上 传 &tblNewNameField=noField
	 * @return
	 */
	private String getParames()
	{
		if(kvMap.size()==0)
			return "";
		else
		{
			StringBuffer sb = new StringBuffer();
			int count=0;
			Iterator<String> keys = kvMap.keySet().iterator();
			while(keys.hasNext())
			{
				String key = keys.next();
				if(kvMap.get(key)!=null&&!"".equals(kvMap.get(key).trim()))
				{
					if(count>0)
					{
		
						sb.append("&"+key+"="+kvMap.get(key).trim());
					}
					else
					{
						count++;
						sb.append("?"+key+"="+kvMap.get(key).trim());
					}
				}
			}
			//System.out.println(sb.toString());
			return sb.toString();
		}
	}
	/**
	 * 得到唯一文件名 以工程为起始目录
	 * @param fileName
	 * @param request
	 * @return
	 */
	private String getDBFiledFileName(String fileName,HttpServletRequest request)
	{
		if(fileName==null||"".equals(fileName.trim()))
		{
			return "";
		}
		else
		{
			String rootName = request.getContextPath().substring(1); //工程名字 /callcenterj_sy
			int index = fileName.indexOf(rootName);
			return fileName.substring(index);
		}
	}
}