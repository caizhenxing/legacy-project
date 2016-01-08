<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ page import="excellence.common.tree.base.service.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>


<html:html locale="true">

<link href="./../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #f5f5f5;
}
-->
</style>
<body >
<%--<table class="td" border="0" cellspacing="0" cellpadding="0" background="../images/shu.jpg">--%>
<%----%>
<%--<tr class="text">--%>
<%--</tr>--%>
<%--<!-- Tree Component -->--%>
<%----%>
<%--  <newtree:tree tree="modTreeSession" --%>
<%--  			action="moduleLinkTreeSearch.do?method=mod&tree=$-{name}"--%>
<%--  			style="text" styleSelected="text-selected"--%>
<%--  			styleUnselected="text-unselected"--%>
<%--  			images="images"--%>
<%--  />--%>
<%----%>
<%--  </table>--%>
  
  
  <% 
  	TreeControlNodeService tcn = (TreeControlNodeService)request.getAttribute("subTreeNode");
  	if(tcn!=null)
  	{
  		excellence.common.tree.base.service.TreeControlNodeService[] l = tcn.findChildren();
  		for(int i=0; i<l.length; i++)
  		{
  			TreeControlNodeService ctcn = (TreeControlNodeService)l[i];
  			out.print(ctcn.getId()+":"+ctcn.getLabel());
  		}
  	}
  %>
  <table class="td" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../images/shu.gif">
<tr>
	<td>
	</td>
</tr>
<tr class="text" valign="top">



<!-- Tree Component -->
	<td align="center">
  <newtree:tree tree="parameterTreeSession" 
  			action="../../../sys/param/tree/paramTree.do?method=loadTree&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="images"
  />
  </td>
 </tr>
  </table>
  
  
</body>

<!-- Standard Footer -->

</html:html>