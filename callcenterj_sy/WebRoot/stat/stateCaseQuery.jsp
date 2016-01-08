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
			document.forms[0].action="../stat/statecase.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
				document.forms[0].action="../stat/statecase.do?method=toDisplay";
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
		<html:form action="/stat/statecase" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						当前位置&ndash;&gt;座席受理案例统计 
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
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('userCaseForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('userCaseForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('userCaseForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('userCaseForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<html:text property="caseRid" styleClass="writeTextStyle" />
				</td>
				<td class="LabelStyle">
						审核状态
					</td>
					<td class="valueStyle">
					<html:select property="stateType" styleClass="selectStyle">
							<html:option value="formerly">原始</html:option>
							<html:option value="waitCensor">待审</html:option>
							<html:option value="turnDown">驳回</html:option>
							<html:option value="censoring">已审</html:option>
							<html:option value="putOut">发布</html:option>
						</html:select>
					</td>
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
						<input class="buttonStyle" type="button" name="btnSearch" value="统计"  
							onclick="dodisplay()" />
							<input class="buttonStyle" type="reset" value="刷新"  >
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
