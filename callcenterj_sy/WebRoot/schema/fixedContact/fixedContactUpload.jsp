<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ include file="../../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>�ϴ���Ƭ</title>
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<!-- ǰ̨�ж� -->
	<script language="javascript">
  	function check()//�ж��ϴ���ͼƬ�ļ��Ƿ�Ϊ��
    {
    	var x = document.getElementById("file");
    	if(x.value=="")
    	{
    		alert("�������������ťѡ���ϴ�����Ƭ");
    		return false;
    	}
    	if(x.value != null)//�ж��ϴ���ͼƬ�ļ��Ƿ�Ϸ�
    	{
    		filePath	= document.forms[0].file.value;   
	      var   i   = filePath.lastIndexOf('.');//���ұ߿�ʼ�ҵ�һ��'.'   
	      var   len = filePath.length;//ȡ���ܳ���   
	      var   str = filePath.substring(len,i+1);//ȡ�ú�׺��   
	      var   exName = "JPG,GIF,PNF,BMP";//����ĺ�׺��   
	      var   k = exName.indexOf(str.toUpperCase());//ת�ɴ�д���ж�  
	      if(k==-1)//û�з��ϵ�   
	      {  
	      	alert("�����ϴ���jpg������gif������pnf������bmp����ʽ��ͼƬ�ļ�!");   
	        return   false;   
	      }   
    	}
    } 
  </script>
</head>
<body class="conditionBody">
	<%
		String viewPicFlag = (String) session.getAttribute("viewPicFlag");//��ʾͼƬ��״̬��־
		//System.out.println("JSP��ǰ�����ǣ�" + viewPicFlag);
		String uploadFlag = (String) request.getAttribute("check");//�ϴ�ͼƬд��ɹ���־
		//System.out.println("JSP->�ӱ�->�ϴ�ͼƬ�Ƿ�ɹ���" + uploadFlag);

		String picName = (String) session.getAttribute("picName");//ͼƬ����·��
		String picPath = (String) session.getAttribute("picPath");

		if (picName != null && !"".equals(picName)) session.setAttribute("jsp_pic_name", picName);
		if (picPath != null && !"".equals(picPath)) session.setAttribute("jsp_pic_path", picPath);

		//System.out.println("JSP->�ӱ�->�ϴ�ͼƬ�����ǣ�" + picName);
		//System.out.println("JSP->�ӱ�->�ϴ�ͼƬ·���ǣ�" + picPath);
	%>
	<!-- ��̨�ж� -->
	<logic:equal name="check" value="success">
		<script>
   		alert("��ϲ����ͼƬ�ϴ��ɹ�");
   	</script>
	</logic:equal>

	<logic:equal name="check" value="fail">
		<script>
   		alert("�Բ���!ͼƬ�ϴ�ʧ��");
   		window.close();
   	</script>
	</logic:equal>

	<logic:equal name="check" value="errorType">
		<script>
   		alert("ͼƬ��׺������Ϊ'.exe'��'.com'�� '.cgi'��'.jsp'֮һ��");
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
								picName = (String) session.getAttribute("old_pic_name");//��dto��session���õ�ԭ����ͼƬ����
					%>
					<a href="user_images/<%=picName%>" target="_blank"><img
							src="user_images/<%=picName%>
							" width="120" height="80"
							border="0" align="middle" /> </a>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="�޸���Ƭ" />
					<%
							}
							else
							{
					%>
								<b>��δ�ϴ�ͼƬ</b>
					<%
							}
						}
						//�����Ȼ�������ϴ�������ͨ��������ť�����ӹ�����
						if (uploadFlag != null && viewPicFlag == null)
						{
					%>
					<a href="user_images/<%=picName%>" target="_blank"><img
							src="user_images/<%=picName%>
							" width="120" height="80"
							border="0" align="middle" /> </a>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="�޸���Ƭ" />
					<%
						}
						//�����ͨ��������ӹ�����
						if ("insert".equals(viewPicFlag))
						{
					%>
					<font color="red">��Ƭ��׺���޶�Ϊ��jpg������gif������pnf������bmp��</font>
					<html:file property="file" styleClass="buttonStyle" size="25" />
					<html:submit styleClass="buttonStyle" onclick="return check()"
						value="�ϴ���Ƭ" />
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
