<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<%
	String id = request.getAttribute("operid").toString();
	%>

	<title>处理会议列表信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript">
		//使哑
		function a(){
			document.forms[0].action = "../conf.do?method=operConf&id=<%=id%>&state=2";	
			document.forms[0].submit();
		}
		//使哑
		function b(){
			document.forms[0].action = "../conf.do?method=operConf&id=<%=id%>&state=1";	
			document.forms[0].submit();
		}
		//使哑
		function c(){
			document.forms[0].action = "../conf.do?method=operConf&id=<%=id%>&state=3";	
			document.forms[0].submit();
		}
		//返回页面
		function toback(){
			//opener.parent.topp.document.all.search.click();
			opener.parent.bottomm.document.execCommand('Refresh');
			
		}
	</script>
</head>

<body onunload="toback()" class="listBody">

	<logic:equal name="operSign" value="sys.common.operSuccess">
		<script>
		alert("操作成功！"); toback(); window.close();
	</script>
	</logic:equal>

	<html:form action="/callcenter/conf" method="post">
		<input type="button" name="shiya" value="使外线用户禁声" onclick="a()" />
		<input type="button" name="shifayin" value="使外线用户发声" onclick="b()" />
		<input type="button" name="tichu" value="使外线用户退出" onclick="c()" />
	</html:form>
</body>
</html:html>
