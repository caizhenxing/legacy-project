<%@ page 
language="java"
contentType="text/html; charset=GBK"
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
	background-image: ;
	background-image: url(images/bg.jpg);
}
-->
</style>
<link href="images/css/styleA.css" rel="stylesheet" type="text/css" />
</head>


<body >
	 <html:form action="/login.do?method=login" method="post" focus="username">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" background="images/index_bg.jpg">
  <tr>
    <td align="center"><table width="537" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2" valign="top"><img src="images/index_top_image.jpg" width="537" height="128"></td>
      </tr>
      <tr>
        <td width="246" valign="top"><img src="images/index_left_image.jpg" width="246" height="200"></td>
        <td width="291" valign="middle" bgcolor="#f5f5f5">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2">请输入用户名</td>
            </tr>
            <tr>
              <td colspan="2"><html:text property="username" styleClass="input" /></td>
            </tr>
            <tr>
              <td colspan="2">请输入密码</td>
            </tr>
            <tr>
              <td colspan="2"><html:password property="pw" styleClass="input" /></td>
            </tr>
            <tr>
              <td colspan="2">验证码</td>
            </tr>
            <tr>
              <td width="16%"><html:text property="val" styleClass="input" size="10"/></td>
              <td width="84%"><img border='0' src='RandomPicClient.jsp'/></td>
            </tr>
            <tr>
              <td height="25" colspan="2" align="center">
              <html:submit value="登陆"/><html:reset value="重写"/>
              </td>
            </tr>
          </table>
         </td>
      </tr>
      <logic:present name="error">
    <tr bgcolor="#f5f5f5">
    <td align="center" colspan="2">
         <font color="red"><bean:message name="error"/></font>
         </td>
    </tr>
    </logic:present>
      <tr>
        <td colspan="2" valign="top"><img src="images/index_buttom_image.jpg" width="537" height="9"></td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="top" bgcolor="#f5f5f5"> OA 办公系统</td>
      </tr>
    </table></td>
  </tr>
</table>
    
     </html:form>
</body>
</html:html>
