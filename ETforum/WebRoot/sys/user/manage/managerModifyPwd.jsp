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
    
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%
      response.setHeader("Pragma","No-cache");
      response.setHeader("Cache-Control","no-cache");
      response.setDateHeader("Expires", 0);
      String userId = request.getAttribute("userId").toString();
    %>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
        function checkForm(addstaffer){
         //   if (!checkNotNull(addstaffer.password,"旧密码")) return false;
            if (!checkNotNull(addstaffer.repassword,"新密码")) return false;
            if (!checkNotNull(addstaffer.repasswordAffirm,"确认密码")) return false;
              return true;
            }
        //修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../UserOper.do?method=ManagerModifyPwd&userId=<%=userId%>";
    		  document.forms[0].submit();
    	    }
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <logic:notEmpty name="operSign">
	  <script>alert("<bean:message name='operSign'/>"); window.close();</script>
	</logic:notEmpty>
<%--	<logic:notEmpty name="operSign">--%>
<%--	<%--%>
<%--	    out.println("<script language=javascript>");--%>
<%--	    out.print("window.alert('");%><html:errors name="operSign"/><% out.println("')");--%>
<%--	    out.println("</script>");--%>
<%--	%>--%>
<%--    </logic:notEmpty>--%>
    <html:form action="/sys/user/UserOper" method="post" >
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpicload">修改密码</td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
<%--          <tr>  --%>
            <html:hidden property="userId"/>
<%--            <TD class="tdbgcolorloadright">用户名</TD>--%>
<%--            <TD class="tdbgcolorloadleft"><html:text property="userId" styleClass="input"></html:text> </TD>--%>
<%--          </tr>--%>

          <tr>
<%--               <html:hidden property="userId"/>  --%>
            <TD class="tdbgcolorloadright">新密码</TD>
            <TD class="tdbgcolorloadleft"><html:password  property="repassword" styleClass="input" ></html:password></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright">确认密码</TD>
            <TD class="tdbgcolorloadleft"><html:password  property="repasswordAffirm" styleClass="input"></html:password></TD>
          </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html>
