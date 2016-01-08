
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
<script language="JavaScript" type="text/JavaScript">
	
	function hideList(){
	  //alert(window.parent.exce.cols);
	  if(window.parent.exce.cols=="0,5,*") {
	    lybbs_tree.title="Close"
	    frameshow1.src="<bean:write name='imagesinsession'/>left_dot.jpg";
	  	window.parent.exce.cols="210,5,*";
	  } else {
	    frameshow1.src="<bean:write name='imagesinsession'/>left_dot2.jpg";
	    lybbs_tree.title="Open"
	    window.parent.exce.cols="0,5,*";
	  }
	}
	
	function check() {
	  if(window.parent.exce.cols=="210,5,*") {
	    frameshow1.src="<bean:write name='imagesinsession'/>left_dot.jpg";
		  lybbs_tree.title="Close"
	  }
	}
	
	
	
	</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />

  </head>
  
	<body>
		<table width="5" height="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="445" valign="middle" background="<bean:write name='imagesinsession'/>sp.gif">
		    <div id=lybbs_tree onclick="hideList();" title='Open'>
		    <img id="frameshow1" src="<bean:write name='imagesinsession'/>left_dot.jpg" width="5" height="50" >
		    </div>
		    </td>
		  </tr>
		</table>
	</body>

</html:html>
