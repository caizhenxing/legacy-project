<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<SCRIPT language=javascript src="../js/calendar3.js"
			type=text/javascript>
		</SCRIPT>
		<script language="javascript" src="../js/common.js"></script>
		<script language="javascript" src="../js/clock.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/medicinfoP.do?method=tomedicinfoPDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/medicinfoP.do?method=tomedicinfoPDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
			var time = new Date();
			document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
			time.setYear(time.getYear()-1);
			document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
		}
		</script>
	</head>
	<body class="conditionBody" onload="setTime()">
		<html:form action="/stat/medicinfoP" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;普通医疗使用率统计
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
						<html:text property="beginTime" styleClass="writeTextStyle2" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('medicinfoPForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('medicinfoPForm','endTime',false);">
					</td>

					<td class="queryLabelStyle">
						统计类型
					</td>
					<td class="valueStyle">
						<%--						<html:text property="statType" styleClass="input" />--%>
						<html:select property="statType" styleClass="selectStyle">
							<html:option value="userType">
								按照用户统计
							</html:option>
							<html:option value="trackType">
								按照跟踪服务统计
							</html:option>
							<html:option value="gonghaoType">
								按照受理工号统计
							</html:option>
						</html:select>
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
						<input type="button" name="btnSearch" value="统计"
							  onclick="dodisplay()" class="buttonStyle"/>
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
