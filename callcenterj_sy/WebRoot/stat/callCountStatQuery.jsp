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
			document.forms[0].action="../stat/callCountStat.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/callCountStat.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		</script>
	</head>
	<body class="conditionBody" onload="time()">
		<html:form action="/stat/callCountStat.do?method=toDisplay" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;时段咨询量及栏目咨询量统计
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle" size="10"
							onclick="openCal('callCountStatForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('callCountStatForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle" size="10"
							onclick="openCal('callCountStatForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('callCountStatForm','endTime',false);">
					</td>
					
					<td class="LabelStyle">
						统计类别
					</td>
					<td class="valueStyle">
						<html:select property="dateType" styleClass="selectStyle">
							<html:option value="day">日</html:option>
							<html:option value="month">月</html:option>
							<html:option value="year">年</html:option>
							
							<html:option value="colday">栏目(当日)</html:option>
							<html:option value="colmonth">栏目(当月)</html:option>
							<html:option value="colyear">栏目(当年)</html:option>
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
						<input type="button" name="btnSearch" class="buttonStyle" value="统计"  
							onclick="dodisplay()" />
							<input type="reset" class="buttonStyle" value="刷新" >
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
