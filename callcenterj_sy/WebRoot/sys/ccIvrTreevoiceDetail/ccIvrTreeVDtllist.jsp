<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

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

	<title>语音详细列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
 	function fileAdd()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=insert'
<%--		  if(sTreeId!=0)--%>
<%--		  {--%>
<%--		  	fUrl = fUrl + '&treeId='+sTreeId;--%>
<%--		  }--%>
			var num=Math.round(Math.random( )*10000);
			popUp(num,fUrl,650,190);
	}
	function fileAddText()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=inserttext'
<%--		  if(sTreeId!=0)--%>
<%--		  {--%>
<%--		  	fUrl = fUrl + '&treeId='+sTreeId;--%>
<%--		  }--%>
			var num=Math.round(Math.random( )*10000);
			popUp(num,fUrl,710,480);
	}
 </script>
</head>

<body class="listBody">
	<html:form action="/sys/ccIvrTreevoiceDetail.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="183px">
					语音栏目
				</td>
				<td class="listTitleStyle">
					语音路径
				</td>
				<td class="listTitleStyle">
					排序
				</td>
<%--				<td class="listTitleStyle">--%>
<%--					备注--%>
<%--				</td>--%>
				<%--		    <td class="listTitleStyle">是否使用</td>--%>
				<%--		    <td class="listTitleStyle" style="display:none;">语言类型</td>--%>

				<td class="listTitleStyle" width="185px">
					操作
				</td>

			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
				String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
				%>

				<tr style="line-height: 21px">
					<td >
						<bean:write name="c" property="name" filter="true" />
					</td>
					<%--		  	<td ><bean:write name="c" property="infoContent" filter="true"/></td>--%>
					<td >
						<bean:write name="c" property="path" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="id" filter="true" />
					</td>
<%--					<td >--%>
<%--						<bean:write name="c" property="remark" filter="true" />--%>
<%--					</td>--%>

					<%--		    <td ><bean:write name="c" property="voicePath" filter="true"/></td>--%>
					<%--		    <td ><bean:write name="c" property="isUse" filter="true"/></td>--%>
					<%--		    <td style="display:none;"><bean:write name="c" property="languageType" filter="true"/></td>--%>
					<%--		    <td ><bean:write name="c" property="layerOrder" filter="true"/></td>--%>

					<td width="15%" >
						<img alt="详细"
							src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('1<bean:write name='c' property='id'/>','ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=detail&id=<bean:write name='c' property='id'/>&treeId=<bean:write name='c' property='treeId'/>',650,240)"
							width="16" height="16" border="0" />
						<img alt="修改"
							src="../../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('2<bean:write name='c' property='id'/>','ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=update&id=<bean:write name='c' property='id'/>&treeId=<bean:write name='c' property='treeId'/>',650,240)"
							width="16" height="16" border="0" />
						<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
							onclick="popUp('3<bean:write name='c' property='id'/>','ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=delete&id=<bean:write name='c' property='id'/>&treeId=<bean:write name='c' property='treeId'/>',650,240)"
							width="16" height="16" border="0" />
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="2" class="pageTable">
					<page:page name="operpriceinfopageTurning" style="second" />
				</td>
				<td class="pageTable" colspan="2" style="text-align:right">
					<input type="button" style="display:inline;" name="btnadd"
						value="文件添加" class="buttonStyle" onclick="fileAdd()" />
					<input type="button" style="display:inline;" name="btnaddtext"
						value="文本添加" class="buttonStyle" onclick="fileAddText()" />
				</td>
			</tr>
		</table>

	</html:form>
</body>
</html:html>
