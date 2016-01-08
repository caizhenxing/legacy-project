<%@ page contentType="text/html; charset=gbk"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>Insert title here</title>
	</head>
	<body>
		<%
			String type = (String) request.getAttribute("type");
			if (type != null)
			{
				out.println("<font color=red>" + type + "</font>");
		%>
				<br><br><a href="javascript:history.go(-1)">返回</a>
		<%		
			}
		%>
	</body>
</html>
