<%@ page contentType="text/html; charset=gbk" language="java" errorPage=""%>

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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>�绰������</title>
		<script type='text/javascript' src='../../js/msg.js'></script>
		<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
		
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
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=insert";
					document.getElementById('spanHead').innerHTML="����û���Ϣ";
					document.getElementById('buttonSubmit').value=" �� �� ";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=update";
					document.getElementById('spanHead').innerHTML="�޸��û���Ϣ";
					document.getElementById('buttonSubmit').value=" �� �� ";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=delete";
					document.getElementById('spanHead').innerHTML="ɾ���û���Ϣ";
					document.getElementById('buttonSubmit').value=" ɾ �� ";
					v_flag="del"
				</logic:equal>		
			}
			//ִ����֤
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
<%--				$("#cust_age").formValidator({empty:true,onshow:"���������䣬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"����",onempty:"û����д����"}).inputValidator({min:1,max:99,type:"value",onerrormin:"���������ڵ���1",onerrormax:"�������С�ڵ���99",onerror:"���������1-99֮��"});				
				$("#expert_type").formValidator({onshow:"��ѡ��ר�����",onfocus:"ר��������ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ��ר�����"});
				$("#dict_cust_voc").formValidator({onshow:"�����������ҵ",onfocus:"������ҵ����Ϊ��",oncorrect:"������ҵ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������ҵ���߲����пշ���"},onerror:"������ҵ����Ϊ��"});--%>
<%--				$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"סլ�绰����Ϊ��",oncorrect:"סլ�绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"סլ�绰���߲����пշ���"},onerror:"סլ�绰����Ϊ��"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"סլ�绰��ʽ����ȷ"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});--%>
<%--				$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"�칫�绰����Ϊ��",oncorrect:"�칫�绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�칫�绰���߲����пշ���"},onerror:"�칫�绰����Ϊ��"});	
				$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"�칫�绰�Ϸ�",onempty:"û����д�칫�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�칫�绰��ʽ����ȷ"});
				$("#cust_addr").formValidator({onshow:"������ͨ�ŵ�ַ",onfocus:"ͨ�ŵ�ַ����Ϊ��",oncorrect:"ͨ�ŵ�ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ͨ�ŵ�ַ���߲����пշ���"},onerror:"ͨ�ŵ�ַ����Ϊ��"});
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������벻��Ϊ��"});--%>
<%--				$("#cust_email").formValidator({onshow:"������email",onfocus:"email����Ϊ��",oncorrect:"email�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"email���߲����пշ���"},onerror:"email����Ϊ��"});	--%>
<%--				$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
<%--				$("#cust_age").formValidator({empty:true,onshow:"���������䣬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"����",onempty:"û����д����"}).inputValidator({min:1,max:99,type:"value",onerrormin:"���������ڵ���1",onerrormax:"�������С�ڵ���99",onerror:"���������1-99֮��"});	
				$("#expert_type").formValidator({onshow:"��ѡ��ר�����",onfocus:"ר��������ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ��ר�����"});
				$("#dict_cust_voc").formValidator({onshow:"�����������ҵ",onfocus:"������ҵ����Ϊ��",oncorrect:"������ҵ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������ҵ���߲����пշ���"},onerror:"������ҵ����Ϊ��"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"סլ�绰��ʽ����ȷ"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});
				$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"�칫�绰�Ϸ�",onempty:"û����д�칫�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�칫�绰��ʽ����ȷ"});
				$("#cust_addr").formValidator({onshow:"������ͨ�ŵ�ַ",onfocus:"ͨ�ŵ�ַ����Ϊ��",oncorrect:"ͨ�ŵ�ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ͨ�ŵ�ַ���߲����пշ���"},onerror:"ͨ�ŵ�ַ����Ϊ��"});
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������벻��Ϊ��"});
				$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
			
			})
			</logic:equal>
		
		
