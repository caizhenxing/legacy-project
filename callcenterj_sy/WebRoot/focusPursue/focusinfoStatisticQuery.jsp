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

	<title>焦点追踪库信息统计类型选择</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
	</SCRIPT>


	<script type="text/javascript">
 function query()
 	{
 		var type = document.forms[0].statisticType.value;
<%-- 		document.forms[0].action="../generalCaseinfo.do?method=toStatisticForwardAction";--%>
<%-- 		document.forms[0].submit();--%>
		var num=Math.round(Math.random( )*10000);
		popUp(num,'focusPursue.do?method=toStatisticForwardAction&statisticType='+type,800,460);
 		
<%-- 		document.forms[0].target="bottomm";--%>
<%-- 		window.close();--%>
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus();
	}
 </script>

</head>
<%-- onload="document.forms[0].btnSearch.click()"--%>
<body class="conditionBody">
	<html:form action="/focusPursue" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置->焦点追踪库信息统计类型选择
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				
				<td class="LabelStyle">
						统计指标
					</td>
					<td class="valueStyle">
						<html:select property="statisticType" styleClass="selectStyle">
							<html:option value="ByEditor">产品编辑量统计</html:option>
<%--							<html:option value="OneEditor">座席版别量统计</html:option>--%>
<%--							<html:option value="ByType">产品版别量统计</html:option>							--%>
							<html:option value="AllEditor">产品批示量统计</html:option>
						</html:select>
					</td>
				<td class="queryLabelStyle" width="80">
					<input type="button" name="btnSearch" value="确定"
						class="buttonStyle" onclick="query()" />
					<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="11" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
