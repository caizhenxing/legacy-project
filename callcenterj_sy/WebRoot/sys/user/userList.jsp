<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
     <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body class="listBody">
    <html:form action="/sys/user/UserOper.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr class="tdbgpiclist">

		    <td class="listTitleStyle">
		    	�û�����
		    </td>
		    <td class="listTitleStyle">
		    	��ϯ����
		    </td>
		     <td class="listTitleStyle">
		    	��ϯ��ɫ 
		    </td>
		     <td class="listTitleStyle">
		    	��&nbsp;��&nbsp;��
		    </td>
		     <td class="listTitleStyle">
		    	�Ƿ񶳽�
		    </td>
<%--		    <td class="listTitleStyle">--%>
<%--		    	��&nbsp;&nbsp;&nbsp;&nbsp;ע--%>
<%--		    </td>--%>
		    <td class="listTitleStyle" width="100px">
		    	��&nbsp;&nbsp;&nbsp;&nbsp;�� 
		    </td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr>
<%--		    <td >--%>
<%--		    <bean:write name='c' property='id' filter='true'/>--%>
<%--		    </td>--%>
		  
		    <td >
		    <bean:write name="c" property="userName" filter="true"/>
		    </td>
		    <td >
		    <bean:write name="c" property="id" filter="true"/>
		    </td>
		    <td >
		    <bean:write name="c" property="roleName" filter="true"/>
		    </td>
		    <td >
		    <bean:write name="c" property="groupName" filter="true"/>
		    </td>
		     <td >
		    <bean:write name="c" property="isFreeze" filter="true"/>
		    </td>
		
<%--		     <td >--%>
<%--		    <bean:write name="c" property="remark" filter="true"/>--%>
<%--		    </td>--%>
		    
		    <td >
            <img alt="��ϸ" src="../../style/<%=styleLocation %>/images/detail.gif" 
            onclick="popUp('1<bean:write name='c' property='id'/>','UserOper.do?method=toUserLoginload&type=detail&id=<bean:write name='c' property='id'/>',750,310)" width="16" height="16" border="0"/>
            <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif" 
            onclick="popUp('2<bean:write name='c' property='id'/>','UserOper.do?method=toUserLoginload&type=update&id=<bean:write name='c' property='id'/>',750,310)" width="16" height="16" border="0"/>
		    <img alt="��Ȩ" src="../../style/<%=styleLocation %>/images/power.gif"
		    onclick="popUp('3<bean:write name='c' property='id'/>','../right.do?method=loadUser&user=<bean:write name='c' property='id'/>',750,310)" width="16" height="16"  border="0"/>
		    <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"
		    onclick="popUp('4<bean:write name='c' property='id'/>','UserOper.do?method=toUserLoginload&type=delete&id=<bean:write name='c' property='id'/>',750,310)" width="16" height="16"  border="0"/>
		    </td>
		    
		    
		  </tr>
		  </logic:iterate>
		  <tr>
		  	<td width="128px" class="pageTable">
		  	</td>
		    <td colspan="4" class="pageTable">
				<page:page name="userLoginTurning" style="second"/>
		    </td>
		    <td width="102px" class="pageTable"  style="text-align:right;">
		    	<input type="button" style="display:block" name="select" value="���" class="buttonStyle"
						onclick="popUp('windowsAddUser','../../sys/user/UserOper.do?method=toUserLoginload&type=insert',750,310)" />
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
