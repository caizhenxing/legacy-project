<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ taglib uri="/WEB-INF/newtree.tld" prefix="newtree" %>
<%@ include file="../../style.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>用户查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	
<script type="text/javascript">		
		function query(){
			var utype = document.forms[0].userType.value;
			if(utype == ""){
				alert("用户类别必须选择！");
				return;
			}
    		document.forms[0].action = "../../callcenter/callOutSet.do?method=getUserList";
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
</script>
  </head>
  <body>
	
 <html:form action="/callcenter/callOutSet.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
		  <tr>
		     <td class="navigateStyle">
		     当前位置&ndash;&gt;用户查询
		     </td>
		  </tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="nivagateTable">
			 <tr>
				<td class="labelStyle">用户类别</td>
			 	<td  class="valueStyle">
				<html:select property="userType">
			      		<html:option value="">
			      			请选择
			      		</html:option>
			    		<html:options collection="list"
			  							property="value"
			  							labelProperty="label"/>
			    </html:select>
				</td>					
		 	  </tr>
<%--		 <tr>--%>
<%--		      <td class="tdbgcolorqueryright">业务编号<br></td>--%>
<%--				<td  class="tdbgcolorqueryleft" colspan="4">--%>
<%--				<html:text  property="operationNum" styleClass="input" size="25%"/>--%>
<%--				</td>--%>
<%--		</tr>--%>
<%--		 <tr>--%>
<%--		    <td class="tdbgcolorqueryright" align="right">开始时间</td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="begintime" styleClass="input" readonly="true"/>--%>
<%--		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(begintime)"/></td>--%>
<%--		    <td class="tdbgcolorqueryright" align="right">结束时间</td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="endtime" styleClass="input" readonly="true"/>--%>
<%--		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endtime)"/></td>--%>
<%--		  </tr>	 --%>
		 <tr>
		    <td colspan="5" class="buttonAreaStyle">
<%--		    <input name="btnAdd" type="button" class="bottom" value="添加" onclick="popUp('windows','../sms/linkGroup.do?method=operLinkGroup&type=insert',800,600)"/>--%>
		    <input name="btnSearch" type="button" class="buttom" value="查询" onclick="query()"/>
		    <input type="reset" value="刷新" class="buttom"/>
		    </td>
		  </tr>
		</table> 		
    </html:form>
  </body>
</html:html>

