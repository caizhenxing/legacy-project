<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
		//删除选择
		function delSelect(){
			document.forms[0].action = "../operaffiche.do?method=operAfiche&type=delete";
			document.forms[0].submit();
		}
    </script>
  </head>
  
  <body>
    <html:form action="/oa/operaffiche.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="agrofront.afiche.aficheList.select"/></td>
		    <td><bean:message key="agrofront.afiche.aficheList.afichetitle"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <html:multibox property="chk"><bean:write name='c' property='id' filter='true'/></html:multibox>
		    </td>
		    <td class="<%=style%>"><bean:write name="c" property="aficheTitle" filter="true"/></td>
		  </tr>
		  </logic:iterate>
		  <tr>
		  	<td>
		  	<a href="javascript:selectAll()"><bean:message key="agrofront.afiche.aficheList.selectornot"/></a>
		    <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
		    
		    <input type="button" name="btnDel" value="<bean:message key='agrofront.common.delete'/>" onclick="delSelect()"/>
		  	</td>
		    <td align="right">
				<page:page name="afichepageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
