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
		<input type="hidden" name="filePathName" value="<%=newName %>" /><!-- �ϴ��ļ��� �����������ɵ�ͼƬΨһ���ְ���·�� <img src ��ֱ�ӷ��� -->
		<input type="hidden" name="uploadPath" value="<%=uploadPath %>" /> <!-- �Թ�������ʼ�� /callcenterj_sy/uploadImgs -->
		<input type="hidden" name="oldFileName" id="oldFileName" value="<%=oldName %>" /> <!-- �û��ϴ����ļ����� -->
		<input type="hidden" name="dbid" id="dbid" value="<%=dbid %>" />
		<input type="submit" name="Submit2" value=" �� �� " style="height: 19px">
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
		//�����ļ���չ����ʾ��ͬͼƬ
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
		//�ж��ļ���С
		var fso = new ActiveXObject('Scripting.FileSystemObject');
		var file = fso.GetFile(filepath);
		var rarSize = file.Size;
		var fileSize = 70*1024*1024;//��������ϴ�15M
		if(rarSize>fileSize){
			alert("���ϴ����ļ�����15M���������ϴ�����15M�ĸ�����");
			return false;
		}
	}
</script>
</body>