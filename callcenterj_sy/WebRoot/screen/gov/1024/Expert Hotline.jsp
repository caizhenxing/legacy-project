<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>

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
	var rows = '';
	function loadData(){
		$.post(
			"../../../screen/expertGroupHL.do?method=groupHotLineByType&type=政府",
			function(data){
				
				var result = $.evalJSON(data);
				
				//解析数组
				for(var key in result){
						var obj = result[key];

						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			 var sImg = obj.ehExpertPic;
             if(sImg=='noImg')
             {
             	sImg = 'images/Market Analysis1.jpg';
             }
             else
             {
             	sImg='<%=basePath%>'+sImg;
             }
	    var str = '    <tr >';
         str +='       <td width="123" height="213" align="left" valign="top" class="Even"><br>';
          str +='            <table width="73%" border="0" align="center" cellpadding="0" cellspacing="3">';
          str +='              <tr>';
         str +='                 <td width="54"><span class="wenzi2"><img src="'+sImg+'" width="70" height="69" align="left"></span></span></td>';
          str +='                </tr>';
          str +='              <tr>';
           str +='               <td class="wenzi1">'+obj.ehCallName+'</td>';
           str +='               </tr>';
          str +='          </table></td>';
           str +='       <td width="95" valign="top" class="Odd"><br>   ';               
             str +='     '+obj.ehExpertZone+'<br></td>';
            str +='      <td valign="top" class="Odd"><br>';
             str +='     <table width="90%" height="173" border="0" cellpadding="0" cellspacing="0">';
             str +='       <tr>';
            str +='          <td valign="top">';
               str +='         <span class="wenzi2">';
              str +='            <span class="Title4">【简&nbsp;&nbsp;&nbsp;&nbsp;介】</span>';
                str +=         obj.ehExpertSummary;
              str +='          </span></td>';
              str +='      </tr>';
             str +='     </table>';
            str +='        <br></td>';
            str +='      <td width="108" valign="top" class="Odd"><br>';
            str +='        '+obj.ehAgreeLevel+'支持率</td>';
           str +='     </tr>';
<%--            			            var str = '		<tr>';--%>
<%--             str +=' <td width="253" height="213" align="left" valign="top" class="Even"><br>';--%>
<%--             str +='     <table width="90%" border="0" align="center" cellpadding="0" cellspacing="3">';--%>
<%--              str +='       <tr>';--%>
<%--             var sImg = obj.ehExpertPic;--%>
<%--             if(sImg=='noImg')--%>
<%--             {--%>
<%--             	sImg = 'images/Market Analysis1.jpg';--%>
<%--             }--%>
<%--              str +='        <td width="54"><span class="wenzi2"><img src="'+sImg+'" width="70" height="69" align="left"></span></td>';--%>
<%--               str +='        <td width="102" class="wenzi1">农民支持率：'+obj.ehAgreeLevel+'</td>';--%>
<%--              str +='       </tr>';--%>
<%--              str +='   </table></td>';--%>
<%--             str +='  <td width="687" valign="top" class="Odd"><br>';--%>
<%--              str +='     <table width="90%" height="173" border="0" cellpadding="0" cellspacing="0">';--%>
<%--              str +='       <tr>';--%>
<%--              str +='         <td valign="top"><span class="Title5">【分&nbsp;析&nbsp;师】 '+obj.ehCallName+'</span> <span class="Title4"><br>';--%>
<%--                str +='         【个人领域】</span><span class="wenzi2">'+obj.ehExpertZone;--%>
<%--                str +='           <span class="Title4">【简&nbsp;&nbsp;&nbsp;&nbsp;介】</span>';--%>
<%--                 str +=obj.ehExpertSummary;        --%>
<%--                 str +='        </span></td>';--%>
<%--                str +='     </tr>';--%>
<%--              str +='  </table>';--%>
<%--               str +='    <br></td>';--%>
<%--             str +='</tr>';--%>
            			$("#marqueeTableId").append(str);
            			//alert(document.getElementById("marqueeTableId").innerHTML);
            			
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
-->
</style>
</head>
<body>
     
            <table width="975" height="20" border="0"  cellpadding="0" cellspacing="0">
              <tr>
                <td width="123" height="24" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">专家照片</td>
                <td width="95" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">专家领域</td>
                <td class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">专家介绍</td>
                <td width="108" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">农民支持率</td>
              </tr>
             </table>
             <div id="marquees">
             <table id="marqueeTableId" width="975" height="20" border="0"  cellpadding="0" cellspacing="0">
              <tr class="templeteRow">
                <td width="123" height="213" align="left" valign="top" class="Even"><br>
                    <table width="73%" border="0" align="center" cellpadding="0" cellspacing="3">
                      <tr>
                        <td width="54"><span class="wenzi2"><img src="images/Market Analysis1.jpg" width="70" height="69" align="left"></span></span></td>
                        </tr>
                      <tr>
                        <td width="95" class="wenzi1">刘可</td>
                        </tr>
                  </table></td>
                <td valign="top" class="Odd"><br>                  
                农民各领域<br></td>
                <td valign="top" class="Odd"><br>
                <table width="90%" height="173" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td valign="top">
                     <span class="wenzi2">
                        <span class="Title4">
                        　　【简&nbsp;&nbsp;&nbsp;&nbsp;介】
                        </span>
                        苹果树下正在进行改版，希望养眼的界面、全新的栏目和有价值的内容会让新的树下重新焕发生机.同时也希望苹果精灵们把心里的想法说出来，你们希望新的苹果树下是什么样的。 希望有什么样的栏目，现有栏目如何改进，如何提高人气等。 苹果树下的新域名是 shuxia.cc，更好记并且还是CC后缀 <br>
                        关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade <br>
                      </span></td>
                  </tr>
                </table>
                  <br></td>
                <td width="108" valign="top" class="Odd"><br>
                  90%支持率</td>
              </tr>
            </table>
          </div>
</body>
</html>
<!-- 以下是javascript代码 -->
<script type="text/javascript">

function go(){
		new Marquee("marquees","top",1,975,610,30,1000);}
</script>

