<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.eclipse.swt.internal.extension.Extension" %>
<%@ page import="java.text.NumberFormat" %>
<%@ include file="../style.jsp"%>
<%
String b = request.getParameter("b");
if(b!=null){
	int usages = Extension.GetCpuUsages();				//CPUʹ����
	int avail = Extension.GlobalMemoryStatus().dwAvailPhys / 1024;	//�ڴ������
out.print(usages+","+avail);
}else{
	int total = Extension.GlobalMemoryStatus().dwTotalPhys / 1024;	//�ڴ�����
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
</head>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<body class="listBody">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;ϵͳ���
					</td>
				</tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
				<tr>
					<td class="labelStyle" width="10%">CPUʹ����</td>
					<td class="valueStyle" width="10%"><span id="usages"></span></td>
					<td class="labelStyle" width="10%">�ڴ������</td>
					<td class="valueStyle" width="10%"><span id="avail"></span>&nbsp;KB</td>
					<td class="labelStyle" width="10%">�ڴ�����</td>
					<td class="valueStyle" width="10%"><span id="total"><%= total %></span>&nbsp;KB</td>
				
				</tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						Ӳ����Ϣ
					</td>
				</tr>
		</table>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
  <tr>
    <td class="listTitleStyle">���ش���</td>
    <td class="listTitleStyle">�ܴ�С</td>
    <td class="listTitleStyle">���ÿռ�</td>
  </tr>
<%
	NumberFormat nf = NumberFormat.getInstance();	//���ص�ǰĬ�����Ի�����ͨ�����ָ�ʽ
		nf.setMaximumFractionDigits(2);				//��������С�����ֵ����Ϊ2λ��
		nf.setMinimumFractionDigits(0);				//��������С�������������Сλ��
		
String[] drives = Extension.GetLogicalDrives();
for (int i = 0; i < drives.length; i++) {
	String disk = drives[i];				//ȡ����
	if(Extension.GetDriveType(disk) == 3){	//�ж��Ƿ����
		String diskTotal = nf.format(((float)Extension.GetDiskFreeSpace(disk).totalNumberOfBytes)/1024/1024/1024);
		String diskFree = nf.format(((float)Extension.GetDiskFreeSpace(disk).totalNumberOfFreeBytes)/1024/1024/1024);

	String style = i % 2 == 0 ? "oddStyle": "evenStyle";
%>
  <tr>
    <td >( <%= disk %> )</td>
    <td ><%= diskTotal %> GB</td>
    <td ><%= diskFree %> GB</td>
  </tr>
<%
	}
}
%>
</table>

</body>
</html>
<script>  
changesrc();
function changesrc(){
	sendRequest("?b=y",null);
	window.setTimeout(changesrc,1000);//������Ļˢʱ����Ϊ2000���룬�����޸�
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
            	var res = XMLHttpReq.responseText;
				//window.alert(res); 
				var str = res.split(",");
				document.getElementById("usages").innerHTML = str[0] + " %";
				document.getElementById("avail").innerHTML = str[1];
				     
            } else { //ҳ�治����
                //window.alert("���������ҳ�����쳣��");
            }
        }
    }
</script>
<%
}
%>