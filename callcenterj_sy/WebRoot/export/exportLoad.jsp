<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>专家管理</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<style type="text/css">
<!--
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<SCRIPT language=javascript src="../js/calendar.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/common.js"></script>
	<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language="javascript" src="../js/form.js" type=text/javascript></SCRIPT>
	<link REL=stylesheet href="../markanainfo/css/divtext.css" type="text/css" />
	<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>

 <!-- jquery验证 -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
	<script type="text/javascript">	
	
	//初始化
		function init(){
			<logic:equal name="opertype" value="detail">
	    		document.getElementById('buttonSubmit').style.display="none";
	    	</logic:equal>
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "export.do?method=toExportOper&type=insert";
				document.getElementById('tdHead').innerHTML="添加信息";
				document.getElementById('buttonSubmit').value="添加";		    		
	    	</logic:equal>
			
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "export.do?method=toExportOper&type=update";
				document.getElementById('tdHead').innerHTML="修改信息";
				document.getElementById('buttonSubmit').value="修改";
	    		
	    	</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "export.do?method=toExportOper&type=delete";
				document.getElementById('tdHead').innerHTML="删除信息";
				document.getElementById('buttonSubmit').value="删除";		    		
	    	</logic:equal>
			
		}
		//执行验证
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"export",onerror:function(msg){alert(msg)}});
			$("#name").formValidator({onshow:"请输入专家姓名",onfocus:"专家姓名不能为空",oncorrect:"专家姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"专家姓名两边不能有空符号"},onerror:"专家姓名不能为空"});
			$("#remark").formValidator({onshow:"请输入专家简介",onfocus:"专家简介不能为空",oncorrect:"专家简介合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"专家简介两边不能有空符号"},onerror:"专家简介不能为空"});
		})
    	</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"export",onerror:function(msg){alert(msg)}});
			$("#name").formValidator({onshow:"请输入专家姓名",onfocus:"专家姓名不能为空",oncorrect:"专家姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"专家姓名两边不能有空符号"},onerror:"专家姓名不能为空"});
			$("#remark").formValidator({onshow:"请输入专家简介",onfocus:"专家简介不能为空",oncorrect:"专家简介合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"专家简介两边不能有空符号"},onerror:"专家简介不能为空"});
		})
    	</logic:equal>
    	
    	
<%--		function openwin(param)--%>
<%--		{--%>
<%--		   var aResult = showCalDialog(param);--%>
<%--		   if (aResult != null)--%>
<%--		   {--%>
<%--		     param.value  = aResult;--%>
<%--		   }--%>
<%--		}--%>
<%--		--%>
<%--		function showCalDialog(param)--%>
<%--		{--%>
<%--		   var url="<%=request.getContextPath()%>/html/calendar.html";--%>
<%--		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");--%>
<%--		   if (aRetVal != null)--%>
<%--		   {--%>
<%--		      return aRetVal;--%>
<%--		   }--%>
<%--		   return null;--%>
<%--		}--%>
			
<%--	function comptime(beginTime,endTime)--%>
<%--    {--%>
<%----%>
<%--			var beginTimes=beginTime.substring(0,10).split('-');--%>
<%--			var endTimes=endTime.substring(0,10).split('-');--%>
<%--			--%>
<%--			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);--%>
<%--			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);--%>
<%--			 --%>
<%--			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;--%>
<%--			--%>
<%--			if(a<0)--%>
<%--			{--%>
<%--				return -1;--%>
<%--			}--%>
<%--			else if (a>0)--%>
<%--				{--%>
<%--				return 1;--%>
<%--				}--%>
<%--			else if (a==0)--%>
<%--			{--%>
<%--				return 0;--%>
<%--			}--%>
<%--			else--%>
<%--			{--%>
<%--				return 'exception'--%>
<%--			}--%>
<%--	}--%>
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.name,"专家姓名")) return false; 
        	if (!checkNotNull(addstaffer.remark,"专家简介")) return false;
           return true;
        }
<%--		function add()--%>
<%--		{--%>
<%--		    var f =document.forms[0];--%>
<%--  	    		if(checkForm(f)){--%>
<%--	 		document.forms[0].action="export.do?method=toExportOper&type=insert";--%>
<%--	 		--%>
<%--	 		document.forms[0].submit();--%>
<%--	 		}--%>
<%--		}--%>
<%--		function update()--%>
<%--		{--%>
<%--			var f =document.forms[0];--%>
<%--  	    		if(checkForm(f)){--%>
<%--	 		document.forms[0].action="export.do?method=toExportOper&type=update";--%>
<%--	 	--%>
<%--	 		document.forms[0].submit();--%>
<%--	 		}--%>
<%--		}--%>
<%--		function del()--%>
<%--		{--%>
<%--	 		document.forms[0].action="export.do?method=toExportOper&type=delete";--%>
<%--	 		--%>
<%--	 		document.forms[0].submit();--%>
<%--		}--%>
				
		function toback(){
			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
</script>

</head>

<body onunload="toback()" class="listBody" onload="init();">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="export/export.do" method="post" styleId="export">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		添加信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		查看信息
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		修改信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		删除信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle" width="80">
						专家姓名
					</td>
					<td colspan="2" class="valueStyle">						
						<html:text property="name" style="width:200px" styleId="name"></html:text>
						<div id="nameTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="80">
						专家照片						
					</td>
					<td colspan="2" class="valueStyle">
						<logic:equal name="opertype" value="insert">
							<html:file property="photo" styleClass="writeTextStyle"></html:file>
<%--							<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true" ></iframe>--%>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
							<img src="export.do?method=toExportPhoto&&id=${id}" width="202" height="266"/>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<img src="export.do?method=toExportPhoto&&id=${id}" width="202" height="266"/>
							<html:file property="photo" styleClass="writeTextStyle"></html:file>
<%--							<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true" ></iframe>--%>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
							<img src="export.do?method=toExportPhoto&&id=${id}" width="202" height="266"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="80">
						专家简介
						
					</td>
					<td colspan="2" class="valueStyle">
						<html:textarea property="remark" cols="56" rows="5" styleClass="writeTextStyle" styleId="remark"></html:textarea>
						<div id="remarkTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
			
			<tr>
				<td colspan="9" align="center" class="buttonAreaStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="添加" onclick="add()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate" value="确定" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete" value="删除" onclick="del()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
					<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
					<input type="button" value="关闭" onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
		</table>
		<html:hidden property="id" />
		</html:form>
</body>
</html:html>