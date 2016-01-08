<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
  <head>
    <title>µק²יֿךַי</title>
  </head>
  
  <frameset name="dict" rows="9%,*" border="0" frameborder="0"  framespacing="0" bordercolor="#008000">
  	<frame name="topp" src="detailForm.do?method=toQuery&topicId=<%=request.getParameter("id") %>" noresize scrolling="no">
	<frame name="bottomm" src="html/content.jsp" noresize scrolling="no">
  </frameset>
</html>
