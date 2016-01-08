/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 31, 200711:27:14 AM
 * 文件名：CreateXml.java
 * 制作者：zhaoyf
 * 
 */
package test.tools.auto.create.xml;

import java.beans.IntrospectionException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

import com.zyf.tools.auto.create.entry.EntryJspMeta;
import com.zyf.tools.auto.create.info.InfoJspData;
import com.zyf.tools.auto.create.info.InfoMeta;
import com.zyf.tools.auto.create.list.ListJspMeta;

/**
 * @author zhaoyf
 *
 */
public class CreateXml {

	/**
	 * 功能描述
	 * @param args
	 * Oct 31, 2007 11:27:14 AM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createInfo();
	}
	
	private static void createInfo()
	{
		InfoJspData ijd=new InfoJspData();
		ijd.setAbsPath("/aa/bb/cc/dd/");
		ijd.setFileName("rateInfo.jsp");
		ijd.setSubmit("/hr/ratemanage.do?step=save");
		ijd.setCreate("/hr/ratemanage.do?step=info");
		ijd.setTdMax(2);
		InfoMeta im=new InfoMeta();
		im.setCode("bean.tblHrDept.id");
		im.setDisplayName("部门/班组");
		im.setIsMust("true");
		im.setName("bean.tblHrDept.deptName");
		im.setPattern("yyyy-MM-dd");
		im.setSelectModuleCode("GWEI");
		im.setSelectSubSysCode("HR");
		im.setStyle("aaa");
		im.setValueDefault("theForm");
		im.setViewClass("class=\"attribute\"");
		im.setWidth(1);
		im.setValid("Require|Double");
		im.setValidMsg("必须填写部门");
		im.setInfoType(im.INFO_TYPE_DEPT);
		ijd.addInfoMeta(im);
		im.setInfoType(im.INFO_TYPE_SELECT);
		ijd.addInfoMeta(im);
		im.setInfoType(im.INFO_TYPE_TEXT);
		ijd.addInfoMeta(im);
		im.setInfoType(im.INFO_TYPE_TIME);
		ijd.addInfoMeta(im);
		
		Writer ow=null;
		try {
			ow = new FileWriter("d:\\zhaoyifei\\info\\infoMeta.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeanWriter bw=new BeanWriter(ow);
		bw.setEndOfLine("\r\n");
		bw.setIndent("\t");
		bw.enablePrettyPrint();
		bw.setWriteEmptyElements(true);
		try {
			bw.write(ijd);
			ow.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void createList()
	{
		ListJspMeta lsm=new ListJspMeta();
		lsm.setPageLink("/hr/ratemanage.do");
		lsm.setQueryLink("/hr/ratemanage.do?step=${theForm.step}");
		lsm.setRemoveAllLink("/hr/ratemanage.do?step=deleteAll&oids=");
		lsm.setRemoveLink("/hr/ratemanage.do?step=delete&oid=");
		lsm.setSearchJspfInclude("/jsp/hr/rate/rateSearch.jspf");
		Writer ow=null;
		try {
			ow = new FileWriter("d:\\zhaoyifei\\ListJspMeta.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeanWriter bw=new BeanWriter(ow);
		bw.setEndOfLine("\r\n");
		bw.setIndent("\t");
		bw.enablePrettyPrint();
		bw.setWriteEmptyElements(true);
		try {
			bw.write(lsm);
			ow.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void createEntry()
	{
		EntryJspMeta ejm=new EntryJspMeta();
		ejm.setAbsPath("");
		ejm.setFileName("");
		ejm.setName("岗位费率维护");
		ejm.addArrs("0","/hr/ratemanage.do?step=list&paginater.pageSize=0");
		ejm.addArrs("1","/hr/ratemanage.do?step=info");
		Writer ow=null;
		try {
			ow = new FileWriter("d:\\zhaoyifei\\entry\\entryJsp.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeanWriter bw=new BeanWriter(ow);
		bw.setEndOfLine("\r\n");
		bw.setIndent("\t");
		bw.enablePrettyPrint();
		bw.setWriteEmptyElements(true);
		try {
			bw.write(ejm);
			ow.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void createSearch()
	{
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
}
