
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>详细计划情况添加</title>
    
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
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  <SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="JavaScript" type="text/JavaScript">
function getvalue(str){
	  	var par = window.dialogArguments;
	  	var d, s = "<"; // 声明变量。 
		d = new Date(); // 创建 Date 对象。 
		s += d.getYear(); // 获取年份。
		s +="-";
		s += (d.getMonth() + 1); // 获取月份。 
		s +="-";
		s += d.getDate(); // 获取日。 
		s +=" ";
		s +=d.getHours() ;
		s +=":";
		s +=d.getMinutes() ;
		s +=">";
	  	var enter="";
	  	if(par.value!="")
	  	enter="\r\n";
	     par.value = par.value+enter+str.value+s;
	     window.close();
	  }
	  </SCRIPT>
  </head>
  
  <body>
  <br>
  <html:form action="/workplan.do?method=query" target="bottommm">
    <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td   class="tdbgcolorquerytitle">详细计划情况添加</td>
  </tr>
<tr>
    
    
    <td class="tdbgcolorqueryleft">
    <textarea name="append" cols="30" rows="5"></textarea>
    
    </td>
    
  </tr>
  
  <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom"><html:button property="selectB"  onclick="getvalue(append)" ><bean:message bundle="sys" key="sys.insert"/></html:button></td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
