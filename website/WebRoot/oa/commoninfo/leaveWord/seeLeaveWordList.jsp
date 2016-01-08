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
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
    function seeQuery(){
    		document.forms[0].action = "../leaveWord.do?method=toSeeLeaveWordList";
    		//document.forms[0].target = "bottomm";
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
  
<%--  <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">--%>
<%--		  <tr>--%>
<%--		    <td colspan="4" class="tdbgcolorquerybuttom">--%>
<%--		    <input name="btnSearch2" type="button" class="bottom" value="²é¿´ÁôÑÔ" onclick="seeQuery()"/>--%>
<%--		  	<input name="btnAdd" type="button" class="bottom" value="Ìí¼ÓÁôÑÔ" onclick="popUp('windows','../commoninfo/leaveWord.do?method=toLeaveWordLoad&type=insert',680,400)"/>--%>
<%--		  	</td>--%>
<%--		  </tr>--%>
<%--		</table>--%>
    <html:form action="/oa/commoninfo/leaveWord" method="post">
    	<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		    <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.leaveWord.seeLeaveWord'/>" onclick="seeQuery()"/>
		  	<input name="btnAdd" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.leaveWord.addLeaveWord'/>" onclick="popUp('windows','../commoninfo/leaveWord.do?method=toLeaveWordLoad&type=insert',680,400)"/>
		  	</td>
		   </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message key="et.oa.commoninfo.leaveWord.leavaWordName"/></td>
		    <td class="tdbgpiclist"><bean:message key="et.oa.commoninfo.leaveWord.leavaWordInfo"/></td>
		  </tr>
          <logic:iterate id="c" name="list" indexId="i">
          	<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
          <tr> 
          <td rowspan="2" width="20%" align="center" class="<%=style%>">
          <bean:write name="c" property="name" filter="true"/><br>        
          <br>
          </td>
          <td class="<%=style%>" height="22" ><bean:message key="et.oa.commoninfo.leaveWord.title2"/><bean:write name="c" property="title" filter="true"/>&nbsp;&nbsp;
                                              <bean:message key="et.oa.commoninfo.leaveWord.publish"/><bean:write name="c" property="leaveDate" filter="true"/>
          </td>
          <tr> 
          <td class="<%=style%>" height="100" valign=top>          
             <bean:write name="c" property="content" filter="true"/>
          <br>
          <br>          
          </td>
          </tr>
      </logic:iterate>
          <tr>
		    <td colspan="2">
				<page:page name="agropageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>