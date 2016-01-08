<%@ page contentType="text/html; charset=gbk" language="java" import="java.io.*" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%request.setCharacterEncoding("gbk");
String pathfile = request.getRealPath(request.getServletPath());
String path = pathfile.substring(0, pathfile.lastIndexOf("\\"));
String ok = "";
String filename = request.getParameter("name");
String del = request.getParameter("del");
String content1 = request.getParameter("content1");
if(content1 == null){
	content1 = "";
	if(filename != null && !filename.equals("")){
		FileReader fr=new FileReader(path + "/" + filename);
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine();
		while(line != null){
			content1 = content1 + line;
			line = br.readLine();
		}
		br.close();
		fr.close();
	}
	if(del != null && !del.equals("")){
		File delFile = new File(path, del);
		delFile.delete();
		ok = "删除成功";
	}
}else{
	if(filename == null || filename.equals("") || filename.equals("null")){
		filename = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + ".htm";
	}
	File fileName = new File(path, filename);
	fileName.createNewFile();
	FileWriter fw = new FileWriter(fileName);
	fw.write(content1);
	fw.close();
	ok = "保存成功！";
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>金农市场分析</title>
</head>
<style>
body,table {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<body>
<table width="100%" height="100%" border="0">
<form name="form1" method="post" action="">
  <tr><input name="name" type="hidden" id="name" value="<%= filename %>">
  	<td valign="top" width="140">
<%
File list[]=new File(path).listFiles();
for(int i = 0; i < list.length; i++){
	String fname = list[i].getName();
	if(fname.endsWith("htm")){
		out.print("<a href='" + fname + "' target='_blank'>" + fname + "</a><br>");
		out.print("[ <a href='edit.jsp?name="+ fname +"'>修改</a> ] [ <a href='edit.jsp?del="+ fname +"'>删除</a> ]<br>");
	}
}
%>

  	</td>
    <td>
		<FCK:editor instanceName="content1" height="100%">
			<jsp:attribute name="value"><%=content1%></jsp:attribute>
		</FCK:editor>
	</td>
  </tr>
  <tr>
    <td height="30" align="right" colspan="2"><%=ok %>
    	<input type="button" name="Submit" value="新建" onClick="window.location.href='?'">
		<input type="submit" name="Submit" value="保存">
		<input type="reset" name="Submit2" value="刷新">
	</td>
  </tr>
</form>
</table>
</body>
</html>