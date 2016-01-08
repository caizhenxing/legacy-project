<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>ũ��Ʒ�۸��</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/common.js"></script>
	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/clockCN.js"></script>


	<script type="text/javascript">
 function add()
 	{
 		document.forms[0].action="../linkmanpriceinfo.do?method=toOperPriceinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../linkmanpriceinfo.do?method=toOperPriceinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 </script>
 <% 
 	String atype = request.getParameter("atype");
 	String curPosition = "����Ա�������";
 	if("dayOfPrice".equals(atype))
 	{
 		curPosition = "ÿ�ռ۸�";
 	}
 %>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/linkmanpriceinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;<%=curPosition %>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('linkmanpriceinfoBean','beginTime',false);">
				</td>
				<td class="queryLabelStyle">
					����Ա����
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle2"
						style="width:100px" size="10"/>
				</td>
				<td class="queryLabelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td  class="valueStyle" colspan="2">
					<html:text property="custAddr" styleClass="writeTextStyle2"
						style="width:140px" size="50" readonly="true"/>
					<html:button property="button" value="ѡ��" style="width:30px"
						styleClass="buttonStyle"
						onclick="window.open('linkmanPriceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
						onmousemove="this.style.cursor='pointer';" />
				</td>
				
				
			</tr>
			<tr>
				<td class="queryLabelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('linkmanpriceinfoBean','endTime',false);">
				</td>
				<td class="queryLabelStyle">
					����Ա���
				</td>
				<td class="valueStyle">
					<html:text property="cust_number" styleClass="writeTextStyle2"
						style="width:100px" size="10"/>
				</td>
				<td class="queryLabelStyle">
					�۸�����
				</td>
				<td class="valueStyle" colspan="2">
					<html:select property="dictPriceType" styleClass="selectStyle" style="width:135px">
						<html:option value="">
	    						��ѡ��
	    					</html:option>
						<html:options collection="priceList" property="value"
							labelProperty="label" />
					</html:select>
				</td>

			</tr>
			<tr>
				<td class="queryLabelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;Ʒ
				</td>
				<td class="valueStyle" colspan="3">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)" style="width:110px">
						<OPTION value="">
							��ѡ�����
						</OPTION>
						<jsp:include flush="true" page="select_product.jsp" />
					</select>
					<span id="dict_product_type2_span"> 
					<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px">
							<OPTION value="">
								��ѡ��С��
							</OPTION>
						</select> </span>
					<span id="product_name_span"> <select name="productName"
							class="selectStyle" style="width:110px">
							<OPTION value="">
								��ѡ������
							</OPTION>
						</select> </span>
				</td>
				<td class="queryLabelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width: 100px;">
						<option value="">ȫ��</option>
						<option>ԭʼ</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
					</select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" value="��ѯ"  class="buttonStyle" onclick="query()" />
					<input type="reset"  class="buttonStyle" value="ˢ��"  >
				</td>
			</tr>
			<tr height="1px">
				<td colspan="11" class="navigateStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
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