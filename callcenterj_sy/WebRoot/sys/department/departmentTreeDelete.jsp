<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>paramDel.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
      <form name="TreeInfo" method="post" action="./sys/department/deptTree.do?method=operParamTree" target="contents">
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload">类型管理</td>
  </tr>
  <input type="hidden" name="operType" value="delete">
  <input type="hidden" name="id" value="<%=request.getParameter("id") %>">
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <input type="submit" value="删除" onclick="window.close()">
</td>
  </tr>
</table>
</form>
  </body>
</html:html>

