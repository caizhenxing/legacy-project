<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="./../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>������ϸ����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="./../js/calendar.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="./../js/common.js"></script>
	<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
	<script language="javascript" src="./../js/clock.js"></script>
	<SCRIPT language="javascript" src="./../js/form.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="./../js/ajax.js"></script>
	<script language="javascript" src="./../js/all.js"></script>

	<script type="text/javascript">
	function selecttype1(){
		//ר�����id
		var billnum = document.getElementById('billNum').value;
		getClassExpertsInfo('expertName','',billnum);
		//��̬���ɵ�select id Ϊ expert_name
		
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
        	if (!checkNotNull(addstaffer.priceRid,"������")) return false;
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
            return true;
        }
        
				function add()
				{
				    var f =document.forms[0];
				    var treeid=document.getElementById("treeId").value;
				    if(treeid==''){
				    	alert('��ѡ�񸸽ڵ�id!');
				    	return false;
				    }
				    //����֤
    	    		if(true){
    	    		var voicePathObj = document.getElementById("voicePath");
    	    		var selectFileObj = document.getElementById("selectFile");
    	    		voicePathObj.value = selectFileObj.value;
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=insert";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				function update()
				{
			 		document.forms[0].action="../incomming/incommingInfo.do?method=toOperIncommingInfo&type=update";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
				}
				function del()
				{
					var delflag=confirm('�Ƿ�ȷ��ɾ����');
					if(true==delflag){
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=delete";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
			//pener.parent.topp.document.all.btnSearch.click();
<%--			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;--%>
		
		}
		function modifyVoicePath()
		{
			document.getElementById('selectFile').style.display='inline';
		}
		function onloadDoWithVoicePath()
		{
			var opertype =  '<bean:write name="opertype" />';
			if('update'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="inline";
			}
			else if('insert'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="inline";
				voicePath.style.display="none";
				modifyVoicePathHref.style.display="none";
			}
			else
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="none";
			}
		}
			</script>

</head>

<body onunload="toback()" class="loadBody">

	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/incomming/incommingInfo.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					<logic:equal name="opertype" value="insert">
		    		�����Ϣ
		    	</logic:equal>
					<logic:equal name="opertype" value="detail">
		    		�鿴��Ϣ
		    	</logic:equal>
					<logic:equal name="opertype" value="update">
		    		�޸���Ϣ
		    	</logic:equal>
					<logic:equal name="opertype" value="delete">
		    		ɾ����Ϣ
		    	</logic:equal>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="tel_num" styleClass="writeTextStyle" size="20"
						readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="addtime" styleClass="writeTextStyle" size="20"
						readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle" width="100">
					��ѯ��Ŀ
				</td>
				<td class="valueStyle" width="100">
					<%--					<html:text property="dictQuestionType1" styleClass="writeTextStyle" size="20" readonly="true" />--%>

					<html:select property="dictQuestionType1" styleClass="selectStyle">
						<html:option value="������ѯ"></html:option>
						<html:option value="��ֲ��ѯ"></html:option>
						<html:option value="��ֳ��ѯ"></html:option>
						<html:option value="��Ŀ��ѯ"></html:option>
						<html:option value="������ѯ"></html:option>
						<html:option value="�ش��¼��ϱ�"></html:option>
						<html:option value="��Ϣ����"></html:option>
						<html:option value="��ũͨ"></html:option>
						<html:option value="��ҵ����"></html:option>
						<html:option value="ҽ�Ʒ���"></html:option>
						<html:option value="�۸�����"></html:option>
						<html:option value="�۸���"></html:option>
						<html:option value="���󷢲�"></html:option>
						<html:option value="���ߵ���"></html:option>
						<html:option value="����ͨ"></html:option>
						<html:option value="�����ѯ"></html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<html:text property="respondent" styleClass="writeTextStyle"
						size="20" readonly="true" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<html:select property="dictIsAnswerSucceed"
						styleClass="writeTextStyle">
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=dict_is_answer_succeed" />
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					�����ʽ
				</td>
				<td class="valueStyle">
					<select name="answerMan" class="writeTextStyle">
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=answer_man" />
					</select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					�Ƿ�ط�
				</td>
				<td class="valueStyle">
					<html:select styleId="dictIsCallback" property="dictIsCallback"
						styleClass="writeTextStyle">
						<html:option value="">��ѡ��</html:option>
						<html:option value="��">��</html:option>
						<html:option value="��">��</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����ר��
				</td>
				<td class="valueStyle">
					<html:select styleId="billNum" property="billNum"
						styleClass="writeTextStyle" onchange="selecttype1()">
						<html:option value="0">ѡ��ר�����</html:option>
						<html:options collection="expertList" property="value"
							labelProperty="label" styleClass="writeTypeStyle" />
						<html:option value="0">��ũ����</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName"
						styleClass="writeTextStyle">
						<html:option value="">ѡ��ר��</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��ѯ����
				</td>
				<td class="valueStyle">
					<html:textarea property="questionContent" rows="10" cols="90"
						styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					���ߴ�
				</td>
				<td class="valueStyle" class="tdbgcolorloadleft">
					<html:textarea property="answerContent" rows="10" cols="90"
						styleClass="writeTextStyle" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
					<logic:equal name="opertype" value="insert">
						<input type="button" name="btnAdd" class="buttonStyle" value="���"
							onclick="add()" />
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<input type="button" name="btnUpdate" class="buttonStyle"
							value="�޸�" onclick="update()" />
					</logic:equal>
					<logic:equal name="opertype" value="delete">
						<input type="button" name="btnDelete" class="buttonStyle"
							value="ɾ��" />
					</logic:equal>

					<input type="button" name="" value="�ر�" class="buttonStyle"
						onClick="javascript:window.close();" />

				</td>
			</tr>
			<tr style="display: none;">
				<html:hidden property="id" styleClass="writeTextStyle" />
				<%--				<html:hidden property="talkId" styleClass="writeTextStyle"  />--%>
				<%--				<html:hidden property="mainId" styleClass="writeTextStyle"  />--%>
				<input type="hidden" name="opertype"
					value="<%=(String) request.getAttribute("opertype")%>" />
				<input type="submit" id="btnSubmit" value="�ύ"
					style="display: none;" />
			</tr>
		</table>

	</html:form>
</body>
</html:html>