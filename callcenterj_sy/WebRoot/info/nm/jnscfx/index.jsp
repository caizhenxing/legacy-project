<%@ page contentType="text/html; charset=gbk" import="java.io.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>金农市场分析</title>
<style type="text/css">
body{margin:0px; text-align:center;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');}
</style>
</head>

<body style="text-align:center; background:url(images/i_bj.gif) top left repeat-x;">

	<table width="100%" height="100%">
		<tr>
			<td>
			<marquee direction="up" SCROLLAMOUNT="4" width="100%" height="100%">
<%
String p = "";
String pathfile = request.getRealPath(request.getServletPath());
String path = pathfile.substring(0, pathfile.lastIndexOf("\\"));
File list[]=new File(path).listFiles();
for(int i = list.length-1; i >= 0; i--){
	String fname = list[i].getName();
	if(fname.endsWith("htm")){
		p = fname;
		break;
	}
}
if( p != null && !p.equals("")){
	File file = new File(path, p);
	FileReader fr=new FileReader(file);
	BufferedReader br=new BufferedReader(fr);
	String Line=br.readLine();
	while(Line!=null){
		out.println(Line);
		Line=br.readLine();
	}
	br.close();
	fr.close();
}
%>			</marquee>
			</td>
		</tr>
	</table>
</body>
</html>