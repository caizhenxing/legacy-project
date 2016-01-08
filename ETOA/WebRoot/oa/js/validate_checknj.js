/**
 * 添加缺勤信息页验证
 */
function validate_addabsence(thisForm){
	if(thisForm.userid.value == ""){
		alert("请选择车主姓名！");
		thisForm.userid.focus();
		return false;
	}
	if(thisForm.businessid.value == ""){
		alert("请输入厂商id!");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njtype.value == ""){
		alert("请输入型号！");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njcode.value == ""){
		alert("请输入发动机号！");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njprice.value == ""){
		alert("请输入价格！");
		thisForm.endDate.focus();
		return false;
	}
	if(thisForm.njplate.value == ""){
		alert("请输入牌照号码！");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.njdate.value == ""){
		alert("请选择发牌日期！");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njyear.value == ""){
		alert("请输入有效年数!");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njcolor.value == ""){
		alert("请输入车颜色！");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njphone.value == ""){
		alert("请输入厂商电话！");
		thisForm.endDate.focus();
		return false;
	}
	if(thisForm.njsign.value == ""){
		alert("请输入停驶复驶标记!");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.njcheck.value == ""){
		alert("请输入年检标志!");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njreject.value == ""){
		alert("请输入报费标记!");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njimage.value == ""){
		alert("请选择开始时间");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njmark.value == ""){
		alert("请输入农机介绍!");
		thisForm.endDate.focus();
		return false;
	}
}