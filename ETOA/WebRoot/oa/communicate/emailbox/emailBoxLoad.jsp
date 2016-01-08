<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link REL=stylesheet href="css/divtext.css" type="text/css">
    <script language="JavaScript" src="js/divtext.js"></script>
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
        function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.name,"邮箱信息")) return false;
        	if (!checkNotNull(addstaffer.emailaddress,"邮件地址")) return false;
        	if (!checkNotNull(addstaffer.returnadress,"回复地址")) return false;
        	if (!checkNotNull(addstaffer.pop3,"接收邮件(POP3)")) return false;
        	if (!checkNotNull(addstaffer.smtp,"发送邮件(SMTP)")) return false;
        	if (!checkNotNull(addstaffer.pop3user,"用户名")) return false;
        	if (!checkNotNull(addstaffer.poppassword,"密码")) return false;
           return true;
        }
    	//添加
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "../emailbox.do?method=operEmailBox&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "../emailbox.do?method=operEmailBox&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../emailbox.do?method=operEmailBox&type=delete";
    		document.forms[0].submit();
    	}
    	
    	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		//返回页面
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/communicate/emailbox.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.operator"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadleft"><bean:message key="et.oa.communicate.emailbox.emailboxload.userinfo"/></td>
		    <td class="tdbgcolorloadleft"></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.emailboxinfo"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="name" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.163box"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.emailaddr"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="emailaddress" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.yourdomain"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.answeraddr"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="returnadress" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.returnaddr"/>
		    </td>
		  </tr>
		  
		  <tr>
		    <td class="tdbgcolorloadleft"><bean:message key="et.oa.communicate.emailbox.emailboxload.serverinfo"/></td>
		    <td class="tdbgcolorloadleft"></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.getpop"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="pop3" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.popdomain"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.smtp"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="smtp" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.smtpdomain"/>
		    </td>
		  </tr>
		  
		  <tr>
		    <td class="tdbgcolorloadleft"><bean:message key="et.oa.communicate.emailbox.emailboxload.getsend"/></td>
		    <td class="tdbgcolorloadleft"></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.user"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="pop3user" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.yourname"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.communicate.emailbox.emailboxload.password"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:password property="poppassword" styleClass="input"/>
		    <bean:message key="et.oa.communicate.emailbox.emailboxload.emailboxpass"/>
		    </td>
		  </tr>
		  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="delete">
		     <input name="btnDel" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="del()"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnUpdate" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
