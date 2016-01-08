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
    <script language="javascript">
    	function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.num,"电话号码")) return false;
        	if (!checkNotNull(addstaffer.unit,"单位")) return false;
        	if (!checkNotNull(addstaffer.deprtment,"部门")) return false;
           return true;
        }
    	//添加
    	function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "search.do?method=operSearch&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//修改
    	function update(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "search.do?method=operSearch&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "search.do?method=operSearch&type=delete";
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
  
    <html:form action="/pcc/phonesearch/search.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.phonesearch.load.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="pcc" key="et.pcc.phonesearch.load.num"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="num" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright">
		    <bean:message bundle="pcc" key="et.pcc.phonesearch.load.dep"/>
		    </td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="unit" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td  class="tdbgcolorloadright">
		    <bean:message bundle="pcc" key="et.pcc.phonesearch.load.unit"/>
		    </td>
		    <td  class="tdbgcolorloadleft">
		    <html:text property="deprtment" styleClass="input"/>
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
