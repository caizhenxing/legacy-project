<jsp:directive.page contentType="text/html; charset=GBK" />
<jsp:directive.include file="/decorators/edit.jspf" />
<html>
<body class="main_body">
<form name="f" action="" method="post">
	<input type="hidden" name="oid" value="<c:out value=''$'{theForm.bean.id}''/>" />
	<input type="hidden" name="step"	value="<c:out value=''$'{theForm.step}''/>" />

<div class="update_subhead">
	<span class="switch_open" onClick="StyleControl.switchDiv(this,submenu1)" title="点击收缩节点">基本信息</span>
</div>
	<table border="0" cellpadding="0" cellspacing="0" class="Detail" id="submenu1">
{0}
	</table>
	
<script type="text/javascript">
{1}
</script>
	
</form>
</body>
</html>
