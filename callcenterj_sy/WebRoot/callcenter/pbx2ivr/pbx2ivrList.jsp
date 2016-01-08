<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<html:base />
		<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
			<script>
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
	
			editorWin = window.open(loc,win_name, 'status=yes,resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name,  'status=yes,resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}	
			</script>
	</head>

	<body class="listBody">

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="listTable">
				<tr>
					<td class="listTitleStyle">
						交换机类型
					</td>
					<td class="listTitleStyle">
						交换机端口
					</td>
					<td class="listTitleStyle">
						IVR端口
					</td>
					<td class="listTitleStyle">
						备注
					</td>
					<td class="listTitleStyle">
						操作
					</td>

				</tr>
				<logic:iterate id="c" name="list" indexId="i">
					<%
								String style = i.intValue() % 2 == 0 ? "oddStyle"
								: "evenStyle";
					%>

					<tr>
						<td >
							<bean:write name="c" property="pbxType" filter="true" />
						</td>
						<td >
							<bean:write name="c" property="pbxPort" filter="true" />
						</td>
						<td >
							<bean:write name="c" property="ivrPort" filter="true" />
						</td>
						<td >
							<bean:write name="c" property="remark" filter="true" />
						</td>
						<td >
							<img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"
								onclick="popUp('inquiryWindows','pbx2ivr.do?method=toLoad&type=update&id=<bean:write name='c' property='id'/>',350,200)"
								width="18" height="18" border="0"  />
							<img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"
								onclick="popUp('inquiryWindows','pbx2ivr.do?method=toLoad&type=delete&id=<bean:write name='c' property='id'/>',350,200)"
								width="18" height="18" border="0"  />
						</td>
					</tr>
				</logic:iterate>
			</table>
	</body>
</html>
l