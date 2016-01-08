<%@ page pageEncoding="GBK" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
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
	<html:form action="/schema/fixedContact.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="15%">
					姓名
				</td>
				<td class="listTitleStyle" width="15%">
					宅电
				</td>
				<td class="listTitleStyle" width="15%">
					手机
				</td>
				<td class="listTitleStyle" width="15%">
					办公电话
				</td>
				<td class="listTitleStyle" width="10%">
					客户行业
				</td>
				<td class="listTitleStyle" width="15%">
					客户类型
				</td>
				<td class="listTitleStyle" width="10%">
					操作
				</td>
			</tr>
			<logic:iterate id="pagelist" name="list" indexId="i">
				<%
				String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
				%>
				<tr>
					<td >
						<bean:write name="pagelist" property="cust_name" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_tel_home" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_tel_mob" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_tel_work" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_voc" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_type" filter="true" />
					</td>
					<td >
						<img alt="详细"
							src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('fixedContactWindows','fixedContact.do?method=toFixedContactLoad&type=detail&id=<bean:write name='pagelist' property='cust_id'/>',750,405)"
							width="16" height="16" border="0" />
						<img alt="修改"
							src="../../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('fixedContactWindows','fixedContact.do?method=toFixedContactLoad&type=update&id=<bean:write name='pagelist' property='cust_id'/>',750,490)"
							width="16" height="16" border="0" />
						<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
							onclick="popUp('fixedContactWindows','fixedContact.do?method=toFixedContactLoad&type=delete&id=<bean:write name='pagelist' property='cust_id'/>',750,405)"
							width="16" height="16" border="0" />
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="7" class="pageTable">
					<page:page name="userpageTurning" style="second" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>