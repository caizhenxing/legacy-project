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
        //Ñ¡ÔñËùÓÐ
    	function selectAll() {
			for (var i=0;i<document.forms[0].chk.length;i++) {
				var e=document.forms[0].chk[i];
				e.checked=!e.checked;
			}
		}
		//É¾³ýÑ¡Ôñ
		function delSelect(){
			document.forms[0].action = "../handsetnote.do?method=operHandSetNote&type=delete";
			document.forms[0].submit();
		}
		//ÓÀ¾ÃÉ¾³ý
		function delForever(){
			document.forms[0].action = "../handsetnote.do?method=operHandSetNote&type=deleteForever";
			document.forms[0].submit();
		}
    </script>
  </head>
  
  <body>
  
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
	
    <html:form action="/oa/communicate/handsetnote.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="et.oa.communicate.handsetnote.handsetnotelist.select"/></td>
		    <td><bean:message key="et.oa.communicate.handsetnote.handsetnotelist.num"/></td>
		    <td><bean:message key="et.oa.communicate.handsetnote.handsetnotelist.content"/></td>
		    <td><bean:message key="et.oa.communicate.handsetnote.handsetnotelist.sendtime"/></td>
		    <td><bean:message key="et.oa.communicate.handsetnote.handsetnotelist.operator"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <html:multibox property="chk"><bean:write name='c' property='id' filter='true'/></html:multibox>
		    </td>
		    <td class="<%=style%>"><bean:write name="c" property="handsetnum" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="handsetinfo" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="sendtime" filter="true"/></td>
		    <td class="<%=style%>">
			<img alt="<bean:message key='agrofront.common.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('handsetnote.do?method=toHandsetNoteLoad&type=update&id=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		  	<td colspan="2">
		  	<a href="javascript:selectAll()"><bean:message key="agrofront.afiche.aficheList.selectornot"/></a>
		    <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
		    
		    <input type="button" name="btnDel" value="<bean:message key='et.oa.communicate.handsetnote.handsetnotelist.delforever'/>" onclick="delForever()"/>
		    <input type="button" name="btnDel" value="<bean:message key='agrofront.common.delete'/>" onclick="delSelect()"/>
		  	</td>
		    <td align="right">
				<page:page name="handsetpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
