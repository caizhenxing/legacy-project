<%@ page language="java" contentType="text/html;charset=GB2312" %>
<jsp:directive.include file="/base/default.jspf"/>
<html>
<body class="main_body">
<form name="f" action="" method="post">
	<input type="hidden" name="oid" value="<c:out value='${oid}'/>" />

<div class="update_subhead">
	<span class="switch_open" onClick="StyleControl.switchDiv(this,submenu1)" title="伸缩节点">基本信息</span>
</div>
	<table border="0" cellpadding="0" cellspacing="0" class="Detail" id="submenu1">
		<tr>
		 <td class="attribute">员工号</td> 
		<td><input bisname="员工号" maxlength="50" name="entityObject.userId" value="<c:out value='${entityObject.userId}'/>" />&nbsp;<span
			class="font_request">*</span> </td>
		<td class="attribute">姓名</td>
		<td><input bisname="姓名" maxlength="50" name="entityObject.userName" value="<c:out value='${entityObject.userName}'/>" />&nbsp;<span
			class="font_request">*</span> </td>	
	</tr>
		<tr>		
			<td class="attribute">部门</td>
		<td>
			<input type="hidden" name="entityObject.sysDepartment.id" id="deptId" value="<c:out value='${entityObject.sysDepartment.id}'/>" />
			<input type="text" name="entityObject.sysDepartment.name" id="deptName" value="<c:out value='${entityObject.sysDepartment.name}'/>" readonly=readonly >&nbsp;<span class="font_request">*</span> 
			<input type="button" id="select_fromtree" onClick="showCgmlTreeWindow(deptId, 'entityObject.sysDepartment.id', 'entityObject.sysDepartment.name', '', '1');" title="点击这里进行部门选择" <c:out value='${pageScope.buttonDisable }'/>/>	
		</td>
			<td class="attribute">组</td>	
			<td>
			<ec:composite value="${entityObject.sysGroup.id}" valueName = "entityObject.sysGroup.id" textName = "entityObject.sysGroup.name" source = 'com.cc.sys.db.SysGroup-id-name' />&nbsp;</td>
		
		</tr>
	</table>
	<div class="update_subhead"><span class="switch_open"
	onClick="StyleControl.switchDiv(this,submenu2)" title="伸缩节点">详细信息</span>
</div>
<table border="0" cellpadding="0" cellspacing="0" class="Detail"
	id="submenu2">
	<tr>
		 <td class="attribute">真实姓名</td> 
		<td><input bisname="真实姓名" maxlength="50" name="entityObject.sysUserInfo.realName" value="<c:out value='${entityObject.sysUserInfo.realName}'/>" />&nbsp;<span
			class="font_request">*</span> </td>
		<td class="attribute">性别</td>
		<td><ec:composite value="${entityObject.sysUserInfo.sexId.code}" valueName = "entityObject.sysUserInfo.sexId.code" textName = "entityObject.sysUserInfo.sexId.name" source = 'sex' />&nbsp;</td>
	</tr>
	</table>
</form>
</body>
</html>	

<script type="text/javascript">
var msgInfo_ = new msgInfo();

	if (CurrentPage == null) {
		var CurrentPage = {};
	}
	CurrentPage.reset = function () {
		document.f.reset();
	}
	CurrentPage.submit = function () {
		if (!CurrentPage.validation()) {
			return;
		}
		if(!verifyAllform()){return false;}
		if (CurrentPage.CheckNum(document.getElementById('bean.rate')) == false) {
		  return false;
		}	
		FormUtils.post(document.forms[0], '<c:url value='/hr/ratemanage.do?step=save'/>');
	}
	CurrentPage.validation = function () {
		return Validator.Validate(document.forms[0],5);
	}
	CurrentPage.initValideInput = function () {
		
	}	
	CurrentPage.initValideInput();
	CurrentPage.create = function() {
		$('bean.station.code').value = '';
		$('bean.stationGrade.code').value = '';
		$('bean.rate').value='';
		$('oid').value = '';
		$('step').value = 'info';
		FormUtils.post(document.forms[0], '<c:url value='/hr/ratemanage.do?step=info'/>');
	}
	
	
	
</script>


