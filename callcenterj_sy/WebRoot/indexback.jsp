<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ include file="style.jsp"%>
<script>
function RandomPicture(){
	document.write("<img border='0' src='../RandomPicClient.jsp'/>");
}

</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<html:base />
<%
	java.text.DateFormat f=new java.text.SimpleDateFormat("yyyy年MM月dd日 EEE");
%>
<head>
	<title>用户登录</title>
	<link href="style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<style type="text/css">
<!--
bodyLogin {
	margin-left: 1px;
	margin-top: 1px;
	margin-right: 1px;
	margin-bottom: 1px;
	background-color: #cccccc;
}

.wz1 {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 15px;
	font-weight: normal;
	color: #0264BF;
	text-align: center;
}
.wb {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 25px;
	font-weight: normal;
	color: #CCCCCC;
	text-align: left;
	text-indent: 3px;
}
.xl {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 15px;
	font-weight: normal;
	font-variant: normal;
	color: #0264BF;
	text-align: left;
}
-->
</style>
<script>
if(!opener.closed){			//如果父窗口没关，就关一下
	opener.window.close();	//关闭原来的父窗口
}
function RandomPicture(){
	document.write("<img border='0' src='../RandomPicClient.jsp'/>");
}

function reset(){
	document.forms[0].reset();
}

function judge(){
	if(window.event.keyCode == 13)
	{
		document.forms[0].submit();
	}
}

function selectChange()
{
	document.forms[0].action="./sys/login.do?method=selectChange";
	document.forms[0].submit();
}

</script>
</head>
<body class="bodyLogin">
<br />
<br />
<br />
<br />
<br />
<br />
<html:form action="/sys/login.do?method=login" method="post">
<table width="677" height="194" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="72"><table width="524" height="72" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="478" rowspan="3"><img src="style/<%=styleLocation %>/images/dl1.jpg" width="478" height="72" /></td>
        <td width="46" height="32"><img src="style/<%=styleLocation %>/images/dl2.jpg" width="208" height="36" /></td>
      </tr>
      <tr>
        <td width="204" height="27" background="style/<%=styleLocation %>/images/dl3.jpg" class="rq">日期：<%=f.format(new java.util.Date())%></td>
      </tr>
      <tr>
        <td height="9"><img src="style/<%=styleLocation %>/images/dl4.jpg" width="208" height="9" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="style/<%=styleLocation %>/images/dl5.jpg" width="685" height="137" /></td>
  </tr>
  <tr>
    <td><table width="678" height="172" border="0" cellpadding="0" cellspacing="0" background="style/<%=styleLocation %>/images/dl6.jpg">
      <tr>
        <td width="224" align="center">&nbsp;</td>
        <td width="462" align="center" valign="top"><table width="227" height="64" border="0" cellpadding="0" cellspacing="2">
            <tr>
              <td width="299" align="center"><table width="295" height="14" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="60" height="30" class="wz1">界面风格</td>
                    <td width="285" align="left">
                    
                    <html:select property="select"  styleClass="selectStyle" onchange="selectChange()">
                          <html:option value="spring">春暖花开</html:option>
                          <html:option value="summer">夏日炎炎</html:option>
                          <html:option value="autumn">秋高气爽</html:option>
                          <html:option value="winter">残冬腊月</html:option>
                      </html:select>
                    </td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td align="center"><table width="297" height="65" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="60" height="28" class="wz1">工&nbsp;&nbsp;&nbsp;号</td>
                    <td colspan="2" align="left"><html:text property="userName" size="20" styleClass="writeTextStyle"/></td>
                  </tr>
                  <tr>
                    <td width="60" height="28" class="wz1">密&nbsp;&nbsp;&nbsp;码</td>
                    <td colspan="2" align="left"><html:password property="password" styleClass="mima" size="20" styleClass="writeTextStyle"/></td>
                  </tr>
                  <tr>
                    <td width="60" height="28" class="wz1">验证码</td>
                    <td width="66" align="left"><html:text property="val" size="4" styleClass="writeTextStyle" onkeyup="judge()"/></td>
                    <td width="140" align="left"><img border='0' src='RandomPicClient.jsp' width="55" height="20" /></td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td><table width="205" border="0" cellpadding="0" cellspacing="2">
                <tr>
                  <td valign="top"><br></td><td width="90"><input type="image" name="imageField" src="style/<%=styleLocation %>/images/dl9.jpg" width="92" height="22" class="btnstyle"></td>
                  <td width="80"><input type="image" name="imageField" src="style/<%=styleLocation %>/images/dl11.jpg" width="92" height="22" class="btnstyle" onClick="javascript:window.close();"/></td>
                </tr>
              </table></td>
            </tr>
            <tr>

            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="style/<%=styleLocation %>/images/dl7.jpg" width="685" height="53" /></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
