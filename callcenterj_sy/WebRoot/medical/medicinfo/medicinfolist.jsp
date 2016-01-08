<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>

<html:html locale="true">
<head>
	<html:base />

	<title>��ͨҽ���б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>

</head>

<body class="listBody">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="50">
					ѡ��
				</td>
				<td class="listTitleStyle" >
					��������
				</td>
				<td class="listTitleStyle" >
					������
				</td>
				<td class="listTitleStyle">
					��ѯ����
				</td>
				<td class="listTitleStyle" >
					���ߴ�
				</td>
				<td class="listTitleStyle">
					���״̬
				</td>
				<td class="listTitleStyle" width="70">
					����ר��
				</td>				
				<td class="listTitleStyle" width="80">
					����
				</td>

			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
	String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
				%>

				<tr��style="line-height: 21px">
					<td >
		  				<input name="educe" type="checkbox" id="educe"
							onclick="setCheckbox(this)" value="<bean:write name='c' property='id'/>">
		  			</td>
					<td >
						<bean:write name="c" property="createTime" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="medicRid" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="contents" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="reply" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="state" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="expertName" filter="true" />
					</td>
					<td >
						<img alt="��ϸ"
							src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('1<bean:write name='c' property='id'/>','medicinfo.do?method=toMedicinfoLoad&type=detail&id=<bean:write name='c' property='id'/>',610,485)"
							width="16" height="16" border="0" />
 <leafRight:bodyImg alt="�޸�" nickName="medicinfo_update"  src="../../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						onclick="popUp('2<bean:write name='c' property='id'/>','medicinfo.do?method=toMedicinfoLoad&type=update&id=<bean:write name='c' property='id'/>',900,480)"
						</leafRight:bodyImg>
						<leafRight:bodyImg alt="ɾ��" nickName="medicinfo_delete"  src="../../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						 onclick="popUp('3<bean:write name='c' property='id'/>','medicinfo.do?method=toMedicinfoLoad&type=delete&id=<bean:write name='c' property='id'/>',610,485)"
						</leafRight:bodyImg>
	<%--					<img alt="�޸�"
	<%--						src="../../style/<%=styleLocation%>/images/update.gif"--%>
	<%--						onclick="popUp('2<bean:write name='c' property='id'/>','medicinfo.do?method=toMedicinfoLoad&type=update&id=<bean:write name='c' property='id'/>',900,480)"--%>
	<%--						width="16" height="16" border="0" />--%>
	<%--					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"--%>
	<%--						onclick="popUp('3<bean:write name='c' property='id'/>','medicinfo.do?method=toMedicinfoLoad&type=delete&id=<bean:write name='c' property='id'/>',610,485)"--%>
	<%--						width="16" height="16" border="0" />--%>
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="3" class="pageTable">
					<jsp:include flush="true" page="../../output/inc.jsp?dbType=medicinfo" />
				</td>
				<td colspan="4" class="pageTable">
					<page:page name="medicinfopageTurning" style="second"/>
				</td>
				<td style="text-align:right" class="pageTable">
				<%
							UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
				%>
					<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle"
						onclick="popUp('statisticWindows','medicinfo.do?method=toPopStatistic',500,40)" />
				<%
							}
				%>		
					<input type="button" name="btnadd" value="���" class="buttonStyle"
						onclick="popUp('medicinfoWindows','medicinfo.do?method=toMedicinfoLoad&type=insert',900,480)" />
				</td>
			</tr>
		</table>

</body>
</html:html>