
//var serverIp = "192.168.3.221";
var serverIp = "192.168.0.13";
var callerImcomming = ""; //4�����
var incommingTime = ""; //4��ʱ��
var gIsLog = 1; //ȫ�� �Ƿ���Ҫ���� 0 ���������Ϣ; 1 alert��Ӧ��Ϣ
function printAgentState(msg) {
	getObj("txtAgentState").value = msg;
}
//��־���� msg��ϯҳ����ʾ��Ϣ gIsLog������Ϣ
function printAgentStateLog(msg) {
	if (gIsLog == 1) {
		alert(msg);
	}
	getObj("txtAgentState").value = msg;
}
//��ϯ״̬��Ŀ ��½��ϯ��,������ϯ��,ͨ����ϯ��,��ϯ��ϯ��,�����Ŷ���,��������
function queryAgentStateNum() {
/*
	[28] long tlaQueryinfo(long function);
���ܣ���ѯ��Ϣ��
����һ���ѯ��Ϣ���ࡣ
���أ���ѯ���
��ע����ݲ���Ĳ�ͬ�����Բ�ѯ������=1��½��ϯ��������=2������ϯ��������=3ͨ����ϯ��������=4��ϯ��ϯ��������=6�����Ŷ���������=5��������
*/
	var status = '';
	status = status + obj1.tlaQueryinfo(1);
	status = status + ',' + obj1.tlaQueryinfo(2);
	status = status + ',' + obj1.tlaQueryinfo(3);
	status = status + ',' + obj1.tlaQueryinfo(4);
	status = status + ',' + obj1.tlaQueryinfo(6);
	status = status + ',' + obj1.tlaQueryinfo(5);
	return status;
}
function MyOpenWin(Url, name, Width, Height) {
//function OpenWin(Url,name,Width,Height,features) {
	var Top = (screen.height - Height) / 2;
	var Left = (screen.width - Width) / 2;
	var screenWin = window.open(Url, name, "width=" + Width + ",height=" + Height + ",top=" + Top + ",left=" + Left + ",resizable=0,scrollbars=1,status=0,location=0");
	screenWin.focus();
	return screenWin;
}
//alert
//��ϯ׼����½ȥ��¼ҳ
function prepLogin() {
	window.open("./agentLogin.jsp", "", "width=800,height=600,status=yes,resizable=yes,scrollbars=yes,top=200,left=200");
}
//��ϯ׼���ں�ҳѡ���ߺ�
function prepMakeCallIn() {
	var url = "./operCallIn.jsp?inlines=" + createTxtForInnerLine();
	window.open(url, "", "width=300,height=100,status=yes,resizable=yes,scrollbars=yes,top=200,left=200");
}
//��ϯ׼�����鵯��ҳ
function prepConference() {
	var url = "./conf.jsp";
	window.open(url, "", "width=800,height=600,status=yes,resizable=yes,scrollbars=yes,top=200,left=200");
}
//��ϯ���ɹ�����ҳ
function popUpCallOutSuccess() {
	var url = "./../custinfo/openwin.do?method=toOpenwinLoad&tel=12316";
	window.open(url, "", "width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280");
}
//��ϯ�������
function execConf() {
	var confNum = getObj("txtConfNum").value;
	if (confNum != 0) {
		var i = TransfertoivrAppendParam("ConfRoom.vds", confNum);
		if (i == 0) {
			printAgentState("\u53d1\u8d77\u4f1a\u8bae\u6210\u529f");
			confSuccessState();
		} else {
			printAgentState("\u53d1\u8d77\u4f1a\u8bae\u5931\u8d25");
		}
	}
}
function execHold() {
	var i = atHold();
	//�ȴ�ɹ� btnHold btnUnHold
	if (i == 0) {	//btnLaACW btnLaWCA
		getObj("btnUnHold").style.display = "inline"; //��ϯ
		getObj("btnHold").style.display = "none"; //��ϯ
		//��ȴ�ɹ�ʱ��״̬
		clickBtnHoldState();
		//changeBGImageForBtn('btnHold');
	}
}
function execUnHold() {
	var i = atUnHold();
	//����ɹ�
	if (i == 0) {	//btnLaACW btnLaWCA
		getObj("btnHold").style.display = "inline"; //��ϯ
		getObj("btnUnHold").style.display = "none"; //��ϯ
		clickBtnUnHoldState();
		//changeBGImageForBtn('btnUnHold');
	}
}
function execAnswer() {
	var i = laAnswer();
	//Ӧ��ɹ�
	if (i == 0) {	//btnLaACW btnLaWCA
		getObj("btnOnHook").style.display = "inline"; //��ϯ
		getObj("btnAnswer").style.display = "none"; //��ϯ
		changeIncomingIcon(1, "imgIncoming"); //��ͨͼ��
		//����ɹ��˵���
		clickBtnAnswerState();
		openWinIncoming(callerImcomming);
		//changeBGImageForBtn('btnAnswer');
	}
}
function execOnHook() {
	var i = laOnhook();
	//�һ�ɹ�
	if (i == 0) {	//btnLaACW btnLaWCA
		//�һ�״̬
		hookOnState();
	}
	else
	{
		//������ Ҳִ�йһ�״
		hookOnState();
	}
	return i;
}
//��ϯ��ivr
function agentOpenToIvr() {
	openToIvr();
	changeBGImageForBtn("btnPrepChangeToIvr");
}
//�������ߺ�е���
function exexMageCallThree()
{
	var rv = atHold();
	if(rv==0)
	{
		var caller = getObj("textlineid").value;
		var callee = getObj("txtOutLine").value;
		if (caller == -1)
		{
			printAgentState("����ߺ�Ϊ�ղ������");
			return;
		}
		var i = -1;
		//alert("callee is:"+callee + "@ caller is :"+caller);
		//i = Transfer_new1(2, callee, caller);
		i = MakeCall(2,callee,caller);
		//alert("����ֵ"+i);
		if(i==0)
		{
			//alert('����ֵר��״̬');
			//atTlaActivate();
			getObj('btnIsThreeCallExpert').value="1";
		}
	}
	else
	{
		alert("����onholdʧ����");
		printAgentState("��������ʧ��");
	}

}
function execSimpleMakeCallOut(isThree) {
	//��ϯ�ߺ�
	var caller = getObj("textlineid").value;
	var callee = getObj("txtOutLine").value;
	if (callee == 0) {
		alert("\u8bf7\u586b\u5199\u5916\u7ebf\u7535\u8bdd\u53f7\u7801!");
	} 
	else 
	{
		if (caller == -1) {
			alert("\u5ea7\u5e2d\u7ebf\u53f7\u4e3a\u7a7a\u4e0d\u80fd\u5916\u547c!");
		} 
		else 
		{
			var i = -1;
			if (isThree) {
				//i = Transfer_new1(2, callee, caller);
				i = MakeCall(2,callee,caller);
			} else {
			//alert('��ͨ���');
				i = MakeCall(2, callee, caller);
			}
				return i;
		}
		//clickGoOnState();
	}
	return -1;
}
//������ߺ� txtInline txtOutLine
function execMakeCallOut(isThree) {
	//��ϯ�ߺ�
	var caller = getObj("textlineid").value;
	var callee = getObj("txtOutLine").value;
	if (callee == 0) {
		alert("\u8bf7\u586b\u5199\u5916\u7ebf\u7535\u8bdd\u53f7\u7801!");
	} else {
		if (caller == -1) {
			alert("\u5ea7\u5e2d\u7ebf\u53f7\u4e3a\u7a7a\u4e0d\u80fd\u5916\u547c!");
		} else {
			var i = -1;
			if (isThree) {
				//i = Transfer_new1(2, callee, caller);
				i = MakeCall(2,callee,caller);
			} else {
			//alert('��ͨ���');
				i = MakeCall(2, callee, caller);
			}
			if (i == 0) {
				if (isThree) {
					threeCallSuccessState();
				} else {
					outCallState();
				}
				changeBGImageForBtn("btnOutCall");
			}
		//clickGoOnState();
		}
	}
}
//����ҳ�������� ׼�����䲥��
function execPrepVoicePlay() {
	prepVoicePlayForLetter();
}
function execCallExpert() {
	//��ϯ�ߺ�
	var caller = getObj("textlineid").value;
	var callee = getObj("textAgentCallThree").value;
	if (callee == 0) {
		alert("\u8bf7\u586b\u5199\u5916\u7ebf\u7535\u8bdd\u53f7\u7801!");
	} else {
		if (caller == -1) {
			alert("\u5ea7\u5e2d\u7ebf\u53f7\u4e3a\u7a7a\u4e0d\u80fd\u5916\u547c!");
		} else {
			var i = MakeCall(2, callee, caller);
			if (i == 0) {
				threeCallSuccessState();
			//���ר�ҳɹ��� ִ����ݿ�������
				updateEasyStateInfo(callee, obj1.tlaGetuserdata());
			//���ר��ʱ״̬�Ȳ���
			//outCallState();
			//changeBGImageForBtn('btnOutCall');
			}
		//clickGoOnState();
		}
	}
}
function execMakeCallIn() {
	var callee = getObj("txtInline").value;
	if (callee == -1) {
		alert("\u8bf7\u586b\u5199\u5185\u7ebf\u5ea7\u5e2d\u5de5\u53f7!");
	} else {
		var i = MakeCall(1, callee);
		if (i == 0) {
			inCallState();
			changeBGImageForBtn("btnChangeInLine");
		}
	}
	hasAllAgent = 0;
}
function bodyOnLoad() {
	var i = connect(serverIp);
	if (i != 0) {
		netCloseState();
	}
}
//תivr ��һ���ļ����� ��Ҫ���뱻�л�û������?
function execTransfertoivr(FileName) {
	var outlineNum = getObj("outlineNum").value;
	var fN = getObj("txtChangeToIvr").value;
	var i;
	//especialFileName �滻�������ivr�ļ���
	if (fN.indexOf("ConfRoom.vds") != -1) {
		if (outlineNum == 0) {
			alert("\u53d6\u5916\u7ebf\u53f7\u65f6\u51fa\u73b0\u9519\u8bef\u7981\u6b62\u8f6civr");
			return;
		}
		//FileName = fN + "," + outlineNum;
		//alert(FileName+":"+outlineNum);
		i = TransfertoivrAppendParam(fN, outlineNum);
	} else {
		//alert(fN);
		i = Transfertoivr(fN);
	}
	if (i == 0) {	//תivr�ɹ���
		changeIvrState();
	}
}
//�������
function execMultiIVR() {
	var fileName = getObj("txtOperationType").value;
	var param = getObj("txtS_ivrtype").value;
	//alert(fileName+":"+param);
	if (fileName != 0 && param != 0) {
		var i = TransfertoivrAppendParam(fileName, param);
		if (i == 0) {
			changeIvrState();
		}
		//changeBGImageForBtn("btnOutlineAppraise");
	} else {
		//alert('fileNameΪ�ջ�����Ϊ�մ���');
	}
}
//ִ���û��<�
function execOutlineAppraise(FileName) {
	//�û��<ۼ���ϯ��						\u5750\u5e2d\u6ee1\u610f\u5ea6\u9009\u62e9.vds
	//var i = TransfertoivrAppendParam("��ϯ�����ѡ��.vds", getObj("textworkid").value);
	var i = TransfertoivrAppendParam(FileName, getObj("textworkid").value);
	if (i == 0) {
		changeIvrState();
	}
	changeBGImageForBtn("btnOutlineAppraise");
}
//��ϯ�����ר��
function execPrepCallExpert() {
	var url = './prepThreeCall.jsp';
	return MyOpenWin(url,'',380,118);
}
//���ҳ�����ߺŷ���
function prepMakeCallOut() {
	window.open("./operCallOut.jsp", "", "width=380,height=102,status=yes,resizable=yes,scrollbars=yes,top=200,left=200");
	//window.open('../callcenter/userInfo.do?method=toCustinfoMain','','width=800,height=420,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
}

//#####################################################
//                  ��ϯ�����õĺ���
//#####################################################
function m_tlaListen(nWorkID)
{
	return obj1.tlaListen(nWorkID);
}
function m_tlaStoplisten()
{
	return obj1.tlaStoplisten();
}

//ǿ��
function m_tlaIntrude(wokrid)
{
	return obj1.tlaIntrude(wokrid)
}
//ǿ��
function m_tlaCut(workid)
{
	return obj1.tlaCut(workid);
}

//ע��
function m_tlaKill(mode,para)
{
	return obj1.tlaKill(mode,para);
}
//���9�� ���
function m_tlaGetcall(workid)
{
	return obj1.tlaGetcall(workid);
}
