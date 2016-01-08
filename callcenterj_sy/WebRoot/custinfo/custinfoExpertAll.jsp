<%@ page language="java"
	import="java.util.List,excellence.framework.base.dto.impl.DynaBeanDTO"
	pageEncoding="gbk" contentType="text/html; charset=gbk"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title>����ר���б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript">
		var xmlHttp;
		function createXMLHttpRequest(){
 			if(window.ActiveXObject)
 			{
  				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
 			}
 			else if(window.XMLHttpRequest)
 			{
  				xmlHttp=new XMLHttpRequest();
 			}
		}
<%--	function createQueryString()--%>
<%--	{--%>
<%-- 		var firstName=document.getElementById("firstname").value;--%>
<%-- 		var middleName=document.getElementById("middleName").value;--%>
<%-- 		var birthday=document.getElementById("birthday").value;--%>
<%-- 		var queryString="firstName=" + firstName + "&middleName=" + middleName + "&birthday=" + birthday;--%>
<%-- 		return queryString;--%>
<%--	}--%>
	function doRequestUsingGET(type,str){	
		var ntype = type;
 		createXMLHttpRequest();
 		var queryString = '';
 		
 		if(str==''){
 			queryString="../ThreeCall?qtype="+ntype+"&timeStamp=" + new Date().getTime();
 		}else{
 			queryString="../ThreeCall?qtype="+ntype+"&str="+str+"&timeStamp=" + new Date().getTime();
 		}

 		xmlHttp.onreadystatechange=handleStateChange;
 		xmlHttp.open("GET",queryString,true);
 		xmlHttp.send(null);
	}
	
	function doRequestUsingPost(){
 		createXMLHttpRequest();
 		var url="../ThreeCall?timeStamp=" + new Date().getTime();
 		xmlHttp.open("POST",url,true);
 		xmlHttp.onreadystatechange=handleStateChange;
 		xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
 		xmlHttp.send(null);
	}
	
	function handleStateChange(){
 		if(xmlHttp.readyState==4)
 		{
  			if(xmlHttp.status==200)
  			{
   				parseResults();
  			}
 		}
	}
	
	function parseResults(){
			var responseText= xmlHttp.responseText;
			var s = responseText.split(":");
			if(s[0]=='all'){
				var spanstr = responseText;
				var spanSlt = spanstr.split(",");
				var span1 = document.getElementById('sp1');
				var span2 = document.getElementById('sp2');
				var span3 = document.getElementById('sp3');
				span1.innerHtml = spanSlt[0];
				span2.innerHtml = spanSlt[1];
				span3.innerHtml = spanSlt[2];
			}
			if(s[0]=='three'){
				var threeStr = responseText;
				var slt = document.getElementById('incommingSelect');
				//���slt
				var length = slt.length;
				if(length>0)
				{
					for(var i=1; i<length; i++)
					{
						slt.options[1] = null;
					}
				}
				var sArr = threeStr.split(":");
				for(var i=0; i<sArr.length; i++)
				{
					var douArr = sArr[i].split(",");
					if(douArr!='three'){
						slt.options[slt.length] = new Option(douArr[0], douArr[1]);
					}
				}
			}
<%-- 		var responseDiv=document.getElementById("serverResponse");--%>
<%-- 		if(responseDiv.hasChildNodes())--%>
<%-- 		{--%>
<%--  			responseDiv.removeChild(responseDiv.childNodes[0]);--%>
<%-- 		}--%>
<%-- 		var responseText=document.createTextNode(xmlHttp.responseText);--%>
<%-- 		responseDiv.appendChild(responseText);--%>
	}

		//��������
		function goCallThree(tel){
				opener.document.getElementById('textAgentCallThree').value=tel;	
				opener.document.getElementById("btnAgentCallThree").click();
		}
		//����
		function listen(){
			alert("jianting()");
		}
		//ǿ��
		function insert(){
			alert("qiangcha()");
		}
		//ǿ��
		function remove(){
			alert("qiangchai()");
		}
		//����ȫ��
		function threeHold(){
			alert('three on hook');
			opener.document.getElementById("threeonhook").click();
			//doRequestUsingGET('threehold','');threeonhook
		}
		//�Ҷ���ϯ
		function agentHold(){
			doRequestUsingGET('agenthold','');
			//opener.document.getElementById("btnOnHook").click();
		}
		//ר�ҹҶ�
		function expertHold(){
			doRequestUsingGET('experthold','');
		}
		//ר���б�
		function getExpertList(){
			doRequestUsingGET('expertlist','');
		}
		//�õ�ר�ң���ϯ��������Ա����Ϣ
		function getAllInfo(){
			doRequestUsingGET('allinfo',document.getElementById('incommingSelect').value);
		}
	</script>


