<html>

<jsp:directive.include file="/decorators/default.jspf"/>

<body class="list_body">
<script language="javascript">
	var CurrentPage = {};
	CurrentPage.remove = function(oid) {
		if (!confirm(' 删除节点同时将会删除所有子节点, 您确定要继续吗 ? ')) {
			return false;
		} 
		FormUtils.post(document.forms[0], '<c:url value = "/common/basiccodemanager.do?step=delete"/>&oid=' + oid + "&parentCode=" + $('parentCode').value);
		if(parent.codeTree){
			parent.codeTree.Update(parent.codeTree.SelectId);
		}
	}
</script>
<form>
<input type="hidden" name="parentCode" value="<c:out value = "${theForm.parentCode}"/>"/>

<c:set var="parent" value="${theForm.bean}"/>
<jsp:directive.include file="parent.jspf"/>
<div class="update_subhead">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<span class="switch_open" onClick="StyleControl.switchDiv(this, $('listtable'))" title="伸缩节点"  style="font-weight:bolder">代码列表</span>
			</td>
		</tr>
	</table>
</div>
<div id="divId_scrollLing" class="list_scroll">
	<table border="0"  width=100% cellpadding="0" cellspacing="0" class="Listing" id="listtable"  onClick="TableSort.sortColumn(event)">
		<thead>
			<tr>
				<td style="width:5%">&nbsp;</td>
				<td type='Number'>序号</td>
				<td>代码</td>
				<td>名称</td>
				<td>系统名称</td>
				<td>系统类型</td>
				<td>描述</td>
				<td style="width:5%">操作</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${theForm.list}" varStatus="status">
			<tr>
				<td>
					<input type="hidden" name="oid" value="<c:out value="${item.id}"/>"/>
					<c:choose>
						<c:when test="${item.leaf}">&nbsp;&nbsp;</c:when>
						<c:otherwise><img src="<c:out value="${initParam['publicResourceServer']}"/>/image/tree/column/pic_dept.gif" border="0" align="absmiddle"/></c:otherwise>
					</c:choose>
				</td>
				<td style="text-align:right"><c:out value="${status.count}"/>&nbsp;</td>
				<td><c:out value="${item.code}"/>&nbsp;</td>
				<td><c:out value="${item.name}"/>&nbsp;</td>
				<td><c:out value="${item.subid}"/>&nbsp;</td>
				<td><c:out value="${item.typeCode}"/>&nbsp;</td>
				<td><c:out value="${item.codeDesc}"/>&nbsp;</td>				
				<td align="center">
					<input type="button" class="list_delete" onclick="CurrentPage.remove('<c:out value="${item.id}"/>')"/>
				</td>
			</tr>										
		</c:forEach>						
		</tbody>
	</table>
	<div class="list_bottom">
	<c:set var = "paginater.forwardUrl" value = "/common/basiccodemanager.do?step=list" scope = "page" />
	<%@ include file = "/decorators/paginater.jspf" %>
</div>
</div>
</form>
</body>
</html>
