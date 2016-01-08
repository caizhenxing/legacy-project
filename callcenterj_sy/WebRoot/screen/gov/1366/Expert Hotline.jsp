<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
			"../../../screen/screen.do?method=caseStudy",
			function(data){
				
				var result = $.evalJSON(data);
				
				//解析数组
				for(var key in result){
						var obj = result[key];

						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			
            			var str = '<tr>';
            			str = str + '<td width=276 class=Even>';
            			str = str + obj.casecontent;
            			str = str + '</td>';
            			str = str + '<td width=983 class=Even>';
            			str = str + obj.casereply;
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
<body>
 
<!--begin-->

	 <table width="1298" height="20" border="0" align="center" cellpadding="0" cellspacing="0">     
       <tr>
          <td width="276" height="24" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">专家照片</td>
          <td width="983" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">专家介绍</td>
       </tr>
     </table>
     <div id="marquees">     
     <table id="marqueeTableId" width="1298" border="0" cellpadding="0" cellspacing="0">       
       <tr class="templeteRow">
         <td width="276" class="Even">1</td>
         <td width="983" class="Even">1</td>
       </tr>       
     </table>     
     </div>
     
     <!--end-->

</body>
<!-- 以下是javascript代码 -->
<script type="text/javascript">
		new Marquee("marquees","top",1,1298,550,30,1000);
</script>
</html>