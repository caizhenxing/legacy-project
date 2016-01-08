<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>
    <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
        function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.bookUser,"借书人")) return false;
            return true;
        }
    	//借书
    	function borrow(){
    	    var f =document.forms[0];
    		document.forms[0].action = "../book.do?method=operBook&type=borrow";
    		document.forms[0].submit();
    	}
    	
    	//续借
    	function reborrow(){
    	    var f =document.forms[0];
    		document.forms[0].action = "../book.do?method=operBook&type=reborrow";
    		document.forms[0].submit();
    	}
    	
    	//还书
    	function returnback(){
    	    var f =document.forms[0];
    		document.forms[0].action = "../book.do?method=operBook&type=return";
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
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
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
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/commoninfo/book.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message key="et.oa.commoninfo.book.bookborrowLoad.operator"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookborrowLoad.bookname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookName" styleClass="input" readonly="true"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookborrowLoad.borrowman"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:select property="bookUser">
		    	<html:options collection="employeename" property="value" labelProperty="label" />
		    </html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright">
		    <logic:equal name="opertype" value="borrow">
		    <bean:message key="et.oa.commoninfo.book.bookborrowLoad.borrowtime"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="return">
		    <bean:message key="et.oa.commoninfo.book.bookborrowLoad.getbacktime"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="reborrow">
		    <bean:message key="et.oa.commoninfo.book.bookborrowLoad.borrowtime"/>
		    </logic:equal>
		     
		    </td>
		    <td class="tdbgcolorloadleft">
		    <logic:equal name="opertype" value="borrow">
		    <html:text property="borrowTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="return">
		    <html:text property="returnTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </logic:equal> 
		    <logic:equal name="opertype" value="reborrow">
		    <html:text property="borrowTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </logic:equal>
		    </td>
		  </tr>
		  	  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="borrow">
		     <input name="btnBorrow" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.book.bookborrowLoad.borrowbook'/>" onclick="borrow()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="reborrow">
		     <input name="btnBorrow" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.book.bookload.continueborrow'/>" onclick="reborrow()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="return">
		     <input name="btnReturn" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.book.bookborrowLoad.getbackbook'/>" onclick="returnback()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
