<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
<%--    <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>--%>
<%--    </SCRIPT>--%>
    <script language="javascript">
    	function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.fuzzNo,"警号")) return false;
        	if (!checkNotNull(addstaffer.name,"姓名")) return false;
        	if (addstaffer.password.value !=addstaffer.repassword.value)
            {
            	alert("两次输入的密码不一致！");
            	return false;
            }
           return true;
        }
    	//添加
    	function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "fuzz.do?method=operFuzz&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//修改
    	function update(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "fuzz.do?method=operFuzz&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "fuzz.do?method=operFuzz&type=delete";
    		document.forms[0].submit();
    	}
    	
    	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendardate.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		//返回页面
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()" bgcolor="#eeeeee">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<bean:message bundle='pcc' name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/pcc/policefuzz/fuzz.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.fuzznum"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="fuzzNo" styleClass="input"/>	
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.fuzzname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="name" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.sex"/></td>
		    <td class="tdbgcolorloadleft">
		    	<html:select property="sex">
				<html:option value="1"><bean:message bundle="pcc" key="sys.man"/></html:option>
				<html:option value="0"><bean:message bundle="pcc" key="sys.woman"/></html:option>
		    	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.birthday"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="birthday" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(birthday)"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.password"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="password" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.checkpassword"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:password property="repassword" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.mobilephone"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="mobileTel" styleClass="input"/>
		    </td>
		  </tr>
		  
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.workontime"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="workontime" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(workontime)"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.idcard"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="idcard" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.personstate"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="personstate" styleClass="input"/>
		    </td>
		  </tr>
		  
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagunit"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:select property="tagUnit">		
        		<html:option value="0" ><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
        		<html:optionsCollection name="dtreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagarea"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="tagArea" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.tagfreeze"/></td>
		    <td  class="tdbgcolorloadleft">
		    <html:select property="tagPoliceKind">
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.del'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
