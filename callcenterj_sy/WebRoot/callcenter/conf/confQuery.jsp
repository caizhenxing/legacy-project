<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>查询会议列表</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
   <script language="javascript" src="../../js/common.js"></script>
   <script language="javascript">
   		//查询
    	function query(){
    		document.forms[0].action = "../conf.do?method=toConfList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
   </script>
  </head>
  
  <body class="listBody">
    <html:form action="/callcenter/conf" method="post">
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="search" type="button" class="bottom" value="查询" onclick="query()"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
