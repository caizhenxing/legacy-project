var  obj1 = document.getElementById("xx");
var hasAllAgent = 0;
//function printAgentState(msg) {
//	getObj("txtAgentState").value = msg;
//}
//��ϯ�ߺż���
var lineArr = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16']; //������ϯ�ߺŵ�ȫ������ʱ��ֵ���ȥ
var MAXFREELENGTH = 100;
var MAXWORKIDLENGTH = 100;
var workidArr = new Array(MAXWORKIDLENGTH); //������ϯ���ŵ�ȫ��100Ӧ���㹻��
var freeWorkidArr = new Array(MAXFREELENGTH); //�������ϯ���ŵ� 100��Ŀǰ�㹻��
function changeBGImageForBtn(oId)
{
	/*
	var obj = getObj(oId);
	if(obj)
	{
		var name = obj.name;
		//printAgentState(name);
		if(name == "btn1")
		{
			obj.name = "btn2";
			obj.style.backgroundImage="url(./../images/tpagent/dl8Dark.jpg)";
		}
		else if(name == "btn2")
		{
			obj.name = "btn1";
			obj.style.backgroundImage="url(./../images/tpagent/dl8.jpg)";
		}
	}
	*/
}
function getObj(id)
{
	return document.getElementById(id);
}

function evtChangeAgentState(agtId,state)
{
	getObj(agtId).value=state;
}

function clearFreeWorkids()
{
	for(var i=0; i<MAXFREELENGTH; i++)
	{
		freeWorkidArr[i] = '-1';
	}
}
//�õ����еĿ��й������� 
function getFreeWorkids()
{
	clearFreeWorkids();
	var freeAgentNum = obj1.tlaQueryinfo(2);
	//printAgentState(1);
	for(var i=0; i<freeAgentNum; i++)
	{
		//����
		var workId = obj1.tlaQueryworkid(i); 
		if(workId != -1)
		{
			freeWorkidArr[i] = workId;
		}
	}
	freeWorkidArr[freeAgentNum] = "-1";
	return freeWorkidArr;
}
function atMonitorLevel()
{
	obj1.tlaSetpriority(1,2000);
}
function atTlaSwap()
{
	var i = obj1.tlaSwap();
	return i;
}
//�õ���¼��ϯ����
function getHasLoginAgentNum()
{
	return obj1.tlaQueryinfo(1);
}
//������ŵĵ���ϯ״̬
function getAgentStateMonitorByNum(num)
{
	var arr = ['δ��¼','����','��ϯ', '����', '��������','ͨ��','����','ת��','����','����','��������','����ͨ��'];
	var v = obj1.tlaQuerystate(num);
	if(v < 0 )
	{
		v = 0;
	}

	return arr[v];
}
//������ŵĵ���ϯ���� -1û�ҵ�
function getAgentWorkIdMonitorByNum(num)
{
	return obj1.tlaQueryworkid(num);
}
function clearSelect(slt)
{
	if(slt)
	{
		var length = slt.length;
		for(var i=0; i<length; i++)
		{
			slt.options[0] = null;
		}
	}
}
//�������б����ı���ʽ��������ϯ��*******************************************************************
function createTxtForInnerLine()
{
	var numStrs = '';
	//do.....
	var freeWarr = getFreeWorkids();
	for(var i=0; i<MAXWORKIDLENGTH; i++)
	{
		var workid = freeWarr[i];
		//�д��󷵻�
		if(workid == -1)
			return numStrs;
		//����һ��option
		if(i>0)
		{
			numStrs = numStrs+':'+workid;
		}
		else
		{
			numStrs = workid;
		}
	}
	return numStrs;
}
//�������б���option��ʽ��ʾ����ϯ��
function createOptionForInnerLine(selectId)
{
	if(hasAllAgent==0)
	{
		//do.....
		var slt = getObj(selectId);
		if(slt)
		{
			clearSelect(slt); //���
			var freeWarr = getFreeWorkids();
			for(var i=0; i<MAXWORKIDLENGTH; i++)
			{
				var workid = freeWarr[i];
				//�д��󷵻�
				if(workid == -1)
					return;
				//����һ��option
				slt.options[slt.length] = new Option(workid, workid);
			}
		}
	}
	hasAllAgent++;
}


