/**
 * ����������ҳ����֤
 */
function validate_addmeetroom(thisForm){
  	if (thisForm.meetingName.value == "") {
  		alert("�������������");
  		thisForm.meetingName.focus();
  		return(false);
  	}
  	if (thisForm.meetingThing.value == "") {
  		alert("�������������Դ����");
  		thisForm.meetingThing.focus();
  		return(false);
  	}
  	if (thisForm.meetingPrincipal.value == "") {
  		alert("��������Դ������");
  		thisForm.meetingPrincipal.focus();
  		return(false);
  	}
}

/**
 * ����������ҳ����֤
 */
function validate_applyRoom(thisForm){
	if (thisForm.meetingName.value == "") {
		alert("��ѡ���������");
		thisForm.meetingName.focus();
		return false;
	}
	if (thisForm.useDate.value == "") {
		alert("������ʹ������");
		thisForm.useDate.focus();
		return false;
	}
	if (thisForm.startHour.value == "") {
		alert("������ʹ��ʱ��Σ���ʼʱ��");
		thisForm.startHour.focus();
		return false;
	}
	if (thisForm.endHour.value == "") {
		alert("������ʹ��ʱ��Σ�����ʱ��");
		thisForm.endHour.focus();
		return false;
	}
	if (thisForm.applyReason.value == "") {
		alert("����д����˵��");
		thisForm.applyReason.focus();
		return false;
	}
	if (thisForm.applyUser.value == "") {
		alert("�����������");
		thisForm.applyReason.focus();
		return false;
	}

}

/**
 * �������ҳ��֤
 */
function validate_addCar(thisForm){
	if (thisForm.carCode.value == "") {
		alert("������Ҫ��ӵĳ��ƺ�");
		thisForm.carCode.focus();
		return false;
	}else {
		var re = new RegExp("^��[A-M]-[A-Z0-9][0-9]{4}");
      		if (!re.test(thisForm.carCode.value)){
      			alert("���ƺŸ�ʽ����");
      			thisForm.carCode.focus();
      			return false;
      		}
	}
	if (thisForm.carName.value == "") {
		alert("�����복������");
		thisForm.carName.focus();
		return false;
	}
	if (thisForm.carThing.value == "") {
		alert("�����복��������Ϣ");
		thisForm.carThing.focus();
		return false;
	}
}

/**
 * �ó�����ҳ��֤
 */
function validate_applyCar(thisForm){
	if (thisForm.applyUser.value == "") {
		alert("�������ó���");
		thisForm.applyUser.focus();
		return false;
	}
	if (thisForm.applyReason.value == "") {
		alert("�������ó�����");
		thisForm.applyReason.focus();
		return false;
	}
	if (thisForm.startDate.value == "") {
		alert("�����뿪ʼʱ��");
		thisForm.startDate.focus();
		return false;
	}
	if (thisForm.endDate.value == "") {
		alert("���������ʱ��");
		thisForm.endDate.focus();
		return false;
	}
	if (thisForm.operUser.value == "") {
		alert("������������");
		thisForm.operUser.focus();
		return false;
	}
	return true;
}