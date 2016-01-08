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
     <leafRight:btn styleId="addBtn" name="addBtn" width="80px;" style="height:30px;" scopeName="userRoleLeafRightInsession" nickName="Group_add" value="增加" onclick="alert('增加按钮点击了')" />
     <leafRight:img alt="详细" nickName="Group_add" alt="增加" onclick="alert('增加按钮点击了')" border="0"  src="./style/chun/images/detail.gif" styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession"  />
<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="Group_update" value="修改" onclick="alert('修改按钮点击了')" />
<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="Group_delete" value="删除" onclick="alert('删除按钮点击了')" />
  </body>
</html>
<%--<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"--%>
<%--						onclick="popUp('1<bean:write name='c' property='caseId'/>','effectCaseinfo.do?method=toEffectCaseinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,457)"--%>
<%--						width="16" height="16" border="0" />--%>
