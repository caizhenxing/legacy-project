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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	//²éÑ¯
    	function query(){
    		document.forms[0].action = "../addressListSort.do?method=toAddressListSortList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	function companyquery(){
    		document.forms[0].action = "../addressListSort.do?method=toAddressListSortList&sortSet=company";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	function personalquery(){
    		document.forms[0].action = "../addressListSort.do?method=toAddressListSortList&sortSet=personal";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	function commonquery(){
    		document.forms[0].action = "../addressListSort.do?method=toAddressListSortList&sortSet=commonquery";
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/oa/privy/addressListSort" method="post">
      	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  <td class="tdbgcolorquerytitle">
		    <bean:message key="et.oa.privy.addressList.sortSetting"/>
		    <logic:equal name="sortSet" value="company"> 
		      <html:hidden property="sortMark" value="0"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="personal"> 
		      <html:hidden property="sortMark" value="1"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="common">
		      <html:hidden property="sortMark" value="2"/> 
		    </logic:equal>
		    </td>
		    <td colspan="4" class="tdbgcolorquerytitle">
		    <logic:equal name="sortSet" value="company"> 
		      <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.query'/>" onclick="companyquery()"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="personal"> 
              <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.query'/>" onclick="personalquery()"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="common">
		      <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.query'/>" onclick="commonquery()"/>
		    </logic:equal>
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.query'/>" onclick="query()"/>
		    <logic:equal name="sortSet" value="company"> 
		      <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.add'/>" onclick="popUp('windows','../privy/addressListSort.do?method=toAddressListSortLoad&type=insert&addressListSign=company',680,400)"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="personal"> 
		      <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.add'/>" onclick="popUp('windows','../privy/addressListSort.do?method=toAddressListSortLoad&type=insert&addressListSign=personal',680,400)"/>
		    </logic:equal>
		    <logic:equal name="sortSet" value="common">
		      <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.add'/>" onclick="popUp('windows','../privy/addressListSort.do?method=toAddressListSortLoad&type=insert&addressListSign=common',680,400)"/>
		    </logic:equal>
		  	</td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
