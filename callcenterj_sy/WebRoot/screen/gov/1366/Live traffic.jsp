<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>

<!-- 引入jquery -->
<script type="text/javascript" src="../../../js/jquery/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../../../js/jquery/plug/jquery[1].json-1.3.min.js"></script>
<!-- 滚动页面 -->
<script language="javascript" src="../../../js/MSClass.js" ></script>

<script type="text/javascript">

	$(document).ready(function(){
		loadData();
		$("tr.templeteRow").hide();//确保隐藏
	}
	);

	function loadData(){
		$.post(
			"../../../incomming/incommingInfo.do?method=telCondition",
			function(data){
				
				var result = $.evalJSON(data);
				
				//解析数组
				for(var key in result){
						var obj = result[key];

						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			
            			var str = '<tr>';
            			str = str + '<td class="Odd">';
            			str = str + obj.ringBegintime;
            			str = str + '</td>';
            			
            			str = str + '<td class="Odd">';
            			str = str + obj.cust_name;
            			str = str + '</td>';
            			str = str + '<td class="Odd">';
            			str = str + obj.cust_addr;
            			str = str + '</td>';
            			
            			
            			str = str + '<td class="Odd">';
            			str = str + obj.dictQuestionType1;
            			str = str + '</td>';
            			str = str + '<td class="Odd">';
            			str = str + obj.questionContent;
            			str = str + '</td>';
            			str = str + '<td class="Odd">';
            			str = str + obj.answerMan;
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
	width: 555px;
}
/****************************数据内容********************************/
/*浅蓝色背景标题*/
.Title{
	font-family: "创艺简隶书";
	font-size: 19px;
	color: #000000;
	background-color: #CEDDF0;
	text-align: center;
	border: 1px solid #CEDDF0;
	font-weight: bold;
}
/*奇数行 白色背景*/
.Odd{
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
/*偶数行 浅蓝色背景*/
.Even{
	font-family: "宋体";
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
/*数据图示说明，红色字体*/
.Icon Description{
	font-family: "创艺简隶书";
	font-size: 19px;
	color: #D74305;
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
.Title2 {	font-family: "宋体";
	font-size: 14px;
	color: #000000;
	background-color: #CEDDF0;
	text-align: left;
	border: 1px solid #CEDDF0;
	font-weight: bold;
}
.Title3 {	font-family: "宋体";
	font-size: 14px;
	color: #003399;
	background-color: #CEDDF0;
	text-align: left;
	border: 1px solid #CEDDF0;
	font-weight: bold;
}
-->
</style>
<body >
   
  <!--begin-->
     <table width="1300" border="0" cellpadding="0" cellspacing="0">
     	<tr>
         <td width="150" height="24" class="Title">受理时间</td>
         <td width="99" class="Title">用户姓名</td>
         <td width="383" class="Title">用户地址</td>
         <td width="100" class="Title">咨询栏目</td>
         <td width="351" class="Title">咨询问题</td>
         <td width="106" class="Title">解决方式</td>
       </tr>       
      </table>
     <div id="marquees">

     <table  id="marqueeTableId" width="1300" border="0" cellpadding="0" cellspacing="0">
       <tr class="templeteRow" style="display:none">
         <td class="Odd">2009年3月1日 13：39</td>
         <td class="Odd">张红</td>
         <td class="Odd">沈阳市皇姑区泰山路沈阳软件出口基地B座3号楼</td>
         <td class="Odd">农业产品</td>
         <td class="Odd">今年产品蔬菜多钱一斤？</td>
         <td class="Odd">人工方式</td>
       </tr>       
     </table>
              
     </div>
     <!--end-->
     
</body>

</html>
<script language="javascript">
		new Marquee("marquees","top",1,1300,550,30,1000);
</script>