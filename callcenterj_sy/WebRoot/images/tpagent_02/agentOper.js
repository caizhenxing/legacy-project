var  obj1 = document.getElementById("xx");
var hasAllAgent = 0;
//��ϯ�ߺż���
var lineArr = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16']; //������ϯ�ߺŵ�ȫ������ʱ��ֵ���ȥ
var MAXFREELENGTH = 100;
var MAXWORKIDLENGTH = 100;
var workidArr = new Array(MAXWORKIDLENGTH); //������ϯ���ŵ�ȫ��100Ӧ���㹻��
var freeWorkidArr = new Array(MAXFREELENGTH); //�������ϯ���ŵ� 100��Ŀǰ�㹻��

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
	//alert(1);
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
  
  if (obj1 == null) alert("fail")
  else{
	  var i = obj1.tlaOpen(ip);
	  if (i != 0 ){
	   alert("����ʧ��!ip is:"+ip);
	  }else 
	 {
	   alert("���ӳɹ�!");
	  }
  }
}
//�Ͽ�
function disconnect()
{
  var i = obj1.tlaClose();
  if (i != 0 ){
   alert("�Ͽ�������������ʧ��!");
  }else 
 {
   alert("�Ͽ������������ӳɹ�!");
  } 
}
//��¼ ���ڹ���״̬
function login(workid,lineid,groupid)  
{   
	if(obj1 == null)
		alert('obj1 == null error');
	//alert(lineid+":"+workid+":"+groupid);
  var i = obj1.tlaLogin(lineid,workid,groupid);  
  
  if (i != 0 ){
   alert("����ʧ��!");
  }else 
 {
   alert("����ɹ�!");
   
 }
 return i;
}
//ע��
function laLogoff()
{
  var i = obj1.tlaLogoff();
 
  if (i != 0 ){
   alert("ע��ʧ��!");
  }else 
 {
   alert("ע���ɹ�!");
  } 
  
  return i;
}
//��ϯ ���ڹ���״̬ �����ܽӵ绰��
function laACW()
{
  var i = obj1.tlaACW();
  if (i != 0 ){
   alert("��ϯʧ��!");
  }else 
 {
   alert("��ϯ�ɹ�!");
  } 
  return i;
}
//��ϯ
function laWCA()
{
  var i = obj1.tlaWCA();
  if (i != 0 ){
   alert("��ϯʧ��!");
  }else 
 {
   alert("��ϯ�ɹ�!");
  } 
}
//�ȴ�
function atHold()
{
	var i = obj1.tlaHold();
	if(i != 0)
	{
	   alert("�ȴ�ʧ��!");
	 }else 
	 {
	   alert("�ȴ��ɹ�!");
	  } 
	return i;
tlaUnhold
}
function atUnHold()
{
	var i = obj1.tlaUnhold();
	if(i != 0)
	{
	   alert("����ʧ��!");
	 }else 
	 {
	   alert("�����ɹ�!");
	  } 
	return i;
}
//���� ת�� ֹͣת��
function Makecallsrc(ACallMode,strCallee,strCaller)
{
  if (ACallMode[0].checked){
	  var i = obj1.tlaMakecall(1,strCallee,"",0);
	  if (i != 0 ){
	   alert("����ʧ��!");
	  }else 
	 {
	   alert("�Ѿ��ɹ���ʼ����!");
	  } 
  }
  else{
      var i = obj1.tlaMakecall(2,strCallee,"",0);
	  if (i != 0 ){
	   alert("����ʧ��!");
	  }else 
	 {
	   alert("�Ѿ��ɹ���ʼ����!");
	  } 
   
   }
}

