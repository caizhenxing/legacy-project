<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>�г���������</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<style type="text/css">
<!--
#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
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
	<link REL=stylesheet href="../markanainfo/css/divtext.css"
		type="text/css" />
	<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>
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
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=insert";
					document.getElementById('btnOper').value="���";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=update";
					document.getElementById('btnOper').value="�޸�";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=delete";
					document.getElementById('btnOper').value="ɾ��";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		//ִ����֤
		//Ʒ��     ��� �� ����
		
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#frontFor").formValidator({onshow:"���������췽",onfocus:"���췽����Ϊ��",oncorrect:"���췽�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���췽���߲����пշ���"},onerror:"���췽����Ϊ��"});
			$("#underTake").formValidator({onshow:"������а췽",onfocus:"�а췽����Ϊ��",oncorrect:"�а췽�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�а췽���߲����пշ���"},onerror:"�а췽����Ϊ��"});
			$("#supportTel").formValidator({onshow:"������֧������",onfocus:"֧�����߲���Ϊ��",oncorrect:"֧�����ߺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"֧���������߲����пշ���"},onerror:"֧�����߲���Ϊ��"});
			$("#supportSite").formValidator({onshow:"������֧����վ",onfocus:"֧����վ����Ϊ��",oncorrect:"֧����վ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"֧����վ���߲����пշ���"},onerror:"֧����վ����Ϊ��"});
			$("#contactMail").formValidator({onshow:"��������ϵ����",onfocus:"��ϵ���䲻��Ϊ��",oncorrect:"��ϵ����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�������߲����пշ���"},onerror:"��ϵ���䲻��Ϊ��"});
			$("#chiefEditor").formValidator({onshow:"����������",onfocus:"���಻��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���಻��Ϊ��"});
			$("#subEditor").formValidator({onshow:"�����븱����",onfocus:"�����಻��Ϊ��",oncorrect:"������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����಻��Ϊ��"});			
			$("#firstEditor").formValidator({onshow:"��������ϯ�༭",onfocus:"��ϯ�༭����Ϊ��",oncorrect:"��ϯ�༭�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�༭���߲����пշ���"},onerror:"��ϯ�༭����Ϊ��"});
			$("#chargeEditor").formValidator({onshow:"���������α༭",onfocus:"���α༭����Ϊ��",oncorrect:"���α༭�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���α༭���߲����пշ���"},onerror:"���α༭����Ϊ��"});
			$("#period").formValidator({onshow:"�������ڵ�",onfocus:"�ڵڲ���Ϊ��",oncorrect:"�ڵںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ڵ����߲����пշ���"},onerror:"�ڵڲ���Ϊ��"});
			$("#createTime").formValidator({onshow:"���������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#sendUnit").formValidator({onshow:"�����뱨�͵�λ",onfocus:"���͵�λ����Ϊ��",oncorrect:"���͵�λ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���͵�λ���߲����пշ���"},onerror:"���͵�λ����Ϊ��"});
			$("#dictProductType").formValidator({empty:false,onshow:"��ѡ��Ʒ��",onfocus:"Ʒ�ֱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��Ʒ��"});
			$("#dictCommentType").formValidator({empty:false,onshow:"��ѡ������",onfocus:"�������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ������"});
			$("#chiefTitle").formValidator({onshow:"������������",onfocus:"�����ⲻ��Ϊ��",oncorrect:"������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����ⲻ��Ϊ��"});
			$("#summary").formValidator({onshow:"������ժҪ��Ϣ",onfocus:"ժҪ��Ϣ����Ϊ��",oncorrect:"ժҪ��Ϣ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ժҪ��Ϣ���߲����пշ���"},onerror:"ժҪ��Ϣ����Ϊ��"});		
