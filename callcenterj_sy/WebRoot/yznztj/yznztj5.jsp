<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����ũ���ƽ�</title>
<link href="../info/1/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
<div class="div">
	<table width="1240" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
      		<td height="40" class="Title">����ũ���ƽ�</td>
      		<td class="Slogan" colspan="5">��ϵũҵ ��ϵũ�� ��ϵũ��</td>
      	</tr>
		<tr style="text-align: center;" class="Subtitle2" class="Title2">
			<td style="width: 15%">��ƷͼƬ</td>
            <td style="width: 10%">��Ʒ���</td>
            <td style="width: 10%">��Ʒ����</td>
            <td style="width: 15%">��Ʒ����</td>
            <td style="width: 25%">ʹ�÷�Χ</td>
            <td style="width: 25%">ʹ�÷���</td>
      	  </td>
		</tr>
	</table>

	<marquee direction="up" width="100%" height="530" SCROLLAMOUNT="5">
	<table width="1240" align="center" style="vertical-align: top;">
		<logic:iterate id="c" name="list" indexId="i">
		<%
		String odd =  i.intValue() % 2 == 0 ? "#9CB9D7" : "";
		%>
		<tr class="Darkblue3" bgcolor="<%=odd %>">
			<td style="width: 15%">
				<img src="yznztj.do?getMethod=toYznztjPhoto&&id=<bean:write name="c" property="id" />">
			</td>
			<td style="width: 10%">
				<bean:write name="c" property="sort" />
			</td>
			<td style="width: 10%">
				<bean:write name="c" property="productName" />
			</td>
			<td style="width: 15%">
				<bean:write name="c" property="trait" />
			</td>
			<td style="width: 25%;text-align: left;">
				<bean:write name="c" property="scope" />
			</td>
			<td style="width: 25%;text-align: left;">
				<bean:write name="c" property="method" />
			</td>
		</tr>
	</logic:iterate>
	</table>
	</marquee>
	
</div>
</body>
</html>