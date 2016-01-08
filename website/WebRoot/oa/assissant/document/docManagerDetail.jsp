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
    
    <title><bean:message key="agrofront.oa.assissant.document.docManagerDetail.detail"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <script language="javascript">
    	//ÐÞ¸Ä
    	function update(){
    		document.forms[0].action = "../doc.do?method=toDocLoad&type=update";
    		document.forms[0].submit();
    	}
    	//É¾³ý
    	function del(){
    		document.forms[0].action = "../doc.do?method=toDocLoad&type=delete";
    		document.forms[0].submit();
    	}
    	
    	
		//·µ»ØÒ³Ãæ
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()">
   
  
<html:form action="/oa/assissant/doc" method="post">
	
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		<tr>
		    <td colspan="2" class="tdbgpicload"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.docInfo"/></td>
		  </tr>
		<tr>
		    <html:hidden property="id"/>
		    
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.folderName"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="folderName" styleClass="input" readonly="true"></html:text></TD>
          </tr>            
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.folderCode"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="folderCode" styleClass="input" readonly="true"></html:text></TD>
          </tr> 
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.folderType"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="folderType" styleClass="input" readonly="true"/></TD>
            
          </tr>    
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.folderVersion"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="folderVersion" styleClass="input" readonly="true"/></TD>
          </tr>    
          <TR>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerDetail.remark"/></TD>
            <TD class="tdbgcolorloadleft"><html:textarea  property="remark" rows="5" cols="65" readonly="true"></html:textarea> </TD>
          </TR>
		  <tr>	
		    <td colspan="4" class="tdbgcolorloadbuttom" >
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.document.docManagerDetail.chanel'/>" />
		    <input name="cccc" type="button" value="<bean:message key="agrofront.common.close"/>" onclick="javascript:window.close()"/>
		    </td>
		  </tr>
	</table>
</html:form>
  </body>
</html:html>
