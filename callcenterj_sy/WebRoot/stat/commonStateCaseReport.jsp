<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
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
    <script language="javascript" src="../../js/common.js"></script>
  		<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js" ></script>
  </head>
  
  <body class="listBody">
		<table 	id="tbl1" width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle" width="15%">
		    <logic:empty name="caseRid">������</logic:empty>
		    <logic:notEmpty name="caseRid">����</logic:notEmpty>
		    </td>
		     <td class="listTitleStyle" width="15%">
		   ����
		    </td>
		    <td class="listTitleStyle" width="15%">
			 ԭʼ	
		    </td>
		     <td class="listTitleStyle" width="20%">
			����
		    </td>
		     <td class="listTitleStyle" width="20%">
		   ����
		    </td>
		    <td class="listTitleStyle" width="15%">
		 	�ϼ�(��)
		    </td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"oddStyle":"evenStyle";
			%>
		  <tr>
					<td width="80px">
						<bean:write name="c" property="col1" filter="false" />
					</td>
					<td >
						<bean:write name="c" property="����" filter="false" />
					</td>
						<td >
						<bean:write name="c" property="ԭʼ" filter="false" />
					</td>
						<td >
						<bean:write name="c" property="����" filter="false" />
					</td>
						<td >
						<bean:write name="c" property="����" filter="false" />
					</td>
					<td >
						<bean:write name="c" property="rowSum" filter="false" />
					</td>
				</tr>
		  </logic:iterate>
		    		  <tr>
					<td colspan="6" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
					<div style="text-align:right;">
						<input type="button" class="buttonStyle" value="����Excel" onclick="parseTbl('tbl1','��ϯ������ͳ��','<%=basePath %>')" />
					</div>
					</td>
				</tr>
		</table>
  </body>
</html:html>