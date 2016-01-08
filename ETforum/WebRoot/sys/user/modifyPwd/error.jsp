<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <html:base/>
    
    <title>²Ù×÷Ê§°Ü</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script>
    	function c()
    	{
			document.forms[0].action = "../UserOper.do?method=toModifyPwd";
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
  <html:form action="/sys/user/UserOper" method="post">
  <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" class="tdbgpic1"><bean:message key='sys.clew'/></td>
  </tr>
  </table>
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5">
    <tr>
      <td width="100%" class="tdbgcolor1"><div align="center"><bean:message key='sys.clew.error'/></div></td>
    </tr>
    <tr>
      <td class="tdbgcolor1"><div align="center">
        <html:button property="aaa"styleClass="bottom" onclick="c()"><bean:message key='agrofront.common.close'/> </html:button>
    </div></td>
  </tr>
</table>
</html:form>
  </body>
</html>
