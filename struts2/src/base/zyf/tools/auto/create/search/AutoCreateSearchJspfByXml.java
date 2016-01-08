/**
 * 
 * 制作时间：Oct 29, 20079:33:36 AM
 * 文件名：AutoCreateSearchJspfByXml.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.tools.auto.create.search;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;

import org.apache.commons.betwixt.io.BeanReader;
import org.xml.sax.SAXException;



/**
 * @author zhaoyf
 *
 */
public class AutoCreateSearchJspfByXml extends AutoCreateSearchJspf{

	/**
	 * 功能描述
	 * @param args
	 * Oct 29, 2007 9:33:36 AM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//AutoCreateSearchJspfByXml acs=new AutoCreateSearchJspfByXml();
		//acs.readMetaData();
		//acs.setRealPath("");
		
		
//		SearchJspfData sjd=new SearchJspfData();
//		sjd.setAbsPath("/aa/bb/cc/dd/");
//		sjd.setFileName("aaa.jspf");
//		sjd.setTdMax(12);
//		
//		SearchMeta sm=new SearchMeta();
//		sm.setSearchType(sm.SEARCH_TYPE_DEPT);
//		sm.setCode("AA.BB.CC");
//		sm.setName("AA.BB.DD");
//		sm.setDisPlayName("测试部门标签");
//		sm.setValueDefault("${theForm}");
//		sjd.getSearchMetas().add(sm);
//		sm=new SearchMeta();
//		
//		sm.setSearchType(sm.SEARCH_TYPE_SELECT);
//		sm.setName("AA.BB.CC");
//		sm.setSelectSubSysCode("HR");
//		sm.setDisPlayName("测试下拉框标签");
//		sm.setSelectModuleCode("MINZ");
//		sjd.getSearchMetas().add(sm);
//		sm=new SearchMeta();
//		
//		sm.setSearchType(sm.SEARCH_TYPE_TEXT);
//		sm.setName("AA.BB.CC");
//		sm.setOper(">=");
//		sm.setDisPlayName("测试文本标签");
//		sm.setValueDefault("${theForm}");
//		sm.setInputClass("colspan='3'");
//		sm.setStyle("style=\"ddddd\"");
//		sjd.getSearchMetas().add(sm);
//		sm=new SearchMeta();
//		
//		sm.setSearchType(sm.SEARCH_TYPE_TIME);
//		sm.setName("AA.BB.Cc");
//		sm.setDisPlayName("测试时间标签");
//		sm.setValueDefault("${theForm}");
//		sjd.getSearchMetas().add(sm);
//		sm=new SearchMeta();
//		//acs.createJspf();
//		Writer ow=null;
//		try {
//			ow = new FileWriter("d:\\betwixt.xml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		BeanWriter bw=new BeanWriter(ow);
//		bw.setEndOfLine("\r\n");
//		bw.setIndent("\t");
//		bw.enablePrettyPrint();
//		bw.setWriteEmptyElements(true);
//		try {
//			bw.write(sjd);
//			ow.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IntrospectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		BeanReader br=new BeanReader();
//		br.getXMLIntrospector().setWrapCollectionsInElement(false);
//		try {
//			br.registerBeanClass(AutoCreateSearchJspfByXml.class);
//			
//		} catch (IntrospectionException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		//br.getBindingConfiguration().setMapIDs(false);
//		try {
//			AutoCreateSearchJspfByXml auto=(AutoCreateSearchJspfByXml)br.parse(new File("d:\\AutoCreateSearchJspfByXml.xml"));
//			auto.files.size();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	protected void findFiles(String name)
	{
		BeanReader br=new BeanReader();
		br.getXMLIntrospector().setWrapCollectionsInElement(false);
		try {
			br.registerBeanClass(AutoCreateSearchJspfByXml.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			AutoCreateSearchJspfByXml auto=(AutoCreateSearchJspfByXml)br.parse(new File(name));
			this.files=auto.files;
			this.realPath=auto.realPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void readMetaData() {
		// TODO Auto-generated method stub
		BeanReader br=new BeanReader();
		br.getXMLIntrospector().setWrapCollectionsInElement(false);
		try {
			br.registerBeanClass(SearchJspfData.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			for(int i=0,size=files.size();i<size;i++)
			{
				SearchJspfData sjda=(SearchJspfData)br.parse(new File(files.get(i).toString()));
				this.searchJspfDatas.add(sjda);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
