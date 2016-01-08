<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp" %>
<html>
	<head>
	<html:base />
		<title></title>
		<link href="../style/<%=styleLocation %>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			var telNum=document.forms[0].telnum.value;
<%--			if(telNum==null||telNum==""){--%>
<%--				alert("电话号码必添！")--%>
<%--				return false;--%>
<%--			}--%>
			document.forms[0].action="../stat/userdial.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
				setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/userdial.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
				var time = new Date();
				document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
				//time.setYear(time.getYear()-1);
				var timeStr = time.format('yyyy-MM-dd');
				timeStr=timeStr.substring(0,7)+'-01';
				document.forms[0].beginTime.value=timeStr;
				//document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
	}
				function setLoad()
	{
		setTime();
		document.getElementById('btnSearch').click();
	}
		function goQueryOutLine()
		{
			window.open('../callcenter/userInfo.do?method=toCustinfoMain','','width=800,height=380,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
		}
		</script>
	</head>
	<body class="conditionBody" onload="setLoad()">
		<html:form action="/stat/userdial" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						当前位置&ndash;&gt;用户拨打统计 
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="2" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"size="10"
							onclick="openCal('userDialForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('userDialForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2" size="10"
							onclick="openCal('userDialForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('userDialForm','endTime',false);">
					</td>
					<td class="labelStyle">
					电话号码
				</td>
				<td colspan="3" class="valueStyle">
					<html:text styleId="handInputOutLine" property="telnum" styleClass="writeTextStyle2" /><input type="button" value="..." onclick="goQueryOutLine()" class="buttonStyle"/>
				</td>
				
<%--					<td class="LabelStyle">--%>
<%--						统计条件--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:select property="condition" styleClass="selectStyle">--%>
<%--							<html:option value="count">电话数量</html:option>--%>
<%--							<html:option value="periodtime">时长</html:option>--%>
<%--						</html:select>--%>
<%--					</td>--%>
					<td class="LabelStyle">
						生成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3D图像</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input class="buttonStyle" type="button" id="btnSearch" name="btnSearch" value="统计"  
							onclick="dodisplay()" />
							<input class="buttonStyle" type="reset" value="刷新"  >
					</td>
				</tr>
				<tr height="1px">
					<td colspan="13" class="buttonAreaStyle">
					</td>				
				</tr>
			</table>
		</html:form>
	</body>
</html>
