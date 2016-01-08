<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
<script language="javascript" src="../../../js/et/tools.js"></script>
<!-- 引入dwr -->
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/constKeyValueService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentDayQuestionVolumeService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/operScreen.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>

<!-- 引入jquery -->
<script type="text/javascript" src="../../../js/jquery/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../../../js/jquery/plug/jquery[1].json-1.3.min.js"></script>
<script language="javascript">
/**
huaWuSum
dayHuaWu
produceInquiry
marketInquiry
policyInquiry
medicalInquiry
otherInquiry
*/
function doWothQuestions(v)
{
	if(v.indexOf('@')!=-1)
	{
		var vArr = v.split("@");
	
		document.getElementById('huaWuSum').value=vArr[0];
		document.getElementById('dayHuaWu').value=vArr[1];
		document.getElementById('produceInquiry').value=vArr[2];
		document.getElementById('marketInquiry').value=vArr[3];
		document.getElementById('policyInquiry').value=vArr[4];
		document.getElementById('medicalInquiry').value=vArr[5];
		document.getElementById('otherInquiry').value=vArr[6];
		
	}
}
function getQuestions()
{
	var ymd = new Date().format('yyyy-MM-dd');
	agentDayQuestionVolumeService.getScreenQuestions(ymd,doWothQuestions);

}
function callBackFirstExpert(obj)
{
	if(obj!='')
	{
		var result = $.evalJSON(obj);
				
		//解析数组
		for(var key in result){
			var v = result[key];
			//alert(obj.constValue);
			if(v)
			{
				document.getElementById('scrollFirstExpert').innerHTML=v.constValue;
			}
		}
	}
}
function callBackClassExpert(obj)
{
	if(obj!='')
	{
		var result = $.evalJSON(obj);
				
		//解析数组
		for(var key in result){
			var v = result[key];
			if(v)
			{
				document.getElementById('scrollClassExpert').innerHTML=v.constValue;
			}
		}
	}
}
function getFirstExpert()
{
	var obj = constKeyValueService.getConstValueByTypeKey('screen','expert_first',callBackFirstExpert);
}
function getClassExpert()
{
	var obj = constKeyValueService.getConstValueByTypeKey('screen','expert_class',callBackClassExpert);
}
function updateExpert()
{
	getFirstExpert();
	getClassExpert();
}
</script>
<script language="javascript">
//设置计数变量，当页面加载时重0开始
var i = 0;
//设置访问次数时页面的值
//顺序为（话务实况，话务统计，话务分析，价格看板，焦点追踪，金农市场分析，经典案例，12316专题调查，热线专家团，12316金农热线）
var str = new Array("./Live traffic.jsp","./traffic statistics.jsp","./CallLogStatis2.jsp","./Price Kanban.jsp","./Focustracking.jsp","./Market Analysis.jsp","./Case Study.jsp","./specialsurvey.jsp","./Expert Hotline.jsp","./McKinnonHotline.jsp");
var img = new Array("images/Live traffic1.jpg","images/traffic statistics1.jpg","images/navigation1.jpg","images/Price Kanban1.jpg","images/Focus tracking1.jpg","images/Market Analysis2.jpg","images/Case Study2.jpg","images/Case Study1.jpg","images/Expert Hotline1.jpg","images/McKinnon Hotline1.jpg");

$(document).ready(function(){
		setInterval('changeUrl()',3000);
		operScreen.getQuickMessage(changeQuickMessage);		
		setInterval('operScreen.getQuickMessage(changeQuickMessage)',10000);
		setInterval('updateExpert()',10000);
		window.setInterval("getQuestions",60*2*1000);			
});

function changeUrl(){

	i = i + 1;

	var url = '';
	var img_url = "";
	
	switch(i%10){
		
		case 1:
		url = str[0];
		img_url = img[0];
		break;
		
		case 2:
		url = str[1];img_url = img[1];
		break;
		
		case 3:
		url = str[2];img_url = img[2];
		break;
		
		case 4:
		url = str[3];img_url = img[3];
		break;
		
		case 5:
		url = str[4];img_url = img[4];
		break;
		
		case 6:
		url = str[5];img_url = img[5];
		break;
		
		case 7:
		url = str[6];img_url = img[6];
		break;
		
		case 8:
		url = str[7];img_url = img[7];
		break;
		
		case 9:
		url = str[8];img_url = img[8];
		break;
		
		case 0:
		url = str[9];img_url = img[9];
		break;
		
		default:
			url = str[0];img_url = img[0];
	}
	var demos = $("#screenShow");
	demos.find('iframe').attr('src',url);
	$("#img_show").attr('src',img_url);
	
}

