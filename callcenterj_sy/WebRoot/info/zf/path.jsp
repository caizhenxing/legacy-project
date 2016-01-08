<%@ page contentType="text/html; charset=gbk" language="java" import="java.io.*" %>
<%
String pathfile = request.getRealPath(request.getServletPath());
String path = pathfile.substring(0, pathfile.lastIndexOf("\\"));
String str = "";
File file = new File(path);
File list[]=file.listFiles();
for(int i=0;i<list.length;i++){
	if(list[i].isDirectory()){
		if(!list[i].getName().equals("images")){
			str += list[i].getName() + ",";
		}
	}
}
if(!str.equals("")){
	str = str.substring(0, str.length()-1);
}
out.print("<input name=\"filepath\" type=\"hidden\" id=\"filepath\" value=\"" + str + "\">");
%>
