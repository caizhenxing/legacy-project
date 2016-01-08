<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

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
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript">
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
    	
    	//2008-03-20 增加 loc 减少更改
    	loc = "custinfo.do?method=toCustinfoLoad&type=" + loc;
    	
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
	<html:form action="/callcenter/userInfo.do?method=toCustinfoMain"
		method="post" target="_blank">
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="80px">
					选择
				</td>
				<td class="listTitleStyle" >
					姓名
				</td>
				<logic:equal name="telType" value="mobile" scope="session">
					<td class="listTitleStyle" >
						手机
					</td>
				</logic:equal>
				<logic:equal name="telType" value="homephone" scope="session">
					<td class="listTitleStyle" >
						宅电
					</td>
				</logic:equal>
				<logic:equal name="telType" value="workphone" scope="session">
					<td class="listTitleStyle" >
						办公电话
					</td>
				</logic:equal>
				<td class="listTitleStyle" width="10%">
					客户行业
				</td>
				<td class="listTitleStyle" width="15%">
					客户类型
				</td>
			</tr>
			<logic:iterate id="pagelist" name="list" indexId="i">
				<%
							String style = i.intValue() % 2 == 0 ? "oddStyle"
							: "evenStyle";
				%>
				<tr>
					<td >
						<logic:equal name="telType" value="homephone" scope="session">
							<input name="educe" type="radio" id="educe"
								onclick="setCheckbox(this)"
								value="<bean:write name='pagelist' property='cust_tel_home'/>">
						</logic:equal>
						<logic:equal name="telType" value="mobile" scope="session">
							<input name="educe" type="radio" id="educe"
								onclick="setCheckbox(this)"
								value="<bean:write name='pagelist' property='cust_tel_mob'/>">
						</logic:equal>
						<logic:equal name="telType" value="workphone" scope="session">
							<input name="educe" type="radio" id="educe"
								onclick="setCheckbox(this)"
								value="<bean:write name='pagelist' property='cust_tel_work'/>">
						</logic:equal>
					</td>
					<td >
						<bean:write name="pagelist" property="cust_name" filter="true" />
					</td>
					<logic:equal name="telType" value="homephone" scope="session">
						<td >
							<bean:write name="pagelist" property="cust_tel_home"
								filter="true" />
						</td>
					</logic:equal>
					<logic:equal name="telType" value="mobile" scope="session">
						<td >
							<bean:write name="pagelist" property="cust_tel_mob" filter="true" />
						</td>
					</logic:equal>
					<logic:equal name="telType" value="workphone" scope="session">
						<td >
							<bean:write name="pagelist" property="cust_tel_work"
								filter="true" />
						</td>
					</logic:equal>
					<td >
						<bean:write name="pagelist" property="cust_voc" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_type" filter="true" />
					</td>

				</tr>
			</logic:iterate>
			<tr>
				<td width="80px" class="pageTable">
			    </td>
<%--				<td colspan="3" class="evenStyle">--%>
				<td colspan="3" class="pageTable">
					<page:page name="userpageTurning" style="second" />
				</td>
				<td align="center" class="pageTable">
				  <input type="button" value="确认" onclick="clearTopValues()" class="buttonStyle"/>
				</td>
			</tr>
			<jsp:include flush="true" page="incforUser.jsp?dbType=focus" />
		</table>
	</html:form>
</body>
</html:html>