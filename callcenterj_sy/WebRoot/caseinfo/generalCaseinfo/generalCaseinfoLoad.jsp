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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>��ͨ��������Ϣ����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript" src="../../js/clock.js"></script>
	<script language="javascript" src="./../../js/ajax.js"></script>
	<script language="javascript" src="./../../js/all.js"></script>
	<script language="javascript" src="./../../js/agentState.js"></script>
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	
	<!-- dwr -->	
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/casetype.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
    
	<!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
	
<style type="text/css">
#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}
</style>

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
<%--				function add()--%>
<%--				{--%>
<%--				    var f =document.forms[0];--%>
<%--    	    		if(checkForm(f)){--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=insert";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
<%--				function update()--%>
<%--				{--%>
<%--					var f =document.forms[0];--%>
<%--    	    		if(checkForm(f)){--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=update";--%>
<%--			 	--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
<%--				function del()--%>
<%--				{--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=delete";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--				}--%>
				
				//��ʼ��
		function init(){
			<c:choose>
				<c:when test="${opertype=='detail'}">
					document.getElementById('buttonSubmit').style.display="none";
				</c:when>
				<c:when test="${opertype=='insert'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=insert";
					document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.getElementById('buttonSubmit').value="���";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=update";
					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.getElementById('buttonSubmit').value="�޸�";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=delete";
					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.getElementById('buttonSubmit').value="ɾ��";
				</c:when>
			</c:choose>
		}
		//ִ����֤

		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"generalCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#expert_name").formValidator({onshow:"��ѡ������ר��",onfocus:"����ר�ұ���ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ������ר��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#caseAttr1").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#caseAttr2").formValidator({empty:false,onshow:"��ѡ����С��",onfocus:"����С�����ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ����С��"});
			$("#caseAttr3").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});
			$("#caseContent").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#caseReply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"generalCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#expert_name").formValidator({onshow:"��ѡ������ר��",onfocus:"����ר�ұ���ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ������ר��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#caseAttr1").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
			$("#caseAttr2").formValidator({empty:false,onshow:"��ѡ����С��",onfocus:"����С�����ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ����С��"});
			$("#caseAttr3").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"�����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});
			$("#caseContent").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#caseReply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			})
			</c:when>
		</c:choose>
		
		function toback()
		{

			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
				var link = opener.parent.bottomm.document.location.href;
				if(link.indexOf("pagestate") == -1){
					link += "&pagestate=1";
				}
				opener.parent.bottomm.document.location = link;
			}
		
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

	<!-- dwr -->
    <script type="text/javascript">
	//���� ����--->С��
    function bigChange(obj){   
    	var pid=obj.value;
    	document.getElementById("caseAttr2").length=1;  	    	
    	if(pid!="")
    	casetype.getSmallTypeByBigType_app(pid,getSmallList);
    }
    
    function getSmallList(obj){
    	var v_obj = document.getElementById("caseAttr2");
    	v_obj.length=1;
    	DWRUtil.addOptions(v_obj,obj);
    }
	</script>
	
</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/caseinfo/generalCaseinfo.do" method="post" styleId="generalCaseId">

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
				<td class="valueStyle">
					<html:text property="caseRid" styleClass="writeTextStyle" readonly="true" styleId="dealCaseId"/>
					<div id="dealCaseIdTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					����ר��
				</td>
				<td class="valueStyle" colspan="3">
					<html:select styleId="bill_num" property="expertType" styleClass="selectStyle" onchange="selecttype1()">
						<html:option value="0">ר�����</html:option>
						<html:options collection="expertList" property="value" labelProperty="label"/>
						<html:option value="0">��ũ����</html:option>
					</html:select>
					<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
					<html:option value="0">ѡ��ר��</html:option>
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName))
									{
										out.print("<option value='"+rExpertName+"' selected='selected'>"+rExpertName+"</option>");
									}
									/*else
									{
										out.print("<option value=\"0\">ѡ��ר��</option>");
									}*/
								%>
					</html:select>
					<div id="expert_nameTip" style="width:0px;display:inline;"></div>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					�û�����
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="userName"/>
					<div id="userNameTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					�û��绰
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">				
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=delete"/>
				</logic:equal>
<%--				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean"/>--%>
					<br>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					�û���ַ
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="71"
						styleClass="writeTextStyle" />
					<input type="button" name="btnadd" class="buttonStyle" value="ѡ��"
						onClick="window.open('generalCaseinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td class="labelStyle" width="70">
					����ʱ��
				</td>
				<td class="valueStyle" width="130">
					<html:text property="caseTime" styleClass="writeTextStyle" size="18" readonly="true" styleId="caseTime"/>
					<html:hidden property="dictCaseType" value="putong" />
					<div id="caseTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>

				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
<%--					<html:select property="caseAttr1" styleClass="selectStyle" onchange="selecttype(this)" styleId="caseAttr1">--%>
<%--						<html:option value="">��ѡ��</html:option>--%>
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
					<html:select property="caseAttr1" onchange="bigChange(this)" styleId="caseAttr1" styleClass="selectStyle">
				    	<html:option value="">--��ѡ��--</html:option>
				    	<html:optionsCollection name="bigtypelist"/>
			    	</html:select>
					<div id="caseAttr1Tip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					����С��
				</td>
				<td class="valueStyle" >
<%--					<span id="caseAttr2_td">--%>
<%--					<html:text property="caseAttr2" styleClass="writeTextStyle" styleId="caseAttr2"/>					--%>
<%--					</span>--%>
					<html:select property="caseAttr2" styleClass="writeTextStyle" styleId="caseAttr2">
			    		<html:option value="">--��ѡ��--</html:option>
			    		<html:optionsCollection name="smallTypeList"/>
			    	</html:select>
					<div id="caseAttr2Tip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:select property="caseAttr3" styleClass="selectStyle" style="width:120px" styleId="caseAttr3">
						<html:option value="">��ѡ��</html:option>
						<html:option value="Ʒ�ֽ���">Ʒ�ֽ���</html:option>
						<html:option value="�������">�������</html:option>
						<html:option value="��ֳ����">��ֳ����</html:option>
						<html:option value="�߲�����">�߲�����</html:option>
						<html:option value="��ݷ���">��ݷ���</html:option>
						<html:option value="�ջ�����">�ջ�����</html:option>
						<html:option value="��Ʒ�ӹ�">��Ʒ�ӹ�</html:option>
						<html:option value="�г�����">�г�����</html:option>
						<html:option value="��������">��������</html:option>
						<html:option value="ũ��ʹ��">ũ��ʹ��</html:option>
						<html:option value="��ʩ�޽�">��ʩ�޽�</html:option>
						<html:option value="���߹���">���߹���</html:option>
						<html:option value="�����ۺ�">�����ۺ�</html:option>
					</html:select>
					<div id="caseAttr3Tip" style="width:0px;display:inline;"></div>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					��ѯ����
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseContent" styleClass="writeTextStyle"
						rows="4" cols="77" styleId="caseContent"/>
						<div id="caseContentTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="180" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					���ߴ�
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReply" styleClass="writeTextStyle"
						rows="4" cols="77"  styleId="caseReply"/>
						<div id="caseReplyTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReview" styleClass="writeTextStyle" rows="4" cols="77" />
				</td>
				<%
					String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("generalCaseinfoBean")).get("caseId");							
					String p = "../../upload/show2.jsp?t=oper_caseinfo.case_id&id=" + id + "&opertype="+request.getAttribute("opertype");
				%>
				<td class="valueStyle" rowspan="2" colspan="2" style="text-indent: 0px;">					
					<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;ע
				</td>
				<td class="valueStyle" colspan="3">
				<html:textarea property="remark" styleClass="writeTextStyle" cols="77" rows="4" />
				</td>
			</tr>
			<tr>
				<td colspan="6" align="right" class="buttonAreaStyle">
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
<%--						<input type="button" name="btnDelete" class="" value="ɾ��"--%>
<%--							onclick="del()" />--%>
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
	
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "��ѡ��"){
			sendRequest("../../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
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
	
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle' id='caseAttr2'>"+res+"</select>";//<div id='caseAttr2Tip' style='width:0px;display:inline;'></div>
<%--                document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle' id='caseAttr2'><option value=\"\">��ѡ��</option>"+res+"</select>";--%>
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

</script>
