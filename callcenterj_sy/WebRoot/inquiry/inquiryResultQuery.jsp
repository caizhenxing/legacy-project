<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<html:base />
		<title>调查信息分析库</title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></SCRIPT>
		<script>
		function doquery(){
			document.forms[0].action="../inquiryResult.do?method=toList";
			document.forms[0].target="bottomm";
			document.forms[0].submit();
		}
</script>
    <% 
    	String atype = request.getParameter("atype");
    	String curPosition = "调查信息分析库";
    	if("12316inquiry".equals(atype))
    	{
    		curPosition = "12316调查";
    	}
    %>
	</head>
	
	
	<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
		<html:form action="/inquiry" method="post">

			<table width="100%" align="center"  cellpadding="0"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;调查信息分析库
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				
				<tr class="conditionoddstyle">
					<td class="queryLabelStyle">
						开始时间 
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('inquiryForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						问卷主题
					</td>
					<td class="valueStyle">
						<html:text property="topic" styleClass="writeTextStyle"/>
					</td>
					<td class="queryLabelStyle">
						调查类别
					</td>
					<td class="valueStyle">
						<html:select property="inquiryType" styleClass="selectStyle" style="width: 132px;">
							<html:option value="">全部</html:option>
							<html:options collection="inquiryTypes" property="value"
								labelProperty="label" />
						</html:select>
					</td>
					<td class="queryLabelStyle">
						审核状态
					</td>
					<td class="valueStyle">
						<select name="reportState" id="reportState" class="selectStyle"><!-- 唯此处与其它不同 -->
							<option value="">全部</option>
							<option>原始</option>
							<option>待审</option>
							<option>驳回</option>
							<option>已审</option>
							<option>发布</option>
						</select>
					</td>
				</tr>
				<tr class="conditionoddstyle">
					
					<td class="queryLabelStyle">
						结束时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('inquiryForm','endTime',false);">
					</td>
					<td class="queryLabelStyle">
						发起机构
					</td>
					<td class="valueStyle">
						<html:text property="organiztion" styleClass="writeTextStyle"/>
					</td>
					<td class="queryLabelStyle">
						组 织 者
					</td>
					<td class="valueStyle">
						<html:text property="organizers" styleClass="writeTextStyle"/>
					</td>
					<td class="queryLabelStyle" align="center" colspan="2">
						<input type="button" name="btnSearch" value="查询" class="buttonStyle"  onclick="doquery()" />
						<input  type="reset" value="刷新" class="buttonStyle"  onClick="parent.bottomm.document.location=parent.bottomm.document.location;">
					</td>
				</tr>
				<tr height="1px">
					<td colspan="8" class="buttonAreaStyle">
						
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>