<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="./style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testLeaf.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <leafRight:btn styleId="addBtn" name="addBtn" width="80px;" style="height:30px;" scopeName="userRoleLeafRightInsession" nickName="Group_add" value="����" onclick="alert('���Ӱ�ť�����')" />
     <leafRight:img alt="��ϸ" nickName="Group_add" alt="����" onclick="alert('���Ӱ�ť�����')" border="0"  src="./style/chun/images/detail.gif" styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession"  />
<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="Group_update" value="�޸�" onclick="alert('�޸İ�ť�����')" />
<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="Group_delete" value="ɾ��" onclick="alert('ɾ����ť�����')" />
  </body>
</html>
<%--<img alt="��ϸ" src="../../style/<%=styleLocation%>/images/detail.gif"--%>
<%--						onclick="popUp('1<bean:write name='c' property='caseId'/>','effectCaseinfo.do?method=toEffectCaseinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,457)"--%>
<%--						width="16" height="16" border="0" />--%>
