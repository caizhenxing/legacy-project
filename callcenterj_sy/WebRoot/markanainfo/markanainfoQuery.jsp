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

	<title>市场分析库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>

	<script language="javascript" src="../js/clockCN.js"></script>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
<link REL=stylesheet href="../markanainfo/css/divtext.css" type="text/css"/>
<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>


	<script type="text/javascript">
function query()
 	{
 		document.forms[0].action="../markanainfo.do?method=toMarkanainfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 	
 
 </script>

</head>

<body class="conditionBody">
	<html:form action="/markanainfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;市场分析库
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('markanainfoBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					主&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle">
					<html:text property="chiefTitle" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					责任编辑
				</td>
				<td class="valueStyle">
					<html:text property="chargeEditor" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					评&nbsp;&nbsp;&nbsp;&nbsp;别
				</td>
				<td class="valueStyle">
					<html:select property="dictCommentType" styleClass="selectStyle" style="width: 75px;">
						<html:option value="">全部</html:option>
						<html:option value="周评">周评</html:option>
						<html:option value="月评">月评</html:option>
						<html:option value="季评">季评</html:option>
						<html:option value="年评">年评</html:option>
					</html:select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" class="buttonStyle" value="查询" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('markanainfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle">
					<html:text property="summary" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle" >
					品&nbsp;&nbsp;&nbsp;&nbsp;种
				</td>
				<td class="valueStyle">
<%--						<html:text property="dictProductType" styleClass="writeTextStyle" size="10"/>--%>
						<html:select property="dictProductType" styleClass="writeTextStyle">
							<option value="">请选择</option>
							<html:options collection="list" property="value"
								labelProperty="label" />
						</html:select>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle">
					<%
					String str_state = request.getParameter("state");
					if("firstDraft".equals(str_state)){
					%>
					<option value="">全部</option>
						<option selected="selected">初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>						
						<option>发布</option>
					<%
					}else if("firstCensor".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option selected="selected">一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>						
						<option>发布</option>
					<%
					}else if("firstCensorBack".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option selected="selected">一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>						
						<option>发布</option>
					<%
					}else if("secondCensor".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option selected="selected">二审</option>
						<option>二审驳回</option>						
						<option>发布</option>
					<%
					}else if("secondCensorBack".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option selected="selected">二审驳回</option>						
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>						
						<option selected="selected">发布</option>
					<%
					}else{
					%>					
						<option value="" selected="selected">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>						
						<option>发布</option>
					<%
					}
					 %>
<%--						<option value="">全部</option>--%>
<%--						<option>初稿</option>--%>
<%--						<option>一审</option>--%>
<%--						<option>一审驳回</option>--%>
<%--						<option>二审</option>--%>
<%--						<option>二审驳回</option>--%>
<%--						<option>三审</option>--%>
<%--						<option>三审驳回</option>--%>
<%--						<option>发布</option>--%>
					</select>
				</td>
				<td class="labelStyle" align="center">
					<input type="reset" class="buttonStyle" value="刷新"  >
				</td>
			</tr>
			<tr>
				<td class="labelStyle" height="1px" colspan="9"></td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>