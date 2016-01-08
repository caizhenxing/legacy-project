<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
			document.forms[0].action="../stat/voiceLeave.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/voiceLeave.do?method=toDisplay";
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
		</script>
	</head>
	<body class="conditionBody" onload="setLoad()">
		<html:form action="/stat/voiceLeave" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;语音留言统计
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
							onclick="openCal('voiceForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('voiceForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('voiceForm','endTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('voiceForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
						语音栏目
					</td>
					<td class="valueStyle">
						<html:select property="menu" style="width:95px">
							<option value="">请选择</option>
							<html:options collection="menus" property="value"
								labelProperty="label" />
						</html:select>
					</td>
<%--					<td class="queryLabelStyle">--%>
<%--						选择地区--%>
<%--					</td>--%>
<%--					<td class="valueStyle"  align="left">--%>
<%--						<html:text property="address" styleClass="writeTextStyle" />--%>
<%--						<input name="select" type="button"   value="选择"--%>
<%--							onclick="select()" />--%>
<%--					</td>--%>
					<td class="queryLabelStyle">
						生&nbsp;&nbsp;成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
					</td>
					<td class="queryLabelStyle">
						3D图像
					</td>
					<td class="valueStyle">
						<html:checkbox property="is3d" onclick="dodisplay3d()" />
					</td>
					<td align="center" class="queryLabelStyle" colspan="2">
						<input type="button" id="btnSearch" name="btnSearch" value="统计"
							  onclick="dodisplay()" class="buttonStyle"/>
						<input type="reset" value="刷新"  class="buttonStyle"/>
					</td>
				</tr>
				<tr height="1px">
					<td colspan="12" class="buttonAreaStyle">
					</td>

				</tr>
			</table>
		</html:form>
	</body>
</html>
