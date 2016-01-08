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
			document.forms[0].action="../stat/focusCaseInfoUser.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/focusCaseInfoUser.do?method=toDisplay";
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
		<html:form action="/stat/focusCaseInfoUser.do?method=toDisplay" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;��ϯ������ͳ��
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
							onclick="openCal('focusCaseInfoForm','beginTime',false);" size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('focusCaseInfoForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"
							onclick="openCal('focusCaseInfoForm','endTime',false);" size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('focusCaseInfoForm','endTime',false);">
					</td>
					<td class="LabelStyle">
<%--						��ϯ����--%>
						������
					</td>
					<td class="valueStyle">
						<html:select property="userId">
						<option value="">ѡ�񹤺�</option>
						<%
							List<LabelValueBean> l = et.bo.sys.common.ListForSelect.getUserList();
							for(int i=0; i<l.size(); i++)
							{
								LabelValueBean bean = l.get(i);
								out.print("<option value=\""+bean.getValue()+"\">"+bean.getLabel()+"</option>");
							}
					     %>
						</html:select>
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
							<input type="reset" class="buttonStyle" value="ˢ��"  >
					</td>
				</tr>
				<tr height="1px">
					<td colspan="9" class="buttonAreaStyle">&nbsp;
					</td>				
				</tr>
			</table>
		</html:form>
	</body>
</html>
