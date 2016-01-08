
function checkForm(thisForm) {
	if (thisForm.cust_name.value == "") {
		alert("\u8bf7\u8f93\u5165\u771f\u5b9e\u59d3\u540d!");
		thisForm.cust_name.focus();
		return false;
	}
	if (thisForm.cust_identity_card.value == "") {
		alert("\u8bf7\u8f93\u5165\u8eab\u4efd\u8bc1\u53f7\u7801!");
		thisForm.cust_identity_card.focus();
		return false;
	} else {
		if (!(/^\d{15}$|^\d{18}$|^\d{17}x$/.test(thisForm.cust_identity_card.value))) {
			alert("\u8eab\u4efd\u8bc1\u53f7\u7801\u8f93\u5165\u6709\u8bef!");
			thisForm.cust_identity_card.focus();
			return false;
		}
	}
	if (thisForm.cust_addr.value == "") {
		alert("\u8bf7\u8f93\u5165\u5730\u5740!");
		thisForm.cust_addr.focus();
		return false;
	} else {
		if (thisForm.cust_addr.value.length > 200) {
			alert("\u5730\u5740\u9650\u4e3a100\u4e2a\u6c49\u5b57\u4ee5\u5185!");
			thisForm.cust_addr.focus();
			return false;
		}
	}
	if (thisForm.cust_pcode.value == "") {
		alert("\u8bf7\u8f93\u5165\u90ae\u7f16!");
		thisForm.cust_pcode.focus();
		return false;
	} else {
		if (thisForm.cust_pcode.value.length != 6) {
			var pattern = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;
			if (!pattern.test(thisForm.cust_pcode.value)) {
				alert("\u90ae\u7f16\u5fc5\u987b\u5168\u90e8\u4e3a\u6570\u5b57");
				thisForm.cust_pcode.focus();
				thisForm.cust_pcode.select();
				return false;
			}
		} else {
			alert("\u90ae\u7f16\u9650\u4e3a6\u4e2a\u5b57\u7b26\u4ee5\u5185!");
			thisForm.cust_addr.focus();
			return false;
		}
	}
	if (thisForm.cust_email.value == "") {
		alert("\u8bf7\u8f93\u5165E-Mail!");
		thisForm.cust_email.focus();
		return false;
	} else {
		var pattern = /^.+@.+\..+$/;
		if (thisForm.cust_email.value == "") {
			return true;
		}
		if (!pattern.test(thisForm.cust_email.value)) {
			alert("\u4e0d\u662f\u5408\u6cd5\u7684E-Mail");
			thisForm.cust_email.focus();
			thisForm.cust_email.select();
			return false;
		}
	}
	if (thisForm.cust_tel_home.value != "") {
		if (!(/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/.test(thisForm.cust_tel_home.value))) {
			alert("\u5b85\u7535\u683c\u5f0f\u4e0d\u5bf9");
			thisForm.cust_tel_home.focus();
			return false;
		}
	} else {
		alert("\u8bf7\u8f93\u5165\u5b85\u7535");
		thisForm.cust_tel_home.focus();
		return false;
	}
	if (thisForm.cust_tel_work.value != "") {
		if (!(/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/.test(thisForm.cust_tel_work.value))) {
			alert("\u529e\u516c\u7535\u8bdd\u683c\u5f0f\u4e0d\u5bf9");
			thisForm.cust_tel_work.focus();
			return false;
		}
	} else {
		alert("\u8bf7\u8f93\u5165\u529e\u516c\u7535\u8bdd");
		thisForm.cust_tel_work.focus();
		return false;
	}
	if (thisForm.cust_tel_mob.value != "") {
		if (!(/^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/.test(thisForm.cust_tel_mob.value))) {
			alert("\u624b\u673a\u53f7\u7801\u683c\u5f0f\u4e0d\u5bf9");
			thisForm.cust_tel_mob.focus();
			return false;
		}
	} else {
		alert("\u8bf7\u8f93\u5165\u624b\u673a\u53f7");
		thisForm.cust_tel_mob.focus();
		return false;
	}
	if (thisForm.cust_voc.value != "") {
		if (thisForm.cust_voc.value.length > 50) {
			alert("\u5ba2\u6237\u884c\u4e1a\u9650\u4e3a50\u4e2a\u5b57\u7b26\u4ee5\u5185");
			thisForm.cust_voc.focus();
			return false;
		}
	} else {
		alert("\u8bf7\u8f93\u5165\u5ba2\u6237\u884c\u4e1a");
		thisForm.cust_voc.focus();
		return false;
	}
	if (thisForm.remark.value != "") {
		if (thisForm.remark.value.length > 400) {
			alert("\u5907\u6ce8\u9650\u4e3a400\u4e2a\u5b57\u7b26\u4ee5\u5185");
			thisForm.remark.focus();
			return false;
		}
	} else {
		alert("\u8bf7\u8f93\u5165\u5907\u6ce8");
		thisForm.remark.focus();
		return false;
	}
}

