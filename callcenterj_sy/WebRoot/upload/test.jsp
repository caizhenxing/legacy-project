<%@ page contentType="text/html; charset=gbk"%>

<iframe frameborder="0" width="100%" scrolling="No" src="up.jsp"></iframe>

<%

out.print(session.getAttribute("uploadfile"));
session.removeAttribute("uploadfile");

%>
