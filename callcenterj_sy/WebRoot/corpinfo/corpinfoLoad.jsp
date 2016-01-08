<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>��ҵ����Ϣ����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<style type="text/css">

#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}

</style>	
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
<SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../js/common.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/form.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript></SCRIPT>

<!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- jquery��֤ end -->

<script type="text/javascript">	
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
        	if (!checkNotNull(addstaffer.corpRid,"������")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
        	
			if (!checkTelNumber(addstaffer.custTel)) return false;
        	
           return true;
        }
				function addinfo()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{
			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();
				opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
			}
		}
		
		//��ʼ��
		function init(){
			<c:choose>
				<c:when test="${opertype=='insert'}">
					document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=insert";
					document.getElementById('buttonSubmit').value="���";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=update";
					document.getElementById('buttonSubmit').value="�޸�";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";
					document.getElementById('buttonSubmit').value="ɾ��";
				</c:when>
				<c:when test="${opertype=='detail'}">
					document.getElementById('spanHead').innerHTML="�鿴��Ϣ";
<%--					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";--%>
					document.getElementById('buttonSubmit').style.display="none"
				</c:when>
			</c:choose>
		}
		
		//ִ����֤		
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"operCorpinfoId",onerror:function(msg){alert(msg)}});
			$("#corpRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#custName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#custAddr").formValidator({onshow:"�������û���ַ",onfocus:"�û���ַ����Ϊ��",oncorrect:"�û���ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û���ַ���߲����пշ���"},onerror:"�û���ַ����Ϊ��"});
			$("#dictServiceType").formValidator({empty:false,onshow:"��ѡ���������",onfocus:"�������ͱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ���������"});			
			$("#createTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#contents").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#reply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"operCorpinfoId",onerror:function(msg){alert(msg)}});
			$("#corpRid").formValidator({onshow:"������������",onfocus:"�����Ų���Ϊ��",oncorrect:"�����źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ų���Ϊ��"});
			$("#custName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});				
			$("#custTel").formValidator({onshow:"�������û��绰",onfocus:"�û��绰����Ϊ��",oncorrect:"�û��绰�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��绰���߲����пշ���"},onerror:"�û��绰����Ϊ��"});
			$("#custAddr").formValidator({onshow:"�������û���ַ",onfocus:"�û���ַ����Ϊ��",oncorrect:"�û���ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û���ַ���߲����пշ���"},onerror:"�û���ַ����Ϊ��"});
			$("#dictServiceType").formValidator({empty:false,onshow:"��ѡ���������",onfocus:"�������ͱ���ѡ��",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "û��ѡ���������"});			
			$("#createTime").formValidator({onshow:"����������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
			$("#contents").formValidator({onshow:"��������ѯ����",onfocus:"��ѯ���ݲ���Ϊ��",oncorrect:"��ѯ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ѯ�������߲����пշ���"},onerror:"��ѯ���ݲ���Ϊ��"});
			$("#reply").formValidator({onshow:"���������ߴ�",onfocus:"���ߴ𸴲���Ϊ��",oncorrect:"���ߴ𸴺Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���ߴ����߲����пշ���"},onerror:"���ߴ𸴲���Ϊ��"});
			
			})
			</c:when>
		</c:choose>
			</script>

  </head>
  
  <body onunload="toback()" class="loadBody" onload="init()">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/operCorpinfo.do" method="post" styleId="operCorpinfoId">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="navigateStyle">
		    	<span id="spanHead">�鿴��Ϣ</span>
