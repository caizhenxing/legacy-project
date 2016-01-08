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

<html:html locale="true">
<head>
	<html:base />

	<title>����׷�ٴ���Ļ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../js/common.js"></script>

</head>

<body class="listBody">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="listTable">
			<tr>				
				<td class="listTitleStyle" width="10%">
					����
				</td>
				<td class="listTitleStyle"  width="25%">
					����
				</td>
				<td class="listTitleStyle" >
					ժҪ
				</td>				
				<td class="listTitleStyle" width="10%">
					����
				</td>
			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
				%>

				<tr style="line-height: 21px;">					
					<td >
						<bean:write name="c" property="ftPeriod" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="ftTitle" filter="true" />
					</td>
					<td>
						<bean:write name="c" property="ftSummary" filter="true" />
					</td>
					

					<td>
						<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('focusTracking','focusTracking.do?method=toFocusTrackingLoad&type=detail&id=<bean:write name='c' property='ftId'/>',700,270)"
							width="16" height="16" border="0" />
<%--						<leafRight:bodyImg alt="�޸�" nickName="focuspursue_update"  src="../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('2<bean:write name='c' property='ftId'/>','focusTracking.do?method=toFocusTrackingLoad&type=update&id=<bean:write name='c' property='ftId'/>',700,220)"--%>
<%--						</leafRight:bodyImg>--%>
<%--						<leafRight:bodyImg alt="ɾ��" nickName="focuspursue_delete"  src="../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('3<bean:write name='c' property='ftId'/>','focusTracking.do?method=toFocusTrackingLoad&type=delete&id=<bean:write name='c' property='ftId'/>',700,220)"--%>
<%--						</leafRight:bodyImg>--%>
<%--						<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif"--%>
<%--							onclick="popUp('2<bean:write name='c' property='ftId'/>','focusTracking.do?method=toFocusTrackingLoad&type=update&id=<bean:write name='c' property='ftId'/>',1010,700)"--%>
<%--							width="16" height="16" border="0" />--%>
<%--						<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif"--%>
<%--							onclick="popUp('3<bean:write name='c' property='ftId'/>','focusTracking.do?method=toFocusTrackingLoad&type=delete&id=<bean:write name='c' property='ftId'/>',1010,585)"--%>
<%--							width="16" height="16" border="0" />--%>
						<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('focusTracking','focusTracking.do?method=toFocusTrackingLoad&type=update&id=<bean:write name='c' property='ftId'/>',700,220)"
							width="16" height="16" border="0" />
						<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif"
							onclick="popUp('focusTracking','focusTracking.do?method=toFocusTrackingLoad&type=delete&id=<bean:write name='c' property='ftId'/>',700,220)"
							width="16" height="16" border="0" />	
					</td>
				</tr>
			</logic:iterate>
			<tr>
				
				<td colspan="3" class="pageTable">
					<page:page name="focusTrackingpageTurning" style="second" />
				</td>
				<td style="text-align:right" class="pageTable" width="80">
					<input type="button" name="btnadd" value="���" class="buttonStyle"
						onclick="popUp('focusTracking','focusTracking.do?method=toFocusTrackingLoad&type=insert',700,220)" />
				</td>
			</tr>
			
		</table>
</body>
</html:html>	