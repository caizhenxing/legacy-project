<%-- jsp1.2 --%>
<html>
<jsp:directive.page contentType="text/html; charset=GBK" />
<jsp:directive.include file="/decorators/default.jspf" />
<body class="list_body">
<div class="main_title">
	<div>{0}</div>
</div>
<div class="main_body">
	<div id="divId_panel"></div>
</div>

<script type="text/javascript">
//定义选项卡
	var arr = new Array();
{1}
	var panel = new Panel.panelObject("panel", arr, 0, "<c:out value = "$'{initParam[''publicResourceServer'']}/image/main/" />");
	document.getElementById("divId_panel").innerHTML = panel.display();
	Global.displayOperaButton = function()'{ };
</script>
</body>
</html>
