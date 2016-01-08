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
<p>&nbsp;</p>
<p>&nbsp;</p>
<html:form action="/sys/login.do?method=login" method="post">
<table width="205" height="152" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td rowspan="3"><img src="style/<%=styleLocation %>/images/dl1.jpg" width="472" height="68" /></td>
        <td><img src="style/<%=styleLocation %>/images/dl2.jpg" width="206" height="32" /></td>
      </tr>
      <tr>
        <td height="27" background="style/<%=styleLocation %>/images/dl3.jpg" class="shijian">
        	<div id='time'style="color:white;">
            <script>document.getElementById('time').innerHTML=new Date().toLocaleString();
            setInterval("document.getElementById('time').innerHTML=new Date().toLocaleString();",1000);
			</script>
            </div>
        </td>
      </tr>
      <tr>
        <td><img src="style/<%=styleLocation %>/images/dl4.jpg" width="206" height="9" /></td>
      </tr>
      <tr>
        <td colspan="2"><img src="style/<%=styleLocation %>/images/dl5.jpg" width="677" height="135" /></td>
      </tr>
      <tr>
        <td height="156" colspan="2"><table width="677" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30"><img src="style/<%=styleLocation %>/images/dl6.jpg" width="30" height="156" /></td>
            <td width="246"><img src="style/<%=styleLocation %>/images/dl12.jpg" width="246" height="156" /></td>
            <td width="378" align="center" background="style/<%=styleLocation %>/images/dl13.jpg"><table width="243" height="108" border="0" cellpadding="0" cellspacing="3">
              <tr>
                <td width="73" align="center" class="jimian">界面风格</td>
                <td colspan="3" align="right">
                <html:select property="select"  styleClass="xiala">
                          <html:option value="spring">春暖花开</html:option>
                          <html:option value="summer">夏日炎炎</html:option>
                          <html:option value="autumn">秋高气爽</html:option>
                          <html:option value="winter">残冬腊月</html:option>
                      </html:select>
                </td>
              </tr>
              <tr>
                <td align="center" class="jimian">工&nbsp;&nbsp;&nbsp;&nbsp;号</td>
                <td colspan="3" align="right"><html:text property="userName" size="20" styleClass="wenben"/></td>
              </tr>
              <tr>
                <td align="center" class="jimian">密&nbsp;&nbsp;&nbsp;&nbsp;码</td>
                <td colspan="3" align="right"><html:password property="password" styleClass="mima" size="20" styleClass="wenben"/></td>
              </tr>
<%--              <tr>--%>
<%--                <td align="center" class="jimian">验&nbsp;&nbsp;&nbsp;&nbsp;证</td>--%>
<%--                <td width="75" align="right"><html:text property="val" size="4" styleClass="yanzhengma" onkeyup="judge()"/></td>--%>
<%--                <td width="80" align="left"><img border='0' src='RandomPicClient.jsp' width="55" height="20" /></td>--%>
<%--                <td width="50" align="left">&nbsp;</td>--%>
<%--              </tr>--%>
              <tr>
                <td colspan="4" align="center" class="jimian">
                <table width="238" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="120" align="left"><input type="image" name="imageField" src="style/<%=styleLocation %>/images/dl9.jpg" width="92" height="22" class="btnstyle"></td>
                    <td width="118" align="right"><input type="image" name="imageField" src="style/<%=styleLocation %>/images/dl11.jpg" width="92" height="22" class="btnstyle" onClick="javascript:window.close();"/></td>
                  </tr>
                </table>
                </td>
                </tr>
            </table></td>
            <td width="23"><img src="style/<%=styleLocation %>/images/dl14.jpg" width="66" height="156" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td colspan="2"><img src="style/<%=styleLocation %>/images/dl7.jpg" width="677" height="51" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
