/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 1, 20071:26:22 PM
 * 文件名：EntryJspMeta.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.tools.auto.create.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zyf.tools.FileUtil;

/**
 * @author zhaoyf
 *
 */
public class EntryJspMeta {

	private static String staticArr="arr[{0}] = [\"{1}\", \"\", \"<c:url value = ''{2}''/>\"];";
	
	private String absPath;
	
	private String fileName;
	
	
	/**
	 * 模块名
	 */
	private String name="";
	/**
	 * arr[0] = ["列表", "", "<c:url value = '/hr/ratemanage.do?step=list&paginater.pageSize=14'/>"];
	 */
	private Map arrs=new LinkedHashMap();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map getArrs() {
		return arrs;
	}
	public void setArrs(Map args) {
		this.arrs = args;
	}
//	public void addEntry(String key,String value)
//	{
//		arrs.put(key, value);
//	}
//	public void addArr(String key,String value)
//	{
//		arrs.put(key, value);
//	}
	public void addArrs(String key,String value)
	{
		arrs.put(key, value);
	}
	public void createFiles(String realPath)
	{
		String path=realPath+this.getAbsPath()+this.getFileName();
		BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("entry.jsp"));
		PrintWriter pw=FileUtil.getFileWriter(path);
		String arrSb=getArr();
		String temp;
		try {
			while((temp=br.readLine())!=null)
			{
				
				pw.println(MessageFormat.format(temp,new Object[]{name,arrSb}));
				
			}
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
		
		}
	}
	private String getArr()
	{
		StringBuffer arrSb=new StringBuffer();
		Iterator i=arrs.keySet().iterator();
		int size=0;
		while(i.hasNext())
		{
			String key=(String)i.next();
			String value=(String)arrs.get(key);
			arrSb.append("\t");
			arrSb.append(MessageFormat.format(staticArr,new Object[]{Integer.toString(size),key,value}));
			arrSb.append("\r\n");
			size++;
		}
		return arrSb.toString();
	}
	public String getAbsPath() {
		return absPath;
	}
	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
