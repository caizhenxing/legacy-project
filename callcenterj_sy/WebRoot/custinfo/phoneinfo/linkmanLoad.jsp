<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>
<%@ page import="java.util.*"%>
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
		<title>����Ա��Ϣ����</title>
		<script type='text/javascript' src='../../js/msg.js'></script>
		<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
   		<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></SCRIPT>
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
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=insert";
					document.getElementById('spanHead').innerHTML="�������Ա��Ϣ";
					document.getElementById('buttonSubmit').value=" �� �� ";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=update";
					document.getElementById('spanHead').innerHTML="�޸�����Ա��Ϣ";
					document.getElementById('buttonSubmit').value=" �� �� ";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=delete";
					document.getElementById('spanHead').innerHTML="ɾ������Ա��Ϣ";
					document.getElementById('buttonSubmit').value=" ɾ �� ";
					v_flag="del"
				</logic:equal>		
			}
			//ִ����֤
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
				$("#cust_idcard").formValidator({empty:false,onshow:"������15��18λ�����֤��",onfocus:"���֤�Ų���Ϊ��",oncorrect:"���֤����ȷ",onempty:"û���������֤��"}).regexValidator({regexp:"idcard",datatype:"enum",onerror:"��������֤�Ÿ�ʽ����ȷ"});
<%--				$("#cust_idcard").formValidator({empty:false,onshow:"���������֤��",onfocus:"���֤�Ų���Ϊ��",oncorrect:"���֤�źϷ�",onempty:"û����д���֤��"}).functionValidator({fun:isCardID});--%>
				$("#cust_addr").formValidator({onshow:"�������ַ",onfocus:"��ַ����Ϊ��",oncorrect:"��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ַ���߲����пշ���"},onerror:"��ַ����Ϊ��"});	
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:6,max:6,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�����������Ϊ��λ"});
				$("#cust_tel_home").formValidator({empty:false,onshow:"������绰",onfocus:"��:02423448833���޸��ߣ���������������绰�ö��Ÿ���",oncorrect:"�绰�Ϸ�",onempty:"û����д�绰"}).regexValidator({regexp:"^((0|1)[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�绰��ʽ����ȷ"});
				$("#cust_way_bys").formValidator({onshow:"��ѡ���û����(����ѡһ��)",onfocus:"�û�������ѡ��",oncorrect:"�û����ѡ����ȷ",onempty:"û��ѡ���û����"}).inputValidator({min:1,empty:"û��ѡ���û����",onerror:"û��ѡ���û����"});
				$("#dict_cust_scale").formValidator({onshow:"��:��������ֳ300ͷ��������ֲ30Ķ",onfocus:"������Ŀ��������ģ����Ϊ��",oncorrect:"������Ŀ��������ģ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������Ŀ��������ģ���߲����пշ���"},onerror:"������Ŀ��������ģ����Ϊ��"});	
				$("#cust_work_ways").formValidator({onshow:"��ѡ������ʽ(����ѡһ��)",onfocus:"������ʽ����ѡ��",oncorrect:"������ʽѡ����ȷ",onempty:"û��ѡ������ʽ"}).inputValidator({min:1,empty:"û��ѡ������ʽ",onerror:"û��ѡ������ʽ"});
				$("#cust_develop_time").formValidator({onshow:"�����뷢չʱ��",onfocus:"��չʱ�䲻��Ϊ��",oncorrect:"��չʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��չʱ�����߲����пշ���"},onerror:"��չʱ�䲻��Ϊ��"});	
				$("#cust_job").formValidator({onshow:"��ѡ��������Ŀ",onfocus:"������Ŀ����Ϊ��",oncorrect:"������Ŀ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"������Ŀ����Ϊ��"});
					
					
<%--				$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});--%>
<%--				$("#cust_age").formValidator({empty:true,onshow:"���������䣬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"����",onempty:"û����д����"}).inputValidator({min:1,max:99,type:"value",onerrormin:"���������ڵ���1",onerrormax:"�������С�ڵ���99",onerror:"���������1-99֮��"});				--%>
				
				
<%--				$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"�칫�绰�Ϸ�",onempty:"û����д�칫�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�칫�绰��ʽ����ȷ"});--%>
<%--				$("#cust_email").formValidator({empty:false,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
				
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});
				$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
				$("#cust_idcard").formValidator({empty:false,onshow:"������15��18λ�����֤��",onfocus:"���֤�Ų���Ϊ��",oncorrect:"���֤����ȷ",onempty:"û���������֤��"}).regexValidator({regexp:"idcard",datatype:"enum",onerror:"��������֤�Ÿ�ʽ����ȷ"});
