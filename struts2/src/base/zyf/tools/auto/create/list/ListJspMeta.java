/**
 * 
 * ����ʱ�䣺Oct 31, 200711:18:08 AM
 * �ļ�����ListJspMeta.java
 * �����ߣ�zhaoyf
 * 
 */
package base.zyf.tools.auto.create.list;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import base.zyf.tools.FileUtil;

/**
 * @author zhaoyf
 *
 */
public class ListJspMeta {
	
	
	private String absPath;
	
	private String fileName;
	

	/**
	 * ��ѯ����{0}
	 */
	private String queryLink="";
	/**
	 * ɾ������{1}
	 */
	private String removeLink="";
	/**
	 * ȫ��ɾ������{2}
	 */
	private String removeAllLink="";
	/**
	 * ��ѯҳ�����õ�ַ{3}
	 */
	private String searchJspfInclude="";
	/**
	 * ��ҳ����{4}
	 */
	private String pageLink="";
	/**
	 * �б�ҳ������
	 */
	private String listName="";

	public String getQueryLink() {
		return queryLink;
	}

	public void setQueryLink(String queryLink) {
		this.queryLink = queryLink;
	}

	public String getRemoveLink() {
		return removeLink;
	}

	public void setRemoveLink(String removeLink) {
		this.removeLink = removeLink;
	}

	public String getRemoveAllLink() {
		return removeAllLink;
	}

	public void setRemoveAllLink(String removeAllLink) {
		this.removeAllLink = removeAllLink;
	}

	public String getSearchJspfInclude() {
		return searchJspfInclude;
	}

	public void setSearchJspfInclude(String searchJspfInclude) {
		this.searchJspfInclude = searchJspfInclude;
	}

	public String getPageLink() {
		return pageLink;
	}

	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}
	private String getJs()
	{
		StringBuffer js=new StringBuffer();
		BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("list.js-"));
		String temp;
		try {
			while((temp=br.readLine())!=null)
			{
				//temp="'"+temp;
				//System.out.println(temp);
				js.append(MessageFormat.format(temp,new Object[]{queryLink,removeLink,removeAllLink,searchJspfInclude,pageLink,"'"}));
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
	public void createFiles(String realPath)
	{
		
			String path=realPath+this.getAbsPath()+this.getFileName();
			BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("list.jsp"));
			PrintWriter pw=FileUtil.getFileWriter(path);
			String js=this.getJs();
			String temp;
			try {
				while((temp=br.readLine())!=null)
				{
					
					pw.println(MessageFormat.format(temp,new Object[]{queryLink,removeLink,removeAllLink,searchJspfInclude,pageLink,"'",js,listName}));
					
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

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}
}
