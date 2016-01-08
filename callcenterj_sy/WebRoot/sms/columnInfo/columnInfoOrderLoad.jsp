<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object orderType = request.getAttribute("orderType");
String ot = "";
if(orderType != null)
	ot = orderType.toString();
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>短信点播操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript" src="../../js/clock.js"></script>
			<script language="javascript" src="./../../js/ajax.js"></script>
		<script language="javascript" src="./../../js/all.js"></script>
		<script language="javascript" src="./../../js/agentState.js"></script>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
	</SCRIPT>


<style type="text/css">
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
</style>

	


	<script type="text/javascript">
	
			
			
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
		
				function cancel()
				{
				    
			 		document.forms[0].action="../columnInfo.do?method=toColumnInfoOrderProgramme&type=cancel&orderType=<%=ot%>";
			 		
			 		document.forms[0].submit();
				}
				function del()
				{
			 		document.forms[0].action="../columnInfo.do?method=toColumnInfoOrderProgramme&type=delete&orderType=<%=ot%>";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
				var link = opener.parent.bottomm.document.location.href;
				if(link.indexOf("pagestate") == -1){
					link += "&orderType=<%=ot%>&pagestate=1";
				}
				opener.parent.bottomm.document.location = link;
			}
		
		}
			function selecttype1(){
		//专家类别id
		var billnum = document.getElementById('bill_num').value;
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		
	}
			</script>

</head>

<body onunload="toback()" class="loadBody">
	<%
		//System.out.println("operSign  "+request.getAttribute("operSign"));
	 %>
	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); 
	window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/sms/columnInfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				<logic:equal name="opertype" value="cancel">
		    		当前位置->取消点播
		    	</logic:equal>
				<logic:equal name="opertype" value="detail">
		    		 当前位置->查看点播信息
		    	</logic:equal>
					<logic:equal name="opertype" value="delete">
		    		当前位置->删除点播信息
		    	</logic:equal>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					手机号码
				</td>
				<td class="valueStyle">
					<html:text property="mobileNum"></html:text>
				</td>
			</tr>
					<td class="labelStyle">
						订制栏目
					</td>
					<td class="valueStyle">
						<html:select property="columnInfo">
							<option value="">
								${columnInfo}
							</option>
							<html:options collection="list" property="value"
								labelProperty="label" />
						</html:select>
					</td>
			<tr>
				<td class="labelStyle">
					订制时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../../html/img/cal.gif"
							onclick="openCal('columnInfoBean','endTime',false);">
				</td>
			</tr>
			<tr>
				<td colspan="6" align="right" class="buttonAreaStyle">					
					<logic:equal name="opertype" value="cancel">
						<input type="button" name="btnAdd" class="buttonStyle" value="退订"
							onclick="cancel()" />
					</logic:equal>
					<logic:equal name="opertype" value="delete">
						<input type="button" name="btnDelete" class="buttonStyle" value="删除"
							onclick="del()" />
					</logic:equal>

					<input type="button" name="" value="关闭" class="buttonStyle"
						onClick="javascript:window.close();" />
				</td>
			</tr>
			<html:hidden property="id" />
		</table>

	</html:form>
</body>
</html:html>
<script>
	
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "请选择"){
			sendRequest("../../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
		}
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
				document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>
