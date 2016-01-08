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
    	   if (!checkNotNull(addstaffer.phonenum,"电话号码")) return false;
           if (!checkNotNull(addstaffer.fuzzNo,"警号")) return false;
           if (!checkNotNull(addstaffer.password,"密码")) return false;
           return true;
        }
    	//检查
    	function check(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "phone.do?method=searchPNum";
    		document.forms[0].submit();
    		}
    	}
    	function closeIt(){
    		bey();
    		window.close();
    	}
    	
    	//关闭
    	function bey(){
    		document.forms[0].action = "phone.do?method=savecclog&phonenum="+document.forms[0].phonenum.value;
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">

	
    <html:form action="/pcc/phoneinfo/phone.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.phoneinfo.check.checknum"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.phoneinfo.check.phonenum"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="phonenum" styleClass="input"/>	
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.phoneinfo.check.fuzzno"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="fuzzNo" styleClass="input"/>	
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.phoneinfo.check.password"/></td>
		    <td class="tdbgcolorloadleft">
			<html:password property="password" styleClass="input"/>	
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		     <input name="btnCheck" type="button" class="bottom" value="<bean:message bundle='pcc' key='et.pcc.phoneinfo.check.next'/>" onclick="check()"/>&nbsp;&nbsp;
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>"/>&nbsp;&nbsp;
		    <input name="btnClose" type="button" class="bottom" onclick="closeIt()" value="<bean:message bundle='pcc' key='sys.close'/>"/>
		    </td>
		  </tr>
		</table>
		<br>
		
		    <logic:notEmpty name="bey_check_num">
	<script>alert("<bean:message bundle='pcc' name='bey_check_num'/>");bey();window.close();</script>
	</logic:notEmpty>
	
		<logic:notEmpty name="error_police">
			<div align="center">
			<font color="red">
			第<bean:write name="checknum"/>次验证
			<html:errors bundle="pcc" name="error_police"/>
			
			</font>
			</div>
		</logic:notEmpty>
		<html:hidden property="id"/>
		<html:hidden property="checknum"/>
    </html:form>
  </body>
</html:html>
