<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter,et.bo.servlet.StaticServlet"%>


<%@ include file="../../style.jsp"%>
<%UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>��ͨ��������Ϣ�б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	
	<!-- ������ʽ�ļ� -->
	<script language="javascript" src="../../js/et/style.js"></script>

</head>

<body class="listBody" onload="equalsCheckbox()">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td width="50">
				ѡ��
			</td>
			<td>
				����ʱ��
			</td>
			<td>
				��ѯ����
			</td>
			<td>
				���ߴ�
			</td>
			<td>
				���״̬
			</td>
			<td width="80">
				����
			</td>
		</tr>
		<logic:iterate id="c" name="list">

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
					<bean:write name="c" property="caseContent" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseReply" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="state" filter="true" />
				</td>
				<td>
					<img alt="��ϸ" src="../../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,470)"
						width="16" height="16" border="0" />
<%--					<%		--%>
<%--					if(StaticServlet.userPowerMap.get("��ͨ������").contains(ub.getUserId())||!ub.getUserGroup().equals("operator")){--%>
<%--					%>--%>
					<img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',800,600)"
						width="16" height="16" border="0" />					
					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,470)"
						width="16" height="16" border="0" />
<%--					<%--%>
<%--					}else{--%>
<%--					%>--%>
<%--					<logic:equal name="c" property="caseRid" value="<%=ub.getUserId() %>">--%>
<%--					 <img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif"--%>
<%--						onclick="popUp('2<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',800,600)"--%>
<%--						width="16" height="16" border="0" />					--%>
<%--					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"--%>
<%--						onclick="popUp('3<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,470)"--%>
<%--						width="16" height="16" border="0" />--%>
<%--					 </logic:equal>--%>
<%--					<%--%>
<%--					}--%>
<%--					 %>--%>

				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="3" class="pageTable">
				<jsp:include flush="true" page="../../output/inc.jsp?dbType=general" />
			</td>
			<td colspan="2" class="pageTable">
				<page:page name="consultationCaseinfopageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
			<%	
				
				String userGroup = ub.getUserGroup();
				if (!userGroup.equals("operator")) {
			%>
			<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle"
					onclick="popUp('4<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toPopStatistic',500,40)" />
			<%
				}
			%>
			<input type="button" name="btnadd" value="���" class="buttonStyle"
					onclick="popUp('5<bean:write name='c' property='caseId'/>','generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=insert',800,600)" />
			</td>
		</tr>
		<tr>
		<td colspan="8"><input class="buttonStyle" type="button" name="btnadd1" value="��ϯԱ������ͳ��"
					onclick="parent.document.location.href='../stat/usercase.do?method=toMain'" style="display='none'"/>
				<input class="buttonStyle" type="button" name="btnadd2" value="����ͳ��"
				onclick="parent.document.location.href='../stat/case.do?method=toMain'" style="display='none'"/>
<%--				<input class="buttonStyle" type="button" name="btnadd2" value="���״̬����ͳ��"--%>
<%--				onclick="parent.document.location.href='../stat/statecase.do?method=toMain'"/>--%>
				</td>
		</tr>
		
	</table>
</body>
</html:html>
