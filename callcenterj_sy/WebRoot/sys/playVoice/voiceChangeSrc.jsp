<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>
<%@ include file="../../style.jsp"%>
<%
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>播放语音</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
		
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
<style type="text/css">
<!--
/*页面边距*/
body {
	margin-left: 1px;
	margin-top: 1px;
	margin-right: 1px;
	margin-bottom: 1px;
	overflow:hidden;
}
.text-selected{
	background: #ccffff;
}
-->
</style>
<script language="javascript">
	function nockClick(id)
	{
		//alert('当前节点id'+id);
	}
</script>
  </head>
  
  <body>
    <form action="./../playVoice.do?method=toCharacter2VoiceFile" method="post">
  <table width="400" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="400" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="3"><img src="../../style/<%=styleLocation%>/images/bofang/bf1.jpg" width="400" height="52" /></td>
      </tr>
      <tr>
        <td><img src="../../style/<%=styleLocation%>/images/bofang/bf2.jpg" width="56" height="346" /></td>
        <td width="282" align="center" background="../../style/<%=styleLocation%>/images/bofang/bf4.jpg">
         <!-- 文本粘贴框 -->
         <textarea name="affixTxt" class="lundong" id="tZhantei"></textarea>
        </td>
        <td><img src="../../style/<%=styleLocation%>/images/bofang/bf3.jpg" width="62" height="346" /></td>
      </tr>
      <tr>
        <td colspan="3"><table width="400" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="../../style/<%=styleLocation%>/images/bofang/bf6.jpg" width="177" height="32" /></td>
            <td width="161" align="center" background="../../style/<%=styleLocation%>/images/bofang/bf8.jpg"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="../../style/<%=styleLocation%>/images/bofang/bf9.jpg" width="73" height="26" onclick="document.forms[0].submit()" /></td>
                <td>&nbsp;</td>
                <td><img src="../../style/<%=styleLocation%>/images/bofang/bf10.jpg" width="73" height="26" onclick="javascript:window.close()" /></td>
              </tr>
            </table></td>
            <td><img src="../../style/<%=styleLocation%>/images/bofang/bf7.jpg" width="62" height="32" /></td>
          </tr>
        </table></td>
        </tr>
      <tr>
        <td colspan="3"><img src="../../style/<%=styleLocation%>/images/bofang/bf5.jpg" width="400" height="20" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
  </body>
   
</html:html>
