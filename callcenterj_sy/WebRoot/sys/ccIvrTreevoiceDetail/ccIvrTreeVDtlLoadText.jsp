<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>语音信息维护</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></SCRIPT>
<script language="javascript" src="../../js/common.js"></script>
<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
<script language="javascript" src="../../js/clock.js"></script>
<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript></SCRIPT>


<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>

	<script type="text/javascript">
	
		//初始化
		function init(){
			document.forms[0].action = "../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=inserttext";			
		}
		//执行验证
		$(document).ready(function(){
			
			$.formValidator.initConfig({formid:"voicetextId",onerror:function(msg){alert(msg)}});
			$("#treeId").formValidator({empty:false,onshow:"请选择栏目节点",onfocus:"栏目节点必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择栏目节点"});
			$("#playcontent").formValidator({onshow:"请输入播放内容",onfocus:"播放内容不能为空",oncorrect:"播放内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"播放内容两边不能有空符号"},onerror:"播放内容不能为空"});

		})
		
	</script>

<script type="text/javascript">
			
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
			
<%--		function checkForm(addstaffer){--%>
<%--        	if (!checkNotNull(addstaffer.priceRid,"受理工号")) return false;        	--%>
<%--        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;--%>
<%--           return true;--%>
<%--        }--%>
<%--				function add()--%>
<%--				{--%>
<%--				    var f =document.forms[0];--%>
<%--				    var treeid=document.getElementById("treeId").value;--%>
<%--				    if(treeid==''){--%>
<%--				    	alert('请选择父节点id!');--%>
<%--				    	return false;--%>
<%--				    }--%>
<%--				    //不验证--%>
<%--    	    		if(true){--%>
<%--    	    		var voicePathObj = document.getElementById("voicePath");--%>
<%--    	    		var selectFileObj = document.getElementById("selectFile");--%>
<%--    	    		voicePathObj.value = selectFileObj.value;--%>
<%--			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=inserttext";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
<%--				function update()--%>
<%--				{--%>
<%--					var voicePathObj = document.getElementById("voicePath");--%>
<%--    	    		var selectFileObj = document.getElementById("selectFile");--%>
<%--    	    		voicePathObj.value = selectFileObj.value;--%>
<%--			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=update";--%>
<%--			 	--%>
<%--			 		document.forms[0].submit();--%>
<%--				}--%>
<%--				function del()--%>
<%--				{--%>
<%--					var delflag=confirm('是否确定删除？');--%>
<%--					if(true==delflag){--%>
<%--			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=delete";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
<%--			opener.parent.topp.document.location=opener.parent.bottomm.document.location;--%>
		
		}
<%--		function modifyVoicePath()--%>
<%--		{--%>
<%--			document.getElementById('selectFile').style.display='inline';--%>
<%--		}--%>
<%--		function onloadDoWithVoicePath()--%>
<%--		{--%>
<%--			var opertype =  '<bean:write name="opertype" />';--%>
<%--			if('update'==opertype)--%>
<%--			{--%>
<%--				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');--%>
<%--				var voicePath = document.getElementById('voicePath');--%>
<%--				var selectFile = document.getElementById('selectFile');--%>
<%--				selectFile.style.display="none";--%>
<%--				voicePath.style.display="inline";--%>
<%--				modifyVoicePathHref.style.display="inline";--%>
<%--			}--%>
<%--			else if('insert'==opertype)--%>
<%--			{--%>
<%--				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');--%>
<%--				var voicePath = document.getElementById('voicePath');--%>
<%--				var selectFile = document.getElementById('selectFile');--%>
<%--				selectFile.style.display="inline";--%>
<%--				voicePath.style.display="none";--%>
<%--				modifyVoicePathHref.style.display="none";--%>
<%--			}--%>
<%--			else if('inserttext'==opertype)--%>
<%--			{--%>
<%--				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');--%>
<%--				var voicePath = document.getElementById('voicePath');--%>
<%--				var selectFile = document.getElementById('selectFile');--%>
<%--				selectFile.style.display="inline";--%>
<%--				voicePath.style.display="none";--%>
<%--				modifyVoicePathHref.style.display="none";--%>
<%--			}--%>
<%--			else--%>
<%--			{--%>
<%--				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');--%>
<%--				var voicePath = document.getElementById('voicePath');--%>
<%--				var selectFile = document.getElementById('selectFile');--%>
<%--				selectFile.style.display="none";--%>
<%--				voicePath.style.display="inline";--%>
<%--				modifyVoicePathHref.style.display="none";--%>
<%--			}--%>
<%--		}--%>
			</script>

  </head>
<%-- onloadDoWithVoicePath() --%>
  <body  class="loadBody" onload="init();">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); toback();window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/sys/ccIvrTreevoiceDetail.do" method="post" styleId="voicetextId">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		   <td class="navigateStyle">
		    	<logic:equal name="opertype" value="inserttext">
		    		当前位置&ndash;&gt;添加文本信息
		    	</logic:equal>
<%--		    	<logic:equal name="opertype" value="detail">--%>
<%--		    		查看信息--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="update">--%>
<%--		    		修改信息--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="delete">--%>
<%--		    		删除信息--%>
<%--		    	</logic:equal>--%>
		    </td>
		  </tr>
		</table>
  
    	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
    			<tr style="display=none;">
	    			<td class="labelStyle">
	    				id         
	    			</td>
	    			<td class="valueStyle">
						<html:text property="id"  styleClass="writeTextStyle" readonly="true"/>
	    			</td>
	    		 </tr>
	    		 
	    		 <tr style="display=none;">
	    			<td class="labelStyle">
	    				语音路径        
	    			</td>
	    			<td class="valueStyle" colspan="3">
	    				<input type="file"  id="selectFile" name="upfile1"  size="30"   class="input" style="display:none" />   
						<html:text property="voicePath" styleId="voicePath"  styleClass="writeTextStyle" style="display:inline" /><a id="modifyVoicePathHref" href="javascript:modifyVoicePath()">修改</a>
	    			</td>
	    		</tr>
	    		
<%--	    	<tr>--%>
<%--	    		<td class="labelStyle">--%>
<%--	    				是否使用        --%>
<%--	    			</td>--%>
<%--	    			<td class="valueStyle">--%>
<%--	    			<html:select property="isUse" styleClass="selectStyle">--%>
<%--						<html:option value="1">使用</html:option>--%>
<%--						<html:option value="0">不使用</html:option>--%>
<%--					</html:select>--%>
<%--	    			</td>--%>
<%--	    			 </tr>--%>
<%--	    			  <tr>--%>
<%--	    			<td class="labelStyle">--%>
<%--	    				排序         --%>
<%--	    			</td>--%>
<%--	    			<td class="valueStyle" colspan="3">--%>
<%--						<html:text property="layerOrder" styleClass="writeTextStyle"/>--%>
<%--	    			</td>--%>
<%--	    		</tr>--%>
	    		 <tr style="display:none">
	    			<td class="labelStyle">
	    				语言类型         
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:text property="languageType" styleClass="writeTextStyle"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				栏目节点       
	    			</td>
	    			<td class="valueStyle" colspan="3">
<%--						<html:text property="treeId" styleClass="input"/>--%>
	    				<html:select property="treeId" styleClass="selectStyle" styleId="treeId">
		    				<html:option value="">请选择</html:option>
				    		<html:options collection="IVRLVList"
				  							property="value"
				  							labelProperty="label"/>
				    	</html:select>
					<div id="treeIdTip" style="width:180px;display:inline;"></div>
	    			</td>
	    			 </tr>
	    			  <tr>
	    			<td class="labelStyle">
	    				播放内容      
	    			</td>
	    			<td class="valueStyle">
						<html:textarea rows="20" cols="100" property="playcontent" styleClass="writeTextStyle" styleId="playcontent"/>
						<div id="playcontentTip" style="width:180px;display:inline;"></div>
	    			</td>
	    		</tr>
	    		 <tr>
	    			<td class="labelStyle">
	    				备&nbsp;&nbsp;&nbsp;&nbsp;注       
	    			</td>
	    			<td class="valueStyle">
						<html:textarea rows="5" cols="100" property="remark" styleClass="writeTextStyle"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    		</tr>
	    		
	    		
    		<tr>
    			<td colspan="6" bgcolor="#ffffff" align="center" class="buttonAreaStyle">
<%--    			<logic:equal name="opertype" value="inserttext">--%>
<%--    				<input type="button" name="btnAdd" class="buttonStyle" value="添加" onclick="add()"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="update">--%>
<%--    				<input type="button" name="btnUpdate"  class="buttonStyle" value="修改" onclick="update()"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="delete">--%>
<%--    				<input type="button" name="btnDelete"  class="buttonStyle" value="删除" onclick="del()"/>--%>
<%--    			</logic:equal>--%>
    				<input type="submit" name="button" id="buttonSubmit" value="添加" class="buttonStyle" />
    				<input type="button" name="" value="关闭"  class="buttonStyle" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>
			<html:hidden property="id"/>
    	</table>
    	</html:form>
  </body>
</html:html>
