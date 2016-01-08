<%@ page language="java" import="java.util.*" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
  <head>
    <html:base />
    
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../../screen/expertGroupHL.do?method=toExpertGroupHLList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
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
 <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
 <SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
  </head>
  
  <body class="conditionBody">
    <html:form action="screen/expertGroupHL.do" method="post">
      	<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;专家团热线信息管理
					</td>
				</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="queryLabelStyle">专家名（称呼）</td>
		    <td class="valueStyle">
		       <html:text property="ehCallName" styleClass="writeTextStyle" size="10"/>
		    </td>
           <td class="queryLabelStyle">
           	个人领域
           </td>
           <td class="valueStyle">
          		<html:text property="ehExpertZone" styleClass="writeTextStyle" size="10"/>
           </td>
		    <td class="queryLabelStyle">专家支持率</td>
		   	<td class="valueStyle">
		   	  <html:text property="ehAgreeLevel" styleClass="writeTextStyle" size="10"/><strong>％</strong>
		   	</td>
            <td class="queryLabelStyle">专家类型</td>
		   	<td class="valueStyle">
		   	  <html:select property="ehType">
        				<html:option value="">请选择</html:option>
        				<html:option value="政府">政府</html:option>
        				<html:option value="农民">农民</html:option>
        	  </html:select>
		
		   	</td>
		   	<td class="queryLabelStyle" align="center" >
		   	<input name="btnSearch" type="button"   value="查询" onclick="query()" class="buttonStyle"/>
		    <html:reset value="刷新" styleClass="buttonStyle" onclick="parent.bottomm.document.location=parent.bottomm.document.location;" />
		   	</td>
		   	 </tr>
		   	<tr height="1px">
				<td colspan="11" class="buttonAreaStyle">

				</td>
			</tr>

		</table>
    </html:form>
  </body>
</html:html>