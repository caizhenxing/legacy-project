<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="./../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>来电信息查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	<!--
	body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	}
</style>
 <link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
 <SCRIPT language=javascript src="./../js/form.js" type=text/javascript></SCRIPT>
 <SCRIPT language=javascript src="./../js/calendar3.js" type=text/javascript></SCRIPT>
 <script language="javascript" src="./../js/common.js"></script>
 <script language="javascript" src="./../js/clock.js"></script>
 <script language="javascript" src="./../js/ajax.js"></script>
 <script language="javascript" src="./../js/all.js"></script>
		
<script type='text/javascript'src='/callcenterj_sy/dwr/interface/expertService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
		
 <script type="text/javascript">
 document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
 	function add()
 	{
 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../incomming/incommingInfo.do?method=toIncommingInfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	var sTreeId = 0;
	function setTree(obj)
	{
		sTreeId = obj.value;
	}
	function fileAdd()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=insert'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,650,300);
	}
	function fileAddText()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=inserttext'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,850,500);
	}
	
	function selecttype1(){
		//专家类别id
		var billnum = document.getElementById('billNum').value;
		getClassExpertsInfo('expertName','',billnum);
		//动态生成的select id 为 expert_name
		
	}
//------------------------------------------	
	 // 根据专家类别,获得专家
    	function getExpert_dwr(){    	
    		var obj_expertName = document.getElementById("expertName");    		
    		var pro_Value = document.getElementById('billNum').value;
    		if(pro_Value != "" && pro_Value != null){    		
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
 
  </head>
  
 <body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/incomming/incommingInfo.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;服务记录
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="addtimeBegin" styleClass="writeTextStyle" size="10" readonly="true" />
					<img alt="选择日期" src="./../html/img/cal.gif"
						onclick="openCal('incommingInfoBean','addtimeBegin',false);">
				</td>
				<td class="queryLabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="addtimeEnd" styleClass="writeTextStyle" size="10" readonly="true" />
					<img alt="选择日期" src="./../html/img/cal.gif"
						onclick="openCal('incommingInfoBean','addtimeEnd',false);">
				</td>
				<td class="queryLabelStyle">
					来电
				</td>
				<td class="valueStyle">
					<html:text property="tel_num" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="queryLabelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<html:select property="respondent" styleClass="writeTextStyle">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td class="queryLabelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:text property="questionContent" styleClass="writeTextStyle" size="10"/>
				</td>
				
			
				<td class="queryLabelStyle">
					咨询栏目
				</td>
				<td class="valueStyle">
					<html:select styleId="dictQuestionType1" property="dictQuestionType1" styleClass="writeTextStyle" style="text-indent: 5px;">
						<html:option value="">选择专家</html:option>
						<html:option value="政策咨询">政策咨询</html:option>
						<html:option value="种植咨询">种植咨询</html:option>
						<html:option value="养殖咨询">养殖咨询</html:option>
						<html:option value="项目咨询">项目咨询</html:option>
						<html:option value="环保咨询">环保咨询</html:option>
						<html:option value="重大事件上报">重大事件上报</html:option>
						<html:option value="信息定制">信息定制</html:option>
						<html:option value="金农通">金农通</html:option>
						<html:option value="企业服务">企业服务</html:option>
						<html:option value="医疗服务">医疗服务</html:option>
						<html:option value="价格行情">价格行情</html:option>
						<html:option value="价格报送">价格报送</html:option>
						<html:option value="供求发布">供求发布</html:option>
						<html:option value="热线调查">热线调查</html:option>
						<html:option value="万事通">万事通</html:option>
						<option value="供求查询">供求查询</option>
					</html:select>	

				</td>
				<td class="queryLabelStyle">
					解决状态
				</td>
				<td class="valueStyle">
					<select name="dictIsAnswerSucceed" class="writeTextStyle">
					<option value="">请选择</option>
						<jsp:include flush="true" page="../custinfo/textout.jsp?selectName=dict_is_answer_succeed" />
					</select>
				</td>
				<td class="queryLabelStyle">
					解决方式
				</td>
				<td class="valueStyle">
					<select name="answerMan" class="writeTextStyle" style="width: 88px;">
					<option value="">请选择</option>
						<jsp:include flush="true" page="../custinfo/textout.jsp?selectName=answer_man" />
					</select>
				</td>
			</tr>
			
			<tr>	
				<td class="queryLabelStyle">
					是否回访
				</td>
				<td class="valueStyle">
					<html:select styleId="dictIsCallback" property="dictIsCallback" styleClass="writeTextStyle" style="text-indent: 5px;">
					<html:option value="">请选择</html:option>
					<html:option value="否">否</html:option>
					<html:option value="是">是</html:option>
					</html:select>					
				</td>
				<td class="queryLabelStyle">
					受理专家
				</td>
				<td class="valueStyle" colspan="2">
					<html:select styleId="billNum" property="billNum" styleClass="writeTextStyle" onchange="getExpert_dwr()" style="text-indent: 5px;">
						<html:option value="">选择专家类别</html:option>
						<html:options collection="expertList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName" styleClass="writeTextStyle" style="text-indent: 5px;">
					<html:option value="">选择专家</html:option>
					</html:select>
				</td>
				<td class="queryLabelStyle" align="right" colspan="3">
					<input type="button" name="btnSearch" value="查询" class="buttonStyle" onclick="query()" />
					<input type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
