<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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
			"../../../screen/screen.do?method=FocusTracking",
			function(data){
				
				var result = $.evalJSON(data);
				//alert(data);
				//解析数组
				for(var key in result){
						var obj = result[key];
						
						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			
            			var str = '<tr>';            			
						str = str + "<td height=\"24\" class=\"Title2\"><span class=\"Title3\">【"+obj.period+"】</span>"+obj.title+"</td>";
						str = str + "</tr><tr><td height=\"140\" class=\"Odd\"><table width=\"90%\" height=\"57\" border=\"0\" cellpadding=\"0\" cellspacing=\"3\">";
						str = str + "<tr><td width=\"64\" height=\"20\" valign=\"middle\" class=\"wenzi1\">【摘要】</td>";
						str = str + "<td width=\"724\" rowspan=\"2\" class=\"wenzi2\">"+obj.summary+"</td>";
						str = str + "</tr></table></td></tr>";
<%--						str = str + "</tr><tr><td height=\"0\">&nbsp;</td></tr></table></td></tr>";--%>
            			$("#marqueeTableId").append(str);
            			
				}
				go();
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
</head>
<body>   
          <!--begin-->
          <div id="marquees" >
          <table id="marqueeTableId" width="975" border="0" align="center"  cellpadding="0" cellspacing="0">
            <tr class="templeteRow">
              <td width="739" height="24" class="Title2"><span class="Title3">【第27期】</span>种植植物调整积极，粮油生产发展稳定</td>
            </tr>
            <tr class="templeteRow" style="display:none;">
              <td height="140" class="Odd"><table width="805" height="191" border="0" cellpadding="0" cellspacing="3" style="height: 191px;">
                  <tr>
                    <td width="64" height="20" valign="middle" class="wenzi1">【摘要】</td>
                    <td width="724" rowspan="2" class="wenzi2">苹果树下正在进行改版，希望养眼的界面、全新的栏目和有价值的内容会让新的树下重新焕发生机.同时也希望苹果精灵们把心里的想法说出来，你们希望新的苹果树下是什么样的。苹果树下的新域shuxia.cc，更好记并且还是CC后缀 关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade 新版绝不是空洞的花架子，除了好看新增的核心栏目是能创造价值的应用，当然也少不了好玩的小栏目 涂鸦改动会比较大，重写涂鸦板绘画程序，效率更高，笔刷效果更流畅逼真，会增加橡皮差和颜料桶 新版论用discuz</td>
                  </tr>                 
              </table></td>
            </tr>
              </table>
			<tr>
				<td height="90"></td>
			</tr>
          </div>
          <!--end-->
 
</body>
<script language="javascript">
function go(){
		new Marquee("marquees","top",1,975,620,30,1000);}
</script>
</html>
