/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-8-23����09:46:15
 * �ļ�����TableTag.java
 * �����ߣ�zhaoyifei
 * 
 */
package com.zyf.common.crud.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.web.util.ExpressionEvaluationUtils;

import com.zyf.common.crud.service.ViewBean;
import com.zyf.common.crud.service.ViewBean.ViewAssi;
import com.zyf.persistent.hibernate3.entity.CodeWrapper;
import com.zyf.security.SecurityContextInfo;
import com.zyf.struts.BaseFormPlus;
import com.zyf.tools.Tools;

/**
 * @author zhaoyifei
 *
 */
public class TableTag extends TagSupport {
	
	/**
	 * �Զ���Ӧ����ѡ������
	 */
	
	boolean isSelect=false;
	private static String script1="<script language=\"javascript\">\r\n"+
	"var listObject = new Object();\r\n"+
	"CurrentPage.onLoadSelect = function(){\r\n"+
	"listObject = new ListUtil.Listing('listObject', 'listtable');\r\n"+
	"listObject.init();\r\n"+
	"top.definedWin.selectListing = function(inum) {\r\n";
	
		//<c:if test="${theForm.popSelectType == 'radio'}">listObject.selectWindow(1);</c:if>
		//<c:if test="${theForm.popSelectType == 'checkbox'}">listObject.selectWindow(2);</c:if>
	private static String script2="}\r\n"+
	"top.definedWin.closeListing = function(inum) {\r\n"+
	"listObject.selectWindow();\r\n"+
	"}\r\n"+
"}\r\n"+
"CurrentPage.onLoadSelect();\r\n"+
	"</script>"; 
	private static String selectType1="<input type='hidden' name='popSelectType' value='";
	private static String selectType2="'/>"; 
	/**************************************/
	
	
	private String script="<script type=\"text/javascript\">"+
									" if (CurrentPage == null) { var CurrentPage = {}; } "+
								"CurrentPage.settable =function(pageid,asc){"+
									"var url = ContextInfo.fetchServerAddr()+'/qware/curd/curdAction.do?step=setTable&pageId=';"+
									"url+=pageid;"+
									"if(asc!=null)"+
									"url+='&asc=asc';"+
									"definedWin.openListingUrl(\"setTable\",url);"+
									"}"+
						"</script>";
	private String linkScript="<div onclick=\"javascript:definedWin.openUrl(''{0}'', ''{1}'')\" class=\"font_link\">{2}</div>";
	private String name=null;
	private String del=null;
	private String box=null;
	private String publicResourceServer=null;
	private String formName;
	
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	//private String qwareServer=null;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		publicResourceServer=this.pageContext.getServletContext().getInitParameter("publicResourceServer");
		//qwareServer=this.pageContext.getServletContext().getInitParameter("qwareServerName");
		JspWriter out = pageContext.getOut();
		String selcetFuction=null;
		String popSelectType=null;
		if(StringUtils.isNotBlank(formName))
		{
			BaseFormPlus form = (BaseFormPlus)ExpressionEvaluationUtils.evaluate("formName",formName, pageContext);
			popSelectType=form.getPopSelectType();
			if("only".equals(form.getPopSelectType()))
				this.box="radio";
			if("multi".equals(form.getPopSelectType()))
				this.box="check";
			if("only".equals(form.getPopSelectType()))
				selcetFuction="listObject.selectWindow(1);";
			if("multi".equals(form.getPopSelectType()))
				selcetFuction="listObject.selectWindow(2);";
			if(!StringUtils.isBlank(form.getPopSelectType()))
			{
			isSelect=true;
			this.del="false";
			}
		}
		ViewBean vb=this.getViewBean();
		StringBuffer idgetMethod=new StringBuffer();
		idgetMethod.append("get");
		String idName=vb.getIdName();
		String idfirst=idName.substring(0,1);
		idgetMethod.append(idfirst.toUpperCase());
		idgetMethod.append(idName.substring(1));
		//-----Start�޸���2008.01.10 by het--------//
		//publicResourceServer=getPublicResourceServer();
		//-----End----------------------------//
		try {
				List rl=vb.getViewRow();
				List resultl=vb.getViewList();
				Iterator rli=rl.iterator();
				out.println("<thead name=\"tabtitle\">");
				out.println("<tr>");
				out.print("<td width=\"25px\" field= id nowrap=\"nowrap\">");
				if(!"radio".equals(this.box))
				out.print("<input  width='15px' id='detailIdsForPrintAll' type='checkbox' onclick=\"FormUtils.checkAll(this,document.getElementsByName('oid'))\" title=\"�Ƿ�ȫѡ\"/>");
				else
					out.print("&nbsp;");
				out.print("</td>");
				while(rli.hasNext())
				{
					ViewAssi va=(ViewAssi)rli.next();
					Class cp=null;
					for(int i=0;i<vb.getViewList().size();i++)
					{
						try {
							cp=PropertyUtils.getPropertyType(vb.getViewList().get(0), va.getRow());
							break;
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							continue;
						}
					}
					
					if((null!=cp)&&isSelect&&(vb.getViewList().size()!=0)&&(CodeWrapper.class.isAssignableFrom(cp)))
					{
						out.print("<td field=" + va.getRow() +
						"Code  style='display:none'/>");
					}
					String dataType="";
					if((null!=cp)&&(Number.class.isAssignableFrom(cp)))
					{
						dataType=" type=\"Number\" ";
					}
					out.print("<td field=" + StringUtils.replaceChars(va.getRow(), '.','_') +dataType+
						" nowrap=\"nowrap\" >");
					out.print(va.getRowName());
					out.print("</td>");
				
				}
				if(!"false".equals(this.del))
				out.println("<td  width=\"40px\" nowrap=\"nowrap\" type=\"operate\">����</td>");
				out.println("</tr>");
				out.println("</thead>");
				
			
		
		Iterator resultli=resultl.iterator();
		
		out.println("<tbody id='tablist'>");
		
		while(resultli.hasNext())
		{
			Object object=resultli.next();
			String id=(String)MethodUtils.invokeExactMethod(object,idgetMethod.toString(),null);
			out.print("<tr class=\"\" />" );
			out.print("<td class=\"list_first\" nowrap=\"nowrap\" align='center'>" );
			if(!"radio".equals(this.box))
			{
			out.print("<input type=\"checkbox\" name=\"oid\" onclick=\"FormUtils.check($('detailIdsForPrintAll'),document.getElementsByName('oid'))\" value=\""+id+"\"");
			//out.print(id);
			out.print("/>" );
			}
			else
			{
				out.print("<input type=\"radio\" name=\"oid\"  value=\""+id+"\"/>");
			}
//			out.print("<input type=\"hidden\" name=\"oid\" value=\"");
//			out.print(id);
//			out.print("\" />" );
			out.print("</td>");
			Iterator i=rl.iterator();
			while(i.hasNext())
			{
				ViewAssi va=(ViewAssi)i.next();
				String r=va.getRow();
				String link=va.getLink();
				
				Object value;
				try {
					value = PropertyUtils.getNestedProperty(object, r);
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					out.print("<td nowrap=\"nowrap\" align='left'> &nbsp;" );
					out.print("</td>");
					continue;
				}
				if(value==null)
				{
					out.print("<td nowrap=\"nowrap\" align='left'> &nbsp;" );
					out.print("</td>");
					continue;
				}
				if(Date.class.isAssignableFrom(value.getClass()))
				{
					out.print("<td nowrap=\"nowrap\" align='center'>" );
					String style="yyyy-MM-dd";
					if(StringUtils.isNotBlank(va.getStyle()))
						style=va.getStyle();
					try {
						out.print(this.changeScript(va.getRowName(), link,Tools.getTheTime((Date)value,style) ,object));
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						out.print("&nbsp;");
					}
				}
				if(Number.class.isAssignableFrom(value.getClass()))
				{
					out.print("<td nowrap=\"nowrap\" align='right'>" );
					String style="#,###.##";
					if(StringUtils.isNotBlank(va.getStyle()))
						style=va.getStyle();
					try{
					if(isSelect)
						out.print(this.changeScript(va.getRowName(), link,value.toString(),object));
					else
						out.print(this.changeScript(va.getRowName(), link,new DecimalFormat(style).format(value),object));
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					out.print("&nbsp;");
				}
				}
				if(value.getClass().equals(String.class))
				{
					out.print("<td nowrap=\"nowrap\" align='left'>" );
					String style="14";
					if(StringUtils.isNotBlank(va.getStyle()))
						style=va.getStyle();
					try{
					out.print(this.changeScript(va.getRowName(), link,Tools.abbreviate(Tools.changeHtml(value.toString()),Integer.parseInt(style)),object));
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					out.print("&nbsp;");
				}
				}
				if(CodeWrapper.class.isAssignableFrom(value.getClass()))
				{
					if(isSelect)
					{
					out.print("<td style='display:none'>" );
					out.print(((CodeWrapper)value).getCode());
					out.print("</td>");
					}
					out.print("<td nowrap=\"nowrap\" align='left'>" );
					out.print(this.changeScript(va.getRowName(), link,Tools.changeHtml(((CodeWrapper)value).getName()),object));
				}
				
//				}
//				else
//				{
//					out.print("<td nowrap=\"nowrap\" align='left'>" );
//					out.print(dict.get(r));
//				}
				//
				if(link != null && value.getClass().equals(String.class) && !StringUtils.isBlank(value.toString())){
					out.print("</td>");
				}
				else{
					out.print("&nbsp;</td>");
				}
			
			}
			if(!"false".equals(this.del))
			{
				out.print("<td nowrap='nowrap' align=\"center\">");
				//��γ������Ȩ�ޣ�����ʹ�Ļ�������Ȩ���й�
				try{
					Integer integer=(Integer)(SecurityContextInfo.getRwCtrlTypeMap().get(id));
					if(integer.intValue()!=1)
						out.print("<input type=\"button\" class=\"list_delete\" onclick=\"CurrentPage.remove('"+id+"')\" title=\"���ɾ��\"/>");
					else
						out.print("&nbsp;");
				}catch(Exception e){
					//����ȡ����Ȩ�޵�ʱ����ΪĿǰϵͳ�д��ڲ�����Ȩ�޹��˵Ŀ����У���ȡ�ſ�����
					out.print("<input type=\"button\" class=\"list_delete\" onclick=\"CurrentPage.remove('"+id+"')\" title=\"���ɾ��\"/>");
				}
				out.print("</td>");
			}
			out.println("</tr>");
		}
		out.print("</tbody>");	
			out.print(script);
			if(isSelect)
			{
				out.print(script1);
				out.print(selcetFuction);
				out.print(script2);
				out.print(selectType1);
				out.print(popSelectType);
				out.print(selectType2);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new JspException();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	private ViewBean getViewBean()
	{
		ViewBean vb=(ViewBean)pageContext.getAttribute(name,
                PageContext.PAGE_SCOPE);
		if(vb==null)
		{
			vb=(ViewBean)pageContext.getAttribute(name,
	                PageContext.REQUEST_SCOPE);
		}
		return vb;
	}
	private String getPublicResourceServer()
	{
		String publicResourceServer=(String) pageContext.getAttribute("initParam['publicResourceServer']",PageContext.PAGE_SCOPE);
		if(publicResourceServer==null||publicResourceServer.equals(""))
			publicResourceServer=(String) pageContext.getAttribute("initParam['publicResourceServer']",PageContext.REQUEST_SCOPE);
		if(publicResourceServer==null||publicResourceServer.equals(""))
			publicResourceServer=(String) pageContext.getAttribute("initParam['publicResourceServer']",PageContext.SESSION_SCOPE);
		
		return publicResourceServer;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getBox() {
		return box;
	}

	public void setBox(String box) {
		this.box = box;
	}
	private String changeScript(String popName,String link,String view,Object bean)
	{
		if(isSelect)
			return view;
		if(StringUtils.isBlank(link))
			return view;
		if(StringUtils.isBlank(view))
			return "&nbsp;";
		return MessageFormat.format(linkScript,new Object[]{popName,changeLink(link,bean),view});
	}
	private String changeLink(String link,Object bean)
	{
		try {
			Velocity.init();
			VelocityContext context=new VelocityContext();
			context.put("bean",bean);
			StringWriter out=new StringWriter();
			boolean a=Velocity.evaluate(context, out, "",publicResourceServer+link);
			String s=out.toString();
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
