/**
 * 会议室设置页面验证
 */
function validate_addmeetroom(thisForm){
  	if (thisForm.meetingName.value == "") {
  		alert("请输入会议室名");
  		thisForm.meetingName.focus();
  		return(false);
  	}
  	if (thisForm.meetingThing.value == "") {
  		alert("请输入会议室资源描述");
  		thisForm.meetingThing.focus();
  		return(false);
  	}
  	if (thisForm.meetingPrincipal.value == "") {
  		alert("请输入资源负责人");
  		thisForm.meetingPrincipal.focus();
  		return(false);
  	}
}

/**
 * 会议室申请页面验证
 */
function validate_applyRoom(thisForm){
	if (thisForm.meetingName.value == "") {
		alert("请选择会议室名");
		thisForm.meetingName.focus();
		return false;
	}
	if (thisForm.useDate.value == "") {
		alert("请输入使用日期");
		thisForm.useDate.focus();
		return false;
	}
	if (thisForm.startHour.value == "") {
		alert("请输入使用时间段－开始时间");
		thisForm.startHour.focus();
		return false;
	}
	if (thisForm.endHour.value == "") {
		alert("请输入使用时间段－结束时间");
		thisForm.endHour.focus();
		return false;
	}
	if (thisForm.applyReason.value == "") {
		alert("请填写申请说明");
		thisForm.applyReason.focus();
		return false;
	}
	if (thisForm.applyUser.value == "") {
		alert("请添加申请人");
		thisForm.applyReason.focus();
		return false;
	}

}

/**
 * 车辆添加页验证
 */
function validate_addCar(thisForm){
	if (thisForm.carCode.value == "") {
		alert("请输入要添加的车牌号");
		thisForm.carCode.focus();
		return false;
	}else {
		var re = new RegExp("^辽[A-M]-[A-Z0-9][0-9]{4}");
      		if (!re.test(thisForm.carCode.value)){
      			alert("车牌号格式不对");
      			thisForm.carCode.focus();
      			return false;
      		}
	}
	if (thisForm.carName.value == "") {
		alert("请输入车辆名称");
		thisForm.carName.focus();
		return false;
	}
	if (thisForm.carThing.value == "") {
		alert("请输入车辆描述信息");
		thisForm.carThing.focus();
		return false;
	}
}

/**
 * 用车申请页验证
 */
function validate_applyCar(thisForm){
	if (thisForm.applyUser.value == "") {
		alert("请输入用车人");
		thisForm.applyUser.focus();
		return false;
	}
	if (thisForm.applyReason.value == "") {
		alert("请输入用车事由");
		thisForm.applyReason.focus();
		return false;
	}
	if (thisForm.startDate.value == "") {
		alert("请输入开始时间");
		thisForm.startDate.focus();
		return false;
	}
	if (thisForm.endDate.value == "") {
		alert("请输入结束时间");
		thisForm.endDate.focus();
		return false;
	}
	if (thisForm.operUser.value == "") {
		alert("请输入审批人");
		thisForm.operUser.focus();
		return false;
	}
	return true;
}