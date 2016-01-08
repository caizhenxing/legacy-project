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
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
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
		<!-- jquery��֤ -->
		<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
		<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
		<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
		<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
		   
		<script type="text/javascript">
			var v_flag="";
			function formAction(){
				if(v_flag=="del"){
					if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
						return true;
					else
						return false;
				}
			}
			
			//��ʼ��
			function init(){	
				<logic:equal name="opertype" value="detail">
					document.getElementById('buttonSubmit').style.display="none";
				</logic:equal>		
				<logic:equal name="opertype" value="insert">
					document.forms[0].action = "question.do?method=toQuestionOper&type=insert";
					//document.getElementById('spanHead').innerHTML="���";
					document.getElementById('buttonSubmit').value="�� ��";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "question.do?method=toQuestionOper&type=update";
					//document.getElementById('spanHead').innerHTML="�޸�";
					document.getElementById('buttonSubmit').value="�� ��";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "question.do?method=toQuestionOper&type=delete";
					//document.getElementById('spanHead').innerHTML="ɾ��";
					document.getElementById('buttonSubmit').value="ɾ ��";
					v_flag="del"
				</logic:equal>		
			}
			//ִ����֤
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"question",onerror:function(msg){alert(msg)}});	
				$("#question_content").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});	
				$("#answer_content").formValidator({onshow:"��������ѯ��",onfocus:"��ѯ�𰸲���Ϊ��",oncorrect:"��ѯ�𰸺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�����߲����пշ���"},onerror:"��ѯ�𰸲���Ϊ��"});	
			
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"question",onerror:function(msg){alert(msg)}});	
				$("#question_content").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});	
				$("#answer_content").formValidator({onshow:"��������ѯ��",onfocus:"��ѯ�𰸲���Ϊ��",oncorrect:"��ѯ�𰸺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�����߲����пշ���"},onerror:"��ѯ�𰸲���Ϊ��"});	
			
			})
			</logic:equal>		
		
