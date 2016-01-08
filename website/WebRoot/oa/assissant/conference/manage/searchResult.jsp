
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
    <td><bean:message key='hl.bo.oa.conference.info.conferenceDate'/></td>
    <td><bean:message key='hl.bo.oa.conference.info.synodOwner'/></td>
    <td><bean:message key='hl.bo.oa.conference.toSearch.status'/></td>
    <td><bean:message key='hl.bo.oa.asset.oper'/></td>
  </tr>
  <logic:iterate name="list" id="l" indexId="i">
			<%
  String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  %>
  <tr>
    <td class=<%=style%>><html:link page="/oa/assissant/conference/conferOper.do?method=toLoad&type=l" paramId="did" paramName="l" paramProperty="id" target="_blank"><bean:write name="l" property="synodTopic"/></html:link></td>
    <td class=<%=style%>><bean:write name="l" property="synodDate"/>&nbsp;<bean:write name="l" property="synodHour"/></td>
    <td class=<%=style%>><bean:write name="l" property="synodOwner"/></td>
    <td class=<%=style%>>
    	<logic:equal name="l" property="examResult" value="to_db"><bean:message key='hl.bo.oa.conference.toSearch.status.exam'/></logic:equal>
    	<logic:equal name="l" property="examResult" value="to_update"><bean:message key='hl.bo.oa.conference.toSearch.status.examun'/></logic:equal>
    	<logic:equal name="l" property="examResult" value="-1"><bean:message key='hl.bo.oa.conference.toSearch.status.unexam'/></logic:equal>
    </td>
    <td align="center" class=<%=style%>>
    <img alt="<bean:message key='hl.bo.oa.asset.see'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('conferOper.do?method=toLoad&type=l&did=<bean:write name='l' property='id'/>','windows','')" width="16" height="16" target="_blank" border="0"/>    
    </td>
  </tr>
  </logic:iterate>
  <tr>
    <td colspan="6">
    <page:page name="conferTurning" style="first"/>
    </td>
    </tr>
</table>

  </body>
</html:html>
