<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "question.do?method=toQuestionList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}

	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
   	</script>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">

	<html:form action="/question/question.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;�����¼
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" class="conditionTable">
			<tr class="conditionoddstyle">
				<td class="queryLabelStyle" width="8%">
					��ѯ��Ŀ
				</td>
				<td class="valueStyle" width="25%">

					<html:hidden property="id" />
					<html:select property="dict_question_type1"
						styleClass="selectStyle" style="width:135">
						<html:option value="">ȫ��</html:option>
						<html:option value="������ѯ">������ѯ</html:option>
						<html:option value="��ֲ��ѯ">��ֲ��ѯ</html:option>
						<html:option value="��ֳ��ѯ">��ֳ��ѯ</html:option>
						<html:option value="��Ŀ��ѯ">��Ŀ��ѯ</html:option>
						<html:option value="������ѯ">������ѯ</html:option>
						<html:option value="�ش��¼��ϱ�">�ش��¼��ϱ�</html:option>
						<html:option value="��Ϣ����">��Ϣ����</html:option>
						<html:option value="��ũͨ">��ũͨ</html:option>
						<html:option value="�۸�����">�۸�����</html:option>
						<html:option value="�۸���">�۸���</html:option>
						<html:option value="���󷢲�">���󷢲�</html:option>
						<html:option value="���ߵ���">���ߵ���</html:option>
						<html:option value="��ҵ����">��ҵ����</html:option>
						<html:option value="ҽ�Ʒ���">ҽ�Ʒ���</html:option>
					</html:select>
				</td>

				<td class="queryLabelStyle" width="8%">
					����ר��
				</td>

				<td class="valueStyle" width="25%">
					<html:select property="bill_num" styleClass="selectStyle" style="width:135">
						<html:option value="">ȫ��</html:option>
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=expertType" />
					</html:select>
				</td>
				<td class="queryLabelStyle" width="8%">
					�Ƿ�ط�
				</td>
				<td class="valueStyle" colspan="2"  width="26%">
					<html:select property="dict_is_callback" styleClass="selectStyle" style="width:85">
						<html:option value="">ȫ��</html:option>
						<html:option value="��">��</html:option>
						<html:option value="��">��</html:option>
					</html:select>
				</td>
			</tr>


			<tr>
				<td class="queryLabelStyle">
					��ѯ����
				</td>
				<td class="valueStyle">
					<html:text property="question_content" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					��ѯ��
				</td>
				<td class="valueStyle">
					<html:text property="answer_content" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					���״̬
				</td>
				<td class="valueStyle" colspan="2">
					<html:select property="dict_is_answer_succeed"
						styleClass="selectStyle" style="width:85">
						<html:option value="">ȫ��</html:option>
						<html:options collection="dict_is_answer_succeed" property="value"
							labelProperty="label" />
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="queryLabelStyle">
					�������
				</td>
				<td class="valueStyle" colspan="3">
					<select name="dict_question_type2" class="selectStyle"
						onChange="selecttype(this)">
						<option value="">
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
					<span id="selectspan"> <select name="dict_question_type3"  
							class="selectStyle">
							<option value="">
								��ѡ��С��
							</option>
						</select> </span>
					<select name="dict_question_type4" class="selectStyle">
						<option value="">
							ȫ��
						</option>
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
				<td class="queryLabelStyle">
					�����ʽ
				</td>
				<td class="valueStyle">
					<html:select property="answer_man" styleClass="selectStyle">
						<html:option value="">ȫ��</html:option>
						<html:options collection="answer_man" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button"  
						value="��ѯ" onclick="query()" class="buttonStyle"/>
					<input value="ˢ��" type="reset"  class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="7" class="buttonAreaStyle">
				</td>
			</tr>


		</table>

	</html:form>
</body>
</html:html>
<script>
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		sendRequest("../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
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
				document.getElementById("selectspan").innerHTML = "<select name='dict_question_type3' class='selectStyle'>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

</script>