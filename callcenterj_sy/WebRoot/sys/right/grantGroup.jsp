<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>
<%@ include file="../../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html:html locale="true">
<head><title><bean:message bundle="sys" key="sys.grant"/></title>
<link href="<%=basePath %>style/<%=styleLocation%>/style.css" rel="stylesheet"
	type="text/css" />
	<link href="<%=basePath %>sys/right/subTreestyle.css" rel="stylesheet"
	type="text/css" />
<%--<style type="text/css">--%>
<%--<!----%>
<%--body {--%>
<%--	margin-left: 20px;--%>
<%--	margin-top: 30px;--%>
<%--	margin-right: 20px;--%>
<%--	margin-bottom: 30px;--%>
<%--}--%>
<%--.text-selected{--%>
<%--	background: #ccffff;--%>
<%--}--%>
<%---->--%>
<%--</style>--%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color:#E5F3F6;
<%--	background-color: #f5f5f5;--%>
}
a{ text-decoration: none;}
.text-selected{
	background: #ccffff;
}
.text{
	background-color:#E5F3F6;
}
-->
</style>
<script>
function toSubmit()
{
	document.forms[0].submit();
}
</script>

</head>
<!-- Body -->


<body class = "body_left emptyBodyStyle" style="margin:10 10 10 10;">
当前位置&ndash;&gt;组授权
<br>
<table width="100%" align="center" height="23"  border="0" cellpadding="0" cellspacing="0" >
                  <tr>
                <td width="70%" align="center">&nbsp; <font color="black"><bean:message bundle="sys" key="sys.grant"/></font></td>
                
              </tr>
                </table>
<!-- Tree Component action is this node's search-->
  <newtree:tree tree="grantTree"
               action="moduleLinkTreeSearch.do?method=right&type=g&tree=$-{name}"
                style="text"
        styleSelected="text-selected"
      styleUnselected="text-unselected"
      images="style/xia/images/tree"
  />
  
<html:form action="/sys/right.do?method=group" >

<table width="80%">
<tr>
<td align="right">
<input type="button" value="<bean:message bundle='sys' key='sys.grant'/>"   onclick="toSubmit()"/>

</td>
</tr>
</table>
</html:form> 
</body>

<!-- Standard Footer -->

</html:html>
