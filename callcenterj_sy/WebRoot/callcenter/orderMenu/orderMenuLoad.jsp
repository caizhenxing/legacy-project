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
		opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
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
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>操作</title>
<script type='text/javascript' src='../js/msg.js'></script>
<script type="text/javascript">
	// 查询订制记录
	function query(){
		var tel = document.forms[0].telNum.value;
		if(tel == "") {
   			alert("查询时必须输入用户号码！");
   			return false;
   		}
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuList";
   		document.forms[0].target="bottomm";
<%--   		document.forms[0].target = "_blank";--%>
		document.forms[0].submit();
	}
	//添加
   	function add(){
   		var tel = document.forms[0].telNum.value;
   		var otype = document.forms[0].orderType.value;
   		var begindate = document.forms[0].beginDate.value;
   		var begintime = document.forms[0].beginTime.value;
   		var ivrInfo = document.forms[0].ivrInfo.value;
   		if(tel == "") {
   			alert("用户号码不可以为空！");
   			return false;
   		}
   		if(otype == "") {
   			alert("订制类型必须选择！");
   			return false;
   		}
   		if(otype == "dingzhi" && begintime == "") {
   			alert("定制业务必须选择具体时间");
   			return false;
   		}
   		if(otype == "dianbo" && (begindate == ""||begintime == "")) {
   			alert("点播业务必须选择日期时间");
   			return false;
   		}
   		
   		if(ivrInfo == "") {
   			alert("订制栏目不可以为空！");
   			return false;
   		}
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuOper";
		document.forms[0].submit();
   	}
   	//修改
   	function update(){
   		document.forms[0].action = url + "update";
		document.forms[0].submit();
   	}
   	//删除
   	function del(){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
   	
   	function change(){
   		if(document.forms[0].orderType.value == "dianbo") {
   			document.all.Submit.value = "点 播";
   			document.getElementById("div1").style.display = 'block';
   		}
   		if(document.forms[0].orderType.value == "dingzhi") {
   			
   			document.getElementById("div1").style.display = 'none';
<%--   			document.getElementById("div2").style.display = 'block';--%>
   			document.all.Submit.value = "定 制";
   		}
   		if(document.forms[0].orderType.value == "") {
   			document.all.Submit.value = "添 加";
   		}
   	}
	
</script>
</head>

<body class="loadBody">
<%--<html:form action="/callcenter/orderMenu" method="post">--%>
<%--<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">--%>
<%--  <tr>--%>
<%--    <td class="navigateStyle">--%>
<%--    当前位置&ndash;&gt;语音信息定制--%>
<%--    </td>--%>
<%--  </tr>--%>
<%--</table>--%>
<%--<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">--%>
<%--  <tr>--%>
<%--    <td class="labelStyle">用户号码</td>--%>
<%--    <td class="valueStyle" colspan="2">--%>
<%--      <html:text property="telNum" styleClass="input" styleId="telNum"/>&nbsp;<font color="red">*</font>--%>
<%--      <html:hidden property="id"/></td>--%>
<%--    <td class="labelStyle">订制类型</td>--%>
<%--    <td class="valueStyle">--%>
<%--      <html:select property="orderType" styleClass="selectStyle" onchange="change()" style="width:155px" styleId="orderType">		--%>
<%--	     <html:option value="" >请选择</html:option>--%>
<%--	     <html:option value="dianbo" >点播</html:option>--%>
<%--	     <html:option value="dingzhi" >定制</html:option>--%>
<%--	  </html:select>&nbsp;<font color="red">*</font>--%>
<%--    </td>--%>
<%--   </tr>--%>
<%--   <tr>--%>
<%--     <td class="labelStyle">--%>
<%--		发送时间--%>
<%--     </td>--%>
<%--     <td class="valueStyle" width="180">	--%>
<%--     <div id='div1'>--%>
<%--     <html:text property="beginDate" styleClass="writeTextStyle"/>--%>
<%--     <img alt="选择时间" src="../html/img/cal.gif"--%>
<%--			onclick="openCal('orderMenuBean','beginDate',false);">&nbsp;<font color="red">*</font></div>--%>
<%--	</td>--%>
<%--	 <td class="valueStyle">--%>
<%--	 <html:text property="beginTime" maxlength="10" size="10"  styleClass="input" readonly="true" styleId="beginTime"/>&nbsp;<font color="red">*</font>--%>
<%--       		<input type="button"   value="选择时间" onclick="OpenTime(document.all.beginTime);"/>--%>
<%--       		</td>--%>
<%--    --%>
<%--     <td class="labelStyle">--%>
<%--     	订制栏目--%>
<%--     </td>--%>
<%--     <td class="valueStyle">--%>
<%--      <html:select property="ivrInfo" styleClass="input" styleId="ivrInfo">--%>
<%--      	<html:option value="">请选择栏目名称</html:option>--%>
<%--        <html:options collection="ivrlist" property="value" labelProperty="label"/>--%>
<%--      </html:select>&nbsp;<font color="red">*</font>--%>
<%--     </td>--%>
<%--   </tr>--%>
<%--  --%>
<%--  <tr class="buttonAreaStyle">--%>
<%--    <td colspan="5" align="right">--%>
<%--      <input type="button" name="Submit4" value="查 询" onClick="query()"  class="buttonStyle"/>--%>
<%--      <input type="button" name="Submit" id="Submit" value="添 加" onClick="add()"  class="buttonStyle"/>--%>
<%--      <input type="reset" name="Submit2" value="重 置"  class="buttonStyle"/>--%>
<%--      <input type="button" name="Submit3" value="关 闭" onClick="javascript:window.close()"  class="buttonStyle" style="display:none"/>--%>
<%--      </td>--%>
<%--    </tr>--%>
<%--</table>--%>
<%--</html:form>--%>
</body>
</html>
