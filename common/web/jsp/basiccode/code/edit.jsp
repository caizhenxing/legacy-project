<%--
    @version:$Id: edit.jsp,v 1.14 2008/01/22 13:53:59 lanxg Exp $
    @since $Date: 2008/01/22 13:53:59 $
--%>
<html>
<jsp:directive.include file="/decorators/edit.jspf"/>

<body class="main_body">
<form name="f" action="" method="post">
	
	<input type="hidden" name="oid" value="<c:out value='${theForm.bean.id}'/>"/>
	<input type="hidden" name="parentCode" value="<c:out value='${theForm.parentCode}'/>"/>
	<input type="hidden" name="step" value="save"/>
	<input type="hidden" name="bean.version" value="<c:out value='${theForm.bean.version}'/>"/>
	<!-- 
	<div class="list_group">
		<div class="list_title">����༭<span class="list_notes"></span></div>
	</div>
	 -->
	<c:set var="parent" value="${theForm.bean.parent}"/>
	<jsp:directive.include file="parent.jspf"/>
	<div class="update_subhead">
		 <span class="switch_open" onclick="StyleControl.switchDiv(this,submenu1)" title="�����ڵ�">������Ϣ</span>
	</div>
	<table border="0" cellpadding="0" cellspacing="0" class="Detail" id="submenu1">
		<tr>
			<td class="attribute">id</td>
			<td colspan="3"><input name="bean.id" value="<c:out value='${theForm.bean.id}' />" class="readonly" readonly="readonly"/>
			<input type="hidden" name="bean.deleteState" value="<c:out value='${theForm.bean.deleteState}'/>" />
			<input type="hidden" name="bean.layerNum" value="<c:out value='${theForm.bean.layerNum}'/>" />
			</td>
		</tr>
		<tr>
			<td class="attribute">����</td>
			<td><input name="bean.code" value="<c:out value='${theForm.bean.code}'/>" />
			<span class="font_request">*</span>&nbsp;</td>
			<td class="attribute">����</td>
			<td><input name="bean.name" value="<c:out value='${theForm.bean.name}'/>"/>
			<span class="font_request">*</span>&nbsp;</td>
		</tr>	
		<tr>
			<td class="attribute">��ʾ��</td>
			<td><input name="bean.displayName" value="<c:out value='${theForm.bean.displayName}'/>" />
			&nbsp;</td>
			<td class="attribute">���</td>
			<td><input name="bean.showSequence" value="<c:out value='${theForm.bean.showSequence}'/>" />
			<span class="font_request">*</span>&nbsp;</td>
		</tr>	
		<tr>
			<td class="attribute">��ϵͳ��</td>
			<td><input name="bean.subid" value="<c:out value='${theForm.bean.subid}'/>"  class="readonly" readonly="readonly"/>
			<span class="font_request">*</span>&nbsp;</td>
			<td class="attribute">ģ����</td>
			<td><input name="bean.typeCode" value="<c:out value='${theForm.bean.typeCode}'/>" class="readonly" readonly="readonly"/>
			<span class="font_request">*</span>&nbsp;</td>
		</tr>													
		<tr>
			<td class="attribute">����</td>
			<td colspan="3">
			<input name="bean.codeDesc" style="width:84.6%" value="<c:out value = '${theForm.bean.codeDesc}'/>" />
				<span title="<c:out value='${theForm.bean.codeDesc}'/>">
				<input type="button" id="edit_longText" onClick="definedWin.openLongTextWin(document.getElementsByName('bean.codeDesc')[0],'','');"/>
			</span></td>
		</tr>
	</table>
	
	<script type="text/javascript">
		if (CurrentPage == null) {
			var CurrentPage = {};
		}
		
		CurrentPage.reset = function () {
			document.f.reset();
		}
		
		CurrentPage.submit = function () {
			if (CurrentPage.validation() == false) {
				return;
			}
			FormUtils.post(document.forms[0], '<c:url value='/common/basiccodemanager.do'/>');
		}
		CurrentPage.validation = function () {
			return Validator.Validate(document.forms[0], 4);
		}
		CurrentPage.initValidateInfo = function () {
			document.getElementById('bean.code').dataType = 'Require';
			document.getElementById('bean.code').msg = '������д����';
			document.getElementById('bean.name').dataType = 'Require';
			document.getElementById('bean.name').msg = '������д����';
			document.getElementById('bean.showSequence').dataType = 'Require|Number';
			document.getElementById('bean.showSequence').msg = '��ű�����д����|��ű�����д����';
		}
		CurrentPage.initValidateInfo();
		CurrentPage.create = function() {
			//$('oid').value = '';
			FormUtils.post(document.forms[0], '<c:url value='/common/basiccodemanager.do?step=edit&type=new'/>');
		}
		
		function Validator.afterSuccessMessage(){
			if(parent.codeTree){
				parent.codeTree.Update(parent.codeTree.SelectId);
			}
		}
	</script>
</form>
</body>
</html>
