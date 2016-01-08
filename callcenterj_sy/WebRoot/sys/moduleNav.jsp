<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="et.bo.sys.common.renderTree.RenderModuleTree"/>
<jsp:directive.page import="excellence.common.tree.ext.view.impl.ViewTree" />
<%@ include file="../style1.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String stylePath = basePath+"style/"+styleLocation+"/images/collapseNav/";
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
	<link href="<%=basePath+"/style/"+styleLocation+"/"+"images/collapseNav/comNav.css"%>" rel="stylesheet" type="text/css" />
	<style type="text/css">
		H1 a {
			background-image: url(<%=stylePath%>/dh7.jpg);
			background-repeat: no-repeat;
		}
		H1 a span{
		padding-left:5px;
		font-family: "ËÎÌו";
		font-size: 12px;
		font-style: normal;
		line-height: 14px;
		font-weight: normal;
		font-variant: normal;
		color: #476074;
		text-align: left;
		}
		.content p{
			margin-top:4px;
		}
		.title a div{
			display:none;
		}
	</style>
<%--	<style>--%>
<%--	body {--%>
<%--		font:12px Arial, Helvetica, sans-serif;--%>
<%--		color: #000;--%>
<%--	}--%>
<%--	#container {--%>
<%--		width: 180px;--%>
<%--	}--%>
<%--	H1 {--%>
<%--		font-size: 11px;--%>
<%--		margin: 0px;--%>
<%--		width: 180px;--%>
<%--		cursor: pointer;--%>
<%--		height: 22px;--%>
<%--		line-height: 20px;	--%>
<%--	}--%>
<%--	H1 a {--%>
<%--		display: block;--%>
<%--		padding-top: 1px;--%>
<%--		padding-right: 8px;--%>
<%--		padding-bottom: 0px;--%>
<%--		padding-left: 8px;	--%>
<%--		width: 180px;--%>
<%--		color: #000;--%>
<%--		height: 22px;--%>
<%--		text-decoration: none;	--%>
<%--		moz-outline-style: none;--%>
<%--		background-image: url(<%=basePath %>/js/moduleMenu/dh7.jpg);--%>
<%--		background-repeat: repeat-x;--%>
<%--	}--%>
<%--	.content{--%>
<%--		padding-left: 8px;--%>
<%--	}--%>
<%--	.content p{--%>
<%--		margin-top:4px;--%>
<%--		margin-bottom:4px;--%>
<%--	}--%>
<%--	</style>--%>

  </head>
  
  <body>
  	<table width="0" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table  height="83" border="0" cellpadding="0" cellspacing="0">
      <tr>
<%--        <td height="29"><img src="<%=stylePath%>/dh1.jpg" width="209" height="29" /></td>--%>
      </tr>
      <tr>
<%--        <td height="6"><img src="<%=stylePath%>/dh2.jpg" width="209" height="6" /></td>--%>
      </tr>
      
      <tr>
        <td height="19"><table width="0" height="19" border="0" cellpadding="0" cellspacing="0">
          <tr>
<%--            <td width="9" height="19" background="<%=stylePath%>/dh9.jpg">&nbsp;</td>--%>
			<td width="9" height="0" >&nbsp;</td>
<%--            <td width="190" align="left" background="<%=stylePath%>/dh8.jpg">--%>
			<td align="left" >
            <div id="container" >
    <%
        	ViewTree tree = (ViewTree)request.getSession().getAttribute("modTreeSession");
        	RenderModuleTree renderTree = new RenderModuleTree(tree);
        	renderTree.setBasePath(stylePath);
        	StringBuffer sb = new StringBuffer();
        	renderTree.render(sb);
        	renderTree.renderRootSubLeaf(sb);
        	out.print(sb.toString());
    %>
     </div>
            </td>
<%--            <td width="9" background="<%=stylePath %>dh10.jpg">&nbsp;</td>--%>
			<td width="9" >&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
<%--        <td height="2"><img src="<%=stylePath %>dh27.jpg" width="209" height="1" /></td>--%>
      <td height="2"></td>
      </tr>
      <tr>
<%--        <td height="27"><img src="<%=stylePath %>dh7.jpg" width="209" height="27" /></td>--%>
<%--      	<td height="27" style="padding-left:2px;"><img src="<%=stylePath %>dh7.jpg" width="210" height="27" /></td>--%>
      </tr>
      
      
      
    </table></td>
  </tr>
</table> 
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
