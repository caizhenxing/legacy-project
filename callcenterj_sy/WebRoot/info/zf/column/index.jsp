<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title></title>
<style type="text/css">
body{margin:0px; text-align:center;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');}
.table_title,.talbe_conton{ border-collapse:collapse; width:98%;}
.table_title td,.talbe_conton td{padding:5px;border:#0066b3 1px solid;}
.table_title td{font:18px "宋体"; font-weight:bold; text-align:center;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#0066b3', endColorStr='#37d3fc', gradientType='0'); color:#FFFFFF;}
.talbe_conton{background-color:#FFFFFF;}
.talbe_conton td{font:18px "宋体";text-align:center;font-weight:bold; }
.odd{background-color:#eafaff;}
</style>
</head>

<body>
<marquee direction="up" width="100%" height="490" SCROLLAMOUNT="5">

<table align="center" width="80%">
   <tr>
    <td>
	<iframe src="../../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colday" 
		width="100%" height="430" scrolling="no">
	</iframe>
	<br><br>
	</td>
   </tr>
   <tr>	
   	<td>
	<iframe src="../../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colmonth" 
		width="100%" height="430" scrolling="no">
	</iframe>
	<br><br>
	</td>
   </tr>
   <tr>
    <td>
	<iframe src="../../../stat/callCountStat.do?method=toDisplay&chartType=bar&dateType=colyear" 
		width="100%" height="430" scrolling="no">
	</iframe>
	</td>
   </tr>
  </table>
  
  </marquee>
</body>
</html>
