<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

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
  </head>
  
  <body bgcolor="#eeeeee">
  
    <html:form action="/pcc/assay/question.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.title"/>
		    </td>
		  </tr>

		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.questiontype"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:select property="taginfo">		
        		<html:option value="0" ><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.question"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="quinfo" cols="40" rows="8" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.questioncontent"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="content" cols="40" rows="8" readonly="true"/>
		    </td>
		  </tr>
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.close'/>" onclick="javascript:window.close();"/>
		    </td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
