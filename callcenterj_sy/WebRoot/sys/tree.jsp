<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ include file="../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>


<html:html locale="true">
<html:base />
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" src="../js/public.js"></script>
<script language="javascript">
	//alert(objArray.Item("key2"));   
	var gs = getScroll();
	//alert(gs["t"] +":"+ gs["l"] +":"+  gs["w"] +":"+  gs["h"]);
</script>
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
<body>
	<table class="td" width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0" background="../style/<%=styleLocation %>/images/tree/shu.gif">
		<tr>
			<td>
			</td>
		</tr>
		<tr class="text" valign="top">
			<!-- Tree Component -->
			<td align="center">
				<newtree:tree tree="modTreeSession"
					action="moduleLinkTreeSearch.do?method=mod&tree=$-{name}"
					style="text" styleSelected="text-selected"
					styleUnselected="text-unselected"
					images="style/xia/images/tree" />
			</td>
		</tr>
	</table>


</body>

<!-- Standard Footer -->

</html:html>
