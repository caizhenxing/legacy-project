<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
			document.forms[0].action="../stat/appraise.do?method=toIvrDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/appraise.do?method=toIvrDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				var timeStr = time.format('yyyy-MM-dd');
				timeStr=timeStr.substring(0,7)+'-01';
				document.forms[0].beginTime.value=timeStr;//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
	function setLoad()
	{
		time();
		document.getElementById('btnSearch').click();
	}
	function setLoad()
	{
		time();
		document.getElementById('btnSearch').click();
	}
		</script>
	</head>
	<body class="conditionBody" onload="setLoad()">
		<html:form action="/stat/appraise" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;用户评价统计
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
						<html:text property="beginTime" styleClass="writeTextStyle" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('appraiseForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						座席工号
					</td>
					<td class="valueStyle">
					<html:select property="agentNum">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
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
						<input class="buttonStyle" type="button" id="btnSearch" name="btnSearch" value="统计" onclick="dodisplay()"/>
					</td>
					</tr>
					<tr>
					<td class="queryLabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('appraiseForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
<%--						统计指标--%>
					</td>
					<td class="valueStyle">
<%--						<html:select property="qitem" styleClass="selectStyle" style="width:85px">--%>
<%--							<html:option value="1">满意</html:option>--%>
<%--							<html:option value="2">基本满意</html:option>--%>
<%--							<html:option value="3">不满意</html:option>--%>
<%--						</html:select>--%>
					</td>
					<td class="valueStyle">
					</td>
					<td class="valueStyle">
					</td>
					<td align="center" class="queryLabelStyle">
						<input class="buttonStyle" value="刷新" type="reset"  class="buttonStyle"/>
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
