/**
 * 农机驾驶员考试申请 确认 验证
 */
function validate_applyMain(thisForm){
      if (thisForm.driver_card.value == "") {
        alert("请输入身份证号码!");
        thisForm.driver_card.focus();
		return false;
      } else if (!(/^\d{15}$|^\d{18}$|^\d{17}x$/.test(thisForm.driver_card.value))) {
        alert("身份证号码输入有误!");
        thisForm.driver_card.focus();
        return false;
        }
}

/**
 * 考试班级安排页 验证
 */
function validate_classSet(thisForm){
	if(thisForm.class_name.value == ""){
		alert("请输入班级名称!");
        thisForm.class_name.focus();
		return false;
	}
	if(thisForm.class_time.value == ""){
		alert("请输入招生时间!");
        thisForm.class_time.focus();
		return false;
	}
	if(thisForm.class_opentime.value == ""){
		alert("请输入开班时间!");
        thisForm.class_opentime.focus();
		return false;
	}
	if(thisForm.class_address.value == ""){
		alert("请输入上课地点!");
        thisForm.class_address.focus();
		return false;
	}
}

/**
 * 沈阳农机车辆考试申请页 验证
 */
 function validate_examApply(thisForm){
	if(thisForm.driver_name.value == ""){
		alert("请输入真实姓名!");
        thisForm.driver_name.focus();
		return false;
	}
	if(thisForm.driver_date.value == ""){
		alert("请输入出生日期!");
        thisForm.driver_date.focus();
		return false;
	}
	if(thisForm.driver_email.value == ""){
		alert("请输入电子邮件!");
        thisForm.driver_email.focus();
		return false;
	}else{
		var re = new RegExp("^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}");
		// var re = new RegExp("w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*");
      		if (!re.test(thisForm.driver_email.value)){
      			alert("电子邮件格式不对");
      			thisForm.driver_email.focus();
      			return false;
      		}
	}
	if(thisForm.driver_card.value == ""){
		alert("请输入身份证号码!");
        thisForm.driver_card.focus();
		return false;
	} else if (!(/^\d{15}$|^\d{18}$|^\d{17}x$/.test(thisForm.driver_card.value))) {
        alert("身份证号码输入有误!");
        thisForm.driver_card.focus();
        return false;
        }
	if(thisForm.driver_phone.value != ""){
		if(!(/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/.test(thisForm.driver_phone.value))){
			alert("固定电话格式不对");
      		thisForm.driver_phone.focus();
      		return false;
		}
	}
	if(thisForm.driver_mobile.value != ""){
		if(!(/^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/.test(thisForm.driver_mobile.value))){
			alert("手机号码格式不对");
      		thisForm.driver_mobile.focus();
      		return false;
		}
	}
	if(thisForm.driver_name.value == ""){
		alert("请输入真实姓名!");
        thisForm.driver_name.focus();
		return false;
	}
	
}