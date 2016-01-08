<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<html:base />
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			var address=document.forms[0].address.value;			
<%--			if(address==null||address==""){--%>
<%--				alert("��ַ����Ϊ�գ�")--%>
<%--				return false;--%>
<%--			}--%>
			document.forms[0].action="../stat/priceStatForProduct.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/priceStatForProduct.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
			var time = new Date();
			document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
			time.setYear(time.getYear()-1);
			document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
		}
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
				//window.alert(res); sdfsf
				document.getElementById("product_name_span").innerHTML = "<select name='productName' class='selectStyle'><OPTION  value=''>��ѡ������</OPTION>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}
		
		</script>
	</head>
	<body class="conditionBody" onload="setTime()">
		<html:form action="/stat/priceStatForProduct" method="post"
			target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;���ز�Ʒ��ֵ/��ֵͳ��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('priceStatForProductForm','beginTime',false);"
							size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('priceStatForProductForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��Ʒ����
					</td>
					<td class="valueStyle">
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
					<td class="LabelStyle">
						����
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle"
							style="width:80px">
							<html:option value="">���</html:option>
							<html:option value="bar">��ͼ</html:option>
							<html:option value="pie">��ͼ</html:option>
							<html:option value="line">����ͼ</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()"
							styleClass="checkBoxStyle">3Dͼ��</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch"  class="buttonStyle" value="ͳ��"
							onclick="dodisplay()" />

					</td>
				</tr>

				<tr>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('priceStatForProductForm','endTime',false);"
							size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
							onclick="openCal('priceStatForProductForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ַ
					</td>
					<td class="valueStyle" >
						<html:text property="address" size="30"
							styleClass="writeTextStyle" />
						<input type="button" value="ѡ���ַ"
							onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
<%--						<font color="red">*</font>--%>
					</td>
					<td class="LabelStyle">
						�۸�����
					</td>
					<td class="valueStyle" >						
						<html:select property="productType" styleClass="selectStyle">
							<html:option value="">��ѡ��</html:option>
							<html:option value="SYS_TREE_0000000627">�չ���</html:option>
							<html:option value="SYS_TREE_0000000628">������</html:option>
							<html:option value="SYS_TREE_0000000629">���ۼ�</html:option>
						</html:select>
					</td>
					<td class="LabelStyle" align="center">
						<input class="buttonStyle" type="reset" value="ˢ��">
					</td>
				</tr>

				<tr height="1px">
					<td colspan="10" class="buttonAreaStyle">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
<%--<script>
	
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

</script>--%>
