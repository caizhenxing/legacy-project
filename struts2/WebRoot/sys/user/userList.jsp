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
			
			FormUtils.post(document.forms[0], '<c:url value="/sys/user/list.do"/>');
		}
		
		CurrentPage.remove = function(oid) {
			if (!confirm(msgInfo_.getMsgById('HR_I061_A_0'))) {
				return false;
			} 
			FormUtils.post(document.forms[0], '<c:url value = "/sys/user/delete.do&id="/>'+ oid );
		}
		
		CurrentPage.deleteall = function() {			
		 	var str="";
		 	var dd=document.getElementsByName('oid');
		
			for(var i=0;i<dd.length;i++){
				if(dd[i].checked==true){				
				var oid1=dd[i].value;			
				str+=oid1+"#";
				}
			}
			if (str==""||str==null){
				alert(msgInfo_.getMsgById('HR_I059_A_0'));
			}else{
				if (!confirm(msgInfo_.getMsgById('HR_I061_A_0'))) {
			    	return false;
		    	}

				$('step').value = "deleteAll";
				FormUtils.post(document.forms[0], '<c:url value="/hr/ratemanage.do?step=deleteAll&oids="/>' + str);
			}				
		}
	
		CurrentPage.create = function () {
			var len = listtable.rows.length;
			if(len<2){
				TableSort.setNoSelect();
				TableSort.dblClick();
			}else{
				$('oid').value = '';
				TableSort.setNoSelect();
				TableSort.dblClick();
			}
		}
		</script>
		
		<form name="f">
		<input type="hidden" name="oid" value=""/>
		
		<input type="hidden" name="entityClass" value="com.cc.sys.db.SysUser"/>
			<jsp:directive.include file="/sys/user/userSearch.jspf"/>
			<div class="update_subhead">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<span class="switch_open"
								onClick="StyleControl.switchDiv(this, $(allHidden))"
								title="伸缩节点"> 检索结果</span>
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
				<input type="button" class="opera_export" title="导出Excel文件"
					onClick="Print.exportExcel($(divId_scrollLing))" value="" />
				<input type="button" name="" class="opera_display" value="定制显示" title="定制显示列表栏目"
					onClick="CurrentPage.settable('hr_rate')" />					
				<input type="button" name="" class="opera_batchdelete" value="批量删除"
					onClick="CurrentPage.deleteall()" />
			</div>
			</div>
		</form>
	</body>
</html>
