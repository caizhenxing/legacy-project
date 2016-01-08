<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>�г������б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript" src="../js/common.js"></script>

</head>

<body class="listBody" onload="equalsCheckbox()">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" width="47">
				ѡ��
			</td>
			<td class="listTitleStyle" width="50">
				��������
			</td>
			<td class="listTitleStyle" width="80">
				�� ϵ ��
			</td>
			<td class="listTitleStyle" width="110">
				��ϵ�绰
			</td>
			<td class="listTitleStyle" width="200">
				��Ʒ����
			</td>
			<td class="listTitleStyle" width="80">
				��������
			</td>
			<td class="listTitleStyle" >
				������
			</td>
			<td class="listTitleStyle">
				���״̬
			</td>
			<td class="listTitleStyle" width="80">
				����
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>

			<tr style="line-height: 21px;">
				<td >
					<input name="educe" type="checkbox" id="educe"
						onclick="setCheckbox(this)"
						value="<bean:write name='c' property='sadId'/>">
				</td>
				<td>
					<bean:write name="c" property="dictSadType" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="custName" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="custTel" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="productName" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="sadTime" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="sadRid" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="state" filter="true" />
				</td>
				<td>
<%--						<leafRight:bodyImg alt="��ϸ" nickName="sad_detail"  src="../style/${styleLocation}/images/detail.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('1<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=detail&id=<bean:write name='c' property='sadId'/>',757,290)"--%>
<%--						</leafRight:bodyImg>--%>
											<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=detail&id=<bean:write name='c' property='sadId'/>',757,290)"
						width="16" height="16" border="0" />
						<leafRight:bodyImg alt="�޸�" nickName="sad_update"  src="../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						onclick="popUp('2<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=update&id=<bean:write name='c' property='sadId'/>',1020,307)"
						</leafRight:bodyImg>
						<leafRight:bodyImg alt="ɾ��" nickName="sad_delete"  src="../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						onclick="popUp('3<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=delete&id=<bean:write name='c' property='sadId'/>',757,290)"
						</leafRight:bodyImg>
<%--					<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif"--%>
<%--						onclick="popUp('1<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=detail&id=<bean:write name='c' property='sadId'/>',757,290)"--%>
<%--						width="16" height="16" border="0" />--%>
<%--					<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif"--%>
<%--						onclick="popUp('2<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=update&id=<bean:write name='c' property='sadId'/>',1020,307)"--%>
<%--						width="16" height="16" border="0" />--%>
<%--					<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif"--%>
<%--						onclick="popUp('3<bean:write name='c' property='sadId'/>','sad.do?method=toSadLoad&type=delete&id=<bean:write name='c' property='sadId'/>',757,290)"--%>
<%--						width="16" height="16" border="0" />--%>
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="4" class="pageTable">
				<jsp:include flush="true" page="../output/inc.jsp?dbType=sad" />
			</td>
			<td colspan="4" class="pageTable">
				<page:page name="sadpageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
			<%
							UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
			%>
<%--			<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle"--%>
<%--					onclick="popUp('statisticWindows','sad.do?method=toPopStatistic',500,40)" />--%>
				<leafRight:btn  nickName="sad_stat" styleClass="buttonStyle" value="ͳ��" onclick="popUp('statisticWindows','sad.do?method=toPopStatistic',500,40)" scopeName="userRoleLeafRightInsession"/>
					
			<%
							}
			%>		
<%--				<input type="button" name="btnadd" value="���" class="buttonStyle"--%>
<%--				 onclick="popUp('windows','sad.do?method=toSadLoad&type=insert',1020,307)" />--%>
				<leafRight:btn  nickName="sad_add" styleClass="buttonStyle" value="���" onclick="popUp('windows','sad.do?method=toSadLoad&type=insert',1020,307)" scopeName="userRoleLeafRightInsession" />
			</td>
		</tr>
		<tr>
		<td colspan="8"><input class="buttonStyle" type="button" name="btnadd1" value="ÿһ��ϯԱ����Ĳ�Ʒ��������ͳ��"
					onclick="parent.document.location.href='./stat/priceAgentProCount.do?method=toMain'" style="display='none'" />
					</td>
		</tr>
	</table>

</body>
</html:html>