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
    
    <title><bean:message key="agrofront.oa.assissant.document.docManagerQuery3.documentUpdate"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	//≤È—Ø
    	function query(){
    		document.forms[0].action = "../doc.do?method=toDocList3";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
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
		//∑µªÿ“≥√Ê
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/oa/assissant/doc" method="post">
      		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message bundle="sys" key="sys.current.page"/>
		    <bean:message key="agrofront.oa.assissant.document.docManagerQuery3.documentUpdate"/>
		    </td>
		  </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerQuery.folderCode"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="folderCode" styleClass="input"></html:text></td>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerQuery.folderName"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="folderName" styleClass="input"></html:text></td>
          </tr>  
          <tr>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerQuery.createTime"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="createTime" readonly="true" styleClass="input"></html:text>
            <img src="../../../images/time.png" width="20" height="20" onclick="openwin(createTime)"/></td>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerQuery.updateTime"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="updateTime" readonly="true" styleClass="input"></html:text>
            <img src="../../../images/time.png" width="20" height="20" onclick="openwin(updateTime)"/></td>
          </tr>
          <tr>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerQuery2.folderVersion"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="folderVersion" styleClass="input"></html:text></td>
            <td class="tdbgcolorqueryright"><bean:message key="agrofront.oa.assissant.document.docManagerLoad.folderType"/>
            </td>
            <td class="tdbgcolorqueryleft">
            <html:select property="folderType">
    		<html:options collection="tl"
  						  property="value"
  						  labelProperty="label"/>
    	    </html:select>
            </td>
          </tr>  
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.document.docManagerQuery.query'/>" onclick="query()"/></td>

		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
