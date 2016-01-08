
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
    
    <title>toSearch.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script>
	function s()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=searchExam&oper=exam";
    	document.forms[0].submit();
	}
	function a()
		{
			window.open("/ETOA/oa/assissant/conference/conferOper.do?method=toLoad&type=i","a");
		}
</script>
  </head>
  
  <body>
  <br>
  <html:form action="/oa/assissant/conference/conferOper" target="mainFrame">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message key='hl.bo.oa.conference'/></td>
  </tr>
<tr>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.conference.info.synodTopic'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="synodTopic"></html:text></td>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.conference.info.synodOwner'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="synodOwner"></html:text></td>
  </tr>
  <tr>
    <td  colspan="4" class="tdbgcolorquerybuttom">
    <input name="Submit" type="button" class="bottom" value="<bean:message key='agrofront.common.search'/>" onclick="s()"/>
	
	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