//��ϯ���ż���
//����
function connect(ip)
{
  printAgentState(ip);
  if (obj1 == null) printAgentState("fail");
  else{
	  var i = obj1.tlaOpen(ip);
	  if (i != 0 ){
	   changeNetStateIcon(1, 'imgNetState');
	   printAgentState("����ʧ��")
	  }else 
	 {
	   printAgentState("���ӳɹ�!");
	   changeNetStateIcon(0, 'imgNetState');
	   connSuccessState();
	  }
	  return i;
  }
}
//�Ͽ�
function disconnect()
{
  var i = obj1.tlaClose();
  if (i != 0 ){
   printAgentState("�Ͽ�������������ʧ��!");
  }else 
 {
   printAgentState("�Ͽ������������ӳɹ�!");
   changeNetStateIcon(1, 'imgNetState');
  } 
}
//��¼ ���ڹ���״̬
function login(workid,lineid,groupid)  
{   
	if(obj1 == null)
	{
		printAgentState('obj1 == null error');
		alert("������ϯ�˳������!");
	}
printAgentState(lineid+":"+workid+":"+groupid);
obj1.tlaClose();
	 obj1.tlaOpen(serverIp);
obj1.tlaKill(1,lineid);
obj1.tlaKill(2,workid);
  var i = obj1.tlaLogin(lineid,workid,0);  
  
  if (i != 0 ){
   printAgentState("����ʧ��!");
   loginFailureState();
  }else 
 {
   printAgentState("����ɹ�!");
 }
 return i;
}
//ע��
function laLogoff()
{
  var i = obj1.tlaLogoff();
 
  if (i != 0 ){
   printAgentState("ע��ʧ��!");
   change2LoginState();
  }else 
 {
   printAgentState("ע���ɹ�!");
   change2LoginState();
  } 
  
  return i;
}
//��ϯ ���ڹ���״̬ �����ܽӵ绰��
function laACW()
{
  var i = obj1.tlaACW();
  if (i != 0 ){
   printAgentState("��ϯʧ��!");
   connSuccessState()
  }else 
 {
   printAgentState("��ϯ�ɹ�!");
   clickPauseState();
  } 
  return i;
}
//��ϯ
function laWCA()
{
  var i = obj1.tlaWCA();
  if (i != 0 ){
   printAgentState("��ϯʧ��!");
   connSuccessState()
  }else 
 {
   printAgentState("��ϯ�ɹ�!");
  } 
  return i;
}
//�ȴ�
function atHold()
{
	var i = obj1.tlaHold();
	if(i != 0)
	{
	   printAgentState("�ȴ�ʧ��!");
	   connSuccessState();
	 }else 
	 {
	   printAgentState("�ȴ��ɹ�!");
	  } 
	return i;
}
function atTlaActivate()
{
	var i = obj1.tlaActivate();
	if(i != 0)
	{
	   printAgentState("���ü����������ʧ��!");
	 }else 
	 {
	   printAgentState("���ü�����������ɹ�!");
	  } 
	return i;
}
function atUnHold()
{
	var i = obj1.tlaUnhold();
	if(i != 0)
	{
	   printAgentState("����ʧ��!");
	 }else 
	 {
	   printAgentState("�����ɹ�!");
	  } 
	return i;
}
//���� ת�� ֹͣת��
function Makecallsrc(ACallMode,strCallee,strCaller)
{
	var i = -1;
  if (ACallMode[0].checked){
	  i = obj1.tlaMakecall(1,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("����ʧ��!");
	  }else 
	 {
	   printAgentState("�Ѿ��ɹ���ʼ����!");
	  } 
  }
  else{
      i = obj1.tlaMakecall(2,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("����ʧ��!");
	  }else 
	 {
	   printAgentState("�Ѿ��ɹ���ʼ����!");
	  } 
   	  
   }
   return i;
}

