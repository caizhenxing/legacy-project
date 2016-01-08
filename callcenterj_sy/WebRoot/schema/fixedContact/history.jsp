<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
</head>
<link href="../images/css/styleA.css" rel="stylesheet" type="text/css" />
<style type="text/css">

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;

}
tr{font-size:13px;} 

</style>
<body>

<table border="0" width="100%" id="mytable">

 <logic:iterate id="pagelist" name="list" indexId="i">

  <tr align="center" bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" 
onmouseup="window.open('../question/question.do?method=toQuestionLoad&type=detail&id=<bean:write name='pagelist' property='id'/>','','width=800,height=450,status=no,resizable=yes,scrollbars=yes,top=100,left=200')"/>
    <td width="90" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="addtime" filter="true"/></td>
    <td width="80" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="dict_question_type1" filter="true"/></td>
    <td style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="question_content" filter="true"/>&nbsp;</td>
    <td width="60" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="dict_is_answer_succeed" filter="true"/></td>
  </tr>
  
 </logic:iterate>

</table>

</body>
</html>


