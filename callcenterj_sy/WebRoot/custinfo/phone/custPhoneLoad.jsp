<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>客户操作</title>
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
<script type='text/javascript' src='../../js/msg.js'></script>
<script type="text/javascript">
	function checkForm(custinfo){
            if (!checkNotNull(custinfo.cust_name,"姓名")) return false;
            if (!checkNotNull(custinfo.cust_addr,"地址")) return false;
            if (!checkNotNull(custinfo.cust_voc,"客户行业")) return false;
            
              return true;
    }
	var url = "custinfo.do?method=toPhoneOper&type=";
	//添加
   	function add(){
   		var f =document.forms[0];
    	if(checkForm(f)){
	   		document.forms[0].action = url + "insert";
			document.forms[0].submit();
		}
   	}
   	//修改
   	function update(){
   		var f =document.forms[0];
    	if(checkForm(f)){
	   		document.forms[0].action = url + "update";
			document.forms[0].submit();
		}
   	}
   	//删除
   	function del(){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody">
<html:form action="custinfo/custinfo.do" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>
    <td width="139" class="valueStyle">
      <html:text property="cust_name" size="13" styleClass="input"/>
      <html:hidden property="cust_id"/></td>
    <td class="labelStyle">性&nbsp;&nbsp;&nbsp;&nbsp;别</td>
    <td class="valueStyle">
      <html:select property="dict_sex">
        <html:options collection="sexList" property="value" labelProperty="label"/>
      </html:select>      </td>
    </tr>
  <tr>
    <td class="labelStyle">地&nbsp;&nbsp;&nbsp;&nbsp;址</td>
    <td colspan="3" class="valueStyle"><html:text property="cust_addr" size="46" styleClass="input"/></td>
    </tr>
  <tr>
    <td class="labelStyle">邮&nbsp;&nbsp;&nbsp;&nbsp;编</td>
    <td class="valueStyle">
      <html:text property="cust_pcode" size="5" styleClass="input"/>
    </td>
    <td class="labelStyle">&nbsp;E_mail&nbsp;</td>
    <td class="valueStyle">   
      <html:text property="cust_email" size="13" styleClass="input"/>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">宅&nbsp;&nbsp;&nbsp;&nbsp;电</td>
    <td class="valueStyle"><html:text property="cust_tel_home" size="13" styleClass="input"/></td>
    <td class="labelStyle">办公电话</td>
    <td class="valueStyle">
      <html:text property="cust_tel_work" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">手&nbsp;&nbsp;&nbsp;&nbsp;机</td>
    <td class="valueStyle"><html:text property="cust_tel_mob" size="13" styleClass="input"/></td>
    <td class="labelStyle">传真号码</td>
    <td class="valueStyle">
      <html:text property="cust_fax" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">客户行业</td>
    <td class="valueStyle"><html:text property="cust_voc" size="13" styleClass="input"/></td>
    <td class="labelStyle">行业规模</td>
    <td class="valueStyle">
      <html:text property="cust_scale" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">客户类型</td>
    <td colspan="3" class="valueStyle">
      <html:select property="cust_type">
        <html:options collection="typeList" property="value" labelProperty="label"/>
      </html:select>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" 添 加 " onClick="add()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" 修 改 " onClick="update()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" 删 除 " onClick="del()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
