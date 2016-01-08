<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
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
<title>��˲���</title>
<script type="text/javascript">
	
	var url = "flow.do?method=toFlowOper&type=";
	//���
   	function add(){  
   		document.forms[0].action = url + "insert";
		document.forms[0].submit();
   	}
   	//�޸�
   	function update(){
   		document.forms[0].action = url + "update";
		document.forms[0].submit();
   	}
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
   	
   	function link(){
		var type_id_obj = document.forms[0].type_id;
		var type_id = type_id_obj.options[type_id_obj.selectedIndex].value;
		var entry_id = document.forms[0].entry_id.value;
		var link = "";
		if(isNaN(entry_id)){
			var entry_id_str = entry_id.substring(0,entry_id.length-11);
			entry_id_str = entry_id_str.toLowerCase();		//ת��Сд
			
			if(entry_id_str == "oper_caseinfo"){			//����ǰ�����
				if(type_id == "oper_caseinfo_putong"){		//��ͨ����
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_FocusCase"){//����
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_HZCase"){	//����
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_effectCase"){//Ч��
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}
				
			}else if(entry_id_str == "oper_priceinfo"){		//�۸��
				link = "../operpriceinfo.do?method=toOperPriceinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_sadinfo"){		//�����
				link = "../sad.do?method=toSadLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_medicinfo"){		//ҽ�ƿ�
				link = "../medical/medicinfo.do?method=toMedicinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_book_medicinfo"){//ԤԼҽ�ƿ�
				link = "../medical/bookMedicinfo.do?method=toBookMedicinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_corpinfo"){		//��ҵ��
				link = "../operCorpinfo.do?method=toOperCorpinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_focusinfo"){		//����׷�ٿ�
				link = "../focusPursue.do?method=toFocusPursueLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_markanainfo"){	//�г�������
				link = "../markanainfo.do?method=toMarkanainfoLoad&type=detail&id="+entry_id;
			}

			link = "[ <a href="+link+" target=_blank>�鿴</a> ]";
		}else{
			link = "��������";
		}
		//��ID��߲�������
		document.getElementById("link").innerHTML = link;
   	}
	
</script>
<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="link()">
<html:form action="/flow/flow" method="post">

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="flow_id"/>
    <td class="labelStyle">�������Ͽ�</td>
    <td class="valueStyle">
      <html:select property="type_id" styleClass="selectStyle">
		<html:option value="oper_caseinfo_putong">��ͨ������</html:option>
		<html:option value="oper_caseinfo_FocusCase">���㰸����</html:option>
		<html:option value="oper_caseinfo_HZCase">���ﰸ����</html:option>
		<html:option value="oper_caseinfo_effectCase">Ч��������</html:option>
		<html:option value="oper_priceinfo">ũ��Ʒ�۸��</html:option>
		<html:option value="oper_sadinfo">ũ��Ʒ�����</html:option>
		<html:option value="oper_medicinfo">ҽ����Ϣ��</html:option>
		<html:option value="oper_book_medicinfo">ԤԼҽ����Ϣ��</html:option>
		<html:option value="oper_corpinfo">��ҵ��Ϣ��</html:option>
		<html:option value="oper_focusinfo">����׷�ٿ�</html:option>
		<html:option value="oper_markanainfo">�г�������</html:option>
      </html:select>
    </td>
    <td class="labelStyle">���Ͽ�ID</td>
    <td class="valueStyle"><html:text property="entry_id" styleClass="readTextStyle"/>
    <span id="link"></span>
    </td>
  </tr>
  <tr><% String date = (String)request.getAttribute("date"); %>
    <td class="labelStyle">�ύ��ID</td>
    <td class="valueStyle"><html:text property="submit_id" styleClass="readTextStyle" readonly="true"/></td>
    <td class="labelStyle">�ύʱ��</td>
    <td class="valueStyle">
    <html:text property="submit_time" onclick = "openCal('flow','submit_time',false);" styleClass="readTextStyle" value="<%= date %>"/>
    <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('flow','submit_time',false);">
    </td>
  </tr>
  <tr>
    <td class="labelStyle">������ID</td>
    <td class="valueStyle"><html:text property="accept_id" styleClass="readTextStyle" readonly="true"/></td>
    <td class="labelStyle">����ʱ��</td>
    <td class="valueStyle">
    <html:text property="handle_time" onclick = "openCal('flow','handle_time',false);" styleClass="readTextStyle" value="<%= date %>"/>
    <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('flow','handle_time',false);">
    </td>
  </tr>
  <tr>
    <td class="labelStyle">��ǰ״̬</td>
    <td colspan="3" class="valueStyle">

	    <html:select property="dict_flow_state" styleClass="selectStyle" onchange="getAccid(this.options[this.selectedIndex].value);">
		  <html:options collection="typelist" property="value" labelProperty="label"/>
		</html:select>
		<span id = accids></span>
��ֻ��״̬�ͱ�ע�ǿ����޸ĵģ�
    </td>
  </tr>
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3" styleClass="writeTextStyle"/></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" �� �� " onClick="add()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "  class="buttonStyle">
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" �� �� " onClick="update()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "  class="buttonStyle">
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"  class="buttonStyle">
&nbsp;&nbsp;&nbsp;&nbsp;	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"  class="buttonStyle">
&nbsp;&nbsp;&nbsp;    </td>
  </tr>
</table>

</html:form>
</body>
</html>
<script>

	function getAccid(v){
		sendRequest("../focusPursue/getAccid.jsp", "state="+v);
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
	function sendRequest(url,value) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//ָ����Ӧ����
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // ��������
	}
	// ��������Ϣ����
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("accids").innerHTML = res;
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

</script>
