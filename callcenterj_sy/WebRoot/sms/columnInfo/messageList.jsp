<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("已经订制过该业务，不能重复订制！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("已经退订过该业务，不能重复退订！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
	</script>
	</logic:equal>
</logic:notEmpty>

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
    
   <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
      	//
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
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
  
  <body bgcolor="#eeeeee" class="listBody">
    <html:form action="/sms/columnInfo" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">短信标题</td>
		    <td class="listTitleStyle">短信栏目</td>
		    <td class="listTitleStyle">操作</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr>
		    <td ><bean:write name="c" property="columnName" filter="true"/></td>
			<td ><bean:write name="c" property="columnInfo" filter="true"/></td>
		   	<td >
		   		<img alt="详细" src="../../style/<%=styleLocation %>/images/detail.png"
		    		onclick="popUp('windows','columnInfo.do?method=toMessagesLoad&id=<bean:write name='c' property='id'/>&type=detail',680,300)" width="16" height="16" border="0"/>
		   		<img alt="修改" src="../../style/<%=styleLocation %>/images/update.png"
		    		onclick="popUp('windows','columnInfo.do?method=toMessagesLoad&id=<bean:write name='c' property='id'/>&type=update',680,300)" width="16" height="16" border="0"/>
		   		<img alt="删除" src="../../style/<%=styleLocation %>/images/del.png"
		    		onclick="popUp('windows','columnInfo.do?method=toMessagesLoad&id=<bean:write name='c' property='id'/>&type=delete',680,300)" width="16" height="16" border="0"/>
		   	</td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5" class="pageTable">
				<page:page name="userpageTurning" style="second"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
