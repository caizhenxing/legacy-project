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
			var telNum=document.forms[0].telnum.value;
<%--			if(telNum==null||telNum==""){--%>
<%--				alert("�绰�������")--%>
<%--				return false;--%>
<%--			}--%>
			document.forms[0].action="../stat/userdial.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
				setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/userdial.do?method=toDisplay";
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
		function goQueryOutLine()
		{
			window.open('../callcenter/userInfo.do?method=toCustinfoMain','','width=800,height=380,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
		}
		</script>
	</head>
	<body class="conditionBody" onload="setLoad()">
		<html:form action="/stat/userdial" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						��ǰλ��&ndash;&gt;�û�����ͳ�� 
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="2" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"size="10"
							onclick="openCal('userDialForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('userDialForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2" size="10"
							onclick="openCal('userDialForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('userDialForm','endTime',false);">
					</td>
					<td class="labelStyle">
					�绰����
				</td>
				<td colspan="3" class="valueStyle">
					<html:text styleId="handInputOutLine" property="telnum" styleClass="writeTextStyle2" /><input type="button" value="..." onclick="goQueryOutLine()" class="buttonStyle"/>
				</td>
				
<%--					<td class="LabelStyle">--%>
<%--						ͳ������--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:select property="condition" styleClass="selectStyle">--%>
<%--							<html:option value="count">�绰����</html:option>--%>
<%--							<html:option value="periodtime">ʱ��</html:option>--%>
<%--						</html:select>--%>
<%--					</td>--%>
					<td class="LabelStyle">
						����
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3Dͼ��</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input class="buttonStyle" type="button" id="btnSearch" name="btnSearch" value="ͳ��"  
							onclick="dodisplay()" />
							<input class="buttonStyle" type="reset" value="ˢ��"  >
					</td>
				</tr>
				<tr height="1px">
					<td colspan="13" class="buttonAreaStyle">
					</td>				
				</tr>
			</table>
		</html:form>
	</body>
</html>
