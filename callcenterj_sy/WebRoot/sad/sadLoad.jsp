<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>�г��������</title>

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
	<script language="javascript" src="../js/clockCN.js"></script>
	<script language="javascript" src="../js/clock.js"></script>

	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
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
			<logic:equal name="opertype" value="detail">
			document.getElementById('btnOper').style.display="none";
			</logic:equal>
			<logic:equal name="opertype" value="insert">
    				document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.forms[0].action = "../sad.do?method=toSadOper&type=insert";
					document.getElementById('btnOper').value="���";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../sad.do?method=toSadOper&type=update";
					document.getElementById('btnOper').value="�޸�";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../sad.do?method=toSadOper&type=delete";
					document.getElementById('btnOper').value="ɾ��";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		
		//ִ����֤
		<logic:equal name="opertype" value="insert">
 		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#sadRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#dictSadType").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�������ͱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#sadTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#custName").formValidator({onshow:"��������ϵ��",onfocus:"��ϵ�˲���Ϊ��",oncorrect:"��ϵ�˺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�����߲����пշ���"},onerror:"��ϵ�˲���Ϊ��"});
			$("#productName").formValidator({onshow:"�������Ʒ����",onfocus:"��Ʒ���Ʋ���Ϊ��",oncorrect:"��Ʒ���ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ʒ�������߲����пշ���"},onerror:"��Ʒ���Ʋ���Ϊ��"});
<%--			$("#deployBegin").formValidator({onshow:"�����뿪ʼ����",onfocus:"��ʼ���ڲ���Ϊ��",oncorrect:"��ʼ���ںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼ�������߲����пշ���"},onerror:"��ʼ���ڲ���Ϊ��"});--%>
			$("#custTel").formValidator({onshow:"��������ϵ�绰",onfocus:"��ϵ�绰����Ϊ��",oncorrect:"��ϵ�绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�绰���߲����пշ���"},onerror:"��ϵ�绰����Ϊ��"});	
			$("#productCount").formValidator({onshow:"�������Ʒ����",onfocus:"��Ʒ��������Ϊ��",oncorrect:"��Ʒ�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ʒ�������߲����пշ���"},onerror:"��Ʒ��������Ϊ��"});
<%--			$("#deployEnd").formValidator({onshow:"�������ֹ����",onfocus:"��ֹ���ڲ���Ϊ��",oncorrect:"��ֹ���ںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ֹ�������߲����пշ���"},onerror:"��ֹ���ڲ���Ϊ��"}).CompareValidator({desID:"deployBegin",operateor:">"});--%>
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#sadRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#dictSadType").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�������ͱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#sadTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#custName").formValidator({onshow:"��������ϵ��",onfocus:"��ϵ�˲���Ϊ��",oncorrect:"��ϵ�˺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�����߲����пշ���"},onerror:"��ϵ�˲���Ϊ��"});
			$("#productName").formValidator({onshow:"�������Ʒ����",onfocus:"��Ʒ���Ʋ���Ϊ��",oncorrect:"��Ʒ���ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ʒ�������߲����пշ���"},onerror:"��Ʒ���Ʋ���Ϊ��"});
<%--			$("#deployBegin").formValidator({onshow:"�����뿪ʼ����",onfocus:"��ʼ���ڲ���Ϊ��",oncorrect:"��ʼ���ںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼ�������߲����пշ���"},onerror:"��ʼ���ڲ���Ϊ��"});--%>
			$("#custTel").formValidator({onshow:"��������ϵ�绰",onfocus:"��ϵ�绰����Ϊ��",oncorrect:"��ϵ�绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�绰���߲����пշ���"},onerror:"��ϵ�绰����Ϊ��"});	
			$("#productCount").formValidator({onshow:"�������Ʒ����",onfocus:"��Ʒ��������Ϊ��",oncorrect:"��Ʒ�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ʒ�������߲����пշ���"},onerror:"��Ʒ��������Ϊ��"});
<%--			$("#deployEnd").formValidator({onshow:"�������ֹ����",onfocus:"��ֹ���ڲ���Ϊ��",oncorrect:"��ֹ���ںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ֹ�������߲����пշ���"},onerror:"��ֹ���ڲ���Ϊ��"});--%>
		})
		</logic:equal>
		
			
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
        	if (!checkNotNull(addstaffer.sadRid,"������")) return false;
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
	       	if (!checkNotNull(addstaffer.custTel,"�û��绰")) return false;
	   		if (!checkTelNumber(addstaffer.custTel)) return false;
           return true;
        }
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../sad.do?method=toSadOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function userupdate()
				{
					 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../sad.do?method=toSadOper&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function userdel()
				{
			 		document.forms[0].action="../sad.do?method=toSadOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{
			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();
				opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;				
			}
			
		}
			</script>