<%--		function checkForm(){--%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("�û���������Ϊ��");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			var pattern = /(^(\d{2,4}[-_����]?)?\d{3,8}([-_����]?\d{3,8})?([-_����]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--		 	var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_age=document.forms[0].cust_age.value;--%>
<%--			--%>
<%--		 	if (cust_age!=""&&!pattern1.test(cust_age)) {--%>
<%--		 		alert("�������Ϊ�Ϸ�����");--%>
<%--		 		document.forms[0].cust_age.focus();--%>
<%--		 		document.forms[0].cust_age.select();--%>
<%--		 		return false;--%>
<%--		 	}		--%>
<%--		 	if (home_tel!=""&&!pattern.test(home_tel)) {--%>
<%--		 		alert("����ȷ��дסլ�绰���룡");--%>
<%--		 		document.forms[0].cust_tel_home.focus();--%>
<%--		 		document.forms[0].cust_tel_home.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (mob_tel!=""&&!pattern.test(mob_tel)) {--%>
<%--		 		alert("����ȷ��д�ֻ��绰���룡");--%>
<%--		 		document.forms[0].cust_tel_mob.focus();--%>
<%--		 		document.forms[0].cust_tel_mob.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (work_tel!=""&&!pattern.test(work_tel)) {--%>
<%--		 		alert("����ȷ��д�����绰���룡");--%>
<%--		 		document.forms[0].cust_tel_work.focus();--%>
<%--		 		document.forms[0].cust_tel_work.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		    var pattern2 = /^.+@.+\..+$/;--%>
<%--			if (email!=""&&!pattern2.test(email)) {--%>
<%--				alert("����ȷ��дemail");--%>
<%--				document.forms[0].cust_email.focus();--%>
<%--				document.forms[0].cust_email.select();--%>
<%--				return false;--%>
<%--			}--%>
<%--            return true;--%>
<%--   		}--%>
<%--		var url = "phoneinfo.do?method=toPhoneOper&type=";--%>
		var selectUrl = "custinfo/phoneinfo.do?method=toPhoneLoad&custType=";
		function selectType()
		{
			var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			selectUrl = "phoneinfo.do?method=toPhoneLoad&type="+operType+"&custType=";
			document.forms[0].action = selectUrl + type;
			document.forms[0].submit();
		}
		
<%--	//���--%>
<%--   	function add(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--  		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "update";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   		--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();--%>
<%--   		--%>
<%--   		}--%>
<%--   	}--%>
	
</script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	</head>
	<%	String opertype = (String) request.getAttribute("opertype"); %>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		��ǰλ��&ndash;&gt;����û���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴�û���Ϣ</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		��ǰλ��&ndash;&gt;�޸��û���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		��ǰλ��&ndash;&gt;ɾ���û���Ϣ--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>			
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td colspan="2" class="labelStyle">
						ѡ���û�����
					</td>
					<td colspan="2" class="valueStyle">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle" onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" size="8" styleClass="writeTypeStyle" styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_age"  styleClass="writeTypeStyle" size="3" styleId="cust_age"/>
<%--						<div id="cust_ageTip" style="width: 10px;display:inline;"></div>--%>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						ר�����
					</td>
					<td class="valueStyle">
						<html:select property="expert_type" styleClass="writeTypeStyle" styleId="expert_type">
							<html:option value="">��ѡ��</html:option>
							<html:options collection="expertList" property="value"
								labelProperty="label" styleClass="writeTypeStyle"/>
						</html:select>
						<div id="expert_typeTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						
					</td>
					<td class="valueStyle">
						
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�Ļ��̶�
					</td>
					<td class="valueStyle">
						<html:text property="cust_degree" styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						������ҵ
					</td>
					<td class="valueStyle">
						<html:text property="dict_cust_voc" styleClass="writeTypeStyle" styleId="dict_cust_voc"/>
						<div id="dict_cust_vocTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						���ڵ�λ
					</td>
					<td class="valueStyle">
						<html:text property="cust_unit"  styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						ְ��ְ��
					</td>
					<td class="valueStyle">
						<html:text property="cust_duty" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						סլ�绰
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_home" styleClass="writeTypeStyle" size="13" styleId="cust_tel_home"/>
						<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" styleClass="writeTypeStyle" size="11" styleId="cust_tel_mob"/>
						<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�칫�绰
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_work" styleClass="writeTypeStyle" size="13" styleId="cust_tel_work"/>
						<div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_fax" styleClass="writeTypeStyle"  styleId=""/>
<%--						<div id="Tip" style="width: 10px;display:inline;"></div>--%>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						ͨ�ŵ�ַ
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr"  styleClass="writeTypeStyle" styleId="cust_addr"/>
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" styleClass="writeTypeStyle" styleId="cust_email"/>
						<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						������ҳ
					</td>
					<td class="valueStyle">
						<html:text property="cust_homepage" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;Ƭ
					</td>
					<%
		    			String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_id");
		    			//System.out.println("id == "+id);
		    			String p = "phoneinfo/show2.jsp?t=oper_custinfo.cust_id&id="+id;
		    			//System.out.println("p == "+p);
		    		 %>
		    		<logic:equal name="opertype" value="detail">
		    			 <td class="valueStyle" colspan="3">
		    			 	<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
		    			 </td>
		    		</logic:equal>
		    		<logic:equal name="opertype" value="delete">
		    			 <td class="valueStyle" colspan="3">
		    			 	<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
		    			 </td>
		    		</logic:equal>
					<logic:equal name="opertype" value="insert">
						<td class="valueStyle" colspan="3">
							<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<td class="valueStyle" colspan="3">
							<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
						</td>
					</logic:equal>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center">
						
<%--						<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="Submit" value=" �� �� "  onclick="add()" class="buttonStyle">--%>
<%--						</logic:equal>--%>
<%--						--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="Submit" value=" ȷ �� " onClick="update()" class="buttonStyle">--%>
<%--						</logic:equal>--%>
<%--						--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="Submit" value=" ɾ �� " onClick="del()"class="buttonStyle">--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value=" �� �� " class="buttonStyle">
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()" class="buttonStyle">
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
