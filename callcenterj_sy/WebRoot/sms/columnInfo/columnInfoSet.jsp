<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("�Ѿ����ƹ���ҵ�񣬲����ظ����ƣ�");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("�Ѿ��˶�����ҵ�񣬲����ظ��˶���");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
	</script>
	</logic:equal>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>����</title>
<script type='text/javascript' src='../js/msg.js'></script>
<script type="text/javascript">
	// ��ѯ���Ƽ�¼
	function tijiao(){		
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoUpdate";
   		document.forms[0].target = "contents";
		document.forms[0].submit();
		window.close();
	}
	function toback(){			
			if(opener.parent){
				//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
				var link = opener.parent.bottomm.document.location.href;
				alert(opener.parent)
				alert(link)
				if(link.indexOf("pagestate") == -1){
					link += "&pagestate=1";
				}
				opener.parent.bottomm.document.location = link;
			}
		
		}
</script> 
</head>

<body onunload="toback()" class="loadBody">
<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>
<html:form action="/sms/columnInfo" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
     ��ǰλ��&ndash;&gt;�趨��Ŀ��Ϣ
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
     <td class="labelStyle">
     	��Ŀ��Ϣ
     </td>
     <td class="valueStyle">
       <html:text property="columnInfo" styleClass="writeTextStyle"/>
     </td> 
  <tr class="buttonAreaStyle">
    <td colspan="4" align="right">
      <input type="button" name="Submit4" value="�� ��" onClick="tijiao()"  >
    </td>
  </tr>
</table>
<html:hidden property="nickname"/>
</html:form>
</body>
</html>
