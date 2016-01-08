<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="et.bo.sys.common.renderTree.RenderModuleTree"/>
<jsp:directive.page import="excellence.common.tree.ext.view.impl.ViewTree" />
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%@ include file="../style.jsp"%>
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
	<link href="<%=basePath+"/style/"+styleLocation+"/"+"images/collapseNav/comNav.css"%>" rel="stylesheet" type="text/css" />
	<style type="text/css">
		H1 a {
			background-image: url(<%=stylePath%>/dh9.jpg);
		}
	</style>
	<style>
		html { overflow-x:hidden; }
	</style> 
  </head>
  
  <body >
  <input type="text" id="txtDoNo" value="" style="display:none;"/>
	<script src="<%=basePath%>/js/moduleMenu/prototype.lite.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/moduleMenu/moo.fx.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/moduleMenu/moo.fx.pack.js" type="text/javascript"></script>
    
		<table width="0" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="200" height="83" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="29"><img src="<%=stylePath%>/dh1.jpg" width="209" height="29" /></td>
      </tr>
      <tr>
        <td height="6"><img src="<%=stylePath%>/dh2.jpg" width="209" height="6" /></td>
      </tr>
      
      <tr>
        <td height="19"><table width="0" height="19" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="9" height="19" background="<%=stylePath%>/dh9.jpg">&nbsp;</td>
            <td width="191" align="left" background="<%=stylePath%>/dh8.jpg">
            <div id="container">
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
            <td width="9" background="<%=stylePath %>dh10.jpg">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="2"><img src="<%=stylePath %>dh27.jpg" width="209" height="1" /></td>
      </tr>
      <tr>
        <td height="27"><img src="<%=stylePath %>dh7.jpg" width="209" height="27" /></td>
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
		toggles, contents, {opacity: true, duration: 280}
	);
	myAccordion.showThisHideOpen(contents[0]);
</script>
