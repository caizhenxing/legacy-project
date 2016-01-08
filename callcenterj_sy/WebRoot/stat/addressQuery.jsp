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
						��ǰλ��&ndash;&gt;������ѯͳ��
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
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('addressForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('addressForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('addressForm','endTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('addressForm','endTime',false);">
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
						<input type="button" id="btnSearch" name="btnSearch" value="ͳ��"
							  onclick="dodisplay()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="queryLabelStyle">
<%--						�δ�����Ͱδ�ʱ��--%>
						ͳ��ָ��
					</td>
					<td class="valueStyle">
						<html:select property="qtype" styleClass="selectStyle">
							<html:option value="ͳ�ƴ���">�δ����</html:option>
							<html:option value="ͳ��ʱ��">�δ�ʱ��</html:option>
						</html:select>
					</td>
					<td class="queryLabelStyle">
						ѡ�����
					</td>
<%--					<td class="valueStyle"  align="left" colspan="3">--%>
<%--						<html:text property="address" styleClass="writeTextStyle2" style="width:200px"/>--%>
<%--						<input name="select" type="button"  value="ѡ��" class="buttonStyle"--%>
<%--							onclick="window.open('selected_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" >--%>
<%--					</td>--%>
					<td class="valueStyle"  align="left" colspan="3">
						<html:text property="address" styleClass="writeTextStyle2" style="width:275px"/>
						<input name="select" type="button"  value="ѡ��" class="buttonStyle"
							onclick="window.open('selected_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" >
					</td>
					<td align="center" class="queryLabelStyle">
						<input type="reset" value="ˢ��" class="buttonStyle"/>
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
