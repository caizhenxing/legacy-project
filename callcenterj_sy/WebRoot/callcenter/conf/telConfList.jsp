<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>会议管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
   
   <style type="text/css">
   	.listTitleStyleLeft {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	line-height: 23px;
	font-weight: bold;
	color: #333333;
	text-align: left;
	background-image: url(../../style/chun/images/tiaoti2.jpg);
}
	.contextMenu ul li{
		font-size:1em;
	}
   </style>
      	<!-- 引入dwr -->	
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/confManagerService.js'></script>
 	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
   <script language="javascript" src="../../js/common.js"></script>
   <script src="../../js/jquery/jquery.js"></script>
   <script src="../../js/jquery/plug/jquery.contextmenu.r2.js"></script>
   

   
   <script type="text/javascript">
   		//选择会议室
   		function selectRoominfo(){
   			document.forms[0].action = "../conf.do?method=toSelectConf";
   			document.forms[0].submit();
   		}
   		//查找会议通道号
   		function searchConfno(){
   			document.forms[0].action = "../conf.do?method=toConfList";
   			document.forms[0].submit();
   		}
   		function execFlag(id)
   		{
   			alert(id);
   		}
   </script>
  </head>
  
  <body class="listBody" onload="setInterval('searchConfno()',60000)">
    <html:form action="/callcenter/conf" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="navigateStyle" colspan="5">
		    当前位置&ndash;&gt;会议管理
		    </td>
		  </tr>
		</table>		
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">通道号</td>
		    <td class="listTitleStyle">主叫号码</td>
<%--		    <td class="listTitleStyle">进入时间</td>--%>
		    <td class="listTitleStyle">会议室号</td>
		    <td class="listTitleStyle">当前状态</td>
		    <td class="listTitleStyle">申请新状态</td>
		    <td class="listTitleStyle">审批状态</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style ="oddStyle";
			%>
			<logic:equal name="c" property="id" value="0">
			<%
				 style ="evenStyle";
			%>
			</logic:equal>
		  <tr>
		    <td ><bean:write name="c" property="channo" filter="true"/></td>
		    <td ><bean:write name="c" property="callid"  filter="true"/></td>
<%--		    <td ><bean:write name="c" property="rectime"  filter="true"/></td>--%>
		    <td ><bean:write name="c" property="roomid"  filter="true"/></td>
		    <td ><span class="currentstate" style="color:green;" id="<bean:write name="c" property="id"  filter="true"/>"><bean:write name="c" property="charroomstate"  filter="true"/></span><img  src="../../style/<%=styleLocation %>/images/confIcon/<bean:write name="c" property="curStateIcon"  filter="true"/>" width="16" height="16"  border="0"/></td>
		     <td ><span class="newState" style="color:green;" id="<bean:write name="c" property="id"  filter="true"/>"><bean:write name="c" property="newstate"  filter="true"/></span><img  src="../../style/<%=styleLocation %>/images/confIcon/<bean:write name="c" property="newStateIcon"  filter="true"/>" width="16" height="16"  border="0"/></td>
		    <td ><span class="allowFlag" style="color:green;" id="<bean:write name="c" property="id"  filter="true"/>"><bean:write name="c" property="allowflag"  filter="true"/></span></td>
<%--		    <td ><input type="button" onclick="execFlag('<bean:write name="c" property="id"  filter="true"/>')" value="审批" /></td>--%>
		  </tr>
		  </logic:iterate>
		</table>
    </html:form>
     <!--右键菜单的源-->
     <div class="contextMenu" id="allowFlagMenu">
      <ul>
        <li id="setFlag" style="font-size:12">审&nbsp;&nbsp;&nbsp;&nbsp;核</li>
        <li id="clearFlag" style="font-size:12">未 审 核</li>
      </ul>
    </div>
    <div class="contextMenu" id="newStateMenu">
      <ul>
        <li id="newListenAndSay" style="font-size:12">能听能说</li>
        <li id="newOnlyListen" style="font-size:12">只听不说</li>
        <li id="newExitConf" style="font-size:12">退出会议</li>
      </ul>
    </div>
    <div class="contextMenu" id="currentstateMenu">
      <ul>
        <li id="newListenAndSay" style="font-size:12">能听能说</li>
        <li id="newOnlyListen" style="font-size:12">只听不说</li>
        <li id="newExitConf" style="font-size:12">退出会议</li>
      </ul>
    </div>
  </body>
</html:html>
<script language="javascript" >
//所有class为demo1的span标签都会绑定此右键菜单
     $('span.allowFlag').contextMenu('allowFlagMenu',
     {
         bindings:
          {
              'setFlag': function(t) {
                  confManagerService.setAllowFlag(t.id,'1');
              },
              'clearFlag': function(t) {
                  confManagerService.setAllowFlag(t.id,'0');
              }
          }

     });
     
     //newState
     $('span.newState').contextMenu('newStateMenu',
     {
         bindings:
          {
              'newListenAndSay': function(t) {
                  confManagerService.setNewState(t.id,'1');
              },
              'newOnlyListen': function(t) {
                  confManagerService.setNewState(t.id,'2');
              },
              'newExitConf': function(t) {
                  confManagerService.setNewState(t.id,'3');
              }
          }

     });
     
     //currentstateMenu
     $('span.currentstate').contextMenu('currentstateMenu',
     {
         bindings:
          {
              'newListenAndSay': function(t) {
                  confManagerService.setCurState(t.id,'1');
              },
              'newOnlyListen': function(t) {
                  confManagerService.setCurState(t.id,'2');
              },
              'newExitConf': function(t) {
                  confManagerService.setCurState(t.id,'3');
              }
          }

     });
</script>
