<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter,et.bo.servlet.StaticServlet"%>


<%@ include file="../../../style.jsp"%>
<%UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>���߷����ƽ�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../../js/common.js"></script>
	
	<!-- ������ʽ�ļ� -->
	<script language="javascript" src="../../../js/et/style.js"></script>

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
		
		<tr style="line-height: 21px;">
				<td>
					<input name="educe" type="checkbox" id="educe" onclick="setCheckbox(this)" value="">
				</td>
				<td>
					ddddddddddddd
				</td>
				<td>
					dddddddddddd
				</td>
				<td>
					ddddddddddddddd
				</td>
				<td>
					ddddddddddddddd
				</td>
				<td>
					<img alt="��ϸ" src="../../style/<%=styleLocation%>/images/detail.gif" onclick="" width="16" height="16" border="0" />
					<img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif" onclick="" width="16" height="16" border="0" />					
					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif" onclick="" width="16" height="16" border="0" />

				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="3" class="pageTable">
			</td>
			<td colspan="2" class="pageTable">
				
			</td>
			<td style="text-align:right" class="pageTable">
			<input type="button" name="btnstatistic" value="ͳ��" class="buttonStyle" onclick="" />
			<input type="button" name="btnadd" value="���" class="buttonStyle" onclick="" />
			</td>
		</tr>
		<tr>
		<td colspan="8">

		</td>
		</tr>
		
	</table>
</body>
</html:html>
