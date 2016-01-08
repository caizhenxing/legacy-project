<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>Ч����������Ϣ����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<style type="text/css">
<!--
#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript" src="../../js/clock.js"></script>
	<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>
	<script language="javascript" src="./../../js/ajax.js"></script>
<script language="javascript" src="./../../js/all.js"></script>
<script language="javascript" src="./../../js/agentState.js"></script>
		
	<!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
		
	<script type="text/javascript">
	
			
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
		
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.caseRid,"������")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
        	if (!checkTelNumber(addstaffer.custTel)) return false;

           return true;
        }
				function add()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		//��ʼ��
		function init(){
			<c:choose>
				<c:when test="${opertype=='detail'}">
					document.getElementById('buttonSubmit').style.display="none";
				</c:when>
				<c:when test="${opertype=='insert'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=insert";
					document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.getElementById('buttonSubmit').value="���";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=update";
					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.getElementById('buttonSubmit').value="�޸�";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=delete";
					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.getElementById('buttonSubmit').value="ɾ��";
				</c:when>
			</c:choose>
		}
		//ִ����֤
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"effectCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#caseAttr5").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�������Ա���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#caseContent").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#caseReply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			$("#caseReport").formValidator({onshow:"��������ر���",onfocus:"��ر�������Ϊ��",oncorrect:"��ر����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ر������߲����пշ���"},onerror:"��ر�������Ϊ��"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"effectCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#caseAttr5").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�������Ա���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#caseContent").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#caseReply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			$("#caseReport").formValidator({onshow:"��������ر���",onfocus:"��ر�������Ϊ��",oncorrect:"��ر����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ر������߲����пշ���"},onerror:"��ر�������Ϊ��"});
			})
			</c:when>
		</c:choose>		
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
					function selecttype1(){
					document.getElementById('expert_name').innerHTML="";			
					document.getElementById('expert_name').add(new Option("ѡ��ר��","0")); 
		//ר�����id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//��̬���ɵ�select id Ϊ expert_name
		
	}
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/caseinfo/effectCaseinfo.do" method="post" styleId="effectCaseId">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
			<td class="navigateStyle">
				��ǰλ��&ndash;&gt;
				<span id="spanHead">��ϸ</span>
				</td>
<%--				<td class="navigateStyle">--%>
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
<%--				</td>--%>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle" width="268">
					<html:text property="caseRid" styleClass="writeTextStyle" readonly="true" styleId="dealCaseId"/>
					<div id="dealCaseIdTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" width="80">
					����ר��
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="caseExpert" styleClass="writeTextStyle" />--%>
					<html:select styleId="bill_num" property="expertType" styleClass="selectStyle" onchange="selecttype1()">
						<html:option value="0">ר�����</html:option>
						<html:options collection="expertList" property="value" labelProperty="label"/>
						<html:option value="0">��ũ����</html:option>
					</html:select>
					<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName))
									{
										out.print("<option value="+rExpertName+">"+rExpertName+"</option>");
									}
									else
									{
										out.print("<option value=\"0\">ѡ��ר��</option>");
									}
								%>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					�û�����
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="userName"/>
				<div id="userNameTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					�û��绰
				</td>
				<td class="valueStyle">
					<html:text property="custTel" style="width: 120" styleClass="writeTextStyle" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=delete"/>
				</logic:equal>					
<%--					<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean"/>--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					�û���ַ
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="70"
						styleClass="writeTextStyle" />
						<input type="button" name="btnadd" class="buttonStyle" value="ѡ��"
						onClick="window.open('effectCaseinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="caseTime" styleClass="writeTextStyle" size="18" styleId="caseTime"/>
					<html:hidden property="dictCaseType" value="effectCase" />
					<div id="caseTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle" colspan="5">
					<html:select property="caseAttr5" styleClass="selectStyle" style="width:130px"  styleId="caseAttr5">
						<html:option value="">��ѡ��</html:option>
						<html:option value="�û�����">�û�����</html:option>
						<html:option value="�û�����">�û�����</html:option>
						<html:option value="�����Ч">�����Ч</html:option>
					</html:select>
					<div id="caseAttr5Tip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��ѯ����
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseContent" styleClass="writeTextStyle"
						cols="75" rows="4" styleId="caseContent"/>
						<div id="caseContentTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="180" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
<%--					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					���ߴ�
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReply" styleClass="writeTextStyle"
						cols="75" rows="4" styleId="caseReply"/>
					<div id="caseReplyTip" style="width:0px;display:inline;"></div>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					��ر���
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReport" styleClass="writeTextStyle"
						cols="75" rows="4"  styleId="caseReport"/>
						<div id="caseReportTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="valueStyle" rowspan="2" colspan="2" style="width:212px"
					style="text-indent: 0px;">
					<%
						String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("effectCaseinfoBean")).get("caseId");
						String p = "../../upload/show2.jsp?t=oper_caseinfo.case_id&id=" + id + "&opertype="+request.getAttribute("opertype");
					%>
					<iframe marginwidth="0" framespacing="0" marginheight="0"
						frameborder="0" scrolling="auto" style="width:100%" src="<%=p%>"></iframe>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;ע
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="remark" styleClass="writeTextStyle"
						cols="75" rows="4" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
				<input type="submit" name="addbtn" value="���" id="buttonSubmit" class="buttonStyle" />
					<input type="button" name="reset" value="�ر�"
						onClick="javascript:window.close();" class="buttonStyle" />
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd" class="buttonStyle" value="���"--%>
<%--							onclick="add()" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate" class="buttonStyle"--%>
<%--							value="ȷ��" onclick="update()" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete" class="buttonStyle"--%>
<%--							value="ɾ��" onclick="del()" />--%>
<%--					</logic:equal>--%>
<%----%>
<%--					<input type="button" name="" value="�ر�" class="buttonStyle"--%>
<%--						onClick="javascript:window.close();" />--%>

				</td>
			</tr>
			<html:hidden property="caseId" />
		</table>

	</html:form>
</body>
</html:html>
<script>

	function getAccid(v){
		sendRequest("../../focusPursue/getAccid.jsp", "state="+v);
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
