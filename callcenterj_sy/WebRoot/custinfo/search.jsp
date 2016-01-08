<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<table width="100%">
<style type="text/css">

tr{
	color: #0066CC;
	font-size:13px;
} 

</style>
<logic:iterate id="pagelist" name="list" indexId="i">

  <tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';">
  <logic:equal value="putong" name="pagelist" property="type">
  	<td width="100%" onClick="window.open('../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id=<bean:write name='pagelist' property='case_id'/>','','width=800,height=460,status=no,resizable=yes,scrollbars=yes,top=100,left=200')">
  </logic:equal>
  <logic:equal value="HZCase" name="pagelist" property="type">
  	<td width="100%" onClick="window.open('../caseinfo/hzinfo.do?method=tohzinfoLoad&type=detail&id=<bean:write name='pagelist' property='case_id'/>','','width=800,height=485,status=no,resizable=yes,scrollbars=yes,top=100,left=200')">
  </logic:equal>
  <logic:equal value="FocusCase" name="pagelist" property="type">
  	<td width="100%" onClick="window.open('../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoLoad&type=detail&id=<bean:write name='pagelist' property='case_id'/>','','width=800,height=475,status=no,resizable=yes,scrollbars=yes,top=100,left=200')">
  </logic:equal>
  <logic:equal value="effectCase" name="pagelist" property="type">
  	<td width="100%" onClick="window.open('../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoLoad&type=detail&id=<bean:write name='pagelist' property='case_id'/>','','width=800,height=457,status=no,resizable=yes,scrollbars=yes,top=100,left=200')">
  </logic:equal>

    <bean:write name="pagelist" property="compose"/>
    </td>
  </tr>

</logic:iterate>
  
</table>