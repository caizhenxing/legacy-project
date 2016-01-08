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
		if(opener.parent.bottomm){
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}else{
			opener.document.location=opener.document.location;
		}
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>联络员任务管理</title>
		<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
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
				document.getElementById('spanHead').innerHTML="查看任务反馈信息";
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=insert";				
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=update";
				document.getElementById('spanHead').innerHTML="修改任务反馈信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=delete";
				document.getElementById('spanHead').innerHTML="删除任务反馈信息";
				document.getElementById('buttonSubmit').value="删除";
				v_flag="del"
			</logic:equal>		
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"eventResult",onerror:function(msg){alert(msg)}});	
			$("#linkman_id").formValidator({onshow:"请选择受理工号",onfocus:"受理工号必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择受理工号!"});	
			$("#feedback").formValidator({onshow:"请输入反馈内容",onfocus:"反馈内容不能为空",oncorrect:"反馈内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"反馈内容两边不能有空符号"},onerror:"反馈内容不能为空"});	

		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"eventResult",onerror:function(msg){alert(msg)}});	
			$("#linkman_id").formValidator({onshow:"请选择受理工号",onfocus:"受理工号必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择受理工号!"});	
			$("#feedback").formValidator({onshow:"请输入反馈内容",onfocus:"反馈内容不能为空",oncorrect:"反馈内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"反馈内容两边不能有空符号"},onerror:"反馈内容不能为空"});	

		})
		</logic:equal>
		
		
<%--	var url = "eventResult.do?method=toEventResultOper&type=";--%>
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
<%--   		--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>
	
	    function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].linkman;
			arrparm[1] = document.forms[0].cust_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/eventResult/eventResult.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }
	
</script>		
	</head>

	<body class="loadBody" onload="init();">
		<html:form action="/eventResult/eventResult" method="post" styleId="eventResult" onsubmit="return formAction();">
			<html:hidden property="id" />
			<html:hidden property="event_id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
		    				当前位置&ndash;&gt;<span id="spanHead">添加任务反馈信息</span>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
<%--				    		查看信息--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		修改信息--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		删除信息--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">受理工号</td>
<%--					<td class="valueStyle">--%>
<%--						<html:text property="linkman" styleClass="writeTextStyle" />--%>
<%--					</td>--%>
					<td class="valueStyle">
						<html:select property="linkman_id" style="width:75" styleId="linkman_id">
							<option value="">请选择</option>
								<logic:iterate id="u" name="user">
									<html:option value="${u.userId}">${u.userId}</html:option>						
								</logic:iterate>
						</html:select>
						<div id="linkmanTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				
				<tr>
					<td class="labelStyle">联络员</td>
					<td class="valueStyle">
						<html:text property="linkman" styleClass="writeTextStyle" size="30"/>
						<html:hidden property="cust_id" />
						<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="选择联络员" onclick="dep()" width="16" height="16" border="0"/>
					</td>
				</tr>
				
				<tr>
					<td class="labelStyle">反馈时间</td>
					<td class="valueStyle">
						<html:text property="feedback_date" styleClass="writeTextStyle" size="10"/>
						<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('eventResult','feedback_date',false);">
					</td>
				</tr>
				<tr>
					<td class="labelStyle">反馈内容</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="feedback" styleClass="writeTextStyle" rows="4" cols="42" styleId="feedback"/>
						<div id="feedbackTip" style="width: 10px;display:inline;"></div>
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
						<input type="button" value="关闭" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>