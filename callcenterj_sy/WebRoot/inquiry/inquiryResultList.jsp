<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>

<html>
	<head>
		<html:base />
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/common.js"></script>
	</head>

	<body class="listBody">


		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle">
					ѡ��
				</td>
				<td class="listTitleStyle">
					�������
				</td>
				<td class="listTitleStyle">
					�ʾ�����
				</td>
				<td class="listTitleStyle">
					�������
				</td>
				<td class="listTitleStyle">
					�� ֯ ��
				</td>
				<td class="listTitleStyle">
					��ʼʱ��
				</td>
				<td class="listTitleStyle">
					����ʱ��
				</td>
				<td class="listTitleStyle">
					���״̬
				</td>
				<td class="listTitleStyle">
					����
				</td>

			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
				String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
				%>

				<tr style="line-height: 21px;">
					<td>
						<input name="educe" type="checkbox" id="educe" onclick="setCheckbox(this)" value="<bean:write name='c' property='id'/>">
					</td>
					<td>
						<bean:write name="c" property="inquiryTypeLabel" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="topic" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="organiztion" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="organizers" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="beginTime" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="endTime" filter="fslse" />
					</td>
					<td>
						<bean:write name="c" property="reportState" filter="true" />
					</td>

					<td>
						<input type="button" name="btnadd" value="����"
							onclick="popUp('1<bean:write name='c' property='id'/>','inquiry.do?method=toDetail&id=<bean:write name='c' property='id'/>',1015,675)" width="18"
							height="18" border="0" class="buttonStyle" />
						<input type="button" name="btnadd" value="����"
							onclick="popUp('2<bean:write name='c' property='id'/>','inquiry.do?method=toReportLoad&id=<bean:write name='c' property='id'/>',1015,675)" width="18"
							height="18" border="0" class="buttonStyle" />
						<input type="button" name="btnadd" value="���"
							onclick="popUp('3<bean:write name='c' property='id'/>','inquiry.do?method=toStat&id=<bean:write name='c' property='id'/>',1015,675)" width="18"
							height="18" border="0" class="buttonStyle" />
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="3" class="pageTable">
					<jsp:include flush="true" page="../output/inc.jsp?dbType=inquiryresult" />
				</td>
				<td colspan="5" class="pageTable">
					<page:page name="inquiryTurning" style="second" />
				</td>
				<td class="pageTable">
				<%
							UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
							if(ub!=null){
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
				%>
					<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle" onclick="popUp('statisticWindows','inquiryResult.do?method=toPopStatistic',500,40)" />
				<%
							}
							}
				%>	
				</td>
			</tr>
		</table>


	</body>
</html>
