//来电记录
function getDayIncommingNote(id,agent,incommingNote)
{

	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=IncommingNote';

	postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
}
//日资讯量
function getDayIncommingVol(id,agent,incommingVol)
{
	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=DayQuestionVolume';
	postXmlHttp(url,"addV2Txt('"+id+"')",'doNo()');
}
//服务总时
function getWorkSumTime(id,agent,incommingVol)
{
	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=WorkSumTime';
	postXmlHttp(url,"addV2Txt('"+id+"')",'doNo()');
}
//本次时常
function getCurTalkTime(incommingTime,txtId)
{
	var endTime = new Date();
	if(incommingTime)
	{
		var betweenTimeSs = (endTime.getTime()-incommingTime.getTime())/1000; //秒数
		document.getElementById(txtId).value=betweenTimeSs+"秒";
	}
}
//座席呼三方挂断专家
function getHoldThreeExpert(lineNum,threeNum)
{
	var url = './../searchOneFiledServlet?agentNum=0&cName=HoldThreeExpert&lineNum='+lineNum;
	postXmlHttp(url,"doNo()",'doNo()');
}
function addV2Txt(id)
{
	var oT = document.getElementById(id);
	var res_db = _xmlHttpRequestObj.responseText;
	oT.value = res_db;
}

function addOption2Select(id)
{
	var slt = document.getElementById(id);
	var res_db = _xmlHttpRequestObj.responseText;
	//清空slt
	var length = slt.length;
	if(length>0)
	{
		for(var i=1; i<length; i++)
		{
			slt.options[1] = null;
		}
	}
	var sArr = res_db.split(":");
	for(var i=0; i<sArr.length; i++)
	{
	
		//增加一个option
		slt.options[slt.length] = new Option(sArr[i], sArr[i]);
	}
}

//#####################呼叫专家成功执行数据库update操作######################
/**
* threeCall 外线电话号
* threePort 外线通道号
*/
function updateEasyStateInfo(threeCall,threePort)
{
	var outChannelLine = obj1.tlaGetuserdata();
	//三方端口号赋值
	document.getElementById('textAgentCallThreePort').value=outChannelLine;
	if(outChannelLine!=-1)
	{
		threePort = outChannelLine;
		var url = './../searchOneFiledServlet?agentNum=allEmpty&cName=OperEasyStateInfo&incommingCall='+callerImcomming+'&threeCall='+threeCall+'&threePort='+threePort;
		postXmlHttp(url,"doWithEasyStateInfo()",'doNo()');
	}
}
function doWithEasyStateInfo()
{
	//alert('处理三方通话成功状态!');
}
//#####################座席大面板 话务实况#####################
function showHuaWuShiKuang(tblId)
{
	//searchOneFiledServlet 要求agnetNum不能为空临时传一个无用座席号
	//alert('取服务总时'+id+":"+agent+":"+incommingVol);AgentTellInfoService
	var url = './../../searchOneFiledServlet?agentNum=allEmpty&cName=AgentTellInfo';
	postXmlHttp(url,"doWithHuaWuShiKuang('"+tblId+"')",'doNo()');
}
//出示座席大面板 话务实况
function doWithHuaWuShiKuang(tblId)
{
	//ajax 返回的字符串
	var res_db = _xmlHttpRequestObj.responseText;
	//alert(res_db);
	//表格id,返回字符串
	parseAddTrs2Table(tblId,res_db);
}
//#############################################################
//来电来电弹屏根据大类得到专家信息
function getClassExpertsInfo(id,agent,expertType)
{
	if(expertType!=0)
	{
		var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=ShowClassExpertors&expertType='+expertType;
		
		postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
	}
}
//来电来电弹屏根据大类得到专家信息
function getBClassExpertsInfo(id,agent,expertType,basePath)
{
	if(expertType!=0)
	{
		var url = basePath+'searchOneFiledServlet?agentNum='+agent+'&cName=ShowClassExpertors&expertType='+expertType;
		postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
	}
}