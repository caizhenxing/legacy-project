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
    		if (!checkNotNull(addstaffer.password,"密码")) return false;
    		if (!checkNotNull(addstaffer.repassword,"确认密码")) return false;
        	if (addstaffer.password.value !=addstaffer.repassword.value)
            {
            	alert("两次输入的密码不一致！");
            	return false;
            }
           return true;
        }
    	//修改
    	function update(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "fuzz.do?method=updatePassword";
    		document.forms[0].submit();
    		}
    	}
    	//返回
    	function back(){
    		document.forms[0].action = "fuzz.do?method=toCheckPoliceNum";
    		document.forms[0].submit();
    	}


    </script>
  </head>
  
  <body onunload="toback()" bgcolor="#eeeeee">
  
    <html:form action="/pcc/policefuzz/fuzz.do" method="post">
    
    <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
	      <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.policenum"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.name"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.birthday"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.mobilephone"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.policekind"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.byunit"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.duty"/></td>
		  </tr>
		  <tr>
		    <td><bean:write name="fuzzno" property="fuzzNo" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="name" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="birthday" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="mobileTel" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="tagPoliceKind" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="tagUnit" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="duty" filter="false"/></td>
		  </tr>
		</table>
		
		<br/>
		
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.fuzz.updatePoliceNum.title"/></td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright">新密码</td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="password" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright">确认新密码</td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="repassword" styleClass="input"/>
		    </td>
		  </tr>

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		     <input name="btnConfirm" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.update'/>" onclick="update()"/>
		     <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>" onclick="back()"/>
		    </td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
