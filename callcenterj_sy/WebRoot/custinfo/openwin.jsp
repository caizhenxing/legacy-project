<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<logic:notEmpty name="flag">
	<script>
		alert("�����ɹ�");
		//window.opener=null;
		//window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>���絯������</title>
		<link href="style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="./../js/ajax.js"></script>
		<script language="javascript" src="./../js/all.js"></script>
		<script language="javascript" src="./../js/agentState.js"></script>
	</head>

	<body class="loadBody" topmargin="0" leftmargin="0" rightmargin="0">

		<html:form method="post"
			action="/custinfo/openwin.do?method=toOpenwinOper">

			<table width="820" border="0" align="center" class="navigateTable"
				cellpadding="0" cellspacing="0" style="border: solid #000000 1px">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td colspan="8" class="Blue_title">
									�û�������Ϣ
									<html:hidden property="flag" />
									<html:hidden property="cust_id" />
								</td>
							</tr>
							<tr align="center">
								<td class="Content_title" style="text-indent: 10px;">
									�������
								</td>
								<td class="Content" width="140">
									<input name="tel" type="text" size="20"
										value='<%=request.getAttribute("tel")%>' class="Text">
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									�ƶ��绰
								</td>
								<td class="Content" width="140">
									<html:text property="cust_tel_mob" size="20" styleClass="Text" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									סլ�绰
								</td>
								<td class="Content" width="140">
									<html:text property="cust_tel_home" size="20" styleClass="Text"
										onclick="OR(this)" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									�칫�绰
								</td>
								<td class="Content" width="140">
									<html:text property="cust_tel_work" size="20" styleClass="Text"
										onclick="OR(this)" />
								</td>
							</tr>
							<tr align="center">
								<td class="Content_title" style="text-indent: 10px;">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="Content">
									<html:text property="cust_name" size="20" styleClass="Text" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="Content">
									<html:select property="dict_sex" styleClass="Next_pulls"
										style="width: 131px">
										<html:options collection="sexList" property="value"
											labelProperty="label" />
									</html:select>
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									E_mail
								</td>
								<td class="Content">
									<html:text property="cust_email" size="20" styleClass="Text" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="Content">
									<html:text property="cust_fax" size="20" styleClass="Text" />
								</td>
							</tr>
							<tr align="center">
								<td class="Content_title" style="text-indent: 10px;">
									�û���ַ
								</td>
								<td class="Content" colspan="3">
									<html:text property="cust_addr" size="47" styleClass="Text" />
									<input name="add" type="button" id="add" value="ѡ��"
										onClick="window.open('select_address.jsp','','width=480,height=100,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
										class="buttonStyle"
										style="BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #002D96 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform . Microsoft . Gradient(GradientType = 0, StartColorStr = #FFFFFF, EndColorStr = #9DBCEA); BORDER-LEFT: #002D96 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #002D96 1px solid; height: 17; width: 33;" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									��&nbsp;&nbsp;&nbsp; ��
								</td>
								<td class="Content">
									<html:text property="cust_pcode" size="20" styleClass="Text" />
								</td>
								<td class="Content_title" rowspan="2" style="text-indent: 10px;">
									��&nbsp;&nbsp;&nbsp; ע
								</td>
								<td class="Content" rowspan="2">
									<html:textarea property="remark" rows="3" style="width: 130px;"
										styleClass="Note_text" />
								</td>
							</tr>
							<tr align="center">
								<td class="Content_title" style="text-indent: 10px;">
									�û���ҵ
								</td>
								<td class="Content">
									<html:select property="cust_voc" styleClass="Next_pulls"
										style="width: 131px;">
										<html:option value="��ͨũ��">��ͨũ��</html:option>
										<html:option value="��ֲ��">��ֲ��</html:option>
										<html:option value="��ֳ��">��ֳ��</html:option>
										<html:option value="�ӹ���">�ӹ���</html:option>
										<html:option value="ũ�徭����">ũ�徭����</html:option>
										<html:option value="ũ�ʾ�����">ũ�ʾ�����</html:option>
									</html:select>
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									��ҵ��ģ
								</td>
								<td class="Content">
									<html:text property="cust_scale" size="20" styleClass="Text" />
								</td>
								<td class="Content_title" style="text-indent: 10px;">
									�û�����
								</td>
								<td class="Content">
									<!-- zhangfeng add û�취�����ֻ����ʱ��ô�ӣ��Ժ��޸ĵ�ʱ����˵ -->
									<html:select property="cust_type" styleClass="Next_pulls"
										style="width: 131px;">
										<html:option value="SYS_TREE_0000002109">��ͨũ��</html:option>
										<html:option value="SYS_TREE_0000002103">ר��</html:option>
										<html:option value="SYS_TREE_0000002104">��ҵ</html:option>
										<html:option value="SYS_TREE_0000002105">ý��</html:option>
										<html:option value="SYS_TREE_0000002106">����</html:option>
										<html:option value="SYS_TREE_0000002108">����Ա</html:option>
									</html:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td class="Blue_title" colspan="6">
									��ʷ��ѯ��Ϣ
								</td>
							</tr>
							<tr class="Blue_title2">
								<td width="91">
									�������
								</td>
								<td width="120">
									����ʱ��
								</td>
								<td width="100">
									��ѯ��Ŀ
								</td>
								<td>
									��ѯ����
								</td>
								<td width="90">
									������
								</td>
								<td width="90">
									����
								</td>
							</tr>
							<tr>
								<td height="46" colspan="6">
									<IFRAME scrolling="yes"
										src='openwin.do?method=toQuestionList&tel=<%=request.getAttribute("tel")%>'
										frameborder="0" width="100%" height="100%"></IFRAME>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" height="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr>
								<td class="Blue_title" colspan="4">
									��ǰ��ѯ��Ϣ
								</td>
							</tr>
							<tr>
								<td width="61" class="Content_title" style="text-indent: 10px;">
									��ѯ��Ŀ
								</td>
								<td width="335" class="Content" style="text-align: left;">
									<!-- zhangfeng add ��ӷ���changeOptionValue���ı�option value��ֵ -->
									<select name="dict_question_type1"
										onChange="edit(this.options[this.selectedIndex].value)"
										class="Next_pulls" style="text-indent: 5px;">
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
										<option value="�����ѯ">
											�����ѯ
										</option>
									</select>
								</td>
								<td class="Content_title" style="text-indent: 10px;" width="60">
									����ר��
								</td>
								<td class="Content" width="352" style="text-align: left;">
									<html:select styleId="bill_num" property="bill_num"
										styleClass="writeTypeStyle" onchange="selecttype1()"
										style="text-indent: 5px;">
										<html:option value="0">ѡ��ר�����</html:option>
										<html:options collection="expertList" property="value"
											labelProperty="label" styleClass="writeTypeStyle" />
										<html:option value="0">��ũ����</html:option>
									</html:select>
									<select id="expert_name" name="expert_name" class="Next_pulls">
										<option value="">
											ѡ��ר��
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td height="80" colspan="4" id="edit">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="59" class="Content_title"
												style="text-indent: 7px;">
												��ѯ����
											</td>
											<td width="337" class="Content">
												<textarea name="question_content" cols="51" rows="4"
													class="Text"></textarea>
											</td>
											<td width="60" class="Content_title"
												style="text-indent: 10px;">
												�������
											</td>
											<td class="Content" width="235" colspan="3">

												<table cellpadding="0" cellspacing="0">
													<tr>
														<td>
															<select name="dict_question_type2" class="Next_pulls"
																onChange="selecttype(this)" style="width: 230px;"
																style="text-indent: 5px;">
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
														</td>
													</tr>
													<tr>
														<td>
															<span id="selectspan"> <select
																	name="dict_question_type3" class="Next_pulls"
																	style="width: 230px;" style="text-indent: 5px;">
																	<option>
																		��ѡ��С��
																	</option>
																	<option>
																		����ѡ����࣬Ȼ��ſ���ѡ��С��
																	</option>
																</select> </span>
														</td>
													</tr>
													<tr>
														<td>
															<select name="dict_question_type4" class="Next_pulls"
																style="width: 230px;" style="text-indent: 5px;">
																<option style="">
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
												</table>



											</td>
											<td rowspan="3" class="Content_title" width="20"
												align="center">
												��
												<br>
												<br>
												��
												<br>
												<br>
												Ϊ
											</td>
											<td rowspan="3" class="Content" width="80">
												<select name="savedata" size="9" multiple class="Next_pulls"
													style="width: 80px">
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
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="Content_title" rowspan="2"
												style="text-indent: 7px;">
												���ߴ�
											</td>
											<td class="Content" rowspan="2">
												<textarea name="answer_content" cols="51" rows="4"
													class="Text"></textarea>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												���״̬
											</td>
											<td class="Content" width="85">
												<select name="dict_is_answer_succeed" class="Next_pulls">
													<jsp:include flush="true"
														page="textout.jsp?selectName=dict_is_answer_succeed" />
												</select>
											</td>
											<td class="Content_title" width="60"
												style="text-indent: 8px;">
												�������
											</td>
											<td class="Content" width="105" style="text-align: left;">
												<select name="answer_man" class="Next_pulls"
													style="width: 88px;">
													<jsp:include flush="true"
														page="textout.jsp?selectName=answer_man" />
												</select>
											</td>
										</tr>
										<tr>
											<td class="Content_title" style="text-indent: 10px;">
												�Ƿ�ط�
											</td>
											<td class="Content">
												<select name="dict_is_callback" class="Next_pulls"
													style="width: 75px;">
													<option>
														��
													</option>
													<option>
														��
													</option>
												</select>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												�ط�ʱ��
											</td>
											<td class="Content" style="text-align: left;">
												<input name="callback_time" type="text"
													onclick="openCal('openwin','callback_time',false);"
													value='<%=request.getAttribute("date")%>' size="9"
													class="Text">
												<img alt="ѡ������" src="../html/img/cal.gif"
													onclick="openCal('openwin','callback_time',false);">
											</td>
										</tr>
									</table>
									<table width="100%" height="100" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="61" class="Content_title"
												style="text-indent: 7px;">
												ͬ������
											</td>
											<td class="Content">
												<input name="textfield242" type="text" value="�����������"
													size="122" onClick="if(this.value=='�����������')this.value=''"
													onpropertychange="search(this.value)" class="Text">
											</td>
										</tr>
										<tr>
											<td width="60" class="Content_title"
												style="text-indent: 7px;">
												�������
											</td>
											<td height="100%">

												<DIV style="width: 100%; height: 100%; overflow-y: auto;"
													id="div1">

													<table width="100%">
														<tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'"
															onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';"
															class="Content">
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
				<tr class="Blue_title" align="right">
					<td>
						<input type="submit" name="Submit" value="ȷ ��"
							onclick="return check()" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value="�� ��" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value="�� ��"
							onclick="window.close();" />
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
	
	//�༩ҳ����صĲ�ͬҳ��
	var v_edit_history_value="������ѯ";//��ʷ��¼��Ĭ��ֵ����������ѯ��
	
	function edit(v){
		var text = new Array("text1.jsp","text2.jsp","text3.jsp","text4.jsp","textyiliao.jsp","textqiye.jsp");
	
		if(v == "�۸���"){//�۸��ʽ
			v_edit_history_value = v;
			//sendRequest("text1.jsp", null, processResponse);
			sendRequest(text[0], null, processResponse);
		}else if(v == "���󷢲�"){//�����ʽ
			v_edit_history_value = v;
			//sendRequest("text2.jsp", null, processResponse);
			sendRequest(text[1], null, processResponse);
		}else if(v == "���ߵ���"){
			v_edit_history_value = v;	
			//sendRequest("text4.jsp", null, processResponse);
			sendRequest(text[3], null, processResponse);
		}else if(v=="ҽ�Ʒ���"||v=="��ũͨ"){			
			//text = "textyiliao.jsp";					
			if(v_edit_history_value!="ҽ�Ʒ���"&&v_edit_history_value!="��ũͨ"){
				v_edit_history_value = v;
				sendRequest(text[4], null, processResponse);
			}			
		}else if(v=="��ҵ����"){
			v_edit_history_value = v;
			//text = "textqiye.jsp";
			sendRequest(text[5], null, processResponse);
		}else{//�����ʽ
			if(v_edit_history_value=="�۸���"||v_edit_history_value=="���󷢲�"||v_edit_history_value=="���ߵ���"||v_edit_history_value=="ҽ�Ʒ���"||v_edit_history_value=="��ũͨ"||v_edit_history_value=="��ҵ����"){
				v_edit_history_value = v;
				sendRequest(text[2], null, processResponse);
			}
		}

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
				document.getElementById("selectspan").innerHTML = "<select name='dict_question_type3' class='Next_pulls'>"+res+"</select>";
                
            } else { //ҳ�治����
                //window.alert("���������ҳ�����쳣��");
            }
        }
	}
	function processResponse4() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				document.getElementById(producttd).innerHTML = "<select name='dict_product_type1' class='Next_pulls'>"+res+"</select>";
                
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
				document.getElementById(producttd2).innerHTML = "<select name='dict_product_type2' class='Next_pulls'  onChange='select1(this)'><OPTION>��ѡ��С��</OPTION>"+res+"</select>";
                
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
				document.getElementById(producttd2).innerHTML = "<select name='product_name' class='Next_pulls'><OPTION>��ѡ������</OPTION>"+res+"</select>";
                
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
		tr.style.textAlign = "center";
		tr.height = "26";
		if(rowindex % 2 == 1){
			tr.className = "Content";
		}else{
			tr.className = "Content_title";
		}
		
		td = tr.insertCell();
		td.innerHTML = '<select name="dict_product_type1" class="Next_pulls" onChange="select1(this)"><option value="">��ѡ�����</option><option value="����">����</option><option value="����">����</option><option value="����">����</option><option value="��������">��������</option><option value="��ʳ����">��ʳ����</option><option value="��������">��������</option><option value="ʳ�þ�">ʳ�þ�</option><option value="�߲�">�߲�</option><option value="ˮ����">ˮ����</option><option value="ˮ��">ˮ��</option><option value="������ֳ">������ֳ</option><option value="ҩ��">ҩ��</option><option value="��������">��������</option></select>';
		td = tr.insertCell();
		td.id = "dict_product_type2_td_"+tdindex;
		td.innerHTML = '<select name="dict_product_type2" class="Next_pulls"  onChange="select1(this)"><OPTION>��ѡ��С��</OPTION></select>';
		td = tr.insertCell();
		td.id = "product_name_td_"+tdindex;
		td.innerHTML = '<select name="product_name" class="Next_pulls"><OPTION>��ѡ������</OPTION></select>';
		td = tr.insertCell();
		td.innerHTML = '<select name="dict_price_type" class="Next_pulls">'+ selectStr +'</select>';
		td = tr.insertCell();
		td.innerHTML = '<input name="product_price" type="text" class="Text" id="product_price">';
		td = tr.insertCell();
		td.innerHTML = '<input name="remarkj" type="text" class="Text" id="remarkj" size="35">';
		td = tr.insertCell();
		td.innerHTML = '<input name="del" type="button" id="del" value="ɾ��" onClick="deltr(this)" style="BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #002D96 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #002D96 1px solid;" >';
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
