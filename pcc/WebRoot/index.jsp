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
<title>ÓÃ »§ µÇ Â¼</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #2a7793;
}
-->
</style>
<link href="css/styleA.css" rel="stylesheet" type="text/css" />
</head>

<body>
	 <html:form action="/login.do?method=login" method="post" focus="username">
     
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><table width="537" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2" valign="top"><img src="images/login01.jpg" width="537" height="128"></td>
      </tr>
      <tr>
        <td width="246" valign="top" bgcolor="#e6edf9"><img src="images/login02.jpg" width="246" height="200"></td>
        <td width="291" valign="middle" bgcolor="#e6edf9">
        	<table border="0" align="center">
        <tr align="center">
          <td colspan="2" ><bean:message key="sys.login.userkey"/></td>
          <td colspan="2"><html:text property="username" styleClass="input"/></td>
        </tr>
        <tr align="center">
          <td colspan="2"><bean:message key="sys.login.password"/></td>
          <td colspan="2"><html:password property="pw" styleClass="input"/></td>
        </tr>
        <tr align="center">
          <td colspan="2"><bean:message key="sys.login.validate"/></td>
          
          <td colspan="2" ><html:text property="val" styleClass="input"/></td>
          <td colspan="2"><img border='0' src='RandomPicClient.jsp'/></td>
          
        </tr>
        
        <tr align="center">
          <td colspan="4" align="right" class="submit"><html:submit><bean:message key="sys.login.login"/></html:submit><html:reset value="ÖØÐ´"/></td>
        </tr>
      </table>
        </td>
      </tr>
      <tr>
        <td colspan="2" valign="top"><img src="images/login03.jpg" width="537" height="9"></td>
      </tr>
      <logic:present name="error">
    <tr align="center">
          <td colspan="2" align="center" valign="top" bgcolor="#e6edf9"><html:errors name="error"/></td>
    </tr>
    </logic:present>
      <tr>
        <td colspan="2" align="center" valign="top" bgcolor="#e6edf9"><bean:message key="sys.login.title"/></td>
      </tr>
    </table></td>
  </tr>
</table>
 </html:form>
    
</body>
</html:html>
