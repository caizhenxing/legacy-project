<jsp:directive.page contentType="text/html; charset=GBK" />
<jsp:directive.include file="/decorators/default.jspf" />
<html>
	<body>
		<script language="javascript">
			{6}
		</script>
		<form name="f">
			<jsp:directive.include file="{3}" />
			<input type="hidden" name="step" value=''<c:out value="${theForm.step}"/>'' />
			<div class="list_explorer">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<span class="switch_open"
								onClick="StyleControl.switchDiv(this, $('listtable'))"
								title="点击收缩表格"> {7}</span>
						</td>
						<td align="right">
							
						</td>
					</tr>
				</table>
			</div>
			<div id="divId_scrollLing" class="list_scroll">
				<table border="0" cellpadding="0" cellspacing="0" class="Listing" id="listtable" onClick="TableSort.sortColumn(event)">
					<table:table name="viewBean" />
				</table>
			</div>
			<div class="list_bottom">
				<c:set var="paginater.forwardUrl" value="{4}" scope="page" />
				<%@ include file="/decorators/paginater.jspf"%>
				<input type="button" class="opera_export" title="导出Excel"
					onClick="Print.exportExcel($('divId_scrollLing'))" value="" />
					<input type="button" name="" class="opera_batchdelete" value="批量删除"
								onClick="CurrentPage.deleteall()" />
							<input type="button" name="button" value="定制显示"
								onClick="CurrentPage.settable({5}hr_rate{5})" />
			</div>
		</form>
	</body>
</html>
