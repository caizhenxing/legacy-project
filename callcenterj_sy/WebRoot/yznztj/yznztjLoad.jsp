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

	<title>优质农资推介维护</title>

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
	
	function init(){
			<logic:equal name="opertype" value="detail">
	    		document.getElementById('buttonSubmit').style.display="none";
	    	</logic:equal>
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "yznztj.do?getMethod=toYznztjOper&type=insert";
				document.getElementById('tdHead').innerHTML="添加优质农资信息";
				document.getElementById('buttonSubmit').value="添加";		    		
	    	</logic:equal>
			
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "yznztj.do?getMethod=toYznztjOper&type=update";
				document.getElementById('tdHead').innerHTML="修改优质农资信息";
				document.getElementById('buttonSubmit').value="修改";
	    		
	    	</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "yznztj.do?getMethod=toYznztjOper&type=delete";
				document.getElementById('tdHead').innerHTML="删除优质农资信息";
				document.getElementById('buttonSubmit').value="删除";		    		
	    	</logic:equal>
			
		}
		//执行验证
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"yznztj",onerror:function(msg){alert(msg)}});
			$("#sort").formValidator({onshow:"请输入产品类别",onfocus:"产品类别不能为空",oncorrect:"产品类别合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品类别两边不能有空符号"},onerror:"产品类别不能为空"});
			$("#productName").formValidator({onshow:"请输入产品名称",onfocus:"产品名称不能为空",oncorrect:"产品名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品名称两边不能有空符号"},onerror:"产品名称不能为空"});
			$("#trait").formValidator({onshow:"请输入产品特性",onfocus:"产品特性不能为空",oncorrect:"产品特性合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品特性两边不能有空符号"},onerror:"产品特性不能为空"});
			$("#scope").formValidator({onshow:"请输入使用范围",onfocus:"使用范围不能为空",oncorrect:"使用范围合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"使用范围两边不能有空符号"},onerror:"使用范围不能为空"});
			$("#method").formValidator({onshow:"请输入使用方法",onfocus:"使用方法不能为空",oncorrect:"使用方法合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"使用方法两边不能有空符号"},onerror:"使用方法不能为空"});
			
		})
    	</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"yznztj",onerror:function(msg){alert(msg)}});
			$("#sort").formValidator({onshow:"请输入产品类别",onfocus:"产品类别不能为空",oncorrect:"产品类别合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品类别两边不能有空符号"},onerror:"产品类别不能为空"});
			$("#productName").formValidator({onshow:"请输入产品名称",onfocus:"产品名称不能为空",oncorrect:"产品名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品名称两边不能有空符号"},onerror:"产品名称不能为空"});
			$("#trait").formValidator({onshow:"请输入产品特性",onfocus:"产品特性不能为空",oncorrect:"产品特性合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品特性两边不能有空符号"},onerror:"产品特性不能为空"});
			$("#scope").formValidator({onshow:"请输入使用范围",onfocus:"使用范围不能为空",oncorrect:"使用范围合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"使用范围两边不能有空符号"},onerror:"使用范围不能为空"});
			$("#method").formValidator({onshow:"请输入使用方法",onfocus:"使用方法不能为空",oncorrect:"使用方法合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"使用方法两边不能有空符号"},onerror:"使用方法不能为空"});
			
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
			
<%--			--%>
<%--			--%>
<%--		function checkForm(addstaffer){--%>
<%--        	if (!checkNotNull(addstaffer.sort,"产品类别")) return false; --%>
<%--        	if (!checkNotNull(addstaffer.productName,"产品名称")) return false;--%>
<%--        	if (!checkNotNull(addstaffer.trait,"产品特性")) return false;--%>
<%--        	if (!checkNotNull(addstaffer.scope,"使用范围")) return false;--%>
<%--        	if (!checkNotNull(addstaffer.method,"使用方法")) return false;--%>
<%--           return true;--%>
<%--        }--%>
<%--		function add()--%>
<%--		{--%>
<%--		    var f =document.forms[0];--%>
<%--  	    		if(checkForm(f)){--%>
<%--	 		document.forms[0].action="yznztj.do?getMethod=toYznztjOper&type=insert";--%>
<%--	 		--%>
<%--	 		document.forms[0].submit();--%>
<%--	 		}--%>
<%--		}--%>
<%--		function update()--%>
<%--		{--%>
<%--			var f =document.forms[0];--%>
<%--  	    		if(checkForm(f)){--%>
<%--	 		document.forms[0].action="yznztj.do?getMethod=toYznztjOper&type=update";--%>
<%--	 	--%>
<%--	 		document.forms[0].submit();--%>
<%--	 		}--%>
<%--		}--%>
<%--		function del()--%>
<%--		{--%>
<%--	 		document.forms[0].action="yznztj.do?getMethod=toYznztjOper&type=delete";--%>
<%--	 		--%>
<%--	 		document.forms[0].submit();--%>
<%--		}--%>
				
		function toback()
		{

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

	<html:form action="yznztj/yznztj.do" method="post" styleId="yznztj">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		添加优质农资信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		查看优质农资信息
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		修改优质农资信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		删除优质农资信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
				
				<tr>				
					<td  class="valueStyle" rowspan="3" colspan="2" align="center">
						<logic:equal name="opertype" value="insert">
							<html:file property="photo" styleClass="writeTextStyle" style="width:150px"></html:file>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
							<img src="yznztj.do?getMethod=toYznztjPhoto&&id=${id}" width="101" height="133"/>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<p><img src="yznztj.do?getMethod=toYznztjPhoto&&id=${id}" width="101" height="133"/></p>
							<p><html:file property="photo" styleClass="writeTextStyle" style="width:150px"></html:file></p>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
							<img src="yznztj.do?getMethod=toYznztjPhoto&&id=${id}" width="101" height="133"/>
						</logic:equal>
					</td>
					<td class="labelStyle">
						产品类别
					</td>
				
					<td class="valueStyle">
						<html:text property="sort" styleClass="writeTextStyle"  styleId="sort"></html:text>
						<div id="sortTip" style="width: 150px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						产品名称
					</td>				
					<td class="valueStyle">
						<html:text property="productName" styleClass="writeTextStyle"  styleId="productName"></html:text>
						<div id="productNameTip" style="width: 150px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						产品特性
					</td>
					<td class="valueStyle">
						<html:text property="trait" styleClass="writeTextStyle"  styleId="trait"></html:text>
						<div id="traitTip" style="width: 150px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" >
						使用范围
					</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="scope" rows="5" cols="40" styleId="scope"></html:textarea>
						<div id="scopeTip" style="width: 150px;display:inline;"></div>
					</td>				
				</tr>
				<tr>
					<td class="labelStyle">
						使用方法
					</td>
					<td class="valueStyle" colspan="3" >
						<html:textarea property="method" rows="5" cols="40" styleId="method"></html:textarea>
						<div id="methodTip" style="width: 150px;display:inline;"></div>
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
					<input type="button" name="" value="关闭" onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
		</table>
		<html:hidden property="id" />
		<html:hidden property="addMan" value="${session.userInfoSession}"/>
		</html:form>
</body>
</html:html>