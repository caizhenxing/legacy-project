<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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

<%--    <link href="../../images/css/news.css" rel="stylesheet" type="text/css" />--%>
  </head>
  
  <body bgcolor="#eeeeee">
<%--    <html:form action="/forum/userInfo" method="post">--%>

<%--    </html:form>--%>
<br>


<jsp:include flush="true" page="newsTypeSix.jsp"></jsp:include><br>
<jsp:include flush="true" page="newsTypeSix.jsp"></jsp:include><br>
<%--<jsp:include flush="true" page="newsTypeFive.jsp"></jsp:include><br>--%>

<br><br>


  </body>
</html:html>
