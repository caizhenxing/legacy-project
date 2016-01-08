<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>
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
-->
</style>
<script language="javascript">
	function nockClick(id)
	{
		//alert('当前节点id'+id);
	}
</script>
<body >
<%--<table class="td" border="0" cellspacing="0" cellpadding="0" background="../images/shu.jpg">--%>
<%----%>
<%--<tr class="text">--%>
<%--</tr>--%>
<%--<!-- Tree Component -->--%>
<%----%>
<%--  <newtree:tree tree="modTreeSession" --%>
<%--  			action="moduleLinkTreeSearch.do?method=mod&tree=$-{name}"--%>
<%--  			style="text" styleSelected="text-selected"--%>
<%--  			styleUnselected="text-unselected"--%>
<%--  			images="images"--%>
<%--  />--%>
<%----%>
<%--  </table>--%>
  <table class="td" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../images/shu.gif">
<tr>
	<td>
	</td>
</tr>
<tr class="text" valign="top">



<!-- Tree Component parameterTreeSession-->
<%
	//System.out.println(session.getAttribute("ivrYuyinTreeSession")+"...........");
 %>
	<td align="center">
  <newtree:tree tree="ivrYuyinTreeSession" 
  			action="./../ivr/paramTree.do?method=loadYuyinParamTree&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="images"
  />
  
  </td>
 </tr>
  </table>
</body>

<!-- Standard Footer -->

</html:html>