<%--		function checkForm(question){--%>
<%--            if (!checkNotNull(question.question_content,"��ѯ����")) return false;--%>
<%--            if (!checkNotNull(question.answer_content,"��ѯ��")) return false;            --%>
<%--            return true;--%>
<%--   		}--%>
<%--		var url = "question.do?method=toQuestionOper&type=";--%>
<%--		//���--%>
<%--	   	function add(){  --%>
<%--	   		var f =document.forms[0];--%>
<%--	    	if(checkForm(f)){--%>
<%--		   		document.forms[0].action = url + "insert";--%>
<%--				document.forms[0].submit();--%>
<%--			}--%>
<%--	   	}--%>
<%--	   	//�޸�--%>
<%--	   	function update(){--%>
<%--	   		var f =document.forms[0];--%>
<%--	    	if(checkForm(f)){--%>
<%--		   		document.forms[0].action = url + "update";--%>
<%--				document.forms[0].submit();--%>
<%--			}--%>
<%--	   	}--%>
<%--	   	//ɾ��--%>
<%--	   	function del(){--%>
<%--			if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
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
						��ǰ��ѯ��Ϣ ��ѯ��Ŀ
						<html:hidden property="id" />
						<html:select property="dict_question_type1"
							styleClass="selectStyle">
							<html:option value="������ѯ">������ѯ</html:option>
							<html:option value="��ֲ��ѯ">��ֲ��ѯ</html:option>
							<html:option value="��ֳ��ѯ">��ֳ��ѯ</html:option>
							<html:option value="��Ŀ��ѯ">��Ŀ��ѯ</html:option>
							<html:option value="������ѯ">������ѯ</html:option>
							<html:option value="�ش��¼��ϱ�">�ش��¼��ϱ�</html:option>
							<html:option value="��Ϣ����">��Ϣ����</html:option>
							<html:option value="��ũͨ">��ũͨ</html:option>
							<html:option value="��ҵ����">��ҵ����</html:option>
							<html:option value="ҽ�Ʒ���">ҽ�Ʒ���</html:option>
							<html:option value="�۸�����">�۸�����</html:option>
							<html:option value="�۸���">�۸���</html:option>
							<html:option value="���󷢲�">���󷢲�</html:option>
							<html:option value="���ߵ���">���ߵ���</html:option>
						</html:select>
						����ר��
							<html:select styleId="bill_num" property="bill_num" styleClass="writeTypeStyle"
									onchange="selecttype1()" style="width:130px">
									<html:option value="0">��ѡ��ר��</html:option>
									<html:options collection="expertTypeList" property="value"
										labelProperty="label" styleClass="writeTypeStyle" />
									<html:option value="0">��ũ����</html:option>
							</html:select>
							<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName)){
										out.print("<option value="+rExpertName+">*"+rExpertName+"</option>");
									}else{
										out.print("<option value=\"0\">ѡ��ר��</option>");
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
											<td class="labelStyle">��ѯ����</td>
											<td width="40%" colspan="3" class="valueStyle">
												<html:textarea property="question_content" cols="44"
													styleClass="writeTextStyle" styleId="question_content"/>
												<div id="question_contentTip" style="width: 10px;display:inline;"></div>	
											</td>
										</tr>
										<tr>
											<td class="labelStyle">��ѯ��</td>
											<td colspan="3" class="valueStyle">
												<html:textarea property="answer_content" cols="44" rows="3"
													styleClass="writeTextStyle" styleId="answer_content"/>
												<div id="answer_contentTip" style="width: 10px;display:inline;"></div>
											</td>
										</tr>
										<tr>
											<td width="25%" align="left" class="labelStyle">�������</td>
											<td colspan="3" width="75%" class="valueStyle">
												<html:select property="dict_question_type2"
													styleClass="selectStyle" onchange="selecttype(this)">
													<html:option value="">��ѡ�����</html:option>
													<html:option value="��������">��������</html:option>
													<html:option value="��������">��������</html:option>
													<html:option value="�߲�">�߲�</html:option>
													<html:option value="ҩ��">ҩ��</html:option>
													<html:option value="����">����</html:option>
													<html:option value="��ƺ���ر�">��ƺ���ر�</html:option>
													<html:option value="����">����</html:option>
													<html:option value="����">����</html:option>
													<html:option value="����">����</html:option>
													<html:option value="����">����</html:option>
													<html:option value="Ϻ/з/��/��/��/��/�ݱ���������">Ϻ/з/��/��/��/��/�ݱ���������</html:option>
													<html:option value="������ֳ">������ֳ</html:option>
													<html:option value="������ʩ����������">������ʩ����������</html:option>
													<html:option value="���߷��漰����">���߷��漰����</html:option>
													<html:option value="����">����</html:option>
												</html:select>
												<br>
												<span id="selectspan"> <html:text
														property="dict_question_type3" styleClass="writeTextStyle" />
												</span>
												<br>
												<select name="dict_question_type4" class="selectStyle">
													<option>Ʒ�ֽ���</option>
													<option>�������</option>
													<option>��ֳ����</option>
													<option>�߲�����</option>
													<option>��ݷ���</option>
													<option>�ջ�����</option>
													<option>��Ʒ�ӹ�</option>
													<option>�г�����</option>
													<option>��������</option>
													<option>ũ��ʹ��</option>
													<option>��ʩ�޽�</option>
													<option>���߹���</option>
													<option>�����ۺ�	</option>
												</select>
											</td>
										</tr>
										<tr>
											<td width="25%" class="labelStyle">���״̬</td>
											<td width="25%" class="valueStyle">
												<html:select property="dict_is_answer_succeed"
													styleClass="selectStyle">
													<html:options collection="dict_is_answer_succeed"
														property="value" labelProperty="label" />
												</html:select>
											</td>
											<td width="25%" class="labelStyle">�����ʽ</td>
											<td width="25%" class="valueStyle">
												<html:select property="answer_man" styleClass="selectStyle">
													<html:options collection="answer_man" property="value"
														labelProperty="label" />
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="labelStyle">�Ƿ�ط�</td>
											<td colspan="3" class="valueStyle">
												<html:select property="dict_is_callback"
													styleClass="selectStyle">
													<html:option value="��">��</html:option>
													<html:option value="��">��</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="labelStyle">�ط�ʱ��</td>
											<td colspan="3" class="valueStyle">
												<%
															String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
															.format(new java.util.Date());
												%>
												<html:text property="callback_time"
													onclick="openCal('question','callback_time',false);"
													size="10" value="<%=date%>" styleClass="writeTextStyle" />
												<img alt="ѡ������" src="../html/img/cal.gif"
													onclick="openCal('question','callback_time',false);">
											</td>
										</tr>
										<tr class="buttonAreaStyle">
											<td colspan="4" align="center">
<%--												<logic:equal name="opertype" value="insert">--%>
<%--													<input type="button" name="Submit" value="�� ��"--%>
<%--														  onClick="add()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="reset" name="Submit2" value="�� ��"--%>
<%--														 class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="update">--%>
<%--													<input type="button" name="Submit" value="ȷ ��"--%>
<%--														  onClick="update()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="reset" name="Submit2" value="�� ��"--%>
<%--														 class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="delete">--%>
<%--													<input type="button" name="Submit" value="ɾ ��"--%>
<%--														  onClick="del()" class="buttonStyle"/>--%>
<%--			      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			      <input type="button" name="Submit" value="�� ��"--%>
<%--														  onClick="javascript:window.close()" class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
<%----%>
<%--												<logic:equal name="opertype" value="detail">--%>
<%--													<input type="button" name="Submit" value="�� ��"--%>
<%--														  onClick="javascript:window.close()" class="buttonStyle"/>--%>
<%--												</logic:equal>--%>
											<input type="submit" name="button" id="buttonSubmit" value="�ύ"  class="buttonStyle"/>
      										&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" name="Submit" value="�� ��" onClick="javascript:window.close()" class="buttonStyle"/>
											</td>
										</tr>
									</table>
								</td>
								<td width="50%" class="valueStyle">
									<table width="100%" height="100%" border="0">
										<tr>
											<td>
												<input name="textfield242" type="text" value="�����������"
													size="60" onClick="if(this.value=='�����������')this.value=''"
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
															<td width="100%" class="valueStyle">��������б�</td>
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
										<td class="valueStyle" width="20%">�طü�¼</td>
										<td class="valueStyle" width="40%">
											�ط��Ƿ�ɹ�
											<html:select property="is_callback_succ"
												styleClass="selectStyle">
												<html:option value="�ɹ�">�ɹ�</html:option>
												<html:option value="���ɹ�">���ɹ�</html:option>
											</html:select>
										</td>
										<td class="valueStyle" colspan="2">
											<logic:equal name="isAdd" value="yes">
												<input name="btnAdd" type="button"  
													value="��ӻط�"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=insert&qid='+document.forms[0].id.value,'','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="listTitleStyle" width="30%">�ط�����</td>
										<td class="listTitleStyle" width="40%">�طñ�ע</td>
										<td class="listTitleStyle" width="10%">�ط�ʱ��</td>
										<td class="listTitleStyle" width="11%">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
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

												<img alt="��ϸ"
													src="../style/<%=styleLocation%>/images/detail.gif"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=detail&id=<bean:write name='pagelist' property='id'/>','','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
												<img alt="�޸�"
													src="../style/<%=styleLocation%>/images/update.gif"
													onclick="window.open('../callback/callback.do?method=toCallbackLoad&type=update&id=<bean:write name='pagelist' property='id'/>','','width=560,height=330,status=no,resizable=yes,scrollbars=no,top=200,left=280')" />
												<img alt="ɾ��"
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

	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("div1").innerHTML = res;
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
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
                window.alert("���������ҳ�����쳣��");
            }
        }
	}
			function selecttype1(){
		//ר�����id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//��̬���ɵ�select id Ϊ expert_name
		}
</script>
</html>