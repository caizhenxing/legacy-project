<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>农产品供求库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/common.js"></script>
	<script language="javascript" src="../js/clockCN.js"></script>
	<script language="javascript" src="../js/clock.js"></script>

	<script type="text/javascript">
	function add()
 	{
 		document.forms[0].action="../sad.do?method=toSadLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../sad.do?method=toSadList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/sad" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;农产品供求库
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"/>
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('asdBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					产品名称
				</td>
				<td class="valueStyle">
					<html:text property="productName" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					供求类型
				</td>
				<td class="valueStyle">
					<html:select property="dictSadType" styleClass="writeTextStyle">
						<html:option value="">
	    				选择类型
	    				</html:option>
						<html:options collection="sadTypeList" property="value"
							labelProperty="label" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"/>
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('asdBean','endTime',false);">
				</td>
				<td class="labelStyle"
					onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					onMouseOver="this.style.cursor='pointer';">
					联系地址
				</td>
				<td class="valueStyle">
					<html:text property="custAddr" size="20"
						styleClass="writeTextStyle" readonly="true"/>
					<input name="add" type="button" id="add" value="选择"
					onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					class="buttonStyle" />
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:65px">
					<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option selected="selected">待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option selected="selected">驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("pass".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option selected="selected">已审</option>
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option selected="selected">发布</option>
					<%
					}else{
					%>
					<option value="" selected="selected">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}
					 %>

					</select>
				</td>
			</tr>
			<tr>
			<td class="labelStyle">
					联系电话
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle"/>
				</td>
			<td class="labelStyle">
					工号
				</td>
				<td class="valueStyle">
						<html:select property="sadRid">
						<option value="">请选择</option>
						<logic:iterate id="u" name="userList">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>	
				</td>
				<td class="labelStyle" align="center" colspan="2">
				    <input type="button" class="buttonStyle" name="btnSearch" value="查询"
						  onclick="query()" />
					<input type="reset" class="buttonStyle" value="刷新"  />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="11" class="navigateStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>