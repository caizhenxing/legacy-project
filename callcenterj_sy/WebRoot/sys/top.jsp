<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
	<%@ include file="../style.jsp"%>
	<html:base />
	<%
	java.text.DateFormat f=new java.text.SimpleDateFormat("yyyy年MM月dd日 EEE");
	styleLocation = "chun";
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
.rq {	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 15px;
	font-weight: normal;
	font-variant: normal;
	color: #FFFFFF;
	text-align: center;
}
-->
</style>
		<link href="../style/<%=styleLocation%>/images/zx/zx.css" rel="stylesheet"
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
<%--			<tr>--%>
<%--				<td width="686" background="../style/<%=styleLocation%>/images/sy2.jpg"> --%>
<%--					<img src="../style/<%=styleLocation%>/../style/chun/images/sy1.jpg" width="776"--%>
<%--						height="66" />--%>
<%--				</td>--%>
<%--				<td width="330" height="69" align="center" valign="middle"--%>
<%--					background="../style/<%=styleLocation%>/../style/chun/images/sy2.jpg">--%>
<%--					--%>
<%--					<table width="135" height="28" border="0" cellpadding="0"--%>
<%--						cellspacing="3">--%>
<%--						<tr>--%>
<%--							<td>--%>
<%--								<span class="rq"><%=f.format(new java.util.Date())%>--%>
<%--								</span>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td height="32" colspan="2" align="center"--%>
<%--					background="../style/<%=styleLocation%>/../style/chun/images/sy3.jpg">--%>
<%--					<table width="963" height="25" border="0" cellpadding="0"--%>
<%--						cellspacing="0">--%>
<%--						<tr>--%>
<%--							<td width="45" align="center">--%>
<%--								<img src="../style/<%=styleLocation%>/../style/chun/images/dl54.jpg"--%>
<%--									width="30" height="23" />--%>
<%--							</td>--%>
<%--							<td width="150" class="rq">--%>
<%--								<bean:write name="userInfoSession" property="userName"/>--%>
<%--								&nbsp;&nbsp;--%>
<%--								<bean:message bundle="sys" key="sys.hello" />--%>
<%--							</td>--%>
<%--							<td width="200" class="rq">--%>
<%--								--%>
<%--								 短消息:--%>
<%--								<bean:write name="msgNum2" scope="session"/>/<bean:write name="msgNum" scope="session"/>--%>
<%--								--%>
<%--							</td>--%>
<%--							<td width="576">--%>
<%--								&nbsp;--%>
<%--							</td>--%>
<%--							<td width="97" align="center">--%>
<%--								<a href="../" target="_parent">--%>
<%--								<img class="buttonstyle"--%>
<%--									src="../style/<%=styleLocation%>/../style/chun/images/dl56.jpg" width="97"--%>
<%--									height="23" border="0"/>--%>
<%--								</a>--%>
<%--							</td>--%>
<%--							<td width="9">--%>
<%--								&nbsp;--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		</table>--%>
<input name="outputID" type="hidden" id="outputID" value=""><%--删东西时看着点，谁再把这行删除我就跟他拼命！！--%>
		<table width="1024" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="686" height="69" background="../style/chun/images/sy2.jpg"> 
					<img src="../style/chun/images/sy1.gif" width="1440"
						height="98" />	</td>
    <td width="527" align="center">
    	<table width="199" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="65"  colspan="2" >
          <br /><table width="156" height="50" border="0" align="center" cellpadding="0" cellspacing="2">
            <tr>
              <td width="59" rowspan="2">&nbsp;</td>
              <td width="116" ><!-- <img src="../style/chun/images/dl56.jpg" width="109" height="20" /> --></td>
            </tr>
            <tr>
              <td ><!-- <img src="../style/chun/images/dl57.jpg" width="109" height="20" />--></td>
            </tr>
          </table>
          </div>
         </td>
        </tr>
        <tr>
          <td width="148" height="29" style="border:0px;"><div style="position:relative;padding:0px; width:148px;top:-1px; height:29px;background-image:url(../style/chun/images/sy3.jpg) "></div></td>
          <td style="border:0px;"><div style="position:relative;padding:0px; width:45px;top:-1px;left:-3px; height:29px;background-image:url(../style/chun/images/sy4.jpg) "></div></td>
        </tr>
      </table></td>
  </tr>
</table>
<div style="position:absolute;left:0px;bottom:0px;">
								
<%--								 短消息:--%>
<%--								<bean:write name="msgNum2" scope="session"/>/<bean:write name="msgNum" scope="session"/>--%>
		<table width="130"  border="0" cellpadding="0" cellspacing="1" >
            <tr >
              <td width="60" height="15"><span class="wenzi7"><bean:write name="userInfoSession" property="userName"/></span></td>
              <logic:equal name="userInfoSession" property="userGroup" value="administrator">
                <td width="60" ><span class="wenzi7">管理员</span></td>
              </logic:equal>
              <logic:equal name="userInfoSession" property="userGroup" value="admin">
                <td width="60" ><span class="wenzi7">管理员</span></td>
              </logic:equal>
              <td width="60" ><span class="wenzi7"><bean:message bundle="sys" key="sys.hello" /></span></td>
            </tr>
          </table>
							
</div>
<%--<div style="position:absolute;right:18px;bottom:35px;width:109px;">--%>
<%--<img src="../style/chun/images/dl57.jpg" width="75" height="20" onclick="goLogin()" />						--%>
<%--</div>--%>
<div style="position:absolute;right:10px;top:6px;width:250px;">
	<img src="../style/chun/images/dl58.jpg" width="74" height="19" onclick="goRefresh()" style="display:inline;cursor:pointer;"/>	
	<img src="../style/chun/images/dl56.jpg" width="74" height="19" style="display:inline;cursor:pointer;" onclick="popUp('111','help/help.html','800','600')"/>
		<img src="../style/chun/images/dl57.jpg" width="74" height="19" onclick="goLogin()" style="display:inline;cursor:pointer;" />	
</div>
	</body>
</html>

