<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="./../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>������Ϣ��ѯ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	<!--
	body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	}
</style>
 <link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
 <SCRIPT language=javascript src="./../js/form.js" type=text/javascript></SCRIPT>
 <SCRIPT language=javascript src="./../js/calendar3.js" type=text/javascript></SCRIPT>
 <script language="javascript" src="./../js/common.js"></script>
 <script language="javascript" src="./../js/clock.js"></script>
 <script language="javascript" src="./../js/ajax.js"></script>
 <script language="javascript" src="./../js/all.js"></script>
		
<script type='text/javascript'src='/callcenterj_sy/dwr/interface/expertService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
		
 <script type="text/javascript">
 document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
 	function add()
 	{
 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../incomming/incommingInfo.do?method=toIncommingInfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
	var sTreeId = 0;
	function setTree(obj)
	{
		sTreeId = obj.value;
	}
	function fileAdd()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=insert'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,650,300);
	}
	function fileAddText()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=inserttext'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,850,500);
	}
	
	function selecttype1(){
		//ר�����id
		var billnum = document.getElementById('billNum').value;
		getClassExpertsInfo('expertName','',billnum);
		//��̬���ɵ�select id Ϊ expert_name
		
	}
//------------------------------------------	
	 // ����ר�����,���ר��
    	function getExpert_dwr(){    	
    		var obj_expertName = document.getElementById("expertName");    		
    		var pro_Value = document.getElementById('billNum').value;
    		if(pro_Value != "" && pro_Value != null){    		
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
 
  </head>
  
 <body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/incomming/incommingInfo.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;�����¼
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="addtimeBegin" styleClass="writeTextStyle" size="10" readonly="true" />
					<img alt="ѡ������" src="./../html/img/cal.gif"
						onclick="openCal('incommingInfoBean','addtimeBegin',false);">
				</td>
				<td class="queryLabelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="addtimeEnd" styleClass="writeTextStyle" size="10" readonly="true" />
					<img alt="ѡ������" src="./../html/img/cal.gif"
						onclick="openCal('incommingInfoBean','addtimeEnd',false);">
				</td>
				<td class="queryLabelStyle">
					����
				</td>
				<td class="valueStyle">
					<html:text property="tel_num" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="queryLabelStyle">
					������
				</td>
				<td class="valueStyle">
					<html:select property="respondent" styleClass="writeTextStyle">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td class="queryLabelStyle">
					��ѯ����
				</td>
				<td class="valueStyle">
					<html:text property="questionContent" styleClass="writeTextStyle" size="10"/>
				</td>
				
			
				<td class="queryLabelStyle">
					��ѯ��Ŀ
				</td>
				<td class="valueStyle">
					<html:select styleId="dictQuestionType1" property="dictQuestionType1" styleClass="writeTextStyle" style="text-indent: 5px;">
						<html:option value="">ѡ��ר��</html:option>
						<html:option value="������ѯ">������ѯ</html:option>
						<html:option value="��ֲ��ѯ">��ֲ��ѯ</html:option>
						<html:option value="��ֳ��ѯ">��ֳ��ѯ</html:option>
						<html:option value="��Ŀ��ѯ">��Ŀ��ѯ</html:option>
						<html:option value="������ѯ">������ѯ</html:option>
						<html:option value="�ش��¼��ϱ�">�ش��¼��ϱ�</html:option>
						<html:option value="��Ϣ����">��Ϣ����</html:option>
						<html:option value="��ũͨ">��ũͨ</html:option>
						<html:option value="��ҵ����">��ҵ����</html:option>
						<html:option value="ҽ�Ʒ���">ҽ�Ʒ���</html:option>
						<html:option value="�۸�����">�۸�����</html:option>
						<html:option value="�۸���">�۸���</html:option>
						<html:option value="���󷢲�">���󷢲�</html:option>
						<html:option value="���ߵ���">���ߵ���</html:option>
						<html:option value="����ͨ">����ͨ</html:option>
						<option value="�����ѯ">�����ѯ</option>
					</html:select>	

				</td>
				<td class="queryLabelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<select name="dictIsAnswerSucceed" class="writeTextStyle">
					<option value="">��ѡ��</option>
						<jsp:include flush="true" page="../custinfo/textout.jsp?selectName=dict_is_answer_succeed" />
					</select>
				</td>
				<td class="queryLabelStyle">
					�����ʽ
				</td>
				<td class="valueStyle">
					<select name="answerMan" class="writeTextStyle" style="width: 88px;">
					<option value="">��ѡ��</option>
						<jsp:include flush="true" page="../custinfo/textout.jsp?selectName=answer_man" />
					</select>
				</td>
			</tr>
			
			<tr>	
				<td class="queryLabelStyle">
					�Ƿ�ط�
				</td>
				<td class="valueStyle">
					<html:select styleId="dictIsCallback" property="dictIsCallback" styleClass="writeTextStyle" style="text-indent: 5px;">
					<html:option value="">��ѡ��</html:option>
					<html:option value="��">��</html:option>
					<html:option value="��">��</html:option>
					</html:select>					
				</td>
				<td class="queryLabelStyle">
					����ר��
				</td>
				<td class="valueStyle" colspan="2">
					<html:select styleId="billNum" property="billNum" styleClass="writeTextStyle" onchange="getExpert_dwr()" style="text-indent: 5px;">
						<html:option value="">ѡ��ר�����</html:option>
						<html:options collection="expertList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
						<html:option value="0">��ũ����</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName" styleClass="writeTextStyle" style="text-indent: 5px;">
					<html:option value="">ѡ��ר��</html:option>
					</html:select>
				</td>
				<td class="queryLabelStyle" align="right" colspan="3">
					<input type="button" name="btnSearch" value="��ѯ" class="buttonStyle" onclick="query()" />
					<input type="reset" value="ˢ��" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
