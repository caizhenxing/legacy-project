/**
 * ���ȱ����Ϣҳ��֤
 */
function validate_addabsence(thisForm){
	if(thisForm.userid.value == ""){
		alert("��ѡ����������");
		thisForm.userid.focus();
		return false;
	}
	if(thisForm.businessid.value == ""){
		alert("�����볧��id!");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njtype.value == ""){
		alert("�������ͺţ�");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njcode.value == ""){
		alert("�����뷢�����ţ�");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njprice.value == ""){
		alert("������۸�");
		thisForm.endDate.focus();
		return false;
	}
	if(thisForm.njplate.value == ""){
		alert("���������պ��룡");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.njdate.value == ""){
		alert("��ѡ�������ڣ�");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njyear.value == ""){
		alert("��������Ч����!");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njcolor.value == ""){
		alert("�����복��ɫ��");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njphone.value == ""){
		alert("�����볧�̵绰��");
		thisForm.endDate.focus();
		return false;
	}
	if(thisForm.njsign.value == ""){
		alert("������ͣʻ��ʻ���!");
		thisForm.department.focus();
		return false;
	}
	if(thisForm.njcheck.value == ""){
		alert("����������־!");
		thisForm.absenceUser.focus();
		return false;
	}
	if(thisForm.njreject.value == ""){
		alert("�����뱨�ѱ��!");
		thisForm.absenceType.focus();
		return false;
	}
	if(thisForm.njimage.value == ""){
		alert("��ѡ��ʼʱ��");
		thisForm.startDate.focus();
		return false;
	}
	if(thisForm.njmark.value == ""){
		alert("������ũ������!");
		thisForm.endDate.focus();
		return false;
	}
}