<%--		    	<logic:equal name="opertype" value="insert">--%>
<%--		    		�����Ϣ--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="detail">--%>
<%--		    		�鿴��Ϣ--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="update">--%>
<%--		    		�޸���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--		    	 <logic:equal name="opertype" value="delete">--%>
<%--		    		ɾ����Ϣ--%>
<%--		    	</logic:equal>--%>
		    </td>
		  </tr>
		</table>
  
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
    			<tr>
    			
	    			<td class="labelStyle">
	    				������
	    			</td>
	    			<td class="valueStyle">
						<html:text property="corpRid" styleClass="writeTextStyle" readonly="true" styleId="corpRid"/>
						<div id="corpRidTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				����ר��
	    			</td>
	    			<td class="valueStyle">
						<html:text property="expert" styleClass="writeTextStyle"/>
	    			</td>
	    			<td class="labelStyle" rowspan="4" width="180" style="text-indent: 0px;">
	    			<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No"
						src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No"
						src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					</td>
	    		</tr>
	    	<tr>
	    		<td class="labelStyle">
	    				�û�����
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custName" styleClass="writeTextStyle" styleId="custName"/>
						<div id="custNameTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				�û��绰
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel"/>
						<div id="custTelTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				�û���ַ
	    			</td>
	    			<td class="valueStyle">
						<html:text property="custAddr" styleClass="writeTextStyle" styleId="custAddr"/>						
						<input name="add" type="button" id="add" value="ѡ��"
					onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					class="buttonStyle" />
					<div id="custAddrTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				���״̬
	    			</td>
	    			<td class="valueStyle">
	    			<logic:equal name="opertype" value="insert">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=insert"/>
					</logic:equal>
					<logic:equal name="opertype" value="detail">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=detail"/>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=update"/>
					</logic:equal>
					<logic:equal name="opertype" value="delete">
					<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean&opertype=delete"/>
					</logic:equal>
<%--						<jsp:include flush="true" page="../flow/incState.jsp?form=operCorpinfoBean"/>--%>
	    			</td>
	    		</tr>
	    		<tr>
	    		<td class="labelStyle">
	    				��������
	    			</td>
	    			<td class="valueStyle">
						<html:select property="dictServiceType" styleClass="selectStyle" styleId="dictServiceType">
	    				<html:option value="">��ѡ��</html:option>
	    				<html:options collection="ServiceList" property="value" labelProperty="label"/>
	    				</html:select>
	    				<div id="dictServiceTypeTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<td class="labelStyle">
	    				����ʱ��
	    			</td>
	    			<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" styleId="createTime" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('operCorpinfoBean','createTime',false);">
					<div id="createTimeTip" style="width: 0px;display:inline;"></div>	
				    </td>
	    		</tr>		
	    		<tr>
	    			<td class="labelStyle">
	    				��ѯ����
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="contents" styleClass="writeTextStyle" cols="80" rows="5" styleId="contents"/>
						<div id="contentsTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    			<%
					String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("operCorpinfoBean")).get("id");
					String p = "../upload/show2.jsp?t=oper_corpinfo.id&id=" + id;
					%>
					<td class="labelStyle" rowspan="3" width="180" height="100%" style="text-indent: 0px;">
					  <logic:equal name="opertype" value="insert">
						<iframe frameborder="0" width="100%" height="100%" scrolling="auto" src="<%=p %>"></iframe>
					  </logic:equal>
					  <logic:equal name="opertype" value="update">
						<iframe frameborder="0" width="100%" height="100%" scrolling="auto" src="<%=p %>"></iframe>
					  </logic:equal>
					</td>
	    		</tr>
	    		
	    		<tr>
	    		
	    		<td class="labelStyle">
	    				���ߴ�
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="reply" styleClass="writeTextStyle" cols="80" rows="5" styleId="reply"/>
						<div id="replyTip" style="width: 0px;display:inline;"></div>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				��&nbsp;&nbsp;&nbsp;&nbsp;ע
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:textarea property="remark" styleClass="writeTextStyle" cols="80" rows="5" />
	    			</td>
	    		</tr>
	    		
    		<tr>
    			<td colspan="7" align="center" class="buttonAreaStyle">
<%--    			<logic:equal name="opertype" value="insert">--%>
<%--    				<input type="button" name="btnAdd"   value="���" onclick="addinfo()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="update">--%>
<%--    				<input type="button" name="btnUpdate"    value="�޸�" onclick="update()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="delete">--%>
<%--    				<input type="button" name="btnDelete"    value="ɾ��" onclick="del()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
					
						<input type="submit" name="addbtn" value="���" id="buttonSubmit"
							class="buttonStyle" style="display:inline" />
    			
    				<input type="button" name="" value="�ر�"    onClick="javascript:window.close();" class="buttonStyle"/>
    			
    			</td>
    		</tr>
			<html:hidden property="id"/>
    	</table>
    	</html:form>
  </body>
</html:html>
<script>

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
	function sendRequest(url,value) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//ָ����Ӧ����
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

</script>
