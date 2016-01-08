<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "No-cache"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setDateHeader("Expires", 0); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无标题文档</title>
<!-- 引入jquery -->
<script type="text/javascript" src="../../../js/jquery/jquery-1.3.1.min.js"></script>
<!-- 统计部分 -->
<script language="JavaScript" src="../../../js/fusion/FusionCharts.js"></script>
<!-- dwr 生成统计图使用的xml文件 -->
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/operScreen.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
<script type="text/javascript">
	function   getLeft(div)   
	  {   
	          var   left   =   0;   
	          while(div!=   null)   
	          {   
	                  left   +=   div.offsetLeft;   
	                  div=   div.offsetParent;     
	          }     
	          return   left;     
	  }  
	  	function   getTop(div)   
	  {   
	          var   top   =   0;   
	          while(div!=   null)   
	          {   
	                  top   +=   div.offsetTop;   
	                  div=   div.offsetParent;     
	          }             
	          return   top;     
	  } 
	  
	  function setPosition(chartdiv,coverTopDiv){
	  	var div=document.getElementById(chartdiv);
	  	var coverTopDiv = document.getElementById(coverTopDiv);
	  	coverTopDiv.style.top=getTop(div);
	  	coverTopDiv.style.left=getLeft(div);
	  } 
	  
	  function changePosition(chartdiv,coverTopDiv){
	  	setPosition(chartdiv,coverTopDiv);
	  }
	  
<%--	  function changeAll(){--%>
<%--	  	operScreen.getCallLogStatisByMonth();--%>
<%--	  	changePosition('chartdiv','coverTopDiv');--%>
<%--	  	operScreen.getCallLogStatisByDay();--%>
<%--	  	changePosition('chartdiv2','coverTopDiv2');--%>
<%--	  }--%>
 	 function changeAll()
	  {
	  	changePosition('chartdiv','coverTopDiv');
	  	changePosition('chartdiv2','coverTopDiv2');
	  }
	  
	  function initload()
	  {
	  	  	operScreen.getCallLogStatisByMonth();
	  	operScreen.getCallLogStatisByDay();
<%--	  $.blockUI({message:'waiting......'});--%>
<%--		setTimeout($.unblockUI, 2000);--%>
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

<body onload="initload()" onresize="changeAll()">


          <!-- wwq html页面没有内容 这个只是个模板 具体做时需要咨询 -->
          <div id="marquees" >
          <table  id="marqueeTableId"  width="975" border="0" align="left" cellpadding="0" cellspacing="0">
                      
            <tr class="templeteRow" >
             
              <td width="975">
              	<div id="chartdiv" align="center" style="background-color:blue;width:975px;"></div>
    			<div id="coverTopDiv"  style="border-top-width:0px;border-bottom-width:0px; border-right-width:1px;border-left-width:1px; border-right-color:#A3A4A1;border-left-color:#A3A4A1; border-style:solid;z-index:999;padding:0px;position:absolute;margin-top:-2px;width: 975px;height:20;top:0;left:0;background-color:red;background:#cccccc   url(../../../images/chart02.jpg);"></div>
      			<script type="text/javascript">
		  			 var chart = new FusionCharts("../../../css/charts/Column2D.swf", "ChartId", "975", "300", "0", "0");
		  			 chart.setDataURL("../../../dataxml/CallLogStatisMonth2D.xml");		   
		   			 chart.render("chartdiv");
				</script>
              </td>             
              </tr>
   				<tr class="templeteRow" >
              <td width="975">
              <div id="chartdiv2" align="center" style="background-color:blue;width:975px;"></div>
              <div id="coverTopDiv2"  style="border-top-width:0px;border-bottom-width:0px; border-right-width:1px;border-left-width:1px; border-right-color:#A3A4A1;border-left-color:#A3A4A1; border-style:solid;z-index:999;padding:0px;position:absolute;margin-top:-2px;width: 975px;height:20;top:0;left:0;background-color:red;background:#cccccc   url(../../../images/chart02.jpg);"></div>
              <script type="text/javascript">
		  			 var chart = new FusionCharts("../../../css/charts/Pie3D.swf", "ChartId", "975", "300", "0", "0");
<%--		  			 chart.setDataURL("../../../dataxml/CallLogStatisDay2D.xml");		   --%>
chart.setDataURL("../../../dataxml/CallLogStatisDayPie3D.xml");
		   			 chart.render("chartdiv2");
				</script>
              </td>
            </tr>
            
          </table>
          </div>
          <!--end-->
          

</body>
</html>
<script language="javascript">
changeAll();
</script>
