<%@ page language="java" pageEncoding="gb2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    <title>
      <bean:message key="hl.bo.oa.message.addrList.query.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    	<style type="text/css">
			<!--
			body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
			-->
		</style>
    <script type="text/javascript">
    
    	//已经发的邮件
    	function emailBox(){
    		document.forms[0].action = "../email.do?method=toEmailList&type=sendBox";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    	//接收邮件，重新请求数据库
    	function takeEmail(){
    		document.forms[0].action = "../email.do?method=toEmailList&type=getBox";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    	//列表跳转
		function jumpSelect(){
			document.forms[0].action = "../email.do?method=toEmailList&type="+document.forms[0].select.value;
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
    <html:form action="/oa/communicate/email.do" method="post">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
        <tr>
		  <td class="tdbgcolorqueryleft">
	        <bean:message key="oa.communicate.email.emailquery.pleaseselect"/>
	        <html:select property="select" onchange="jumpSelect()">
	        	<html:option value="sendBox"><bean:message key="agrofront.email.emailList.send"/></html:option>
	        	<html:option value="getBox"><bean:message key="agrofront.email.emailList.get"/></html:option>
	        	<html:option value="draftBox"><bean:message key="agrofront.email.emailList.notsend"/></html:option>
	        	<html:option value="dustbinBox"><bean:message key="agrofront.email.emailList.recycle"/></html:option>
	        </html:select>
          </td>
		  <td class="tdbgcolorquerybuttom">
		    <input type="button" name="postBox" value="<bean:message key='oa.communicate.email.emailquery.mysend'/>" onclick="emailBox()"/>
		    <input type="button" name="sendEmail" value="<bean:message key='oa.communicate.email.emailquery.send'/>" onclick="popUp('windows','email.do?method=toEmailLoad&type=insert',650,550)"/>
			<input type="button" name="getEmail" value="<bean:message key='oa.communicate.email.emailquery.get'/>" onclick="takeEmail()"/>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
