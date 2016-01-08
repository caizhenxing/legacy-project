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
    <link REL=stylesheet href="<bean:write name='imagesinsession'/>affiche/divtext.css" type="text/css">
    <script language="JavaScript" src="js/divtext.js"></script>
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="JavaScript" src="../../js/calendar.js"></script>
    <script language="javascript">
        function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.aficheTitle,"公告标题")) return false;
        	if (!checkNotNull(addstaffer.aficheUser,"公告撰写人")) return false;
           return true;
        }
    	//添加
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].elements["aficheInfo"].value = document.getElementById(maindiv).innerHTML;
    		document.forms[0].action = "../operaffiche.do?method=operAfiche&type=insert";
    		document.forms[0].submit();
    		}
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
  
    <html:form action="/oa/operaffiche.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message key="agrofront.afiche.aficheLoad.title"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.afichetitle"/></td>
		    <td class="tdbgcolorloadleft"><html:text property="aficheTitle" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.aficheuser"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="aficheUser" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.afichetype"/></td>
		    <td class="tdbgcolorloadleft"><span class="tdbgcolor2">
			<html:text property="aficheType" styleClass="input"/>
		    </span></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.aficheinfo"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="aficheInfo" style="width:1px;height:1px;"/>
		    	<script language="JavaScript" src="js/format.js"></script>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.begintime"/></td>
		    <td class="tdbgcolorloadleft">
		      <html:text property="beginTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.afiche.aficheLoad.endtime"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="endTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
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