<%--			����--%>
			
		})
   		</logic:equal>
   		<logic:equal name="opertype" value="update">
   		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#frontFor").formValidator({onshow:"���������췽",onfocus:"���췽����Ϊ��",oncorrect:"���췽�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���췽���߲����пշ���"},onerror:"���췽����Ϊ��"});
			$("#underTake").formValidator({onshow:"������а췽",onfocus:"�а췽����Ϊ��",oncorrect:"�а췽�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�а췽���߲����пշ���"},onerror:"�а췽����Ϊ��"});
			$("#supportTel").formValidator({onshow:"������֧������",onfocus:"֧�����߲���Ϊ��",oncorrect:"֧�����ߺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"֧���������߲����пշ���"},onerror:"֧�����߲���Ϊ��"});
			$("#supportSite").formValidator({onshow:"������֧����վ",onfocus:"֧����վ����Ϊ��",oncorrect:"֧����վ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"֧����վ���߲����пշ���"},onerror:"֧����վ����Ϊ��"});
			$("#contactMail").formValidator({onshow:"��������ϵ����",onfocus:"��ϵ���䲻��Ϊ��",oncorrect:"��ϵ����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ�������߲����пշ���"},onerror:"��ϵ���䲻��Ϊ��"});
			$("#chiefEditor").formValidator({onshow:"����������",onfocus:"���಻��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���಻��Ϊ��"});
			$("#subEditor").formValidator({onshow:"�����븱����",onfocus:"�����಻��Ϊ��",oncorrect:"������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����಻��Ϊ��"});			
			$("#firstEditor").formValidator({onshow:"��������ϯ�༭",onfocus:"��ϯ�༭����Ϊ��",oncorrect:"��ϯ�༭�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�༭���߲����пշ���"},onerror:"��ϯ�༭����Ϊ��"});
			$("#chargeEditor").formValidator({onshow:"���������α༭",onfocus:"���α༭����Ϊ��",oncorrect:"���α༭�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���α༭���߲����пշ���"},onerror:"���α༭����Ϊ��"});
			$("#period").formValidator({onshow:"�������ڵ�",onfocus:"�ڵڲ���Ϊ��",oncorrect:"�ڵںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ڵ����߲����пշ���"},onerror:"�ڵڲ���Ϊ��"});
			$("#createTime").formValidator({onshow:"���������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#sendUnit").formValidator({onshow:"�����뱨�͵�λ",onfocus:"���͵�λ����Ϊ��",oncorrect:"���͵�λ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���͵�λ���߲����пշ���"},onerror:"���͵�λ����Ϊ��"});
			$("#dictProductType").formValidator({empty:false,onshow:"��ѡ��Ʒ��",onfocus:"Ʒ�ֱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ��Ʒ��"});
			$("#dictCommentType").formValidator({empty:false,onshow:"��ѡ������",onfocus:"�������ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ������"});
			$("#chiefTitle").formValidator({onshow:"������������",onfocus:"�����ⲻ��Ϊ��",oncorrect:"������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����ⲻ��Ϊ��"});
			$("#summary").formValidator({onshow:"������ժҪ��Ϣ",onfocus:"ժҪ��Ϣ����Ϊ��",oncorrect:"ժҪ��Ϣ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ժҪ��Ϣ���߲����пշ���"},onerror:"ժҪ��Ϣ����Ϊ��"});		
<%--			����--%>
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
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.frontFor,"���췽")) return false; 
        	
        	if (!checkNotNull(addstaffer.underTake,"�а췽")) return false;
        	if (!checkNotNull(addstaffer.chiefTitle,"������")) return false;
        	if (!checkNotNull(addstaffer.summary,"ժҪ")) return false;
        	if (!checkTelNumber(addstaffer.supportTel)) return false;
			if (!checkEmail(addstaffer.contactMail)) return false;

           return true;
        }
				function add()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
    	    			document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=insert";
			 			document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
			</script>

</head>

