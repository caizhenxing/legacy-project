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
    		document.forms[0].action = "../userInfo.do?method=toCustinfoList";
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
		function execCallOut(callOutId)
		{
			//外线电话号
			var callOut = document.getElementById(callOutId).value;
			if(callOut == 0)
			{
				alert('外线号为空禁止外呼!');
				return;
			}
			
			parent.opener.document.getElementById('txtOutLine').value=callOut;
			parent.opener.document.getElementById('execBtnOutCall').click();
			parent.window.close();
		}
		//文档onload时刷新content页面
		function bodyOnload()
   		{
   			document.getElementById('btnQuery').click();
   		}
   	</script>
<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
  </head>
  
  <body class="conditionBody" onload="bodyOnload()">
    <html:form action="/callcenter/userInfo" method="post">
      	<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;客户电话查询
					</td>
				</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
<%--		  <tr>--%>
<%--		    <td class="labelStyle" width="80px">--%>
<%--		    外线号码--%>
<%--		    </td>--%>
<%--		    <td class="valueStyle" align="left">--%>
<%--		    <input type="text" class="writeTextStyle" id="txtCallOutId" size="10" />--%>
<%--		    </td>--%>
<%--		    <td colspan="7" align="left" class="labelStyle">--%>
<%--		    <input class="buttonStyle" type="button" value="呼叫" onclick="execCallOut('txtCallOutId')"/>--%>
<%--		    <input name="btnAdd" type="button"   value="添加" onclick="popUp('windows','custinfo.do?method=toCustinfoLoad&type=insert',560,320)"/>--%>
<%--		    <html:reset value="刷新" styleClass="buttonStyle"/>--%>
<%--		    </td>--%>
<%--		  </tr>--%>
		  <tr>
		    <td class="labelStyle">姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>
		    <td class="valueStyle"><html:text property="cust_name" size="10" styleClass="writeTextStyle"/></td>
		    <td class="labelStyle">电话类型</td>
		    <td class="valueStyle">
		    	<html:select property="tel_type" styleClass="selectStyle">
		    		  <html:option value="mobile">手机</html:option>
		    		  <html:option value="workphone">办公电话</html:option>
		    		  <html:option value="homephone">宅电</html:option>
			    </html:select>
			</td>
		    <td class="labelStyle">客户行业</td>
		   	<td class="valueStyle"><html:text property="cust_voc" size="10" styleClass="writeTextStyle"/></td>
		    <td class="labelStyle">客户类型</td>
		    <td class="valueStyle">
<%--		    		<html:select property="cust_type" styleClass="selectStyle">--%>
<%--		    		  <html:option value="">全部</html:option>--%>
<%--			          <html:options collection="typeList" property="value" labelProperty="label"/>--%>
<%--			        </html:select>--%>
<select name="cust_type" onchange="selectType()" class="writeTypeStyle"><option value="" selected="selected">请选择</option>
							<option value="SYS_TREE_0000002102" class="writeTypeStyle">├座席员</option>
<option value="SYS_TREE_0000002103" class="writeTypeStyle">├专家</option>
<option value="SYS_TREE_0000002104" class="writeTypeStyle">├企业</option>
<option value="SYS_TREE_0000002105" class="writeTypeStyle">├媒体</option>
<option value="SYS_TREE_0000002106" class="writeTypeStyle">├政府</option>
<option value="SYS_TREE_0000002108" class="writeTypeStyle">├联络员</option>
<option value="SYS_TREE_0000002109" class="writeTypeStyle">├普通用户</option></select>
		    </td>
		    <td class="labelStyle" align="right" width="118px">
		    	<input name="btnSearch" type="button" id="btnQuery"   value="查询" onclick="query()" class="buttonStyle"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>