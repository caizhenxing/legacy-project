/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 5, 20073:24:06 PM
 * 文件名：InfoJspData.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.tools.auto.create.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.zyf.tools.FileUtil;
import com.zyf.tools.auto.create.search.SearchMeta;

/**
 * @author zhaoyf
 *
 */
public class InfoJspData {

	private static String enter="\r\n";
	private static String tab="\t";
	private static StringBuffer tr=new StringBuffer();
	/**
	 * 
	 * {0}->具体的td
	 */
	static
	{
		tr.append(tab+tab);
		tr.append("<tr>");
		tr.append(enter);
		tr.append("{0}");
		tr.append(enter);
		tr.append(tab+tab);
		tr.append("</tr>");
		tr.append(enter);
	}

	
	private String fileName;
	private String absPath;
	private String submit;
	private String create;
	private StringBuffer validate=new StringBuffer();
	private List infoMetas=new ArrayList();
	private int tdMax=2;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAbsPath() {
		return absPath;
	}
	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
	public List getInfoMetas() {
		return infoMetas;
	}
	public void setInfoMetas(List infoMetas) {
		this.infoMetas = infoMetas;
	}
	public int getTdMax() {
		return tdMax;
	}
	public void setTdMax(int tdMax) {
		this.tdMax = tdMax;
	}
	
	
	public void addInfoMeta(InfoMeta im)
	{
		this.infoMetas.add(im);
	}
	
	
	public String getInfoTag()
	{
		StringBuffer st=new StringBuffer();
		int count=0;
		StringBuffer temp=new StringBuffer();
		for(int i=0,size=infoMetas.size();i<size;i++)
		{
			InfoMeta im=(InfoMeta)infoMetas.get(i);
			validate.append(im.validate());
			if(count==0)
			{
				temp.append(im.getTag());
				count+=im.getWidth();
			}
			else
			{
				if((count+im.getWidth())<=tdMax)
				{
					temp.append(im.getTag());
					count+=im.getWidth();
				}
				else
				{
					/**
					 * 没有很好的扩展，没有考虑2列以上情况
					 */
					st.append(MessageFormat.format(tr.toString(),new Object[]{im.getTag()}));
				}
			}
			if(count==tdMax)
			{
				st.append(MessageFormat.format(tr.toString(),new Object[]{temp.toString()}));
				count=0;
				temp=new StringBuffer();
			}
		}
		if(count!=0)
		{
			temp.append(SearchMeta.getNullTag());
			st.append(MessageFormat.format(tr.toString(),new Object[]{temp.toString()}));
			//st.append(MessageFormat.format(tr.toString(),new Object[]{temp.}));
		}
		return st.toString();
	}
	public void createFiles(String realPath)
	{
		
			String path=realPath+this.getAbsPath()+this.getFileName();
			BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("info.jsp"));
			PrintWriter pw=FileUtil.getFileWriter(path);
			String info=this.getInfoTag();
			String js=this.getJs();
			String temp;
			try {
				while((temp=br.readLine())!=null)
				{
					
					pw.println(MessageFormat.format(temp,new Object[]{info,js}));
					
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
	private String getJs()
	{
		StringBuffer js=new StringBuffer();
		BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("info.js-"));
		String temp;
		try {
			while((temp=br.readLine())!=null)
			{
				//temp="'"+temp;
				//System.out.println(temp);
				js.append(MessageFormat.format(temp,new Object[]{submit,create,validate.toString()}));
				js.append("\r\n");
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return js.toString();
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getCreate() {
		return create;
	}
	public void setCreate(String create) {
		this.create = create;
	}
}
