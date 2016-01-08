<%@ page language="java" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'agentNav.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%
		String cssinsession = styleLocation;
		String bgColor = "#8FBEDB";
		String tdAColor = "#63A908";
		String tdABorder = "#FFFFFF";
		String backColor = "#63A908";
		if("chun".equals(cssinsession))
		{
			bgColor = "#9FD26C";
			tdAColor = "#63A908";
			tdABorder = "#FFFFFF";
			backColor = "backColor";
		}
		else if("xia".equals(cssinsession))
		{
			bgColor = "#8FBEDB";
			tdAColor = "#4698D7";
			tdABorder = "#FFFFFF";
			backColor = "#469BD7";
		}
		else if("qiu".equals(cssinsession))
		{
			bgColor = "#FFDFA0";
			tdAColor = "#FE9A17";
			tdABorder = "#FFDFA0";
			backColor = "#FE9A17";
		}
		else if("dong".equals(cssinsession))
		{
			bgColor = "#C2DEF3";
			tdAColor = "#A6CFEE";
			tdABorder = "#FFFFFF";
			backColor = "#A6CFEE";
		}
	%>
		<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
			
	 <style type="text/css">
    	/* CSS Document */
		<!--
		.anniu{
			border:0px;
			font-family: "宋体";
			font-size: 12px;
			line-height: 20px;
			font-weight: normal;
			color: #333333;
			background-image: url(../../style/chun/images/annuBig.jpg);
			text-align: center;
			height: 22px;
			width: 150px;
		}
		.anniuM{
			border:0px;
			font-family: "宋体";
			font-size: 12px;
			line-height: 20px;
			font-weight: normal;
			color: #333333;
			background-image: url(../../style/chun/images/annuMin.jpg);
			text-align: center;
			height: 22px;
			width: 150px;
		}
	.buttonStyle2{
	font-family: "宋体";
	font-size: 12px;
	color: red;
	background-color: #7EB0D5;
	text-align: center;
}
    a:link {
	color: #FFFFFF;
	text-decoration: none;
}
    a:visited {
	text-decoration: none;
	color: #FFFFFF;
}
    a:hover {
	text-decoration: none;
	color: #FFFFFF;
}
    a:active {
	text-decoration: none;
	color: #FFFFFF;
}
.buttonStyle2{
	font-family: "宋体";
	font-size: 12px;
	color: #FFFFFF;
	background-color: #7EB0D5;
	text-align: center;
}
.midlistTitleStyle2 {
	background-image: url(<%=basePath %>style/chun/images/tiaoti.jpg);
	padding-bottom:5px;
}
.midlistTitleStyle2 td{
	height:20px;
}

-->
</style>
  </head>
  
  <body  style="margin:0px;padding:0px;"><!-- class="backcolor" -->
    <newtree:enuSubNodeNav tree="modTreeSession" nodeName="treeNode" tdClass="midlistTitleStyle2" imgClass="navAgentImg" skins="<%=styleLocation %>"  childTarget="bottom" />
  </body>

</html>
