<%@ page language="java" contentType="text/html;charset=GB2312" %>

<html>
<jsp:directive.include file="/base/default.jspf"/>
<body class="main_body">
<form name="f" action="" method="post">
<input type="hidden" name="oid" value="<c:out value='${oid}'/>"/>
<c:set var="parent" value="${entityObject.parent}"/>
<div class="update_subhead">
	<span class="switch_open" onClick="StyleControl.switchDiv(this,$('parenttable'))" title="�����ڵ�">������Ϣ</span>
</div>
<table border="0" cellpadding="0" cellspacing="0" class="Detail" id="parenttable">
	<tr>
		
		<td class="attribute">�ϼ�ģ��</td>
		<td><input name="dummy.parent.name" value="<c:out value='${parent.name}'/>"class="readonly" readonly="readonly"/>
		</td>
		<td class="attribute">ģ������</td>
		<td><input bisname="ģ������" maxlength="50" name="entityObject.name" value="<c:out value='${entityObject.name}'/>"/>
		<span class="font_request">*</span>&nbsp;
		</td>			
	</tr>														
	<tr>
		
		<td class="attribute">ģ������</td>
		<td colspan="3"><input name="entityObject.action" value="<c:out value='${entityObject.action}'/>"/>
		</td>			
	</tr>
	
</table>

</form>
</body>
</html>