</head>

<body class="listBody">
	<html:form action="custinfo/custinfo.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle">
					����ͨ����������
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="evenStyle">
					����-ר��ͨ�����
				</td>
				<td class="evenStyle">
					����
				</td>
			</tr>
			<tr>
				<td class="oddStyle">
					<select name="incommingSelect" id="incommingSelect"
						onmouseover="getExpertList()" onchange="getAllInfo()">
						<option value="0">
							��ѡ��...
						</option>
					</select>
				</td>
				<td class="oddStyle">
<%--					<input type="button" name="jianting" value="����" onclick="listen()" />--%>
<%--					<input type="button" name="qiangcha" value="ǿ��" onclick="insert()" />--%>
<%--					<input type="button" name="qiangchai" value="ǿ��" onclick="remove()" />--%>
				</td>
			</tr>
			<tr>
				<td class="evenStyle">
					��Ϣ��¼
				</td>
				<td class="evenStyle">
					����
				</td>
			</tr>
			<tr>
				<td class="oddStyle">
					<table width="400">
					<tr>
					<td class="oddStyle">
					ר����Ϣ
					</td>
					<td class="oddStyle">
					<span id="sp1"></span>
					</td>
					</tr>
					<tr>
					<td class="oddStyle">
					�û���Ϣ
					</td>
					<td>
					<span id="sp2"></span>
					</td>
					</tr>
					<tr>
					<td class="oddStyle">
					��ϯ��Ϣ
					</td>
					<td class="oddStyle">
					<span id="sp3"></span>
					</td>
					</tr>
					</table>
				</td>
				<td class="oddStyle">
					<input type="button" name="shanfanggua" value="����ȫ��"
						onclick="threeHold()" />
<%--					<input type="button" name="guazhuoxi" value="�Ҷ���ϯ"--%>
<%--						onclick="agentHold()" />--%>
<%--					<input type="button" name="guazhuanjia" value="�Ҷ�ר��"--%>
<%--						onclick="expertHold()" />--%>
				</td>
			</tr>
		</table>



		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle">
					����ר���б�
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="10%">
					ר�����
				</td>
				<td class="listTitleStyle" width="15%">
					����
				</td>
				<td class="listTitleStyle" width="15%">
					լ��
				</td>
				<td class="listTitleStyle" width="15%">
					�ֻ�
				</td>
				<td class="listTitleStyle" width="15%">
					�칫�绰
				</td>
			</tr>

			<%
				List list = (List) request.getAttribute("list");
				for (int i = 0; i < list.size(); i++) {
					DynaBeanDTO dto = (DynaBeanDTO) list.get(i);

					String cust_name = (String) dto.get("cust_name");
					String cust_tel_home = (String) dto.get("cust_tel_home");
					String cust_tel_mob = (String) dto.get("cust_tel_mob");
					String cust_tel_work = (String) dto.get("cust_tel_work");
					String expert_type = (String) dto.get("expert_type");
					String tel_mob = "";
					String tel_home = "";
					String tel_work = "";
					//System.out.println(cust_name + "::" + cust_tel_home + ":"
					//+ cust_tel_mob + ":" + cust_tel_work);
					if (cust_tel_mob != null && !cust_tel_mob.equals("")) {
						tel_mob = "<a href=\"javascript:goCallThree('"
						+ cust_tel_mob + "')\" >" + cust_tel_mob + "</a>";
					}
					if (cust_tel_home != null && !cust_tel_home.equals("")) {
						tel_home = "<a href=\"javascript:goCallThree('"
						+ cust_tel_home + "')\" >" + cust_tel_home + "</a>";
					}
					if (cust_tel_work != null && !cust_tel_work.equals("")) {
						tel_work = "<a href=\"javascript:goCallThree('"
						+ cust_tel_work + "')\" >" + cust_tel_work + "</a>";

					}
					String style = i % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr>
				<td >
					<%=expert_type%>
				</td>
				<td >
					<%=cust_name%>
				</td>
				<td >
					<%=tel_home%>
				</td>
				<td >
					<%=tel_mob%>
				</td>
				<td >
					<%=tel_work%>
				</td>
			</tr>
			<%
			}
			%>
		</table>
	</html:form>
</body>
</html:html>
