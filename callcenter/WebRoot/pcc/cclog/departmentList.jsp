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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  
  <body bgcolor="#eeeeee">
  <html:form action="/pcc/assay/question.do" method="post"></html:form>
  <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="tdbgpiclist">部门</td>
      <td class="tdbgpiclist">打进电话次数</td>
    </tr>
    <logic:iterate id="c" name="deplist">
    <tr>
      <td class="tdbgcolorlist2"><bean:write name="c" property="depname" filter="true"/></td>
      <td class="tdbgcolorlist2"><bean:write name="c" property="count" filter="true"/></td>
    </tr>
    </logic:iterate>
  </table>
  </body>
</html:html>
