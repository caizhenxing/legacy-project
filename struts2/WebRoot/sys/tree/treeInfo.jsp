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
		
		<td class="attribute">�ϼ��ڵ�</td>
		<td><input name="dummy.parent.name" value="<c:out value='${parent.label}'/>"class="readonly" readonly="readonly"/>
		</td>
		<td class="attribute">�ڵ�����</td>
		<td><input bisname="�ڵ�����" maxlength="50" name="entityObject.label" value="<c:out value='${entityObject.label}'/>"/>
		<span class="font_request">*</span>&nbsp;
		</td>			
	</tr>														
	<tr>
		
		<td class="attribute">�ڵ���</td>
		<td><input name="entityObject.id" value="<c:out value='${entityObject.id}'/>"/>
		<span class="font_request">*</span>&nbsp;
		</td>
		<td class="attribute">�ڵ����</td>
		<td><input bisname="�ڵ����" maxlength="50" name="entityObject.procAlias" value="<c:out value='${entityObject.procAlias}'/>"/>
		
		</td>			
	</tr>
	
</table>

</form>
</body>
</html>
