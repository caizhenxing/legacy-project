<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body class="listBody">
  <html:form action="/callcenter/cclog.do" method="post"></html:form>
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
    <tr>
      <td class="listTitleStyle">IVR模块名称</td>
      <td class="listTitleStyle">进入模块时间</td>
      <td class="listTitleStyle">离开模块时间</td>
    </tr>
    <logic:iterate id="c" name="ivrlist" indexId="i">
    <%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
	%>
    <tr>
      <td ><bean:write name="c" property="moduleName" filter="true"/></td>
      <td ><bean:write name="c" property="enterTime" format="yyyy-MM-dd HH:mm:ss" filter="true"/></td>
      <td ><bean:write name="c" property="leaveTime" format="yyyy-MM-dd HH:mm:ss" filter="true"/></td>
    </tr>
    </logic:iterate>
  </table>
  </body>
</html:html>
