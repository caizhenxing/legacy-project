<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>联络员任务管理</title>
		<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
		<script language="javascript" src="../js/common.js"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
				
		<!-- jquery验证 -->
		<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
		<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
		<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
		<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
		   
		<script type="text/javascript">		
		var v_flag="";
		function formAction(){
			if(v_flag=="del"){
				if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'"))
					return true;
				else
					return false;
			}
		}
		
		//初始化
		function init(){	
			<logic:equal name="opertype" value="detail">
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "event.do?method=toEventOper&type=insert";
				document.getElementById('spanHead').innerHTML="添加任务信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "event.do?method=toEventOper&type=update";
				document.getElementById('spanHead').innerHTML="修改任务信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "event.do?method=toEventOper&type=delete";
				document.getElementById('spanHead').innerHTML="删除任务信息";
				document.getElementById('buttonSubmit').value="删除";
				v_flag="del"
			</logic:equal>		
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"event_form",onerror:function(msg){alert(msg)}});	
			$("#principal").formValidator({onshow:"请输入任务责任人",onfocus:"任务责任人不能为空",oncorrect:"任务责任人合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务责任人两边不能有空符号"},onerror:"任务责任人不能为空"});	
			$("#actor").formValidator({onshow:"请输入任务参与者",onfocus:"任务参与者不能为空",oncorrect:"任务参与者合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务参与者两边不能有空符号"},onerror:"任务参与者不能为空"});	
			$("#topic").formValidator({onshow:"请输入任务名称",onfocus:"任务名称不能为空",oncorrect:"任务名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务名称两边不能有空符号"},onerror:"任务名称不能为空"});	
			$("#contents").formValidator({onshow:"请输入任务详情",onfocus:"任务详情不能为空",oncorrect:"任务详情合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务详情两边不能有空符号"},onerror:"任务详情不能为空"});	
		
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"event_form",onerror:function(msg){alert(msg)}});	
			$("#principal").formValidator({onshow:"请输入任务责任人",onfocus:"任务责任人不能为空",oncorrect:"任务责任人合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务责任人两边不能有空符号"},onerror:"任务责任人不能为空"});	
			$("#actor").formValidator({onshow:"请输入任务参与者",onfocus:"任务参与者不能为空",oncorrect:"任务参与者合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务参与者两边不能有空符号"},onerror:"任务参与者不能为空"});	
			$("#topic").formValidator({onshow:"请输入任务名称",onfocus:"任务名称不能为空",oncorrect:"任务名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务名称两边不能有空符号"},onerror:"任务名称不能为空"});	
			$("#contents").formValidator({onshow:"请输入任务详情",onfocus:"任务详情不能为空",oncorrect:"任务详情合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"任务详情两边不能有空符号"},onerror:"任务详情不能为空"});	
		
		})
		</logic:equal>
	
<%--	var url = "event.do?method=toEventOper&type=";--%>
<%--	//添加--%>
<%--   	function add(){  --%>
<%--   		document.forms[0].action = url + "insert";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--   		document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//删除--%>
<%--   	function del(){--%>
<%--		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>	
</script>
	</head>

	<body class="loadBody" onload="init();">
		<html:form action="/event/event" method="post" styleId="event_form" onsubmit="return formAction();">
			<html:hidden property="id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				当前位置&ndash;&gt;添加任务信息--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		当前位置&ndash;&gt;<span id="spanHead">查看任务信息</span>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		当前位置&ndash;&gt;修改任务信息--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		当前位置&ndash;&gt;删除任务信息--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">任务责任人</td>
					<td class="valueStyle">
						<html:text property="principal" styleClass="writeTextStyle" styleId="principal"/>
						<div id="principalTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">事件时间</td>
					<td class="valueStyle">
						<html:text property="eventdate" styleClass="writeTextStyle" size="10" />
						<img alt="选择时间" src="../html/img/cal.gif" onclick="openCal('event','eventdate',false);">
					</td>
				</tr>
				<tr>
					<td class="labelStyle">任务参与者</td>
					<td class="valueStyle" colspan="3">
						<html:text property="actor" styleClass="writeTextStyle" styleId="actor"/>
						<div id="actorTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">任务名称</td>
					<td class="valueStyle" colspan="3">
						<html:text property="topic" styleClass="writeTextStyle" styleId="topic"/>
						<div id="topicTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">任务详情</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="contents" styleClass="writeTextStyle" rows="4" cols="40" styleId="contents"/>
						<div id="contentsTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="right" class="buttonAreaStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--							<input type="button" name="btnAdd" class="buttonStyle" value="添加" onclick="add()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--							<input type="button" name="btnUpdate" class="buttonStyle" value="确定" onclick="update()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--							<input type="button" name="btnDelete" class="buttonStyle" value="删除" onclick="del()" />--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="提交"  class="buttonStyle"/>
						<input type="button" name="" value="关闭" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>			
			<logic:notEmpty name="list">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
					<tr>
						<td class="valueStyle">
							任务反馈列表
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
					<tr>
						<td class="listTitleStyle">
							联络员
						</td>
						<td class="listTitleStyle">
							反馈内容
						</td>
						<td class="listTitleStyle">
							反馈时间
						</td>
						<td class="listTitleStyle" width="77">
							操&nbsp;&nbsp;作
						</td>
					</tr>
					<logic:iterate id="pagelist" name="list" indexId="i">
						<%
						String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
						%>
						<tr style="line-height: 21px;">
							<td>
								<bean:write name="pagelist" property="linkman" />
							</td>
							<td>
								<bean:write name="pagelist" property="feedback" />
							</td>
							<td>
								<bean:write name="pagelist" property="feedback_date" />
							</td>
							<td>

								<img alt="详细" src="../style/<%=styleLocation%>/images/detail.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=detail&id=<bean:write name="pagelist" property="id"/>',450,150)" />
								<img alt="修改" src="../style/<%=styleLocation%>/images/update.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=update&id=<bean:write name='pagelist' property='id'/>',450,150)" />
								<img alt="删除" src="../style/<%=styleLocation%>/images/del.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=delete&id=<bean:write name='pagelist' property='id'/>',450,150)" />
							</td>
						</tr>
					</logic:iterate>
				</table>
				</logic:notEmpty>			
		</html:form>
	</body>
</html>