<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>execLayerOrder.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
function toSubmit()
{
	document.getElementById("execOrderBtn").disabled=true;
	document.forms[0].submit();
}
</script>
  </head>
  
  <body>
  <logic:present name="operSuccess" scope="request">
<script language="javascript">
alert("树结点排序成功!");
</script>
</logic:present>
    <html:form action="/sys/layerOrder/layerOrder.do?method=operParamTree" >
<table width="80%">
<tr>
<td align="right">
<input type="button" value="排序"   onclick="toSubmit()" id="execOrderBtn"/>
</td>
</tr>
</table>
</html:form> 
  </body>
</html:html>
