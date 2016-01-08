<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
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
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../appraise.do?method=toAppraiseList";
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

<body class="conditionBody">

	<html:form action="/callcenter/appraise" method="post"
		onsubmit="query()">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt; 用户评价
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="10"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('appraiseBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					来电号码
				</td>
				<td class="valueStyle">
					<html:text property="telNum" styleClass="writeTextStyle" size="16" />
				</td>
				<td class="labelStyle">
					评价类型
				</td>
				<td class="valueStyle">
					<html:select property="appType" styleClass="selectStyle">
						<html:option value="">请选择</html:option>
						<html:option value="agent">座席评价</html:option>
						<html:option value="expert">专家评价</html:option>
					</html:select>
				</td>
				<td class="labelStyle">
				评价对象
				</td>
				<td class="valueStyle">
				<html:select property="appraiseObject">
						<option value="">请选择</option>
						<logic:iterate id="u" name="userList">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle" align="center">
					<input name="btnSearch" type="submit"  
						value="查询" class="buttonStyle"/>
					
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="10"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('appraiseBean','endTime',false);">
				</td>
				<td class="labelStyle">
				评价指标
				</td>
				<td class="valueStyle">
				<html:select property="appResult" styleClass="selectStyle">
				<html:option value="">请选择</html:option>
				<html:option value="1">满意</html:option>
				<html:option value="2">基本满意</html:option>
				<html:option value="3">不满意</html:option>
				</html:select>
				</td>
				<td class="labelStyle">
				评价对象
				</td>
				<td class="valueStyle">
					<html:text property="appraiseObject" styleClass="writeTextStyle" size="16" />
				</td>
				<td class="labelStyle">
				</td>
				<td class="valueStyle">
				</td>
				<td class="labelStyle" align="center">
				<input type="reset" value="刷新"  
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" class="buttonStyle"/>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
	