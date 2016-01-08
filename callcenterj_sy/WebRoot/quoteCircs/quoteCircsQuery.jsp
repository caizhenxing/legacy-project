<%@ page language="java" import="java.util.*" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
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
	<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></script>
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "quoteCircs.do?method=toList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}

   	</script>

  </head>
  
<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/quoteCircs/quoteCircs" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;����Ա����ͳ��
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="13" />
					<img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('quoteCircs','beginTime',false);">
				</td>
				<td class="queryLabelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="13" />
					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('quoteCircs','endTime',false);">
				</td>
				<td class="queryLabelStyle">
					����Ա���
				</td>
				<td class="valueStyle">
					<html:text property="custId" styleClass="writeTextStyle"
						size="15" />
				</td>
				<td class="queryLabelStyle">
					<input type="button" class="buttonStyle" name="btnSearch" value="��ѯ" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle">
					�û���ַ
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="35" styleClass="writeTextStyle" />
					<input type="button" name="btnadd" value="ѡ��" onClick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" onmousemove="this.style.cursor='pointer';" style="width:30px" class="buttonStyle"/>
<%--				<input type="button" name="button" value="ѡ��" onclick="window.open('../priceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" onmousemove="this.style.cursor='pointer';" style="width:30px" class="buttonStyle">--%>
				</td>
				<td class="queryLabelStyle">
					����Ա
				</td>
				<td class="valueStyle">
					<html:select property="custName">
					<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.custName}">${u.custName}</html:option>						
						</logic:iterate>
					</html:select>
				</td>	
				<td class="queryLabelStyle">
					
					<input type="reset" class="buttonStyle" value="ˢ��"  onClick="parent.bottomm.document.location=parent.bottomm.document.location;">
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

