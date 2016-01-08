
<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>资源查询</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  
  <frameset name="dict" rows="25%,75%" border="0" frameborder="0" framespacing="0">
  <frame name="topp" src="resourceManager.do?method=toQuery" noresize>
  <frame name="bottomm" src="../html/content.html" noresize>
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架</p>

  </body>
  </noframes>
</frameset>
</html:html>
