<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>来电屏蔽管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../../js/common.js"></script>
<script language="javascript" src="../../js/clock.js"></script>
<script language="javascript" src="../../js/clockCN.js"></script>
<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript></SCRIPT>
<SCRIPT language="javascript" src="../../js/calendar3.js" type=text/javascript></SCRIPT>

	<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
	    //初始化
	    function init(){	
			<logic:equal name="opertype" value="detail">
			document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=insert";
				document.getElementById('spanHead').innerHTML="添加屏蔽信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=update";
				document.getElementById('spanHead').innerHTML="修改屏蔽信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=delete";
				document.getElementById('spanHead').innerHTML="删除屏蔽信息";
				document.getElementById('buttonSubmit').value="删除";
			</logic:equal>		
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"callinFirewall",onerror:function(msg){alert(msg)}});
			$("#callinNum").formValidator({onshow:"请输入来电号码",onfocus:"来电号码不能为空",oncorrect:"来电号码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"来电号码两边不能有空符号"},onerror:"来电号码不能为空"});
			$("#isPass").formValidator({onshow:"请选择是否通过",onfocus:"是否通过必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择是否通过!"});
			$("#beginTime").formValidator({onshow:"请输入开始时间",onfocus:"开始时间不能为空",oncorrect:"开始时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"开始时间两边不能有空符号"},onerror:"开始时间不能为空"});
			$("#endTime").formValidator({onshow:"请输入结束时间",onfocus:"结束时间不能为空",oncorrect:"结束时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"结束时间两边不能有空符号"},onerror:"结束时间不能为空"});
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"callinFirewall",onerror:function(msg){alert(msg)}});
			$("#callinNum").formValidator({onshow:"请输入来电号码",onfocus:"来电号码不能为空",oncorrect:"来电号码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"来电号码两边不能有空符号"},onerror:"来电号码不能为空"});
			$("#isPass").formValidator({onshow:"请选择是否通过",onfocus:"是否通过必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择是否通过!"});
			$("#beginTime").formValidator({onshow:"请输入开始时间",onfocus:"开始时间不能为空",oncorrect:"开始时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"开始时间两边不能有空符号"},onerror:"开始时间不能为空"});
			$("#endTime").formValidator({onshow:"请输入结束时间",onfocus:"结束时间不能为空",oncorrect:"结束时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"结束时间两边不能有空符号"},onerror:"结束时间不能为空"});
		})
		</logic:equal>
    
<%--    	//添加--%>
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.callinNum,"来电号码")) return false;--%>
<%--            if (!checkNotNull(addstaffer.isPass,"是否通过")) return false;--%>
<%--            if (!checkNotNull(addstaffer.isAvailable,"<bean:message bundle='pccye' key='et.pcc.callinFirewall.isAvailable'/>")) return false;--%>
<%--              return true;--%>
<%--            }--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    	      document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=insert";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//修改--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		  document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=update";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//删除--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
    	
<%--    	function openwin(param)--%>
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
		//返回页面
		function toback(){
			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody" onload="init();">
    <logic:notEmpty name="operSign">
	<script>
	alert("操作成功！"); window.close();
	</script>
	</logic:notEmpty>
  
    <html:form action="/callcenter/callinFirewall" method="post" styleId="callinFirewall">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
<%--		    <logic:equal name="opertype" value="detail">--%>
		     当前位置&ndash;&gt;<span id="spanHead">屏蔽详细信息</span>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     当前位置&ndash;&gt;添加屏蔽信息--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     当前位置&ndash;&gt;修改屏蔽信息--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		      当前位置&ndash;&gt;删除屏蔽信息--%>
<%--		    </logic:equal>--%>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
          <tr>
		    <td class="labelStyle" width="30%">来电号码</td>
		    <td class="valueStyle" colspan="3">
		    	<html:text property="callinNum" styleClass="writeTextStyle" styleId="callinNum"/>
		    	<div id="callinNumTip" style="width: 10px;display:inline;"></div>
		    </td>
		  </tr>
		  <tr>
<%--		  	<logic:equal name="opertype" value="detail">--%>
<%--		     <td class="labelStyle">是否通过</td>--%>
<%--		     <td class="valueStyle" colspan="3">--%>
<%--		     	<html:text property="isPass" styleClass="writeTextStyle"/>--%>
<%--		     </td>	 --%>
<%--		    </logic:equal>--%>
		    <logic:notEqual name="opertype" value="detail">
		    <td class="labelStyle">是否通过</td>
		    <td class="valueStyle" colspan="3">	    	
		    	<html:select property="isPass" styleClass="selectStyle" styleId="isPass">		
	        		<html:option value="" >请选择</html:option>
	        		<html:option value="0" >通过</html:option>
	        		<html:option value="1" >未通过</html:option>
	        	</html:select>
	        	<div id="isPassTip" style="width: 10px;display:inline;"></div>
		    </td>
		    </logic:notEqual>
		  </tr>
		  <tr>
		    <td class="labelStyle">开始时间</td>
		    <td class="valueStyle">
		    <html:text property="beginTime" styleClass="writeTextStyle" readonly="true" styleId="beginTime"/>
		    <img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('callinFirewallBean','beginTime',false);">
			<div id="beginTimeTip" style="width: 10px;display:inline;"></div>
			</td>
		 </tr>
		  <tr>	
		    <td class="labelStyle">结束时间</td>
		    <td class="valueStyle">
		    <html:text property="endTime" styleClass="writeTextStyle" readonly="true" styleId="endTime"/>
		    <img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('callinFirewallBean','endTime',false);">
			<div id="endTimeTip" style="width: 10px;display:inline;"></div>
			</td>
		  </tr>
		  <tr>
		    <td class="labelStyle">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>  
		    <td class="valueStyle" colspan="3">
		        <html:textarea property="remark" rows="3" styleClass="writeTextStyle" cols="30"/>
		    </td>
		  </tr>
		  <tr>	
		  
		    <td colspan="4" align="center" width="100%" class="buttonAreaStyle">
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     <input name="btnAdd" type="button"   value="添加" onclick="add()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     <input name="btnAdd" type="button"   value="确定" onclick="update()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		     <input name="btnAdd" type="button"   value="删除" onclick="del()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
			<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
		    <input name="addgov" type="button"   value="关闭" onClick="javascript: window.close();" class="buttonStyle"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
