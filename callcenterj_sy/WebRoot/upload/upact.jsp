<%@ page contentType="text/html; charset=gbk" import="com.jspsmart.upload.*"%>
<%request.setCharacterEncoding("gbk");%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
String path="UploadFile";//文件上传路径，相对于站点根目录
//String[] fileExtAllow={"jpg","bmp","gif","png","rar","zip","doc","xls"};//允许上传的图片格式
//boolean extBoolean=true;

String uploadfile=(String)session.getAttribute("uploadfile");

SmartUpload su = new SmartUpload();
su.initialize(pageContext);
su.upload();

if(su.getFiles().getFile(0).isMissing()){
	out.print("<script>");
    out.print("alert('您没有选择上传文件，请重试!');");
    out.print("history.go(-1);");
    out.print("</script>");
    return;
}
String oldFileName = su.getFiles().getFile(0).getFileName();
String fileExt = su.getFiles().getFile(0).getFileExt();

//for(int i=0;i<fileExtAllow.length;i++){
//	if(fileExt.equalsIgnoreCase(fileExtAllow[i])){
//		extBoolean=false;
//		break;
//	}
//}
//上边注释掉了，不进行文件类型验证了,下边的if也不判断了
if(false){
	out.print("<script>");
    out.print("alert('不允许上传此类型的文件，请重试!');");
    out.print("history.go(-1);");
    out.print("</script>");
	
}else{
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
	String newFileName = sdf.format(new java.util.Date())+"."+fileExt;

com.jspsmart.upload.File jpgFile = su.getFiles().getFile(0);
jpgFile.saveAs(path + "/" + newFileName);

//合成字符串，原文件名和新文件名用冒号分隔，如 oldName.rar:newName.rar
String fileName = oldFileName + ":" + newFileName;
//合成字符串后存放到session里
if(uploadfile == null || uploadfile.equals("")){
	uploadfile = fileName;
}else{
	uploadfile += "," + fileName;
}
session.setAttribute("uploadfile", uploadfile);

java.io.File file=new java.io.File(request.getRealPath(path), newFileName);

	if(file.exists()){
		out.print("<script>");
	    out.print("window.location.href='"+ request.getParameter("p") +"';");
	    out.print("</script>");

	}else{
		out.print("<script>");
	    out.print("alert('上传失败，请重试!');");
	    out.print("history.go(-1);");
	    out.print("</script>");	
	
	}
}

%>
