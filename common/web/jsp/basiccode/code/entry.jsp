<%--
    @version:$Id: entry.jsp,v 1.8 2008/01/10 02:17:36 piaosh Exp $
    @since:$Date: 2008/01/10 02:17:36 $
--%>
<html>
<jsp:directive.include file="/decorators/default.jspf" />
<script type="text/javascript" src = "<c:out value='${initParam["publicResourceServer"]}'/>/public/scripts/xmlhttp.js"></script> 
<script type="text/javascript" src = "<c:out value='${initParam["publicResourceServer"]}'/>/public/scripts/codeTree.js"></script>

<body class="list_body">
	<form name="instrument_entry" method="post" action="">
		<table width="100%" border=0 cellpadding="0" cellspacing="0" height="100%">
		   <div class="main_title"><div>基础编码维护</div></div>
		    <tr valign="top">
		        <td width="20%" id="tdId_1">
		            <div class="update_subhead" id="list_button">&nbsp;</div>
					<div id="span_menu"  class="tree"></div>
		        </td>
		        
		        <td width="5" style="cursor:e-resize" onmousedown="setCapture()" onmouseup="releaseCapture();" onmousemove="StyleControl.dragWidth(tdId_1,30)" bgcolor="#eeeeee">&nbsp;</td>
		        <td>
		            <div class="main_body">
		                <div id="divId_panel"></div>
		            </div>
		            <script type="text/javascript">
		                var arr = new Array();
		                arr[0] = [" 列表 ", "", "<c:url value='/common/basiccodemanager.do?step=list&paginater.page=0&parentCode=${theForm.root}'/>"];
		                arr[1] = [" 详细 ", "", "<c:url value='/common/basiccodemanager.do?step=edit&parentCode='/>"];
		
		                var panel = new Panel.panelObject("panel", arr, 0, "<c:out value = "${initParam['publicResourceServer']}/image/main/"/>");
		                document.getElementById("divId_panel").innerHTML = panel.display();
		            </script>
		        </td>
		    </tr>
		</table>
	</form>
	<jsp:directive.include file="tree.jspf"/>
</body>
</html>
