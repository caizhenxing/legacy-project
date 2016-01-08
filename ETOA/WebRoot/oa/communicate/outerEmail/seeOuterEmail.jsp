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
    
    <title>operator</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link REL=stylesheet href="../css/divtext.css" type="text/css">
    <script language="JavaScript" src="../js/divtext.js"></script>
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//Ìí¼Ó
    	function load(){
    		document.getElementById(maindiv).innerHTML = document.forms[0].elements["emailInfo"].value;
    	}
    </script>
  </head>
  
  <body onload="load()">
  
    <html:form action="/oa/communicate/email.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message key="agrofront.email.emailLoad.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.takeuser"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="takeUser" size="50" styleClass="input" readonly="true"/>
			
		    <logic:notPresent name="adjunctInfo">
		    	<bean:message key="oa.communicate.email.seeemail.addother"/>
		    	<logic:iterate id="c" name="adjunctList">
		    		<a href="<bean:write name='c' property='url'/>" target="blank">
		    		<bean:write name="c" property="name"/>
		    		</a>
		    	</logic:iterate>
		    </logic:notPresent>
			
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.emailtitle"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="emailTitle" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.emailinfo"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="emailInfo" style="width:1px;height:1px;"/>
		    <script language="JavaScript" src="../js/format.js"></script>
		    </td>
		  </tr>
		  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <input name="btnClose" type="button" class="bottom" value="<bean:message key='oa.communicate.email.seeemail.close'/>" onclick="javascript:window.close();"/>
		   </td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
