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
		//专家类别id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//动态生成的select id 为 expert_name
		
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
						当前位置&ndash;&gt;专家受理量统计
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('caseForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('caseForm','beginTime',false);">
					</td>
					
					<td class="LabelStyle">
						生成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle" style="width:135px">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3D图像</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch" value="统计"  
							onclick="dodisplay()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('caseForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('caseForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						受理专家
					</td>
					<td class="valueStyle">
								<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
									onchange="selecttype1()" style="width:135px">
									<html:option value="0">请选择专家</html:option>
									<html:options collection="expertList" property="value"
										labelProperty="label" styleClass="writeTypeStyle" />
									<html:option value="0">金农热线</html:option>
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
										out.print("<option value=\"\">选择专家</option>");
									}
								%>
								</html:select>
					</td>
					<td class="LabelStyle" align="center">
						<input type="reset" value="刷新"  class="buttonStyle"/>
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
