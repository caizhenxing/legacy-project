<%@ page contentType="text/html; charset=gbk"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>Insert title here</title>
	</head>
	<body>
		<%
			String viewPicFlag = (String) session.getAttribute("viewPicFlag");//显示图片的状态标志

			String picName = (String) session.getAttribute("picName");//图片名和路径
			String picPath = (String) session.getAttribute("picPath");

			if (picName != null && !"".equals(picName)) session.setAttribute("jsp_pic_name", picName);
			if (picPath != null && !"".equals(picPath)) session.setAttribute("jsp_pic_path", picPath);

			//System.out.println("JSP->子表单->上传图片名称是：" + picName);
			//System.out.println("JSP->子表单->上传图片路径是：" + picPath);

			String uploadFlag = (String) request.getAttribute("check");//上传图片写入成功标志
			//System.out.println("JSP->子表单->上传图片是否成功？" + uploadFlag);
		%>
			<html:form action="/schema/fixedContactUpload.do?method=upload"
				method="post" enctype="multipart/form-data">
				<table width="90%" border="0" align="left" cellpadding="0"
					cellspacing="0" class="conditionTable">
					<tr>
						<td class="valueStyle" height="80" align="center">
							<logic:equal name="check" value="faile">
							上传图片失败！
					</logic:equal>
							<%
								if (picName != null && picPath != null || "update".equals(viewPicFlag))
								{
							%>
							<a href="user_images/<%=picName%>" target="_blank"><img
									src="user_images/<%=picName%>
							" width="120" height="80"
									border="0" align="middle" /> </a>
<%--							<html:file property="file" styleClass="buttonStyle" size="25" />--%>
							<html:submit styleClass="buttonStyle" onclick="return check()"
								value="修改照片" />
							<%
							}
							%>
							<%
								if (viewPicFlag != null && ("detail".equals(viewPicFlag) || "delete".equals(viewPicFlag)))
								{
							%>
							<a href="user_images/<%=picName%>" target="_blank"><img
									src="user_images/<%=picName%>
							" width="120" height="80"
									border="0" align="middle" /> </a>
							<%
							}
							%>
						</td>
					</tr>
					<tr>
						<td class="valueStyle">
							<%
								if (picName == null && picPath == null && "insert".equals(viewPicFlag))
								{
							%>
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
				//if("insert".equals(viewPicFlag))
				session.removeAttribute("jsp_pic_path");
				session.removeAttribute("jsp_pic_paths");
				session.removeAttribute("viewPicFlag");
			%>
	</body>
</html>
