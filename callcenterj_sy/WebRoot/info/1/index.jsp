<%@ page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>��Ϣ����</title>
<link href="style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width:100%; background-color:#FFFFFF">
	<table width="1280" border="0" cellpadding="0" cellspacing="0" bgcolor="#D4D4D4" class="table" align="center">
        <tr>
          <td class="Farmer">
            <div class="Time" id="showtime"></div>
            <div class="Icon"></div>
            <div class="Quick_news">��ũ��Ѷ</div>
          <div class="Text4">
          	<iframe src="../../screen/screen.do?method=toQuickMessageAdd" height="26" width="100%" scrolling="no" frameborder="0" allowTransparency="true"></iframe>
          </div>
          </td>
        </tr>
        <tr>
          <td align="center" valign="top" height="612">
		  <br>
		  <iframe name="iframe1" id="iframe1" src="" height="100%" width="100%" scrolling="No" frameborder="0"></iframe></td>
        </tr>
        <tr>
          <td height="37" class="Subtitle">&nbsp;</td>
        </tr>
  </table>	  
</div>
</body>
</html>
<script>

	var filepath = "mrjg1.jsp,jnscfx2.jsp,mrgq3.jsp,jdal4.jsp,yznztj5.jsp,zjtj6.jsp,dzfw7.jsp";
	var paths = filepath.split(",");
	
	var timeout = 10000;
	var j = 0;
	var l = paths.length;
	switchPage();
	function switchPage(){
		document.iframe1.location = paths[j];
		//document.iframe1.location = "jdal4.jsp";
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
		document.getElementById("showtime").innerHTML = "<b>"+yy+"��"+MM+"��"+dd+"��<br>"+ww+"<br>"+HH+":"+mm+":"+ss+"</b>" ;
		setTimeout(show,1000);
	
	}
	
</script>