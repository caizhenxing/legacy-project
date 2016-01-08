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
    <html:form action="/pcc/operatorStatistic" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist" width="12%">
		        <bean:message bundle="pccye" key="sys.common.time"></bean:message>
		    </td>
		    <td class="tdbgpiclist">
		        <logic:notEmpty name="opertype">
		          <bean:message bundle="pccye" name="opertype"></bean:message>	 
	            </logic:notEmpty>
		    </td>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.operatorStatistic.operator"/></td>--%>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.operatorStatistic.signInNum"/></td>--%>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.operatorStatistic.settingNum"/></td>--%>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.operatorStatistic.answerPhoneNum"/></td>--%>
<%--		    <td class="tdbgpiclist">结束时间</td>--%>
<%--		    <td class="tdbgpiclist">收听录音</td>--%>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="sys.common.detail"/></td>--%>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="operateDate" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="chuxiInfo" filter="true"/></td>
<%--		    <td class="<%=style%>"><bean:write name="c" property="setting"  filter="true"/></td>--%>
<%--		    <td class="<%=style%>"><bean:write name="c" property="answerPhone" filter="true"/></td>--%>		    
<%--		    <td class="<%=style%>">--%>
<%--		    <a href="<bean:write name='c' property='recFile' filter='true'/>" target=_blank>收听录音</a>--%>
<%--		    </td>--%>		    
<%--		    <td align="center" class="<%=style%>" width="10%">--%>
<%--		    <logic:equal name="c" property="sortMark" value="1"> --%>
<%--		    <a href="../callinFirewall.do?method=toCallinFireWallLoad&type=update&id=<bean:write name='c' property='id'/>"><img alt="<bean:message bundle='pcc' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    <a href="../callinFirewall.do?method=toCallinFireWallLoad&type=delete&id=<bean:write name='c' property='id'/>"><img alt="<bean:message bundle='pcc' key='sys.del'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" border="0"  /></a>--%>
<%--这个--%>
<%--            <img alt="<bean:message key='sys.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('operatorStatistic.do?method=toOperatorStatisticDetail&userId=<bean:write name='c' property='operator'/>&st=<bean:write name='c' property='beginTime' filter='true'/>&et=<bean:write name='c' property='endTime' filter='true' />','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>--%>
<%--            <img alt="<bean:message key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('callinFirewall.do?method=toCallinFireWallLoad&type=update&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>--%>
<%--		    <img alt="<bean:message key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('callinFirewall.do?method=toCallinFireWallLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="c" property="sortMark" value="2"> --%>
<%--		    <a href="../addressListSort.do?method=toAddressListSortLoad&type=update&addressListSign=common&id=<bean:write name='c' property='id'/>"><img alt="<bean:message key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    <a href="../addressListSort.do?method=toAddressListSortLoad&type=delete&addressListSign=common&id=<bean:write name='c' property='id'/>"><img alt="<bean:message key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" border="0"  /></a>--%>
<%--		    </logic:equal>--%>
<%--		    &nbsp;--%>
<%--		    </td>--%>
		 </tr>
		  </logic:iterate>

		</table>
    </html:form>
  </body>
</html:html>
