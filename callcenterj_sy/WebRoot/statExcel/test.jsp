<%@ page language="java"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="./js/Table.js" ></script>
  </head>
  
  <body>
		<input type="button" value="test" onclick="parseTbl('tbl1','小权excel','<%=basePath %>')">
		<table id="tbl1">
			<tr>
				<td>
					1
				</td>
				<td>
					1
				</td>
				<td>
					1
				</td>
			</tr>
				<tr>
				<td>
					2
				</td>
				<td>
					2中文
				</td>
				<td>
					2
				</td>
			</tr>
				<tr>
				<td>
					3
				</td>
				<td>
					3
				</td>
				<td>
					3中文
				</td>
			</tr>
							<tr>
				<td>
					4
				</td>
				<td>
					
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				<td>
					5
				</td>
				<td>
					0
				</td>
				<td>
					
				</td>
			</tr>
		</table>
  </body>
</html>
