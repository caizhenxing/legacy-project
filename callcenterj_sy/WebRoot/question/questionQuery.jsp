<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "question.do?method=toQuestionList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}

	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
   	</script>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">

	<html:form action="/question/question.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;服务记录
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" class="conditionTable">
			<tr class="conditionoddstyle">
				<td class="queryLabelStyle" width="8%">
					咨询栏目
				</td>
				<td class="valueStyle" width="25%">

					<html:hidden property="id" />
					<html:select property="dict_question_type1"
						styleClass="selectStyle" style="width:135">
						<html:option value="">全部</html:option>
						<html:option value="政策咨询">政策咨询</html:option>
						<html:option value="种植咨询">种植咨询</html:option>
						<html:option value="养殖咨询">养殖咨询</html:option>
						<html:option value="项目咨询">项目咨询</html:option>
						<html:option value="环保咨询">环保咨询</html:option>
						<html:option value="重大事件上报">重大事件上报</html:option>
						<html:option value="信息订制">信息订制</html:option>
						<html:option value="金农通">金农通</html:option>
						<html:option value="价格行情">价格行情</html:option>
						<html:option value="价格报送">价格报送</html:option>
						<html:option value="供求发布">供求发布</html:option>
						<html:option value="热线调查">热线调查</html:option>
						<html:option value="企业服务">企业服务</html:option>
						<html:option value="医疗服务">医疗服务</html:option>
					</html:select>
				</td>

				<td class="queryLabelStyle" width="8%">
					受理专家
				</td>

				<td class="valueStyle" width="25%">
					<html:select property="bill_num" styleClass="selectStyle" style="width:135">
						<html:option value="">全部</html:option>
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=expertType" />
					</html:select>
				</td>
				<td class="queryLabelStyle" width="8%">
					是否回访
				</td>
				<td class="valueStyle" colspan="2"  width="26%">
					<html:select property="dict_is_callback" styleClass="selectStyle" style="width:85">
						<html:option value="">全部</html:option>
						<html:option value="否">否</html:option>
						<html:option value="是">是</html:option>
					</html:select>
				</td>
			</tr>


			<tr>
				<td class="queryLabelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:text property="question_content" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					咨询答案
				</td>
				<td class="valueStyle">
					<html:text property="answer_content" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					解决状态
				</td>
				<td class="valueStyle" colspan="2">
					<html:select property="dict_is_answer_succeed"
						styleClass="selectStyle" style="width:85">
						<html:option value="">全部</html:option>
						<html:options collection="dict_is_answer_succeed" property="value"
							labelProperty="label" />
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="queryLabelStyle">
					问题分类
				</td>
				<td class="valueStyle" colspan="3">
					<select name="dict_question_type2" class="selectStyle"
						onChange="selecttype(this)">
						<option value="">
							请选择大类
						</option>
						<option>
							粮油作物
						</option>
						<option>
							经济作物
						</option>
						<option>
							蔬菜
						</option>
						<option>
							药材
						</option>
						<option>
							花卉
						</option>
						<option>
							草坪及地被
						</option>
						<option>
							家畜
						</option>
						<option>
							家禽
						</option>
						<option>
							牧草
						</option>
						<option>
							鱼类
						</option>
						<option>
							虾/蟹/鳖/龟/蛙/藻/螺贝及软体类
						</option>
						<option>
							特种养殖
						</option>
						<option>
							基础设施及生产资料
						</option>
						<option>
							政策法规及管理
						</option>
						<option>
							其他
						</option>
					</select>
					<span id="selectspan"> <select name="dict_question_type3"  
							class="selectStyle">
							<option value="">
								请选择小类
							</option>
						</select> </span>
					<select name="dict_question_type4" class="selectStyle">
						<option value="">
							全部
						</option>
						<option>
							品种介绍
						</option>
						<option>
							栽培管理
						</option>
						<option>
							养殖管理
						</option>
						<option>
							疫病防治
						</option>
						<option>
							虫草防除
						</option>
						<option>
							收获贮运
						</option>
						<option>
							产品加工
						</option>
						<option>
							市场行情
						</option>
						<option>
							饲料配制
						</option>
						<option>
							农资使用
						</option>
						<option>
							设施修建
						</option>
						<option>
							政策管理
						</option>
						<option>
							其他综合
						</option>
					</select>
				</td>
				<td class="queryLabelStyle">
					解决方式
				</td>
				<td class="valueStyle">
					<html:select property="answer_man" styleClass="selectStyle">
						<html:option value="">全部</html:option>
						<html:options collection="answer_man" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button"  
						value="查询" onclick="query()" class="buttonStyle"/>
					<input value="刷新" type="reset"  class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="7" class="buttonAreaStyle">
				</td>
			</tr>


		</table>

	</html:form>
</body>
</html:html>
<script>
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		sendRequest("../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
	}

	var XMLHttpReq = false;
 	//创建XMLHttpRequest对象       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//发送请求函数
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}

	
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("selectspan").innerHTML = "<select name='dict_question_type3' class='selectStyle'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>