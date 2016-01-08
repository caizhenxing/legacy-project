/**
 * 
 * 制作时间：Oct 25, 20077:45:04 PM
 * 文件名：AutoCreateSearchJspf.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.tools.auto.create.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import base.zyf.tools.FileUtil;
import base.zyf.tools.auto.create.AutoCreateFile;

/**
 * @author zhaoyf
 * 
 */
public abstract class AutoCreateSearchJspf extends AutoCreateFile{

//	private static StringBuffer head=new StringBuffer();
//	static 
//	{
//		head.append("<%@ taglib uri=\"/WEB-INF/search.tld\" prefix=\"search\" %>");
//		head.append("<jsp:directive.page contentType=\"text/html; charset=GBK\"/>");
//		head.append("<div class=\"update_subhead\">");
//		head.append("<span class=\"switch_close\" onclick=\"StyleControl.switchDiv(this,$('supplierQuery'))\" title=\"点击这里进行查询\">查询条件</span>");
//		head.append("</div>");
//		head.append("<div id=\"supplierQuery\" style=\"display:none\">");
//		head.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"Detail\" id=\"parenttable\" style=\"display:\">");
//	}
//	private static StringBuffer tail=new StringBuffer();
//	static
//	{
//		tail.append("</table>");
//		tail.append("<div class=\"query_button\">");
//		tail.append("<input type=\"button\" value=\"\" name=\"\" id=\"opera_query\"  title=\"点击查询\"  onClick=\"CurrentPage.query();\"/>");
//		tail.append("</div>");
//		tail.append("</div>");
//		
//	}
	
	
//	private static String enter="\r\n";
//	private static StringBuffer tr=new StringBuffer();
//	/**
//	 * {0}->
//	 * {1}->第一项汉字，例如：办公电子邮箱
//	 * {2}->第二项汉字
//	 * {3}->第一项输入
//	 * {4}->第二项输入
//	 */
//	static
//	{
//		tr.append("<tr>");
//		tr.append("<td class=\"attribute\">{1}</td>");
//		tr.append("<td >");
//		tr.append("{3}");
//		tr.append("</td>");
//		tr.append("<td class=\"attribute\">{2}</td>");
//		tr.append("<td >");
//		tr.append("{4}");
//		tr.append("</td>");
//		tr.append("</tr>");
//	}
//	private static StringBuffer tr1=new StringBuffer();
//	/**
//	 * 
//	 * {5}->第一项汉字，例如：办公电子邮箱
//	 * {6}->第二项输入
//	 */
//	static
//	{
//		tr1.append("<tr>");
//		tr1.append("<td class=\"attribute\">{5}</td>");
//		tr1.append("<td colspan=3>");
//		tr1.append("{6}");
//		tr1.append("</td>");
//		
//		tr1.append("</tr>");
//	}
	
	
	
	List searchJspfDatas=new ArrayList();
//	protected abstract void readMetaData();
//	protected abstract void findFiles(String name);
//	protected String realPath;
//	public String getRealPath() {
//		return realPath;
//	}
//	public void setRealPath(String realPath) {
//		this.realPath = realPath;
//	}
//	List files=new ArrayList();
//	public List getFiles() {
//		return files;
//	}
//	public void setFiles(List files) {
//		this.files = files;
//	}
//	public void addFile(String file)
//	{
//		this.files.add(file);
//	}
	public void createJsp(String name)
	{
		findFiles(name);
		readMetaData();
		for(int i=0,size=searchJspfDatas.size();i<size;i++)
		{
			SearchJspfData sjd=(SearchJspfData)searchJspfDatas.get(i);
			String path=realPath+sjd.getAbsPath()+sjd.getFileName();
			BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("search.jspf"));
			PrintWriter pw=FileUtil.getFileWriter(path);
			files.add(path);
			String temp;
			String search=sjd.getSearchTag();
			try {
				while((temp=br.readLine())!=null)
				pw.println(MessageFormat.format(temp,new Object[]{search}));
				br.close();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pw.close();
				FileUtil.removeFile(path);
				break;
			}
		}
	}
	
	
}
