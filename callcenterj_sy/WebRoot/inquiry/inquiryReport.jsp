<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�"); window.close();
	</script>
</logic:notEmpty>
<%@ include file="../style.jsp"%>

<html>
	<head>
		<link href="style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="js/calendar3.js"></script>
		
<!-- jquery��֤ -->
	<script src="js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="css/validator.css"></link>	
	<script src="js/jquery/formValidator.js" type="text/javascript"charset="UTF-8"></script>
	<script src="js/jquery/formValidatorRegex.js"type="text/javascript" charset="UTF-8"></script>

		<script type="text/javascript">
		<logic:empty name="inquiryForm" property="reportTopic">
		$(document).ready(function(){			
			$.formValidator.initConfig({formid:"inquiryID",onerror:function(msg){alert(msg)}});
			$("#topic").formValidator({onshow:"�������������",onfocus:"�������ⲻ��Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ⲻ��Ϊ��"});
			$("#organizers").formValidator({onshow:"��������֯��",onfocus:"��֯�߲���Ϊ��",oncorrect:"��֯�ߺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��֯�����߲����пշ���"},onerror:"��֯�߲���Ϊ��"});
			$("#organiztion").formValidator({onshow:"�����뷢�����",onfocus:"�����������Ϊ��",oncorrect:"��������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����������߲����пշ���"},onerror:"�����������Ϊ��"});
						
		})
		</logic:empty>
		
		function aaa(){
			document.forms[0].action = "inquiry.do?method=toReportDelete";
			document.forms[0].submit();
		}
		</script>
	</head>

<body class="loadBody">
<html:form action="/inquiry.do?method=toReportUpdate" method="post" styleId="inquiryID">
<html:hidden property="id" />
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					���鱨��
				</td>
			</tr>
		</table>
<table width="1015" border="0" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ��</td>
    <td class="valueStyle"><html:text property="reportTopic" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">��&nbsp; ��&nbsp; ��</td>
    <td class="valueStyle" colspan="3"><html:text property="reportTopic2" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">��������</td>
    <td class="valueStyle">
    <html:text property="topic" styleClass="writeTextStyle" size="30" styleId="topic"/>
    <div id="topicTip" style="width:10px;display:inline;"></div>
    </td>
    <td class="labelStyle">��&nbsp; ֯&nbsp; ��</td>
    <td class="valueStyle" colspan="3">
    <html:text property="organizers" styleClass="writeTextStyle" size="30" styleId="organizers"/>
    <div id="organizersTip" style="width:100px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">�������</td>
    <td class="valueStyle">
    <html:text property="organiztion" styleClass="writeTextStyle" size="30" styleId="organiztion"/>
    <div id="organiztionTip" style="width:10px;display:inline;"></div>
    </td>
    <td class="labelStyle">����  �� Ա</td>
    <td class="valueStyle" colspan="3"><html:text property="actors" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">׫ �� ��</td>
    <td class="valueStyle"><html:text property="reportCopywriter" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">��&nbsp; ��&nbsp; ��</td>
    <td class="valueStyle" colspan="3"><html:text property="reportKeyword" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">����ʱ��</td>
    <td class="valueStyle">
    <html:text property="beginTime" styleClass="writeTextStyle" size="18"/>
    <img alt="ѡ��ʱ��" src="html/img/cal.gif" onclick="openCal('inquiryForm','beginTime',false);">
    ��
    <html:text property="endTime" styleClass="writeTextStyle" size="18"/>
    <img alt="ѡ��ʱ��" src="html/img/cal.gif" onclick="openCal('inquiryForm','endTime',false);">
    </td>
    <td class="labelStyle">ժ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Ҫ</td>
    <td class="valueStyle" colspan="3"><html:text property="reportAbstract" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">��������</td>
    <td class="valueStyle"><html:text property="reportSwatch" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">������Ч��</td>
    <td class="valueStyle">
    	<html:text property="reportEfficiency" styleClass="writeTextStyle"/>
    </td>
     <td class="labelStyle">���״̬</td>
    <td class="valueStyle">
    <logic:equal name="opertype" value="insert">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=insert"/>
	</logic:equal>
	<logic:equal name="opertype" value="detail">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=detail"/>
	</logic:equal>
	<logic:equal name="opertype" value="update">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=update"/>
	</logic:equal>
	<logic:equal name="opertype" value="delete">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=delete"/>
	</logic:equal>
<%--    	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm"/>--%>
    </td>
  </tr>
    <tr>
    <td class="labelStyle">��������</td>
    <td colspan="5" class="valueStyle">
    	<FCK:editor instanceName="reportContent">
			<jsp:attribute name="value"><bean:write name="inquiryForm" property="reportContent" filter="false"/></jsp:attribute>
			<jsp:attribute name="height">350</jsp:attribute>
		</FCK:editor>
    </td>
    </tr>
    <tr>
    <td class="labelStyle">��������</td>
    <td colspan="5" class="valueStyle"><html:textarea property="reportReview" styleClass="writeTextStyle" rows="3" cols="153"/></td>
    </tr>
    <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp; ע</td>
    <td colspan="5" class="valueStyle"><html:textarea property="reportRemark" styleClass="writeTextStyle" rows="3" cols="153"/></td>
    </tr>
    <tr>
				<td colspan="6" align="right" class="buttonAreaStyle">
					<logic:notEmpty name="inquiryForm" property="reportTopic">
						<input type="button" name="submit3" value="ɾ��" class="buttonStyle" onclick="aaa()">
					</logic:notEmpty>
					<input type="submit" name="submit1" value="ȷ��" class="buttonStyle" >
					<input type="button" name="submit2" value="�ر�" class="buttonStyle" onClick="javascript:window.close();" >
				</td>
			</tr>
</table>
</html:form>
</body>
</html>
