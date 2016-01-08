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
    
    <title>备份设置查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
<%-- <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />--%>
<%-- <link href="../../../images/css/jingcss.css" rel="stylesheet" type="text/css" />--%>
<%-- <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>--%>
 
 <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
 </SCRIPT>
<script language="javascript" src="../../js/clockCN.js"></script>
<script language="javascript" src="../../js/clock.js"></script>

 <script type="text/javascript">

 	function addinter()
 	{
 		document.forms[0].action="../bak.do?method=tobakOperSaveLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../../bak.do?method=tobakList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
 </script>
 
  </head>
  
  <body >
    <html:form action="/bak.do" method="post">
    
	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    当前位置&ndash;&gt;数据库备份设置查询
		    </td>
		  </tr>
		</table>
    
    	<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    	
<%--    		<tr>--%>
<%--    			<td class="tdbgcolorloadright">--%>
<%--    				网址名--%>
<%--    			</td>--%>
<%--    			<td class="tdbgcolorloadleft">--%>
<%--    				<html:text property="interUsername" size="60" styleClass="input"/>--%>
<%--    			</td>--%>
<%--    			<td class="tdbgcolorloadright">--%>
<%--    				类型--%>
<%--    			</td>--%>
<%--    			<td class="tdbgcolorloadleft">--%>
<%--    				<html:select property="dictInterType">--%>
<%--    				<html:option value="">--%>
<%--    				请选择--%>
<%--    				</html:option>--%>
<%--    				<html:options collection="interType"--%>
<%--  							property="value"--%>
<%--  							labelProperty="label"/>--%>
<%--    			--%>
<%--    				</html:select>--%>
<%--    			</td>--%>
<%--    			--%>
<%--    		</tr>--%>
<tr>
	    			 <td class="tdbgcolorloadright">
	    				选择备份文件时间方式
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    				<html:select property="dbtype">		
	    				<html:option value="">请选择</html:option>
	        		<html:option value="day">日备份</html:option>
	        		<html:option value="week">周备份</html:option>
	        		<html:option value="month">月备份</html:option>
	        		<html:option value="quarter">季备份</html:option>
	        	  </html:select>
	    			</td>
	    		</tr>
	    		<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件开始日期
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<input type="text" name="begindate" value="" onfocus="calendar()" class="input">
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件结束日期
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<input type="text" name="enddate" value="" onfocus="calendar()" class="input">
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件开始时间
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<input type="text" name="begintime" maxlength="10" size="10" value="" class="input">
          		<input type="button" class="button" value="时间" onclick="OpenTime(document.all.begintime);"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件结束时间
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<input type="text" name="endtime" maxlength="10" size="10" value="" class="input">
          		<input type="button" class="button" value="时间" onclick="OpenTime(document.all.endtime);"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					备注
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="remark" size="50" styleClass="input"/>
	    				</td>
	    			</tr>
    		<tr>
    			
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="查询"  class="button" onclick="query()"/>
<%--    				<input type="button" name="btnadd" value="添加" onclick="popUp('windows','intersave.do?method=tointerSaveLoad&type=insert',650,200)"/>--%>
    				<input type="reset" value="刷新"  class="button"/>
    			</td>
    			
    		</tr>
    		
<%--    		<html:hidden property="interUsername"/>--%>
    	</table>
    </html:form>
  </body>
</html:html>
W