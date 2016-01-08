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
			"../../../screen/screen.do?method=marAnalysisStudy&showSize=20",
			function(data){
				
				var result = $.evalJSON(data);
				
				//解析数组
				for(var key in result){
						var obj = result[key];

						//根据解析得到问题的内容的值obj.casereply是得到问题答案的值
            			
<%--            			var str = '<tr>';--%>
<%--            			str = str + '<td width=253 class=Even>';--%>
<%--            			str = str + obj.casecontent;--%>
<%--            			str = str + '</td>';--%>
<%--            			str = str + '<td width=681 class=Even>';--%>
<%--            			str = str + obj.casereply;--%>
<%--            			str = str + '</td>';--%>
<%--            			str = str + '</tr>';--%>
            var str = 		'<tr >';
            str += '<td valign="top" class="Odd"><br>';
            str += '<img src="'+'<%=basePath%>'+obj.analysiserPhoto+'" width="70" height="69"> <br>';
            str +=      '<br>';
            str +=       '<table width="90%" border="0" cellpadding="0" cellspacing="3">';
            str +=        '<tr>';
            str +=          '<td><span class="Title5">分析师：'+obj.analysisPerson+'<br>'
            str +=                '<span class="Title4">简介：</span><span class="wenzi2">'+obj.analysisPersonInfo+'<br>';
            str +=                '</span></span></td>';
            str +=         '</tr>';
            str +=      '</table>';
            str +=    '<p class="Title5">&nbsp;</p></td>';
            str +=  '<td valign="top" class="Odd"><br>';
            str +=      '<table width="90%" height="322" border="0" cellpadding="0" cellspacing="0">';
            str +=        '<tr>'
            str +=          '<td valign="top"><span class="Title5">【标题】 '+obj.subTitle+'</span> <br>';
            str +=              '<span class="Title4">【正文】</span> <span class="wenzi2">'+obj.analysisContent+'</span></td>';
            str +=        '</tr>';
            str +=       '</table>';
            str +=     '</td>';
            str +=   '</tr>';
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
           <table width="975" height="20" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="241" height="24" class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">分析师信息</td>
              <td class="Title"><img src="images/traffic statistics2.jpg" width="12" height="13">分析内容</td>
            </tr>
            </table>
          <div id="marquees">
          <table id="marqueeTableId" width="975" height="559" border="0" cellpadding="0" cellspacing="0">
            <tr class="templeteRow">
              <td valign="top" class="Odd"><br>
                  <img src="images/Market Analysis1.jpg" width="70" height="69"> <br>
                  <br>
                  <table width="90%" border="0" cellpadding="0" cellspacing="3">
                    <tr>
                      <td><span class="Title5">分析师：孙主任<br>
                            <span class="Title4">简介：</span><span class="wenzi2">1987年于星海音乐学院音乐教育专业毕业后分配到海南中学工作至今，历任学校图音教研组组长、艺术部主任。并任海南省教育学会音乐教育专业委员会副理事长、海南省基础教育课程改革指导组成<br>
                              员...</span></span></td>
                    </tr>
                  </table>
                <p class="Title5">&nbsp;</p></td>
              <td valign="top" class="Odd"><br>
                  <table width="90%" height="322" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td valign="top"><span class="Title5">【标题】 苹果树下改版建议</span> <br>
                          <span class="Title4">【正文】</span> <span class="wenzi2">苹果树下正在进行改版，希望养眼的界面、全新的栏目和有价值的内容会让新的树下重新焕发生机.同时也希望苹果精灵们把心里的想法说出来，你们希望新的苹果树下是什么样的。 希望有什么样的栏目，现有栏目如何改进，如何提高人气等。 苹果树下的新域名是 shuxia.cc，更好记并且还是CC后缀 <br>
                            关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade <br>
                            新版绝不是空洞的花架子，除了好看新增的核心栏目是能为大家创造价值的应用，当然也少不了好玩的小栏目 涂鸦改动会比较大，重写涂鸦板绘画程序，效率更高，笔刷效果更流畅逼真，会增加橡皮差和颜料桶苹果树下的新域名是 shuxia.cc，更好记并且还是CC后缀 关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade 新版绝不是空洞的花架子，除了好看新增的核心栏目是能为大家创造价值的应用，当然也少不了好玩的小栏目涂鸦改动会比较大，重写涂鸦板绘画程序，效率更高，笔刷效果更流畅逼真，会增加橡皮差和颜料桶关于新版上线时间，估计最快也要8月份，我们会精雕细琢新网站，所有的元素都是handmade <br>
                            新版绝不是空洞的花架子，除了好看新增的核心栏目是能为大家创造价值的应用，当然也少不了好玩的小栏目 涂鸦改动会比较大，重写涂鸦板绘画程序，效率更高，笔刷效果更流畅逼真，会增加橡皮差和颜料桶 </span></td>
                    </tr>
                  </table>
                </td>
            </tr>
          </table>
          </div>
          <!--end-->
          

</div>
</body>
</html>
<script language="javascript">
		new Marquee("marquees","top",1,975,620,30,1000);
</script>
<%--wwq注释 下班的js在以前的html里就有了不知道有没有用先注释起来--%>

<%--<!-- 以下是javascript代码 -->--%>
<%--<script language="javascript">--%>
<%--<!----%>
<%--marqueesHeight=561; //内容区高度--%>
<%--stopscroll=false; //这个变量控制是否停止滚动--%>
<%--with(marquees){--%>
<%--noWrap=true; //这句表内容区不自动换行--%>
<%--style.width=0; //于是我们可以将它的宽度设为0，因为它会被撑大--%>
<%--style.height=marqueesHeight;--%>
<%--style.overflowY="hidden"; //滚动条不可见--%>
<%--//onmouseover=new Function("stopscroll=true"); //鼠标经过，停止滚动--%>
<%--//onmouseout=new Function("stopscroll=false"); //鼠标离开，开始滚动--%>
<%--}--%>
<%----%>
<%--//这时候，内容区的高度是无法读取了。下面输出一个不可见的层"templayer"，稍后将内容复制到里面：--%>
<%--document.write('<div id="templayer" style="position:absolute;z-index:1;visibility:hidden"></div>');--%>
<%--function init(){ //初始化滚动内容--%>
<%--//多次复制原内容到"templayer"，直到"templayer"的高度大于内容区高度：--%>
<%--while(templayer.offsetHeight<marqueesHeight){--%>
<%--templayer.innerHTML+=marquees.innerHTML;--%>
<%--} //把"templayer"的内容的“两倍”复制回原内容区：--%>
<%--marquees.innerHTML=templayer.innerHTML+templayer.innerHTML;--%>
<%--//设置连续超时，调用"scrollUp()"函数驱动滚动条：--%>
<%--setInterval("scrollUp()",20);--%>
<%--}--%>
<%--document.body.onload=init;--%>
<%--preTop=0; //这个变量用于判断滚动条是否已经到了尽头--%>
<%--function scrollUp(){ //滚动条的驱动函数--%>
<%--if(stopscroll==true) return; //如果变量"stopscroll"为真，则停止滚动 --%>
<%--preTop=marquees.scrollTop; //记录滚动前的滚动条位置--%>
<%--marquees.scrollTop+=1; //滚动条向下移动一个像素--%>
<%--//如果滚动条不动了，则向上滚动到和当前画面一样的位置--%>
<%--//当然不仅如此，同样还要向下滚动一个像素(+1)：--%>
<%--if(preTop==marquees.scrollTop){--%>
<%--marquees.scrollTop=templayer.offsetHeight-marqueesHeight+1;--%>
<%--}--%>
<%--}--%>
<%---->--%>
<%--</script>--%>
