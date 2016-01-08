
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
    
    <title>searchResult.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
  
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr class="tdbgpiclist">
    <td><bean:message key='hl.bo.oa.conference.info.synodTopic'/></td>
    <td><bean:message key='hl.bo.oa.conference.info.conferenceAdd'/></td>
    <td><bean:message key='hl.bo.oa.conference.info.time'/></td>
    <td><bean:message key='hl.bo.oa.conference.info.synodOwner'/></td>
    <td><bean:message bundle='sys' key='sys.workflow.checkstate'/></td>
    <td><bean:message key='hl.bo.oa.asset.oper'/></td>
  </tr>
  <logic:iterate name="list" id="l" indexId="i">
		<%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  %>
  <tr>
    <td height="24" class=<%=style%>><html:link page="/oa/assissant/conference/conferOper.do?method=toLoad&type=l" paramId="did" paramName="l" paramProperty="id" target="_blank"><bean:write name="l" property="synodTopic"/></html:link></td>
    <td class=<%=style%>><bean:write name="l" property="synodAddr"/></td>
    <td class=<%=style%>><bean:write name="l" property="synodDate"/>&nbsp;<bean:write name="l" property="synodHour"/></td>
    <td class=<%=style%>><bean:write name="l" property="synodOwner"/></td>
    <td class=<%=style%>>
    	<logic:equal name="l" property="examResult" value="1"><bean:message bundle='sys' key='sys.workflow.checkpass'/></logic:equal>
    	<logic:equal name="l" property="examResult" value="0"><bean:message bundle='sys' key='sys.workflow.checkfalse'/></logic:equal>
    	<logic:equal name="l" property="examResult" value="-1"><bean:message bundle='sys' key='sys.workflow.nocheck'/></logic:equal>
    </td>
    <td class=<%=style%>>
    <img alt="<bean:message bundle='sys' key='sys.info'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('conferOper.do?method=toLoad&type=l&did=<bean:write name='l' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>
    <logic:present name="oper">
    <logic:equal name="oper" value="exam">
    <img alt="<bean:message bundle='sys' key='sys.workflow.check'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('conferOper.do?method=toLoad&type=exam&did=<bean:write name='l' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>   
    </logic:equal>
    </logic:present>
    <logic:notPresent name="oper">
    <img alt="<bean:message key='agrofront.common.update'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('conferOper.do?method=toLoad&type=u&did=<bean:write name='l' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/> 
    <img alt="<bean:message key='agrofront.common.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('conferOper.do?method=toLoad&type=d&did=<bean:write name='l' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>   
    </logic:notPresent>
    </td>
  </tr>
  </logic:iterate>
  <tr>
    <td colspan="6">
    <page:page name="conferExamTurning" style="first"/>
    </td>
    </tr>
</table>
 
  </body>
</html:html>
