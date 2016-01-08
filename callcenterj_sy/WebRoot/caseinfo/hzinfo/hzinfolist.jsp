<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<%@ page import="et.bo.sys.login.bean.UserBean,et.bo.servlet.StaticServlet"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>
<%UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION); %>
<html:html locale="true">
<head>
	<html:base />

	<title>会诊案例库信列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	
</head>
<body class="listBody" onload="equalsCheckbox()">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" width="50">
				选择
			</td>
			<td class="listTitleStyle" width="80">
				受理时间
			</td>
			<td class="listTitleStyle" width="210">
				会诊主题
			</td>
			<td class="listTitleStyle" width="250">
				会诊内容
			</td>
			<td class="listTitleStyle" width="60">
				审核状态
			</td>
			<td class="listTitleStyle" width="80">
				操作
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>

			<tr style="line-height: 21px;">
				<td>
					<input name="educe" type="checkbox" id="educe"
						onclick="setCheckbox(this)"
						value="<bean:write name='c' property='caseId'/>">
				</td>
				<td>
					<bean:write name="c" property="caseTime" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseSubject" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseContent" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="state" filter="true" />
				</td>
				<td>
					<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,600)"
						width="16" height="16" border="0" />
						
					<%		
					if(StaticServlet.userPowerMap.get("会诊案例库").contains(ub.getUserId())||!ub.getUserGroup().equals("operator")){
					%>
					<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',800,634)"
						width="16" height="16" border="0" />		
					<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,600)"
						width="16" height="16" border="0" />
					<%
					}else{
					%>
					<logic:equal name="c" property="caseRid" value="<%=ub.getUserId() %>">
					<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',800,634)"
						width="16" height="16" border="0" />		
					<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,600)"
						width="16" height="16" border="0" />
					 </logic:equal>
					<%
					}
					 %>
					
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="3" class="pageTable">
				<jsp:include flush="true" page="../../output/inc.jsp?dbType=hzinfo" />				
			</td>
			<td colspan="2" class="pageTable">
				<page:page name="hzpageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
			<%
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
			%>
				<input type="button" name="btnstatistic" value="统计" class="buttonStyle"
					onclick="popUp('4<bean:write name='c' property='caseId'/>','hzinfo.do?method=toPopStatistic',500,40)" />
				
			<%
							}
			%>
				<input type="button" name="btnadd" value="添加" class="buttonStyle"
						onclick="popUp('5<bean:write name='c' property='caseId'/>','hzinfo.do?method=tohzinfoLoad&type=insert',800,634)" />	
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<input type="button" name="btnadd1" value="每一案例属性下的案例数量统计" class="buttonStyle"
					onclick="parent.document.location.href='../stat/hzCaseInfo.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd2" value="座席员受理的案例数量统计" class="buttonStyle"
					onclick="parent.document.location.href='../stat/hzCaseInfoSeat.do?method=toMain'" style="display='none'"/>
			</td>
		</tr>
	</table>
</body>
</html:html>