<%@ page language="java" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>������������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body><br>
	<table width="600" cellspacing="0" cellpadding="0" border="1"  align="center">
		<tbody>
			<tr>
				<td>����Ҫ���ŵ�������������򿽱����±��ı�����</td>
			</tr>
			<tr>
				<td align="center"><textarea cols="60" rows="10" name="textarea1"></textarea></td>
			</tr>
			<tr>
				<td align="center"><input type="button" onclick="execPlayVoice()" value="������������" /></td>
			</tr>
		</tbody>
	</table>
   </body>
</html>
