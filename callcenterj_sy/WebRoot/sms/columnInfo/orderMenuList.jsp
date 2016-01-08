<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("�Ѿ����ƹ���ҵ�񣬲����ظ����ƣ�");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("�Ѿ��˶�����ҵ�񣬲����ظ��˶���");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
	</script>
	</logic:equal>
</logic:notEmpty>

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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
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
  
  <body bgcolor="#eeeeee" class="listBody">
    <html:form action="/callcenter/orderMenu" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">ҵ�����</td>
<%--		    <td class="tdbgpiclist">�����Ŷ�</td>--%>
<%--		    <td class="tdbgpiclist">��ʼʱ��</td>--%>
<%--		    <td class="tdbgpiclist">����ʱ��</td>--%>
		    <td class="listTitleStyle">ҵ������</td>
<%--		    <td class="tdbgpiclist"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isAvailable"/></td>--%>
			<td class="listTitleStyle">��ʼʱ��</td>
		    <td class="listTitleStyle">������Ŀ</td>
		    <td class="listTitleStyle">����</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr>
		    <td ><bean:write name="c" property="telNum" filter="true"/></td>
<%--		    <td ><bean:write name="c" property="callinNumEnd" filter="true"/></td>--%>
<%--		    <td ><bean:write name="c" property="beginTime" format="HH:mm" filter="true"/>--%>
<%--		    <logic:equal name="c" property="beginTime" value="">������</logic:equal>--%>
<%--		    </td>--%>
<%--		    <td ><bean:write name="c" property="endTime" format="HH:mm" filter="true"/>--%>
<%--		    <logic:equal name="c" property="endTime" value="">������</logic:equal></td>--%>
		    <td >
			    <logic:equal name="c" property="orderType" value="dingzhi">����</logic:equal>
            </td>
<%--		    <td width="10%">--%>
<%--		    <logic:equal name="c" property="isAvailable" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.able"/></logic:equal>--%>
<%--            <logic:notEqual name="c" property="isAvailable" value="Y"><bean:message bundle="pccye" key="et.pcc.callinFirewall.disable"/></logic:notEqual>--%>
<%--		    </td>--%>
			<td ><bean:write name="c" property="beginTime" filter="true"/></td>
		   	<td ><bean:write name="c" property="ivrInfo" filter="true"/></td>
		   	<td >
		   		<img alt="�˶�" src="../../style/<%=styleLocation %>/images/del.png"
		    	onclick="popUp('windows','orderMenu.do?method=toDelOrderMenu&id=<bean:write name='c' property='id'/>',680,300)" width="16" height="16" border="0"/>
		   	</td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5" class="pageTable">
				<page:page name="firewallpageTurning" style="second"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
h