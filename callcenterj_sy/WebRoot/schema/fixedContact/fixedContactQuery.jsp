<%@ page pageEncoding="GBK" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>
<html:html lang="true">
<head>
	<html:base />
	<title>固定联络员-主页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript">
    //查询
    function query()
    {
    	document.forms[0].action = "../fixedContact.do?method=toFixedContactList";
    	document.forms[0].target = "bottomm";
    	document.forms[0].submit();
    }
    	
    function popUp( win_name,loc, w, h, menubar,center ) 
    {
			var NS = (document.layers) ? 1 : 0;
			var editorWin;
			if( w == null ) { w = 500; }
			if( h == null ) { h = 350; }
			if( menubar == null || menubar == false ) 
			{
				menubar = "";
			} 
			else 
			{
				menubar = "menubar,";
			}
			if( center == 0 || center == false ) 
			{
				center = "";
			} 
			else 
			{
				center = true;
			}
			if( NS ) { w += 50; }
			if(center==true)
			{
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
			else
			{
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
	<html:form action="/schema/fixedContact.do" method="post">
		<%--	查询oper_custinfo表中的"dict_cust_type"字段值以83为结尾的条件--%>
		<html:hidden property="cust_type" value="SYS_TREE_0000000683" />
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;金农固定联络员操作
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="labelStyle">
					姓&nbsp;&nbsp;&nbsp;&nbsp;名
				</td>
				<td class="valueStyle">
					<html:text property="cust_name" size="10"
						styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					电&nbsp;&nbsp;&nbsp;&nbsp;话
				</td>
				<td class="valueStyle">
					<html:text property="cust_tel_home" size="13"
						styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle"
					onClick="window.open('fixedContact/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					onMouseOver="this.style.cursor='pointer';">
					<b>选择地址</b>
				</td>
				<td class="valueStyle">
					<html:text property="cust_addr" size="13"
						styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr class="buttonAreaStyle">
				<td colspan="8">
					<input name="btnSearch" type="button"  
						value="查询" onclick="query()" class="buttonStyle"/>
					<input name="btnAdd" type="button"   value="添加"
						onclick="popUp('windows','fixedContact.do?method=toFixedContactLoad&type=insert',750,525)" class="buttonStyle"/>
					<html:reset value="刷新" styleClass="buttonStyle" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
t