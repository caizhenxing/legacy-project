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

	<title>��������Ϣ�б�</title>

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
				��������
			</td>
			<td width="200">
				��ѯ����
			</td>
			<td width="240">
				���ߴ�
			</td>
			<td width="70">
				���״̬
			</td>
			<td width="80">
				����
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
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
					<bean:write name="c" property="caseAttr4" filter="true" />
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
						onclick="popUp('1<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,500)"
						width="16" height="16" border="0" />
						
					<%		
					if(StaticServlet.userPowerMap.get("���㰸����").contains(ub.getUserId())||!ub.getUserGroup().equals("operator")){
					%>
					<img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',1000,500)"
						width="16" height="16" border="0" />
					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,500)"
						width="16" height="16" border="0" />
					<%
					}else{
					%>
					<logic:equal name="c" property="caseRid" value="<%=ub.getUserId() %>">
					 <img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',1000,500)"
						width="16" height="16" border="0" />
					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,500)"
						width="16" height="16" border="0" />
					 </logic:equal>
					<%
					}
					 %>
					
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="4" class="pageTable">
				<jsp:include flush="true" page="../../output/inc.jsp?dbType=focus" />
			</td>
			<td colspan="2" class="pageTable">
				<page:page name="focusCaseinfopageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
			<%
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
			%>
				<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle"
					onclick="popUp('4<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toPopStatistic',500,40)" />
			<%
							}
			%>
				<input type="button" name="btnadd" value="���" class="buttonStyle"
					onclick="popUp('5<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=insert',1000,500)" />
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<input type="button" name="btnadd1" value="ÿһ���������µİ�������ͳ��" class="buttonStyle"
					onclick="parent.document.location.href='../stat/focusCaseInfo.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd2" value="��ϯԱ����İ�������ͳ��" class="buttonStyle"
					onclick="parent.document.location.href='../stat/focusCaseInfoUser.do?method=toMain'" style="display='none'"/>
			</td>
		</tr>
	</table>

</body>
</html:html>
