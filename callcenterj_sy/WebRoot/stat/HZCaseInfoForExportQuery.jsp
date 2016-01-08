<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<html:base />
		<title></title>
		<link href="../style/<%=styleLocation %>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/hzCaseInfoForExport.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/hzCaseInfoForExport.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function selecttype1(){
		//ר�����id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//��̬���ɵ�select id Ϊ expert_name
		
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
		<html:form action="/stat/hzCaseInfoForExport" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;ר��������ͳ��
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
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('caseForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('caseForm','beginTime',false);">
					</td>
					
					<td class="LabelStyle">
						����
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle" style="width:135px">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3Dͼ��</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch" value="ͳ��"  
							onclick="dodisplay()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('caseForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('caseForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						����ר��
					</td>
					<td class="valueStyle">
								<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
									onchange="selecttype1()" style="width:135px">
									<html:option value="0">��ѡ��ר��</html:option>
									<html:options collection="expertList" property="value"
										labelProperty="label" styleClass="writeTypeStyle" />
									<html:option value="0">��ũ����</html:option>
								</html:select>
								<html:select styleId="expert_name" property="caseExpert"
								styleClass="selectStyle" style="width:135px">
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName))
									{
										out.print("<option value="+rExpertName+">*"+rExpertName+"</option>");
									}
									else
									{
										out.print("<option value=\"\">ѡ��ר��</option>");
									}
								%>
								</html:select>
					</td>
					<td class="LabelStyle" align="center">
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
