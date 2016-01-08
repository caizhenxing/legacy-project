/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 27, 200711:40:01 AM
 * �ļ�����SearchJspfData.java
 * �����ߣ�zhaoyf
 * 
 */
package com.zyf.tools.auto.create.search;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyf
 *
 */
public class SearchJspfData {

	private static String enter="\r\n";
	private static String tab="\t";
	private static StringBuffer tr=new StringBuffer();
	/**
	 * 
	 * {0}->�����td
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
	private List searchMetas=new ArrayList();
	private int tdMax=2;
	public int getTdMax() {
		return tdMax;
	}
	public void setTdMax(int tdMax) {
		this.tdMax = tdMax;
	}
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
	public List getSearchMetas() {
		return searchMetas;
	}
	public void setSearchMetas(List searchMetas) {
		this.searchMetas = searchMetas;
	}
	public String getSearchTag()
	{
		StringBuffer st=new StringBuffer();
		int count=0;
		StringBuffer temp=new StringBuffer();
		for(int i=0,size=searchMetas.size();i<size;i++)
		{
			SearchMeta sm=(SearchMeta)searchMetas.get(i);
			String tag=sm.getTag();
			if(count==0)
			{
				temp.append(tag);
				count+=sm.getWidth();
			}
			else
			{
				if((count+sm.getWidth())<=tdMax)
				{
					temp.append(tag);
					count+=sm.getWidth();
				}
				else
				{
					/**
					 * û�кܺõ���չ��û�п���2���������
					 */
					st.append(MessageFormat.format(tr.toString(),new Object[]{tag}));
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
	public void addSearchMeta(SearchMeta sm)
	{
		this.searchMetas.add(sm);
	}
}
