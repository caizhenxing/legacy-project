/**
 * ũ����ʻԱ�������� ȷ�� ��֤
 */
function validate_applyMain(thisForm){
      if (thisForm.driver_card.value == "") {
        alert("���������֤����!");
        thisForm.driver_card.focus();
		return false;
      } else if (!(/^\d{15}$|^\d{18}$|^\d{17}x$/.test(thisForm.driver_card.value))) {
        alert("���֤������������!");
        thisForm.driver_card.focus();
        return false;
        }
}

/**
 * ���԰༶����ҳ ��֤
 */
function validate_classSet(thisForm){
	if(thisForm.class_name.value == ""){
		alert("������༶����!");
        thisForm.class_name.focus();
		return false;
	}
	if(thisForm.class_time.value == ""){
		alert("����������ʱ��!");
        thisForm.class_time.focus();
		return false;
	}
	if(thisForm.class_opentime.value == ""){
		alert("�����뿪��ʱ��!");
        thisForm.class_opentime.focus();
		return false;
	}
	if(thisForm.class_address.value == ""){
		alert("�������Ͽεص�!");
        thisForm.class_address.focus();
		return false;
	}
}

/**
 * ����ũ��������������ҳ ��֤
 */
 function validate_examApply(thisForm){
	if(thisForm.driver_name.value == ""){
		alert("��������ʵ����!");
        thisForm.driver_name.focus();
		return false;
	}
	if(thisForm.driver_date.value == ""){
		alert("�������������!");
        thisForm.driver_date.focus();
		return false;
	}
	if(thisForm.driver_email.value == ""){
		alert("����������ʼ�!");
        thisForm.driver_email.focus();
		return false;
	}else{
		var re = new RegExp("^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}");
		// var re = new RegExp("w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*");
      		if (!re.test(thisForm.driver_email.value)){
      			alert("�����ʼ���ʽ����");
      			thisForm.driver_email.focus();
      			return false;
      		}
	}
	if(thisForm.driver_card.value == ""){
		alert("���������֤����!");
        thisForm.driver_card.focus();
		return false;
	} else if (!(/^\d{15}$|^\d{18}$|^\d{17}x$/.test(thisForm.driver_card.value))) {
        alert("���֤������������!");
        thisForm.driver_card.focus();
        return false;
        }
	if(thisForm.driver_phone.value != ""){
		if(!(/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/.test(thisForm.driver_phone.value))){
			alert("�̶��绰��ʽ����");
      		thisForm.driver_phone.focus();
      		return false;
		}
	}
	if(thisForm.driver_mobile.value != ""){
		if(!(/^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/.test(thisForm.driver_mobile.value))){
			alert("�ֻ������ʽ����");
      		thisForm.driver_mobile.focus();
      		return false;
		}
	}
	if(thisForm.driver_name.value == ""){
		alert("��������ʵ����!");
        thisForm.driver_name.focus();
		return false;
	}
	
}