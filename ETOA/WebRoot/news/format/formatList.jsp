
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>formatList.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <html:form action="" method="post" focus="login">
      <table border="0">
        <tr>
          <td>Login:</td>
          <td><html:text property="login" /></td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><html:password property="password" /></td>
        </tr>
        <tr>
          <td colspan="2" align="center"><html:submit /></td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
