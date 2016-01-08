<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>����Ա���۲���</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language="javascript" src="../js/form.js" type=text/javascript>
</SCRIPT>
   <!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- end jquery��֤ -->
	<script type="text/javascript">
        //��ʼ��
		function init(){
			<logic:equal name="opertype" value="insert">
    				document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.forms[0].action = "../linkmanpriceinfo.do?method=toOperPriceinfo&type=insert";
					document.getElementById('btnOper').value="���";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../linkmanpriceinfo.do?method=toOperPriceinfo&type=update";
					document.getElementById('btnOper').value="�޸�";
					document.getElementById('btnOper').style.display="inline";
					document.getElementById('chooseLink').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../linkmanpriceinfo.do?method=toOperPriceinfo&type=delete";
					document.getElementById('btnOper').value="ɾ��";
					document.getElementById('btnOper').style.display="inline";
					
   			</logic:equal>
   			//toback();
		}
		//ִ����֤
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#priceRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#custName").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
		})
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
			
		function checkTelNumber(theField) {
		 	var pattern = /(^(\d{2,4}[-_����]?)?\d{3,8}([-_����]?\d{3,8})?([-_����]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;
		
		 	if(theField.value == "") return true;
		 	if (!pattern.test(theField.value)) {
		 		alert("����ȷ��д�绰���룡");
		 		theField.focus();
		 		theField.select();
		 		return false;
		 	}
		
			return true;
		}	
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.priceRid,"������")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
        	if (!checkTelNumber(addstaffer.custTel)) return false;

           return true;
        }
				function add()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
				 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{
			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();
				opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
			}
		}
		
		function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].custName;
			arrparm[1] = document.forms[0].cust_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/linkmanpriceinfo.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }		
		
			</script>

</head>
<body onunload="toback()" class="loadBody" onload="init()" >
	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/linkmanpriceinfo.do" method="post" styleId="formValidate" >

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				��ǰλ��&ndash;&gt;
				<span id="spanHead">��ϸ</span>
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		�����Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
<%--		    		�鿴��Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		�޸���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		ɾ����Ϣ--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>

				<td width="70" class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<html:text property="priceRid" style="width:87px" styleClass="writeTextStyle"
						size="10" readonly="true" styleId="priceRid" />
<%--					<font color="#ff0000">*</font>--%>
					<span id="priceRidTip" style="width: 10px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					����ר��
				</td>
				<td class="valueStyle">
					<html:text property="priceExpert" styleClass="writeTextStyle"
						style="width:87px" />
				</td>
				<td class="labelStyle">
					�޸�״̬
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../flow/incState.jsp?form=linkmanpriceinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../flow/incState.jsp?form=linkmanpriceinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../flow/incState.jsp?form=linkmanpriceinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../flow/incState.jsp?form=linkmanpriceinfoBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=priceinfoBean"/>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����Ա����
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle"
						style="width:87px" styleId="custName"/>
					<img id="chooseLink" src="../style/<%=styleLocation%>/images/detail.gif" alt="ѡ������Ա" onclick="dep()" width="16" height="16" border="0" style="display:none"/>
					<html:hidden property="cust_id" />
					<span id="custNameTip" style="width: 10px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					����Ա�绰
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" style="width:87px" />
				</td>
				<td class="labelStyle">
					����Ա��ַ
				</td>
				<td class="valueStyle">
					<html:text property="custAddr" styleClass="writeTextStyle2"
						style="width:100px" readonly="true"/>
					<html:button property="button" value="ѡ��" style="width:30px;display:none"
						styleClass="buttonStyle"
						onclick="window.open('linkmanPriceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
						onmousemove="this.style.cursor='pointer';" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��Ʒ����
				</td>
				<td class="valueStyle">
				<%
				String dpt = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("linkmanpriceinfoBean")).get("dictProductType1");
				%>
					<html:select property="dictProductType1" styleClass="selectStyle" onchange="select1(this)">
						<OPTION value="<%=dpt %>"><%=dpt %></OPTION>
          				<jsp:include flush="true" page="select_product.jsp" />
        			</html:select>
				</td>
				<td class="labelStyle">
					��ƷС��
				</td>
				<td class="valueStyle">
					<span id="dict_product_type2_span">
						<html:text property="dictProductType2" styleClass="writeTextStyle" style="width:87px" />
					</span>
				</td>
				<td class="labelStyle">
					��Ʒ����
				</td>
				<td class="valueStyle">
					<span id="product_name_span">
						<html:text property="productName" styleClass="writeTextStyle" style="width:155px" />
					</span>
				</td>
			</tr>
			<tr>

				<td class="labelStyle">
					��Ʒ�۸�
				</td>
				<td class="valueStyle">
					<html:text property="productPrice" style="width:87px" styleClass="writeTextStyle" />
				</td>
				
				<td class="labelStyle">
					�۸�����
				</td>
				<td class="valueStyle">
					<html:select property="dictPriceType" styleClass="selectStyle" style="width:87px">
						<html:option value="">
	    				��ѡ��
	    				</html:option>
						<html:options collection="priceList" property="value"
							labelProperty="label" />

					</html:select>
				</td>
				<td class="labelStyle">
					�۸�λ
				</td>
				<td class="valueStyle">
					<html:text property="priceUnit" styleClass="writeTextStyle" style="width:155px" />
				</td>
			</tr>
			<tr>
			<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="deployTime" styleClass="writeTextStyle" style="width:87px" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('linkmanpriceinfoBean','deployTime',false);">
				</td>
				
				<td class="labelStyle" colspan="3">
					��Ʒ���
				</td>
				<td class="valueStyle">
					<html:text property="productScale" styleClass="writeTextStyle" style="width:155px" />
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;ע
				</td>
				<td class="valueStyle" colspan="6">
					<html:textarea property="remark" styleClass="writeTextStyle"
						cols="84" rows="5" />
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
					<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="��ϸ"  class="buttonStyle"/>
				</logic:present>
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="���" class="buttonStyle"--%>
<%--							onclick="add()" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update" >--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="ȷ��" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="ɾ��" onclick="del()" class="buttonStyle" />--%>
<%--					</logic:equal>--%>

					<input type="button" name="" value="�ر�"  
						onClick="javascript:window.close();"  class="buttonStyle"/>
				</td>
			</tr>
			<html:hidden property="priceId" />
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
	
	function getAccid(v){
		sendRequest("../focusPursue/getAccid.jsp", "state="+v, processResponse);
	}
	
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
	
	function processResponse1() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("dict_product_type2_span").innerHTML = "<select name='dictProductType2' class='selectStyle'  onChange='select1(this)'><OPTION value=''>��ѡ��С��</OPTION>"+res+"</select>";
                document.getElementById("product_name_span").innerHTML = "<select name='productName' class='selectStyle'><OPTION  value=''>��ѡ������</OPTION></select>";                														 
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

