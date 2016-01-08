<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

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
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
</head>

<body class="loadBody">

<html:form method="post" action="/custinfo/openwin.do?method=toOpenwinOper">

<table width="800" border="0" align="center" class="navigateTable">
  <tr>
    <td colspan="2"><table width="100%" border="0">
      <tr>
        <td width="13%" class="labelStyle">�û�������Ϣ </td>
        <td colspan="7" width="88%" class="valueStyle">
        ������룺<input name="tel" type="text" size="13" value='<%= request.getAttribute("tel") %>' class="writeTextStyle">
        <html:hidden property="cust_id"/>
        </td>
      </tr>
      <tr align="center">
        <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ��</td>
        <td class="valueStyle"><html:text property="cust_name" size="13" styleClass="writeTextStyle"/></td>
        <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ��</td>
        <td class="valueStyle">
        <html:select property="dict_sex" styleClass="selectStyle">
          <html:options collection="sexList" property="value" labelProperty="label"/>
        </html:select>
        </td>
        <td class="labelStyle">E_mail</td>
        <td class="valueStyle"><html:text property="cust_email" size="13" styleClass="writeTextStyle"/></td>
        <td rowspan="4" class="labelStyle">��<br><br>&nbsp;&nbsp;&nbsp;ע</td>
        <td rowspan="4" class="valueStyle"><html:textarea property="remark" cols="15" rows="5" styleClass="writeTextStyle"/></td>
      </tr>
      <tr align="center">
        <td class="labelStyle">סլ�绰</td>
        <td class="valueStyle"><html:text property="cust_tel_home" size="13" styleClass="writeTextStyle" onclick="OR(this)"/></td>
        <td class="labelStyle">�칫�绰</td>
        <td class="valueStyle"><html:text property="cust_tel_work" size="13" styleClass="writeTextStyle" onclick="OR(this)"/></td>
        <td class="labelStyle">�ƶ��绰</td>
        <td class="valueStyle"><html:text property="cust_tel_mob" size="13" styleClass="writeTextStyle"/></td>
        </tr>
      <tr align="center">
        <td class="labelStyle" onClick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
        onMouseOver="this.style.cursor='pointer';" >
        <b>ѡ���ַ</b></td>
        <td class="valueStyle"><html:text property="cust_addr" size="13" styleClass="writeTextStyle"/></td>
        <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ��</td>
        <td class="valueStyle"><html:text property="cust_pcode" size="13" styleClass="writeTextStyle"/></td>
        <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ��</td>
        <td class="valueStyle"><html:text property="cust_fax" size="13" styleClass="writeTextStyle"/></td>
        </tr>
      <tr align="center">
        <td class="labelStyle">�û���ҵ</td>
        <td class="valueStyle"><html:text property="cust_voc" size="13" styleClass="writeTextStyle"/></td>
        <td class="labelStyle">��ҵ��ģ</td>
        <td class="valueStyle"><html:text property="cust_scale" size="13" styleClass="writeTextStyle"/></td>
        <td class="labelStyle">�û�����</td>
        <td class="valueStyle">
		  <html:select property="cust_type" styleClass="writeTextStyle">
	        <html:options collection="typeList" property="value" labelProperty="label"/>
	      </html:select>
		</td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" border="0">
      <tr>
        <td class="valueStyle">��ʷ��ѯ��Ϣ</td>
      </tr>
      <tr>
        <td height="80"><IFRAME src='openwin.do?method=toQuestionList&tel=<%= request.getAttribute("tel") %>' frameborder="0" width="100%" height="100%"></IFRAME></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" border="0" height="100%">
      <tr>
        <td width="12%" class="labelStyle">��ǰ��ѯ��Ϣ</td>
        <td width="88%" class="valueStyle">��ѯ��Ŀ
          <select name="dict_question_type1" onChange="edit(this.options[this.selectedIndex].value);" class="selectStyle">
            <option value="������ѯ">������ѯ</option>
            <option value="��ֲ��ѯ">��ֲ��ѯ</option>
            <option value="��ֳ��ѯ">��ֳ��ѯ</option>
            <option value="��Ŀ��ѯ">��Ŀ��ѯ</option>
            <option value="������ѯ">������ѯ</option>
            <option value="�ش��¼��ϱ�">�ش��¼��ϱ�</option>
            <option value="��Ϣ����">��Ϣ����</option>
			<option value="��ũͨ">��ũͨ</option>
            <option value="��ҵ����">��ҵ����</option>
            <option value="ҽ�Ʒ���">ҽ�Ʒ���</option>
            <option value="�۸�����">�۸�����</option>
            <option value="�۸���">�۸���</option>
			<option value="���󷢲�">���󷢲�</option>
            <option value="���ߵ���">���ߵ���</option>
          </select>
          ����ר��
          <select name="bill_num"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=expertType"/>
          </select>
          
        </td>
      </tr>
      <tr>
        <td height="80" colspan="2" id="edit"><table width="100%" height="100%" border="0">
          <tr>
            <td width="50%"><table width="100%" border="0">
              <tr>
                <td align="right" class="labelStyle">��ѯ����</td>
                <td width="40%" colspan="3" class="valueStyle"><textarea name="question_content" cols="47" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">��ѯ��</td>
                <td colspan="3" class="valueStyle"><textarea name="answer_content" cols="47" rows="3" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">�������</td>
                <td colspan="3" width="75%" class="valueStyle">
                <select name="dict_question_type2" class="selectStyle" onChange="selecttype(this)">
                  <option>��ѡ�����</option>
                  <option>��������</option>
                  <option>��������</option>
                  <option>�߲�</option>
                  <option>ҩ��</option>
                  <option>����</option>
                  <option>��ƺ���ر�</option>
                  <option>����</option>
                  <option>����</option>
                  <option>����</option>
                  <option>����</option>
                  <option>Ϻ/з/��/��/��/��/�ݱ���������</option>
                  <option>������ֳ</option>
                  <option>������ʩ����������</option>
                  <option>���߷��漰����</option>
                  <option>����</option>
                </select>
                <br><span id="selectspan">
                <select name="dict_question_type3" class="selectStyle">
                  <option>��ѡ��С��</option>
                  <option>����ѡ����࣬Ȼ��ſ���ѡ��С��</option>
                </select></span>
               <br>
                <select name="dict_question_type4"  class="selectStyle">
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
					<option>�����ۺ�</option>
                </select>
                </td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">���״̬</td>
                <td width="25%" class="valueStyle"><select name="dict_is_answer_succeed"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
                </select></td>
                <td width="25%" align="right" class="labelStyle">�����ʽ</td>
                <td width="25%" class="valueStyle"><select name="answer_man"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
                </select></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">�Ƿ�ط�</td>
                <td class="valueStyle"><select name="dict_is_callback"  class="selectStyle">
                  <option>��</option>
                  <option>��</option>
                </select></td>
                <td align="right" class="labelStyle">���Ϊ</td>
                <td rowspan="2" class="valueStyle">
                <select name="savedata" size="3" multiple  class="selectStyle">
                  <option>��ͨ������</option>
                  <option>���㰸����</option>
                  <option>���ﰸ����</option>
                  <option>Ч��������</option>
                  <option>ũ��Ʒ�۸��</option>
                  <option>ũ��Ʒ�����</option>
                  <option>ר������</option>
                  <option>ҽ����Ϣ��</option>
                  <option>��ҵ��Ϣ��</option>
                </select>
                </td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">�ط�ʱ��</td>
                <td colspan="2" class="valueStyle">
                <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value='<%= request.getAttribute("date") %>' size="10" class="writeTextStyle">
                <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
                </td>
                </tr>
            </table></td>
            <td width="50%" class="valueStyle"><table width="100%" height="100%" border="0">
              <tr>
                <td><input name="textfield242" type="text" value="�����������" size="60" onClick="if(this.value=='�����������')this.value=''" onpropertychange="search(this.value)" class="writeTextStyle"></td>
              </tr>
              <tr>
                <td height="100%">
				
				<DIV style="width:100%;height:100%;overflow-y:auto;" id="div1">
				
				<table width="100%">
				  <tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" class="valueStyle">
				    <td width="100%">��������б�
				    </td>
				  </tr>
				</table>
				
				</Div>
				
				
				</td>
              </tr>
            </table></td>
          </tr>

        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr class="buttonAreaStyle">
  	
    <td><input type="submit" name="Submit" value="�� ��"  >
     &nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" name="Submit2" value="�� ��"  >
     &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="Submit3" value="ȡ ��"  >&nbsp;&nbsp;</td>
    
  </tr>
