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
   </style>
   <script language="javascript" src="../../js/common.js"></script>
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
   </script>
  </head>
  
  <body class="listBody" onload="setInterval('searchConfno()',60000)">
    <html:form action="/callcenter/conf" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="navigateStyle" colspan="5">
		    当前位置&ndash;&gt;
		    会议管理
		    </td>
		    <td class="navigateStyle" align="right"></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyleLeft" colspan="5">
		    选择会议室
		    <html:select property="roomInfo" styleClass="selectStyle" onchange="selectRoominfo()">
						<html:options collection="confroom" property="value"
							labelProperty="label" />
			</html:select>
		    </td>
		    <td class="listTitleStyle" align="right"></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">通道号</td>
		    <td class="listTitleStyle">主叫号码</td>
<%--		    <td class="listTitleStyle">进入时间</td>--%>
		    <td class="listTitleStyle">会议室号</td>
		    <td class="listTitleStyle">当前状态</td>
		    <td class="listTitleStyle">操作</td>
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
		    <td ><bean:write name="c" property="charroomstate"  filter="true"/></td>
		    <td align="center" width="10%">
            <img alt="操作" src="../../style/<%=styleLocation %>/images/detail.gif"
            onclick="window.open('conf.do?method=toOper&id=<bean:write name='c' property='id'/>','windows','width=300,height=50,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		</table>
    </html:form>
  </body>
</html:html>
