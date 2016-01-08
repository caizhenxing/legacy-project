<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../js/common.js"></script>
</head>

<body class="listBody">

	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle">
				��������
			</td>
			<td class="listTitleStyle">
				��������
			</td>
			<td class="listTitleStyle">
				����������
			</td>
			<td class="listTitleStyle">
				���������
			</td>
			<td class="listTitleStyle">
				����ʱ��
			</td>
			<td class="listTitleStyle" width="120">
				����
			</td>
		</tr>
		<logic:iterate id="pagelist" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr style="line-height: 21px;">
				<td>
					<bean:write name="pagelist" property="topic" />
				</td>
				<td>
					<bean:write name="pagelist" property="contents" />
				</td>
				<td>
					<bean:write name="pagelist" property="principal" />
				</td>
				<td>
					<bean:write name="pagelist" property="actor" />
				</td>
				<td>
					<bean:write name="pagelist" property="eventdate" />
				</td>
				<td>

					<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif"  width="16" height="16" border="0" 
						onclick="popUp('1<bean:write name="pagelist" property="id"/>','event.do?method=toEventLoad&type=detail&id=<bean:write name="pagelist" property="id"/>',500,400)"/>
					<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif"  width="16" height="16" border="0" 
						onclick="popUp('2<bean:write name="pagelist" property="id"/>','event.do?method=toEventLoad&type=update&id=<bean:write name='pagelist' property='id'/>',550,400)"/>
					<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif"  width="16" height="16" border="0" 
						onclick="popUp('3<bean:write name="pagelist" property="id"/>','event.do?method=toEventLoad&type=delete&id=<bean:write name='pagelist' property='id'/>',500,400)"/>
					<input type="button" name="btnadd" value="����" class="buttonStyle" onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=insert&event_id=<bean:write name='pagelist' property='id'/>',550,175)" />
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="5" class="pageTable">
				<page:page name="eventTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
				<input type="button" name="btnadd" value="���" class="buttonStyle" onclick="popUp('','event.do?method=toEventLoad&type=insert',550,225)" />
			</td>
		</tr>
	</table>

</body>
</html:html>

