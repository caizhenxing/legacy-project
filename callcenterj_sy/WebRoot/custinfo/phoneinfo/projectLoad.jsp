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
				$("#cust_name").formValidator({onshow:"��������ҵ����",onfocus:"��ҵ���Ʋ���Ϊ��",oncorrect:"��ҵ���ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ�������߲����пշ���"},onerror:"��ҵ���Ʋ���Ϊ��"});
<%--				$("#enterprise_intru").formValidator({onshow:"��������ҵ���",onfocus:"��ҵ��鲻��Ϊ��",oncorrect:"��ҵ���Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ������߲����пշ���"},onerror:"��ҵ��鲻��Ϊ��"});
				$("#work_item").formValidator({onshow:"�����뾭Ӫ��Ŀ",onfocus:"��Ӫ��Ŀ����Ϊ��",oncorrect:"��Ӫ��Ŀ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ӫ��Ŀ���߲����пշ���"},onerror:"��Ӫ��Ŀ����Ϊ��"});
				$("#cust_way_by").formValidator({onshow:"��ѡ��������ҵ",onfocus:"������ҵ����ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ��������ҵ"});
				$("#cust_addr").formValidator({onshow:"��������ҵ��ַ",onfocus:"��ҵ��ַ����Ϊ��",oncorrect:"��ҵ��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ��ַ���߲����пշ���"},onerror:"��ҵ��ַ����Ϊ��"});
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������벻��Ϊ��"});--%>
				$("#custLinkmanName").formValidator({onshow:"��������ϵ������",onfocus:"��ϵ����������Ϊ��",oncorrect:"��ϵ�������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ���������߲����пշ���"},onerror:"��ϵ����������Ϊ��"});
<%--				$("#cust_duty").formValidator({onshow:"��������ϵ��ְ��",onfocus:"��ϵ��ְ����Ϊ��",oncorrect:"��ϵ��ְ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ��ְ�����߲����пշ���"},onerror:"��ϵ��ְ����Ϊ��"});
				
				$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"סլ�绰��ʽ����ȷ"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});
				$("#cust_tel_work").formValidator({onshow:"������칫�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"�칫�绰�Ϸ�"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�칫�绰��ʽ����ȷ"});
				$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"��������ҵ����",onfocus:"��ҵ���Ʋ���Ϊ��",oncorrect:"��ҵ���ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ�������߲����пշ���"},onerror:"��ҵ���Ʋ���Ϊ��"});
<%--				$("#enterprise_intru").formValidator({onshow:"��������ҵ���",onfocus:"��ҵ��鲻��Ϊ��",oncorrect:"��ҵ���Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ������߲����пշ���"},onerror:"��ҵ��鲻��Ϊ��"});
				$("#work_item").formValidator({onshow:"�����뾭Ӫ��Ŀ",onfocus:"��Ӫ��Ŀ����Ϊ��",oncorrect:"��Ӫ��Ŀ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ӫ��Ŀ���߲����пշ���"},onerror:"��Ӫ��Ŀ����Ϊ��"});
				$("#cust_way_by").formValidator({onshow:"��ѡ��������ҵ",onfocus:"������ҵ����ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ��������ҵ"});
				$("#cust_addr").formValidator({onshow:"��������ҵ��ַ",onfocus:"��ҵ��ַ����Ϊ��",oncorrect:"��ҵ��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ҵ��ַ���߲����пշ���"},onerror:"��ҵ��ַ����Ϊ��"});
				$("#cust_pcode").formValidator({onshow:"��������������",onfocus:"�������벻��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������벻��Ϊ��"});--%>
				$("#custLinkmanName").formValidator({onshow:"��������ϵ������",onfocus:"��ϵ����������Ϊ��",oncorrect:"��ϵ�������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ���������߲����пշ���"},onerror:"��ϵ����������Ϊ��"});
