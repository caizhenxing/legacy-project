<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="hl.bo.news.articleload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
    	function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.title,"���±���")) return false;
        	if (!checkNotNull(addstaffer.author,"����")) return false;
        	if (!checkNotNull(addstaffer.copyfrom,"ת����")) return false;
        	if (!checkNotNull(addstaffer.editor,"����¼��")) return false;
        	if (!checkNotNull(addstaffer.key,"�ؼ���")) return false;
           return true;
        }
    	//���
    	function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    	    document.forms[0].elements["content"].value = window.frames["editor"].HtmlEdit.document.body.innerHTML;
    		document.forms[0].action = "../opernews.do?method=operArticle&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//�޸�
    	function update(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].elements["content"].value = window.frames["editor"].HtmlEdit.document.body.innerHTML;
    		document.forms[0].action = "../opernews.do?method=operArticle&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//ɾ��
    	function del(){
    		document.forms[0].elements["content"].value = window.frames["editor"].HtmlEdit.document.body.innerHTML;
    		document.forms[0].action = "../opernews.do?method=operArticle&type=delete";
    		document.forms[0].submit();
    	}
    	
    	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		//����ҳ��
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
		//����form	
		function loadForm(){
  			window.frames["editor"].HtmlEdit.document.body.innerHTML=document.forms[0].elements["content"].value;
  			return true;
		}
    </script>
  </head>
  
  <body onunload="toback()" onload="loadForm()">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/news/opernews.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message key="agrofront.news.article.articleload.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.classname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:select property="classid">		
        	<html:option value="0" >��ѡ��2</html:option>
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.articletitle"/></td>
		    <td class="tdbgcolorloadleft"><html:text property="title" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.author"/></td>
		    <td class="tdbgcolorloadleft"><html:text property="author" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.copyfrom"/></td>
		    <td  class="tdbgcolorloadleft"><html:text property="copyfrom" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.editor"/></td>
		    <td  class="tdbgcolorloadleft"><html:text property="editor" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.key"/></td>
		    <td  class="tdbgcolorloadleft"><html:text property="key" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.hits"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="hits" styleClass="input" size="5" value="0"/>
		    <font color="#FF0000">���ɵ����������µ������Ĭ��Ϊ0����</font>
		    </td>
		  </tr>
		  
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.passed"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:select property="passed">
		    	<html:option value="1">��</html:option>
		    	<html:option value="0">��</html:option>
		    </html:select>
		    <font color="#FF0000">�����ѡ�ǵĻ���ֱ�ӷ�����������˺���ܷ���,Ĭ��Ϊͨ����ˡ���</font>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message key="agrofront.news.article.articleload.content"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:textarea property="content" style="width:1px;height:1px;" onfocus="editor.HtmlEdit.focus();"/>
		    <iframe ID="editor" name="editor" src="../editor/editor.jsp" frameborder="0" scrolling=no width="100%" height="405"></iframe>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="articleid"/>
    </html:form>
  </body>
</html:html>
