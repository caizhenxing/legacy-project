<%@ page contentType="text/html; charset=gbk" language="java" errorPage=""%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
		<title></title>
		<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
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
					document.forms[0].action = "question.do?method=toQuestionOper&type=insert";
					//document.getElementById('spanHead').innerHTML="添加";
					document.getElementById('buttonSubmit').value="添 加";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "question.do?method=toQuestionOper&type=update";
					//document.getElementById('spanHead').innerHTML="修改";
					document.getElementById('buttonSubmit').value="修 改";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "question.do?method=toQuestionOper&type=delete";
					//document.getElementById('spanHead').innerHTML="删除";
					document.getElementById('buttonSubmit').value="删 除";
					v_flag="del"
				</logic:equal>		
			}
			//执行验证
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"question",onerror:function(msg){alert(msg)}});	
				$("#question_content").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});	
				$("#answer_content").formValidator({onshow:"请输入咨询答案",onfocus:"咨询答案不能为空",oncorrect:"咨询答案合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询答案两边不能有空符号"},onerror:"咨询答案不能为空"});	
			
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"question",onerror:function(msg){alert(msg)}});	
				$("#question_content").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});	
				$("#answer_content").formValidator({onshow:"请输入咨询答案",onfocus:"咨询答案不能为空",oncorrect:"咨询答案合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询答案两边不能有空符号"},onerror:"咨询答案不能为空"});	
			
			})
			</logic:equal>		
		
