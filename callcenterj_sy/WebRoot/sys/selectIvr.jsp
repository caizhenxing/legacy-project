<%@ page language="java"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>IVR�ļ�ѡ��</title>
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
<link href="../style/ivryuyin.css" rel="stylesheet" type="text/css" />
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
		//��ͨ��ivr
		function goParent(value)
		{
			//txtChangeToIvr btnChangeToIvr
			opener.document.getElementById('txtChangeToIvr').value=value;
			opener.document.getElementById('btnChangeToIvr').click();
			window.close();
		}
		//����������ҵ��
		function goMultiIVR()
		{
			//txtS_ivrtype txtOperationType
			var operationV = getRadioValue('operationType');
			var s_ivrtypeV = document.getElementById('s_ivrtype').value;
			if(s_ivrtypeV == 0)
			{
				alert("��ѡ��ҵ������!");
				return;
			}
			
			opener.document.getElementById('txtS_ivrtype').value=s_ivrtypeV;
			//��ϯ�����ѡ��.vds
			//opener.document.getElementById('txtOperationType').value='��ϯ�����ѡ��.vds';
			opener.document.getElementById('txtOperationType').value=operationV;
			opener.document.getElementById('btnMultiIVR').click();
			window.close();
		}
		function getRadioValue(radioName)
		{
			 var obj=document.getElementsByName(radioName);
			 for(var i=0;i<obj.length;i++)
			 {
				  if(obj[i].checked)
				  {
				   	return obj[i].value;
				  }
			 }
		}
	</script>

</head>

<div class="div"> 
  <div class="Brow1"></div>
  <div class="Brow2"></div>
  <div class="Brow3">
<table cellpadding="��">
  <tr>
    <td height="1"></td>
  </tr>
</table>
<table width="301" height="22" border="0" cellpadding="0" cellspacing="0">
  <tr>
        <td width="136" class="Button1"><a href="#"><img src="../images/ivryuyin/Button1.gif" width="152" height="22" border="0" /></a></td>
        <td width="10">&nbsp;</td>
        <td width="144" class="Button2"><a href="../sys/playVoice/voiceChange.jsp"><img src="../images/ivryuyin/Button2.gif" width="152" height="22" border="0" /></a></td>
      </tr>
</table>
  </div>
  <html:form action="sys/playVoice.do?method=toSelectIvrFile" method="post">
  <div class="Content"><table border="0" cellpadding="0" cellspacing="0" class="Contenttable">
  <tr>
    <td align="center"><table border="0" cellpadding="0" cellspacing="3" class="ContentButton">
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"><a href="javascript:goParent('start.vds')">����</a></div></td>
    <td>&nbsp;</td>
    <td width="261" class="Bluewriting2"><div class="writingdiv">����������ҵ�񣨵㲥�����ơ��˶�ҵ�񣩣�</div></td>
  </tr>
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"><a href="javascript:goParent('�۸�����.vds')">�۸�����</a></div></td>
    <td width="14">&nbsp;</td>
    <td width="261" align="left"><table cellpadding="0" cellspacing="0" class="Bluewriting3">
      <tr>
        <td>�Ƿ����һ������һ��</td>
        <td><input type="radio" value="��ϯתIVR����������ddt.vds" name="operationType" /></td>
        <td>��</td>
        <td><input type="radio" checked="checked" value="��ϯתIVR����������.vds" name="operationType" /></td>
        <td>��</td>
        <td>&nbsp;</td>
        <td><input type="button" value="ȷ��" onclick="goMultiIVR()" /></td>
<%--        <td><input type="submit" name="Submit" value="ȷ��" /></td>--%>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"><a href="javascript:goParent('ҽ�ƽ���.vds')">ҽ�ƽ���</a></div></td>
   <td rowspan="17" align="center">&nbsp;</td>
    <td width="261" rowspan="17" align="center" valign="top">
   		<html:select size="24" styleClass="Text" property="s_ivrtype">
			<html:optionsCollection
				name="lvList"
				label="value"
				value="label"
			/>
		</html:select>	
   </td>
  </tr>
  <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"><a href="javascript:goParent('��ֳҵ����.vds')">��ֳҵ����</a></div></td>
    </tr>
    <% 
		
    	out.println("<tr>");
		 out.println("<td width=\"173\" class=\"Bluewriting\"><div class=\"writingdiv\">");
		out.println("<a href=\"javascript:goParent('��ֲҵ����.vds')\">��ֲҵ����</a>");
		 out.println("</div></td>");
		out.println("</tr>");
		
						out.println("<tr>");
		 out.println("<td width=\"173\" class=\"Bluewriting\"><div class=\"writingdiv\">");
		out.println("<a href=\"javascript:goParent('������Ϣ.vds')\">������Ϣ</a>");
		 out.println("</div></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td width=\"173\" class=\"Bluewriting\"><div class=\"writingdiv\">");
		out.println("<a href=\"javascript:goParent('ר��.vds')\">ר��</a>");
		 out.println("</div></td>");
		out.println("</tr>");
		
				out.println("<tr>");
		 out.println("<td width=\"173\" class=\"Bluewriting\"><div class=\"writingdiv\">");
		out.println("<a href=\"javascript:goParent('ConfRoom.vds')\">�������</a>");
		 out.println("</div></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td width=\"173\" class=\"Bluewriting\"><div class=\"writingdiv\">");
		out.println("<a href=\"javascript:goParent('record.vds')\">������������</a>");
		 out.println("</div></td>");
		out.println("</tr>");
		
		//################################\

%>
<%--	<!-- #########begin -->  --%>
<%--  <tr>--%>
<%--   <td width="173" class="Bluewriting"><div class="writingdiv"><a href="#">������������</a></div></td>--%>
<%--    </tr>--%>
<%--    <!-- #########end -->--%>
  <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
 <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
 <tr>
   <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
  <tr>
    <td width="173" class="Bluewriting"><div class="writingdiv"></div></td>
    </tr>
</table>
</td>
  </tr>
</table>
</div>

</div>
</html:form>
<!--  -->
<%--<iframe name="yuyinFrame" src="./sys/playVoice/voiceChange.jsp" width="572" height="500" />--%>
</body>

</html>