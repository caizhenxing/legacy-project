<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ordertreeview.tld" prefix="newtree" %>
<%@ include file="../../style.jsp"%>
<html:html locale="true">
<head>
<title><bean:message bundle="sys" key="sys.grant"/></title>
<script>
function toSubmit()
{
	document.forms[0].submit();
}
</script>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 20px;
	margin-top: 30px;
	margin-right: 20px;
	margin-bottom: 30px;
}
.text-selected{
	background: #ccffff;
}
-->
</style>
</head>
<!-- Body -->
<link href="./../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<link href="./../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
<link href="./../../css/style_left.css" rel="stylesheet" type="text/css">

<body class = "body_left">
<br>
<logic:present name="operSuccess" scope="request">
<script language="javascript">
alert("操作成功 请从新登录查看效果");
</script>
</logic:present>
<table width="100%" align="center" height="23"  border="0" cellpadding="0" bgcolor="#E5F3F6"  cellspacing="0" class="text">
                  <tr>
                <td width="70%" align="center">&nbsp; <font color="black">排序</font></td>
                
              </tr>
               <tr>
               <td>
  <newtree:tree tree="layerOrderTree_inSession" 
  			action="./../layerOrder/layerOrder.do?method=loadParamTree&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="style/xia/images/tree"
  />
  </td>
  </tr>
  </table>

<iframe src="./execLayerOrder.jsp" name="execFrame" frameborder="0" scrolling="no" width="100%"></iframe>
</body>	

<!-- Standard Footer -->

</html:html>
