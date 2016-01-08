<%@ page contentType="text/html; charset=gbk"%>
<body bgColor="transparent">
<%
String pageName = request.getServletPath();
pageName = pageName.substring(pageName.lastIndexOf("/")+1, pageName.length());
String uploadfile = (String)session.getAttribute("uploadfile");
if(uploadfile == null){

%>
<form name="form1" method="post" action="upact.jsp?p=<%= pageName %>" enctype="multipart/form-data">
<table height="130">
  <tr>
    <td colspan="2">
		<input type="file" name="upfile" onchange="show();" size="12">
	</td>
  </tr>
  <tr>
    <td>
    	<img name="image" width="90" height="77"src="zw.jpg">
    </td>
    <td align="left" valign="top">
    	<input type="submit" name="Submit2" value=" 上 传 " style="height: 19px">
    </td>
  </tr>
</table>
</form>
<%
}else{
String act=request.getParameter("act");
	if(act==null){
%>
<table height="130">
  <tr>
    <td align="center">
    	文件上传成功！
    	<br><br>
    	[<a href="?act=1"><font color="#FF0000">继续上传</font></a>]
    </td>
  </tr>
</table>

<%
	}else{
%>
<form name="form1" method="post" action="upact.jsp?p=<%= pageName %>" enctype="multipart/form-data">
<table height="130">
  <tr>
    <td colspan="2">
		<input type="file" name="upfile" onchange="show();" size="11">
	</td>
  </tr>
  <tr>
    <td>
    	<img name="image" width="90" height="77" src="zw.jpg">
    </td>
    <td align="left" valign="top">
    	<input type="submit" name="Submit2" value=" 上 传 " style="height: 19px">
    </td>
  </tr>
</table>
</form>
<%
	}
}

%>



<script type="text/javascript">
	function show(){
		//根据文件扩展名显示不同图片
		var filepath = document.form1.upfile.value;
		var n = filepath.lastIndexOf(".");
		var ext = filepath.substring(n+1, filepath.length);
		if(ext == "jpg" || ext == "bmp" || ext == "gif" || ext == "png" ){
			document.form1.image.src = document.form1.upfile.value;
		}else if(ext == "doc"){
			document.form1.image.src = "doc.jpg";
		}else if(ext == "xls"){
			document.form1.image.src = "xls.jpg";
		}else{
			document.form1.image.src = "rar.jpg";
		}
		//判断文件大小
		var fso = new ActiveXObject('Scripting.FileSystemObject');
		var file = fso.GetFile(filepath);
		var rarSize = file.Size;
		var fileSize = 70*1024*1024;//最大允许上传15M
		if(rarSize>fileSize){
			alert("您上传的文件大于15M，不允许上传大于15M的附件！");
			return false;
		}
	}
</script>
</body>