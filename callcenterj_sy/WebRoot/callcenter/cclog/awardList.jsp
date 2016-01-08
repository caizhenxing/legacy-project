<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

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
    
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	
    <script language="javascript">
      function popUp( win_name,loc, w, h, menubar,center ) {
    	
    	//2008-03-20 增加 loc 减少更改
    	loc = "../custinfo/phoneinfo.do?method=phoneLoad&type=" + loc + "&";
    	
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
  
  <body class="listBody">
    <html:form action="/callcenter/award.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">顺序号</td>
		    <td class="listTitleStyle">来电号码</td>
		    <td class="listTitleStyle">来电时间</td>
		    <td class="listTitleStyle">操作</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr>
		    <td ><bean:write name="c" property="orderNum" filter="true"/></td>
		    <td ><bean:write name="c" property="phoneNum" filter="true"/></td>
		    <td ><bean:write name="c" property="ringBegintime" format="yyyy-MM-dd HH:mm:ss" filter="true"/></td>		    
		    <td align="center" width="10%">
		    	<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('<bean:write name='c' property='id'/>','detail&id=<bean:write name='c' property='id'/>&custType=<bean:write name='c' property='dict_cust_type'/>',760,500)" />
		    </td>
		  </tr>
		  </logic:iterate>
		</table>
    </html:form>
  </body>
</html:html>