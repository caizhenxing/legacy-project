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
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>����Ա�������</title>
		<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
		<script language="javascript" src="../js/common.js"></script>
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
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "event.do?method=toEventOper&type=insert";
				document.getElementById('spanHead').innerHTML="���������Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "event.do?method=toEventOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸�������Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "event.do?method=toEventOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ��������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
				v_flag="del"
			</logic:equal>		
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"event_form",onerror:function(msg){alert(msg)}});	
			$("#principal").formValidator({onshow:"����������������",onfocus:"���������˲���Ϊ��",oncorrect:"���������˺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������������߲����пշ���"},onerror:"���������˲���Ϊ��"});	
			$("#actor").formValidator({onshow:"���������������",onfocus:"��������߲���Ϊ��",oncorrect:"��������ߺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������������߲����пշ���"},onerror:"��������߲���Ϊ��"});	
			$("#topic").formValidator({onshow:"��������������",onfocus:"�������Ʋ���Ϊ��",oncorrect:"�������ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������Ʋ���Ϊ��"});	
			$("#contents").formValidator({onshow:"��������������",onfocus:"�������鲻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������鲻��Ϊ��"});	
		
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"event_form",onerror:function(msg){alert(msg)}});	
			$("#principal").formValidator({onshow:"����������������",onfocus:"���������˲���Ϊ��",oncorrect:"���������˺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������������߲����пշ���"},onerror:"���������˲���Ϊ��"});	
			$("#actor").formValidator({onshow:"���������������",onfocus:"��������߲���Ϊ��",oncorrect:"��������ߺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������������߲����пշ���"},onerror:"��������߲���Ϊ��"});	
			$("#topic").formValidator({onshow:"��������������",onfocus:"�������Ʋ���Ϊ��",oncorrect:"�������ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������Ʋ���Ϊ��"});	
			$("#contents").formValidator({onshow:"��������������",onfocus:"�������鲻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������鲻��Ϊ��"});	
		
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

	<body class="loadBody" onload="init();">
		<html:form action="/event/event" method="post" styleId="event_form" onsubmit="return formAction();">
			<html:hidden property="id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				��ǰλ��&ndash;&gt;���������Ϣ--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴������Ϣ</span>
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
					<td class="labelStyle">����������</td>
					<td class="valueStyle">
						<html:text property="principal" styleClass="writeTextStyle" styleId="principal"/>
						<div id="principalTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">�¼�ʱ��</td>
					<td class="valueStyle">
						<html:text property="eventdate" styleClass="writeTextStyle" size="10" />
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('event','eventdate',false);">
					</td>
				</tr>
				<tr>
					<td class="labelStyle">���������</td>
					<td class="valueStyle" colspan="3">
						<html:text property="actor" styleClass="writeTextStyle" styleId="actor"/>
						<div id="actorTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">��������</td>
					<td class="valueStyle" colspan="3">
						<html:text property="topic" styleClass="writeTextStyle" styleId="topic"/>
						<div id="topicTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">��������</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="contents" styleClass="writeTextStyle" rows="4" cols="40" styleId="contents"/>
						<div id="contentsTip" style="width: 10px;display:inline;"></div>
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
			<logic:notEmpty name="list">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
					<tr>
						<td class="valueStyle">
							�������б�
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
					<tr>
						<td class="listTitleStyle">
							����Ա
						</td>
						<td class="listTitleStyle">
							��������
						</td>
						<td class="listTitleStyle">
							����ʱ��
						</td>
						<td class="listTitleStyle" width="77">
							��&nbsp;&nbsp;��
						</td>
					</tr>
					<logic:iterate id="pagelist" name="list" indexId="i">
						<%
						String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
						%>
						<tr style="line-height: 21px;">
							<td>
								<bean:write name="pagelist" property="linkman" />
							</td>
							<td>
								<bean:write name="pagelist" property="feedback" />
							</td>
							<td>
								<bean:write name="pagelist" property="feedback_date" />
							</td>
							<td>

								<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=detail&id=<bean:write name="pagelist" property="id"/>',450,150)" />
								<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=update&id=<bean:write name='pagelist' property='id'/>',450,150)" />
								<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif" width="16" height="16" border="0"
									onclick="popUp('','../eventResult/eventResult.do?method=toEventResultLoad&type=delete&id=<bean:write name='pagelist' property='id'/>',450,150)" />
							</td>
						</tr>
					</logic:iterate>
				</table>
				</logic:notEmpty>			
		</html:form>
	</body>
</html>