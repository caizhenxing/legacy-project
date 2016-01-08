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
<script language="javascript" src="../../js/common.js"></script>
<SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>
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
 		document.forms[0].action="../staffHortation.do?method=toStaffHortationList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
 </script>
  </head>
  
  <body>
	<html:form action="/sys/staff/staffHortation" method="post">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    当前位置&ndash;&gt;奖罚信息
		    </td>
		  </tr>
		</table>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td class="tdbgcolorloadright">
    				奖罚类型
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:select property="hortationType">
	    				<html:option value="">
	    				请选择
	    				</html:option>
	    				<html:option value="award">
	    				奖励
	    				</html:option>
	    				<html:option value="fine">
	    				罚款
	    				</html:option>
	    			</html:select>
    			</td>
    			<td class="tdbgcolorloadright">
    				奖罚时间
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="hortationTime" styleClass="input" onfocus="calendar()" />
    			</td>
    			
    		</tr>
    		<tr>
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="查询"  class="button" onclick="query()"/>
    				<input type="button" name="btnadd" value="添加" onclick="popUp('staffHortationWindows','staffHortation.do?method=toStaffHortationLoad&type=insert',650,200)"/>
    				<input type="reset" value="刷新"  class="button"/>
    			</td>
 
    		</tr>
    	</table>
		
	<br>
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist">奖罚类型</td>
		    <td class="tdbgpiclist">奖罚时间</td>
		    <td class="tdbgpiclist">奖罚内容</td>
		    <td class="tdbgpiclist" width="15%">操作</td>
		    
		  </tr>
		  	  <logic:iterate id="c" name="list" indexId="i">
		 	<%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  	  
		  <tr>

		   
		    <td ><bean:write name="c" property="hortationType" filter="true"/></td>
		    <td ><bean:write name="c" property="hortationTime" filter="true"/></td>
		    <td ><bean:write name="c" property="hortationInfo" filter="true"/></td>

		    
            <td width="15%" >
            <img alt="详细" src="../../../images/sysoper/particular.gif" onclick="popUp('staffHortationWindows','staffHortation.do?method=toStaffHortationLoad&type=detail&id=<bean:write name='c' property='id'/>',650,200)" width="16" height="16" border="0"/>
            <img alt="修改" src="../../../images/sysoper/update.gif" onclick="popUp('staffHortationWindows','staffHortation.do?method=toStaffHortationLoad&type=update&id=<bean:write name='c' property='id'/>',650,200)" width="16" height="16" border="0"/>
		    <img alt="删除" src="../../../images/sysoper/del.gif" onclick="popUp('staffHortationWindows','staffHortation.do?method=toStaffHortationLoad&type=delete&id=<bean:write name='c' property='id'/>',650,200)" width="16" height="16"  border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="StaffHortationpageTurning" style="second"/>
		    </td>
		  </tr>
	</table>
		  
	</html:form>
  </body>
</html:html>
u