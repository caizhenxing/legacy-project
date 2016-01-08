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

	<title>联络员信息查询</title>

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
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script type="text/javascript"> 	
 	function query()
 	{
 		document.forms[0].action="../linkman/linkman.do?method=toLinkmanList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
	
		editorWin.focus(); //causing intermittent errors
	}
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/linkman/linkman" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;联络员工作管理
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="LabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="begintime" styleClass="writeTextStyle" size="10"/>
					<img alt="选择日期" src="../html/img/cal.gif"
						onclick="openCal('linkman','begintime',false);">
				</td>
				<td class="LabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endtime" styleClass="writeTextStyle" size="10"/>
					<img alt="选择日期" src="../html/img/cal.gif"
						onclick="openCal('linkman','endtime',false);">
				</td>
				<td class="LabelStyle">
					事件类型
				</td>
				<td class="valueStyle">
					<select name="dictQuestionType1">
						<option value="">请选择</option>
						<option value="种植咨询">种植咨询</option>
						<option value="项目咨询">项目咨询</option>
						<option value="医疗服务">医疗服务</option>
						<option value="金农通">金农通</option>
						<option value="价格报送">价格报送</option>
						<option value="万事通">万事通</option>
						<option value="养殖咨询">养殖咨询</option>
						<option value="供求发布">供求发布</option>
						<option value="价格行情">价格行情</option>
						<option value="政策咨询">政策咨询</option>
						<option value="信息订制">信息订制</option>
						<option value="企业服务">企业服务</option>
					</select>
				</td>
				<td class="LabelStyle" width="80">
					<input type="button" name="btnSearch" value="查询"
						class="buttonStyle" onclick="query()" />
					<input type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />					
				</td>
			</tr>
			<tr height="1px">
				<td colspan="20" class="buttonAreaStyle">									
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
l