<%--		function checkForm(question){--%>
<%--            if (!checkNotNull(question.question_content,"咨询内容")) return false;--%>
<%--            if (!checkNotNull(question.answer_content,"咨询答案")) return false;            --%>
<%--            return true;--%>
<%--   		}--%>
<%--		var url = "question.do?method=toQuestionOper&type=";--%>
<%--		//添加--%>
<%--	   	function add(){  --%>
<%--	   		var f =document.forms[0];--%>
<%--	    	if(checkForm(f)){--%>
<%--		   		document.forms[0].action = url + "insert";--%>
<%--				document.forms[0].submit();--%>
<%--			}--%>
<%--	   	}--%>
<%--	   	//修改--%>
<%--	   	function update(){--%>
<%--	   		var f =document.forms[0];--%>
<%--	    	if(checkForm(f)){--%>
<%--		   		document.forms[0].action = url + "update";--%>
<%--				document.forms[0].submit();--%>
<%--			}--%>
<%--	   	}--%>
<%--	   	//删除--%>
<%--	   	function del(){--%>
<%--			if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){--%>
<%--	   		--%>
<%--	   		document.forms[0].action = url + "delete";--%>
<%--	   		document.forms[0].submit();--%>
<%--	   		--%>
<%--	   		}--%>
<%--	   	}--%>
	   	
	</script>
	</head>
	
	<body class="loadBody" onload="init();">
		<html:form action="/question/question" method="post" styleId="question" onsubmit="return formAction();">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="navigateTable">
				<tr>
					<td width="12%" class="navigateStyle">
						当前咨询信息 咨询栏目
						<html:hidden property="id" />
						<html:select property="dict_question_type1"
							styleClass="selectStyle">
							<html:option value="政策咨询">政策咨询</html:option>
							<html:option value="种植咨询">种植咨询</html:option>
							<html:option value="养殖咨询">养殖咨询</html:option>
							<html:option value="项目咨询">项目咨询</html:option>
							<html:option value="环保咨询">环保咨询</html:option>
							<html:option value="重大事件上报">重大事件上报</html:option>
							<html:option value="信息订制">信息订制</html:option>
							<html:option value="金农通">金农通</html:option>
							<html:option value="企业服务">企业服务</html:option>
							<html:option value="医疗服务">医疗服务</html:option>
							<html:option value="价格行情">价格行情</html:option>
							<html:option value="价格报送">价格报送</html:option>
							<html:option value="供求发布">供求发布</html:option>
							<html:option value="热线调查">热线调查</html:option>
						</html:select>
						受理专家
							<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
									onchange="selecttype1()" style="width:130px">
									<html:option value="0">请选择专家</html:option>
									<html:options collection="expertTypeList" property="value"
										labelProperty="label" styleClass="writeTypeStyle" />
									<html:option value="0">金农热线</html:option>
							</html:select>
							<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName)){
										out.print("<option value="+rExpertName+">*"+rExpertName+"</option>");
									}else{
										out.print("<option value=\"0\">选择专家</option>");
									}
								%>
					</html:select>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" height="100%" border="0">
							<tr>
								<td width="65%">
									<table width="100%" border="0">
										<tr>
											<td class="labelStyle">咨询内容</td>
											<td width="40%" colspan="3" class="valueStyle">
												<html:textarea property="question_content" cols="44"
													styleClass="writeTextStyle" styleId="question_content"/>
												<div id="question_contentTip" style="width: 10px;display:inline;"></div>	
											</td>
										</tr>
										<tr>
											<td class="labelStyle">咨询答案</td>
											<td colspan="3" class="valueStyle">
												<html:textarea property="answer_content" cols="44" rows="3"
													styleClass="writeTextStyle" styleId="answer_content"/>
												<div id="answer_contentTip" style="width: 10px;display:inline;"></div>
											</td>
										</tr>
										<tr>
											<td width="25%" align="left" class="labelStyle">问题分类</td>
											<td colspan="3" width="75%" class="valueStyle">
												<html:select property="dict_question_type2"
													styleClass="selectStyle" onchange="selecttype(this)">
													<html:option value="">请选择大类</html:option>
													<html:option value="粮油作物">粮油作物</html:option>
													<html:option value="经济作物">经济作物</html:option>
													<html:option value="蔬菜">蔬菜</html:option>
													<html:option value="药材">药材</html:option>
													<html:option value="花卉">花卉</html:option>
													<html:option value="草坪及地被">草坪及地被</html:option>
													<html:option value="家畜">家畜</html:option>
													<html:option value="家禽">家禽</html:option>
													<html:option value="牧草">牧草</html:option>
													<html:option value="鱼类">鱼类</html:option>
													<html:option value="虾/蟹/鳖/龟/蛙/藻/螺贝及软体类">虾/蟹/鳖/龟/蛙/藻/螺贝及软体类</html:option>
													<html:option value="特种养殖">特种养殖</html:option>
													<html:option value="基础设施及生产资料">基础设施及生产资料</html:option>
													<html:option value="政策法规及管理">政策法规及管理</html:option>
													<html:option value="其他">其他</html:option>
												</html:select>
												<br>
												<span id="selectspan"> <html:text
														property="dict_question_type3" styleClass="writeTextStyle" />
												</span>
												<br>
												<select name="dict_question_type4" class="selectStyle">
													<option>品种介绍</option>
													<option>栽培管理</option>
													<option>养殖管理</option>
													<option>疫病防治</option>
													<option>虫草防除</option>
													<option>收获贮运</option>
													<option>产品加工</option>
													<option>市场行情</option>
													<option>饲料配制</option>
													<option>农资使用</option>
													<option>设施修建</option>
													<option>政策管理</option>
													<option>其他综合	</option>
												</select>
											</td>
										</tr>
										<tr>
											<td width="25%" class="labelStyle">解决状态</td>
											<td width="25%" class="valueStyle">
												<html:select property="dict_is_answer_succeed"
													styleClass="selectStyle">
													<html:options collection="dict_is_answer_succeed"
														property="value" labelProperty="label" />
												</html:select>
											</td>
											<td width="25%" class="labelStyle">解决方式</td>
											<td width="25%" class="valueStyle">
												<html:select property="answer_man" styleClass="selectStyle">
													<html:options collection="answer_man" property="value"
														labelProperty="label" />
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="labelStyle">是否回访</td>
											<td colspan="3" class="valueStyle">
												<html:select property="dict_is_callback"
													styleClass="selectStyle">
													<html:option value="否">否</html:option>
													<html:option value="是">是</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="labelStyle">回访时间</td>
											<td colspan="3" class="valueStyle">
												<%
															String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
															.format(new java.util.Date());
												%>
												<html:text property="callback_time"
													onclick="openCal('question','callback_time',false);"
													size="10" value="<%=date%>" styleClass="writeTextStyle" />
												<img alt="选择日期" src="../html/img/cal.gif"
													onclick="openCal('question','callback_time',false);">
											</td>
										</tr>
										<tr class="buttonAreaStyle">
											<td colspan="4" align="center">
