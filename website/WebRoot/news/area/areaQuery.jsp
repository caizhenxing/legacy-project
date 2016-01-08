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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:200px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		
    	//查询
    	function query(){
    		document.forms[0].action = "../newsArea.do?method=toAreaList";
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
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars=auto,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/news/newsArea" method="post">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message bundle="sys" key="sys.current.page"/>
		     区域样式管理
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright">区域名称</td>
		    <td class="tdbgcolorqueryleft">
		    <html:text property="newsAreaName" styleClass="input"/>
		    </td>
		    <td class="tdbgcolorqueryright">样式</td>
		    <td class="tdbgcolorqueryleft"><html:text property="styleId" styleClass="input"/></td>
		  </tr>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message key="agrofront.news.article.articlequery.articleState"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">--%>
<%--		    <html:select property="classid">		--%>
<%--        	<html:option value="">请选择</html:option>--%>
<%--        		<html:optionsCollection name="ctreelist" label="label" value="value"/>--%>
<%--        	</html:select>--%>
<%--		    </td>--%>
<%--		    <td class="tdbgcolorqueryright"></td>--%>
<%--		    <td class="tdbgcolorqueryleft"></td>--%>
<%--		  </tr>--%>
		  
		  
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="查询" onclick="query()"/>
		    <input name="btnAdd" type="button" class="bottom" value="添加" onclick="window.open('newsArea.do?method=toAreaLoad&type=insert','windows','750.700,scrollbars=yes')"/>
		    </td>
		  </tr>
		  
		</table>
    </html:form>
  </body>
</html:html>
