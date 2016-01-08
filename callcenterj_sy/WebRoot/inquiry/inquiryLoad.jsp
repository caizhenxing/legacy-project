<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../style.jsp"%>


<html:html locale="true">
<head>
	<html:base />

	<title><logic:equal name="opertype" value="insert">
    ��ӵ�������
    </logic:equal>
	</title>

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../js/calendar3.js"></script>
	<script language="javascript" src="../js/json.js"></script>
	<script language="javascript" src="../js/common.js"></script>
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript>
   		</SCRIPT>
   		
   	<!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- jquery��֤ end -->
   		
	<script type="text/javascript">
	function checkForm(inquiryForm){
            if (!checkNotNull(inquiryForm.beginTime,"��ʼʱ��")) return false;
            if (!checkNotNull(inquiryForm.endTime,"����ʱ��")) return false;
            if (!checkNotNull(inquiryForm.organiztion,"�������")) return false;
            if (!checkNotNull(inquiryForm.topic,"�ʾ�����")) return false;
            if (!checkNotNull(inquiryForm.organizers,"��֯��")) return false;
            if (!checkNotNull(inquiryForm.aim,"����Ŀ��")) return false;  
            return true;
   	}
	var styleName="oddStyle";
	function addQuestion(){
		table=document.getElementById("questions");
		tr=table.insertRow();
		tr.className = "evenStyle";
		td=tr.insertCell();
		td.innerHTML = '<select name="dict_question_type" id="dict_question_type"><option value="001">��ѡ��</option><option value="002">��ѡ��</option><option value="003">�ʴ���</option></select>';
		td=tr.insertCell();
		td.innerHTML = '<input name="question" type="text" id="question" size="27">';
		td=tr.insertCell();
		td.innerHTML = '<input name="alternatives" type="text" id="alternatives" size="45" ondblclick="autoFill(this)">';
		td=tr.insertCell();
		td.innerHTML = '<input type="button" onclick="deleteQuestion(this)" value="ɾ��">';

	}
	function deleteQuestion(obj){
		document.getElementById("questions").deleteRow(obj.parentNode.parentNode.rowIndex);
	}
	function doadd(){
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action="../inquiry.do?method=toSave&type=insert";
			document.forms[0].submit();
		}
	}
	function doupdate(){
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action="../inquiry.do?method=toSave&type=update";
			document.forms[0].submit();
		}
	}
	function dodel(){
		document.forms[0].action="../inquiry.do?method=toSave&type=delete";
		document.forms[0].submit();
	}
	function toback()
	{
		//opener.parent.topp.document.all.btnSearch.click();
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
	}

	function openCalendar(formName,targetName){
		var cal=new calendar3(document.forms[formName].elements[targetName],window.event);
		cal.year_scroll = true;
		cal.time_comp = false;
		cal.popup();
	}
	
	//��ʼ��
		function init(){
			<c:choose>
				<c:when test="${opertype=='insert'}">
					document.getElementById('spanHead').innerHTML="����ʾ�";
					document.getElementById('spanButton').innerHTML="ע�⣺�ʴ��ⲻ����ӵ���𰸱�ѡ�"
					document.forms[0].action = "../inquiry.do?method=toSave&type=insert";
					document.getElementById('buttonSubmit').value="���";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="�޸��ʾ�";
					document.forms[0].action = "../inquiry.do?method=toSave&type=update";
					document.getElementById('buttonSubmit').value="�޸�";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="ɾ���ʾ�";
					document.forms[0].action = "../inquiry.do?method=toSave&type=delete";
					document.getElementById('buttonSubmit').value="ɾ��";
				</c:when>
				<c:when test="${opertype=='detail'}">
					document.getElementById('spanHead').innerHTML="�鿴�ʾ�";
<%--					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";--%>
					document.getElementById('buttonSubmit').style.display="none"
				</c:when>
			</c:choose>
		}
		
		//ִ����֤
		<c:choose>
			<c:when test="${opertype=='insert'}">
				$(document).ready(function(){
					$.formValidator.initConfig({formid:"inquiryId",onerror:function(msg){alert(msg)}});
					$("#inquiryType").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
					$("#topicId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ʾ��������߲����пշ���"},onerror:"����Ϊ��"});
					$("#aimId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����Ŀ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#beginTimeId").formValidator({onshow:"��ѡ��",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼʱ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#endTimeId").formValidator({onshow:"��ѡ��",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#organiztionId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����������߲����пշ���"},onerror:"����Ϊ��"});
					$("#organizersId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��֯�����߲����пշ���"},onerror:"����Ϊ��"});					
				})	
			</c:when>
			<c:when test="${opertype=='update'}">
				$(document).ready(function(){
					$.formValidator.initConfig({formid:"inquiryId",onerror:function(msg){alert(msg)}});
					$("#inquiryType").formValidator({empty:false,onshow:"��ѡ��������",onfocus:"����������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��������"});			
					$("#topicId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ʾ��������߲����пշ���"},onerror:"����Ϊ��"});
					$("#aimId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����Ŀ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#beginTimeId").formValidator({onshow:"��ѡ��",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼʱ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#endTimeId").formValidator({onshow:"��ѡ��",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����Ϊ��"});
					$("#organiztionId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����������߲����пշ���"},onerror:"����Ϊ��"});
					$("#organizersId").formValidator({onshow:"������",onfocus:"����Ϊ��",oncorrect:"�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��֯�����߲����пշ���"},onerror:"����Ϊ��"});					
				})	
			</c:when>			
		</c:choose>
		function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].actorsName;
			arrparm[1] = document.forms[0].actors;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/messages/messages.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
			//window.open(page,'win_name', 'resizable=no,scrollbars,width=' + 400 + ',height=' + 400+',left=' +0+',top=' +0 );
			
		 }
