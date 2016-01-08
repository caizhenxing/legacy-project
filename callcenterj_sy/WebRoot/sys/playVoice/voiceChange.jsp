<%@ page language="java"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>语音播放</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../../style/yuyin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a:link {
	color: #3B6EB3;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #3B6EB3;
}
a:hover {
	text-decoration: none;
	color: #FFFFFF;
}
a:active {
	text-decoration: none;
}
-->
</style>
<script type="text/javascript">
			function check(str)
			{		
				var v = str;
				
				if(v=='xuanzhe')
				{
					document.getElementById("reportType").style.display="block";
					document.getElementById("affixType").style.display="none";
					
				}
				else if(v=='zhantie')
				{
					document.getElementById("affixType").style.display="block";
					document.getElementById("reportType").style.display="none";
				}
				
			}
			function getValue()
			{
				var radios = document.getElementsByName("play");
				for(var i=0; i<radios.length; i++)
				{
					if(radios[i].checked)
					{
						if("xuanzhe"==radios[i].value)
						{
							return "0,"+parent.ivrOperationframeTree.document.getElementById('selectTreeId').value;//document.getElementById('tXuanzhe').value;
						}
						else if("zhantie"==radios[i].value)
						{
							return "1,"+document.getElementById('tZhantei').value;
						}
					}
				}
			}
			function invokeParent()
			{
				var temp = getValue();

				parent.window.opener.jsAuto2Applet(temp);
			}
		</script>
		<script language="javascript">
	function nockClick(id)
	{
		//alert('当前节点id'+id);
	}
</script>
</head>

<div class="div"> 
  <div class="Brow1" ></div>
  <div class="Brow2"></div>
  <div class="Brow3">
    <table cellpadding="０">
      <tr>
        <td height="1"></td>
      </tr>
    </table>
    <table width="290" height="21" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="136" class="Button1"><a href="../../sys/playVoice.do?method=toSelectIvrFile"><img src="../../images/ivryuyin/Button1.gif" width="152" height="22" border="0" /></a></td>
        <td width="10">&nbsp;</td>
        <td width="144" class="Button2"><a href="#"><img src="../../images/ivryuyin/Button2.gif" width="152" height="22" border="0" /></a></td>
      </tr>
    </table>
  </div>
  <div class="Content">
    <table border="0" cellpadding="0" cellspacing="0" class="Contenttable">
      <tr>
        <td align="center"><table border="0" cellpadding="0" cellspacing="3" class="ContentButton">
          <tr>
            <td width="138" class="Decorationchart">&nbsp;</td>
      <td width="14" rowspan="4">&nbsp;</td>
      <td width="286" rowspan="4" valign="top" class="writingdiv"></select>
        <form action="./../playVoice.do?method=toCharacter2VoiceFile" method="post">
        <input name="affixTxt" id="tZhantei" type="text" class="Text2" size="24"/></td>
    	</form>
    </tr>
          <tr>
            <td class="Decoration">&nbsp;</td>
  </tr>
          <tr>
            <td valign="top" class="Decoration1"><div class="Buttonstyle"><table width="22" height="50" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><a href="#"><img src="../../images/ivryuyin/button3.gif" width="108" height="24" border="0" onclick="document.forms[0].submit()" /></a></td>
    </tr>
  <tr>
    <td><a href="#"><img src="../../images/ivryuyin/button4.gif" width="108" height="24" border="0" onclick="javascript:window.close()" /></a></td>
    </tr>
  </table>
</div>
  </td>
  </tr>
          <tr>
            <td><table cellpadding="０">
              <tr>
                <td height="1"></td>
              </tr>
            </table></td>
    </tr>
          
          
  </table>
  </td>
    </tr>
  </table>
</div></div>
</body>

</html>