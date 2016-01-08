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
    
    <title><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
	<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
    
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.department,"部门")) return false;
        	if (!checkNotNull(addstaffer.password,"密码")) return false;
        	if (!checkNotNull(addstaffer.repassword,"验证密码")) return false;
        	if (addstaffer.password.value !=addstaffer.repassword.value)
            {
            	alert("两次输入的密码不一致！");
            	return false;
            }
           return true;
        }
    	//修改密码
    	function update(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "valid.do?method=upInfo";
    		document.forms[0].submit();
    		}
    	}

    </script>
  </head>
  
  <body bgcolor="#eeeeee">
  
    <html:form action="/pcc/policeValid/valid.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload">第二步：输入您的新密码完成操作。</td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.fuzznum"/></td>
		    <td class="tdbgcolorloadleft" width="70%">
			<html:text property="fuzzNo" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.name"/></td>
		    <td class="tdbgcolorloadleft" width="70%">
		    <html:text property="name" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.idcard"/></td>
		    <td class="tdbgcolorloadleft" width="70%">
			<html:text property="idcard" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.unit"/></td>
		    <td  class="tdbgcolorloadleft" width="70%">		
			<html:text property="unit" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		</table>
		<br/>
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td  class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.dep"/></td>
		    <td  class="tdbgcolorloadleft" width="70%">
			<html:text property="department" styleClass="input"/>
			<font color="red"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.depexample"/></font>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.pass"/></td>
		    <td  class="tdbgcolorloadleft" width="70%">
		    <html:password property="password" styleClass="input"/>
		    <font color="red"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.newpass"/></font>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright" width="30%"><bean:message bundle="pcc" key="et.pcc.policeinfo.policevalid.repass"/></td>
		    <td  class="tdbgcolorloadleft" width="70%">
		    <html:password property="repassword" styleClass="input"/>
		    </td>
		  </tr>

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>"/>&nbsp;&nbsp;
		    <input name="btnClose" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.close'/>" onclick="javascript:window.close();"/>
		    </td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
