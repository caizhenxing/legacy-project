<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.selectStyle{
		width:120px;
	}
	</style>
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "messages.do?method=toMessagesList";
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
   	<script language="javascript" type="text/JavaScript" src="../js/Date.js"></script>

</head>

<body class="conditionBody">
	<html:form action="/messages/messages" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;消息管理
					</td>
				</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="conditionTable">
			<tr>
				
				<td class="queryLabelStyle">
					消息类型
				</td>
				<td class="valueStyle">
					<html:select property="type" styleClass="selectStyle" style="width:50px;">
						<html:option value="">全部</html:option>
						<html:option value="1">接收</html:option>
						<html:option value="0">发送</html:option>
				    </html:select>
				</td>
				<td class="queryLabelStyle" >
					消息内容
				</td>
				<td class="valueStyle">
					<html:text property="message_content" styleClass="writeTextStyle" size="15"/>
				</td>
				<td class="queryLabelStyle">
					是否读取
				</td>
				<td class="valueStyle">
				    <html:select property="dict_read_flag" styleClass="selectStyle" style="width:50px;">
				    	<html:option value="">全部</html:option>
						<html:option value="0">未读</html:option>
						<html:option value="1">已读</html:option>
				    </html:select>
				</td>
				<td width="95px" class="queryLabelStyle" style="text-align:right;">
					<input name="btnSearch" class="buttonStyle" type="button"   value="查询"
						onclick="query()" />
					<input style="display:none;" name="btnAdd" type="button"   value="发送消息"
						onclick="popUp('windows','messages.do?method=toMessagesLoad&type=insert',680,125)" />
					<html:reset value="刷新" styleClass="buttonStyle" onclick="parent.bottomm.document.location=parent.bottomm.document.location;"/>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>