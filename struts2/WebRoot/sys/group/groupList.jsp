<%@ page language="java" contentType="text/html;charset=GB2312" %>
<jsp:directive.include file="/base/default.jspf" />
<html>
	<body>
		<script language="javascript">
		var msgInfo_ = new msgInfo();
		if (CurrentPage == null) {
    		var CurrentPage = {};
		}     
		CurrentPage.query = function() {
			
			FormUtils.post(document.forms[0], '<c:url value="/sys/group/list.do"/>');
		}
		
		
		</script>
		
		<form name="f">
		<input type="hidden" name="oid" value=""/>
		
		<input type="hidden" name="entityClass" value="com.cc.sys.db.SysGroup"/>
			<jsp:directive.include file="/sys/group/groupSearch.jspf"/>
			<div class="update_subhead">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<span class="switch_open"
								onClick="StyleControl.switchDiv(this, $(allHidden))"
								title="�����ڵ�"> �������</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="allHidden">
			<div id="divId_scrollLing" class="list_scroll">
				<table border="0" cellpadding="0" cellspacing="0" class="Listing" id="listtable" onClick="TableSort.sortColumn(event)">
					<table:table name="vb" />
				</table>
			</div>
			<div class="list_bottom">
				<page:page style="first"/>
				<input type="button" class="opera_export" title="����Excel�ļ�"
					onClick="Print.exportExcel($(divId_scrollLing))" value="" />
				<input type="button" name="" class="opera_display" value="������ʾ" title="������ʾ�б���Ŀ"
					onClick="CurrentPage.settable('hr_rate')" />					
				<input type="button" name="" class="opera_batchdelete" value="����ɾ��"
					onClick="CurrentPage.deleteall()" />
			</div>
			</div>
		</form>
	</body>
</html>
