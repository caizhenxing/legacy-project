<%@ page contentType="text/html; charset=gbk" import="com.jspsmart.upload.*"%>
<%request.setCharacterEncoding("gbk");%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
String path="UploadFile";//�ļ��ϴ�·���������վ���Ŀ¼
//String[] fileExtAllow={"jpg","bmp","gif","png","rar","zip","doc","xls"};//�����ϴ���ͼƬ��ʽ
//boolean extBoolean=true;

String uploadfile=(String)session.getAttribute("uploadfile");

SmartUpload su = new SmartUpload();
su.initialize(pageContext);
su.upload();

if(su.getFiles().getFile(0).isMissing()){
	out.print("<script>");
    out.print("alert('��û��ѡ���ϴ��ļ���������!');");
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
//�ϱ�ע�͵��ˣ��������ļ�������֤��,�±ߵ�ifҲ���ж���
if(false){
	out.print("<script>");
    out.print("alert('�������ϴ������͵��ļ���������!');");
    out.print("history.go(-1);");
    out.print("</script>");
	
}else{
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
	String newFileName = sdf.format(new java.util.Date())+"."+fileExt;

com.jspsmart.upload.File jpgFile = su.getFiles().getFile(0);
jpgFile.saveAs(path + "/" + newFileName);

//�ϳ��ַ�����ԭ�ļ��������ļ�����ð�ŷָ����� oldName.rar:newName.rar
String fileName = oldFileName + ":" + newFileName;
//�ϳ��ַ������ŵ�session��
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
	    out.print("alert('�ϴ�ʧ�ܣ�������!');");
	    out.print("history.go(-1);");
	    out.print("</script>");	
	
	}
}

%>
