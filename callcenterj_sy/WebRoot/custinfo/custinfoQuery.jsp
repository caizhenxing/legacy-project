<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>
 
<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />	
	 <SCRIPT language=javascript src="./../js/form.js" type=text/javascript></SCRIPT>
 <SCRIPT language=javascript src="./../js/calendar3.js" type=text/javascript></SCRIPT>
<script language="javascript" src="./../js/common.js"></script>
<script language="javascript" src="./../js/clock.js"></script>
	
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "custinfo.do?method=toCustinfoList";
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
	<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
</head>

<body class="conditionBody">
	<html:form action="custinfo/custinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;普通用户管理
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="queryLabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"readonly="true" />
					<img alt="选择时间" src="./../html/img/cal.gif"
						onclick="openCal('custinfo','beginTime',false);">
				</td>
				<td class="queryLabelStyle">
					用户姓名
				</td>
				<td class="valueStyle">
					<html:text property="cust_name" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					用户行业
				</td>
				<td class="valueStyle">
					<select name="cust_voc" class="selectStyle" >
					  <option value="">全部</option>
					  <option value="普通农户">普通农户</option>
			          <option value="种植大户">种植大户</option>
			          <option value="养殖大户">养殖大户</option>
			          <option value="加工大户">加工大户</option>
			          <option value="农村经济人">农村经济人</option>
			          <option value="农资经销商">农资经销商</option>
			        </select>
				</td>		
				<td class="queryLabelStyle">
					地址
				</td>
				<td class="valueStyle">
					<html:text property="cust_addr" size="25" styleClass="writeTextStyle" styleId="cust_addr"/>
			<input type="button" value="选择地址" onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>		
			</tr>
			<tr>
				
				<td class="queryLabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" readonly="true"  />
					<img alt="选择时间" src="./../html/img/cal.gif"
						onclick="openCal('custinfo','endTime',false);">
				</td>
				<td class="queryLabelStyle">
					用户电话
				</td>
				<td class="valueStyle">
					<html:text property="cust_tel_home" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
						受理工号
				</td>
				<td class="valueStyle">
						<html:select property="cust_rid" styleClass="writeTextStyle">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle" colspan="2">
					<input name="btnSearch" type="button" value="查询" onclick="query()"  class="buttonStyle"/>
					<input type="reset" value="刷新"   onClick="parent.bottomm.document.location=parent.bottomm.document.location;"  class="buttonStyle"/>
<%--					<leafRight:btn name="btnSearch" nickName="custinfo_query" styleClass="buttonStyle" value="查询" onclick="query()" scopeName="userRoleLeafRightInsession" />--%>
<%--					<leafRight:btn type="reset" nickName="custinfo_reset" styleClass="buttonStyle" value="重置" onclick="parent.bottomm.document.location=parent.bottomm.document.location;" scopeName="userRoleLeafRightInsession" />--%>
				</td>
				
			</tr>

		</table>
	</html:form>
</body>
</html:html>
