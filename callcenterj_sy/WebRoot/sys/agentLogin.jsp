<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��ϯ��¼���빤��,�ߺ�,���</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">
		function parentLogin()
		{
			if(checkForm() != -1)
			{
				opener.document.getElementById('textworkid').value=document.getElementById('workId').value ;
				opener.document.getElementById('textlineid').value=document.getElementById('lineId').value;
				opener.document.getElementById('textgroupid').value=document.getElementById('groupId').value;	
				opener.document.getElementById("btnLoginExec").click();
			}
		}
		function checkForm()
		{
			if(document.getElementById('workId').value == 0)
			{
				alert('����д����');
				return -1;
			}
			if(document.getElementById('lineId').value == 0)
			{
				alert('����д�ߺ�');
				return -1;
			}
			if(document.getElementById('groupId').value == 0)
			{
				alert('����д���');
				return -1;
			}
			return 0;
		}
	</script>
  </head>
  
  <body>
   <table width="389" border="1" bgcolor="#0080c0" height="142" align="center" valign="middle">
<tbody><tr>
<td>&nbsp;����</td>
<td>&nbsp;<input type="text" name="workId" id="workId" /></td></tr>
<tr>
<td>&nbsp;�ߺ�</td>
<td>&nbsp;<input type="text" name="lineId" id="lineId" /></td></tr>
<tr>
<td>&nbsp;���</td>
<td>&nbsp;<input type="text" name="groupId" id="groupId" /></td></tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;<input type="button" name="makeSure" onclick="parentLogin()" value="ȷ��" /></td></tr>
</tbody></table>
   </body>
</html>
