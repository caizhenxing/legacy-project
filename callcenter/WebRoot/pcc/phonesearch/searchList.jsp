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
    <script language="javascript">
        //选择所有
    	function selectAll() {
			for (var i=0;i<document.forms[0].chk.length;i++) {
				var e=document.forms[0].chk[i];
				e.checked=!e.checked;
			}
		}
		//执行操作
		function operit(){
			document.forms[0].action = "../fuzz.do?method=operFuzzInfo";
			document.forms[0].submit();
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/pcc/phonesearch/search.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message bundle="pcc" key="et.pcc.phonesearch.list.num"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.phonesearch.list.unit"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.phonesearch.list.dep"/></td>
		    <td><bean:message bundle="pcc" key="sys.operator"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="num" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="unit" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="deprtment" filter="true"/></td>
		    <td class="<%=style%>">
		    	<img alt="<bean:message bundle='pcc' key='et.pcc.assay.question.questionlist.moreinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('search.do?method=toSearchLoad&type=see&id=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
				<logic:equal value="checkrole" name="checkrole">
				<img alt="<bean:message bundle="pcc" key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('search.do?method=toSearchLoad&type=update&id=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
				<img alt="<bean:message bundle="pcc" key='sys.del'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('search.do?method=toSearchLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>   	    
		    	</logic:equal>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="searchpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
