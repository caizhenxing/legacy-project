<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>��ũ�г���������Ļά������</title>
		<script language="javascript" type="text/JavaScript" src="../../js/calendar3.js"></script>
		<script language="javascript" src="../../js/common.js"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
				
		<!-- jquery��֤ -->
		<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
		<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
		<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
		<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
		   
		<script type="text/javascript">		
		var v_flag="";
		function formAction(){
			if(v_flag=="del"){
				if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
					return true;
				else
					return false;
			}
			document.getElementById('analysiserPhoto').value=window.frames["iframeUpload"].document.getElementById('filePathName').value;

		}
		
		//��ʼ��
		function init(){	
			<logic:equal name="opertype" value="detail">
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=insert";
				document.getElementById('spanHead').innerHTML="��ӽ�ũ�г�������Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=update";
				document.getElementById('spanHead').innerHTML="�޸Ľ�ũ�г�������Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ����ũ�г�������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
				v_flag="del"
			</logic:equal>
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#analysisPerson").formValidator({onshow:"���������ʦ",onfocus:"����ʦ����Ϊ��",oncorrect:"����ʦ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʦ���߲����пշ���"},onerror:"����ʦ����Ϊ��"});	
			$("#subTitle").formValidator({onshow:"�������������",onfocus:"�������ⲻ��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ⲻ��Ϊ��"});	
			$("#analysisContent").formValidator({onshow:"�������������",onfocus:"�������Ĳ���Ϊ��",oncorrect:"�������ĺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������Ĳ���Ϊ��"});	
			$("#analysisType").formValidator({onshow:"��ѡ�����",onfocus:"�����Ϊ��",oncorrect:"���Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������߲����пշ���"},onerror:"�����Ϊ��"});	
		
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#analysisPerson").formValidator({onshow:"���������ʦ",onfocus:"����ʦ����Ϊ��",oncorrect:"����ʦ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʦ���߲����пշ���"},onerror:"����ʦ����Ϊ��"});	
			$("#subTitle").formValidator({onshow:"�������������",onfocus:"�������ⲻ��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ⲻ��Ϊ��"});	
			$("#analysisContent").formValidator({onshow:"�������������",onfocus:"�������Ĳ���Ϊ��",oncorrect:"�������ĺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������Ĳ���Ϊ��"});	
			$("#analysisType").formValidator({onshow:"��ѡ�����",onfocus:"�����Ϊ��",oncorrect:"���Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������߲����пշ���"},onerror:"�����Ϊ��"});	
		
		})
		</logic:equal>
	
<%--	var url = "event.do?method=toEventOper&type=";--%>
<%--	//���--%>
<%--   	function add(){  --%>
<%--   		document.forms[0].action = url + "insert";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--   		document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>	
</script>
	</head>
<%
 Object o = request.getAttribute("photoPath");
 if(o != null){
 	System.out.println(o.toString());
 }
 %>
	<body class="loadBody" onload="init();">
		<html:form action="/screen/marAnalysis" method="post" styleId="marAnalysisBean" onsubmit="return formAction();">
			<html:hidden property="id" />
			<html:hidden property="analysiserPhoto2" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				��ǰλ��&ndash;&gt;���������Ϣ--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴��ũ�г�������Ϣ</span>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		��ǰλ��&ndash;&gt;�޸�������Ϣ--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		��ǰλ��&ndash;&gt;ɾ��������Ϣ--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">����ʦ</td>
					<td class="valueStyle" colspan="3">
						<html:text property="analysisPerson" styleClass="writeTextStyle" styleId="analysisPerson"/>
						<div id="analysisPersonTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">����ʦ���</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="analysisPersonInfo" styleClass="writeTextStyle" rows="3" cols="40" styleId="analysisPersonInfo"/>
						<div id="analysisPersonInfoTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">����ʦ��Ƭ</td>
					<td class="valueStyle" colspan="3">
						<logic:equal name="opertype" value="insert">
						    <input type="hidden" name="analysiserPhoto" id="analysiserPhoto" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle"/>--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="hidden" name="analysiserPhoto" id="analysiserPhoto" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle" value="<%=request.getAttribute("photoPath").toString() %>">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle" />--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
						<html:text property="analysiserPhoto" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
						<html:text property="analysiserPhoto" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>&nbsp; 
						<img src="../../<bean:write name="marAnalysisBean" property="analysiserPhoto" />" width="50" height="50" />
				    </td>
				</tr>
				<tr>
					<td class="labelStyle">��������</td>
					<td class="valueStyle" colspan="3">
						<html:text property="subTitle" styleClass="writeTextStyle" styleId="subTitle"/>
						<div id="subTitleTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">��������</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="analysisContent" styleClass="writeTextStyle" rows="5" cols="40" styleId="analysisContent"/>
						<div id="analysisContentTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
					<td class="valueStyle" colspan="3">
						<html:select property="analysisType" style="width:130" styleId="analysisType">
							<html:option value="">��ѡ��</html:option>
							<html:option value="ũ���">ũ���</html:option>
							<html:option value="������">������</html:option>
						</html:select>
						<div id="analysisTypeTip" style="width: 10px;display:inline;"></div>
					</td>	
				</tr>
				
				<tr>
					<td class="labelStyle">��ע</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="remark" styleClass="writeTextStyle" rows="3" cols="40" styleId="remark"/>
						<div id="remarkTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">���ʱ��</td>
					<td class="valueStyle" colspan="3">
						<html:text property="recordTime" styleClass="writeTextStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="right" class="buttonAreaStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--							<input type="button" name="btnAdd" class="buttonStyle" value="���" onclick="add()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--							<input type="button" name="btnUpdate" class="buttonStyle" value="ȷ��" onclick="update()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--							<input type="button" name="btnDelete" class="buttonStyle" value="ɾ��" onclick="del()" />--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="�ύ"  class="buttonStyle"/>
						<input type="button" name="" value="�ر�" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>					
		</html:form>
	</body>
</html:html>