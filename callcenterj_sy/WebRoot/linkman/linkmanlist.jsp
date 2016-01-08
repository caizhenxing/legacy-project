<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>联络员管理系统</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	<script type="text/javascript">
 
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
 
 </script>
</head>

<body class="listBody">
	<html:form action="/medical/medicinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="50">
					联络员
				</td>
				<td class="listTitleStyle" >
					电话
				</td>
				<td class="listTitleStyle">
					地址
				</td>
				<td class="listTitleStyle" >
					事件类型
				</td>				
				<td class="listTitleStyle" >
					呼入/呼出
				</td>
				<td class="listTitleStyle">
					时间
				</td>

			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
							String style = i.intValue() % 2 == 0 ? "oddStyle"
							: "evenStyle";
				%>
				<tr>
					<td >
						<bean:write name="c" property="custName" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="custTelMob" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="custAddr" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="dictQuestionType1" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="isOut" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="addtime" filter="true" />
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="6">
					<page:page name="pageTurning" style="second"/>
				</td>
			</tr>
		</table>

	</html:form>
</body>
</html:html>