<body onunload="toback()" onload="init()" class="loadBody">

	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/markanainfo.do" method="post" styleId="formValidate">

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
<%
String id2 = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("markanainfoBean")).get("markanaId");
	String p2 = "../upload/ico.jsp?t=oper_markanainfo.markana_id&id=" + id2;
			%>
			<tr>
				<td class="valueStyle" rowspan="4">
					<img alt="��Ʒ��ʶ" width="90" height="60" src='<jsp:include flush="true" page="<%=p2 %>"/>'>
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">
				<logic:notEmpty name="markanainfoBean" property="frontFor">
					<html:text property="frontFor" styleClass="writeTextStyle" size="35" styleId="frontFor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="frontFor">
					<html:text property="frontFor" styleClass="writeTextStyle" size="35" styleId="frontFor" value="����ʡũ�徭��ίԱ����Ϣ����"/>
				</logic:empty>				
					<span id="frontForTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">
				<logic:notEmpty name="markanainfoBean" property="underTake">
					<html:text property="underTake" styleClass="writeTextStyle" size="35" styleId="underTake" />
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="underTake">
					<html:text property="underTake" styleClass="writeTextStyle" size="35" styleId="underTake" value="12316��ũ����"/>
				</logic:empty>
					<span id="underTakeTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					֧������
				</td>
				<td class="valueStyle">
				<logic:notEmpty name="markanainfoBean" property="supportTel">
					<html:text property="supportTel" styleClass="writeTextStyle" styleId="supportTel"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="supportTel">
					<html:text property="supportTel" styleClass="writeTextStyle" styleId="supportTel" value="12316"/>
				</logic:empty>				
					<span id="supportTelTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					֧����վ
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="supportSite">
					<html:text property="supportSite" styleClass="writeTextStyle" styleId="supportSite"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="supportSite">
					<html:text property="supportSite" styleClass="writeTextStyle" styleId="supportSite" value="http://www.jinnong.cc/"/>
				</logic:empty>				
					<span id="supportSiteTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��ϵ����
				</td>
				<td class="valueStyle" colspan="3">					
					<logic:notEmpty name="markanainfoBean" property="contactMail">
					<html:text property="contactMail" styleClass="writeTextStyle" styleId="contactMail"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="contactMail">
					<html:text property="contactMail" styleClass="writeTextStyle" styleId="contactMail" value="yangbo--119119@163.com"/>
				</logic:empty>				
					<span id="contactMailTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="chiefEditor">
					<html:text property="chiefEditor" styleClass="writeTextStyle" styleId="chiefEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="chiefEditor">
					<html:text property="chiefEditor" styleClass="writeTextStyle" styleId="chiefEditor" value="Ĳ����"/>
				</logic:empty>				
					<span id="chiefEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="subEditor">
					<html:text property="subEditor" styleClass="writeTextStyle" styleId="subEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="subEditor">
					<html:text property="subEditor" styleClass="writeTextStyle" styleId="subEditor" value="��̷�"/>
				</logic:empty>				
					<span id="subEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��ϯ�༭
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="firstEditor">
					<html:text property="firstEditor" styleClass="writeTextStyle" styleId="firstEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="firstEditor">
					<html:text property="firstEditor" styleClass="writeTextStyle" styleId="firstEditor" value="�"/>
				</logic:empty>				
					<span id="firstEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					���α༭
				</td>
				<td class="valueStyle">
					<html:text property="chargeEditor" styleClass="writeTextStyle" styleId="chargeEditor"/>
					<span id="chargeEditorTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="period" styleClass="writeTextStyle" styleId="period"/>
					<span id="periodTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" styleId="createTime"/>					
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('markanainfoBean','createTime',false);">
					<span id="createTimeTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					���͵�λ
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="sendUnit" size="20" styleClass="writeTextStyle" styleId="sendUnit"/>
					<span id="sendUnitTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					Ʒ&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle" colspan="4">
					<html:select property="dictProductType" styleId="dictProductType" styleClass="selectStyle">
							<option value="">��ѡ��</option>
							<html:options collection="list" property="value" labelProperty="label" />
					</html:select>
					<span id="dictProductTypeTip" style="width: 0px;display:inline;"></span>
