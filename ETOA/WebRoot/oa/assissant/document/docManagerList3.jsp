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
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body>
    <html:form action="/oa/assissant/doc" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.folderCode"/></td>
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.folderName"/></td>
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.folderType"/></td>
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.createTime"/></td>
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.folderVersion"/></td>
		    <td><bean:message key="agrofront.oa.assissant.document.docManagerList.operation"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="folderCode" filter="true"/></td>
		    <td class="<%=style%>">
		    <a href="<bean:write name="c" property="folderId" filter="true"/>"><bean:write name="c" property="folderName" filter="true"/></a>
		    </td>
		    <td class="<%=style%>"><bean:write name="c" property="folderType" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="createTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="folderVersion" filter="true"/></td>
		    <td   class="<%=style%>">
		    <html:link action="/oa/assissant/doc.do?method=toDocLoad&type=update" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',480,400)" target="windows">
		    <bean:message key="agrofront.oa.assissant.document.docManagerList3.update"/>
		    </html:link>
		    /&nbsp;
		    <a href="<bean:write name='c' property='folderId' filter='true'/>"><bean:message key="agrofront.oa.assissant.document.docManagerList3.download"/></a>
<!--		    /&nbsp;-->
<!--		    <html:link action="/oa/assissant/doc.do?method=toDocLoad&type=delete" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',480,400)" target="windows">-->
<!--		    »ØÍË°æ±¾-->
<!--		    </html:link>-->
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td  colspan="7">
			<page:page name="agropageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
