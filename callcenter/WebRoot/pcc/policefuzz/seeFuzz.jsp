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
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
    </SCRIPT>
  </head>
  
  <body bgcolor="#eeeeee">
  
    <html:form action="/pcc/policefuzz/fuzz.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.fuzznum"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="fuzzNo" styleClass="input" readonly="true"/>	
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.fuzzname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="name" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.sex"/></td>
		    <td class="tdbgcolorloadleft">
		    	<html:select property="sex">
				<html:option value="1"><bean:message bundle="pcc" key="sys.man"/></html:option>
				<html:option value="0"><bean:message bundle="pcc" key="sys.woman"/></html:option>
		    	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.birthday"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="birthday" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.password"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="password" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.checkpassword"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="repassword" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.mobilephone"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="mobileTel" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagunit"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:select property="tagUnit">		
        		<html:option value="0" ><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
        		<html:optionsCollection name="dtreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.workontime"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="workontime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.idcard"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="idcard" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.personstate"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="personstate" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagarea"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="tagArea" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagfreeze"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:select property="tagPoliceKind">
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.close'/>" onclick="javascript:window.close();"/>
		    </td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