<%--				$("#cust_idcard").formValidator({empty:false,onshow:"���������֤��",onfocus:"���֤�Ų���Ϊ��",oncorrect:"���֤�źϷ�",onempty:"û����д���֤��"}).functionValidator({fun:isCardID});--%>
				$("#cust_addr").formValidator({onshow:"�������ַ",onfocus:"��ַ����Ϊ��",oncorrect:"��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ַ���߲����пշ���"},onerror:"��ַ����Ϊ��"});	
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:6,max:6,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�����������Ϊ��λ"});
				$("#cust_tel_home").formValidator({empty:false,onshow:"������绰",onfocus:"��:02423448833���޸��ߣ���������������绰�ö��Ÿ���",oncorrect:"�绰�Ϸ�",onempty:"û����д�绰"}).regexValidator({regexp:"^((0|1)[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�绰��ʽ����ȷ"});
				$("#cust_way_bys").formValidator({onshow:"��ѡ���û����(����ѡһ��)",onfocus:"�û�������ѡ��",oncorrect:"�û����ѡ����ȷ",onempty:"û��ѡ���û����"}).inputValidator({min:1,empty:"û��ѡ���û����",onerror:"û��ѡ���û����"});
				$("#dict_cust_scale").formValidator({onshow:"��:��������ֳ300ͷ��������ֲ30Ķ",onfocus:"������Ŀ��������ģ����Ϊ��",oncorrect:"������Ŀ��������ģ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"������Ŀ��������ģ���߲����пշ���"},onerror:"������Ŀ��������ģ����Ϊ��"});	
				$("#cust_work_ways").formValidator({onshow:"��ѡ������ʽ(����ѡһ��)",onfocus:"������ʽ����ѡ��",oncorrect:"������ʽѡ����ȷ",onempty:"û��ѡ������ʽ"}).inputValidator({min:1,empty:"û��ѡ������ʽ",onerror:"û��ѡ������ʽ"});
				$("#cust_develop_time").formValidator({onshow:"�����뷢չʱ��",onfocus:"��չʱ�䲻��Ϊ��",oncorrect:"��չʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��չʱ�����߲����пշ���"},onerror:"��չʱ�䲻��Ϊ��"});	
				$("#cust_job").formValidator({onshow:"��ѡ��������Ŀ",onfocus:"������Ŀ����Ϊ��",oncorrect:"������Ŀ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"������Ŀ����Ϊ��"});
			})
			</logic:equal>
			
			
<%--		function checkForm(){ --%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			var pattern = /(^(\d{2,4}[-_����]?)?\d{3,8}([-_����]?\d{3,8})?([-_����]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--			var pattern2 = /^.+@.+\..+$/;--%>
<%--			var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_age=document.forms[0].cust_age.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("�û���������Ϊ��");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--			--%>
<%--		 	if (cust_age!=""&&!pattern1.test(cust_age)) {--%>
<%--		 		alert("�������Ϊ�Ϸ�����");--%>
<%--		 		document.forms[0].cust_age.focus();--%>
<%--		 		document.forms[0].cust_age.select();--%>
<%--		 		return false;--%>
<%--		 	}			--%>
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
<%--		    --%>
<%--			if (email!=""&&!pattern2.test(email)) {--%>
<%--				alert("����ȷ��дemail");--%>
<%--				document.forms[0].cust_email.focus();--%>
<%--				document.forms[0].cust_email.select();--%>
<%--				return false;--%>
<%--			}         --%>
<%--            return true;--%>
<%--   		}--%>
<%--		var url = "phoneinfo.do?method=toLinkManOper&type=";--%>
<%--		var selectUrl = "custinfo/phoneinfo.do?method=toLinkManOper&custType=";--%>
<%--		--%>
<%--	//���--%>
<%--   	function add(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--    	if(checkForm()){--%>
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
	<% String opertype = (String) request.getAttribute("opertype"); %>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		��ǰλ��&ndash;&gt;�������Ա��Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴����Ա��Ϣ</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		��ǰλ��&ndash;&gt;�޸�����Ա��Ϣ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		��ǰλ��&ndash;&gt;ɾ������Ա��Ϣ--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_number" size="20" styleClass="writeTypeStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;ϯ&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_rid" size="20" styleClass="writeTypeStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" size="20" styleClass="writeTypeStyle"styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:select property="dict_sex" styleClass="writeTypeStyle" style="width:100px" styleId="dict_sex">
							<html:options collection="sexList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
						<div id="dict_sexTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						���֤��
					</td>
					<td class="valueStyle">
						<html:text property="cust_idcard" size="20" styleClass="writeTypeStyle" styleId="cust_idcard" />
						<div id="cust_idcardTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ַ
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr" size="20" styleClass="writeTypeStyle" styleId="cust_addr"/>
						<input type="button" name="btnadd" class="buttonStyle" value="ѡ��"
						onClick="window.open('../custinfo/phoneinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" size="20"
							styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_home" size="20" styleClass="writeTypeStyle" styleId="cust_tel_home"/>
						<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�û����
					</td>
					<td class="valueStyle">
<%--						<html:select property="cust_way_by" styleClass="writeTypeStyle" style="width:100px" styleId="cust_way_by">--%>
<%--							<html:options collection="leibielist" property="value"--%>
<%--								labelProperty="label" styleClass="writeTypeStyle" />--%>
<%--						</html:select>--%>
						ũҵ��<html:checkbox property="cust_way_bys" value="ũҵ��" styleId="cust_way_bys" />
						ũҵЭ��<html:checkbox property="cust_way_bys" value="ũҵЭ��" styleId="cust_way_bys"/>
						ũ��Ʒ������<html:checkbox property="cust_way_bys" value="ũ��Ʒ������" styleId="cust_way_bys"/>
						ũ�ʾ�����<html:checkbox property="cust_way_bys" value="ũ�ʾ�����" styleId="cust_way_bys"/>
						����<html:checkbox property="cust_way_bys" value="����" styleId="cust_way_bys"/>
						<div id="cust_way_bysTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="150">
						������Ŀ��������ģ
					</td>
					<td class="valueStyle">
						<html:text property="dict_cust_scale" size="40" styleClass="writeTypeStyle" styleId="dict_cust_scale"/>
						<div id="dict_cust_scaleTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						������ʽ
					</td>
					<td class="valueStyle">
<%--						<html:select property="cust_work_way" styleClass="writeTypeStyle" style="width:100px" styleId="cust_work_way">--%>
<%--							<html:option value="">��ѡ��</html:option>--%>
<%--							<html:options collection="workmethodlist" property="value"--%>
<%--								labelProperty="label" styleClass="writeTypeStyle" />--%>
<%--						</html:select>--%>
						����<html:checkbox property="cust_work_ways" value="����" styleId="cust_work_ways"/>
						�ط�<html:checkbox property="cust_work_ways" value="�ط�" styleId="cust_work_ways"/>
						<div id="cust_work_waysTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��չʱ��
					</td>
					<td class="valueStyle">
						<html:text property="cust_develop_time" styleClass="writeTextStyle" size="20" readonly="true" styleId="cust_develop_time"/>
						<img alt="ѡ������" src="../html/img/cal.gif"
							onclick="openCal('phoneinfo','cust_develop_time',false);">
						<div id="cust_develop_timeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�û���ע
					</td>
					<td class="valueStyle">
						<html:textarea property="remark" rows="5" cols="40"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<logic:equal name="opertype" value="insert">
				<tr>
					<td class="labelStyle">
						��Ŀ�б�
					</td>
					<td class="valueStyle">
						<select name="dictProductType1" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" multiple="multiple">
							<jsp:include flush="true" page="../select_product.jsp" />
						</select>&nbsp;
						<span id="dict_product_type2_span"> 
						<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" 
							multiple="multiple">
						</select>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;
						
						<span id="product_name_span">
						<select id="productName" name="productName" multiple="multiple"
							class="selectStyle" style="width:110px;height:110px"
							ondblclick="del(this)">
						</select>
						</span>&nbsp;&nbsp;
<%--						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>--%>
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="col()" class="labelStyle">ѡ����Ŀ</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="col()" class="labelStyle">ѡ����Ŀ</a>
						</logic:equal>
					</td>
				</tr>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<tr>
					<td class="labelStyle">
						��Ŀ�б�
					</td>
					<td class="valueStyle">
						<select name="dictProductType1" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" multiple="multiple">
							<jsp:include flush="true" page="../select_product.jsp" />
						</select>&nbsp;
						<span id="dict_product_type2_span"> 
						<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" 
							multiple="multiple">
						</select>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;
						
						<span id="product_name_span">
						<select id="productName" name="productName" multiple="multiple"
							class="selectStyle" style="width:110px;height:110px"
							ondblclick="del(this)">
							<%
							Object o = request.getAttribute("productlist");
							List list = new ArrayList();
							if(o != null)
								list = (List)o;
							for(int i=0,size=list.size(); i<size; i++){
								%><option value="<%=list.get(i)%>"><%=list.get(i)%></option><%
							}
							 %>
<%--							<html:options collection="productlist" styleClass="writeTypeStyle" />--%>
						</select>
						</span>&nbsp;&nbsp;
<%--						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>--%>
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="col()" class="labelStyle">ѡ����Ŀ</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="col()" class="labelStyle">ѡ����Ŀ</a>
						</logic:equal>
					</td>
				</tr>
				</logic:equal>
				
				<tr>
					<td class="labelStyle" width="150">
						������Ŀ
					</td>
					<td class="valueStyle">
						<html:text property="cust_job" size="40" styleClass="writeTypeStyle" 
								styleId="cust_job" readonly="true"/>&nbsp;
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="blank()" class="labelStyle">�����Ŀ</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="blank()" class="labelStyle">�����Ŀ</a>
						</logic:equal>
						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��̭���
					</td>
					<td class="valueStyle">
						<html:select property="is_eliminate" styleClass="writeTypeStyle" style="width:100px">
							<html:option value="">��ѡ��</html:option>
							<html:option value="yes">��</html:option>
							<html:option value="no">��</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��̭ԭ��
					</td>
					<td class="valueStyle">
						<html:text property="eliminate_reason" size="20" styleClass="writeTypeStyle" styleId="cust_tel_work"/>
					</td>
				</tr>
				
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center" class="buttonAreaStyle">
						<%--
								if (opertype != null && !"".equals(opertype.trim())) {
								if ("insert".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" �� �� " onClick="add()"
							class="buttonStyle">
						<%
						} else if ("update".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" ȷ �� "
							onClick="update()" class="buttonStyle">
						<%
						} else if ("delete".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" ɾ �� " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						--%>
						<input type="submit" name="button" id="buttonSubmit" value="�� ��" class="buttonStyle"/>

<%--						<input type="reset" name="Submit2" value=" �� �� "--%>
<%--							class="buttonStyle">--%>
<%--						&nbsp;&nbsp;&nbsp;&nbsp;--%>
						<input type="button" name="Submit3" value=" �� �� "
							onClick="javascript:window.close()" class="buttonStyle">
					</td>
				</tr>
			</table>
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
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
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
	
	function processResponse1() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("dict_product_type2_span").innerHTML = "<select name='dictProductType2' class='selectStyle'  onChange='select1(this)' style='width:110px;height:110px' multiple='multiple'>"+res+"</select>";
                
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
				document.getElementById("product_name_span").innerHTML = "<select id='productName' name='productName' class='selectStyle' style='width:110px;height:110px' multiple='multiple' ondblclick='del(this)'>"+res+"</select>";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

	var cust_way_by = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_way_by") %>";
	var cust_work_way = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_work_way") %>";
	var checkboxs = document.all.item("cust_way_bys");
	var checkboxs2 = document.all.item("cust_work_ways");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(cust_way_by.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
	if (checkboxs2!=null) {
		for (i=0; i<checkboxs2.length; i++) {
			if(cust_work_way.indexOf(checkboxs2[i].value)!=-1){
				checkboxs2[i].checked = true;
			}
		}
	}
	
	function del(obj) {
	    var l = document.getElementById("productName");
	    l.removeChild(l.options[obj.selectedIndex]);
	}
	
	function col() {
		var l = document.getElementById("productName");
		var count = l.options.length;
		var cjob = document.forms[0].cust_job.value;
		if(cjob != "")
			cjob += ",";
		for(var i=0; i<count; i++){
			var flag = 0;
			var cArray = cjob.split(',');
			for(var j=0; j<cArray.length; j++){
				if(cArray[j] == l[i].value)
					flag = 1;
			}
			if(flag == 0){
				cjob += l[i].value;
				cjob += ",";
			}
		}
		if(cjob != "")
			document.forms[0].cust_job.value = cjob.substring(0, cjob.length-1);
		else
			document.forms[0].cust_job.value = cjob;
	}
	
	function blank() {
		document.forms[0].cust_job.value = "";
	}
</script>
