<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script language="javascript" src="../js/common.js"></script>
		<script language="javascript" src="../js/clock.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/address.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/address.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
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
		time();
		document.getElementById('btnSearch').click();
	}
		</script>
	</head>
	<body class="conditionBody" onload="setLoad()">
		<html:form action="/stat/address" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;地区咨询统计
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="conditionTable">

				<tr>

					<td class="queryLabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('addressForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('addressForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('addressForm','endTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('addressForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
						生成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()">3D图像</html:checkbox>
					</td>
					<td align="center" class="queryLabelStyle">
						<input type="button" id="btnSearch" name="btnSearch" value="统计"
							  onclick="dodisplay()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="queryLabelStyle">
<%--						拔打次数和拔打时长--%>
						统计指标
					</td>
					<td class="valueStyle">
						<html:select property="qtype" styleClass="selectStyle">
							<html:option value="统计次数">拔打次数</html:option>
							<html:option value="统计时长">拔打时长</html:option>
						</html:select>
					</td>
					<td class="queryLabelStyle">
						选择地区
					</td>
<%--					<td class="valueStyle"  align="left" colspan="3">--%>
<%--						<html:text property="address" styleClass="writeTextStyle2" style="width:200px"/>--%>
<%--						<input name="select" type="button"  value="选择" class="buttonStyle"--%>
<%--							onclick="window.open('selected_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" >--%>
<%--					</td>--%>
					<td class="valueStyle"  align="left" colspan="3">
						<html:text property="address" styleClass="writeTextStyle2" style="width:275px"/>
						<input name="select" type="button"  value="选择" class="buttonStyle"
							onclick="window.open('selected_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" >
					</td>
					<td align="center" class="queryLabelStyle">
						<input type="reset" value="刷新" class="buttonStyle"/>
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