<%--												<logic:equal name="opertype" value="insert">--%>
<%--													<input type="button" name="Submit" value="添 加"--%>
<%--														  onClick="add()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="reset" name="Submit2" value="重 置"--%>
<%--														 class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="update">--%>
<%--													<input type="button" name="Submit" value="确 定"--%>
<%--														  onClick="update()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="reset" name="Submit2" value="重 置"--%>
<%--														 class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="delete">--%>
<%--													<input type="button" name="Submit" value="删 除"--%>
<%--														  onClick="del()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="button" name="Submit" value="关 闭"--%>
<%--														  onClick="javascript:window.close()" class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="detail">--%>
<%--													<input type="button" name="Submit" value="关 闭"--%>
<%--														  onClick="javascript:window.close()" class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
											<input type="submit" name="button" id="buttonSubmit" value="提交"  class="buttonStyle"/>
      										&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" name="Submit" value="关 闭" onClick="javascript:window.close()" class="buttonStyle"/>
											</td>
										</tr>
									</table>
								</td>
								<td width="50%" class="valueStyle">
									<table width="100%" height="100%" border="0">
										<tr>
											<td>
												<input name="textfield242" type="text" value="搜索相关问题"
													size="60" onClick="if(this.value=='搜索相关问题')this.value=''"
													onpropertychange="search(this.value)"
													class="writeTextStyle">
											</td>
										</tr>
										<tr>
											<td height="100%">
												<DIV style="width:100%;height:100%;overflow-y:auto;"
													id="div1">
													<table width="100%">
														<tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'"
															onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';">
															<td width="100%" class="valueStyle">搜索结果列表</td>
														</tr>
													</table>
												</Div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<logic:notEqual name="opertype" value="insert">
							<logic:equal name="isCallback" value="yes">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="1" class="listTable">
									<tr>
										<td class="valueStyle" width="20%">回访记录</td>
										<td class="valueStyle" width="40%">
											回访是否成功
											<html:select property="is_callback_succ"
												styleClass="selectStyle">
												<html:option value="成功">成功</html:option>
												<html:option value="不成功">不成功</html:option>
											</html:select>
										</td>
										<td class="valueStyle" colspan="2">
											<logic:equal name="isAdd" value="yes">
												<input name="btnAdd" type="button"  
													value="添加回访"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=insert&qid='+document.forms[0].id.value,'','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="listTitleStyle" width="30%">回访内容</td>
										<td class="listTitleStyle" width="40%">回访备注</td>
										<td class="listTitleStyle" width="10%">回访时间</td>
										<td class="listTitleStyle" width="11%">操&nbsp;&nbsp;&nbsp;&nbsp;作</td>
									</tr>
									<logic:iterate id="pagelist" name="list" indexId="i">
										<%
											String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
										%>
										<tr>
											<td >
												<bean:write name="pagelist" property="callback_content"
													filter="true" />
											</td>
											<td >
												<bean:write name="pagelist" property="remark" filter="true" />
											</td>
											<td >
												<bean:write name="pagelist" property="callback_time"
													filter="true" />
											</td>
											<td >

												<img alt="详细"
													src="../style/<%=styleLocation%>/images/detail.gif"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=detail&id=<bean:write name='pagelist' property='id'/>','','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
												<img alt="修改"
													src="../style/<%=styleLocation%>/images/update.gif"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=update&id=<bean:write name='pagelist' property='id'/>','','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
												<img alt="删除"
													src="../style/<%=styleLocation%>/images/del.gif"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=delete&id=<bean:write name='pagelist' property='id'/>','','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
											</td>
										</tr>
									</logic:iterate>
								</table>
							</logic:equal>
						</logic:notEqual>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
	<script language="javascript" type="text/JavaScript">

	function search(v){
		if(v != ""){
			var dict_question_type1 = document.forms[0].dict_question_type1.options[document.forms[0].dict_question_type1.selectedIndex].value;
			sendRequest("../custinfo/openwin.do?method=toSearch", "v="+v, processResponse2);	
		}
	}
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		sendRequest("../custinfo/select_type.jsp", "svalue="+svalue, processResponse3);
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
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}

	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("div1").innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponse3() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("selectspan").innerHTML = "<select name='dict_question_type3' class='selectStyle'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
			function selecttype1(){
		//专家类别id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//动态生成的select id 为 expert_name
		}
</script>
</html>