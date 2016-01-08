<%@ page language="java"  pageEncoding="GBK"%>
<%
	response.setHeader("Expires", "0");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<script language="javascript">
		var filePath = 0;
		<%
			String filePath = (String)request.getAttribute("filePath");
			if(filePath != null)
			{
				out.print(" filePath = '"+filePath+"'");
			}
		%>
		//播放语音
		if(filePath!=0)
		{
			//语音转换成功了 到座席面板 播放语音
			
			playComposeVoice(filePath);
			window.close();
		}
		//多语音播放的函数
		function playComposeVoice(filePath)
		{
			opener.document.getElementById('txtS_ivrtype').value=filePath;
			opener.document.getElementById('txtOperationType').value='播放语音.vds';
			opener.document.getElementById('btnMultiIVR').click();
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