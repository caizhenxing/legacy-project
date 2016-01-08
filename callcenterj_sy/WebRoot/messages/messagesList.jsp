<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
	</script>
</logic:notEmpty>

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
	
	<script language="javascript">
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
    	
    	//2008-03-20 增加 loc 减少更改
    	loc = "messages.do?method=toMessagesLoad&type=" + loc;
    	
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
	<html:form action="/messages/messages" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr>
		  <td class="listTitleStyle" width="50">选择</td>
		    <td class="listTitleStyle" width="123px" align="center" style="display:none;">发送人ID</td>
		    <td class="listTitleStyle"  align="center">发送人姓名</td>
		    <td class="listTitleStyle"  align="center">接收人工号</td>
		    <td class="listTitleStyle"  align="center">消息内容</td>
		    <td class="listTitleStyle"  align="center">接发时间</td>
		    <td class="listTitleStyle"  align="center">是否已读</td>
		    <td class="listTitleStyle" width="95px align="center">操作</td>
		  </tr>
		  <logic:iterate id="pagelist" name="list" indexId="i">
		  <%
			String style = i.intValue() % 2 == 0 ? "oddStyle": "evenStyle";
		  %>
		  <tr>
		 	 <td >
		  				<input name="educe" type="checkbox" id="educe"
							onclick="setCheckbox(this)" value="<bean:write name='pagelist' property='message_id'/>">
		  	</td>
		  	<td style="display:none;"><bean:write name="pagelist" property="send_id" filter="true"/></td>
		    <td ><bean:write name="pagelist" property="send_name" filter="true"/></td>
		    <td ><bean:write name="pagelist" property="receive_id" filter="true"/></td>
		    <td ><bean:write name="pagelist" property="message_content" filter="true"/></td>
            <td ><bean:write name="pagelist" property="send_time" filter="true"/></td>
            <td ><bean:write name="pagelist" property="dict_read_flag" filter="true"/></td>
            <td >

		     <img alt="详细" src="../style/<%=styleLocation %>/images/detail.gif" onclick="popUp('21<bean:write name='pagelist' property='message_id'/>','detail&id=<bean:write name='pagelist' property='message_id'/>',600,175)"/>
             <img alt="修改" src="../style/<%=styleLocation %>/images/update.gif"     onclick="popUp('1<bean:write name='pagelist' property='message_id'/>','update&id=<bean:write name='pagelist' property='message_id'/>',600,175)"/>
             <img alt="删除" src="../style/<%=styleLocation %>/images/del.gif"        onclick="popUp('2<bean:write name='pagelist' property='message_id'/>','delete&id=<bean:write name='pagelist' property='message_id'/>',600,175)"/>
            </td>
		  </tr>
		  </logic:iterate>
		  <tr>
			<td colspan="2" class="pageTable">
				<input name="Submit1" type="button" id="Submit1" onClick="selAllCheckBox('educe')" value="反选" class="buttonStyle">
				<input name="outputID" type="hidden" id="outputID" value="">
				<input name="Submit4" type="button" id="Submit4" onClick="clearTopValues()" class="buttonStyle" value="批量删除" />
		    </td>
		    <td colspan="4" class="pageTable"><page:page name="userpageTurning" style="second"/></td>
		  	<td width="95px" style="text-align:right;" class="pageTable">
		  	<input class="buttonStyle"name="btnAdd" type="button"   value="发送消息"
						onclick="popUp('windows','insert',600,175)" />
		  	</td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>

<script>

	var topO = window.parent.parent.frames.topFrame;
	if(topO){
		topO = window.parent.parent.frames.item("topFrame").document.getElementById('outputID')
	}else{
		topO = window.parent.parent.opener.parent.frames.topFrame.document.getElementById('outputID');
	}
function selAllCheckBox(checkboxname){//全选方法
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		if (checkboxs.length==null) {
			if(checkboxs.checked){
				checkboxs.checked = false;
				clearTopValue(checkboxs.value);
			}else{
				checkboxs.checked = true;
				setTopValue(checkboxs.value);
			}
			return;
		}
		for (i=0; i<checkboxs.length; i++) {
			if(checkboxs[i].checked){
				checkboxs[i].checked = false;
				clearTopValue(checkboxs[i].value);
			}else{
				checkboxs[i].checked = true;
				setTopValue(checkboxs[i].value);
			}
		}
	}
}
function clearAllCheckBox(checkboxname) {//取消全选
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			checkboxs[i].checked = false;
			clearTopValue(checkboxs[i].value);
		}
	}
}
function setTopValue(v){//添加某值
	
	var topObj = topO;

	var topValue = topObj.value;
	if(topValue != ""){
		topValue += "," + v;
	}else{
		topValue = v;
	}
	topObj.value = topValue;
}
function clearTopValue(v){//清除某值
	
	var topObj =  topO;

	var topValue = topObj.value;
	var index = topValue.indexOf(v);
	if(index!=-1){
		var tv1 = "";
		var tv2 = "";
		if(v.length+index == topValue.length){//判断是否是最后一个，如果是最后一个，则去掉前边的那个逗号
			tv1 = topValue.substring(0,index-1);
			tv2 = "";
		}else{
			tv1 = topValue.substring(0,index);
			tv2 = topValue.substring(v.length+1+index,topValue.length);
		}
		topObj.value = tv1 + tv2;
	}
}
function clearTopValues(){//当点击导出后清除全部值

	var topObj =  topO;//alert("44 = "+topObj.value);
	document.getElementById('outputID').value = topObj.value;//设置需要提交的值
	topObj.value = "";//清空临时保存的值
	//var checkboxs = document.all.item("educe");
	//for (i=0; i<checkboxs.length; i++) {//清除当页选中状态
		//checkboxs[i].checked = false;
	//}
	
	document.forms[0].action = "../messages/messages.do?method=toMessagesOper&type=delall";
	document.forms[0].submit();
}
function equalsCheckbox(){//比较值后显示是否选中

	var topObj =  topO;
	var checkboxs = document.all.item("educe");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(topObj.value.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
}
function setCheckbox(obj){//根据对象状态判断操作是选中还是取消
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
function setCheckbox(obj){//根据对象状态判断操作是选中还是取消
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
</script>
 