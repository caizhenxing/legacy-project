<%@ page contentType="text/html; charset=gbk"%>
<%
String uploadfile = (String)session.getAttribute("uploadfile");
if(uploadfile == null){

%>
<form name="form1" method="post" action="upact.jsp" enctype="multipart/form-data">
<table width="300">
  <tr>
    <td width="84" height="75" align="center">上传文件</td>
    <td width="204"><img name="image" width="111" height="95"src="zw.jpg"></td>
  </tr>
  <tr>
  	<td></td>
  </tr>
  <tr>
    <td colspan="2">
<input type="file" name="upfile" onchange="show();">
<input type="submit" name="Submit2" value="上传">
	</td>
  </tr>
</table>
</form>
<%
}else{
String act=request.getParameter("act");
	if(act==null){
%>
<table width="300">
  <tr>
    <td height="75" colspan="2" align="center">文件上传成功！[<a href="?act=1"><font color="#FF0000">继续上传</font></a>]</td>
  </tr>
  <tr>
  	<td width="84"></td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
</table>

<%
	}else{
%>
<form name="form1" method="post" action="upact.jsp" enctype="multipart/form-data">
<table width="300">
  <tr>
    <td width="84" height="75" align="center">继续上传</td>
    <td width="204"><img name="image" width="111" height="95"src="zw.jpg"></td>
  </tr>
  <tr>
  	<td></td>
  </tr>
  <tr>
    <td colspan="2">
<input type="file" name="upfile" onchange="show();">
<input type="submit" name="Submit2" value="上传">
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