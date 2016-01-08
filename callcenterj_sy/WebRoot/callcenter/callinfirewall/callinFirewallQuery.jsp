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
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallList";
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
		function goRefurbish()
		{
			query();
		}
    </script>


</head>

<body class="conditionBody">

	<html:form action="/callcenter/callinFirewall" method="post"
		onsubmit="query()" styleId="fireForm" >
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt; �������ι���
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
			    <td class="labelStyle" style="width:81px;">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="10" />
						<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('fireForm','beginTime',false);">
				</td>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="10" />
						<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('fireForm','endTime',false);">
				</td>
				<td class="labelStyle">
					���κ���
				</td>
				<td class="valueStyle">
					<html:text property="callinNum" styleClass="writeTextStyle" size="10" />
				</td>
				<td class="labelStyle">
					�Ƿ�����
				</td>
				<td class="valueStyle">
					<html:select property="isPass" styleClass="selectStyle">
						<html:option value="">��ѡ��</html:option>
						<html:option value="0">ͨ��</html:option>
						<html:option value="1">δͨ��</html:option>
					</html:select>
				</td>
				<td class="labelStyle" align="center" width="100px">		
					<input name="btnSearch" type="submit"  
						value="��ѯ" class="buttonStyle"/>
					<input id="btnAdd" style="display:none;" name="btnAdd" type="button"   value="���"
						onclick="popUp('windows','callinFirewall.do?method=toCallinFireWallLoad&type=insert',750,260)" class="buttonStyle"/>
					<input type="reset" value="ˢ��"   class="buttonStyle" onclick="goRefurbish()" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
 