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
    		document.forms[0].action = "../handsetnote.do?method=toHandsetNoteList";
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
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/oa/communicate/handsetnote.do" method="post">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message bundle="sys" key="sys.current.page"/>
		    <bean:message key="et.oa.communicate.handsetnote.handsetnotequery.title"/>
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5">
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.number"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="handsetnum" styleClass="input"/></td>
		    <td class="tdbgcolorqueryright"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.content"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="handsetinfo" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.issend"/></td>
		    <td class="tdbgcolorqueryleft">
		    <html:select property="sendstate">
		    	<html:option value="0"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.pleaseselect"/></html:option>
		    	<html:option value="1"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.send"/></html:option>
		    	<html:option value="2"><bean:message key="et.oa.communicate.handsetnote.handsetnotequery.unsend"/></html:option>
		    </html:select>
		    </td>
		    <td class="tdbgcolorqueryright"></td>
		    <td class="tdbgcolorqueryleft"></td>
		  </tr>
		  
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message key='agrofront.common.search'/>" onclick="query()"/>
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="popUp('windows','handsetnote.do?method=toHandsetNoteLoad&type=insert',650,550)"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
