<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>
<%@ include file="../../style.jsp"%>
<%
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<html:html locale="true">

<link href="./../../images/css/styleA.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #f5f5f5;
}
.text-selected{
	background: #ccffff;
}
body{
	padding:0px;
	margin:0px;
}
-->
</style>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
	type="text/css" />
<script language="javascript">
	function nockClick(id)
	{
		//alert('当前节点id'+id);
	}

</script>
<body class="emptyBodyStyle">
  <table class="td" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td>
	</td>
</tr>
<tr class="text"   valign="top">
	<td align="left" style="padding-top:10px;height:100%;width:100%;" id="treeContainer">
  <newtree:tree tree="parameterTreeSession" 
  			action="./../parameter/paramTree.do?method=loadParamTree&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="style/xia/images/tree"
  />
  </td>
 </tr>
  </table>

</body>

</html:html>

 