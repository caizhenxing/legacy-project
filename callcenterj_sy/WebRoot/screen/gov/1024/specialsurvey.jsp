<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
<!-- 引入jquery -->
<script type="text/javascript" src="../../../js/jquery/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../../../js/jquery/plug/jquery[1].json-1.3.min.js"></script>
<script type="text/javascript" src="../../../js/jquery/plug/jquery.blockUI.js"></script>
<!-- 滚动页面 -->
<script language="javascript" src="../../../js/MSClass.js" ></script>

<script type="text/javascript">

	$(document).ready(function(){
	
		//$.blockUI({message:'waiting......'});
		//setTimeout($.unblockUI, 2000);
		
		loadData();
		$("tr.templeteRow").hide();//确保隐藏
	}
	);

	function loadData(){
		$.post(
			"../../../screen/scrspesur.do?method=specialSurvey",
			function(data){
				
				var result = $.evalJSON(data);
				
				//解析数组
				for(var key in result){
						var obj = result[key];

						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			var str = '<tr>';
            			
            			str = str + '<td align="left" valign="top" class="Even"><br>';
            			str = str + '<table width="228" border="0" align="center" cellpadding="0" cellspacing="0" height="158">';
            			str = str + '<tr>';
            			str = str + '<td width="82" class="Title4">【调查主题】</td>';
            			str = str + '<td width="134" class="Title5">';
            			str = str + obj.surveyTitle;
            			str = str + '</td>';
            			str = str + '</tr>';
            			str = str + '<tr>';
            			str = str + '<td class="Title5">【调查时间】</td>';
            			str = str + '<td class="wenzi2">';
            			str = str + obj.surveyTime;
            			str = str + '</td>';
            			str = str + '</tr>';
            			str = str + '<tr>';
            			str = str + '<td class="Title5">【调查样本】</td>';
            			str = str + '<td class="wenzi2">';
            			str = str + obj.surveyExample;
            			str = str + '</td>';
            			str = str + '</tr>';
            			str = str + '<tr>';
            			str = str + '<td class="Title5">【委托机构】</td>';
            			str = str + '<td class="wenzi2">';
            			str = str + obj.delegateDep;
            			str = str + '</td>';
            			str = str + '</tr>';
            			str = str + '</table><br>';
            			str = str + '</td>';
            			
            			str = str + '<td valign="top" class="Odd"><br>';
            			str = str + '<table width="90%" height="188" border="0" align="center" cellpadding="0" cellspacing="0">';
                		str = str + '<tr>';
                		str = str + '<td width="86" height="24" class="Title4">【调查主题】</td>';
                		str = str + '<td width="543" class="Title5">';
                		str = str + obj.surveyTitle;
            			str = str + '</td>';
                		str = str + '</tr>';
                		str = str + '<tr>';
                		str = str + '<td height="24" class="Title5">【撰&nbsp;稿&nbsp;人】</td>';
                		str = str + '<td class="wenzi2">';
                		str = str + obj.writter;
            			str = str + '</td>';
                		str = str + '</tr>';
                		str = str + '<tr>';
                		str = str + '<td height="24" class="Title5">【关&nbsp;键&nbsp;字】</td>';
                		str = str + '<td class="wenzi2">';
                		str = str + obj.keywords;
            			str = str + '</td>';
                		str = str + '</tr>';
                		str = str + '<tr>';
                		str = str + '<td valign="top" class="Title5">【摘&nbsp;&nbsp;&nbsp;&nbsp;要】</td>';
                		str = str + '<td valign="top"><span class="wenzi2">';
                		str = str + obj.summary;
            			str = str + '</span></td>';
                		str = str + '</tr>';
                		str = str + '</table><br>';
                 		str = str + '</td>';
                 		
            			str = str + '</tr>';
            			
            			$("#marqueeTableId").append(str);
            			
				}

			}
		);
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
</head>
<body>
  <!--begin-->
          <table width="975" height="20" border="0"  cellpadding="0" cellspacing="0">
            <tr>
              <td width="235" height="24" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">调查基本信息</td>
              <td  class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">调查分析报告</td>
            </tr>
           </table>
          <div id="marquees">
          <table id="marqueeTableId" width="975" height="598" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr class="templeteRow">
              <td align="left" valign="top" class="Even"><br>                
              <table width="228" border="0" align="center" cellpadding="0" cellspacing="0" height="158">
                <tr>
                  <td width="82" class="Title4">【调查主题】</td>
                  <td width="134" class="Title5">苹果树下改版建议</td>
                </tr>
                <tr>
                  <td class="Title5">【调查时间】</td>
                  <td class="wenzi2">2009年2月3日</td>
                </tr>
                <tr>
                  <td class="Title5">【调查样本】</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td class="Title5">【委托机构】</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
                <br></td>
              <td valign="top" class="Odd"><br>
                <table width="90%" height="188" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="86" height="24" class="Title4">【调查主题】</td>
                    <td width="543" class="Title5">苹果树下改版建议</td>
                  </tr>
                  <tr>
                    <td height="24" class="Title5">【撰&nbsp;稿&nbsp;人】</td>
                    <td class="wenzi2">2009年2月3日</td>
                  </tr>
                  <tr>
                    <td height="24" class="Title5">【关&nbsp;键&nbsp;字】</td>
                    <td class="wenzi2">&nbsp;</td>
                  </tr>
                  <tr>
                    <td valign="top" class="Title5">【摘&nbsp;&nbsp;&nbsp;&nbsp;要】</td>
                    <td valign="top"><span class="wenzi2">苹果树下正在进行改版，希望养眼的界面、全新的栏目和有价值的内容会让新的树下重新焕发生机.同时也希望苹果精灵们把心里的想法说出来，你们希望新的苹果树下是什么样的。 希望有什么样的栏目，现有栏目如何改进，如何提高人气等。 苹果树下的新域名是 shuxia.cc，更好记并且还是CC后缀 <br>
                      关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade </span></td>
                  </tr>
                </table>
                <br></td>
            </tr>
          </table>
          </div>
          <!--end-->
</body>
<script language="javascript">
		new Marquee("marquees","top",1,975,600,30,1000);
</script>
</html>
