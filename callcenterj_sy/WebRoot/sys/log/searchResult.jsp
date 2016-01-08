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
<%--	<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
  
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr class="tdbgpiclist">
    <td><bean:message bundle='sys' key='sys.log.toSearch.username'/></td>
    <td><bean:message bundle='sys' key='sys.oper'/></td>
    <td><bean:message bundle='sys' key='sys.log.searchResult.date'/></td>
	<td><bean:message bundle='sys' key='sys.log.toSearch.module'/></td>
	<td><bean:message bundle='sys' key='sys.property.ip'/></td>
  </tr>
  
  <logic:iterate name="list" id="l" indexId="i">
 <%
  String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  %>
  <tr>
    <td  class=<%=style%>><bean:write name="l" property="sysUser"/></td>
    <td  class=<%=style%>><bean:write name="l" property="actorType"/></td>
    <td  class=<%=style%>><bean:write name="l" property="dt"/></td>
	<td  class=<%=style%>><bean:write name="l" property="sysModule"/></td>
	<td  class=<%=style%>><bean:write name="l" property="ip"/></td>
  </tr>
  </logic:iterate>
  <tr>
    <td colspan="5" ><page:page style="second" name="logTurning"/></td>
    </tr>
</table>
  
  </body>
</html:html>
