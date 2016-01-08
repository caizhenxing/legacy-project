<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
<%--    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />--%>
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
	</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
	<script language="javascript" src="../../js/clock.js"></script>
	<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
	</SCRIPT>
	
    <script language="javascript">
    
    	//下方打开ivr信息的页面
    	function ivrsearch()
    	{
    		document.forms[0].action = "../../callcenter/cclog.do?method=toIVRInfo";
    		document.forms[0].target="down";
    		document.forms[0].submit();
    	}
    	
    	//下方打开咨询问题的页面
    	function questionsearch()
    	{
    		document.forms[0].action = "../../callcenter/cclog.do?method=toQuesInfo";
    		document.forms[0].target="down";
    		document.forms[0].submit();
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		
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
	
	function pageclose()
	{
<%--		opener.parent.topp.document.all.btnSearch.click();--%>
		window.close();
	}
	
		//返回页面
		function toback(){
<%--			opener.parent.topp.document.all.btnSearch.click();--%>
		}
		//添加信息
<%--		function addInfo(){--%>
<%--			document.forms[0].action = "";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody">
  
    <html:form action="/callcenter/cclog.do" method="post">
    
    <html:hidden property="id"/>

		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		  	<td colspan="6" class="navigateStyle">
		  		咨询内容详细信息
		  	</td>
		  </tr>
		
<%--		  <tr>--%>
<%--		   <table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">--%>
		    <tr>
<%--		     <td  width="20%">工单编号</td>--%>
<%--		   	 <td width="25%" ><bean:write name="c" property="billNum" filter="false"/></td>--%>
<%--		   	 <td class="labelStyle" width="15%">操作</td>--%>
<%--		   	 <td class="labelStyle" width="25%">--%>
<%--		      <img alt="<bean:message bundle="pcc" key='sys.update'/>" src="../../images/sysoper/update.gif" onclick="popUp('windowsme','cclog.do?method=toAddLoad&type=update&id=<bean:write name='c' property='talkid' filter='false'/>',680,400)" width="16" height="16" target="windows" border="0"/>    --%>
<%--		      <img alt="回访成功人添加" src="../../images/sysoper/chenggong.jpg" onclick="popUp('windowsname1','cclog.do?method=toAnswerManLoad&id=<bean:write name='c' property='talkid'/>&phonenum=<bean:write name='c' property='telNum'/>',480.50)" width="16" height="16" border="0"/>--%>
<%--		     </td>--%>
		     <td class="labelStyle">问题</td>
		     <td class="labelStyle"><html:text property="question" styleClass="writeTextStyle"/></td>
		     <td class="labelStyle">提&nbsp;问&nbsp;人</td>
		     <td class="labelStyle"><html:text property="userName" styleClass="writeTextStyle"/></td>
		     <td class="labelStyle">问题状态</td>
		     <td class="labelStyle"><html:text property="quinfo" styleClass="writeTextStyle"/></td>
		   	</tr>
		   	  
		   	<tr>
		   	  <td class="labelStyle">是否回访</td>
		   	  <td class="labelStyle"><html:text property="ifNeedRevert" styleClass="writeTextStyle"/></td>
		   	  <td class="labelStyle">回&nbsp;答&nbsp;人</td>
		   	  <td class="labelStyle"><html:text property="answerMan" styleClass="writeTextStyle"/></td>
		   	  <td class="labelStyle">提问时间</td>
		   	  <td class="labelStyle"><html:text property="questionTime" styleClass="writeTextStyle"/></td>		
		   	</tr> 
		   	<tr>
		   	  <td class="labelStyle">回答内容</td>
		   	  <td class="labelStyle" colspan="5"> 
		   	  	<html:textarea property="answer" styleClass="writeTextStyle" cols="100" rows="3" />
		   	  </td>
		   	</tr>
		   	
		    <!-- 修改功能 -->	   
		    </table>
<%--		    <table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">--%>
<%--		    <tr></tr>--%>
<%--		    <tr></tr>--%>
<%--		    </table>--%>
<%--		  </tr>--%>
<%--		 </table>--%>
		  <table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="navigateTable" >
		  <tr>
		    <td colspan="4" align="center" class="buttonAreaStyle">
		    	<input name="addgov" type="button"   value="关闭" onClick="pageclose()"/>
		    </td>
		  </tr>
		  
		</table>
		
    </html:form>
  </body>
</html:html>
 