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
			document.forms[0].action="../stat/priceInfoForType.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/priceInfoForType.do?method=toDisplay";
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
		<html:form action="/stat/priceInfoForType.do?method=toDisplay" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						当前位置&ndash;&gt;价格类型量统计
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
						<html:text property="beginTime" styleClass="writeTextStyle"
							onclick="openCal('priceInfoForm','beginTime',false);" size="10" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('priceInfoForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"
							onclick="openCal('priceInfoForm','endTime',false);" size="10" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('priceInfoForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						产品名称
					</td>
					<td class="valueStyle">
						<html:text property="productName" styleClass="writeTextStyle" size="10" />
					</td>
					<td class="LabelStyle">
						生成
					</td>
					<td class="valueStyle">表格
<%--						<html:select property="chartType" styleClass="selectStyle" style="width:80px;">--%>
<%--							<html:option value="">表格</html:option>--%>
<%--							<html:option value="bar">柱图</html:option>--%>
<%--							<html:option value="pie">饼图</html:option>--%>
<%--							<html:option value="line">曲线图</html:option>--%>
<%--						</html:select>--%>
						<html:hidden property="chartType" value="" />
						<html:hidden property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle"></html:hidden>
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
