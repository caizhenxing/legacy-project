<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<html:html>
  <head>
  <html:base />
    <title>热线调查</title>
  </head>
  <frameset name="dict" rows="15%,*" border="0" frameborder="0"  framespacing="0" bordercolor="#008000">
  	<frame name="topp" src="../inquiry.do?method=toQuery&state=<%=request.getParameter("state")%>" noresize scrolling="no">
	<frame name="bottomm" src="../html/content.jsp" noresize scrolling="no">
  </frameset>
</html:html>
