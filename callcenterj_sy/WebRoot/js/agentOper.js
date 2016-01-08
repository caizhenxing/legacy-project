var  obj1 = document.getElementById("xx");
var hasAllAgent = 0;
//function printAgentState(msg) {
//	getObj("txtAgentState").value = msg;
//}
//座席线号集合
var lineArr = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16']; //这是座席线号的全集加载时将值添进去
var MAXFREELENGTH = 100;
var MAXWORKIDLENGTH = 100;
var workidArr = new Array(MAXWORKIDLENGTH); //这是座席工号的全集100应该足够了
var freeWorkidArr = new Array(MAXFREELENGTH); //存空闲座席工号的 100个目前足够了
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
//得到所有的空闲工号数组 
function getFreeWorkids()
{
	clearFreeWorkids();
	var freeAgentNum = obj1.tlaQueryinfo(2);
	//printAgentState(1);
	for(var i=0; i<freeAgentNum; i++)
	{
		//空闲
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
//得到登录座席数量
function getHasLoginAgentNum()
{
	return obj1.tlaQueryinfo(1);
}
//根据序号的到座席状态
function getAgentStateMonitorByNum(num)
{
	var arr = ['未登录','空闲','离席', '振铃', '播报工号','通话','呼叫','转移','监听','放音','三方呼叫','三方通话'];
	var v = obj1.tlaQuerystate(num);
	if(v < 0 )
	{
		v = 0;
	}

	return arr[v];
}
//根据序号的到座席工号 -1没找到
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
//将内线列表以文本形式解析给座席看*******************************************************************
function createTxtForInnerLine()
{
	var numStrs = '';
	//do.....
	var freeWarr = getFreeWorkids();
	for(var i=0; i<MAXWORKIDLENGTH; i++)
	{
		var workid = freeWarr[i];
		//有错误返回
		if(workid == -1)
			return numStrs;
		//增加一个option
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
//将内线列表以option形式显示给座席看
function createOptionForInnerLine(selectId)
{
	if(hasAllAgent==0)
	{
		//do.....
		var slt = getObj(selectId);
		if(slt)
		{
			clearSelect(slt); //清空
			var freeWarr = getFreeWorkids();
			for(var i=0; i<MAXWORKIDLENGTH; i++)
			{
				var workid = freeWarr[i];
				//有错误返回
				if(workid == -1)
					return;
				//增加一个option
				slt.options[slt.length] = new Option(workid, workid);
			}
		}
	}
	hasAllAgent++;
}


//座席工号集合
//连接
function connect(ip)
{
  printAgentState(ip);
  if (obj1 == null) printAgentState("fail");
  else{
	  var i = obj1.tlaOpen(ip);
	  if (i != 0 ){
	   changeNetStateIcon(1, 'imgNetState');
	   printAgentState("链接失败")
	  }else 
	 {
	   printAgentState("连接成功!");
	   changeNetStateIcon(0, 'imgNetState');
	   connSuccessState();
	  }
	  return i;
  }
}
//断开
function disconnect()
{
  var i = obj1.tlaClose();
  if (i != 0 ){
   printAgentState("断开服务器的连接失败!");
  }else 
 {
   printAgentState("断开服务器的连接成功!");
   changeNetStateIcon(1, 'imgNetState');
  } 
}
//登录 处于工作状态
function login(workid,lineid,groupid)  
{   
	if(obj1 == null)
	{
		printAgentState('obj1 == null error');
		alert("加载座席端程序出错!");
	}
printAgentState(lineid+":"+workid+":"+groupid);
obj1.tlaClose();
	 obj1.tlaOpen(serverIp);
obj1.tlaKill(1,lineid);
obj1.tlaKill(2,workid);
  var i = obj1.tlaLogin(lineid,workid,0);  
  
  if (i != 0 ){
   printAgentState("登入失败!");
   loginFailureState();
  }else 
 {
   printAgentState("登入成功!");
 }
 return i;
}
//注销
function laLogoff()
{
  var i = obj1.tlaLogoff();
 
  if (i != 0 ){
   printAgentState("注销失败!");
   change2LoginState();
  }else 
 {
   printAgentState("注销成功!");
   change2LoginState();
  } 
  
  return i;
}
//离席 处于工作状态 但不能接电话了
function laACW()
{
  var i = obj1.tlaACW();
  if (i != 0 ){
   printAgentState("离席失败!");
   connSuccessState()
  }else 
 {
   printAgentState("离席成功!");
   clickPauseState();
  } 
  return i;
}
//复席
function laWCA()
{
  var i = obj1.tlaWCA();
  if (i != 0 ){
   printAgentState("复席失败!");
   connSuccessState()
  }else 
 {
   printAgentState("复席成功!");
  } 
  return i;
}
//等待
function atHold()
{
	var i = obj1.tlaHold();
	if(i != 0)
	{
	   printAgentState("等待失败!");
	   connSuccessState();
	 }else 
	 {
	   printAgentState("等待成功!");
	  } 
	return i;
}
function atTlaActivate()
{
	var i = obj1.tlaActivate();
	if(i != 0)
	{
	   printAgentState("调用激活进入三方失败!");
	 }else 
	 {
	   printAgentState("调用激活进入三方成功!");
	  } 
	return i;
}
function atUnHold()
{
	var i = obj1.tlaUnhold();
	if(i != 0)
	{
	   printAgentState("继续失败!");
	 }else 
	 {
	   printAgentState("继续成功!");
	  } 
	return i;
}
//呼叫 转移 停止转移
function Makecallsrc(ACallMode,strCallee,strCaller)
{
	var i = -1;
  if (ACallMode[0].checked){
	  i = obj1.tlaMakecall(1,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("呼叫失败!");
	  }else 
	 {
	   printAgentState("已经成功开始呼叫!");
	  } 
  }
  else{
      i = obj1.tlaMakecall(2,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("呼叫失败!");
	  }else 
	 {
	   printAgentState("已经成功开始呼叫!");
	  } 
   	  
   }
   return i;
}

function MakeCall(nCallMode,strCallee,strCaller)
{
	var i = obj1.tlaMakecall(nCallMode, strCallee, strCaller, 0);
	if(i==0)
	{
		printAgentState("已成功呼叫!");
	}
	else 
	{
		printAgentState("呼叫失败!");
	}
	return i;
}
//转移三方_new
function Transfer_new1(ACallMode,strCallee,strCaller)
{
  var i = obj1.tlaTransfer(ACallMode,strCallee,strCaller,0);
  if (i != 0 ){
   printAgentState("转移失败!");
  }else 
 {
   printAgentState("已经成功开始转移!");
  } 
}
function transferInline(strCallee)
{
	var i = obj1.tlaTransfer(1,strCallee,"",0);
}
//转移
function Transfer(ACallMode,strCallee,strCaller)
{
  
  if (ACallMode[0].checked){
	  var i = obj1.tlaTransfer(1,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("转移失败!");
	  }else 
	 {
	   printAgentState("已经成功开始转移!");
	  } 
  }
  else{
      var i = obj1.tlaTransfer(2,strCallee,"",0);
	  if (i != 0 ){
	   printAgentState("转移失败!");
	  }else 
	 {
	   printAgentState("已经成功开始转移!");
	  } 
   
   }
}
//停止转移
function Stoptransfer()
{  
  var i = obj1.tlaStoptransfer();
  if (i != 0 ){
   printAgentState("停止转移失败!");
  }else 
 {
   printAgentState("停止转移成功!");
  } 
  return i;
}
//转到ivr
function Transfertoivr(FileName)
{
  printAgentState('ivr FileName is :'+FileName);
  var i = obj1.tlaTransfertoivr(FileName,"");
  if (i != 0 ){
     printAgentState("转IVR失败!");
  }else 
 {
	 printAgentState("转IVR成功!");
  } 
  return i;
}
//转到ivr,带参数的
function TransfertoivrAppendParam(FileName,param)
{
  //printAgentState(param);
  printAgentState('ivr FileName is :'+FileName);
  var i = obj1.tlaTransfertoivr(FileName,param);
  if (i != 0 ){
     printAgentState("转IVR失败!");
  }else 
 {
	 printAgentState("转IVR成功!");
  } 
  return i;
}
//应答
function laAnswer()
{
  var i = obj1.tlaAnswer();
  if (i != 0 ){
   printAgentState("应答失败!");
  }else 
 {
   printAgentState("应答成功!");
  } 
  return i;
}
//挂机
function laOnhook()
{
  var i = obj1.tlaOnhook();
  if (i != 0 ){
   printAgentState("挂机失败!");
  }else 
 {
   printAgentState("挂机成功!");
  } 
  return i;
}

//三方全挂
function threeOnHook(){
	var i = obj1.tlaOnhook();
	 if (i != 0 ){
   		
 	 }else 
 	 {
   		
  	} 
  return i;
}
//三方座席挂
function threeAgentOnHook()
{
	var agentLineid = getObj('textlineid').value;
	if(agentLineid!=-1)
		TransfertoivrAppendParam('三方_挂断1方.vds',agentLineid);
}
//三方专家挂
function threeExpertOnHook()
{
	var agentLineid = getObj('txtThreeLinenum').value;
	if(agentLineid!=-1)
		TransfertoivrAppendParam('三方_挂断1方.vds',agentLineid);
}
//监听座席工号
function laListen(workid)
{
  var i = obj1.tlaListen(workid);
  if (i != 0 ){
   printAgentState("监听失败!");
  }else 
 {
   printAgentState("监听成功!");
  }  
}
//停止监听
function laStoplisten()
{
  var i = obj1.tlaStoplisten();
  if (i != 0 ){
   printAgentState("停止监听失败!");
  }else 
 {
   printAgentState("停止监听成功!");
  }  
}

//放音
function laPlay(nfilename)
{
  var i = obj1.tlaPlay(nfilename); 
  if (i != 0 ){
   printAgentState("放音失败!");
  }else 
 {
   printAgentState("放音成功!");
  }  
}
//停止放音
function laStopplay()
{
  var i = obj1.tlaStopplay(); 
  if (i != 0 ){
   printAgentState("停止放音失败!");
  }else 
 {
   printAgentState("停止放音成功!");
  }  
}
//录音
function laRecord(nfilename,time)
{
  var i = obj1.tlaRecord(nfilename,time); 
  if (i != 0 ){
   printAgentState("录音失败!");
  }else 
 {
   printAgentState("录音成功!");
  }  
}
//停止录音
function laStoprecord()
{
  var i = obj1.tlaStoprecord(); 
  if (i != 0 ){
   printAgentState("停止录音失败!");
  }else 
 {
   printAgentState("停止录音成功!");
  }  
}
//电话会议 是个空实现
function conferenceOper()
{
	printAgentState('电话会议');
}
//座席呼叫三方专家
function agentPrepCallExpert()
{
	///callcenterj_sy/callcenter/userInfo.do?method=toCustinfoMain
	var url = './prepThreeCall.jsp';
	MyOpenWin(url,'',380,118);
	//window.open('./../custinfo/custinfo.do?method=toAllExpertList','','width=550,height=450,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
}
//准备文字转语音播放
function prepVoicePlayForLetter()
{
	var url = "./../sys/playVoice/voiceChange.jsp"
	MyOpenWin(url,'',400,455);
}
//来电弹屏
function openWinIncoming(telNum)
{
	var url = "./../custinfo/openwin.do?method=toOpenwinLoad&tel="+telNum
	window.open(url,'','width=800,height=590,status=no,resizable=yes,scrollbars=no,top=200,left=280');
}
//转到ivr
function openToIvr()
{
	var url = "./playVoice.do?method=toSelectIvrFile";
	window.open(url,'','width=600,height=620,status=yes,resizable=yes,scrollbars=yes,top=200,left=280');
}
//打开电话薄
function openTelBook()
{
	window.open('./../custinfo/custinfo.do?method=toCustinfoMain','','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
}

//座席状态列表 相关函数
function getAgentState(linenum)
{
	//1空闲，2离席，3振铃，4播报工号，5通话，6呼叫，7转移，8监听，9放音，10三方呼叫，11三方通话。
	var i = tlaQuerystate(linenum);
	if(i == -1)
	{
		return '线号'+linenum+'查找座席状态失败!';
	}
	else if( i == 1 )
	{
		return '空闲';
	}
	else if( i == 2 )
	{
		return '离席';
	}
		else if( i == 3 )
	{
		return '振铃';
	}
		else if( i == 4 )
	{
		return '播报工号';
	}
		else if( i == 5 )
	{
		return '通话';
	}
		else if( i == 6 )
	{
		return '呼叫';
	}
		else if( i == 7 )
	{
		return '转移';
	}
		else if( i == 8 )
	{
		return '监听';
	}
			else if( i == 9 )
	{
		return '放音';
	}
			else if( i == 10 )
	{
		return '三方呼叫';
	}
			else if( i == 11 )
	{
		return '三方通话';
	}
}
//将字符串格式化为日期
function str2date(str)
  {  
  var   d=null;  
  var   reg=/^(\d{4})-(\d{2})-(\d{2})   (\d{2}):(\d{2}):(\d{2})\.(\d+)$/  
  if(arr=str.match(reg))d=new   Date(Number(arr[1]),Number(arr[2])-1,Number(arr[3]),Number(arr[4]),Number(arr[5]),Number(arr[6]),Number(arr[7]))  
  return   d;  
}  
//将date以yyyy-mm-dd hh:mm:ss显示 date2str(new Date());  
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
//将date以yyyy-mm-dd hh:mm:ss显示 date2str(new Date());  
function date2strYMD(d)
{  
  var   ret=d.getFullYear()+"-"  
  ret+=("00"+(d.getMonth()+1)).slice(-2)+"-"  
  ret+=("00"+d.getDate()).slice(-2)+" "
  return   ret; 
}
//转换来电图标
function changeIncomingIcon(state, id)
{
	var oImg = getObj(id);
	var src = oImg.src;
	//state = 0 来电图标 state = 1 普通图标
	if(state == 0 && src.indexOf('dl12.gif')!=-1)
	{
		//dl11.gif 是来电 dl12.gif是普通
		src = src.replace('dl12.gif','dl11.gif');
	}
	else if(state == 1 && src.indexOf('dl11.gif') != -1)
	{
		src = src.replace('dl11.gif','dl12.gif');
	}
	oImg.src = src;
}

//转换网络状态图标
function changeNetStateIcon(state, id)
{
	var oImg = getObj(id);
	var src = oImg.src;
	//state = 0 接通图标 state = 1 断开图标
	if(state == 0 && src.indexOf('dl13.gif')!=-1)
	{
		//dl14.gif 链接 dl13.gif 断开
		src = src.replace('dl13.gif','dl14.gif');
	}
	else if(state == 1 && src.indexOf('dl14.gif') != -1)
	{
		src = src.replace('dl14.gif','dl13.gif');
	}
	oImg.src = src;
}

//------------------------这往下处理面板状态--------------------------
/*
*******状态
1)链接 挂断 取消 // 默认链接
//成功连接
2)登录 可用 其他灰
3)点登录 等待,接听，ivr，用户评价，转内线 是灰的
4)展停 其他全灰 登录时亮的变为注销
4.1）开通时 外乎亮
5)有电话时 接听是亮的 外乎变灰
6)接电话时 等待亮 接听便挂断 ivr亮 用户评价量 转内线亮
7)挂断时恢复初始状态 登录状态
8)网络断了全灰
********组件id 名称
btnLogin 登录    btnLaLogoff 注销
btnLaACW 暂停    btnLaWCA    开通
btnHold  等待    btnUnHold   继续
btnAnswer 接听   btnOnHook   挂断
btnPrepCallExpert 转三方
btnTelBook 电话簿 btnPrepChangeToIvr 转ivr
btnRecvRecord 收听录音 btnOutlineAppraise *用户评价*
//外呼
txtOutLine btnOutCall 外呼
//内呼
btnChangeInLine 转内线
*/
//所有按钮的 不包括其他的
var btnComIds = ['btnLogin', 'btnLaLogoff', 'btnLaACW', 'btnLaWCA', 'btnHold',
              'btnUnHold', 'btnAnswer', 'btnOnHook', 'btnPrepChangeToIvr',
              'btnRecvRecord', 'btnOutlineAppraise', 'btnOutCall','btnPrepCallExpert','btnVoicePlay','btnPrepConference'];//btnPrepConference
//没包括收听录音按钮
var btnInitComsState = ['btnLogin','btnLaACW','btnHold','btnAnswer','btnPrepChangeToIvr','btnOutlineAppraise','btnOutCall','btnPrepCallExpert','btnPrepConference','btnVoicePlay'];
//设置组件可见 与其对应的互斥组件不可见 是否可用并没有考虑
function mutexComponentDisplay(comId)
{
	var obj = getObj(comId);
	//printAgentState(comId+":"+obj);
	obj.style.display = "inline";
	//obj.disabled = false; 
	//一下对互斥按钮 设置其另一按钮不可见
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
//设置座席面板为初始状态 即刚开始链接时的状态 且所有组件可用
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
//设置所有组件状态
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
//设置组件是否可用 同时与其对应的互斥组件不可见
function setComponentEnable()
{
	//设置组件都不可用
	setAllComponentDisabledState(true);
	//给定参数的组件可用
	for(var i=0; i<arguments.length; i++)
	{
		//互次组件不可见
		mutexComponentDisplay(arguments[i]);
		//printAgentState(arguments[i]);
		getObj(arguments[i]).disabled=false;
		getObj(arguments[i]).style.display = "inline";
	}
}
//设置组件是否可用 同时与其对应的互斥组件不可见
function setComponentDisable()
{
	//设置组件都可用
	setAllComponentDisabledState(false);
	//给定参数的组件不可用
	for(var i=0; i<arguments.length; i++)
	{
		//printAgentState(arguments[i]);
		getObj(arguments[i]).disabled=true;
		mutexComponentDisplay(arguments[i]);
	}
}
//成功连接 (登录 可用 其他灰)
function connSuccessState()
{
	setComponentEnable('btnLogin');
}
//点击登录时 等待,接听，ivr，用户评价，转内线 是灰的
function clickLoginState()
{
	//btnLaACW 
	//点登录 登录,等待,接听，ivr，用户评价，转内线  是灰的 (登录互斥的注销变亮)
	/*
	setComponentDisable('btnHold','btnAnswer','btnPrepChangeToIvr','btnOutlineAppraise','btnChangeInLine');
	getObj('btnLaLogoff').style.display = "inline";
	getObj('btnLaLogoff').disabled = false;
	getObj('btnLogin').style.display = "none";
	*/
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//强制注销状态
function strongLogoffState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLogin');
}
//点击注销按钮时 登录是可用的
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
//强制暂停(离席)
function strongPauseState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaWCA');
}
//点暂停时 pause 4)展停 注销亮 开通变量
function clickPauseState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaWCA');
}
//点开通 注销亮 暂停变量 外呼亮
function clickGoOnState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//5)有电话时 (接听是亮的 外乎变灰)
function hasTelState()
{
	setInitAgentPanelState();//btnPrepCallExpert btnPrepConference btnVoicePlay btnPrepChangeToIvr
	setComponentEnable('btnAnswer','btnOutCall','btnAnswer','btnPrepCallExpert','btnPrepConference','btnVoicePlay','btnPrepChangeToIvr');
}
//6)接电话时 (等待亮 接听便挂断 ivr亮 用户评价量 转内线亮
function acceptTelState()
{
	//printAgentState("座席接听状态");
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
	//接听变挂断
	//getObj('btnOnHook').style.display = "inline";
	//getObj('btnAnswer').style.display = "none";
}
//7)挂断 (恢复初始状态 登录状态)
function hookOnState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//8)网络断了(全灰)
function netCloseState()
{
	setInitAgentPanelState();
	setAllComponentDisabledState(true);
}
//9)外呼 外呼  挂断亮
function outCallState()
{
	setInitAgentPanelState();
	setComponentEnable('btnOnHook','btnPrepChangeToIvr','btnPrepCallExpert');
}
//9.1 内呼 内呼 全不可用
function inCallState()
{
	setInitAgentPanelState();
	//setComponentEnable('btnLaLogoff','btnLaACW');
	setAllComponentDisabledState(true);
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//10等待 点等待时 继续 内呼是亮的
function clickBtnHoldState()
{
	setInitAgentPanelState();
	setComponentEnable('btnUnHold','btnPrepCallExpert','btnPrepConference','btnVoicePlay','btnPrepChangeToIvr');
}
//11继续
function clickBtnUnHoldState()
{
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
	//setComponentEnable('btnHold','btnPrepChangeToIvr','btnOutlineAppraise','btnChangeInLine');
	//接听变挂断
	getObj('btnOnHook').style.display = "inline";
	getObj('btnAnswer').style.display = "none";
}
//12转ivr 注销暂停外呼是亮的
function changeIvrState()
{
	//setComponentDisable('btnHold','btnLaACW','btnAnswer','btnChangeInLine');
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff','btnLaACW','btnOutCall');
}
//13点接听按钮时
function clickBtnAnswerState()
{
	setInitAgentPanelState();
	setComponentEnable('btnHold','btnOnHook','btnPrepChangeToIvr','btnVoicePlay','btnOutlineAppraise','btnPrepCallExpert','btnPrepConference');
}
//14三方呼叫成功
function threeCallSuccessState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff', 'btnLaACW','btnHold','btnUnHold');
}
//15发起会议成功
function confSuccessState()
{
	setInitAgentPanelState();
	setComponentEnable('btnLaLogoff', 'btnLaACW');
}


