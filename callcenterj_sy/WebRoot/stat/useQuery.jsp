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
			document.forms[0].action="../stat/use.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
				setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/use.do?method=toDisplay";
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
		<html:form action="/stat/use" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;��Ŀ��ѯͳ��
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
						<html:text property="beginTime" styleClass="writeTextStyle2" size="10"
							onclick="openCal('useStatForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('useStatForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2" size="10"
							onclick="openCal('useStatForm','endTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('useStatForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
						��&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
					</td>
					<td class="queryLabelStyle">
						ѡ�����
					</td>
					<td class="valueStyle"  align="left">
						<html:text property="address" styleClass="writeTextStyle2" />
						<input name="select" type="button"  value="ѡ��" class="buttonStyle"
							onclick="window.open('selected_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" >
					</td>
					<td class="queryLabelStyle">
						3Dͼ��
					</td>
					<td class="valueStyle">
						<html:checkbox property="is3d" onclick="dodisplay3d()" />
					</td>
					<td align="center" class="queryLabelStyle" colspan="2">
						<input class="buttonStyle" type="button" id="btnSearch" name="btnSearch" value="ͳ��"
							  onclick="dodisplay()" />
						<input class="buttonStyle" type="reset" value="ˢ��"  >
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