<%--					<select name="dictProductType1" class="selectStyle"--%>
<%--						onChange="select1(this)">--%>
<%--						<OPTION value="">--%>
<%--							��ѡ�����--%>
<%--						</OPTION>--%>
<%--						<jsp:include flush="true" page="../priceinfo/select_product.jsp" />--%>
<%--					</select>--%>
<%--					<span id="dict_product_type2_span"> <select--%>
<%--							name="dictProductType2" class="selectStyle"--%>
<%--							onChange="select1(this)">--%>
<%--							<OPTION value="">--%>
<%--								��ѡ��С��--%>
<%--							</OPTION>--%>
<%--						</select>--%>
<%--					</span>--%>
<%--					<span id="product_name_span"> --%>
<%--						<logic:empty name="markanainfoBean" property="dictProductType">--%>
<%--							<select name="dictProductType" class="selectStyle" style="width:70px">--%>
<%--								<OPTION value="">��ѡ������</OPTION>--%>
<%--							</select>--%>
<%--						</logic:empty>--%>
<%--						<logic:notEmpty name="markanainfoBean" property="dictProductType">--%>
<%--								<html:text property="dictProductType" styleClass="writeTextStyle" />--%>
<%--						</logic:notEmpty>--%>
<%--					</span>--%>
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:select property="dictCommentType" styleClass="selectStyle" styleId="dictCommentType">
						<html:option value="">��ѡ��</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
					</html:select>
					<span id="dictCommentTypeTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<jsp:include flush="true" page="../flow/incState.jsp?form=markanainfoBean"/>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="4">
					<html:text property="chiefTitle" styleClass="writeTextStyle"
						size="41" styleId="chiefTitle" />
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<span id="chiefTitleTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="subTitle" styleClass="writeTextStyle"
						size="35" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ
				</td>
				<td class="valueStyle" colspan="8">
					<html:text property="summary" styleClass="writeTextStyle" size="124" styleId="summary" />
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<span id="summaryTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td colspan="8" class="valueStyle" style="font-color:#000000">
					<FCK:editor instanceName="markanaContent">
						<jsp:attribute name="value">
							<bean:write name="markanainfoBean" property="markanaContent"
								filter="false" />
						</jsp:attribute>
					</FCK:editor>
					<span id="markanaContentTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					һ����
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise1" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
				<td class="labelStyle" rowspan="3">
				  <logic:equal name="opertype" value="insert">
					�ϴ���ʶ��<br />
					�����ϴ������ͬ��ʽ�ķ���<br />
					���������һ��ͼƬ��Ϊ��ʶ��
				  </logic:equal>
				  <logic:equal name="opertype" value="update">
					�ϴ���ʶ��<br />
					�����ϴ������ͬ��ʽ�ķ���<br />
					���������һ��ͼƬ��Ϊ��ʶ��
				  </logic:equal>
				</td>
				<td class="labelStyle" rowspan="3" colspan="2" width="180"
					style="text-indent: 0px;">
				  <logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
				  </logic:equal>
				  <logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
				  </logic:equal>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise2" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise3" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
			</tr>

			<tr>
				<td colspan="9" align="center" class="buttonAreaStyle">
				<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="��ϸ"  class="buttonStyle"/>
				</logic:present>
				
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="���"--%>
<%--							onclick="add()" class="buttonStyle" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="ȷ��" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="ɾ��" onclick="del()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>

					<input type="button" name="" value="�ر�"  
						onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
			<html:hidden property="markanaId" />
		</table>
		<%
	String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("markanainfoBean")).get("markanaId");
	String p = "../upload/show.jsp?t=oper_markanainfo.markana_id&id=" + id;
	%>
	<jsp:include flush="true" page="<%= p %>"/>
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
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
		}
	}
	
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
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//ָ����Ӧ����
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
				document.getElementById("product_name_span").innerHTML = "<select name='dictProductType' class='selectStyle'><OPTION  value=''>��ѡ������</OPTION>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}
	

</script>
