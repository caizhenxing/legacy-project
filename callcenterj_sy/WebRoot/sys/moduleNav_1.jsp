<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="et.bo.sys.common.renderTree.RenderModuleTree"/>
<jsp:directive.page import="excellence.common.tree.ext.view.impl.ViewTree" />
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'moduleNav.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>/js/moduleMenu/prototype.lite.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/moduleMenu/moo.fx.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/moduleMenu/moo.fx.pack.js" type="text/javascript"></script>
	<style>
	body {
		font:12px Arial, Helvetica, sans-serif;
		color: #000;
	}
	#container {
		width: 150px;
	}
	H1 {
		font-size: 11px;
		margin: 0px;
		width: 180px;
		cursor: pointer;
		height: 22px;
		line-height: 20px;	
	}
	H1 a {
		display: block;
		padding-top: 1px;
		padding-right: 8px;
		padding-bottom: 0px;
		padding-left: 8px;	
		width: 180px;
		color: #000;
		height: 22px;
		text-decoration: none;	
		moz-outline-style: none;
		background-image: url(<%=basePath%>/js/moduleMenu/title.jpg);
		background-repeat: repeat-x;
	}
	H1 a div{
		width:20px;
		height:20px;
		background-image: url(<%=basePath%>/js/moduleMenu/dh26.jpg);
	}
	.content{
		padding-left: 8px;
	}
	.content p{
		margin-top:4px;
		margin-bottom:4px;
	}
	</style>
	<style type="text/css">
	<!--
		body {
			margin-left: 1px;
			margin-top: 1px;
			margin-right: 1px;
			margin-bottom: 1px;
			background-color: #D3F0D2;
		}
		.wenzi {
			font-family: "ËÎÌו";
			font-size: 12px;
			font-style: normal;
			line-height: 14px;
			font-weight: normal;
			font-variant: normal;
			color: #476074;
			text-align: left;
		}
	-->
	</style>
	<style>
		html { overflow-x:hidden; }
	</style> 
  </head>
  
  <body >
    <div id="container">
    <%
            	ViewTree tree = (ViewTree)request.getSession().getAttribute("modTreeSession");
            	RenderModuleTree renderTree = new RenderModuleTree(tree);
            	StringBuffer sb = new StringBuffer();
            	renderTree.render(sb);
            	renderTree.renderRootSubLeaf(sb);
            	out.print(sb.toString());
    %>
    </div>
  </body>
</html>
<script type="text/javascript">
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('title');

	var myAccordion = new fx.Accordion(
		toggles, contents, {opacity: true, duration: 200}
	);
	myAccordion.showThisHideOpen(contents[0]);
</script>
