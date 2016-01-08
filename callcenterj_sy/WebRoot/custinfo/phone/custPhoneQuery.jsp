<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="text/html" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
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
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../custinfo.do?method=toPhoneList";
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
	<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
</head>

<body class="conditionBody">
	<html:form action="custinfo/custinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;客户操作
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="queryLabelStyle">
					姓&nbsp;&nbsp;&nbsp;&nbsp;名
				</td>
				<td class="valueStyle">
					<html:text property="cust_name" size="10"
						styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					电&nbsp;&nbsp;&nbsp;&nbsp;话
				</td>
				<td class="valueStyle">
					<html:text property="cust_tel_home" size="13"
						styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					客户行业
				</td>
				<td class="valueStyle">
					<html:text property="cust_voc" size="10"
						styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					客户类型
				</td>
				<td class="valueStyle">
					<html:select property="cust_type" styleClass="selectStyle">
						<html:option value="1">全部</html:option>
						<html:options collection="typeList" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button" class="buttonStyle"
						value="查询" onclick="query()" />
			       <leafRight:btn name="btnSearch" nickName="custinfo_query" styleClass="buttonStyle" value="查询" onclick="query()" scopeName="userRoleLeafRightInsession" />
					<input type="reset" value="重置" class="buttonStyle">
				</td>
			</tr>
			<tr height="1px" class="buttonAreaStyle">
				<td colspan="9">

				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
