<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>


<html>
	<head>
		<html:base />
		<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="../../js/common.js"></script>
    <script language="javascript" src="../../js/et/style.js"></script>
    <script language="javascript">

    </script>
    	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../../js/Table.js" ></script>
	</head>

	<body class="listBody">
		

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="listTable" id="changeColorTbl">
				<tr>
					<td class="listTitleStyle">
						����ʱ��
					</td>
					<td class="listTitleStyle">
						������
					</td>
					<td class="listTitleStyle">
						�û�����
					</td>
					<td class="listTitleStyle" >
						��������
					</td>
					<td class="listTitleStyle" >
						��������
					</td>
					<td class="listTitleStyle">
						�����
					</td>
				</tr>
				<logic:iterate id="c" name="list" indexId="i">
					<%
					String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
					%>

					<tr >
						<td>
							<bean:write name="c" property="createTime" />
						</td>
						<td>
							<bean:write name="c" property="rid" />
						</td>
						<td>
							<bean:write name="c" property="actor" />
						</td>
						<td>
							<bean:write name="c" property="question_type" />
						</td>
						<td>
							<bean:write name="c" property="question" />
						</td>
						<td>
							<bean:write name="c" property="answer" />
						</td>
					</tr>
				</logic:iterate>
			
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="listTable">
				<tr>
					<td  class="pageTable" >
						<page:page name="inquiryDetail" style="second" />
					</td>
					<td  class="pageTable" align="right" style="padding-right:10px;width:100px">
					<input type="button" class="buttonStyle" value="����Excel" onclick="parseTbl('changeColorTbl','���ߵ���ά�� ','<%=basePath %>')" />
					</td>
				</tr>
			</table>


	</body>
</html>
