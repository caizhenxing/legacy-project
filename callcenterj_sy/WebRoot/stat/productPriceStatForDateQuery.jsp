<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
			document.forms[0].action="../stat/productPriceForDate.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/productPriceForDate.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
			var time = new Date();
			document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
			time.setYear(time.getYear()-1);
			document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
		}
		</script>
	</head>
	<body class="conditionBody" onload="setTime()">
		<html:form action="/stat/productPriceForDate" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						��ǰλ��&ndash;&gt;ʱ���Ʒ��ֵ/��ֵͳ��
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
<%--				</td>	</tr>--%>
			
					<tr>
					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('productPriceForDateForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('productPriceForDateForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('productPriceForDateForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('productPriceForDateForm','endTime',false);">
					</td>
<%--					<td class="LabelStyle">--%>
<%--						��Ʒ����--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:select property="productName" styleClass="selectStyle">--%>
<%--							<html:option value="">��ѡ��</html:option>--%>
<%--							<html:option value="�޻�">�޻�</html:option>--%>
<%--							<html:option value="С��">С��</html:option>--%>
<%--							<html:option value="֥��">֥��</html:option>--%>
<%--						</html:select>--%>
<%--					</td>--%>
				
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
<%--						<input type="reset" value="ˢ��"  >--%>
					</td>
				</tr>
				<tr>
					<td class="LabelStyle">
						��Ʒ����
					</td>
					<td colspan="5" class="valueStyle">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)">
						<OPTION value="">
							��ѡ�����
						</OPTION>
						<jsp:include flush="true" page="select_product.jsp" />
					</select>
					<span id="dict_product_type2_span"> <select
							name="dictProductType2" class="selectStyle"
							onChange="select1(this)">
							<OPTION value="">
								��ѡ��С��
							</OPTION>
						</select> </span>
					<span id="product_name_span"> <select name="productName"
							class="selectStyle">
							<OPTION value="">
								��ѡ������
							</OPTION>
						</select> </span>
				</td>
				<td class="LabelStyle" align="center">
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

	function select1(obj){
		var sid = obj.name;
		var svalue = obj.options[obj.selectedIndex].text;
		if(svalue == "")
			return;
		if(sid == "dictProductType1"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
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
	
	function processResponse1() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("dict_product_type2_span").innerHTML = "<select name='dictProductType2' class='selectStyle'  onChange='select1(this)'><OPTION value=''>��ѡ��С��</OPTION>"+res+"</select>";
                
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
				document.getElementById("product_name_span").innerHTML = "<select name='productName' class='selectStyle'><OPTION  value=''>��ѡ������</OPTION>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}


</script>