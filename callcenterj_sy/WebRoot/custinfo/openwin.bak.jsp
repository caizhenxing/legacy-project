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
		alert("�����ɹ�");
		window.opener=null;
		window.close();
	</script>
</logic:empty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>���絯������</title>
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
									�û�������Ϣ
								</td>
								<td colspan="7" width="88%" class="valueStyle">
									������룺
									<input name="tel" type="text" size="13"
										value='<%=request.getAttribute("tel")%>'
										class="writeTextStyle">
									�û���ַ��
									<html:text property="cust_addr" size="45"
										styleClass="writeTextStyle" />
									<input name="add" type="button" id="add" value="ѡ��"
										onClick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
										class="buttonStyle" />
									<html:hidden property="cust_id" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="valueStyle">
									<html:text property="cust_name" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									��&nbsp;&nbsp;&nbsp; ��
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
									��
									<br>
									<br>
									&nbsp;&nbsp;&nbsp;ע
								</td>
								<td rowspan="4" class="valueStyle">
									<html:textarea property="remark" cols="15" rows="5"
										styleClass="writeTextStyle" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									סլ�绰
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_home" size="13"
										styleClass="writeTextStyle" onclick="OR(this)" />
								</td>
								<td class="labelStyle">
									�칫�绰
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_work" size="13"
										styleClass="writeTextStyle" onclick="OR(this)" />
								</td>
								<td class="labelStyle">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="valueStyle">
									<html:text property="cust_fax" size="13"
										styleClass="writeTextStyle" />
								</td>
							</tr>
							<tr align="center">
								<td class="labelStyle">
									�ƶ��绰
								</td>
								<td class="valueStyle">
									<html:text property="cust_tel_mob" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									��&nbsp;&nbsp;&nbsp; ��
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
									�û���ҵ
								</td>
								<td class="valueStyle">
									<html:select property="cust_voc" styleClass="selectStyle">
										<html:option value="��ͨũ��">��ͨũ��</html:option>
										<html:option value="��ֲ��">��ֲ��</html:option>
										<html:option value="��ֳ��">��ֳ��</html:option>
										<html:option value="�ӹ���">�ӹ���</html:option>
										<html:option value="ũ�徭����">ũ�徭����</html:option>
										<html:option value="ũ�ʾ�����">ũ�ʾ�����</html:option>
									</html:select>
								</td>
								<td class="labelStyle">
									��ҵ��ģ
								</td>
								<td class="valueStyle">
									<html:text property="cust_scale" size="13"
										styleClass="writeTextStyle" />
								</td>
								<td class="labelStyle">
									�û�����
								</td>
								<td class="valueStyle">
									<%--		  <html:select property="cust_type" styleClass="writeTextStyle">--%>
									<%--	        <html:options collection="typeList" property="value" labelProperty="label"/>--%>
									<%--	      </html:select>--%>
									<select name="dict_cust_type" class="writeTypeStyle">
										<option value="SYS_TREE_0000002109" class="writeTypeStyle"
											selected="selected">
											����ͨ�û�
										</option>
										<option value="SYS_TREE_0000002108" class="writeTypeStyle">
											������Ա
										</option>
										<option value="SYS_TREE_0000002104" class="writeTypeStyle">
											����ҵ
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
									��ʷ��ѯ��Ϣ
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
									��ǰ��ѯ��Ϣ
								</td>
								<td width="88%" class="valueStyle">
									��ѯ��Ŀ
									<select name="dict_question_type1"
										onChange="edit(this.options[this.selectedIndex].value);"
										class="selectStyle">
										<option value="������ѯ">
											������ѯ
										</option>
										<option value="��ֲ��ѯ">
											��ֲ��ѯ
										</option>
										<option value="��ֳ��ѯ">
											��ֳ��ѯ
										</option>
										<option value="��Ŀ��ѯ">
											��Ŀ��ѯ
										</option>
										<option value="������ѯ">
											������ѯ
										</option>
										<option value="�ش��¼��ϱ�">
											�ش��¼��ϱ�
										</option>
										<option value="��Ϣ����">
											��Ϣ����
										</option>
										<option value="��ũͨ">
											��ũͨ
										</option>
										<option value="��ҵ����">
											��ҵ����
										</option>
										<option value="ҽ�Ʒ���">
											ҽ�Ʒ���
										</option>
										<option value="�۸�����">
											�۸�����
										</option>
										<option value="�۸���">
											�۸���
										</option>
										<option value="���󷢲�">
											���󷢲�
										</option>
										<option value="���ߵ���">
											���ߵ���
										</option>
										<option value="����ͨ">
											����ͨ
										</option>
									</select>
									����ר��
									<%--          <select name="bill_num"  class="selectStyle">--%>
									<%--                  <jsp:include flush="true" page="textout.jsp?selectName=expertType"/>--%>
									<%--          </select>--%>
									<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
										onchange="selecttype1()">
										<html:option value="0">��ѡ��ר��</html:option>
										<html:options collection="expertList" property="value"
											labelProperty="label" styleClass="writeTypeStyle" />
										<html:option value="0">��ũ����</html:option>
									</html:select>
									
									<!-- ���ר����Ϣ -->
									<select id="expert_name" name="expert_name"
										class="selectStyle">
										<option value="">ѡ��ר��</option>
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
															��ѯ����
														</td>
														<td width="40%" colspan="3" class="valueStyle">
															<textarea name="question_content" cols="47"
																class="writeTextStyle"></textarea>
														</td>
													</tr>
													<tr>
														<td align="right" class="labelStyle">
															���ߴ�
														</td>
														<td colspan="3" class="valueStyle">
															<textarea name="answer_content" cols="47" rows="3"
																class="writeTextStyle"></textarea>
														</td>
													</tr>
													<tr>
														<td width="25%" align="right" class="labelStyle">
															�������
														</td>
														<td colspan="3" width="75%" class="valueStyle">
															<select name="dict_question_type2" class="selectStyle"
																onChange="selecttype(this)">
																<option>
																	��ѡ�����
																</option>
																<option>
																	��������
																</option>
																<option>
																	��������
																</option>
																<option>
																	�߲�
																</option>
																<option>
																	ҩ��
																</option>
																<option>
																	����
																</option>
																<option>
																	��ƺ���ر�
																</option>
																<option>
																	����
																</option>
																<option>
																	����
																</option>
																<option>
																	����
																</option>
																<option>
																	����
																</option>
																<option>
																	Ϻ/з/��/��/��/��/�ݱ���������
																</option>
																<option>
																	������ֳ
																</option>
																<option>
																	������ʩ����������
																</option>
																<option>
																	���߷��漰����
																</option>
																<option>
																	����
																</option>
															</select>
															<br>
															<span id="selectspan"> <select
																	name="dict_question_type3" class="selectStyle">
																	<option>
																		��ѡ��С��
																	</option>
																	<option>
																		����ѡ����࣬Ȼ��ſ���ѡ��С��
																	</option>
																</select>
															</span>
															<br>
															<select name="dict_question_type4" class="selectStyle">
																<option>
																	Ʒ�ֽ���
																</option>
																<option>
																	�������
																</option>
																<option>
																	��ֳ����
																</option>
																<option>
																	�߲�����
																</option>
																<option>
																	��ݷ���
																</option>
																<option>
																	�ջ�����
																</option>
																<option>
																	��Ʒ�ӹ�
																</option>
																<option>
																	�г�����
																</option>
																<option>
																	��������
																</option>
																<option>
																	ũ��ʹ��
																</option>
																<option>
																	��ʩ�޽�
																</option>
																<option>
																	���߹���
																</option>
																<option>
																	�����ۺ�
																</option>
															</select>
														</td>
													</tr>
													<tr>
														<td width="25%" align="right" class="labelStyle">
															���״̬
														</td>
														<td width="25%" class="valueStyle">
															<select name="dict_is_answer_succeed" class="selectStyle">
																<jsp:include flush="true"
																	page="textout.jsp?selectName=dict_is_answer_succeed" />
															</select>
														</td>
														<td width="25%" align="right" class="labelStyle">
															�����ʽ
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
															�Ƿ�ط�
														</td>
														<td class="valueStyle">
															<select name="dict_is_callback" class="selectStyle">
																<option>
																	��
																</option>
																<option>
																	��
																</option>
															</select>
														</td>
														<td align="right" class="labelStyle">
															���Ϊ
														</td>
														<td rowspan="2" class="valueStyle">
															<select name="savedata" size="3" multiple
																class="selectStyle">
																<option>
																	��ͨ������
																</option>
																<option>
																	���㰸����
																</option>
																<option>
																	���ﰸ����
																</option>
																<option>
																	Ч��������
																</option>
																<option>
																	ũ��Ʒ�۸��
																</option>
																<option>
																	ũ��Ʒ�����
																</option>
																<option>
																	ר������
																</option>
																<option>
																	ҽ����Ϣ��
																</option>
																<option>
																	��ҵ��Ϣ��
																</option>
															</select>
														</td>
													</tr>
													<tr>
														<td align="right" class="labelStyle">
															�ط�ʱ��
														</td>
														<td colspan="2" class="valueStyle">
															<input name="callback_time" type="text"
																onclick="openCal('openwin','callback_time',false);"
																value='<%=request.getAttribute("date")%>' size="10"
																class="writeTextStyle">
															<img alt="ѡ������" src="../html/img/cal.gif"
																onclick="openCal('openwin','callback_time',false);">
														</td>
													</tr>
												</table>
											</td>
											<td width="50%" class="valueStyle">
												<table width="100%" height="100%" border="0">
													<tr>
														<td>
															<input name="textfield242" type="text" value="�����������"
																size="60"
																onClick="if(this.value=='�����������')this.value=''"
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
																			��������б�
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
						<input type="submit" name="Submit" value="�� ��"
							onclick="return check()" class="buttonStyle" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value="�� ��" class="buttonStyle" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value="�� ��"
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
		if(svalue != "��ѡ�����"){
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
	
		if(v == "�۸���"){		//�۸��ʽ
			text = text[0];
		}else if(v == "���󷢲�"){					//�⹩���ʽ
			text = text[1];
		}else if(v == "���ߵ���"){	
			text = text[3];
			//window.open("../inquiry.do?method=toFilter");
		}else{										//�����ʽ
			text = text[2];
		}
		
		sendRequest(text, null, processResponse);
		
	}

	var XMLHttpReq = false;
 	//����XMLHttpRequest����       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla �����
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE�����
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//����������
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//ָ����Ӧ����
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // ��������
	}
	// ��������Ϣ����
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("edit").innerHTML = res;
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("div1").innerHTML = res;
                
            } else { //ҳ�治����
                //window.alert("���������ҳ�����쳣��");
            }
        }
	}
	function processResponse3() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("selectspan").innerHTML = "<select name='dict_question_type3' class='selectStyle'>"+res+"</select>";
                
            } else { //ҳ�治����
                //window.alert("���������ҳ�����쳣��");
            }
        }
	}
	function processResponse4() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				document.getElementById(producttd).innerHTML = "<select name='dict_product_type1' class='selectStyle'>"+res+"</select>";
                
            } else { //ҳ�治����
                //window.alert("���������ҳ�����쳣��");
            }
        }
	}
	//���ݴ���绰�����ж��Ƿ��ֻ��ţ������ֵ
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
		var rowindex = obj.parentNode.parentNode.rowIndex-1;//Ҫ�ı�������е�
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
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById(producttd2).innerHTML = "<select name='dict_product_type2' class='selectStyle'  onChange='select1(this)'><OPTION>��ѡ��С��</OPTION>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}
	function processResponseProduct2() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById(producttd2).innerHTML = "<select name='product_name' class='selectStyle'><OPTION>��ѡ������</OPTION>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
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
		td.innerHTML = '<select name="dict_product_type1" class="selectStyle" onChange="select1(this)"><option value="">��ѡ�����</option><option value="����">����</option><option value="����">����</option><option value="����">����</option><option value="��������">��������</option><option value="��ʳ����">��ʳ����</option><option value="��������">��������</option><option value="ʳ�þ�">ʳ�þ�</option><option value="�߲�">�߲�</option><option value="ˮ����">ˮ����</option><option value="ˮ��">ˮ��</option><option value="������ֳ">������ֳ</option><option value="ҩ��">ҩ��</option><option value="��������">��������</option></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "dict_product_type2_td_"+tdindex;
		td.innerHTML = '<select name="dict_product_type2" class="selectStyle"  onChange="select1(this)"><OPTION>��ѡ��С��</OPTION></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "product_name_td_"+tdindex;
		td.innerHTML = '<select name="product_name" class="selectStyle"><OPTION>��ѡ������</OPTION></select>';
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
		td.innerHTML = '<input name="del" type="button" id="del" value="ɾ��" onClick="deltr(this)">';
	}
	function deltr(obj){
		document.getElementById("table").deleteRow(obj.parentNode.parentNode.rowIndex);
	}


	//zhang feng add
	
	//#######################################################################
	function selecttype1(){
		//ר�����id
		var billnum = document.getElementById('bill_num').value;
		getClassExpertsInfo('expert_name','',billnum);
		//��̬���ɵ�select id Ϊ expert_name
		
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
				alert("�û���������Ϊ��");
				document.openwin["cust_name"].focus();
				return false;
			}
			if(document.openwin["cust_addr"].value==""){
				alert("�û���ַ����Ϊ��");
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
