<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script language="javascript" src="../js/common.js"></script>
		<script language="javascript" src="../js/clock.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/inquiry.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/inquiry.do?method=toDisplay";
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
		<html:form action="/stat/inquiry" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;���ߵ���ͳ��
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
							onclick="openCal('inquiryStatForm','beginTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('inquiryStatForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('inquiryStatForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('inquiryStatForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
						ͳ������
					</td>
					<td class="valueStyle">
						<html:select property="topic" styleClass="selectStyle">
	    				<html:option value="">ȫ��</html:option>
	    				<html:options collection="list" property="value" labelProperty="label"/>
	    				</html:select>
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
						3Dͼ��<html:checkbox property="is3d" onclick="dodisplay3d()"/>
					</td>
					<td align="center" class="queryLabelStyle">
						<input type="button" name="btnSearch" value="ͳ��"   onclick="dodisplay()" class="buttonStyle"/>
						<input type="reset" value="ˢ��"  class="buttonStyle"/>
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
