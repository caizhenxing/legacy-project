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
        	if (!checkNotNull(addstaffer.bookName,"Í¼ÊéÃû³Æ")) return false;
        	if (!checkNotNull(addstaffer.bookNum,"Í¼Êé±àºÅ")) return false;
           return true;
        }
    	//Ìí¼Ó
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "../book.do?method=operBook&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	
    	//ÐÞ¸Ä
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "../book.do?method=operBook&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//É¾³ý
    	function del(){
    		document.forms[0].action = "../book.do?method=operBook&type=delete";
    		document.forms[0].submit();
    	}
    	
    	//¹ÒÊ§
    	function lose(){
    		document.forms[0].action = "../book.do?method=operBook&type=lose";
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
		//·µ»ØÒ³Ãæ
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
		    <bean:message key="et.oa.commoninfo.book.bookload.operator"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.booktype"/></td>
		    <td class="tdbgcolorloadleft">
			    <html:select property="bookType">		
	        	<html:option value="" ><bean:message key="et.oa.commoninfo.book.bookload.pleaseselect"/></html:option>
	        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.bookname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookName" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.bookauthor"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookAuthor" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.bookwhere"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookConcern" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.booknumber"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookNum" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.bookprice"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="bookPrice" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.buytime"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="buyTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.addtime"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="noteTime" styleClass="input" readonly="true" onfocus="calendar()"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.book.bookload.bookintro"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="introduce" rows="10" cols="30"/>
		    </td>
		  </tr>
		  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="delete">
		     <input name="btnDel" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="del()"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnUpdate" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="lose">
		     <input name="btnLose" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.book.bookload.lose'/>" onclick="lose()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
