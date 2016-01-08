<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		if(opener.parent.bottomm){
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}else{
			opener.document.location=opener.document.location;
		}
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>����Ա�������</title>
		<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
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
		}
		
		//��ʼ��
		function init(){	
			<logic:equal name="opertype" value="detail">
				document.getElementById('spanHead').innerHTML="�鿴��������Ϣ";
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=insert";				
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸���������Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "eventResult.do?method=toEventResultOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ����������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
				v_flag="del"
			</logic:equal>		
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"eventResult",onerror:function(msg){alert(msg)}});	
			$("#linkman_id").formValidator({onshow:"��ѡ��������",onfocus:"�����ű���ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ��������!"});	
			$("#feedback").formValidator({onshow:"�����뷴������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	

		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"eventResult",onerror:function(msg){alert(msg)}});	
			$("#linkman_id").formValidator({onshow:"��ѡ��������",onfocus:"�����ű���ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ��������!"});	
			$("#feedback").formValidator({onshow:"�����뷴������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	

		})
		</logic:equal>
		
		
<%--	var url = "eventResult.do?method=toEventResultOper&type=";--%>
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
<%--   		--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>
	
	    function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].linkman;
			arrparm[1] = document.forms[0].cust_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/eventResult/eventResult.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }
	
</script>		
	</head>

	<body class="loadBody" onload="init();">
		<html:form action="/eventResult/eventResult" method="post" styleId="eventResult" onsubmit="return formAction();">
			<html:hidden property="id" />
			<html:hidden property="event_id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
		    				��ǰλ��&ndash;&gt;<span id="spanHead">�����������Ϣ</span>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
<%--				    		�鿴��Ϣ--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		�޸���Ϣ--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		ɾ����Ϣ--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">������</td>
<%--					<td class="valueStyle">--%>
<%--						<html:text property="linkman" styleClass="writeTextStyle" />--%>
<%--					</td>--%>
					<td class="valueStyle">
						<html:select property="linkman_id" style="width:75" styleId="linkman_id">
							<option value="">��ѡ��</option>
								<logic:iterate id="u" name="user">
									<html:option value="${u.userId}">${u.userId}</html:option>						
								</logic:iterate>
						</html:select>
						<div id="linkmanTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				
				<tr>
					<td class="labelStyle">����Ա</td>
					<td class="valueStyle">
						<html:text property="linkman" styleClass="writeTextStyle" size="30"/>
						<html:hidden property="cust_id" />
						<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="ѡ������Ա" onclick="dep()" width="16" height="16" border="0"/>
					</td>
				</tr>
				
				<tr>
					<td class="labelStyle">����ʱ��</td>
					<td class="valueStyle">
						<html:text property="feedback_date" styleClass="writeTextStyle" size="10"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('eventResult','feedback_date',false);">
					</td>
				</tr>
				<tr>
					<td class="labelStyle">��������</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="feedback" styleClass="writeTextStyle" rows="4" cols="42" styleId="feedback"/>
						<div id="feedbackTip" style="width: 10px;display:inline;"></div>
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
						<input type="button" value="�ر�" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>