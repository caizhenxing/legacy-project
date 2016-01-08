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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
 	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body class="listBody">
    <html:form action="/sys/role/Role" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr class="tdbgpiclist">
		    <td class="listTitleStyle" width="128px">角色名称<br></td>
		    <td class="listTitleStyle">是否冻结</td>
		    <td class="listTitleStyle">备注</td>
		    <td class="listTitleStyle" width="102px">操作</td>
		  </tr>
		  
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
		  %>
		  <tr>
		    <td ><bean:write name="c" property="name" filter="true"/></td>
		    <td ><logic:equal name="c" property="delMark" value="0"><bean:message bundle="sys" key="sys.role.roleManagerList.yes"/></logic:equal><logic:notEqual name="c" property="delMark" value="0"><bean:message bundle="sys" key="sys.role.roleManagerList.no"/></logic:notEqual></td>
		    <td ><bean:write name="c" property="remark" filter="true"/></td>
		    <td >
		    <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"
		    onclick="popUp('1<bean:write name='c' property='id'/>','Role.do?method=toRoleLoad&type=update&id=<bean:write name='c' property='id'/>',400,180)" width="16" height="16" target="windows" border="0"/>
		    <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"
		    onclick="popUp('2<bean:write name='c' property='id'/>','Role.do?method=toRoleLoad&type=delete&id=<bean:write name='c' property='id'/>',400,180)" width="16" height="16" target="windows" border="0"/>	    
		    <img alt="授权" src="../../style/<%=styleLocation %>/images/power.gif"
		    onclick="popUp('3<bean:write name='c' property='id'/>','./../leafRight/leafRight.do?method=loadParamTree&roleId=<bean:write name='c' property='id'/>',800,600)" width="16" height="16" target="windows" border="0"/>	
		    <img style="display:none;" alt="用户角色批量授权" src="../../style/<%=styleLocation %>/images/power.gif"
		    onclick="popUp('4<bean:write name='c' property='id'/>','../../sys/leafRight/leafRight.do?method=loadDeptRoleTree&roleId=<bean:write name='c' property='id'/>',750,300)" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		   	<td width="128px" class="pageTable">
		  	</td>
		    <td colspan="2" class="pageTable">
			<page:page name="roleTurning" style="second"/>
		    </td>
		    <td width="102px" class="pageTable"  style="text-align:right;">
		    	<input  name="btnAdd" type="button" class="buttonStyle" value="添加"
						onclick="popUp('windows','../role/Role.do?method=toRoleLoad&type=insert',400,180)" />
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
