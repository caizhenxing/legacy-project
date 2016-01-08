<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>
<%!public String gbk(String s) {
		try {
			s = new String(s.getBytes("ISO-8859-1"), "utf-8");
			return s;
		} catch (Exception e) {
			return ""; //���sΪnull�򷵻ؿմ�
		}
	}%>
<%
			ApplicationContext ac = (ApplicationContext) application
			.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
	BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String sid = gbk(request.getParameter("sid"));//ѡȡ�Ŀ�
	String svalue = gbk(request.getParameter("svalue"));//ѡȡ��ֵ

	String city = "";
	if (sid.equals("") && svalue.equals("")) {
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			rs = stmt
			.executeQuery("select distinct city from oper_address order by city");
			while (rs.next()) {
		String s = rs.getString("city");
		city += "<option value='" + s + "'>" + s + "</option>";
			}

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
			} catch (Exception e) {
		System.err.println(e);
			}
		}
%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>ѡ���ַ</title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body class="loadBody">
		<form name="address" method="post" action="">
			<table width="450" border="0" align="center">
				<tr>
					<td colspan="4" class="navigateStyle">
						��ѡ���ַ
					</td>
				</tr>
				<tr>
					<td class="valueStyle">
						<select name="city" id="city" onChange="select2(this)"
							class="selectStyle">
							<option value="">
								��ѡ����
							</option>
							<%--<%=city%>--%>
							<option value='����'>����</option>
							<option value='����'>����</option>
							<option value='��ɽ'>��ɽ</option>
							<option value='��˳'>��˳</option>
							<option value='��Ϫ'>��Ϫ</option>
							<option value='����'>����</option>
							<option value='����'>����</option>
							<option value='Ӫ��'>Ӫ��</option>
							<option value='����'>����</option>
							<option value='����'>����</option>
							<option value='����'>����</option>
							<option value='����'>����</option>
							<option value='�̽�'>�̽�</option>
							<option value='��«��'>��«��</option>
						</select>
					</td>
					<td id="sectiontd" class="valueStyle">
						<select name="section" id="section" class="selectStyle">
							<option>
								��ѡ����/��
							</option>
						</select>
					</td>
					<td id="villagestd" class="valueStyle">
						<select name="villages" id="villages" class="selectStyle">
							<option>
								��ѡ����/��
							</option>
						</select>
					</td>
					<td id="communitytd" class="valueStyle">
						<select name="community" id="community" class="selectStyle">
							<option>
								��ѡ������/��
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="buttonAreaStyle">
						<input type="button" name="button1" value="ѡ��" onClick="select1()"
							class="buttonStyle">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>
	
	function select1(){
		var city = document.address.city.value;
		var section = document.address.section.value;
		var villages = document.address.villages.value;
		var community = document.address.community.value;
		opener.document.forms[0].custAddr.value = city + section + villages + community;
		window.close();
	}
	
	var changeid = "";
	function select2(obj){
		var svalue = obj.options[obj.selectedIndex].value;
		var sid = obj.id;
		if(sid == "city"){
			changeid = "sectiontd";
		}else if(sid == "section"){
			changeid = "villagestd";
		}else if(sid == "villages"){
			changeid = "communitytd";
		}
		if(svalue != "" && sid != "community"){
			sendRequest("select_address.jsp", "sid="+sid+"&svalue="+svalue, processResponse);
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
	// ��������Ϣ����
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById(changeid).innerHTML = ""+res+"";
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }
	}

</script>
<%
		} else if (!sid.equals("") && !svalue.equals("")) {
		String selectid = "";
		String whereid = "";
		if (sid.equals("city")) {
			sid = "section_county";
			selectid = "section";
			whereid = "city";
		} else if (sid.equals("section")) {
			sid = "villages_and_towns";
			selectid = "villages";
			whereid = "section_county";
		} else if (sid.equals("villages")) {
			sid = "community_village";
			selectid = "community";
			whereid = "villages_and_towns";
		}

		String sql = "select distinct " + sid
		+ " from oper_address where " + whereid + " = '"
		+ svalue + "' order by " + sid;
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			String str = "";
			while (rs.next()) {
		String s = rs.getString(1);
		str += "<option value='" + s + "'>" + s + "</option>";
			}
			out
			.print("<select name='"
			+ selectid
			+ "' id='"
			+ selectid
			+ "' onChange='select2(this)' class='selectStyle'><option>��ѡ��</option>"
			+ str + "</select>");
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
			} catch (Exception e) {
		System.err.println(e);
			}
		}
	}
%>
