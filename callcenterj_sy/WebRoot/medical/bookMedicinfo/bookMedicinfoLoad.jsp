<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html:html locale="true">
<head>
	<html:base />

	<title>ԤԼҽ�Ʋ���</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/clock.js"></script>
	<SCRIPT language="javascript" src="../../js/form.js"
		type=text/javascript>
</SCRIPT>
			<script language="javascript" src="./../../js/ajax.js"></script>
		<script language="javascript" src="./../../js/all.js"></script>
		<script language="javascript" src="./../../js/agentState.js"></script>
	<!-- dwr���� -->
	<script type='text/javascript'src='/callcenterj_sy/dwr/interface/expertService.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	<script type="text/javascript">
		 // ����ר�����,���ר��
    	function getExpert_dwr(){
    	//alert("1");
    		//var obj_Pro = document.getElementById("sfd");
    		var obj_expertName = document.getElementById("expertName");
    		//var pro_Index = obj_Pro.selectedIndex;
    		//var pro_Value = obj_Pro.options[pro_Index].value;
    		var pro_Value = document.getElementById('billNum').value;
    		if(pro_Value != "" && pro_Value != null){
    		//alert("2:"+pro_Value);
    			expertService.getExpert(pro_Value,expertReturn);
    		}else{
    			DWRUtil.removeAllOptions(obj_expertName);    			
				DWRUtil.addOptions(obj_expertName,{'':'ѡ��ר��'});
    		}
    	}
    // �ص�����
    	function expertReturn(data){
    		var obj_expertName = document.getElementById("expertName");    		
    		DWRUtil.removeAllOptions(obj_expertName);
			DWRUtil.addOptions(obj_expertName,{'':'ѡ��ר��'});
			DWRUtil.addOptions(obj_expertName,data);
    	}
	</script>
	<!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
	<!-- jquery��֤ end -->
		
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
        	if (!checkNotNull(addstaffer.bookRid,"������")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
        	
			if (!checkTelNumber(addstaffer.custTel)) return false;
           return true;
        }
				function addinfo()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
			//opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
				function selecttype1(){
			//ר�����id
			var billnum = document.getElementById('bill_num').value;
			//getClassExpertsInfo('expert_name','',billnum);
			getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
			//��̬���ɵ�select id Ϊ expert_name
		
		}
		
		//��ʼ��
		function init(){
			<c:choose>
				<c:when test="${opertype=='insert'}">
					document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=insert";
					document.getElementById('buttonSubmit').value="���";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=update";
					document.getElementById('buttonSubmit').value="�޸�";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=delete";
					document.getElementById('buttonSubmit').value="ɾ��";
				</c:when>
				<c:when test="${opertype=='detail'}">
					document.getElementById('spanHead').innerHTML="�鿴��Ϣ";
<%--					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";--%>
					document.getElementById('buttonSubmit').style.display="none"
				</c:when>
			</c:choose>
		}
		
		//ִ����֤
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"bookMedicinfoId",onerror:function(msg){alert(msg)}});
			$("#bookRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#expert_sort").formValidator({onshow:"��ѡ������ר�����",onfocus:"����ר��������ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ������ר�����"});
			$("#custNameId").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#dictSex").formValidator({empty:false,onshow:"��ѡ���û��Ա�",onfocus:"�û��Ա����ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ���û��Ա�"});
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#custAddr").formValidator({onshow:"�������û���ַ",onfocus:"�û���ַ����Ϊ��",oncorrect:"�û���ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û���ַ���߲����пշ���"},onerror:"�û���ַ����Ϊ��"});
<%--			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});--%>
			$("#isParter").formValidator({empty:false,onshow:"��ѡ��μ���ũ��",onfocus:"�μ���ũ�ϱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��μ���ũ��"});
			$("#contents").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#reply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"bookMedicinfoId",onerror:function(msg){alert(msg)}});
			$("#bookRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#expert_sort").formValidator({onshow:"��ѡ������ר�����",onfocus:"����ר��������ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ������ר�����"});
			$("#custNameId").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#dictSex").formValidator({empty:false,onshow:"��ѡ���û��Ա�",onfocus:"�û��Ա����ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ���û��Ա�"});
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#custAddr").formValidator({onshow:"�������û���ַ",onfocus:"�û���ַ����Ϊ��",oncorrect:"�û���ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û���ַ���߲����пշ���"},onerror:"�û���ַ����Ϊ��"});
<%--			$("#caseTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});--%>
			$("#contents").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#reply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			})
			</c:when>
		</c:choose>
					
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
		alert("�����ɹ�"); window.close();
	</script>
	</logic:notEmpty>

	<html:form action="/medical/bookMedicinfo.do" method="post" styleId="bookMedicinfoId">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				<span id="spanHead">�鿴��Ϣ</span>
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

				<td class="labelStyle" width="76">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="bookRid" styleClass="writeTextStyle" readonly="true" size="15" styleId="bookRid"/>
					<div id="bookRidTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					����ר��
				</td>
				<td class="valueStyle" colspan="3">
					<html:select styleId="expert_sort" property="billNum" styleClass="writeTextStyle" onchange="getExpert_dwr()" style="text-indent: 5px;">
						<html:option value="">ѡ��ר�����</html:option>
						<html:options collection="expertList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
						<html:option value="0">��ũ����</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName" styleClass="writeTextStyle" style="text-indent: 5px;">
						<html:option value="">ѡ��ר��</html:option>
						<html:options collection="expertNameList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
					</html:select>
					<div id="expert_sortTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" size="8" styleId="custNameId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="custNameIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					�û��Ա�
				</td>
				<td class="valueStyle">
					<html:select property="dictSex" styleClass="selectStyle"  styleId="dictSex">
						<html:option value="">��ѡ��</html:option>
						<html:option value="SYS_TREE_0000000663">����</html:option>
						<html:option value="SYS_TREE_0000000664">��Ů</html:option>
					</html:select>
					<div id="dictSexTip" style="width:0px;display:inline;"></div>
				</td>

				<td class="labelStyle">
					�û��绰
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" style="width:120px" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;ַ
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="43" styleClass="writeTextStyle"  styleId="custAddr"/>
					<input name="add" type="button" id="add" value="ѡ��"
					onClick="window.open('../sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					class="buttonStyle" >
					<div id="custAddrTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean"/>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					�μ�&nbsp;&nbsp;��ũ��
				</td>
				<td class="valueStyle" colspan="3">
					<html:select property="isParter" styleClass="selectStyle" style="width:135px" styleId="isParter">
						<html:option value="">��ѡ��</html:option>
						<html:option value="yes">��</html:option>
						<html:option value="noyes">��</html:option>
					</html:select>
					<div id="isParterTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" style="width:120px"/>
				</td>
			</tr>

			<tr>

				<td class="labelStyle">
					��&nbsp;ѯ&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="contents" styleClass="writeTextStyle"
						cols="76" rows="3"  styleId="contents"/>
					<div id="contentsTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="reply" styleClass="writeTextStyle"
						cols="76" rows="3" styleId="reply"/>
						<div id="replyTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">

					<html:select property="dictServiceType" styleClass="selectStyle" style="width:155">

						<html:option value="">
	    						��ѡ��	    					
	    					</html:option>
						<html:options collection="diagnoseList" property="value"
							labelProperty="label" />

					</html:select>


				</td>

				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">

					<html:select property="isVisit" styleClass="selectStyle" style="width:155">

						<html:option value="">
	    						��ѡ��	    					
	    					</html:option>
						<html:option value="yes">
	    						��	    					
	    					</html:option>
						<html:option value="noyes">
	    						��	    					
	    					</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					ԤԼ����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="bookVisitTime" readonly="true"
						styleClass="writeTextStyle" />
					<img alt="ѡ������" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','bookVisitTime',false);">
				</td>
				<td class="labelStyle">
					ʵ�ʾ���ʱ��
				</td>
				<td colspan="3" class="valueStyle">
					<html:text property="visitTime" readonly="true"
						styleClass="writeTextStyle" />
					<img alt="ѡ������" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','visitTime',false);">
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					ҽԺ������
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="hospitalAdvice"
						styleClass="writeTextStyle" cols="76" rows="5" />
				</td>
			</tr>
			<tr>
				<td height="16" class="labelStyle">
					��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;Ա
				</td>
				<td class="valueStyle">
					<html:text property="navigator" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="navigator" >--%>

					<%--	    					<html:option value="">--%>
					<%--	    						��ѡ��							--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:optionsCollection property="" name=""/>--%>
					<%--	    				</html:select>	    			--%>
				</td>
				<td rowspan="4" class="labelStyle" styleClass="selectStyle">
					��&nbsp;��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3" rowspan="4">

					<%--	    				<html:select multiple="true" size="5" property="favours">--%>
					<%--	    					<html:options Collection="" name=""  property=""/>--%>
					<%--	    				</html:select>--%>

					<html:textarea property="favours" cols="31" rows="8"
						styleClass="writeTextStyle"></html:textarea>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;λ
				</td>
				<td class="valueStyle">
					<html:text property="bed" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="bed" styleClass="selectStyle">--%>
					<%----%>
					<%--	    					<html:option value="">--%>
					<%--	    						��ѡ��	    					--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:option value="yes">--%>
					<%--	    						��	    					--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:option value="noyes">--%>
					<%--	    						��	    					--%>
					<%--	    					</html:option>--%>
					<%--	    				</html:select>	    			--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;Ŀ
				</td>
				<td class="valueStyle">
					<html:text property="items" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="items" styleClass="selectStyle">--%>
					<%----%>
					<%--	    					<html:option value="">--%>
					<%--	    						��ѡ��	    					--%>
					<%--	    					</html:option>--%>
					<%--	    				</html:select>	    		--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">

					<html:text property="charge" styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;ҽ&nbsp;&nbsp;��&nbsp;��
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="visitResult" cols="76"
						styleClass="writeTextStyle" rows="3" />
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;&nbsp;��&nbsp;��
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="traceService" cols="76"
						styleClass="writeTextStyle" rows="3" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="remark" cols="76"
						styleClass="writeTextStyle" rows="3" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="���"--%>
<%--							onclick="addinfo()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="ȷ��" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="ɾ��" onclick="del()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>

					<input type="submit" name="addbtn" value="���" id="buttonSubmit"
							class="buttonStyle" style="display:inline" />

					<input type="button" name="" value="�ر�"  
						onClick="javascript:window.close();" class="buttonStyle"/>
				</td>
			</tr>
			<html:hidden property="id" />
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