<%--				$("#cust_duty").formValidator({onshow:"��������ϵ��ְ��",onfocus:"��ϵ��ְ����Ϊ��",oncorrect:"��ϵ��ְ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϵ��ְ�����߲����пշ���"},onerror:"��ϵ��ְ����Ϊ��"});
				
				$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"סլ�绰��ʽ����ȷ"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});
				$("#cust_tel_work").formValidator({onshow:"������칫�绰",onfocus:"��ȷ��ʽ��02487654321",oncorrect:"�칫�绰�Ϸ�"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"�칫�绰��ʽ����ȷ"});
				$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
			
			})
			</logic:equal>
		
		
<%--		function checkForm(phoneinfo){--%>
<%--			var f = document.getElementById("cust_name").value;--%>
<%--			var f1 = document.getElementById("work_item").value;--%>
<%--			var f2 = document.getElementById("cust_tel_work").value;--%>
<%--            if (f.length == 0){alert("��λ���Ʋ���Ϊ�գ�����ȷ��д��");return false;}--%>
<%--            if (f1.length == 0){alert("��Ӫ��Ŀ����Ϊ�գ�����ȷ��д��"); return false;}--%>
<%--            if (f2.length == 0){alert("�칫�绰����Ϊ�գ�����ȷ��д��"); return false;}--%>
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
<%--   		var f =document.forms[0];--%>
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
	<%	String opertype = (String) request.getAttribute("opertype");	%>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
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
					<td class="valueStyle">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle"
							onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" >
						��ҵ����
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" size="15" styleClass="writeTypeStyle" styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��ҵ��ģ
					</td>
					<td class="valueStyle">
						<html:text property="dict_cust_scale"  
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��ҵ���
					</td>
					<td class="valueStyle">
						<html:text property="enterprise_intru" styleClass="writeTypeStyle" styleId="enterprise_intru"/>						
					</td>
					<td class="labelStyle">
						��Ӫ��Ŀ
					</td>
					<td class="valueStyle">
						<html:text property="work_item" size="12" styleClass="writeTypeStyle"  styleId="work_item"/>
						<div id="work_itemTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��ҵ��վ
					</td>
					<td class="valueStyle">
						<html:text property="enterprise_net"   styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						������ҵ
					</td>
					<td class="valueStyle">
						<html:select property="cust_way_by" styleClass="writeTypeStyle" styleId="cust_way_by">
							<html:option value="">��ѡ��</html:option>
							<html:options collection="hangyelist" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
						<div id="cust_way_byTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��ҵ��ַ
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr" styleClass="writeTypeStyle" styleId="cust_addr"/>
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" size="6" styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��ϵ������
					</td>
					<td class="valueStyle">
						<html:text property="custLinkmanName"  styleClass="writeTypeStyle" styleId="custLinkmanName"/>
						<div id="custLinkmanNameTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						��ϵ��ְ��
					</td>
					<td class="valueStyle">
						<html:text property="cust_duty" size="20" styleClass="writeTypeStyle" styleId="cust_duty"/>
						<div id="cust_dutyTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�ƶ��绰
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" size="11" styleClass="writeTypeStyle" styleId="cust_tel_mob"/>
						<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						�칫�绰
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_work" size="13" styleClass="writeTypeStyle" styleId="cust_tel_work"/>
						<div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" styleClass="writeTypeStyle" styleId="cust_email"/>
						<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
						<br>
					</td>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_fax" size="20"
							styleClass="writeTypeStyle" />
						<br>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��λ��ʶ
					</td>
					<%
		    			String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_id");
		    			//System.out.println("id == "+id);
		    			String p = "phoneinfo/show.jsp?t=oper_custinfo.cust_id&id="+id;
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
						<%--
							if (opertype != null && !"".equals(opertype.trim())){
								if ("insert".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" �� �� " onClick="add()"
							class="buttonStyle">
						<%
								}else if ("update".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" ȷ �� "
							onClick="update()" class="buttonStyle">
						<%
								}else if ("delete".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" ɾ �� " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						--%>
						<input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value=" �� �� "
							class="buttonStyle">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value=" �� �� "
							onClick="javascript:window.close()" class="buttonStyle">
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
