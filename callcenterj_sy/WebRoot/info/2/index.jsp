<%@ page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ϣ����</title>
<style type="text/css">

body {
	margin: 0px;
}
/* "ũ��ҳ��" üͷ */
.Farmer{
	background-image: url(images/Brow.jpg);
}
/* ��ר��ҳ�桱���⵼�� */
.Navigation{
	width: 100%;
	text-align: center;
	background-color: #93B6DE;
}
/* ����ɫ���֣����壬���� */
.Blue{
	font-family: "����_GB2312";
	font-size: 21px;
	font-weight: bolder;
	color: #003300;
	font-style: normal;
	text-align: center;
	vertical-align: bottom;
}
/* ��ɫ���֣����壬���� */
.Darkblue
{
	font-size: 20px;
	font-weight: bolder;
	color: #003366;
	font-family: "����_GB2312";
	text-align: center;
	vertical-align: bottom;
}
/* �ı����̵� */
.Text{
	font-family: "����";
	background-color: #85AAD6;
	font-size: 22px;
	font-weight: bold;
	color: #FFFF99;
	text-indent: 5px;
	text-align: left;
	vertical-align: bottom;
}
/* �ı������� */
.Text2{
	font-family: "����";
	background-color: #85AAD6;
	font-size: 22px;
	font-weight: bold;
	color: #FFFF99;
	text-indent: 2px;
	text-align: left;
}
/* ������ʾ */
.Data{
	background-color: #ECECEC;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #85AAD6;
	font-family: "����_GB2312";
	font-size: 20px;
	line-height: 24px;
	font-weight: bold;
	color: #003366;
	text-align: center;
}
/* ��ũ��ҳ�桰üͷ��div��ʽ��ʱ�䣩 */
.Time{
	font-size: 15px;
	font-weight: bold;
	text-align: center;
	margin-left: 970px;
	margin-right: 5px;
	margin-top: 12px;
}
.STYLE1 {color: #003366; font-family: "����_GB2312"; text-align: center; vertical-align: bottom; font-weight: bolder;}

</style>
</head>

<body>
<table width="1280" border="0" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td height="90" colspan="8" valign="top" class="Farmer">
    	<div class="Time" id="showtime"></div>
    </td>
  </tr>
  <tr class="Navigation">
    <td height="28"><img src="images/Human.jpg" width="22" height="20"></td>
    <td class="Blue">����ר��</td>
    <td class="Darkblue">��ϯר��</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">Ԭ����  ���ܷ�  ��Ӣ  ����ƽ  ����  �α���  ����Ƽ  ������  ����  �����  ��Ӣ  �ﾴ��</marquee></td>
    <td class="Darkblue">��ϯר��</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">�ſ���  ����Ө  ���  �¹���  ������  ��ҵ��  ������  ����ΰ  ���ĸ�  ����  ����  ʷ����  ���  �ֻݼ�  �ƴ���  ������  ��ΰ  ����  ������  ��Ȩ��  ������  �챦��  �׷���  ������  ������  �ι���  �Ӵ���  ������  ������</marquee></td>
    <td class="Darkblue">ֵ��ר��</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">��̷�  �  ����  ������  ��Ц΢  ����  ����</marquee></td>
  </tr>
  <tr  class="Navigation">
    <td height="28"><img src="images/Picture.jpg" width="21" height="17"></td>
    <td class="Blue">�����ע</td>
    <td colspan="6" class="Text2">
    	<iframe src="../../screen/screen.do?method=toJiaoDianAnliList" height="100%" width="100%" scrolling="no" frameborder="0" allowTransparency="true"></iframe>
    </td>
  </tr>
</table>
<span id="num">
<table width="1280" height="25" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="Data">
  	<td>��ѯ����</td>
    <td>��ȡ��</td>
    <td>������ѯ��</td>
    <td>��ȡ��</td>
    <td>����</td>
    <td>��ȡ��</td>
    <td>�г�</td>
    <td>��ȡ��</td>
    <td>����</td>
    <td>��ȡ��</td>
    <td>ҽ��</td>
    <td>��ȡ��</td>
    <td>����</td>
    <td>��ȡ��</td>
  </tr>
</table>
</span>
<table width="1280" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="550" align="center" bgcolor="#D4D4D4">
	<br>
	<iframe name="iframe1" id="iframe1" src="" height="100%" width="100%" scrolling="No" frameborder="0"></iframe></td>
  </tr>
</table>

<table width="1280" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" bgcolor="#6AA5CF">&nbsp;</td>
  </tr>
</table>


</body>
</html>

<script>
	
	var filepath = "hwsk1.jsp,sdzx2.jsp,lmzx3.jsp,dcbg4.jsp";
	var paths = filepath.split(",");
	
	var timeout = 10000;
	var j = 0;
	var l = paths.length;
	switchPage();
	function switchPage(){
		document.iframe1.location = paths[j];
		//document.iframe1.location = "dcbg4.jsp";
		window.setTimeout(switchPage,timeout);
		j++;
		if(l  == j){
			j = 0;
		}
	}
	
	show();
	function show(){
	
		var date = new Date();
		var yy = date.getYear();
		var MM = date.getMonth() + 1;
		var dd = date.getDate(); 
		var HH = date.getHours();
		var mm = date.getMinutes();
		var ss = date.getSeconds();
		
		switch (date.getDay()) {
		case 0 :
			ww = "������";
			break;
		case 1 :
			ww = "����һ";
			break;
		case 2 :
			ww = "���ڶ�";
			break;
		case 3 :
			ww = "������";
			break;
		case 4 :
			ww = "������";
			break;
		case 5 :
			ww = "������";
			break;
		case 6 :
			ww = "������";
			break;
		}
		HH = HH < 10 ? "0"+HH : HH;
		mm = mm < 10 ? "0"+mm : mm;
		ss = ss < 10 ? "0"+ss : ss;
		document.getElementById("showtime").innerHTML = "<b>"+yy+"��"+MM+"��"+dd+"��<br>"+ww+"<br>"+HH+":"+mm+":"+ss+"</b><br><br>" ;
		window.setTimeout(show,1000);
	
	}

	//////////////////////////////////////////
	sendRequest();
	
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
	function sendRequest() {
		createXMLHttpRequest();
		XMLHttpReq.open("post", "../../screen/screen.do?method=toZixunSumDto", true);
		XMLHttpReq.onreadystatechange = processResponse;//ָ����Ӧ����
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(null);  // ��������
		window.setTimeout(sendRequest,3000);
	}
	// ��������Ϣ����
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
        	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById('num').innerHTML = res;
                
            } else { //ҳ�治����
                window.alert("���������ҳ�����쳣��");
            }
        }

    }
</script>

