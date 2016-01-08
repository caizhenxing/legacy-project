<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


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
    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body>
    <html:form action="/sys/group/Group" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td>组名称<br></td>
		    <td>是否冻结</td>
		    <td>备注</td>
		    <td>操作</td>
		  </tr>
		  
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
  			String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td ><bean:write name="c" property="name" filter="true"/></td>
		    <td ><logic:equal name="c" property="delMark" value="0">是</logic:equal><logic:notEqual name="c" property="delMark" value="0">否</logic:notEqual></td>
		    <td ><bean:write name="c" property="remark" filter="true"/></td>
		    <td >
		    <img alt="修改" src="../../images/sysoper/update.gif" onclick="popUp('windows','Group.do?method=toGroupLoad&type=update&id=<bean:write name='c' property='id'/>',550,250)" width="16" height="16" target="windows" border="0"/>
		    <img alt="删除" src="../../images/sysoper/del.gif" onclick="popUp('windows','Group.do?method=toGroupLoad&type=delete&id=<bean:write name='c' property='id'/>',550,250)" width="16" height="16" target="windows" border="0"/>	    
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4" >
			<page:page name="groupTurning" style="second"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
