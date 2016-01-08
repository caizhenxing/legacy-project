<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>热线专家团信息管理</title>

	<link href="./../../style/<%=styleLocation%>/style.css"
		rel="stylesheet" type="text/css" />
	<SCRIPT language=javascript src="../../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
<%--	<script language="javascript" src="../../js/common.js"></script>--%>
<%--	<script language="javascript" src="../../js/clock.js"></script>--%>
	<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>
	<link REL=stylesheet href="../../markanainfo/css/divtext.css"
		type="text/css" />
<%--	<script language="JavaScript" src="../../markanainfo/js/divtext.js"></script>--%>

	<script language="JavaScript" type="text/javascript">
<%--	function add(){--%>
<%--		if(checkForm()){--%>
<%--		  document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=insert";--%>
<%--		  document.forms[0].submit();--%>
<%--		}--%>
<%--		--%>
<%--	}--%>
<%--	function update(){--%>
<%--	  if(checkForm()){--%>
<%--		document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=update";--%>
<%--		document.forms[0].submit();--%>
<%--	  }--%>
<%--	}--%>
<%--	function del(){--%>
<%--		document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=delete";--%>
<%--		document.forms[0].submit();--%>
<%--	}--%>
	function toback()
	{
		//opener.parent.topp.document.all.btnSearch.click();
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
	}
	function initPhoto()
	{
		document.getElementById('ehExpertPic').value=window.frames["iframeUpload"].document.getElementById('filePathName').value;
	}
	function openCalendar(formName,targetName){
		var cal=new calendar3(document.forms[formName].elements[targetName],window.event);
		cal.year_scroll = true;
		cal.time_comp = false;
		cal.popup();
	}
		function checkForm(userAccuseBean){
        	if (!checkNotNull(expertGroupHLBean.ehCallName,"专家称呼")) return false; 
        	if (!checkNotNull(expertGroupHLBean.ehExpertZone,"个人领域")) return false;
        	if (!checkNotNull(expertGroupHLBean.ehExpertSummary,"专家简介")) return false;
        	if (!checkNotNull(expertGroupHLBean.ehType,"专家类型")) return false;

           return true;
        }
     function add()
	{
	initPhoto();
	   var f =document.forms[0];
      if(checkForm(f)&&isNumRangOk()){
     document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=insert";
	 document.forms[0].submit();
	 }
	}
	function update()
	{
	initPhoto();
	  var f =document.forms[0];
     if(checkForm(f)&&isNumRangOk()){
	 document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=update";
	 document.forms[0].submit();
	 }
	}
	function del()
	{
		if(confirm("要继续删除操作请单击--确定, 取消操作请单击--取消")){
		  document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=delete";
		  document.forms[0].submit();
		}
	}
	function isNumRangOk()
	{
		var i = document.getElementById("ehAgreeLevel").value;
		if(i>0&&i<=100)
		{
			return true;
		}
		alert("支持率必须是0～100的数字");
		return false;
	}
	
<%--	function checknull()--%>
<%--	{--%>
<%--		var val = document.getElementById("ehType").value;--%>
<%--		if(val=="")--%>
<%--		{--%>
<%--			alert("请选择类型！");--%>
<%--			return；--%>
<%--		}--%>
<%--	}--%>
	
</script>

</head>
<body onunload="toback()" class="loadBody">

	<logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
    </script>
	</logic:notEmpty>
	<html:form action="/screen/expertGroupHL.do" method="post">
		<html:hidden property="ehId"/>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;热线专家团信息管理
				<br></td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					专家称呼
				<br>
				</td>
				<logic:equal value="insert" name="opertype">
				<td class="valueStyle">
					<html:text property="ehCallName" styleClass="writeTextStyle"
						size="30" />&nbsp;<font color="red">*</font>
				<br>
				</td>
				</logic:equal>
				<logic:notEqual value="insert" name="opertype">
				<td class="valueStyle">
					<html:text property="ehCallName" styleClass="writeTextStyle"
						size="30" readonly="true"/>&nbsp;<font color="red">*</font>
				<br>
				</td>
				</logic:notEqual>
			</tr>
			<!-- ######begin -->
			
			<tr>
					<td class="labelStyle">分析师照片</td>
					<td class="valueStyle" >
						<logic:equal name="opertype" value="insert">
						    <input type="hidden" name="ehExpertPic" id="ehExpertPic" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle"/>--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../../fileUpload/fileUpload.jsp" allowTransparency="true" ></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="hidden" name="ehExpertPic" id="ehExpertPic" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle" value="<%=request.getAttribute("photoPath").toString() %>">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle" />--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
						<html:text property="ehExpertPic" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
						<html:text property="ehExpertPic" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<img src="../../../<bean:write name="expertGroupHLBean" property="ehExpertPic" />" width="50" height="50" />
				    </td>
				</tr>
			<!-- ######end -->
			<tr>
				<td class="labelStyle">
					个人领域
				<br></td>
				<td class="valueStyle">
					<html:text property="ehExpertZone" styleClass="writeTextStyle" size="30" />&nbsp;<font color="red">*</font>
				<br></td>
			</tr>
			<tr>
					<td class="labelStyle">
						专家支持率
					<br></td>
					<td class="valueStyle">
						<html:text property="ehAgreeLevel" styleClass="writeTextStyle"
							size="10" />&nbsp;%&nbsp;<font color="red">*</font>
					<br></td>
				</tr>
<%--			<logic:notEqual value="insert" name="opertype">--%>
<%--				<tr>--%>
<%--					<td class="labelStyle">--%>
<%--						最后修改时间--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:text property="sssUpdateTime" styleClass="writeTextStyle"--%>
<%--							size="30"  readonly="true"/>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--			</logic:notEqual>--%>
				<tr>
					<td class="labelStyle">
						专家类型
					</td>
					<td class="valueStyle">
						<html:select property="ehType" styleClass="writeTextStyle">
						    <html:option value="">请选择类型</html:option>
							<html:option value="农民">农民</html:option>
							<html:option value="政府">政府</html:option>
						</html:select>&nbsp;<font color="red">*</font>

					</td>
				</tr>
			<tr>
				<td class="labelStyle">
					专家简介
				<br></td>
				<td class="valueStyle"><html:textarea property="ehExpertSummary" styleClass="writeTextStyle"
						rows="10" cols="80" />&nbsp;<font color="red">*</font>
				<br></td>
			</tr>
			
			<tr>
				<td class="navigateStyle" colspan="4" style="text-align:right;">
					<logic:equal value="delete" name="opertype">
						<input type="button" value="删除" onclick="del()"
							class="buttonStyle" />
					</logic:equal>
					<logic:equal value="insert" name="opertype">
						<input type="button" value="添加" onclick="add()"
							class="buttonStyle" />
					</logic:equal>
					<logic:equal value="update" name="opertype">
						<input type="button" value="修改" onclick="update()"
							class="buttonStyle" />
					</logic:equal>
					<input type="button" value="关闭" onclick="window.close()"
						class="buttonStyle" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
