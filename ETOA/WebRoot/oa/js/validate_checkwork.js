/**
 * 添加缺勤信息页验证
 */
function validate_addabsence(thisForm){
	if(thisForm.department.value == ""){
		alert("请选择部门");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.absenceUser.value == ""){
		alert("请选择姓名");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.absenceType.value == ""){
		alert("请选择缺勤类型");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.startDate.value == ""){
		alert("请选择开始时间");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.endDate.value == ""){
		alert("请选择结束时间");
		thisForm.endDate.focus();
		return false;
	}
}

/**
 * 补签登记页验证
 */
function validate_resign(thisForm){
	if(thisForm.department.value == ""){
		alert("请选择部门");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.repairUser.value == ""){
		alert("请选择补签人员");
		thisForm.repairUser.focus();
		return false;
	}	
	if(thisForm.repairDate.value == ""){
		alert("请选择补签日期");
		thisForm.repairDate.focus();
		return false;
	}
	if(thisForm.repairTime.value == ""){
		alert("请选择补签时间");
		thisForm.repairTime.focus();
		return false;
	}
}

/**
 * 缺勤查询页验证
 */
function validate_absenceSelect(thisForm){
	if(thisForm.startDate.value != ""){
		if(thisForm.endDate.value == ""){
			alert("请选择结束时间");
			thisForm.endDate.focus();
			return false;
		}
	}
	if(thisForm.endDate.value != ""){
		if(thisForm.startDate.value == ""){
			alert("请选择开始时间");
			thisForm.startDate.focus();
			return false;
		}
	}
}

/**
 * 考勤查询页验证
 */
function validate_workcheckSelect(thisForm){
	if(thisForm.startT.value != ""){
		if(thisForm.endT.value == ""){
			alert("请选择结束时间");
			thisForm.endT.focus();
			return false;
		}
	}
	if(thisForm.endT.value != ""){
		if(thisForm.startT.value == ""){
			alert("请选择开始时间");
			thisForm.startT.focus();
			return false;
		}
	}
}