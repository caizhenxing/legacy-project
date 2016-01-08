<%@ page 
language="java"
contentType="text/html; charset=gb2312"
pageEncoding="GBK"
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<script>
function RandomPicture(){
	document.write("<img border='0' src='../RandomPicClient.jsp'/>");
}
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
<title>用户登录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.a {
	font-size: 12px;
	color: #333333;
	text-decoration: none;
}
#Layer1 {
	position:absolute;
	left:499px;
	top:459px;
	width:100px;
	height:25px;
	z-index:1;
}
#Layer2 {
	position:absolute;
	left:499px;
	top:434px;
	width:89px;
	height:25px;
	z-index:2;
}
-->
</style>
<link href="images/css/styleA.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>

<body >
	<div id="Layer1">
	<a href="/ETforum/pcc/policeValid/valid.do?method=toValid" target="_blank">
	<img src="images/modPass.gif" width="100" height="25" border="0"/>
	</a>
	</div>
	
	<div id="Layer2">
	<a href="/ETforum/pcc/policeValid/post.do?method=toValid" target="_blank">
	<img src="images/newpass.gif" width="100" height="25" border="0"/>
	</a>
	</div>
	
	<html:form action="/login.do?method=login" method="post" focus="username">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="3"><img src="images/index/logo2_01.jpg" width="1000" height="167" /></td>
  </tr>
  <tr>
    <td><img src="images/index/logo2_02.jpg" width="199" height="91" /></td>
    <td><img src="images/index/logo2_03.jpg" width="371" height="91" /></td>
    <td><img src="images/index/logo2_04.jpg" width="430" height="91" /></td>
  </tr>
  <tr>
    <td><img src="images/index/logo2_05.jpg" width="199" height="232" /></td>
    <td background="images/index/logo2_06.jpg"><table width="200" border="0" align="right" cellpadding="0" cellspacing="1">
      <tr>
        <td width="55">用户名</td>
        <td colspan="2">
          <html:text property="username" styleClass="input" />
          </td>
      </tr>
      <tr>
        <td>密&nbsp;码</td>
        <td colspan="2">
          <html:password property="pw" styleClass="input" />
          </td>
      </tr>
      <tr>
        <td>验证码</td>
        <td width="58">
          <html:text property="val" styleClass="input" size="10"/>
          </td>
        <td width="63"><img border='0' src='RandomPicClient.jsp'/></td>
      </tr>
      <tr>
        <td></td>
        <td colspan="2">
          <input type="submit" name="Submit" value="登录" />
          <input type="submit" name="Submit2" value="取消" />
          </td>
      </tr>
      <logic:present name="error">
	      <tr>
	        <td colspan="3" align="center"> 
	        
	        <font color="red"><bean:message name="error"/></font>
	        </td>
	      </tr>
	  </logic:present>
    </table></td><td><img src="images/index/logo2_07.jpg" width="430" height="232" /></td>
  </tr>
  <tr>
    <td><img src="images/index/logo2_08.jpg" width="199" height="100" /></td>
    <td><img src="images/index/logo2_09.jpg" width="371" height="100" /></td>
    <td><img src="images/index/logo2_10.jpg" width="430" height="100" /></td>
  </tr>
</table>
	</html:form>
</body>
</html:html>