//金农快讯
function changeQuickMessage(obj){
	if(obj!=null)
		document.getElementById("qm_marquee").innerHTML=obj.toString();	
}
</script>
<style type="text/css">
<!--
body {
	margin: 0px;
}
/*整个页面div的宽为100%*/
#width{
	width: 100%;
}
/*************************页面眉头************************/
/*日期时间的文字样式*/
.time{
	font-family: "宋体";
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	text-align: center;
	padding-top: 6px;
}
/*金农分析栏目，白色文字*/
.time2{
	font-family: "宋体";
	font-size: 14px;
	font-weight: bolder;
	color: #000000;
	text-align: left;
	padding: 2px;
}
/*数据标题文字，黑色12号字体*/
.wenzi1{
	font-family: "宋体";
	font-size: 12px;
	font-weight: normal;
	color: #000000;
}
/*摘要内容，黑色12号字体*/
.wenzi2{
	font-family: "宋体";
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	line-height: 22px;
}
/*话务数据，白色显示数据的地方*/
.shuju{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 79px;
}
/*在新专家，白色显示数据的地方*/
.shuju2{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 400px;
}
/*在新专家，白色显示数据的地方*/
.shuju3{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 556px;
}
/****************************数据内容********************************/
/*数据图示说明，红色字体*/
.Icon Description{
	font-family: "创艺简隶书";
	font-size: 19px;
	color: #D74305;
}
.Even {	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	background-color: #F5F9FA;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #CEDDF0;
	border-bottom-color: #CEDDF0;
	border-left-color: #CEDDF0;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	text-align: center;
	line-height: 24px;
}
.Odd {	
    font-family: "宋体";
	font-size: 12px;
	color: #000000;
	background-color: #FFFFFF;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #CEDDF0;
	border-bottom-color: #CEDDF0;
	border-left-color: #CEDDF0;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	text-align: center;
	line-height: 24px;
}
/*数据标题*/
.Title {
	font-family: "宋体";
	font-size: 14px;
	color: #000000;
	background-color: #CEDDF0;
	text-align: center;
	border: 1px solid #CEDDF0;
	font-weight: bolder;
}
/*标题，左侧*/
.Title2{
	font-family: "创艺简隶书";
	font-size: 19px;
	color: #000000;
	background-color: #CEDDF0;
	text-align: left;
	border: 1px solid #CEDDF0;
	font-weight: bold;
}
/*标题，蓝色*/
.Title3{
	font-family: "创艺简隶书";
	font-size: 16px;
	color: #003399;
	background-color: #CEDDF0;
	text-align: left;
	border: 1px solid #CEDDF0;
	font-weight: bold;
}
/*摘要，红色*/
.Title4{
	font-family: "宋体";
	font-size: 12px;
	color: #C00000;
	text-align: left;
	font-weight: bold;
}
/*分析师，蓝色*/
.Title5{
	font-family: "宋体";
	font-size: 12px;
	color: #2270A9;
	text-align: left;
	font-weight: bold;
}
.shuju31 {	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 555px;
}
-->
</style>
<body onclick="getQuestions()">
<div id="width">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
    <tr>
      <td height="57" rowspan="2"><img src="images/CaStatis1.jpg" width="148" height="58" ></td>
      <td rowspan="2"><img src="images/CaStatis2.jpg" width="928" height="58"></td>
      <td height="24" background="images/CaStatis5.jpg" class="time">2009年2月27日&nbsp;星期五&nbsp;15：44</td>
      <td rowspan="2"><img src="images/CaStatis3.jpg" width="102" height="58"></td>
    </tr>
    <tr>
      <td><img src="images/CaStatis4.jpg" width="188" height="34"></td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" width="1366">
  <tr>
    <td><img src="images/information1.jpg" width="12" height="28"></td>
    <td width="136" background="images/information2.jpg">
    <table width="120" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><img src="images/information4.jpg" width="21" height="18"></td>
        <td class="time2">金农快讯</td>
      </tr>
    </table>
    </td>
    <td width="1218" background="images/information3.jpg"><table width="1198" border="0" cellpadding="0" cellspacing="5">
      <tr>
        <td class="wenzi1">
        <marquee id="qm_marquee"scrollamount="4" direction="left" width="800">这里是写到数据库里的金农快讯的信息，随时可能更新！！！</marquee>
        </td>
      </tr>
    </table></td>
  </tr>
