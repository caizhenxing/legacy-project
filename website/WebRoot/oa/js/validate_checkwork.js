/**
 * ���ȱ����Ϣҳ��֤
 */
function validate_addabsence(thisForm){
	if(thisForm.department.value == ""){
		alert("��ѡ����");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.absenceUser.value == ""){
		alert("��ѡ������");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.absenceType.value == ""){
		alert("��ѡ��ȱ������");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.startDate.value == ""){
		alert("��ѡ��ʼʱ��");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.endDate.value == ""){
		alert("��ѡ�����ʱ��");
		thisForm.endDate.focus();
		return false;
	}
}

/**
 * ��ǩ�Ǽ�ҳ��֤
 */
function validate_resign(thisForm){
	if(thisForm.department.value == ""){
		alert("��ѡ����");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.repairUser.value == ""){
		alert("��ѡ��ǩ��Ա");
		thisForm.repairUser.focus();
		return false;
	}	
	if(thisForm.repairDate.value == ""){
		alert("��ѡ��ǩ����");
		thisForm.repairDate.focus();
		return false;
	}
	if(thisForm.repairTime.value == ""){
		alert("��ѡ��ǩʱ��");
		thisForm.repairTime.focus();
		return false;
	}
}

/**
 * ȱ�ڲ�ѯҳ��֤
 */
function validate_absenceSelect(thisForm){
	if(thisForm.startDate.value != ""){
		if(thisForm.endDate.value == ""){
			alert("��ѡ�����ʱ��");
			thisForm.endDate.focus();
			return false;
		}
	}
	if(thisForm.endDate.value != ""){
		if(thisForm.startDate.value == ""){
			alert("��ѡ��ʼʱ��");
			thisForm.startDate.focus();
			return false;
		}
	}
}

/**
 * ���ڲ�ѯҳ��֤
 */
function validate_workcheckSelect(thisForm){
	if(thisForm.startT.value != ""){
		if(thisForm.endT.value == ""){
			alert("��ѡ�����ʱ��");
			thisForm.endT.focus();
			return false;
		}
	}
	if(thisForm.endT.value != ""){
		if(thisForm.startT.value == ""){
			alert("��ѡ��ʼʱ��");
			thisForm.startT.focus();
			return false;
		}
	}
}