</script>

</head>
<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�"); window.close();
	</script>
</logic:notEmpty>
<body onunload="toback()" class="loadBody" onload="init()">
	<html:form action="/inquiry" method="post" styleId="inquiryId">
		<html:hidden property="id" />
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				<span id="spanHead">�鿴�ʾ�</span>
<%--				<logic:equal name="opertype" value="insert">--%>
<%--		    		����ʾ�--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="detail">--%>
<%--		    		�鿴�ʾ�--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		�޸��ʾ�--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="delete">--%>
<%--		    		ɾ���ʾ�--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					�������
				</td>
				<td class="valueStyle">
					<html:select property="inquiryType" styleClass="selectStyle" styleId="inquiryType">
					<html:option value="">��ѡ��</html:option>
						<html:options collection="inquiryTypes" property="value" labelProperty="label" />
					</html:select>
					<div id="inquiryTypeTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" size="10" styleId="beginTimeId"/>
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('inquiryForm','beginTime',false);">
<%--						<font color="#ff0000">*</font>--%>
					<div id="beginTimeIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					�������
				</td>
				<td class="valueStyle">
					<html:text property="organiztion" styleClass="writeTextStyle" size="15" styleId="organiztionId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="organiztionIdTip" style="width: 0px;display:inline;"></div>
				</td>

			</tr>
			<tr>
				<td class="labelStyle">
					�ʾ�����
				</td>
				<td class="valueStyle">
					<html:text property="topic" styleClass="writeTextStyle" size="15" styleId="topicId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="topicIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="10" styleId="endTimeId"/>
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('inquiryForm','endTime',false);">
<%--						<font color="#ff0000">*</font>--%>
					<div id="endTimeIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					�� ֯ ��
				</td>
				<td class="valueStyle">
					<html:text property="organizers" styleClass="writeTextStyle" size="15" styleId="organizersId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="organizersIdTip" style="width: 0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle"> 
					 ����Ŀ��
				</td>
				<td class="valueStyle">
					<html:text property="aim" styleClass="writeTextStyle" size="15" styleId="aimId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="aimIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle"> 
					 ���״̬
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm"/>--%>
				</td>
				<td class="labelStyle">
					������Ա
				</td>
				<td class="valueStyle">
					<html:text property="actors" styleClass="writeTextStyle" size="25"/>
					<input type="hidden" name="actorsName" />
					<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="ѡ�������" onclick="dep()" width="16" height="16" border="0"/>
				</td>
			</tr>
			<tr height="1px">
				<td colspan="6" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
		
		
			<logic:equal name="opertype" value="insert">
				<table id="questions" width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
					<tr>
						<td class="listTitleStyle" width="70">
							��������
						</td>
						<td class="listTitleStyle">
							��������
						</td>
						<td class="listTitleStyle">
							����𰸱�ѡ���ͬ��ѡ����;�Ÿ�����˫���Զ���д��
						</td>
						<td class="listTitleStyle" width="50">
							<input type="button" id="addcardbt" value="���" onclick="addQuestion()" class="buttonStyle"/>
						</td>
					</tr>
					<tr class="evenStyle">
						<td>
							<select name="dict_question_type" id="dict_question_type">
								<option value='001' selected>��ѡ��</option>
								<option value='002'>��ѡ��</option>
								<option value='003'>�ʴ���</option>
							</select>
						</td>
						<td>
							<input name="question" type="text" id="question" size="27">
						</td>
						<td>
							<input name="alternatives" type="text" id="alternatives" size="45" ondblclick="autoFill(this)">
						</td>
						<td>
							<input type='button' onclick='deleteQuestion(this)' value='ɾ��'>
						</td>
					</tr>
					<tr class="evenStyle">
						<td>
							<select name="dict_question_type" id="dict_question_type">
								<option value='001'>��ѡ��</option>
								<option value='002' selected>��ѡ��</option>
								<option value='003'>�ʴ���</option>
							</select>
						</td>
						<td>
							<input name="question" type="text" id="question" size="27">
						</td>
						<td>
							<input name="alternatives" type="text" id="alternatives" size="45" ondblclick="autoFill(this)">
						</td>
						<td>
							<input type='button' onclick='deleteQuestion(this)' value='ɾ��' >
						</td>
					</tr>
					<tr class="evenStyle">
						<td>
							<select name="dict_question_type" id="dict_question_type">
								<option value='001'>��ѡ��</option>
								<option value='002'>��ѡ��</option>
								<option value='003' selected>�ʴ���</option>
							</select>
						</td>
						<td>
							<input name="question" type="text" id="question" size="27">
						</td>
						<td>
							<input name="alternatives" type="text" id="alternatives" size="45" ondblclick="autoFill(this)">
						</td>
						<td>
							<input type='button' onclick='deleteQuestion(this)' value='ɾ��' >
						</td>
					</tr>
				</table>
			</logic:equal>

			<logic:notEqual name="opertype" value="insert">
					<table id="questions" width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
						<tr>
						<td class="listTitleStyle" width="70">
							��������
						</td>
						<td class="listTitleStyle">
							��������
						</td>
						<td class="listTitleStyle">
							����𰸱�ѡ���ͬ��ѡ����;�Ÿ�����˫���Զ���д��
						</td>
						<td class="listTitleStyle" width="50">
						  <logic:equal name="opertype" value="update">
							<input type="button" id="addcardbt" value="���" onclick="addQuestion()" class="buttonStyle"/>
						  </logic:equal>
						  <logic:equal name="opertype" value="insert">
							<input type="button" id="addcardbt" value="���" onclick="addQuestion()" class="buttonStyle"/>
						  </logic:equal>
						</td>
					</tr>
						<logic:iterate name="inquiryForm" property="cards" id="card" indexId="i">
							<%
							String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
							%>
							<tr>
								<td >
									<bean:write name="card" property="dictQuestionType" />
								</td>
								<td >
									<bean:write name="card" property="question" />
								</td>
								<td >
									<bean:write name="card" property="alternatives" />
								</td>
								<td >
									<img alt="�޸�" src="../images/sysoper/update.gif"
										onclick="popUp('inquiryCardWindows','inquiryCard.do?method=toLoad&type=update&id=<bean:write name='card' property='id'/>',650,110)"
										width="16" height="16" border="0" />
									<img alt="ɾ��" src="../images/sysoper/del.gif"
										onclick="popUp('inquiryCardWindows','inquiryCard.do?method=toLoad&type=delete&id=<bean:write name='card' property='id'/>',650,110)"
										width="16" height="16" border="0" />
								</td>
							</tr>
						</logic:iterate>
					</table>

			</logic:notEqual>
			
			
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td colspan="8" align="center" class="buttonAreaStyle">
<%--					<logic:equal name="opertype" value="insert">ע�⣺�ʴ��ⲻ����ӵ���𰸱�ѡ�--%>
<%--						<input type="button" name="btnAdd"   value="����ʾ�" onclick="doadd()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="ȷ��" onclick="doupdate()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="ɾ��" onclick="dodel()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
					
					<span id="spanButton"></span>
					<input type="submit" name="addbtn" value="���" id="buttonSubmit"
							class="buttonStyle" style="display:inline" />
					
					<input type="button" name="" value="�ر�"  
						onClick="javascript:window.close();" class="buttonStyle"/>
				</td>
			</tr>
		</table>
		<html:hidden property="questions" />
		
	</html:form>
	
	
</body>
</html:html>
<script>
	function autoFill(obj){
		var index = obj.parentNode.parentNode.rowIndex;
		window.open('inquiry/autoFill.jsp?'+index,'','width=400,height=200,status=no,resizable=yes,scrollbars=yes,top=300,left=300');
	}
</script>