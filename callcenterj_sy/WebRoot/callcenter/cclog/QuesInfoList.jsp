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
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
    <tr>
      <td class="listTitleStyle">咨询内容</td>
      <td class="listTitleStyle">问题状态</td>
      <td class="listTitleStyle">是否回访</td>
      <td class="listTitleStyle">操作</td>
    </tr>
    <logic:iterate id="c" name="queslist" indexId="i">
    <%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
	%>
    <tr>
      <td ><bean:write name="c" property="question" filter="true"/></td>
      <td ><bean:write name="c" property="quinfo" filter="true"/></td>
      <td ><bean:write name="c" property="ifNeedRevert" filter="true"/></td>
      <td width="15%" >
            <img alt="详细" alt="详细" src="../../style/<%=styleLocation %>/images/detail.gif"
            	onclick="popUp('cclogquestionWindows','cclog.do?method=toQuestionLoad&type=detail&id=<bean:write name='c' property='questionId'/>',850,650)" width="16" height="16" border="0"/>
	  </td>
    </tr>
    </logic:iterate>
  </table>
  </body>
</html:html>
