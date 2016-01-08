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
						��ǰλ��&ndash;&gt;�۸�������ͳ��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle"
							onclick="openCal('priceInfoForm','beginTime',false);" size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('priceInfoForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"
							onclick="openCal('priceInfoForm','endTime',false);" size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('priceInfoForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						��Ʒ����
					</td>
					<td class="valueStyle">
						<html:text property="productName" styleClass="writeTextStyle" size="10" />
					</td>
					<td class="LabelStyle">
						����
					</td>
					<td class="valueStyle">���
<%--						<html:select property="chartType" styleClass="selectStyle" style="width:80px;">--%>
<%--							<html:option value="">���</html:option>--%>
<%--							<html:option value="bar">��ͼ</html:option>--%>
<%--							<html:option value="pie">��ͼ</html:option>--%>
<%--							<html:option value="line">����ͼ</html:option>--%>
<%--						</html:select>--%>
						<html:hidden property="chartType" value="" />
						<html:hidden property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle"></html:hidden>
					</td>
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch" class="buttonStyle" value="ͳ��"  
							onclick="dodisplay()" />
							<input type="reset" class="buttonStyle" value="ˢ��" >
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
