<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>预约医疗服务信息库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>

	<script language="javascript" src="../../js/clock.js"></script>
	
	<script type='text/javascript'src='/callcenterj_sy/dwr/interface/expertService.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	<script type="text/javascript">
		 // 根据专家类别,获得专家
    	function getExpert_dwr(){
    	//alert("1");
    		//var obj_Pro = document.getElementById("sfd");
    		var obj_expertName = document.getElementById("expertName");
    		//var pro_Index = obj_Pro.selectedIndex;
    		//var pro_Value = obj_Pro.options[pro_Index].value;
    		var pro_Value = document.getElementById('billNum').value;
    		if(pro_Value != "" && pro_Value != null){
    		//alert("2:"+pro_Value);
    			expertService.getExpert(pro_Value,expertReturn);
    		}else{
    			DWRUtil.removeAllOptions(obj_expertName);    			
				DWRUtil.addOptions(obj_expertName,{'':'选择专家'});
    		}
    	}
    // 回调函数
    	function expertReturn(data){
    		var obj_expertName = document.getElementById("expertName");    		
    		DWRUtil.removeAllOptions(obj_expertName);
			DWRUtil.addOptions(obj_expertName,{'':'选择专家'});
			DWRUtil.addOptions(obj_expertName,data);
    	}
	</script>
	<script type="text/javascript">
 function add()
 	{
 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 </script>

</head>

<body class="conditionBody">
	<html:form action="/medical/bookMedicinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;预约医疗服务信息库
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class=" conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="12"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:text property="contents" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					就诊类型
				</td>
				<td class="valueStyle">
					<select name="dictServiceType" class="selectStyle">
						<option value="">全部</option>
						<option value="SYS_TREE_0000000714">├就诊预约</option>
						<option value="SYS_TREE_0000000715">├住院预约</option>
					</select>
				</td>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<html:select property="bookRid" style="width:70px;">
						<option value="">请选择</option>
						<logic:iterate id="u" name="userList">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle" align="center" colspan="2">
					<input type="button" name="btnSearch" value="查询"
						class="buttonStyle" onclick="query()" size="10"/>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="12"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					受理专家
				</td>
				<td class="valueStyle">
					<html:select styleId="billNum" property="billNum" styleClass="writeTextStyle" onchange="getExpert_dwr()" style="text-indent: 5px;">
						<html:option value="">选择专家类别</html:option>
						<html:options collection="expertList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName" styleClass="writeTextStyle" style="text-indent: 5px;">
						<html:option value="">选择专家</html:option>
					</html:select>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle">
					<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option selected="selected">待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option selected="selected">驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("pass".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option selected="selected">已审</option>
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option selected="selected">发布</option>
					<%
					}else{
					%>
					<option value="" selected="selected">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}
					 %>
<%--						<option value="">全部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>--%>
<%--						<option>原始</option>--%>
<%--						<option>待审</option>--%>
<%--						<option>驳回</option>--%>
<%--						<option>已审</option>--%>
<%--						<option>发布</option>--%>
					</select>
				</td>
				<td class="labelStyle">
				
				</td>
				<td class="valueStyle">
					
				</td>
				<td class="labelStyle" align="center" colspan="2">
					<input type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" size="10"/>
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