function MakeCall(nCallMode,strCallee,strCaller)
{
	var i = obj1.tlaMakecall(nCallMode, strCallee, strCaller, 0);
	if(i==0)
	{
		printAgentState("�ѳɹ�����!");
	}
	else 
	{
		printAgentState("����ʧ��!");
	}
	return i;
}
//ת������_new
function Transfer_new1(ACallMode,strCallee,strCaller)
{
  var i = obj1.tlaTransfer(ACallMode,strCallee,strCaller,0);
  if (i != 0 ){
   printAgentState("ת��ʧ��!");
  }else 
 {
   printAgentState("�Ѿ��ɹ���ʼת��!");
  } 
}
function transferInline(strCallee)
{
	var i = obj1.tlaTransfer(1,strCallee,"",0);
}
//ת��
function Transfer(ACallMode,strCallee,strCaller)
{
  
  if (ACallMode[0].checked){
	  var i = obj1.tlaTransfer(1,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("ת��ʧ��!");
	  }else 
	 {
	   printAgentState("�Ѿ��ɹ���ʼת��!");
	  } 
  }
  else{
      var i = obj1.tlaTransfer(2,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("ת��ʧ��!");
	  }else 
	 {
	   printAgentState("�Ѿ��ɹ���ʼת��!");
	  } 
   
   }
}
//ֹͣת��
function Stoptransfer()
{  
  var i = obj1.tlaStoptransfer();
  if (i != 0 ){
   printAgentState("ֹͣת��ʧ��!");
  }else 
 {
   printAgentState("ֹͣת�Ƴɹ�!");
  } 
  return i;
}
//ת��ivr
function Transfertoivr(FileName)
{
  printAgentState('ivr FileName is :'+FileName);
  var i = obj1.tlaTransfertoivr(FileName,"");
  if (i != 0 ){
     printAgentState("תIVRʧ��!");
  }else 
 {
	 printAgentState("תIVR�ɹ�!");
  } 
  return i;
}
//ת��ivr,��������
function TransfertoivrAppendParam(FileName,param)
{
  //printAgentState(param);
  printAgentState('ivr FileName is :'+FileName);
  var i = obj1.tlaTransfertoivr(FileName,param);
  if (i != 0 ){
     printAgentState("תIVRʧ��!");
  }else 
 {
	 printAgentState("תIVR�ɹ�!");
  } 
  return i;
}
//Ӧ��
function laAnswer()
{
  var i = obj1.tlaAnswer();
  if (i != 0 ){
   printAgentState("Ӧ��ʧ��!");
  }else 
 {
   printAgentState("Ӧ��ɹ�!");
  } 
  return i;
}
//�һ�
function laOnhook()
{
  var i = obj1.tlaOnhook();
  if (i != 0 ){
   printAgentState("�һ�ʧ��!");
  }else 
 {
   printAgentState("�һ��ɹ�!");
  } 
  return i;
}

//����ȫ��
function threeOnHook(){
	var i = obj1.tlaOnhook();
	 if (i != 0 ){
   		
 	 }else 
 	 {
   		
  	} 
  return i;
}
//������ϯ��
function threeAgentOnHook()
{
	var agentLineid = getObj('textlineid').value;
	if(agentLineid!=-1)
		TransfertoivrAppendParam('����_�Ҷ�1��.vds',agentLineid);
}
//����ר�ҹ�
function threeExpertOnHook()
{
	var agentLineid = getObj('txtThreeLinenum').value;
	if(agentLineid!=-1)
		TransfertoivrAppendParam('����_�Ҷ�1��.vds',agentLineid);
}
//������ϯ����
function laListen(workid)
{
  var i = obj1.tlaListen(workid);
  if (i != 0 ){
   printAgentState("����ʧ��!");
  }else 
 {
   printAgentState("�����ɹ�!");
  }  
}
//ֹͣ����
function laStoplisten()
{
  var i = obj1.tlaStoplisten();
  if (i != 0 ){
   printAgentState("ֹͣ����ʧ��!");
  }else 
 {
   printAgentState("ֹͣ�����ɹ�!");
  }  
}

