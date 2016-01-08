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
    <td width="100%" onClick="window.open('../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id=<bean:write name='pagelist' property='case_id'/>','','width=800,height=450,status=no,resizable=yes,scrollbars=yes,top=100,left=200')">
    <bean:write name="pagelist" property="case_reply" filter="true"/>
    </td>
  </tr>

</logic:iterate>
  
</table>