</head>

<body onload="init()" onunload="toback()" class="loadBody" style="OVERFLOW: auto">

<logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�"); window.close();
	</script>
</logic:notEmpty>

	<html:form action="/sad" method="post" styleId="formValidate">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="showInfoTxt">
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

		<table width="100%" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr><html:hidden property="sadId"/>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<html:text property="sadRid" styleClass="writeTextStyle" readonly="true" styleId="sadRid"/>
<%--					<font color="#ff0000">*</font>--%>
					<span id="sadRidTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:select property="dictSadType" styleClass="selectStyle" style="width:130px" styleId="dictSadType">
						<html:option value="">��ѡ��</html:option>
						<html:options collection="sadTypeList" property="value" labelProperty="label" />
					</html:select>
					<div id="dictSadTypeTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:text property="sadTime" styleClass="writeTextStyle" size="17" styleId="sadTime"/>
					<div id="sadTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					�� ϵ ��
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="custName"/>
					<span id="custNameTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��Ʒ����
				</td>
				<td class="valueStyle">
					<html:text property="productName" styleClass="writeTextStyle" styleId="productName"/>
					<span id="productNameTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��ʼ����
				</td>
				<td class="valueStyle">
					<html:text property="deployBegin" styleClass="writeTextStyle" size="17" styleId="deployBegin"/>					
	    					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('asdBean','deployBegin',false);">
					<span id="deployBeginTip" style="width: 15px;display:inline;"></span>
				</td>
			</tr>
			<tr align="left">
				<td class="labelStyle">
					��ϵ�绰
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel"/>
					<span id="custTelTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��Ʒ����
				</td>
				<td class="valueStyle">
					<html:text property="productCount" styleClass="writeTextStyle" size="20" styleId="productCount"/>
					<span id="productCountTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��ֹ����
				</td>
				<td class="valueStyle">
					<html:text property="deployEnd" styleClass="writeTextStyle" size="17" styleId="deployEnd"/>					
	    					<img alt="ѡ������" src="../html/img/cal.gif"
						onclick="openCal('asdBean','deployEnd',false);">
					<span id="deployEndTip" style="width: 15px;display:inline;"></span>
				</td>
			</tr>
			<tr align="left">				
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="post" styleClass="writeTextStyle"/>&nbsp;
				</td>
				<td class="labelStyle">
					��Ʒ���
				</td>
				<td class="valueStyle">
					<html:text property="productScale" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean"/>--%>
				</td>
			</tr>
			<tr align="center">
				<td class="labelStyle">
					��ϵ��ַ
				</td>
				<td class="valueStyle" colspan="3" align="left">
					<html:text property="custAddr" styleClass="writeTextStyle" size="50" readonly="true"/>
					<input type="button" name="btnadd"   value="ѡ��" onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"/>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="210" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
<%--					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;ע
				</td>
				<td colspan="3" class="valueStyle" >
					<html:textarea property="remark" cols="55" rows="6" styleClass="writeTextStyle"/>
				</td>
			</tr>
			<tr>
    			<td colspan="6"  align="center" class="buttonAreaStyle">
    				<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="��ϸ"  class="buttonStyle"/>
				</logic:present>
<%--    			<logic:equal name="opertype" value="insert">--%>
<%--    				<input type="button" name="btnadd"   value="���" onclick="useradd()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="update">--%>
<%--    				<input type="button" name="btnupd"    value="ȷ��" onclick="userupdate()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="delete">--%>
<%--    				<input type="button" name="btndel"    value="ɾ��" onclick="userdel()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
    			
    				<input type="button" name="" value="�ر�"    onClick="javascript:window.close();" class="buttonStyle"/>
    			
    			</td>
    		</tr>
			<html:hidden property="sadId"/>
		</table>
		<%
		String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("asdBean")).get("sadId");
		String p = "../upload/show.jsp?t=oper_sadinfo.sad_id&id="+id + "&opertype="+request.getAttribute("opertype");
		%>
		<jsp:include flush="true" page="<%= p %>" />
	</html:form>

</body>
</html:html>
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

