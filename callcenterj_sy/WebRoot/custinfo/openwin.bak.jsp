<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:empty name="tel">
	<script>
		alert("操作成功");
		window.opener=null;
		window.close();
	</script>
</logic:empty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>来电弹出窗口</title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="./../js/ajax.js"></script>
		<script language="javascript" src="./../js/all.js"></script>
		<script language="javascript" src="./../js/agentState.js"></script>
	</head>

	<body class="loadBody">

		<html:form method="post"
			action="/custinfo/openwin.do?method=toOpenwinOper">

			<table width="800" border="0" align="center" class="navigateTable">
				<tr>
					<td colspan="2">
						<table width="100%" border="0">
							<tr>
								<td width="13%" class="labelStyle">
									用户基本信息
								</td>
								<td colspan="7" width="88%" class="valueStyle">
									来电号码：
									<input name="tel" type="text" size="13"
										value='<%=request.getAttribute("tel")%>'
										class="writeTextStyle">
									用户地址：
									<html:text property="cust_addr" size="45"
										styleClass="writeTextStyle" />
									<input name="add" type="button" id="add" value="选择"
										onClick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
										class="buttonStyle" />
									<html:hidden property="cust_id" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									姓&nbsp;&nbsp;&nbsp; 名
								</td>
								<td class="valueStyle">
									<html:text property="cust_name" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									性&nbsp;&nbsp;&nbsp; 别
								</td>
								<td class="valueStyle">
									<html:select property="dict_sex" styleClass="selectStyle">
										<html:options collection="sexList" property="value"
											labelProperty="label" />
									</html:select>
								</td>
								<td class="labelStyle">
									E_mail
								</td>
								<td class="valueStyle">
									<html:text property="cust_email" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td rowspan="4" class="labelStyle">
									备
									<br>
									<br>
									&nbsp;&nbsp;&nbsp;注
								</td>
								<td rowspan="4" class="valueStyle">
									<html:textarea property="remark" cols="15" rows="5"
										styleClass="writeTextStyle" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									住宅电话
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_home" size="13"
										styleClass="writeTextStyle" onclick="OR(this)" />
								</td>
								<td class="labelStyle">
									办公电话
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_work" size="13"
										styleClass="writeTextStyle" onclick="OR(this)" />
								</td>
								<td class="labelStyle">
									传&nbsp;&nbsp;&nbsp; 真
								</td>
								<td class="valueStyle">
									<html:text property="cust_fax" size="13"
										styleClass="writeTextStyle" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									移动电话
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_mob" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									邮&nbsp;&nbsp;&nbsp; 编
								</td>
								<td class="valueStyle">
									<html:text property="cust_pcode" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle"></td>
								<td class="valueStyle"></td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									用户行业
								</td>
								<td class="valueStyle">
									<html:select property="cust_voc" styleClass="selectStyle">
										<html:option value="普通农户">普通农户</html:option>
										<html:option value="种植大户">种植大户</html:option>
										<html:option value="养殖大户">养殖大户</html:option>
										<html:option value="加工大户">加工大户</html:option>
										<html:option value="农村经济人">农村经济人</html:option>
										<html:option value="农资经销商">农资经销商</html:option>
									</html:select>
								</td>
								<td class="labelStyle">
									行业规模
								</td>
								<td class="valueStyle">
									<html:text property="cust_scale" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									用户类型
								</td>
								<td class="valueStyle">
									<%--		  <html:select property="cust_type" styleClass="writeTextStyle">--%>
									<%--	        <html:options collection="typeList" property="value" labelProperty="label"/>--%>
									<%--	      </html:select>--%>
									<select name="dict_cust_type" class="writeTypeStyle">
										<option value="SYS_TREE_0000002109" class="writeTypeStyle"
											selected="selected">
											├普通用户
										</option>
										<option value="SYS_TREE_0000002108" class="writeTypeStyle">
											├联络员
										</option>
										<option value="SYS_TREE_0000002104" class="writeTypeStyle">
											├企业
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0">
							<tr>
								<td class="valueStyle">
									历史咨询信息
								</td>
							</tr>
							<tr>
								<td height="80">
									<IFRAME
										src='openwin.do?method=toQuestionList&tel=<%=request.getAttribute("tel")%>'
										frameborder="0" width="100%" height="100%"></IFRAME>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0" height="100%">
							<tr>
								<td width="12%" class="labelStyle">
									当前咨询信息
								</td>
								<td width="88%" class="valueStyle">
									咨询栏目
									<select name="dict_question_type1"
										onChange="edit(this.options[this.selectedIndex].value);"
										class="selectStyle">
										<option value="政策咨询">
											政策咨询
										</option>
										<option value="种植咨询">
											种植咨询
										</option>
										<option value="养殖咨询">
											养殖咨询
										</option>
										<option value="项目咨询">
											项目咨询
										</option>
										<option value="环保咨询">
											环保咨询
										</option>
										<option value="重大事件上报">
											重大事件上报
										</option>
										<option value="信息定制">
											信息定制
										</option>
										<option value="金农通">
											金农通
										</option>
										<option value="企业服务">
											企业服务
										</option>
										<option value="医疗服务">
											医疗服务
										</option>
										<option value="价格行情">
											价格行情
										</option>
										<option value="价格报送">
											价格报送
										</option>
										<option value="供求发布">
											供求发布
										</option>
										<option value="热线调查">
											热线调查
										</option>
										<option value="万事通">
											万事通
										</option>
									</select>
									受理专家
									<%--          <select name="bill_num"  class="selectStyle">--%>
									<%--                  <jsp:include flush="true" page="textout.jsp?selectName=expertType"/>--%>
									<%--          </select>--%>
									<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
										onchange="selecttype1()">
										<html:option value="0">请选择专家</html:option>
										<html:options collection="expertList" property="value"
											labelProperty="label" styleClass="writeTypeStyle" />
										<html:option value="0">金农热线</html:option>
									</html:select>
									
									<!-- 添加专家信息 -->
									<select id="expert_name" name="expert_name"
										class="selectStyle">
										<option value="">选择专家</option>
									</select>

								</td>
							</tr>
							<tr>
								<td height="80" colspan="2" id="edit">
									<table width="100%" height="100%" border="0">
										<tr>
											<td width="50%">
												<table width="100%" border="0">
													<tr>
														<td align="right" class="labelStyle">
															咨询内容
														</td>
														<td width="40%" colspan="3" class="valueStyle">
															<textarea name="question_content" cols="47"
																class="writeTextStyle"></textarea>
														</td>
													</tr>
													<tr>
														<td align="right" class="labelStyle">
															热线答复
														</td>
														<td colspan="3" class="valueStyle">
															<textarea name="answer_content" cols="47" rows="3"
																class="writeTextStyle"></textarea>
														</td>
													</tr>
													<tr>
														<td width="25%" align="right" class="labelStyle">
															问题分类
														</td>
														<td colspan="3" width="75%" class="valueStyle">
															<select name="dict_question_type2" class="selectStyle"
																onChange="selecttype(this)">
																<option>
																	请选择大类
																</option>
																<option>
																	粮油作物
																</option>
																<option>
																	经济作物
																</option>
																<option>
																	蔬菜
																</option>
																<option>
																	药材
																</option>
																<option>
																	花卉
																</option>
																<option>
																	草坪及地被
																</option>
																<option>
																	家畜
																</option>
																<option>
																	家禽
																</option>
																<option>
																	牧草
																</option>
																<option>
																	鱼类
																</option>
																<option>
																	虾/蟹/鳖/龟/蛙/藻/螺贝及软体类
																</option>
																<option>
																	特种养殖
																</option>
																<option>
																	基础设施及生产资料
																</option>
																<option>
																	政策法规及管理
																</option>
																<option>
																	其他
																</option>
															</select>
															<br>
															<span id="selectspan"> <select
																	name="dict_question_type3" class="selectStyle">
																	<option>
																		请选择小类
																	</option>
																	<option>
																		请先选择大类，然后才可以选择小类
																	</option>
																</select>
															</span>
															<br>
															<select name="dict_question_type4" class="selectStyle">
																<option>
																	品种介绍
																</option>
																<option>
																	栽培管理
																</option>
																<option>
																	养殖管理
																</option>
																<option>
																	疫病防治
																</option>
																<option>
																	虫草防除
																</option>
																<option>
																	收获贮运
																</option>
																<option>
																	产品加工
																</option>
																<option>
																	市场行情
																</option>
																<option>
																	饲料配制
																</option>
																<option>
																	农资使用
																</option>
																<option>
																	设施修建
																</option>
																<option>
																	政策管理
																</option>
																<option>
																	其他综合
																</option>
															</select>
														</td>
													</tr>
													<tr>
														<td width="25%" align="right" class="labelStyle">
															解决状态
														</td>
														<td width="25%" class="valueStyle">
															<select name="dict_is_answer_succeed" class="selectStyle">
																<jsp:include flush="true"
																	page="textout.jsp?selectName=dict_is_answer_succeed" />
															</select>
														</td>
														<td width="25%" align="right" class="labelStyle">
															解决方式
														</td>
														<td width="25%" class="valueStyle">
															<select name="answer_man" class="selectStyle">
																<jsp:include flush="true"
																	page="textout.jsp?selectName=answer_man" />
															</select>
														</td>
													</tr>
													<tr>
														<td align="right" class="labelStyle">
															是否回访
														</td>
														<td class="valueStyle">
															<select name="dict_is_callback" class="selectStyle">
																<option>
																	否
																</option>
																<option>
																	是
																</option>
															</select>
														</td>
														<td align="right" class="labelStyle">
															另存为
														</td>
														<td rowspan="2" class="valueStyle">
															<select name="savedata" size="3" multiple
																class="selectStyle">
																<option>
																	普通案例库
																</option>
																<option>
																	焦点案例库
																</option>
																<option>
																	会诊案例库
																</option>
																<option>
																	效果案例库
																</option>
																<option>
																	农产品价格库
																</option>
																<option>
																	农产品供求库
																</option>
																<option>
																	专题调查库
																</option>
																<option>
																	医疗信息库
																</option>
																<option>
																	企业信息库
																</option>
															</select>
														</td>
													</tr>
													<tr>
														<td align="right" class="labelStyle">
															回访时间
														</td>
														<td colspan="2" class="valueStyle">
															<input name="callback_time" type="text"
																onclick="openCal('openwin','callback_time',false);"
																value='<%=request.getAttribute("date")%>' size="10"
																class="writeTextStyle">
															<img alt="选择日期" src="../html/img/cal.gif"
																onclick="openCal('openwin','callback_time',false);">
														</td>
													</tr>
												</table>
											</td>
											<td width="50%" class="valueStyle">
												<table width="100%" height="100%" border="0">
													<tr>
														<td>
															<input name="textfield242" type="text" value="搜索相关问题"
																size="60"
																onClick="if(this.value=='搜索相关问题')this.value=''"
																onpropertychange="search(this.value)"
																class="writeTextStyle">
														</td>
													</tr>
													<tr>
														<td height="100%">

															<DIV style="width:100%;height:100%;overflow-y:auto;"
																id="div1">

																<table width="100%">
																	<tr bgcolor='#ffffff'
																		onMouseOut="this.bgColor='#ffffff'"
																		onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';"
																		class="valueStyle">
																		<td width="100%">
																			搜索结果列表
																		</td>
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
						</table>
					</td>
				</tr>
				<tr class="buttonAreaStyle">

					<td>
						<input type="submit" name="Submit" value="提 交"
							onclick="return check()" class="buttonStyle" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value="重 置" class="buttonStyle" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value="关 闭"
							onclick="window.close();" class="buttonStyle" />
						&nbsp;&nbsp;
						
					</td>

				</tr>
			</table>

		</html:form>

	</body>

	<script language="javascript" type="text/JavaScript"
		src="../js/calendar3.js"></script>
	<script language="javascript" type="text/JavaScript">
	var producttd = "";
	//################################################################################
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "请选择大类"){
			if(name == "dict_question_type2"){
				sendRequest("select_type.jsp", "svalue="+svalue, processResponse3);
			}else{
				sendRequest("select_type.jsp", "svalue="+svalue, processResponse4);
				var rowindex = obj.parentNode.parentNode.rowIndex-1;
				producttd = "dict_product_type1_td_" + rowindex;
			}
		}
	}

	function search(v){
		if(v != ""){
			var dict_question_type1 = document.forms[0].dict_question_type1.options[document.forms[0].dict_question_type1.selectedIndex].value;
			var url = "openwin.do?method=toSearch&searchV="+v;
			sendRequest(url, "v="+v, processResponse2);	
		}
	}
	
	function edit(v){
		var text = new Array("text1.jsp","text2.jsp","text3.jsp","text4.jsp");
	
		if(v == "价格报送"){		//价格格式
			text = text[0];
		}else if(v == "供求发布"){					//这供求格式
			text = text[1];
		}else if(v == "热线调查"){	
			text = text[3];
			//window.open("../inquiry.do?method=toFilter");
		}else{										//问题格式
			text = text[2];
		}
		
		sendRequest(text, null, processResponse);
		
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
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("edit").innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("div1").innerHTML = res;
                
            } else { //页面不正常
                //window.alert("您所请求的页面有异常。");
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
                //window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponse4() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				document.getElementById(producttd).innerHTML = "<select name='dict_product_type1' class='selectStyle'>"+res+"</select>";
                
            } else { //页面不正常
                //window.alert("您所请求的页面有异常。");
            }
        }
	}
	//根据传入电话号码判断是否手机号，且填充值
	var tel = document.openwin.tel.value;
	var testStr = /^((13|15)+\d{9})$/;
	if(testStr.test(tel)){
		document.openwin.cust_tel_mob.value = tel;
	}else{
		document.openwin.cust_tel_home.value = tel;
		document.openwin.cust_tel_work.value = tel;
	}
	function OR(o){
		if(!testStr.test(tel)){
			if(o==document.openwin.cust_tel_home){
				document.openwin.cust_tel_home.value = "";
				document.openwin.cust_tel_work.value = tel;
			}else{
				document.openwin.cust_tel_home.value = tel;
				document.openwin.cust_tel_work.value = "";
			}
		}
	}

