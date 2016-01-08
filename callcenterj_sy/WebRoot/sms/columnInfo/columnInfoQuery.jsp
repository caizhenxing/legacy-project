<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<logic:equal name="operSign" value="dingzhishibai">
		<script>
  		alert("已经订制过该业务，不能重复订制！");
<%--  		document.parent.topp.document.all.Submit4.click();--%>
		document.close();
  	</script>
	</logic:equal>
	<logic:equal name="operSign" value="tuidingshibai">
		<script>
  		alert("已经退订过该业务，不能重复退订！");
<%--  		document.all.Submit4.click();--%>
  	</script>
	</logic:equal>
	<logic:equal name="operSign" value="sys.common.operSuccess">
		<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
<%--		document.parent.topp.document.all.Submit4.click();--%>
<%--		document.getElementById("btnsearch").click();--%>
<%--        window.close();--%>
<%--        opener.parent.topp.document.all.btnSearch.click()--%>
		
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
		<SCRIPT language="javascript" src="../js/calendar3.js"
			type="text/javascript"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />

		<title>短信订制查询</title>
		<script type='text/javascript' src='../js/msg.js'></script>
		<script type="text/javascript">
	// 查询订制记录
	function query(){
		var tel = document.forms[0].mobileNum.value;		
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoList";
   		document.forms[0].target = "bottomm";
		document.forms[0].submit();
	}
	//添加
   	function add(){
   		var tel = document.forms[0].mobileNum.value;
   		var otype = document.forms[0].orderType.value;
   		if(tel == "") {
   			alert("手机号码不可以为空！");
   			return false;
   		}
   		if(otype == "") {
   			alert("业务类型必须选择！");
   			return false;
   		}
<%--   		popUp('windows','columnInfo.do?method=toColumnInfoOper',10,10)--%>
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoOper";
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
   		if(document.forms[0].orderType.value == "orderProgramme") {
   			document.all.Submit.value = "点 播";
<%--   			document.getElementById("div1").style.display = 'block';--%>
   		}
   		if(document.forms[0].orderType.value == "customize") {   			
   			document.all.Submit.value = "订 制";
   		}
   		if(document.forms[0].orderType.value == "") {
   			document.all.Submit.value = "订 制";
   		}
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

	<body class="loadBody">
		<html:form action="/sms/columnInfo" method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;短信订制
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="labelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('columnInfoBean','beginTime',false);">
					</td>
					<td class="labelStyle">
						手机号码
					</td>
					<td class="valueStyle">
						<html:text property="mobileNum" styleClass="input"
							style="width:155px" />
						<html:hidden property="id" />
					</td>
					<td class="labelStyle">
						订制类型
					</td>
					<td class="valueStyle">
						<html:select property="orderType" styleClass="selectStyle"
							onchange="change()">
							<html:option value="orderProgramme">点播</html:option>
							<html:option value="customize">订制</html:option>
							<%--	     <html:option value="tuiding" >退订</html:option>--%>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						结束时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle"/>
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('columnInfoBean','endTime',false);">
					</td>
					<td class="labelStyle">
						订制栏目
					</td>
					<td class="valueStyle">
						<html:select property="columnInfo">
							<%--      	<html:option value="tianqiyubao" >天气预报</html:option>--%>
							<option value="">
								请选择
							</option>
							<html:options collection="list" property="value"
								labelProperty="label" />
						</html:select>
					</td>
					<td class="labelStyle" colspan="2" align="right">
						<input type="button" id="btnsearch" name="Submit4" value="查 询" onClick="query()"
							class="buttonStyle">
						<input type="button" name="Submit" id="Submit" value="点 播"
							onClick="add()" class="buttonStyle">
						<input type="reset" name="aa" value="重 置" class="buttonStyle">
					</td>
				</tr>

				<tr class="buttonAreaStyle">
					<td colspan="7" align="right"></td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
