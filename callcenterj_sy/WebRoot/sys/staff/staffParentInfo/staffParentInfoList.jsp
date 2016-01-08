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
    
    <title>interSaveList.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%--  <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
     <script language="javascript" src="../../js/common.js"></script>
 <script type="text/javascript">
 
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
    	
    function query()
 	{
 		document.forms[0].action="../staffParent.do?method=toStaffParentInfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
 </script>
  </head>
  
  <body>
	<html:form action="/sys/staff/staffParent" method="post">
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    当前位置&ndash;&gt;员工亲属信息
		    </td>
		  </tr>
		</table>
    
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td class="tdbgcolorloadright">
    				亲属姓名
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="parentName"  styleClass="input"/>
    			</td>
    			
    			<td class="tdbgcolorloadright">
    				工作单位
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="work"  styleClass="input"/>
    			</td>
    			
    		</tr>
    		<tr>
    			<td class="tdbgcolorloadright">
    				联系电话
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="linkTel"  styleClass="input"/>
    			</td>
    			
    			<td class="tdbgcolorloadright">
    				政治面貌
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="dictParentPolity"  styleClass="input"/>
    			</td>
    			
    		</tr>
    		<tr>
    			
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="查询"  class="button" onclick="query()"/>
    				<input type="button" name="btnadd" value="添加" onclick="popUp('staffParentwindows','staffParent.do?method=toStaffParentInfoLoad&type=insert',800,260)"/>
    				<input type="reset" value="刷新">
    			</td>
    			
    		</tr>
    		
<%--    		<html:hidden property="interUsername"/>--%>
    	</table>
		
	<br>
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist">亲属姓名</td>
		    <td class="tdbgpiclist">工作单位</td>
		    <td class="tdbgpiclist">联系电话</td>
		    <td class="tdbgpiclist" width="15%">操作</td>
		    
		  </tr>
		  	  <logic:iterate id="c" name="list" indexId="i">
		 	<%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  	  
		  <tr>
            <td ><bean:write name="c" property="parentName" filter="true"/></td>
		    <td ><bean:write name="c" property="work" filter="true"/></td>
		    <td ><bean:write name="c" property="linkTel" filter="true"/></td>
            <td width="15%" >
            <img alt="详细" src="../../../images/sysoper/particular.gif" onclick="popUp('staffParentwindows','staffParent.do?method=toStaffParentInfoLoad&type=detail&id=<bean:write name='c' property='id'/>',800,260)" width="16" height="16" border="0"/>
            <img alt="修改" src="../../../images/sysoper/update.gif" onclick="popUp('staffParentwindows','staffParent.do?method=toStaffParentInfoLoad&type=update&id=<bean:write name='c' property='id'/>',800,260)" width="16" height="16" border="0"/>
		    <img alt="删除" src="../../../images/sysoper/del.gif" onclick="popUp('staffParentwindows','staffParent.do?method=toStaffParentInfoLoad&type=delete&id=<bean:write name='c' property='id'/>',800,260)" width="16" height="16"  border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="StaffParentInfopageTurning" style="second"/>
		    </td>
		  </tr>
	</table>
		  
	</html:form>
  </body>
</html:html>
 