<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <html:base/>
    
    <title>�޸�����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%
      response.setHeader("Pragma","No-cache");
      response.setHeader("Cache-Control","no-cache");
      response.setDateHeader("Expires", 0);
    %>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link href="../../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
        function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.password,"<bean:message bundle='sys' key='sys.user.modifyPwd.oldPwd'/>")) return false;
            if (!checkNotNull(addstaffer.repassword,"<bean:message bundle='sys' key='sys.user.modifyPwd.newPwd'/>")) return false;
            if (!checkNotNull(addstaffer.repasswordAffirm,"<bean:message bundle='sys' key='sys.user.modifyPwd.confirmPwd'/>")) return false;
              return true;
            }
        //�޸�
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../UserOper.do?method=operModifyPwd";
    		  document.forms[0].submit();
    	    }
    	}
    	
    	document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
    </script>
  </head>
  
  <body>
<%--    <logic:notEmpty name="operSign">--%>
<%--	  <script>alert("�����ɹ���");</script>--%>
<%--	</logic:notEmpty>--%>
	<logic:notEmpty name="operSign">
	<script>alert("<bean:message bundle='sys' name='operSign'/>"); window.close();</script>
    </logic:notEmpty>
    <html:form action="/sys/user/UserOper" method="post" >
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpicload"><bean:message bundle="sys" key="sys.user.modifyPwd"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>  
<%--            <html:hidden property="id"/>--%>
            <TD class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.user.modifyPwd.oldPwd"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="password" styleClass="input"></html:text><font color="#ff0000">*</font> </TD>
          </tr>

          <tr>  
            <TD class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.user.modifyPwd.newPwd"/></TD>
            <TD class="tdbgcolorloadleft"><html:password  property="repassword" styleClass="input" ></html:password><font color="#ff0000">*</font></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.user.modifyPwd.confirmPwd"/></TD>
            <TD class="tdbgcolorloadleft"><html:password  property="repasswordAffirm" styleClass="input"></html:password><font color="#ff0000">*</font></TD>
          </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <input name="btnAdd" type="button" class="button" value="<bean:message bundle='sys' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html>
