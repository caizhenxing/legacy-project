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
    
    <title>端口管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />    
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
    <script language="javascript" src="../../js/clockCN.js"></script>
    <script language="javascript" src="../../js/clock.js"></script>
    
    <!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
    	//初始化
		function init(){
			
			
		}
		
		
<%--    	//添加--%>
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.seatNum,"座席号")) return false;--%>
<%--            if (!checkNotNull(addstaffer.ip,"IP")) return false;--%>
<%--            --%>
<%--              return true;--%>
<%--            }--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    	      document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=insert";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//修改--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		  document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=update";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//删除--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=delete";--%>
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
  
  <body class="loadBody" onload="init()">
    <logic:equal name="operSign" value="et.pcc.portCompare.samePortOrIp">
	<script>
		alert("座席分机号或IP已经存在！"); window.close();
	</script>
	</logic:equal>
	
	<logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("操作成功！"); toback(); window.close();
	</script>
	</logic:equal>
  
    <html:form action="/callcenter/cclog/calloutLog" method="post" styleId="portCompare">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
<%--		    <logic:equal name="opertype" value="detail">--%>
		     当前位置&ndash;&gt;<span id="spanHead">外呼日志详细信息</span>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     当前位置&ndash;&gt;端口添加信息--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     当前位置&ndash;&gt;端口修改信息--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		     当前位置&ndash;&gt;端口删除信息--%>
<%--		    </logic:equal>--%>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
         
		  <tr>  
		    <td class="labelStyle">外呼号码</td>
		    <td class="valueStyle">
		    	<html:text property="telNum" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">外呼开始时间</td>
		    <td class="valueStyle">
		    	<html:text property="begintime" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">外呼结束时间</td>
		    <td class="valueStyle">
		    	<html:text property="endtime" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">外呼持续时长</td>
		    <td class="valueStyle">
		    	<html:text property="betweetTime" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">外呼类型</td>
		    <td class="valueStyle">
		    	<html:text property="calloutType" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <logic:equal name="ctType" value="voice">
		  <tr>  
		    <td class="labelStyle">栏目名称</td>
		    <td class="valueStyle">
		    	<html:text property="context" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  </logic:equal>
		  <logic:equal name="ctType" value="message">
		  <tr>  
		    <td class="labelStyle">消息内容</td>
		    <td class="valueStyle">
		    	<html:textarea property="context" styleClass="writeTextStyle" cols="40" rows="5"/>
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">文件路径</td>
		    <td class="valueStyle">
		    	<html:text property="filepath" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  </logic:equal>

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
		    <input name="addgov" type="button"   value="关闭" onClick="javascript: window.close();" class="buttonStyle"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
