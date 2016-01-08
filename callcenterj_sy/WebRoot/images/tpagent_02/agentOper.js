var  obj1 = document.getElementById("xx");
var hasAllAgent = 0;
//座席线号集合
var lineArr = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16']; //这是座席线号的全集加载时将值添进去
var MAXFREELENGTH = 100;
var MAXWORKIDLENGTH = 100;
var workidArr = new Array(MAXWORKIDLENGTH); //这是座席工号的全集100应该足够了
var freeWorkidArr = new Array(MAXFREELENGTH); //存空闲座席工号的 100个目前足够了

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
	//alert(1);
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
  
  if (obj1 == null) alert("fail")
  else{
	  var i = obj1.tlaOpen(ip);
	  if (i != 0 ){
	   alert("连接失败!ip is:"+ip);
	  }else 
	 {
	   alert("连接成功!");
	  }
  }
}
//断开
function disconnect()
{
  var i = obj1.tlaClose();
  if (i != 0 ){
   alert("断开服务器的连接失败!");
  }else 
 {
   alert("断开服务器的连接成功!");
  } 
}
//登录 处于工作状态
function login(workid,lineid,groupid)  
{   
	if(obj1 == null)
		alert('obj1 == null error');
	//alert(lineid+":"+workid+":"+groupid);
  var i = obj1.tlaLogin(lineid,workid,groupid);  
  
  if (i != 0 ){
   alert("登入失败!");
  }else 
 {
   alert("登入成功!");
   
 }
 return i;
}
//注销
function laLogoff()
{
  var i = obj1.tlaLogoff();
 
  if (i != 0 ){
   alert("注销失败!");
  }else 
 {
   alert("注销成功!");
  } 
  
  return i;
}
//离席 处于工作状态 但不能接电话了
function laACW()
{
  var i = obj1.tlaACW();
  if (i != 0 ){
   alert("离席失败!");
  }else 
 {
   alert("离席成功!");
  } 
  return i;
}
//复席
function laWCA()
{
  var i = obj1.tlaWCA();
  if (i != 0 ){
   alert("复席失败!");
  }else 
 {
   alert("复席成功!");
  } 
}
//等待
function atHold()
{
	var i = obj1.tlaHold();
	if(i != 0)
	{
	   alert("等待失败!");
	 }else 
	 {
	   alert("等待成功!");
	  } 
	return i;
tlaUnhold
}
function atUnHold()
{
	var i = obj1.tlaUnhold();
	if(i != 0)
	{
	   alert("继续失败!");
	 }else 
	 {
	   alert("继续成功!");
	  } 
	return i;
}
//呼叫 转移 停止转移
function Makecallsrc(ACallMode,strCallee,strCaller)
{
  if (ACallMode[0].checked){
	  var i = obj1.tlaMakecall(1,strCallee,"",0);
	  if (i != 0 ){
	   alert("呼叫失败!");
	  }else 
	 {
	   alert("已经成功开始呼叫!");
	  } 
  }
  else{
      var i = obj1.tlaMakecall(2,strCallee,"",0);
	  if (i != 0 ){
	   alert("呼叫失败!");
	  }else 
	 {
	   alert("已经成功开始呼叫!");
	  } 
   
   }
}

function MakeCall(nCallMode,strCallee,strCaller)
{
	var i = obj1.tlaMakecall(nCallMode, strCallee, strCaller, 0);
	if(i==0)
	{
		alert("已成功呼叫!");
	}
	else 
	{
		alert("呼叫失败!");
	}
	return i;
}
//转移
function Transfer(ACallMode,strCallee,strCaller)
{
  
  if (ACallMode[0].checked){
	  var i = obj1.tlaTransfer(1,strCallee,"",0);
	  if (i != 0 ){
	   alert("转移失败!");
	  }else 
	 {
	   alert("已经成功开始转移!");
	  } 
  }
  else{
      var i = obj1.tlaTransfer(2,strCallee,"",0);
	  if (i != 0 ){
	   alert("转移失败!");
	  }else 
	 {
	   alert("已经成功开始转移!");
	  } 
   
   }
}
//停止转移
function Stoptransfer()
{  
  var i = obj1.tlaStoptransfer();
  if (i != 0 ){
   alert("停止转移失败!");
  }else 
 {
   alert("停止转移成功!");
  } 
}
//转到ivr
function Transfertoivr(FileName)
{
  var i = obj1.tlaTransfertoivr(FileName,"");
  if (i != 0 ){
     alert("转IVR失败!");
  }else 
 {
	 alert("转IVR成功!");
  } 
  return i;
}

//应答
function laAnswer()
{
  var i = obj1.tlaAnswer();
  if (i != 0 ){
   alert("应答失败!");
  }else 
 {
   alert("应答成功!");
  } 
  return i;
}
//挂机
function laOnhook()
{
  var i = obj1.tlaOnhook();
  if (i != 0 ){
   alert("挂机失败!");
  }else 
 {
   alert("挂机成功!");
  } 
  return i;
}

//三方全挂
function threeOnHook(){
	alert("shan fang quan gua");
	var i = obj1.tlaOnhook();
	 if (i != 0 ){
   		
 	 }else 
 	 {
   		
  	} 
  return i;
}

//监听座席工号
function laListen(workid)
{
  var i = obj1.tlaListen(workid);
  if (i != 0 ){
   alert("监听失败!");
  }else 
 {
   alert("监听成功!");
  }  
}
//停止监听
function laStoplisten()
{
  var i = obj1.tlaStoplisten();
  if (i != 0 ){
   alert("停止监听失败!");
  }else 
 {
   alert("停止监听成功!");
  }  
}

//放音
function laPlay(nfilename)
{
  var i = obj1.tlaPlay(nfilename); 
  if (i != 0 ){
   alert("放音失败!");
  }else 
 {
   alert("放音成功!");
  }  
}
//停止放音
function laStopplay()
{
  var i = obj1.tlaStopplay(); 
  if (i != 0 ){
   alert("停止放音失败!");
  }else 
 {
   alert("停止放音成功!");
  }  
}
//录音
function laRecord(nfilename,time)
{
  var i = obj1.tlaRecord(nfilename,time); 
  if (i != 0 ){
   alert("录音失败!");
  }else 
 {
   alert("录音成功!");
  }  
}
//停止录音
function laStoprecord()
{
  var i = obj1.tlaStoprecord(); 
  if (i != 0 ){
   alert("停止录音失败!");
  }else 
 {
   alert("停止录音成功!");
  }  
}
//电话会议
function conferenceOper()
{
	alert('电话会议');
}

//设置主件可用
function setComponentEnable()
{
	//设置组件都不可用
	//do...
	//给定参数的组件可用
	for(var i=0; i<arguments.length; i++)
	{
		alert(arguments[i]);
	}
}
function setComponentDisable()
{
	//设置组件都可用
	//do...
	//给定参数的组件不可用
	for(var i=0; i<arguments.length; i++)
	{
		alert(arguments[i]);
	}
}

//来电弹屏
function openWinIncoming(telNum)
{
	var url = "./custinfo/openwin.do?method=toOpenwinLoad&tel="+telNum
	window.open(url,'','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
}
//转到ivr
function openToIvr()
{
	var url = "./selectIvr.jsp";
	window.open(url,'','width=800,height=600,status=yes,resizable=yes,scrollbars=yes,top=200,left=280');
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
  ret+=("00"+d.getSeconds()).slice(-2)+"."  
  return   ret+d.getMilliseconds()  
} 
