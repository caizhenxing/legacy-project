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
<script language="javascript" src="../js/common.js"></script>
<link href="style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body {
		margin: 0px;
	}
</style>
<body>

<table border="0" width="100%" cellpadding="0" cellspacing="1">

 <logic:iterate id="pagelist" name="list" indexId="i">
<%
String style = i.intValue() % 2 == 0 ? "OddContent_fence" : "EvenContent_fence";
%>
  <tr class="<%= style %>">
  	<td width="90" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="cust_tel"/></td>
    <td width="120" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="addtime"/></td>
    <td width="100" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="dict_question_type1"/></td>
    <td style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="question_content"/>&nbsp;</td>
    <td width="90" style="border-top: solid  #FF6600 1px"><bean:write name="pagelist" property="rid"/></td>
    <td width="73" style="border-top: solid  #FF6600 1px">
    
		<img alt="详细" src="../style/chun/images/detail.gif"
						onclick="openUrl('<bean:write name="pagelist" property="dict_question_type1"/>','<bean:write name="pagelist" property="id"/>','detail')"
						width="16" height="16" border="0" />
		
		<img alt="修改" src="../style/chun/images/update.gif"
						onclick="openUrl('<bean:write name="pagelist" property="dict_question_type1"/>','<bean:write name="pagelist" property="id"/>','update')"
						width="16" height="16" border="0" />

	</td>
  </tr>
  
 </logic:iterate>

</table>

</body>
</html>
<script>
	function openUrl(type, id, action){
		if(id.indexOf("false") != -1){//如果id字符串中以false开头，则证明问题表里有记录，而关联表里没记录，则直接显示问题的
			id = id.substring(5, id.length);
			popUp(id,'../question/question.do?method=toQuestionLoad&type='+ action +'&id='+ id,800,310);
		}else if(type == "价格报送"){
			popUp(id,'../operpriceinfo.do?method=toOperPriceinfoLoad&type='+ action +'&id='+ id,640,260);
		}else if(type == "供求发布"){
			popUp(id,'../sad.do?method=toSadLoad&type='+ action +'&id='+ id,657,307);
		}else if(type == "热线调查"){
			popUp(id,'../detailForm.do?method=toList&qid='+ id,800,300);
		}else{
			popUp(id,'../question/question.do?method=toQuestionLoad&type='+ action +'&id='+ id,800,310);
		}
	}
</script>