</table>

  <table width="1366" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="148" rowspan="2" valign="bottom"><img id="img_show" src="images/Expert Hotline1.jpg" width="148" height="52"></td>
      <td width="107"><img src="images/navigation2.jpg" width="107" height="25"></td>
      <td><table width="1114" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="12"><img src="images/navigation4.jpg" width="12" height="25"></td>
          <td width="460" background="images/navigation5.jpg"><table width="460" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="56" align="center" valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  首席专家</td>
                <td width="401" align="left"><div style="background:#ffffff"><marquee scrollamount="2" direction="left" width="250" id="scrollFirstExpert">小张 小李 小王</marquee></div></td>
              </tr>
          </table></td>
          <td width="5"><img src="images/navigation6.jpg" width="5" height="25"></td>
          <td width="647"><table width="637" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="12"><img src="images/navigation4.jpg" width="12" height="25"></td>
                <td width="614" background="images/navigation5.jpg"><table width="621" border="0" cellpadding="0" cellspacing="1">
                    <tr>
                      <td width="58" align="center" valign="middle" class="wenzi1"><table cellpadding="０">
                          <tr>
                            <td height="1"></td>
                          </tr>
                        </table>
                        值班专家</td>
                      <td width="560" align="left">
                      <div style="background:#ffffff"><marquee scrollamount="2" direction="left" width="340" id="scrollClassExpert">小张 小李 小王</marquee></div>
                      </td>
                    </tr>
                </table></td>
                <td width="21">&nbsp;</td>
              </tr>
          </table></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td valign="bottom"><img src="images/navigation3.jpg" width="107" height="26"></td>
      <td><table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="27"><img src="images/navigation7.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  话务总量</td>
                <td align="center"><input name="textfield" type="text" class="shuju" id="huaWuSum"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td background="images/navigation11.jpg"><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  当日总量</td>
                <td align="right"><input name="textfield2" type="text" class="shuju" id="dayHuaWu"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td background="images/navigation11.jpg"><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  生产咨询</td>
                <td align="right"><input name="textfield3" type="text" class="shuju" id="produceInquiry"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  市场咨询</td>
                <td align="right"><input name="textfield4" type="text" class="shuju" id="marketInquiry"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  政策咨询</td>
                <td align="right"><input name="textfield5" type="text" class="shuju" id="policyInquiry"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="140" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  医疗咨询</td>
                <td align="right"><input name="textfield6" type="text" class="shuju" id="medicalInquiry"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
          <td><img src="images/navigation10.jpg" width="15" height="26"></td>
          <td width="138" background="images/navigation8.jpg"><table width="137" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td valign="middle" class="wenzi1"><table cellpadding="０">
                    <tr>
                      <td height="1"></td>
                    </tr>
                  </table>
                  其他咨询</td>
                <td align="center"><input name="textfield7" type="text" class="shuju" id="otherInquiry"></td>
              </tr>
          </table></td>
          <td><img src="images/navigation9.jpg" width="4" height="26"></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td colspan="3" valign="top"><table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/contents1.jpg" width="20" height="22"></td>
          <td width="122" background="images/contents2.jpg">&nbsp;</td>
          <td width="1203" background="images/contents3.jpg">&nbsp;</td>
          <td><img src="images/contents5.jpg" width="21" height="22"></td>
        </tr>
        <tr>
          <td height="587" background="images/contents4.jpg">&nbsp;</td>
          <td colspan="2" align="center">
           <!-- 显示屏幕区域 -->
          <div id="screenShow" >
          		<iframe id="screenIframe" src="./Case Study.jsp" width="1269" height="630" scrolling="no" frameborder="0"></iframe>
          </div>
          </td>
          <td background="images/contents6.jpg">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="images/contents7.jpg" width="20" height="22"></td>
          <td colspan="2" background="images/contents8.jpg">&nbsp;</td>
          <td><img src="images/contents9.jpg" width="21" height="22"></td>
        </tr>
      </table></td>
    </tr>
  </table>  
</div>
</body>
</html>