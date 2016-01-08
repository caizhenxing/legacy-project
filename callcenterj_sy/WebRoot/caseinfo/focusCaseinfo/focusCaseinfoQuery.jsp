<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>焦点案例信息查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>

	<script type="text/javascript">

 	function add()
 	{
 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../focusCaseinfo.do?method=toFocusCaseinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
	
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/caseinfo/focusCaseinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;焦点案例库
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="15"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('focusCaseinfoBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					案例属性
				</td>
				<td class="valueStyle">
					<html:select property="caseAttr4" styleClass="selectStyle" style="width:130px">
						<html:option value="">请选择</html:option>
						<html:option value="疫情灾害">疫情灾害</html:option>
						<html:option value="政策纠风">政策纠风</html:option>
						<html:option value="市场经济">市场经济</html:option>
						<html:option value="生产管理">生产管理</html:option>
						<html:option value="医疗卫生">医疗卫生</html:option>
						<html:option value="食品安全">食品安全</html:option>
						<html:option value="文娱教育">文娱教育</html:option>
					</html:select>
				</td>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<%--<html:text property="caseRid" styleClass="writeTextStyle" size="10"/>
					--%><html:select property="caseRid">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
					<td class="labelStyle">
				案例点评
				</td>
				<td class="labelStyle" align="center">
					<html:text property="caseReview" styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="15"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('focusCaseinfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:text property="caseContent" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:65px">
						<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option selected="selected">待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option selected="selected">驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("pass".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option selected="selected">已审</option>
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option selected="selected">发布</option>
					<%
					}else{
					%>
					<option value="" selected="selected">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}
					 %>
<%--						<option value="">全部</option>--%>
<%--						<option>原始</option>--%>
<%--						<option>待审</option>--%>
<%--						<option>驳回</option>--%>
<%--						<option>已审</option>--%>
<%--						<option>发布</option>--%>
					</select>
				</td>
						<td class="labelStyle" align="center">
		
				</td>
				<td class="labelStyle" align="center">	
					<input type="button" name="btnSearch" value="查询"
						class="buttonStyle" onclick="query()" />				
					<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="13" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>