</script>
	<input name="outStr" type="hidden" id="out"
		value="<jsp:include flush="true" page="textout.jsp?selectName=priceType"/>">
	<script>

	var selectStr = document.getElementById("outStr").value;

	function select1(obj){
		var ssid = obj.name;
		var ssvalue = obj.options[obj.selectedIndex].text;
		var rowindex = obj.parentNode.parentNode.rowIndex-1;//要改变的是哪行的
		if(ssvalue == "")
			return;
		if(ssid == "dict_product_type1"){
			sendRequest("../priceinfo/select_product.jsp", "ssvalue="+ssvalue+"&ssid="+ssid, processResponseProduct1);
			this.producttd2 = "dict_product_type2_td_" + rowindex;
		}else if(ssid == "dict_product_type2"){
			sendRequest("../priceinfo/select_product.jsp", "ssvalue="+ssvalue+"&ssid="+ssid, processResponseProduct2);
			this.producttd2 = "product_name_td_" + rowindex;
		}
	}
	
	function processResponseProduct1() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById(producttd2).innerHTML = "<select name='dict_product_type2' class='selectStyle'  onChange='select1(this)'><OPTION>请选择小类</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponseProduct2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById(producttd2).innerHTML = "<select name='product_name' class='selectStyle'><OPTION>请选择名称</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	
	function addtr(){
		var table = document.getElementById("table");
		var rowindex = table.rows.length-1;
		var tdindex = rowindex-1;
		tr = table.insertRow(rowindex);
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<select name="dict_product_type1" class="selectStyle" onChange="select1(this)"><option value="">请选择大类</option><option value="花卉">花卉</option><option value="家禽">家禽</option><option value="家畜">家畜</option><option value="经济作物">经济作物</option><option value="粮食作物">粮食作物</option><option value="生产资料">生产资料</option><option value="食用菌">食用菌</option><option value="蔬菜">蔬菜</option><option value="水产类">水产类</option><option value="水果">水果</option><option value="特种养殖">特种养殖</option><option value="药材">药材</option><option value="油料作物">油料作物</option></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "dict_product_type2_td_"+tdindex;
		td.innerHTML = '<select name="dict_product_type2" class="selectStyle"  onChange="select1(this)"><OPTION>请选择小类</OPTION></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "product_name_td_"+tdindex;
		td.innerHTML = '<select name="product_name" class="selectStyle"><OPTION>请选择名称</OPTION></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<select name="dict_price_type" class="selectStyle">'+ selectStr +'</select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<input name="product_price" type="text" class="writeTextStyle" id="product_price" size="10">';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<input name="remarkj" type="text" class="writeTextStyle" id="remarkj" size="25">';
		td = tr.insertCell();
		td.className = "listTitleStyle";
		td.innerHTML = '<input name="del" type="button" id="del" value="删除" onClick="deltr(this)">';
	}
	function deltr(obj){
		document.getElementById("table").deleteRow(obj.parentNode.parentNode.rowIndex);
	}


	//zhang feng add
	
	//#######################################################################
	function selecttype1(){
		//专家类别id
		var billnum = document.getElementById('bill_num').value;
		getClassExpertsInfo('expert_name','',billnum);
		//动态生成的select id 为 expert_name
		
	}

	var xmlHttp;
	function createXMLHttpRequestWxp()
	{
 	if(window.ActiveXObject)
	 {
 		 xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
 	}
 	else if(window.XMLHttpRequest)
 	{
 	 xmlHttp=new XMLHttpRequest();
	 }
	}
	
	function doRequestUsingGET()
	{
 		createXMLHttpRequestWxp();
 		var queryString="../GetExpertInfo?";
 		//alert(document.getElementById('bill_num').value);
 		queryString=queryString+"experttype="+ document.getElementById('bill_num').value + "&timeStamp=" + new Date().getTime();
 		XMLHttp.onreadystatechange=handleStateChange;
 		XMLHttp.open("GET",queryString,true);
 		XMLHttp.send(null);
	}
	
	function doRequestUsingPost()
	{
 		createXMLHttpRequestWxp();
 		var url="../GetExpertInfo?timeStamp=" + new Date().getTime();
 		XMLHttp.open("POST",url,true);
 		XMLHttp.onreadystatechange=handleStateChange;
 		XMLHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
 		XMLHttp.send(null);
	}
	
	function handleStateChange()
	{
 		if(XMLHttp.readyState==4)
 		{
  			if(XMLHttp.status==200)
  			{
   				parseResults();
  			}
 		}
	}
	function parseResults()
	{
			var responseText= XMLHttpReq.responseText;
			alert(responseText);
	}
</script>
<script>
    	function check(){
    		if(document.openwin["cust_name"].value==""){
				alert("用户姓名不可为空");
				document.openwin["cust_name"].focus();
				return false;
			}
			if(document.openwin["cust_addr"].value==""){
				alert("用户地址不可为空");
				document.openwin["cust_addr"].focus();
				return false;
			}
    		submitInquiry();
    	}
    	function submitInquiry(){
    		var obj = document.iframeInquiry;
    		if(obj){
    			obj = obj.document.frames.rightInquiryFrame;
    			obj.doSubmit();
    		}
    	}
</script>
</html>
