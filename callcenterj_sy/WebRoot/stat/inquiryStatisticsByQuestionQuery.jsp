<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp" %>
<%@ page import="excellence.common.tools.LabelValueBean" %>
<%@ page import="java.util.*" %>
<html>
	<head>
	<html:base />
		<title></title>
		<link href="../style/<%=styleLocation %>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/inquiryStatisticsByQuestion.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/inquiryStatisticsByQuestion.do?method=toDisplay";
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
		<html:form action="/stat/inquiryStatisticsByQuestion.do?method=toDisplay" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;����ش���ͳ��
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
							onclick="openCal('commonForm','beginTime',false);" size="12" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('commonForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"
							onclick="openCal('commonForm','endTime',false);" size="12"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('commonForm','endTime',false);">
					</td>
					<td class="LabelStyle">
<%--					������ wwq2009-02-09��ǰ����ϯΪ�������ڸ����� ����ע��һ�·�ֹ�ڸĻ�ȥ --%>
						��������
				</td>
				<td class="valueStyle">
<%--					<html:select property="agentId" style="width:80px;">--%>
<%--					<option value="">ѡ�񹤺�</option>--%>
<%--					<%--%>
<%--						List<LabelValueBean> l = et.bo.sys.common.ListForSelect.getUserList();--%>
<%--						for(int i=0; i<l.size(); i++)--%>
<%--						{--%>
<%--							LabelValueBean bean = l.get(i);--%>
<%--							out.print("<option value=\""+bean.getValue()+"\">"+bean.getLabel()+"</option>");--%>
<%--						}--%>
<%--				     %>--%>
<%--					</html:select>--%>
						<html:text property="question" styleClass="writeTextStyle" size="15" />
				</td>
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
