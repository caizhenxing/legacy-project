<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ include file="../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>�������</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
    <!--
	body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	}
	-->
</style>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
<script type="text/javascript">
	function goConference()
	{
		var confNum = document.getElementById('confNum').value;
		if(confNum>0)
		{
			opener.document.getElementById('txtConfNum').value=confNum;
			opener.document.getElementById('btnExecConference').click();
			window.close();
		}
		else
		{
			alert("��ѡ������!");
		}
	}
</script>
</head>

<body>
	<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
		<tr>
			<td height="33" align="right" class="navigateStyle" style="text-align:right;margin-right:10px;">
				ר���б�
				<select name="dict_cust_type" onchange="selectType()"
					class="writeTypeStyle" id="confNum">
					<option value="1" class="writeTypeStyle">
						��ר��
					</option>
					<option value="2" class="writeTypeStyle">
						��ר��
					</option>
					<option value="3" class="writeTypeStyle">
						��ר��
					</option>
				</select>
				��ǰ��������:
				<input type="button" value="���߽���" onclick="goConference()" />&nbsp;
			</td>
		</tr>
	</table>
</body>

</html:html>
