<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ include file="../../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>上传照片</title>
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<!-- 前台判断 -->
	<script language="javascript">
  	function check()//判断上传的图片文件是否为空
    {
    	var x = document.getElementById("file");
    	if(x.value=="")
    	{
    		alert("请点击‘浏览’按钮选择上传的照片");
    		return false;
    	}
    	if(x.value != null)//判断上传的图片文件是否合法
    	{
    		filePath	= document.forms[0].file.value;   
	      var   i   = filePath.lastIndexOf('.');//从右边开始找第一个'.'   
	      var   len = filePath.length;//取得总长度   
	      var   str = filePath.substring(len,i+1);//取得后缀名   
	      var   exName = "JPG,GIF,PNF,BMP";//允许的后缀名   
	      var   k = exName.indexOf(str.toUpperCase());//转成大写后判断  
	      if(k==-1)//没有符合的   
	      {  
	      	alert("请您上传‘jpg’、‘gif’、‘pnf’、‘bmp’格式的图片文件!");   
	        return   false;   
	      }   
    	}
    } 
  </script>
</head>
<body class="conditionBody">
	<%
		String viewPicFlag = (String) session.getAttribute("viewPicFlag");//显示图片的状态标志
		//System.out.println("JSP当前操作是：" + viewPicFlag);
		String uploadFlag = (String) request.getAttribute("check");//上传图片写入成功标志
		//System.out.println("JSP->子表单->上传图片是否成功？" + uploadFlag);

		String picName = (String) session.getAttribute("picName");//图片名和路径
		String picPath = (String) session.getAttribute("picPath");

		if (picName != null && !"".equals(picName)) session.setAttribute("jsp_pic_name", picName);
		if (picPath != null && !"".equals(picPath)) session.setAttribute("jsp_pic_path", picPath);

		//System.out.println("JSP->子表单->上传图片名称是：" + picName);
		//System.out.println("JSP->子表单->上传图片路径是：" + picPath);
	%>
	<!-- 后台判断 -->
	<logic:equal name="check" value="success">
		<script>
   		alert("恭喜您！图片上传成功");
   	</script>
	</logic:equal>

	<logic:equal name="check" value="fail">
		<script>
   		alert("对不起!图片上传失败");
   		window.close();
   	</script>
	</logic:equal>

	<logic:equal name="check" value="errorType">
		<script>
   		alert("图片后缀名不可为'.exe'、'.com'、 '.cgi'、'.jsp'之一！");
   		window.close();
   	</script>
	</logic:equal>

	<html:form action="/schema/fixedContactUpload.do?method=upload"
		method="post" enctype="multipart/form-data">
		<table width="90%" border="0" align="left" cellpadding="0"
			cellspacing="0" class="conditionTable">
			<tr>
				<td class="valueStyle" height="80" align="center">
					<%
						if ("update".equals(viewPicFlag))
						{
							if (session.getAttribute("old_pic_name") != null)
							{
								picName = (String) session.getAttribute("old_pic_name");//从dto的session中拿到原来的图片名称
					%>
					<a href="user_images/<%=picName%>" target="_blank"><img
							src="user_images/<%=picName%>
							" width="120" height="80"
							border="0" align="middle" /> </a>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="修改照片" />
					<%
							}
							else
							{
					%>
								<b>您未上传图片</b>
					<%
							}
						}
						//如果虽然处理了上传但不是通过单击按钮来链接过来的
						if (uploadFlag != null && viewPicFlag == null)
						{
					%>
					<a href="user_images/<%=picName%>" target="_blank"><img
							src="user_images/<%=picName%>
							" width="120" height="80"
							border="0" align="middle" /> </a>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="修改照片" />
					<%
						}
						//如果是通过添加链接过来的
						if ("insert".equals(viewPicFlag))
						{
					%>
					<font color="red">照片后缀名限定为‘jpg’、‘gif’、‘pnf’、‘bmp’</font>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="上传照片" />
					<font color="red">*</font>
					<%
					}
					%>
				</td>
			</tr>
		</table>
	</html:form>
	<%
		session.removeAttribute("viewPicFlag");
		session.removeAttribute("jsp_pic_name");
	%>
</body>
</html:html>
