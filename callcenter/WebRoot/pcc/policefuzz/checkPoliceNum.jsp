<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
    	function checkForm(addstaffer){
           if (!checkNotNull(addstaffer.fuzzNo,"¾¯ºÅ")) return false;
           return true;
        }
    	//¼ì²é
    	function check(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "fuzz.do?method=checkPoliceNum";
    		document.forms[0].submit();
    		}
    	}
    	//·µ»Ø
    	function back(){
    		document.forms[0].action = "fuzz.do?method=toCheckPoliceNum";
    		document.forms[0].submit();
    	}
    	
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
  
    <html:form action="/pcc/policefuzz/fuzz.do?method=checkPoliceNum" method="post" onsubmit="check()">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.fuzz.updatePoliceNum.policenumcheck"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.fuzznum"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="fuzzNo" styleClass="input"/>	
		    </td>
		  </tr>

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		     <input name="btnCheck" type="submit" class="bottom" value="<bean:message bundle='pcc' key='sys.search'/>"/>&nbsp;&nbsp;
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>"/></td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
