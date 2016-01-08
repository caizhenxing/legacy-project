<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>
 
<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "../phoneinfo.do?method=toLinkManList";
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
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
	<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/custinfo/phoneinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;����Ա����
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/quoteCircs/quoteCircs.do?method=toMain" target="contents"> ����Ա����ͳ��</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/linkmanpriceinfo.do?method=toOperPriceinfoMain" target="contents">����Ա�������</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/event/event.do?method=toEventMain" target="contents">����Ա�¼�����</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/eventResult/eventResult.do?method=toEventResultMain" target="contents">����Ա�¼�����</a>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;����Ա���
				</td>
				<td class="valueStyle">
					<html:text property="cust_number" styleClass="writeTextStyle" size="14"/>
<%--					<img alt="ѡ������" src="../../html/img/cal.gif"--%>
<%--						onclick="openCal('phoneinfo','cust_develop_time',false);">--%>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;�û�����
				</td>
				<td class="valueStyle">
					<html:text property="user_name" styleClass="writeTextStyle" size="14"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;�û���ַ
				</td>
				<td class="valueStyle">
					<html:text property="cust_addr" styleClass="writeTextStyle" size="16"/>
						<input type="button" name="btnadd" class="buttonStyle" value="ѡ��"
						onClick="window.open('../custinfo/phoneinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;�û����
				</td>
				<td class="valueStyle">
					<html:select property="cust_way_by" style="width:100">
						<option value="">��ѡ��</option>
						<option value="ũҵ��">ũҵ��</option>
						<option value="ũҵЭ��">ũҵЭ��</option>
						<option value="ũ��Ʒ������">ũ��Ʒ������</option>
						<option value="ũ�ʾ�����">ũ�ʾ�����</option>
						<option value="����">����</option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button" class="buttonStyle"
						value="��ѯ" onclick="query()" />
<%--					<leafRight:btn name="btnSearch" nickName="phoneinfo_query" styleClass="buttonStyle" value="��ѯ" onclick="query()" scopeName="userRoleLeafRightInsession" />--%>
<%--					<input type="reset"  class="buttonStyle" value="ˢ��"  >--%>
				</td>
			</tr>
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;�û��绰
				</td>
				<td class="valueStyle">
					<html:text property="cust_tel_home" styleClass="writeTextStyle" size="14"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;������
				</td>
				<td class="valueStyle">
					<html:select property="cust_rid" style="width:90">
					<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="queryLabelStyle">
					������Ŀ
				</td>
				<td colspan="4" class="valueStyle">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)" style="width:110px">
						<OPTION value="">
							��ѡ�����
						</OPTION>
						<jsp:include flush="true" page="../select_product.jsp" />
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
				
			</tr>
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;������ʽ
				</td>
				<td class="valueStyle">
					<html:select property="cust_work_way" style="width:90">
						<option value="">��ѡ��</option>
						<option value="����">����</option>
						<option value="�ط�">�ط�</option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;��̭���
				</td>
				<td class="valueStyle">
					<html:select property="is_eliminate" style="width:90">
							<html:option value="">��ѡ��</html:option>
							<html:option value="yes">��</html:option>
							<html:option value="no">��</html:option>
					</html:select>
				</td>
				<td class="queryLabelStyle">
					&nbsp;��̭ԭ��
				</td>
				<td class="valueStyle">
					<html:text property="eliminate_reason" size="16" styleClass="writeTextStyle" styleId="cust_tel_work"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;��չ����
				</td>
				<td class="valueStyle">
					<html:text property="cust_develop_time" styleClass="writeTextStyle" size="14"/>
					<img alt="ѡ������" src="../../html/img/cal.gif"
						onclick="openCal('phoneinfo','cust_develop_time',false);">
				</td>
				<td align="center" class="queryLabelStyle">
<%--					<input name="btnSearch" type="button" class="buttonStyle"--%>
<%--						value="��ѯ" onclick="query()" />--%>
<%--					<leafRight:btn name="btnSearch" nickName="phoneinfo_query" styleClass="buttonStyle" value="��ѯ" onclick="query()" scopeName="userRoleLeafRightInsession" />--%>
					<input type="reset"  class="buttonStyle" value="ˢ��"  >
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
			sendRequest("../select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("../select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
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