//����
function laPlay(nfilename)
{
  var i = obj1.tlaPlay(nfilename); 
  if (i != 0 ){
   printAgentState("����ʧ��!");
  }else 
 {
   printAgentState("�����ɹ�!");
  }  
}
//ֹͣ����
function laStopplay()
{
  var i = obj1.tlaStopplay(); 
  if (i != 0 ){
   printAgentState("ֹͣ����ʧ��!");
  }else 
 {
   printAgentState("ֹͣ�����ɹ�!");
  }  
}
//¼��
function laRecord(nfilename,time)
{
  var i = obj1.tlaRecord(nfilename,time); 
  if (i != 0 ){
   printAgentState("¼��ʧ��!");
  }else 
 {
   printAgentState("¼���ɹ�!");
  }  
}
//ֹͣ¼��
function laStoprecord()
{
  var i = obj1.tlaStoprecord(); 
  if (i != 0 ){
   printAgentState("ֹͣ¼��ʧ��!");
  }else 
 {
   printAgentState("ֹͣ¼���ɹ�!");
  }  
}
//�绰���� �Ǹ���ʵ��
function conferenceOper()
{
	printAgentState('�绰����');
}
//��ϯ��������ר��
function agentPrepCallExpert()
{
	///callcenterj_sy/callcenter/userInfo.do?method=toCustinfoMain
	var url = './prepThreeCall.jsp';
	MyOpenWin(url,'',380,118);
	//window.open('./../custinfo/custinfo.do?method=toAllExpertList','','width=550,height=450,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
}
//׼������ת��������
function prepVoicePlayForLetter()
{
	var url = "./../sys/playVoice/voiceChange.jsp"
	MyOpenWin(url,'',400,455);
}
//���絯��
function openWinIncoming(telNum)
{
	var url = "./../custinfo/openwin.do?method=toOpenwinLoad&tel="+telNum
	window.open(url,'','width=800,height=590,status=no,resizable=yes,scrollbars=no,top=200,left=280');
}
//ת��ivr
function openToIvr()
{
	var url = "./playVoice.do?method=toSelectIvrFile";
	window.open(url,'','width=600,height=620,status=yes,resizable=yes,scrollbars=yes,top=200,left=280');
}
//�򿪵绰��
function openTelBook()
{
	window.open('./../custinfo/custinfo.do?method=toCustinfoMain','','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
}

//��ϯ״̬�б� ��غ���
function getAgentState(linenum)
{
	//1���У�2��ϯ��3���壬4�������ţ�5ͨ����6���У�7ת�ƣ�8������9������10�������У�11����ͨ����
	var i = tlaQuerystate(linenum);
	if(i == -1)
	{
		return '�ߺ�'+linenum+'������ϯ״̬ʧ��!';
	}
	else if( i == 1 )
	{
		return '����';
	}
	else if( i == 2 )
	{
		return '��ϯ';
	}
		else if( i == 3 )
	{
		return '����';
	}
		else if( i == 4 )
	{
		return '��������';
	}
		else if( i == 5 )
	{
		return 'ͨ��';
	}
		else if( i == 6 )
	{
		return '����';
	}
		else if( i == 7 )
	{
		return 'ת��';
	}
		else if( i == 8 )
	{
		return '����';
	}
			else if( i == 9 )
	{
		return '����';
	}
			else if( i == 10 )
	{
		return '��������';
	}
			else if( i == 11 )
	{
		return '����ͨ��';
	}
}
//���ַ�����ʽ��Ϊ����
function str2date(str)
  {  
  var   d=null;  
  var   reg=/^(\d{4})-(\d{2})-(\d{2})   (\d{2}):(\d{2}):(\d{2})\.(\d+)$/  
  if(arr=str.match(reg))d=new   Date(Number(arr[1]),Number(arr[2])-1,Number(arr[3]),Number(arr[4]),Number(arr[5]),Number(arr[6]),Number(arr[7]))  
  return   d;  
}  
//��date��yyyy-mm-dd hh:mm:ss��ʾ date2str(new Date());  
function date2str(d)
{  
  var   ret=d.getFullYear()+"-"  
  ret+=("00"+(d.getMonth()+1)).slice(-2)+"-"  
  ret+=("00"+d.getDate()).slice(-2)+" "  
  ret+=("00"+d.getHours()).slice(-2)+":"  
  ret+=("00"+d.getMinutes()).slice(-2)+":"  
  ret+=("00"+d.getSeconds()).slice(-2) 
  return   ret; 
} 
//��date��yyyy-mm-dd hh:mm:ss��ʾ date2str(new Date());  
function date2strYMD(d)
{  
  var   ret=d.getFullYear()+"-"  
  ret+=("00"+(d.getMonth()+1)).slice(-2)+"-"  
  ret+=("00"+d.getDate()).slice(-2)+" "
  return   ret; 
}
//ת������ͼ��
function changeIncomingIcon(state, id)
{
	var oImg = getObj(id);
	var src = oImg.src;
	//state = 0 ����ͼ�� state = 1 ��ͨͼ��
	if(state == 0 && src.indexOf('dl12.gif')!=-1)
	{
		//dl11.gif ������ dl12.gif����ͨ
		src = src.replace('dl12.gif','dl11.gif');
	}
	else if(state == 1 && src.indexOf('dl11.gif') != -1)
	{
		src = src.replace('dl11.gif','dl12.gif');
	}
	oImg.src = src;
}

//ת������״̬ͼ��
function changeNetStateIcon(state, id)
{
	var oImg = getObj(id);
	var src = oImg.src;
	//state = 0 ��ͨͼ�� state = 1 �Ͽ�ͼ��
	if(state == 0 && src.indexOf('dl13.gif')!=-1)
	{
		//dl14.gif ���� dl13.gif �Ͽ�
		src = src.replace('dl13.gif','dl14.gif');
	}
	else if(state == 1 && src.indexOf('dl14.gif') != -1)
	{
		src = src.replace('dl14.gif','dl13.gif');
	}
	oImg.src = src;
}

//------------------------�����´������״̬--------------------------
/*
*******״̬
1)���� �Ҷ� ȡ�� // Ĭ������
//�ɹ�����
2)��¼ ���� ������
3)���¼ �ȴ�,������ivr���û����ۣ�ת���� �ǻҵ�
4)չͣ ����ȫ�� ��¼ʱ���ı�Ϊע��
4.1����ͨʱ �����
5)�е绰ʱ ���������� ������
6)�ӵ绰ʱ �ȴ��� ������Ҷ� ivr�� �û������� ת������
7)�Ҷ�ʱ�ָ���ʼ״̬ ��¼״̬
8)�������ȫ��
********���id ����
btnLogin ��¼    btnLaLogoff ע��
btnLaACW ��ͣ    btnLaWCA    ��ͨ
btnHold  �ȴ�    btnUnHold   ����
btnAnswer ����   btnOnHook   �Ҷ�
btnPrepCallExpert ת����
btnTelBook �绰�� btnPrepChangeToIvr תivr
btnRecvRecord ����¼�� btnOutlineAppraise *�û�����*
//���
txtOutLine btnOutCall ���
//�ں�
btnChangeInLine ת����
*/
//���а�ť�� ������������
var btnComIds = ['btnLogin', 'btnLaLogoff', 'btnLaACW', 'btnLaWCA', 'btnHold',
              'btnUnHold', 'btnAnswer', 'btnOnHook', 'btnPrepChangeToIvr',
              'btnRecvRecord', 'btnOutlineAppraise', 'btnOutCall','btnPrepCallExpert','btnVoicePlay','btnPrepConference'];//btnPrepConference
//û��������¼����ť
var btnInitComsState = ['btnLogin','btnLaACW','btnHold','btnAnswer','btnPrepChangeToIvr','btnOutlineAppraise','btnOutCall','btnPrepCallExpert','btnPrepConference','btnVoicePlay'];
//��������ɼ� �����Ӧ�Ļ���������ɼ� �Ƿ���ò�û�п���
function mutexComponentDisplay(comId)
{
	var obj = getObj(comId);
	//printAgentState(comId+":"+obj);
	obj.style.display = "inline";
	//obj.disabled = false; 
	//һ�¶Ի��ⰴť ��������һ��ť���ɼ�
	if(comId=='btnLogin')
	{
		var mObj = getObj('btnLaLogoff').style.display = "none";
	}
	else if(comId == 'btnLaLogoff')
	{
		var mObj = getObj('btnLogin').style.display = "none";
	}
	else if(comId=='btnLaACW')
	{
		var mObj = getObj('btnLaWCA').style.display = "none";
	}
	else if(comId == 'btnLaWCA')
	{
		var mObj = getObj('btnLaACW').style.display = "none";
	}
	else if(comId=='btnHold')
	{
		var mObj = getObj('btnUnHold').style.display = "none";
	}
	else if(comId == 'btnUnHold')
	{
		var mObj = getObj('btnHold').style.display = "none";
	}
	else if(comId=='btnAnswer')
	{
		var mObj = getObj('btnOnHook').style.display = "none";
	}
	else if(comId == 'btnOnHook')
	{
		var mObj = getObj('btnAnswer').style.display = "none";
	}
}
//������ϯ���Ϊ��ʼ״̬ ���տ�ʼ����ʱ��״̬ �������������
function setInitAgentPanelState()
{
	var initComs = btnInitComsState;
	for(var i=0; i<initComs.length; i++)
	{
		var comId = initComs[i];
		var obj = getObj(comId);
		mutexComponentDisplay(comId);
		obj.style.display = "inline";
		obj.disabled = false;
	}
	
} 
//�����������״̬
function setAllComponentDisabledState(state)
{
	for(var i=0; i<btnComIds.length; i++)
	{
		//printAgentState(getObj(btnComIds[i]));
		if(getObj(btnComIds[i]))
		{
			getObj(btnComIds[i]).disabled = state;
		}
		else
		{

		}
	}
}
//��������Ƿ���� ͬʱ�����Ӧ�Ļ���������ɼ�
function setComponentEnable()
{
	//���������������
	setAllComponentDisabledState(true);
	//�����������������
	for(var i=0; i<arguments.length; i++)
	{
		//����������ɼ�
		mutexComponentDisplay(arguments[i]);
		//printAgentState(arguments[i]);
		getObj(arguments[i]).disabled=false;
		getObj(arguments[i]).style.display = "inline";
	}
}
//��������Ƿ���� ͬʱ�����Ӧ�Ļ���������ɼ�
function setComponentDisable()
{
	//�������������
	setAllComponentDisabledState(false);
	//�������������������
	for(var i=0; i<arguments.length; i++)
	{
		//printAgentState(arguments[i]);
		getObj(arguments[i]).disabled=true;
		mutexComponentDisplay(arguments[i]);
	}
}
//�ɹ����� (��¼ ���� ������)
function connSuccessState()
{
	setComponentEnable('btnLogin');
}
//�����¼ʱ �ȴ�,������ivr���û����ۣ�ת���� �ǻҵ�
function clickLoginState()
{
	//btnLaACW 
	//���¼ ��¼,�ȴ�,������ivr���û����ۣ�ת����  �ǻҵ� (��¼�����ע������)
	/*
	setComponentDisable('btnHold','btnAnswer','btnPrepChangeToIvr','btnOutlineAppraise','btnChangeInLine');
	getObj('btnLaLogoff').style.display = "inline";
	getObj('btnLaLogoff').disabled = false;
	getObj('btnLogin').style.display = "none";
	*/
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//ǿ��ע��״̬
function strongLogoffState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLogin');
}
//���ע����ťʱ ��¼�ǿ��õ�
function clickLogoffState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLogin');
}
function loginFailureState()
{
	setComponentEnable('btnLaLogoff');
}
function change2LoginState()
{
	setComponentEnable('btnLogin');
}
//ǿ����ͣ(��ϯ)
function strongPauseState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaWCA');
}
//����ͣʱ pause 4)չͣ ע���� ��ͨ����
function clickPauseState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaWCA');
}
//�㿪ͨ ע���� ��ͣ���� �����
function clickGoOnState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//5)�е绰ʱ (���������� ������)
function hasTelState()
{
	setInitAgentPanelState();//btnPrepCallExpert btnPrepConference btnVoicePlay btnPrepChangeToIvr
	setComponentEnable('btnAnswer','btnOutCall','btnAnswer','btnPrepCallExpert','btnPrepConference','btnVoicePlay','btnPrepChangeToIvr');
}
//6)�ӵ绰ʱ (�ȴ��� ������Ҷ� ivr�� �û������� ת������
function acceptTelState()
{
	//printAgentState("��ϯ����״̬");
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
	//������Ҷ�
	//getObj('btnOnHook').style.display = "inline";
	//getObj('btnAnswer').style.display = "none";
}
//7)�Ҷ� (�ָ���ʼ״̬ ��¼״̬)
function hookOnState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//8)�������(ȫ��)
function netCloseState()
{
	setInitAgentPanelState();
	setAllComponentDisabledState(true);
}
//9)��� ���  �Ҷ���
function outCallState()
{
	setInitAgentPanelState();
	setComponentEnable('btnOnHook','btnPrepChangeToIvr','btnPrepCallExpert');
}
//9.1 �ں� �ں� ȫ������
function inCallState()
{
	setInitAgentPanelState();
	//setComponentEnable('btnLaLogoff','btnLaACW');
	setAllComponentDisabledState(true);
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//10�ȴ� ��ȴ�ʱ ���� �ں�������
function clickBtnHoldState()
{
	setInitAgentPanelState();
	setComponentEnable('btnUnHold','btnPrepCallExpert','btnPrepConference','btnVoicePlay','btnPrepChangeToIvr');
}
//11����
function clickBtnUnHoldState()
{
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
	//setComponentEnable('btnHold','btnPrepChangeToIvr','btnOutlineAppraise','btnChangeInLine');
	//������Ҷ�
	getObj('btnOnHook').style.display = "inline";
	getObj('btnAnswer').style.display = "none";
}
//12תivr ע����ͣ���������
function changeIvrState()
{
	//setComponentDisable('btnHold','btnLaACW','btnAnswer','btnChangeInLine');
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//13�������ťʱ
function clickBtnAnswerState()
{
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
}
//14�������гɹ�
function threeCallSuccessState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff', 'btnLaACW','btnHold','btnUnHold');
}
//15�������ɹ�
function confSuccessState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff', 'btnLaACW');
}