</table>

</html:form>

</body>

<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
<script language="javascript" type="text/JavaScript">
	var producttd = "";
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
			sendRequest("openwin.do?method=toSearch", "v="+v, processResponse);	
		}
	}
	
	function edit(v){
	
		var text = new Array("text1.jsp","text2.jsp","text3.jsp");
	
		if(v == "�۸�����" || v == "�۸���"){		//�۸��ʽ
			text = text[0];
		}else if(v == "���󷢲�"){					//�⹩���ʽ
			text = text[1];
		}else if(v == "���ߵ���"){					//�⹩���ʽ
			
			//window.showModalDialog("../inquiry.do?method=toFilter","","")
			window.open("../inquiry.do?method=toFilter");
			return null;
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
<script type="text/javascript">

	function addtr(){
		var table = document.getElementById("table");
		var rowindex = table.rows.length-1;
		var tdindex = rowindex-1;
		tr = table.insertRow(rowindex);
		td = tr.insertCell();
		td.className = "valueStyle";
		td.innerHTML = '<select name="dict_product_type2_" class="selectStyle" onChange="selecttype(this)"><option value="">��ѡ�����</option><option>��������</option><option>��������</option><option>�߲�</option><option>ҩ��</option><option>����</option><option>��ƺ���ر�</option><option>����</option><option>����</option><option>����</option><option>����</option><option>Ϻ/з/��/��/��/��/�ݱ���������</option><option>������ֳ</option><option>������ʩ����������</option><option>����</option></select>';
		td = tr.insertCell();
		td.className = "valueStyle";
		td.id = "dict_product_type1_td_"+tdindex;
		td.innerHTML = '<select name="dict_product_type1" class="selectStyle"><OPTION>��ѡ��С��</OPTION><OPTION>����ѡ����࣬Ȼ��ſ���ѡ��С��</OPTION></select>';
		td = tr.insertCell();
		td.className = "valueStyle";
		td.innerHTML = '<input name="product_price" type="text" class="writeTextStyle" id="product_price">';
		td = tr.insertCell();
		td.className = "valueStyle";
		td.innerHTML = '<input name="remarkj" type="text" class="writeTextStyle" id="remarkj">';
		td = tr.insertCell();
		td.className = "listTitleStyle";
		td.innerHTML = '<input name="del" type="button" id="del" value="ɾ��" onClick="deltr(this)">';
	}
	function deltr(obj){
		document.getElementById("table").deleteRow(obj.parentNode.parentNode.rowIndex);
	}

</script>
</html>