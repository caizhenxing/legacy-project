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
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>操作</title>
<script type='text/javascript' src='../js/msg.js'></script>
<script type="text/javascript">
	// 查询订制记录
	function tijiao(){
		var stime = document.forms[0].sendTime.value;
		if(stime == "") {
   			alert("请选择时间！");
   			return false;
   		}
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoSet";
   		document.forms[0].target = "contents";
		document.forms[0].submit();
		window.close();
	}
	//添加
   	function add(){
   		var tel = document.forms[0].telNum.value;
   		var otype = document.forms[0].orderType.value;
   		var begindate = document.forms[0].beginDate.value;
   		var begintime = document.forms[0].beginTime.value;
   		if(tel == "") {
   			alert("业务号码不可以为空！");
   			return false;
   		}
   		if(otype == "") {
   			alert("业务类型必须选择！");
   			return false;
   		}
   		if(otype == "dingzhi" && begintime == "") {
   			alert("订制业务必须选择具体时间");
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
<html:form action="/sms/columnInfo" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
     当前位置&ndash;&gt;设定栏目信息发送时间
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
     <td class="labelStyle">
     	栏目信息
     </td>
     <td class="valueStyle">
       <html:text property="columnInfo" styleClass="writeTextStyle"/>
     </td>
    
    <td class="labelStyle">
     	设定时间
     </td>
    <td class="valueStyle">
     <html:text property="sendDate" styleClass="writeTextStyle"/>
     <html:hidden property="nickname" styleClass="writeTextStyle"/>
     <img alt="选择时间" src="../html/img/cal.gif"
			onclick="openCal('columnInfoBean','sendDate',false);">
	 <html:text property="sendTime" maxlength="10" size="10"  styleClass="input" readonly="true"/>
       		<input type="button"   value="选择时间" onclick="OpenTime(document.all.sendTime);"/>
     </td>
   </tr>
  
  <tr class="buttonAreaStyle">
    <td colspan="4" align="right">
      <input type="button" name="Submit4" value="提 交" onClick="tijiao()"  >
    </td>
  </tr>
</table>
</html:form>
</body>
</html>
