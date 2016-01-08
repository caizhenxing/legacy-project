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
<link href="./../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
<link href="./../../css/style_left.css" rel="stylesheet" type="text/css">
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
	function toSubmit()
	{
		document.forms[0].submit();
	}
</script>
<body >
<!-- *********************************************** -->
<br>
<table width="100%" align="center" height="23"  border="0" cellpadding="0" cellspacing="0" class="text">
                  <tr>
                <td width="70%" align="center">&nbsp; <font color="black"><bean:message bundle="sys" key="sys.grant"/></font></td>
                
              </tr>
                </table>
  <newtree:tree tree="moduleTreeLeafRightInSession" 
  			action="./../../sys/leafRight/leafRight.do?method=loadParamTree&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="images"
  />
  
<html:form action="/sys/leafRight/leafRight.do?method=roleGrant" method="post">
<table width="80%">
<tr>
<td align="right">
<input type="button" value="授权" class="buttonStyle" onclick="toSubmit()"/>
</td>
</tr>
</table>
</html:form> 
</body>

<!-- Standard Footer -->

</html:html>