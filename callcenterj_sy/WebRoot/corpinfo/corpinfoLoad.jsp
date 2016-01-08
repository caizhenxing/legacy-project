<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>企业库信息操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<style type="text/css">

#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}

</style>	
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
<SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../js/common.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/form.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript></SCRIPT>

<!-- jquery验证 -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- jquery验证 end -->

<script type="text/javascript">	
		function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.corpRid,"受理工号")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
        	
			if (!checkTelNumber(addstaffer.custTel)) return false;
        	
           return true;
        }
				function addinfo()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{
			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();
				opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
			}
		}
		
		//初始化
		function init(){
			<c:choose>
				<c:when test="${opertype=='insert'}">
					document.getElementById('spanHead').innerHTML="添加信息";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=insert";
					document.getElementById('buttonSubmit').value="添加";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=update";
					document.getElementById('buttonSubmit').value="修改";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";
					document.getElementById('buttonSubmit').value="删除";
				</c:when>
				<c:when test="${opertype=='detail'}">
					document.getElementById('spanHead').innerHTML="查看信息";
<%--					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";--%>
					document.getElementById('buttonSubmit').style.display="none"
				</c:when>
			</c:choose>
		}
		
		//执行验证		
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"operCorpinfoId",onerror:function(msg){alert(msg)}});
			$("#corpRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#custName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#custAddr").formValidator({onshow:"请输入用户地址",onfocus:"用户地址不能为空",oncorrect:"用户地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户地址两边不能有空符号"},onerror:"用户地址不能为空"});
			$("#dictServiceType").formValidator({empty:false,onshow:"请选择服务类型",onfocus:"服务类型必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择服务类型"});			
			$("#createTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#contents").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#reply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"operCorpinfoId",onerror:function(msg){alert(msg)}});
			$("#corpRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#custName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#custAddr").formValidator({onshow:"请输入用户地址",onfocus:"用户地址不能为空",oncorrect:"用户地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户地址两边不能有空符号"},onerror:"用户地址不能为空"});
			$("#dictServiceType").formValidator({empty:false,onshow:"请选择服务类型",onfocus:"服务类型必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择服务类型"});			
			$("#createTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#contents").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#reply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			
			})
			</c:when>
		</c:choose>
			</script>

  </head>
  
  <body onunload="toback()" class="loadBody" onload="init()">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/operCorpinfo.do" method="post" styleId="operCorpinfoId">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="navigateStyle">
		    	<span id="spanHead">查看信息</span>
<%--		    	<logic:equal name="opertype" value="insert">--%>
<%--		    		添加信息--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="detail">--%>
<%--		    		查看信息--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="update">--%>
<%--		    		修改信息--%>
<%--		    	</logic:equal>--%>
<%--		    	 <logic:equal name="opertype" value="delete">--%>
<%--		    		删除信息--%>
<%--		    	</logic:equal>--%>
		    </td>
		  </tr>
		</table>
  
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
    			<tr>
    			
	    			<td class="labelStyle">
	    				受理工号
	    			</td>
	    			<td class="valueStyle">
						<html:text property="corpRid" styleClass="writeTextStyle" readonly="true" styleId="corpRid"/>
						<div id="corpRidTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				受理专家
	    			</td>
	    			<td class="valueStyle">
						<html:text property="expert" styleClass="writeTextStyle"/>
	    			</td>
	    			<td class="labelStyle" rowspan="4" width="180" style="text-indent: 0px;">
	    			<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No"
						src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No"
						src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					</td>
	    		</tr>
	    	<tr>
	    		<td class="labelStyle">
	    				用户姓名
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custName" styleClass="writeTextStyle" styleId="custName"/>
						<div id="custNameTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				用户电话
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel"/>
						<div id="custTelTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				用户地址
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custAddr" styleClass="writeTextStyle" styleId="custAddr"/>						
						<input name="add" type="button" id="add" value="选择"
					onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					class="buttonStyle" />
					<div id="custAddrTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				审核状态
	    			</td>
	    			<td class="valueStyle">
	    			<logic:equal name="opertype" value="insert">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=insert"/>
					</logic:equal>
					<logic:equal name="opertype" value="detail">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=detail"/>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=update"/>
					</logic:equal>
					<logic:equal name="opertype" value="delete">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=delete"/>
					</logic:equal>
<%--						<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean"/>--%>
	    			</td>
	    		</tr>
	    		<tr>
	    		<td class="labelStyle">
	    				服务类型
	    			</td>
	    			<td class="valueStyle">
						<html:select property="dictServiceType" styleClass="selectStyle" styleId="dictServiceType">
	    				<html:option value="">请选择</html:option>
	    				<html:options collection="ServiceList" property="value" labelProperty="label"/>
	    				</html:select>
	    				<div id="dictServiceTypeTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				受理时间
	    			</td>
	    			<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" styleId="createTime" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('operCorpinfoBean','createTime',false);">
					<div id="createTimeTip" style="width: 0px;display:inline;"></div>	
				    </td>
	    		</tr>		
	    		<tr>
	    			<td class="labelStyle">
	    				咨询内容
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="contents" styleClass="writeTextStyle" cols="80" rows="5" styleId="contents"/>
						<div id="contentsTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<%
					String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("operCorpinfoBean")).get("id");
					String p = "../upload/show2.jsp?t=oper_corpinfo.id&id=" + id;
					%>
					<td class="labelStyle" rowspan="3" width="180" height="100%" style="text-indent: 0px;">
					  <logic:equal name="opertype" value="insert">
						<iframe frameborder="0" width="100%" height="100%" scrolling="auto" src="<%=p %>"></iframe>
					  </logic:equal>
					  <logic:equal name="opertype" value="update">
						<iframe frameborder="0" width="100%" height="100%" scrolling="auto" src="<%=p %>"></iframe>
					  </logic:equal>
					</td>
	    		</tr>
	    		
	    		<tr>
	    		
	    		<td class="labelStyle">
	    				热线答复
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="reply" styleClass="writeTextStyle" cols="80" rows="5" styleId="reply"/>
						<div id="replyTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				备&nbsp;&nbsp;&nbsp;&nbsp;注
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="remark" styleClass="writeTextStyle" cols="80" rows="5" />
	    			</td>
	    		</tr>
	    		
    		<tr>
    			<td colspan="7" align="center" class="buttonAreaStyle">
<%--    			<logic:equal name="opertype" value="insert">--%>
<%--    				<input type="button" name="btnAdd"   value="添加" onclick="addinfo()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="update">--%>
<%--    				<input type="button" name="btnUpdate"    value="修改" onclick="update()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="delete">--%>
<%--    				<input type="button" name="btnDelete"    value="删除" onclick="del()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
					
						<input type="submit" name="addbtn" value="添加" id="buttonSubmit"
							class="buttonStyle" style="display:inline" />
    			
    				<input type="button" name="" value="关闭"    onClick="javascript:window.close();" class="buttonStyle"/>
    			
    			</td>
    		</tr>
			<html:hidden property="id"/>
    	</table>
    	</html:form>
  </body>
</html:html>
<script>

	function getAccid(v){
		sendRequest("../focusPursue/getAccid.jsp", "state="+v);
	}

	var XMLHttpReq = false;
 	//创建XMLHttpRequest对象       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//发送请求函数
	function sendRequest(url,value) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("accids").innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>
