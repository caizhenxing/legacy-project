//�����¼
function getDayIncommingNote(id,agent,incommingNote)
{

	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=IncommingNote';

	postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
}
//����Ѷ��
function getDayIncommingVol(id,agent,incommingVol)
{
	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=DayQuestionVolume';
	postXmlHttp(url,"addV2Txt('"+id+"')",'doNo()');
}
//������ʱ
function getWorkSumTime(id,agent,incommingVol)
{
	var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=WorkSumTime';
	postXmlHttp(url,"addV2Txt('"+id+"')",'doNo()');
}
//����ʱ��
function getCurTalkTime(incommingTime,txtId)
{
	var endTime = new Date();
	if(incommingTime)
	{
		var betweenTimeSs = (endTime.getTime()-incommingTime.getTime())/1000; //����
		document.getElementById(txtId).value=betweenTimeSs+"��";
	}
}
//��ϯ�������Ҷ�ר��
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
	//���slt
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
	
		//����һ��option
		slt.options[slt.length] = new Option(sArr[i], sArr[i]);
	}
}

//#####################����ר�ҳɹ�ִ�����ݿ�update����######################
/**
* threeCall ���ߵ绰��
* threePort ����ͨ����
*/
function updateEasyStateInfo(threeCall,threePort)
{
	var outChannelLine = obj1.tlaGetuserdata();
	//�����˿ںŸ�ֵ
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
	//alert('��������ͨ���ɹ�״̬!');
}
//#####################��ϯ����� ����ʵ��#####################
function showHuaWuShiKuang(tblId)
{
	//searchOneFiledServlet Ҫ��agnetNum����Ϊ����ʱ��һ��������ϯ��
	//alert('ȡ������ʱ'+id+":"+agent+":"+incommingVol);AgentTellInfoService
	var url = './../../searchOneFiledServlet?agentNum=allEmpty&cName=AgentTellInfo';
	postXmlHttp(url,"doWithHuaWuShiKuang('"+tblId+"')",'doNo()');
}
//��ʾ��ϯ����� ����ʵ��
function doWithHuaWuShiKuang(tblId)
{
	//ajax ���ص��ַ���
	var res_db = _xmlHttpRequestObj.responseText;
	//alert(res_db);
	//���id,�����ַ���
	parseAddTrs2Table(tblId,res_db);
}
//#############################################################
//�������絯�����ݴ���õ�ר����Ϣ
function getClassExpertsInfo(id,agent,expertType)
{
	if(expertType!=0)
	{
		var url = './../searchOneFiledServlet?agentNum='+agent+'&cName=ShowClassExpertors&expertType='+expertType;
		
		postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
	}
}
//�������絯�����ݴ���õ�ר����Ϣ
function getBClassExpertsInfo(id,agent,expertType,basePath)
{
	if(expertType!=0)
	{
		var url = basePath+'searchOneFiledServlet?agentNum='+agent+'&cName=ShowClassExpertors&expertType='+expertType;
		postXmlHttp(url,"addOption2Select('"+id+"')",'doNo()');
	}
}