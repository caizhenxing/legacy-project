<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>error</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
  	<table width="80%" border="0" cellpadding="1" cellspacing="1" align="center" class="tablebgcolor">
  	  <tr>
    <td class="tdbgpicload">
    <bean:message key='sys.clew.error'/></td>
  </tr>
  	<tr>
   <td class="tdbgcolorloadright">
   <div align="center">
   <html:messages id="message" >
   <bean:write name="message"/><br>
   </html:messages>
   </div>
   </td>
   </tr>
   <tr>
    <td class="tdbgcolorloadbuttom">
    <a href="javascript:history.back()"><bean:message key="et.oa.news.select.error"/></a>
    </td>
  </tr>
   </table>
  </body>
</html:html>
