<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>政府12316大屏幕系统</title>
<script language="javascript" src="../../../js/et/tools.js"></script>
<!-- 引入dwr -->
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/constKeyValueService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentDayQuestionVolumeService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/operScreen1.js'></script>
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
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
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
/*话务数据，白色显示数据的地方*/
.shuju{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 35px;
}
/*在新专家，白色显示数据的地方*/
.shuju2{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 257px;
}
/*在新专家，白色显示数据的地方*/
.shuju3{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 370px;
}

.shuju4{
	background-color: #FFFFFF;
	border: 1px solid #99BBE8;
	font-family: "宋体";
	color: #000000;
	width: 50px;
}

/*数据图示说明，红色字体*/
.Icon Description{
	font-family: "宋体";
	font-size: 14px;
	color: #D74305;
	font-weight: bold;
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
.Odd {	font-family: "宋体";
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
.Title4 {	font-family: "宋体";
	font-size: 12px;
	color: #C00000;
	text-align: left;
	font-weight: bold;
}
.Title5 {	font-family: "宋体";
	font-size: 12px;
	color: #2270A9;
	text-align: left;
	font-weight: bold;
}
.wenzi2 {	font-family: "宋体";
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	line-height: 22px;
}
-->
</style>



<script language="javascript">
//设置计数变量，当页面加载时重0开始
var i = 0;
//设置访问次数时页面的值
//顺序为（话务实况，话务统计，话务分析，价格看板，焦点追踪，金农市场分析，经典案例，12316专题调查，热线专家团，12316金农热线）
var str = new Array("./Live traffic.jsp","./traffic statistics.jsp","./CallLogStatis0.jsp","./Price Kanban.jsp","./Focustracking2.jsp","./Market Analysis.jsp","./Case Study.jsp","./specialsurvey.jsp","./Expert Hotline.jsp","./McKinnonHotline.jsp");
var img = new Array("images/Live traffic1.jpg","images/traffic statistics1.jpg","images/navigation1.jpg","images/Price Kanban1.jpg","images/Focus tracking1.jpg","images/Market Analysis2.jpg","images/Case Study2.jpg","images/Case Study1.jpg","images/Expert Hotline1.jpg","images/McKinnon Hotline1.jpg");

$(document).ready(function(){
		setInterval('changeUrl()',10000);

		operScreen1.getQuickMessage(changeQuickMessage);		
		setInterval('operScreen1.getQuickMessage(changeQuickMessage)',10000);
		setInterval('updateExpert()',10000);
		setInterval("getQuestions()",10000);	
		updateExpert();
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



</head>
<body onload="getQuestions()">
<div id="width">
  <table width="1024" border="0" cellspacing="0" cellpadding="0" >
    <tr>
      <td width="11%" height="104" rowspan="2"><img src="images/special survey1.jpg" width="143" height="104" ></td>
      <td width="46%" rowspan="2"><img src="images/special survey2.jpg" width="613" height="104"></td>
      <td width="14%" height="45" background="images/special survey5.jpg" class="time">
            <div id='time' style="color:red;">
            <script>document.getElementById('time').innerHTML=new Date().toLocaleString();
            	setInterval("document.getElementById('time').innerHTML=new Date().toLocaleString();",1000);
			</script>
			</div>
      </td>
      <td width="29%" rowspan="2"><img src="images/special survey4.jpg" width="80" height="104"></td>
    </tr>
    <tr>
      <td><img src="images/special survey3.jpg" width="188" height="59"></td>
    </tr>
  </table>
<table border="0" cellspacing="0" cellpadding="0" width="1024">
  <tr>
    <td width="12"><img src="images/information1.jpg" width="12" height="26"></td>
    <td width="129" background="images/information2.jpg"><table width="120" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><img src="images/information4.jpg" width="21" height="18"></td>
        <td class="time2">金农快讯</td>
      </tr>
    </table></td>
    <td width="881" background="images/information3.jpg"><table width="881" border="0" cellpadding="0" cellspacing="5">
      <tr>
        <td class="wenzi1" valign="middle"><marquee id="qm_marquee"scrollamount="4" direction="left" width="800">【会讯】辽宁省农业信息工作会议今日在沈阳召开，辽宁省农村经济委员会党组副书记、副主任李忠国同志到会并作重要讲话，14个市农委主管信息工作的负责人到会共商发展，辽宁省农村经济委员会信息中心也将全面部署五大方面的具体工作。</marquee>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1024" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="143" rowspan="2"><img id="img_show" src="images/navigation1.jpg" width="141" height="51"></td>
    <td width="107"><img src="images/navigation2.jpg" width="107" height="24"></td>
    <td>
    
    
    
    
    
	<table width="775" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="12"><img src="images/navigation4.jpg" width="12" height="24"></td>
          <td width="381" valign="bottom" background="images/navigation5.jpg"><table width="376" border="0" cellpadding="0" cellspacing="2">
            <tr>
              <td width="53" align="center" valign="middle" class="wenzi1"><table cellpadding="０">
                  <tr>
                    <td height="1"></td>
                  </tr>
                </table>
                首席专家</td>
              <td width="317" class="wenzi1">
              <div style="background:#ffffff;width:304"><marquee scrollamount="2" direction="left" width="304" id="scrollFirstExpert">小张 小李 小王</marquee></div>
              </td>
            </tr>
          </table></td>
          <td width="10" valign="bottom" background="images/navigation5.jpg"><img src="images/navigation7.jpg" width="8" height="19"></td>
          <td width="372" valign="bottom" background="images/navigation5.jpg"><table width="380" border="0" cellpadding="0" cellspacing="2">
            <tr>
              <td width="52" align="center" valign="middle" class="wenzi1"><table cellpadding="０">
                  <tr>
                    <td height="1"></td>
                  </tr>
                </table>
                值班专家</td>
              <td width="322" class="wenzi1">
              <div style="background:#ffffff;width:304"><marquee scrollamount="2" direction="left" width="304" id="scrollClassExpert">小张 小李 小王</marquee></div>
              </td>
            </tr>
          </table></td>
        </tr>
      </table>
      
      
      
      </td>
  </tr>
  <tr>
    <td><img src="images/navigation3.jpg" width="107" height="26"></td>
    <td>
    
    
	<table width="776" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="12" background="images/navigation5.jpg"><img src="images/navigation4.jpg" width="12" height="24"></td>
        <td width="760" valign="bottom" background="images/navigation5.jpg"><table width="754" border="0" cellpadding="0" cellspacing="2">
          <tr>
            <td width="57" height="19" align="center" valign="middle" class="wenzi1"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>
              话务总量</td>
            <td width="62" align="left" valign="bottom"><input name="textfield" type="text" class="shuju4"  id="huaWuSum" size="10" width="10"/></td>
            <td width="12" align="center"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="55" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">当日总量</span></td>
            <td width="42" align="left" valign="bottom"><input name="textfield2" type="text" class="shuju"  id="dayHuaWu" /></td>
            <td width="15" align="right"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="43" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">生产</span></td>
            <td width="42" align="left" valign="bottom"><input name="textfield3" type="text" class="shuju"  id="produceInquiry" /></td>
            <td width="14" align="right"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="43" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">市场</span></td>
            <td width="42" align="left" valign="bottom"><input name="textfield4" type="text" class="shuju"  id="marketInquiry" /></td>
            <td width="13" align="right"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="43" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">政策</span></td>
            <td width="44" align="left" valign="bottom"><input name="textfield5" type="text" class="shuju"  id="policyInquiry" /></td>
            <td width="15" align="right"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="47" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">医疗</span></td>
            <td width="42" align="left" valign="bottom"><input name="textfield6" type="text" class="shuju"  id="medicalInquiry" /></td>
            <td width="15" align="right"><img src="images/navigation7.jpg" width="8" height="19"></td>
            <td width="44" align="center"><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table>              <span class="wenzi1">其他</span></td>
            <td width="42" align="left" valign="bottom"><input name="textfield7" type="text" class="shuju"  id="otherInquiry" /></td>
          </tr>
        </table></td>
        </tr>
    </table>
    
    
    
    
    </td>
  </tr>
  <tr>
    <td colspan="3"><table width="1024" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="20"><img src="images/contents1.jpg" width="20" height="22"></td>
          <td width="119" background="images/contents2.jpg">&nbsp;</td>
          <td width="864" background="images/contents3.jpg">&nbsp;</td>
          <td width="21"><img src="images/contents5.jpg" width="21" height="22"></td>
        </tr>
        <tr>
          <td height="587" background="images/contents4.jpg">&nbsp;</td>
          <td colspan="2" align="center"><!-- 显示屏幕区域 -->
          <div id="screenShow" style="padding-left:5px;">
          		<iframe id="screenIframe" src="./Case Study.jsp" width="975" height="630" scrolling="no" frameborder="0"></iframe>
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


