<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../style.jsp" %>
<html>
	<head>
	<html:base />
		<title></title>
		<link href="../style/<%=styleLocation %>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/effectStat.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/effectStat.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		</script>
	</head>
	<body class="conditionBody" onload="time()">
		<html:form action="/stat/effectCaseInfoProperty" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						��ǰλ��&ndash;&gt;��ϯ�����ͳ��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">

			
<%--				<tr class="conditionoddstyle"><td class="labelStyle">--%>
<%--					��������--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:select property="caseAttr1" styleClass="selectStyle"--%>
<%--						onchange="selecttype(this)">--%>
<%--						<html:option value="">��ѡ�����</html:option>--%>
<%--						<html:option value="��������">��������</html:option>--%>
<%--						<html:option value="��������">��������</html:option>--%>
<%--						<html:option value="�߲�">�߲�</html:option>--%>
<%--						<html:option value="ҩ��">ҩ��</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="��ƺ���ر�">��ƺ���ر�</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="Ϻ/з/��/��/��/��/�ݱ���������">Ϻ/з/��/��/��/��/�ݱ���������</html:option>--%>
<%--						<html:option value="������ֳ">������ֳ</html:option>--%>
<%--						<html:option value="������ʩ����������">������ʩ����������</html:option>--%>
<%--						<html:option value="���߷��漰����">���߷��漰����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--					</html:select>--%>
<%--				</td>--%>
<%--				<td class="labelStyle">--%>
<%--					����С��--%>
<%--				</td>--%>
<%--				<td class="valueStyle" id="caseAttr2_td">--%>
<%--					<html:text property="caseAttr2" styleClass="writeTextStyle" />--%>
<%--				</td>--%>
<%--				<td class="labelStyle">--%>
<%--					��������--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:select property="caseAttr3" styleClass="selectStyle">--%>
<%--						<html:option value="Ʒ�ֽ���">Ʒ�ֽ���</html:option>--%>
<%--						<html:option value="�������">�������</html:option>--%>
<%--						<html:option value="��ֳ����">��ֳ����</html:option>--%>
<%--						<html:option value="�߲�����">�߲�����</html:option>--%>
<%--						<html:option value="��ݷ���">��ݷ���</html:option>--%>
<%--						<html:option value="�ջ�����">�ջ�����</html:option>--%>
<%--						<html:option value="��Ʒ�ӹ�">��Ʒ�ӹ�</html:option>--%>
<%--						<html:option value="�г�����">�г�����</html:option>--%>
<%--						<html:option value="��������">��������</html:option>--%>
<%--						<html:option value="ũ��ʹ��">ũ��ʹ��</html:option>--%>
<%--						<html:option value="��ʩ�޽�">��ʩ�޽�</html:option>--%>
<%--						<html:option value="���߹���">���߹���</html:option>--%>
<%--						<html:option value="�����ۺ�">�����ۺ�</html:option>--%>
<%--					</html:select>--%>
<%--				</td>--%>
<%--				<td class="LabelStyle" align="center">--%>
<%--						<input type="button" name="btnSearch" value="��ѯ"  --%>
<%--							onclick="dodisplay()" />--%>
<%--				</td></tr>--%>
				
					<tr>
					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('effectCaseInfoPropertyForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('effectCaseInfoPropertyForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('effectCaseInfoPropertyForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('effectCaseInfoPropertyForm','endTime',false);">
					</td>
					<td class="LabelStyle">
<%--						��ϯ����--%>
						������
					</td>
					<td class="valueStyle">
						<html:select property="agentNum">
							<option value="">��ѡ��</option>
							<logic:iterate id="u" name="user">
								<html:option value="${u.userId}">${u.userId}</html:option>						
							</logic:iterate>
						</html:select>
					</td>
				
					<td class="LabelStyle">
						����
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3Dͼ��</html:checkbox>
					</td>
					
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch" value="ͳ��"  
							onclick="dodisplay()" />
						<input type="reset" value="ˢ��"  >
					</td>
				</tr>
				<tr height="1px">
					<td colspan="9" class="buttonAreaStyle">
					</td>				
				</tr>
			</table>
		</html:form>
	</body>
</html>
<script>
	
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "��ѡ�����"){
			sendRequest("../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
		}else{
			document.getElementById("caseAttr2_td").innerHTML = '<input type="text" name="caseAttr2" value="" class="writeTextStyle">';
		}
	}
	
	function getAccid(v){
		sendRequest("../../focusPursue/getAccid.jsp", "state="+v, processResponse);
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
				document.getElementById("accids").innerHTML = res;
                
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
				document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle'>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

</script>