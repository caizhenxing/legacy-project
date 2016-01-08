
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
    
    <title>info.jsp</title>
    
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
<SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
</SCRIPT>
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.receivers,"接受者")) return false;          
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../im.do?method=sendMsg";
    		  document.forms[0].submit();
    	    }
    	}
    </script>
<SCRIPT>
	
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
	
  //添加人员List
      function addSelect(receivers){
      
		var page = "/ETOA/oa/communicate/im.do?method=userList&receivers="+receivers.value;
		
		var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
		var obj = document.ImBean;
		window.showModalDialog(page,obj,winFeatures);
	}
</SCRIPT>
  </head>
  
  <body>
  <html:form action="/oa/communicate/im">
    <table width="60%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="2" class="tdbgpicload"><bean:message key="oa.communicate.im.title"/></TD>
  </TR>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.communicate.im.receiver"/></td>
    <td  class="tdbgcolorloadleft">
		<html:textarea property="receivers" rows="5" cols="30"></html:textarea>
		<html:button property="aa" onclick="addSelect(receivers)"><bean:message key="oa.communicate.im.select"/></html:button>
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.communicate.im.sendcontent"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="contents" rows="5" cols="30"></html:textarea></td>
  </tr>
  <tr>
    
    <td colspan="2"  class="tdbgcolorloadbuttom">
  <input name="btnAdd" type="button" class="bottom" value="<bean:message key='oa.communicate.im.send'/>" onclick="add()"/> 
    </td>
     
  </tr>
</table>
</html:form>
  </body>
</html:html>
