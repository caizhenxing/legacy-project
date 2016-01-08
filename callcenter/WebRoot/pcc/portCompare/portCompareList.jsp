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
      	//
    	function popUp( win_name,loc, w, h, menubar,center ) {
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/pcc/portCompare" method="post">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle" colspan="5">
		    <bean:message key="sys.current.page"/>
		    <bean:message bundle="pccye" key="et.pcc.portCompare"/>
		    </td>
		    <td class="tdbgcolorquerytitle" align="right"><input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pccye' key='sys.add'/>" onclick="popUp('windows','../pcc/portCompare.do?method=toPortCompareLoad&type=insert',680,400)"/></td>
		  </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.portCompare.physicsPort"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.portCompare.portType"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.portCompare.state"/></td>
<%--		    <td class="tdbgpiclist">结束时间</td>--%>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.portCompare.logicPort"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.portCompare.ip"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="sys.operator"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style ="tdbgcolorlist1";
			%>
			<logic:equal name="c" property="PortInOrOut" value="0">
			<%
				 style ="tdbgcolorlist2";
			%>
			</logic:equal>
<%--			<logic:notEqual name="c" property="PortInOrOut" value="0">--%>
<%--			<%--%>
<%--				 style ="tdbgcolorlist2";--%>
<%--			%>--%>
<%--			</logic:notEqual>--%>
<%--			<%--%>
<%--				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";--%>
<%--			%>--%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="physicsPort" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="portType" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="state" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="logicPort" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="ip"  filter="true"/>
<%--		    <logic:equal name="c" property="PortInOrOut" value="0">--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="physicsPort" filter="true"/></td>--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="portType" filter="true"/></td>--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="state" filter="true"/></td>--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="logicPort" filter="true"/></td>--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="ip"  filter="true"/>--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="c" property="beginTime" value="">无限制</logic:equal>--%>
		    </td>
<%--		    <td class="<%=style%>"><bean:write name="c" property="endTime" format="HH:mm" filter="true"/>--%>
<%--		    <logic:equal name="c" property="endTime" value="">无限制</logic:equal></td>--%>
<%--		    <td class="<%=style%>" width="10%">--%>
<%--		    <logic:equal name="c" property="isPass" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.pass"/></logic:equal>--%>
<%--            <logic:notEqual name="c" property="isPass" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.unpass"/></logic:notEqual>--%>
<%--            </td>--%>
<%--		    <td class="<%=style%>" width="10%">--%>
<%--		    <logic:equal name="c" property="isAvailable" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.able"/></logic:equal>--%>
<%--            <logic:notEqual name="c" property="isAvailable" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.disable"/></logic:notEqual>--%>
<%--		    </td>--%>
		    <td align="center" class="<%=style%>" width="10%">
<%--		    <logic:equal name="c" property="sortMark" value="1"> --%>
<%--		    <a href="../callinFirewall.do?method=toCallinFireWallLoad&type=update&id=<bean:write name='c' property='id'/>"><img alt="<bean:message bundle='pccye' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    <a href="../callinFirewall.do?method=toCallinFireWallLoad&type=delete&id=<bean:write name='c' property='id'/>"><img alt="<bean:message bundle='pccye' key='sys.del'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" border="0"  /></a>--%>
            <img alt="<bean:message key='sys.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('portCompare.do?method=toPortCompareLoad&type=detail&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
            <img alt="<bean:message key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('portCompare.do?method=toPortCompareLoad&type=update&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('portCompare.do?method=toPortCompareLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="c" property="sortMark" value="2"> --%>
<%--		    <a href="../addressListSort.do?method=toAddressListSortLoad&type=update&addressListSign=common&id=<bean:write name='c' property='id'/>"><img alt="<bean:message key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    <a href="../addressListSort.do?method=toAddressListSortLoad&type=delete&addressListSign=common&id=<bean:write name='c' property='id'/>"><img alt="<bean:message key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    </logic:equal>--%>
<%--		    &nbsp;--%>
		    </td>
		  </tr>
		  </logic:iterate>
<%--		  <tr>--%>
<%--		    <td colspan="6">--%>
<%--				<page:page name="portComparepageTurning" style="first"/>--%>
<%--		    </td>--%>
<%--		  </tr>--%>
		</table>
    </html:form>
  </body>
</html:html>
