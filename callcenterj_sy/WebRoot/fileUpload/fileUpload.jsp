<%@ page contentType="text/html; charset=gbk"%>
<body bgColor="transparent" valgin="top" style="margin:0px;padding:0px;">
<%
 String uploadPath = request.getParameter("uploadPath");
 if(uploadPath==null||"".equals(uploadPath.trim()))
 	uploadPath="";
 String newName = request.getParameter("newName")==null?"":request.getParameter("newName");
 String dbid = request.getParameter("dbid")==null?"":request.getParameter("dbid");
 String oldName = request.getParameter("oldName");
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("path:"+path);
System.out.println("basePath:"+basePath);
%>
<form name="form1" method="post" action="<%=basePath %>/fileUploadServlet" enctype="multipart/form-data">
<table valgin="top" style="top:0px;left:opx;width:100%;height:100%;">
  <tr>
    <td colspan="2">
		<input type="file" name="upfile" onchange="show();" size="12">
		<input type="hidden" name="filePathName" value="<%=newName %>" /><!-- 上传文件后 到服务器生成的图片唯一名字包括路径 <img src 可直接访问 -->
		<input type="hidden" name="uploadPath" value="<%=uploadPath %>" /> <!-- 以工程名开始如 /callcenterj_sy/uploadImgs -->
		<input type="hidden" name="oldFileName" id="oldFileName" value="<%=oldName %>" /> <!-- 用户上传得文件名字 -->
		<input type="hidden" name="dbid" id="dbid" value="<%=dbid %>" />
		<input type="submit" name="Submit2" value=" 上 传 " style="height: 19px">
		<img name="image" width="50" height="50" src="../../<%=newName %>">
	</td>
  </tr>
<%--  <tr >--%>
<%--    <td valign="top">--%>
<%--    	<img name="image" width="50" height="50" src="../../<%=newName %>">--%>
<%--    </td>--%>
<%--    <td align="left" valign="top">--%>
<%--    </td>--%>
<%--  </tr>--%>
</table>
</form>
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