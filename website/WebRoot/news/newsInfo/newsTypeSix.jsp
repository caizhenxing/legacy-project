<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link href="css/zhi.css" rel="stylesheet" type="text/css" />
<%--    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />--%>
  </head>
  
  <body bgcolor="#eeeeee">
    
<table width="283" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="28" background="images/top.gif" class="zhi">&nbsp; 业务简介</td>
  </tr>
  <tr>
    <td height="60" valign="middle" background="images/m.gif">
    <table width="90%" border="0" align="center" cellpadding="2" cellspacing="2">
      <logic:iterate id="c" name="list" indexId="i">
         <bean:write name="c" property="trtd" filter="false"/>
      </logic:iterate>
<tr><td class="zhi" align="right" colspan="2">      
 <A href="http://gb.corp.163.com/corpnews/">更多...</A> 
</td></tr>
    </table>
    </td>
  </tr>
  <tr>
    <td><img src="images/bottom.gif" width="283" height="11" /></td>
  </tr>
</table>

<table width="283" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="28" background="images/top.gif" class="zhi">&nbsp; 业务简介</td>
  </tr>
  <tr>
    <td height="60" valign="middle" background="images/m.gif">
    <table width="90%" border="0" align="center" cellpadding="2" cellspacing="2">
      <logic:iterate id="c" name="listTwo" indexId="i">
         <bean:write name="c" property="trtd" filter="false"/>
      </logic:iterate>
<tr><td class="zhi" align="right" colspan="2">      
 <A href="http://gb.corp.163.com/corpnews/">更多...</A>
</td></tr>
    </table>
    </td>
  </tr>
  <tr>
    <td><img src="images/bottom.gif" width="283" height="11" /></td>
  </tr>
</table>
  </body>
</html:html>
