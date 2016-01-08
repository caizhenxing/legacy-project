<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
	<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
	<%@ include file="../style.jsp"%>
	<html:base />
	<%
	java.text.DateFormat f=new java.text.SimpleDateFormat("yyyy年MM月dd日 EEE");
	%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>excellence-tech</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
a:link {
	color: #FFFFFF;
	text-decoration: none;
}
a:visited {
	color: #003366;
	text-decoration: none;
}
a:hover {
	color: #C7E2F3;
	text-decoration: none;
}
a:active {
	color: #FFFFFF;
	text-decoration: none;
}
-->
</style>

<style type="text/css">
<!--
.STYLE2 {color: #FFFFFF}
.selectStyle {
	color: #3167A5;
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 25px;
	font-weight: normal;
	background-color: #FFFFFF;
}
-->
</style>

		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="../js/common.js"></script>
	<script language="javascript">
		function goRefresh()
		{
			parent.window.location.reload();   
		}
		function goLogin()
		{
			parent.window.location="../index2.jsp";  
		}
	</script>
	</head>

	<body>
<%--		<table border="0" cellspacing="0" cellpadding="0">--%>
<%--  <tr>--%>
<%--    <td width="686" background="../style/<%=styleLocation%>/images/sy2.jpg"> --%>
<%--					<img src="../style/<%=styleLocation%>/images/sy1.jpg" width="688"--%>
<%--						height="66" />--%>
<%--				</td>--%>
<%--    <td width="527" align="center" background="../style/<%=styleLocation%>/images/zx/sy5.jpg">--%>
<%--    <span class="rq"><%=f.format(new java.util.Date())%>--%>
<%--    <br />--%>
<%--        <br />--%>
<%--      <table width="444" height="24" border="0" cellpadding="0" cellspacing="2">--%>
<%--      --%>
<%--      <tr>--%>
<%--      <td width="160" height="20" class="anys"><newtree:enuRootNav tree="modTreeSession" imgClass="navAgentImg" skins="<%=styleLocation %>" childAction="./agentNav.do?method=toMainControl" childTarget="blank" /></td>--%>
<%--        <td width="160" height="20" ></td>--%>
<%--        <td width="160" height="20" >&nbsp;<a href="../" target="_parent">--%>
<%--								<img class="buttonstyle"--%>
<%--									src="../style/<%=styleLocation%>/images/dl56.jpg" width="97"--%>
<%--									height="23" border="0"/>--%>
<%--								</a></td>--%>
<%--        <td width="148" height="20" >&nbsp;</td>--%>
<%--      </tr>--%>
<%--      --%>
<%--    </table>--%>
<%--    </td>--%>
<%--    <td width="254" height="69" align="center" background="../style/<%=styleLocation%>/images/zx/sy5.jpg" class="dh"><table width="180" height="22" border="0" cellpadding="0" cellspacing="0">--%>
<%--      <tr>--%>
<%--        <td height="20" colspan="2" class="wenzi7">&nbsp;</td>--%>
<%--        </tr>--%>
<%--      <tr>--%>
<%--        <td width="180" class="wenzi7"><%=f.format(new java.util.Date())%></td>--%>
<%--        <td width="0" class="wenzi7"><input name="outputID" type="hidden" id="outputID" value="1234567890"></td>--%>
<%--      </tr>--%>
<%--    </table></td>--%>
<%--  </tr>--%>
<%--</table>--%>
	<table width="1024" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="686" height="69" background="../style/chun/images/sy2.jpg"> 
					<img src="../style/chun/images/sy1.gif" width="1440"
						height="98" />	</td>
    <td width="527" align="center">
    	<table width="199" border="0" cellspacing="0" cellpadding="0" style="position:relative; top:-1px;">
        <tr>
          <td height="65"  colspan="2" >
          <br /><table width="156" height="50" border="0" align="center" cellpadding="0" cellspacing="2">
            <tr>
              <td width="59" rowspan="2">&nbsp;</td>
              <td width="116" ><!-- <img src="../style/chun/images/dl56.jpg" width="109" height="20" /> --></td>
            </tr>
            <tr>
              <td ><!-- <img src="../style/chun/images/dl57.jpg" width="109" height="20" /> --></td>
            </tr>
          </table>
          </div>
         </td>
        </tr>
        <tr>
          <td class="selectStyle" width="148" height="29" border="0" background="../style/chun/images/sy3.jpg" > </td>
          <td background="../style/chun/images/sy4.jpg">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<div style="display:none">
								
		短消息:
<%--		<bean:write name="msgNum2" scope="session"/>/<bean:write name="msgNum" scope="session"/>--%>
		<input name="outputID" type="hidden" id="outputID" value="1234567890" />							
</div>
<%--<div style="position:absolute;right:18px;bottom:30px;width:109px;">--%>
<%--<img src="../style/chun/images/dl57.jpg" width="109" height="20" onclick="goLogin()" />						--%>
<%--</div>--%>
<%--<div style="position:absolute;right:18px;bottom:5px;width:228px;">--%>
<%--	<img src="../style/chun/images/dl58.jpg" width="109" height="20" onclick="goRefresh()" style="display:inline;"/>	--%>
<%--	<img src="../style/chun/images/dl56.jpg" width="109" height="20" style="display:inline;"/>--%>
<%--</div>--%>
<div style="position:absolute;right:10px;top:6px;width:250px;">
	<img src="../style/chun/images/dl58.jpg" width="74" height="19" onclick="goRefresh()" style="display:inline;cursor:pointer;"/>	
	<img src="../style/chun/images/dl56.jpg" width="74" height="19" style="display:inline;cursor:pointer;" onclick="popUp('111','help/agenthelp.html','800','600')"/>
	<img src="../style/chun/images/dl57.jpg" width="74" height="19" onclick="goLogin()" style="display:inline;cursor:pointer;" />	
</div>
<div  style="position:absolute;right:18px;bottom:3px;"><font color="#3167A5" size="2em"><%=f.format(new java.util.Date())%></font></div>
</body>
</html>

