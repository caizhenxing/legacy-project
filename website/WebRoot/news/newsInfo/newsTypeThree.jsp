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
<%--    <table width="90%" border="0" align="center" cellpadding="2" cellspacing="2">--%>
<%--      <tr>--%>
<%--        <td width="13%" class="zhi">1</td>--%>
<%--        <td width="87%" class="zhi">网易123</td>--%>
<%--      </tr>--%>
<%--      <tr>--%>
<%--        <td class="zhi">2</td>--%>
<%--        <td class="zhi">网易123</td>--%>
<%--      </tr>--%>
<%--      <tr>--%>
<%--        <td class="zhi">3</td>--%>
<%--        <td class="zhi">网易123</td>--%>
<%--      </tr>--%>
<%--    </table>--%>
<div class="zhi">
<UL>
  <logic:iterate id="c" name="list">
  <LI>
  <H6><A 
  href="http://gb.corp.163.com/corpnews/07/0108/11/34AF29ER0073238I.html"><bean:write name='c' property="title" filter="true"/></A></H6>
  </LI>
  </logic:iterate>
  
 <div align="right" >
 <A href="http://gb.corp.163.com/corpnews/">更多...</A></div> 
</UL>
</div>
    </td>
  </tr>
  <tr>
    <td><img src="images/bottom.gif" width="283" height="11" /></td>
  </tr>
</table>
  </body>
</html:html>
