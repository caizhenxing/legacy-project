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
			document.forms[0].action="../stat/agrprice.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/agrprice.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		</script>
		<script>

	function select1(obj){
		var sid = obj.name;
		var svalue = obj.options[obj.selectedIndex].text;
		if(svalue == "")
			return;
		if(sid == "dictProductType1"){
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);

		}else if(sid == "dictProductType2"){
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);

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
	</head>
	<body class="conditionBody" onload="time()">
		<html:form action="/stat/agrprice" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;ũ��Ʒ�۸��ͳ��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="2" class="conditionTable">

				<tr class="conditionoddstyle">

					<td class="LabelStyle">
						��ʼʱ��
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('argPriceForm','beginTime',false);" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('argPriceForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						��ֹʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('telStatForm','endTime',false);"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
					onclick="openCal('argPriceForm','endTime',false);">
					</td>
					<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;Ʒ
				</td>
				<td colspan="3" class="valueStyle">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)">
						<OPTION value="">
							��ѡ�����
						</OPTION>
						<jsp:include flush="true" page="../priceinfo/select_product.jsp" />
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
				
				</tr>
				<tr>
					
					<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td colspan="3" class="valueStyle">
					<html:text property="custAddr" styleClass="writeTextStyle2" style="width:100px" />
					<html:button property="button" value="ѡ��" style="width:30px" styleClass="buttonStyle"
						onclick="window.open('../priceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
						onmousemove="this.style.cursor='pointer';" />
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
						<input class="buttonStyle" type="button" name="btnSearch" value="ͳ��"  
							onclick="dodisplay()" />
							<input class="buttonStyle" type="reset" value="ˢ��"  >
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
