<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<html:base />
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script language="javascript" src="../js/common.js"></script>
		<script language="javascript" src="../js/clock.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/money.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/money.do?method=toDisplay";
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
		<html:form action="/stat/money" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;外呼计费统计
						<br>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle" width="100px">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('moneyForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('moneyForm','beginTime',false);">
					</td>
					<td class="LabelStyle">外呼号码
					</td>
					<td class="valueStyle">
					<html:text property="outTelNum" styleId="handInputOutLine" styleClass="writeTextStyle" size="15"  /><input type="button" value="..." onclick="goQueryOutLine()" class="buttonStyle"/>
					</td>
					<td class="LabelStyle">
						统计单价
					</td>
					<td class="valueStyle">
						<html:text property="price" styleClass="writeTextStyle" size="10"
							value="0.2" />
						(单位：元)
					</td>
					<td align="center" class="queryLabelStyle" width="102px">&nbsp;
						<input type="button" id="btnSearch" name="btnSearch" value="统计"
							  onclick="dodisplay()" class="buttonStyle"/>
					</td>
				</tr>
				<tr class="conditionoddstyle">

					<td class="LabelStyle"> 
						截止时间 
					</td>
					<td class="valueStyle" width="100px"><html:text property="endTime" styleClass="writeTextStyle2" onclick="openCal('moneyForm','endTime',false);"></html:text>
					<img alt="选择时间" src="../html/img/cal.gif" onclick="openCal('moneyForm','endTime',false);">
					</td>
					<td class="LabelStyle"> 
					<br></td>
					<td class="valueStyle">&nbsp; <br>
					</td>
					<td class="LabelStyle"> 
					<br></td>
					<td class="valueStyle">&nbsp;&nbsp; 
					</td>
					<td align="center" class="queryLabelStyle" width="102px">&nbsp; 
						<input type="reset" value="刷新"  class="buttonStyle"/>
					</td>
				</tr>
				<tr height="1px">
					<td colspan="9" class="buttonAreaStyle">
					</td>

				</tr>
			</table>
		</html:form>
	</body>
</html>
