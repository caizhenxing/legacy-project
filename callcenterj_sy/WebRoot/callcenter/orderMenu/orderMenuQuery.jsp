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
	
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar3.js"></script>

<title>����</title>
<script type="text/javascript">
	// ��ѯ���Ƽ�¼
	function query(){
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuList";
   		document.forms[0].target="bottomm";
		document.forms[0].submit();
	}
	
</script>
</head>

<body class="loadBody">
<html:form action="/callcenter/orderMenu" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
    ��ǰλ��&ndash;&gt;������Ϣ����
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
    <td class="labelStyle">
		��ʼʱ��
     </td>
     <td class="valueStyle" width="180">	
     <html:text property="beginDate" styleClass="writeTextStyle"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('orderMenuBean','beginDate',false);">
	</td>
	 <td class="labelStyle">��������</td>
    <td class="valueStyle">
      <html:select property="orderType"  style="width:155px" styleId="orderType">		
	     <html:option value="" >��ѡ��</html:option>
	     <html:option value="orderProgramme" >�㲥</html:option>
	     <html:option value="customize" >����</html:option>
<%--	     <html:option value="tuiding" >�˶�</html:option>--%>
	  </html:select>
    </td>
     <td class="labelStyle">
     	������Ŀ
     </td>
     <td class="valueStyle" colspan="2">
      <html:select property="ivrInfo" styleClass="input" styleId="ivrInfo">
      	<html:option value="">��ѡ����Ŀ����</html:option>
        <html:options collection="ivrlist" property="value" labelProperty="label"/>
      </html:select>
	</tr>
   <tr>
	<td class="labelStyle">
		����ʱ��
     </td>
     <td class="valueStyle" width="180">	
    <html:text property="endDate" styleClass="writeTextStyle"/>
						<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('orderMenuBean','endDate',false);">
	</td>
	<td class="labelStyle">�û�����</td>
    <td class="valueStyle" colspan="2">
      <html:text property="telNum" styleClass="input" styleId="telNum"/>
      <html:hidden property="id"/></td>
      <td class="valueStyle" align="center">
      	<input type="button" name="btnSearch" value="�� ѯ" onClick="query()"  class="buttonStyle"/>
     	<input type="reset" name="btnSearch2" value="ˢ ��" onClick="parent.bottomm.document.location=parent.bottomm.document.location;" class="buttonStyle"/>
     </td>
   </tr>
</table>
</html:form>
</body>
</html>
