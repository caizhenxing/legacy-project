<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title></title>
</head>
<%
	String phonenum = (String)request.getAttribute("phonenum");
	String fuzznum = (String)request.getAttribute("fuzznum");
%>
<script language="javascript">
    	//�ر�frame����
    	function frameClose(){
    		document.forms[0].action = "phone.do?method=finishOper&id=<%=id%>";
    		document.forms[0].target = "contents";
    		document.forms[0].submit();
    		window.parent.close();
    	}
</script>
<frameset id="dict" name="dict" rows="30%,70%" border="0" frameborder="0"  framespacing="0">
  <frame name="topp" src="phone.do?method=toInfoQuery&fuzznum=<%=fuzznum%>&phonenum=<%=phonenum%>" noresize>
  <frame name="bottomm" src="../../html/content.html" noresize>
  <noframes>
  <body onunload="frameClose()">

  <p>����ҳʹ���˿�ܣ��������������֧�ֿ�ܡ�</p>

  </body>
  </noframes>
</frameset>

</html>
