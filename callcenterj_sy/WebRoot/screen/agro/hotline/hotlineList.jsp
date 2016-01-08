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

	<title>热线服务推介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../../js/common.js"></script>
	
	<!-- 引入样式文件 -->
	<script language="javascript" src="../../../js/et/style.js"></script>

</head>

<body class="listBody" onload="equalsCheckbox()">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td width="50">
				选择
			</td>
			<td>
				受理时间
			</td>
			<td>
				咨询内容
			</td>
			<td>
				热线答复
			</td>
			<td>
				审核状态
			</td>
			<td width="80">
				操作
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
					<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif" onclick="" width="16" height="16" border="0" />
					<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif" onclick="" width="16" height="16" border="0" />					
					<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif" onclick="" width="16" height="16" border="0" />

				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="3" class="pageTable">
			</td>
			<td colspan="2" class="pageTable">
				
			</td>
			<td style="text-align:right" class="pageTable">
			<input type="button" name="btnstatistic" value="统计" class="buttonStyle" onclick="" />
			<input type="button" name="btnadd" value="添加" class="buttonStyle" onclick="" />
			</td>
		</tr>
		<tr>
		<td colspan="8">

		</td>
		</tr>
		
	</table>
</body>
</html:html>