function MakeCall(nCallMode,strCallee,strCaller)
{
	var i = obj1.tlaMakecall(nCallMode, strCallee, strCaller, 0);
	if(i==0)
	{
		alert("�ѳɹ�����!");
	}
	else 
	{
		alert("����ʧ��!");
	}
	return i;
}
//ת��
function Transfer(ACallMode,strCallee,strCaller)
{
  
  if (ACallMode[0].checked){
	  var i = obj1.tlaTransfer(1,strCallee,"",0);
	  if (i != 0 ){
	   alert("ת��ʧ��!");
	  }else 
	 {
	   alert("�Ѿ��ɹ���ʼת��!");
	  } 
  }
  else{
      var i = obj1.tlaTransfer(2,strCallee,"",0);
	  if (i != 0 ){
	   alert("ת��ʧ��!");
	  }else 
	 {
	   alert("�Ѿ��ɹ���ʼת��!");
	  } 
   
   }
}
//ֹͣת��
function Stoptransfer()
{  
  var i = obj1.tlaStoptransfer();
  if (i != 0 ){
   alert("ֹͣת��ʧ��!");
  }else 
 {
   alert("ֹͣת�Ƴɹ�!");
  } 
}
//ת��ivr
function Transfertoivr(FileName)
{
  var i = obj1.tlaTransfertoivr(FileName,"");
  if (i != 0 ){
     alert("תIVRʧ��!");
  }else 
 {
	 alert("תIVR�ɹ�!");
  } 
  return i;
}

//Ӧ��
function laAnswer()
{
  var i = obj1.tlaAnswer();
  if (i != 0 ){
   alert("Ӧ��ʧ��!");
  }else 
 {
   alert("Ӧ��ɹ�!");
  } 
  return i;
}
//�һ�
function laOnhook()
{
  var i = obj1.tlaOnhook();
  if (i != 0 ){
   alert("�һ�ʧ��!");
  }else 
 {
   alert("�һ��ɹ�!");
  } 
  return i;
}

//����ȫ��
function threeOnHook(){
	alert("shan fang quan gua");
	var i = obj1.tlaOnhook();
	 if (i != 0 ){
   		
 	 }else 
 	 {
   		
  	} 
  return i;
}

//������ϯ����
function laListen(workid)
{
  var i = obj1.tlaListen(workid);
  if (i != 0 ){
   alert("����ʧ��!");
  }else 
 {
   alert("�����ɹ�!");
  }  
}
//ֹͣ����
function laStoplisten()
{
  var i = obj1.tlaStoplisten();
  if (i != 0 ){
   alert("ֹͣ����ʧ��!");
  }else 
 {
   alert("ֹͣ�����ɹ�!");
  }  
}

//����
function laPlay(nfilename)
{
  var i = obj1.tlaPlay(nfilename); 
  if (i != 0 ){
   alert("����ʧ��!");
  }else 
 {
   alert("�����ɹ�!");
  }  
}
//ֹͣ����
function laStopplay()
{
  var i = obj1.tlaStopplay(); 
  if (i != 0 ){
   alert("ֹͣ����ʧ��!");
  }else 
 {
   alert("ֹͣ�����ɹ�!");
  }  
}
//¼��
function laRecord(nfilename,time)
{
  var i = obj1.tlaRecord(nfilename,time); 
  if (i != 0 ){
   alert("¼��ʧ��!");
  }else 
 {
   alert("¼���ɹ�!");
  }  
}
//ֹͣ¼��
function laStoprecord()
{
  var i = obj1.tlaStoprecord(); 
  if (i != 0 ){
   alert("ֹͣ¼��ʧ��!");
  }else 
 {
   alert("ֹͣ¼���ɹ�!");
  }  
}
//�绰����
function conferenceOper()
{
	alert('�绰����');
}

//������������
function setComponentEnable()
{
	//���������������
	//do...
	//�����������������
	for(var i=0; i<arguments.length; i++)
	{
		alert(arguments[i]);
	}
}
function setComponentDisable()
{
	//�������������
	//do...
	//�������������������
	for(var i=0; i<arguments.length; i++)
	{
		alert(arguments[i]);
	}
}

//���絯��
function openWinIncoming(telNum)
{
	var url = "./custinfo/openwin.do?method=toOpenwinLoad&tel="+telNum
	window.open(url,'','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
}
//ת��ivr
function openToIvr()
{
	var url = "./selectIvr.jsp";
	window.open(url,'','width=800,height=600,status=yes,resizable=yes,scrollbars=yes,top=200,left=280');
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
  ret+=("00"+d.getSeconds()).slice(-2)+"."  
  return   ret+d.getMilliseconds()  
} 
