<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>excellence-tech</title>
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
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #000000}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="46" valign="top" background="<bean:write name='imagesinsession'/>top.gif"></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" background="<bean:write name='imagesinsession'/>top_toolbar.gif">
      <tr>
        <td width="190" height="21" align="center" valign="middle" >
            <img src="<bean:write name='imagesinsession'/>clock.gif" width="16" height="16" alt="时间选择" border="0" onclick="window.open('clock.jsp','windows','height=200,width=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,screenX=300,screenY=300');"/>
        
        <SCRIPT language=JavaScript>
 today=new Date();
 function initArray(){
   this.length=initArray.arguments.length
   for(var i=0;i<this.length;i++)
   this[i+1]=initArray.arguments[i]  }
   var d=new initArray(
     "星期日",
     "星期一",
     "星期二",
     "星期三",
     "星期四",
     "星期五",
     "星期六");
document.write(
     "<font color=#000000 style='font-size:9pt;font-family: 宋体'> ",
     today.getYear(),"年",
     today.getMonth()+1,"月",
     today.getDate(),"日",
     d[today.getDay()+1],
     "</font>" ); 
              </SCRIPT>
          <span class="style1"></span> </td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5%"><img src="<bean:write name='imagesinsession'/>ren.gif" width="20" height="20" /></td>
            <td width="10%">
            <bean:write name="userInfoSession" property="userName"/></td>
            <td width="60%"><bean:message bundle="sys" key="sys.hello"/></td>
            <td width="25%">
            <a href="../oa/mainOper.do?method=toMain" target="contents">
            <img src="<bean:write name='imagesinsession'/>bgpt.gif" alt="办公平台" border="0"/>
            </a>
            <a href="../" target="_parent">
            <img src="<bean:write name='imagesinsession'/>tcdl.gif" alt="退出登陆" border="0"/>
            </a>
            </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

