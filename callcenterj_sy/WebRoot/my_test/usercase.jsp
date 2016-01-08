<%@ page language="java" contentType="text/html;charset=GB2312" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0088)http://192.168.1.16:8080/callcenterj_sy//stat/usercase.do?method=toDisplay&chartType=bar -->
<HTML><HEAD><TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312"><LINK 
href="usercase.files/style.css" type=text/css rel=stylesheet>
<SCRIPT>
		</SCRIPT>

<META content="MSHTML 6.00.2900.3354" name=GENERATOR></HEAD>
<BODY class=conditionBody>
<TABLE width=800 align=center>
  <TBODY>
  <TR>
    <TD align=middle><FONT color=red>
      <DIV id=showTitle 
  style="WIDTH: 800px; TEXT-ALIGN: center"></DIV></FONT></TD></TR></TBODY></TABLE>
<CENTER><IMG src="usercase.files/DisplayChart.png" border=1> 
</CENTER>
<Input   Type=Button   Value="保存"   OnClick="document.execCommand('SaveAs',false,'.\\<%=excellence.common.util.time.TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd-hh-mm-ss")%>.xls')">
</BODY></HTML>
