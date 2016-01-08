<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("已经订制过该业务，不能重复订制！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("已经退订过该业务，不能重复退订！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
	</script>
	</logic:equal>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar3.js"></script>

<title>操作</title>
<script type="text/javascript">
	// 查询订制记录
	function query(){
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuList";
   		document.forms[0].target="bottomm";
		document.forms[0].submit();
	}
	
</script>
</head>

<body class="loadBody">
<html:form action="/callcenter/orderMenu" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
    当前位置&ndash;&gt;语音信息定制
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
    <td class="labelStyle">
		开始时间
     </td>
     <td class="valueStyle" width="180">	
     <html:text property="beginDate" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('orderMenuBean','beginDate',false);">
	</td>
	 <td class="labelStyle">订制类型</td>
    <td class="valueStyle">
      <html:select property="orderType"  style="width:155px" styleId="orderType">		
	     <html:option value="" >请选择</html:option>
	     <html:option value="orderProgramme" >点播</html:option>
	     <html:option value="customize" >定制</html:option>
<%--	     <html:option value="tuiding" >退订</html:option>--%>
	  </html:select>
    </td>
     <td class="labelStyle">
     	订制栏目
     </td>
     <td class="valueStyle" colspan="2">
      <html:select property="ivrInfo" styleClass="input" styleId="ivrInfo">
      	<html:option value="">请选择栏目名称</html:option>
        <html:options collection="ivrlist" property="value" labelProperty="label"/>
      </html:select>
	</tr>
   <tr>
	<td class="labelStyle">
		结束时间
     </td>
     <td class="valueStyle" width="180">	
    <html:text property="endDate" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('orderMenuBean','endDate',false);">
	</td>
	<td class="labelStyle">用户号码</td>
    <td class="valueStyle" colspan="2">
      <html:text property="telNum" styleClass="input" styleId="telNum"/>
      <html:hidden property="id"/></td>
      <td class="valueStyle" align="center">
      	<input type="button" name="btnSearch" value="查 询" onClick="query()"  class="buttonStyle"/>
     	<input type="reset" name="btnSearch2" value="刷 新" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" class="buttonStyle"/>
     </td>
   </tr>
</table>
</html:form>
</body>
</html>
