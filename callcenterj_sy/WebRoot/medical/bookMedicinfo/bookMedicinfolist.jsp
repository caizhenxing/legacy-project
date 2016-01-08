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

	<title>预约医疗列表</title>

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
					选择
				</td>
				<td class="listTitleStyle" width="70">
					受理日期
				</td>
				<td class="listTitleStyle" width="100">
					预约就诊时间
				</td>
				<td class="listTitleStyle" >
					就诊类型
				</td>
				<td class="listTitleStyle">
					咨询内容
				</td>
				<td class="listTitleStyle" width="200">
					热线答复
				</td>
				<td class="listTitleStyle">
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
					<td >
		  				<input name="educe" type="checkbox" id="educe"
							onclick="setCheckbox(this)" value="<bean:write name='c' property='id'/>">
		  			</td>
					<td>
						<bean:write name="c" property="createTime" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="bookVisitTime" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="dictServiceType" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="contents" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="contents" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="state" filter="true" />
					</td>

					<td>
						<img alt="详细"
							src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('1<bean:write name='c' property='id'/>','bookMedicinfo.do?method=toBookMedicinfoLoad&type=detail&id=<bean:write name='c' property='id'/>',750,680)"
							width="16" height="16" border="0" />
						<leafRight:bodyImg alt="修改" nickName="bookMedicinfo_update"  src="../../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						onclick="popUp('2<bean:write name='c' property='id'/>','bookMedicinfo.do?method=toBookMedicinfoLoad&type=update&id=<bean:write name='c' property='id'/>',1000,680)"
						</leafRight:bodyImg>
						<leafRight:bodyImg alt="删除" nickName="bookMedicinfo_delete"  src="../../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">
						 onclick="popUp('3<bean:write name='c' property='id'/>','bookMedicinfo.do?method=toBookMedicinfoLoad&type=delete&id=<bean:write name='c' property='id'/>',750,680)"
						</leafRight:bodyImg>
<%--						<img alt="修改"
<%--							src="../../style/<%=styleLocation%>/images/update.gif"--%>
<%--							onclick="popUp('2<bean:write name='c' property='id'/>','bookMedicinfo.do?method=toBookMedicinfoLoad&type=update&id=<bean:write name='c' property='id'/>',1000,680)"--%>
<%--							width="16" height="16" border="0" />--%>
<%--						<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"--%>
<%--							onclick="popUp('3<bean:write name='c' property='id'/>','bookMedicinfo.do?method=toBookMedicinfoLoad&type=delete&id=<bean:write name='c' property='id'/>',750,680)"--%>
<%--							width="16" height="16" border="0" />--%>
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="4" class="pageTable">
					<jsp:include flush="true" page="../../output/inc.jsp?dbType=bookmedicinfo" />
				</td>
				<td colspan="3" class="pageTable">
					<page:page name="bookMedicinfopageTurning" style="second" />
				</td>
				<td style="text-align:right" class="pageTable">
				<%
							UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
				%>
					<input type="button" name="btnstatistic" value="统计" class="buttonStyle"
						onclick="popUp('statisticWindows','bookMedicinfo.do?method=toPopStatistic',500,40)" />
				<%
							}
				%>		
					<input type="button" name="btnadd" value="添加" class="buttonStyle"
						onclick="popUp('bookMedicinfoWindows','bookMedicinfo.do?method=toBookMedicinfoLoad&type=insert',1000,680)" />
				</td>
			</tr>
		</table>

</body>
</html:html>