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
						��ǰλ��&ndash;&gt;�û�����ͳ��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="conditionTable">

				<tr>

					<td class="queryLabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('appraiseForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						��ϯ����
					</td>
					<td class="valueStyle">
					<html:select property="agentNum">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
					</td>
					<td class="queryLabelStyle">
						����
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()">3Dͼ��</html:checkbox>
					</td>
					<td align="center" class="queryLabelStyle">
						<input class="buttonStyle" type="button" id="btnSearch" name="btnSearch" value="ͳ��" onclick="dodisplay()"/>
					</td>
					</tr>
					<tr>
					<td class="queryLabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('appraiseForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
<%--						ͳ��ָ��--%>
					</td>
					<td class="valueStyle">
<%--						<html:select property="qitem" styleClass="selectStyle" style="width:85px">--%>
<%--							<html:option value="1">����</html:option>--%>
<%--							<html:option value="2">��������</html:option>--%>
<%--							<html:option value="3">������</html:option>--%>
<%--						</html:select>--%>
					</td>
					<td class="valueStyle">
					</td>
					<td class="valueStyle">
					</td>
					<td align="center" class="queryLabelStyle">
						<input class="buttonStyle" value="ˢ��" type="reset"  class="buttonStyle"/>
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
