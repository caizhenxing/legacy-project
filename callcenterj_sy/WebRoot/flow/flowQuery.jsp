<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "flow.do?method=toFlowList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}

   	</script>
	<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></script>

</head>

<body class="conditionBody">
	<html:form action="/flow/flow" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;��˹���
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					�״��ύʱ��
				</td>
				<td class="valueStyle" colspan="2">
					<html:text property="begin1" styleClass="writeTextStyle2" size="10" />
					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('flow','begin1',false);">
					��
					<html:text property="end1" styleClass="writeTextStyle2" size="10" />
					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('flow','end1',false);">
				</td>
				<td class="queryLabelStyle">
					�״��ύ��
				</td>
				<td class="valueStyle">
<%--					<html:text property="submit_id" styleClass="writeTextStyle2"/>--%>
						<html:select property="submit_id" styleClass="selectStyle">
 							<html:options collection="uList"
								property="value" labelProperty="label" />
						</html:select>
				</td>
				<td class="queryLabelStyle">
					�� �� ��
				</td>
				<td class="valueStyle" >
					<jsp:include flush="true" page="incType.jsp" />
				</td>
				<td class="queryLabelStyle" align="center">
					<input type="button" name="btnSearch" value="��ѯ"
						class="buttonStyle" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle">
					����ύʱ��
				</td>
				<td class="valueStyle" colspan="2">
					<html:text property="begin2" styleClass="writeTextStyle2" size="10" />
					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('flow','begin2',false);">
					��
					<html:text property="end2" styleClass="writeTextStyle2" size="10" />
					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('flow','end2',false);">
				</td>

				<td class="queryLabelStyle">
					����ύ��
				</td>
				<td class="valueStyle">
					<%
					Map infoMap = (Map) request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String) infoMap.get("userId");
					String sub = request.getParameter("sub");
					if(sub == null || sub.equals("no") || userId == null){
						userId = "";
					}
					%>
<%--					<input type="text" name="submit_id_end" value="<%=userId %>" class="writeTextStyle2">--%>
					<html:select property="submit_id_end" styleClass="selectStyle" styleId="submit_id_end" value="<%=userId %>">
 							<html:options collection="uList"
								property="value" labelProperty="label" />
					</html:select>
				</td>
				<td class="queryLabelStyle">
					��ǰ״̬
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:100">
						<option style="color: black;" value="">
							���״̬
						</option>
						<option style="color: green;">
							����
						</option>
						<option style="color: green;">
							����
						</option>
						<option style="color: green;">
							����
						</option>
						<option style="color: blue;">
							һ��
						</option>
						<option style="color: blue;">
							����
						</option>
						<option style="color: blue;">
							����
						</option>
						<option style="color: black;">
							����
						</option>
					</select>
					<select name="is_read" id="is_read" class="selectStyle" style="width:135">
						<option style="color: black;" value="">
							����״̬
						</option>
						<option style="color: blue;" value="0">
							δ����
						</option>
						<option style="color: green;" value="1">
							�Ѳ���
						</option>
					</select>
				</td>

				<td class="queryLabelStyle" align="center">
					<input type="reset" value="ˢ��" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px" class="buttonAreaStyle">
				<td colspan="8">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>

