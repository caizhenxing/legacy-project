<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ŀ��ѯ��ͳ��</title>
<link href="style.css" rel="stylesheet" type="text/css" />

</head>

<body>

	<table width="1240" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
      		<td height="40" class="Title" colspan="2">��Ŀ��ѯ��ͳ��</td>
     		<td class="Slogan">���߼ܽ��� ��Ϣ��ũ�� ��������ũ</td>
      	</tr>
     </table>
<marquee direction="up" width="100%" height="490" SCROLLAMOUNT="5">

<table align="center" width="80%">
   <tr>
    <td>
	<iframe src="../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colday" 
		width="100%" height="430" scrolling="no">
	</iframe>
	<br><br>
	</td>
   </tr>
   <tr>	
   	<td>
	<iframe src="../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colmonth" 
		width="100%" height="430" scrolling="no">
	</iframe>
	<br><br>
	</td>
   </tr>
   <tr>
    <td>
	<iframe src="../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colyear" 
		width="100%" height="430" scrolling="no">
	</iframe>
	</td>
   </tr>
  </table>
  
  </marquee>
</body>
</html>
