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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></script>
<%--    <script language="JavaScript" src="../../js/calendar.js"></script>--%>
<%--    <script language="JavaScript" src="../../js/calendar.js"></script>--%>
    <script language="javascript">
        function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.beginTime,"<bean:message bundle='pccye' key='sys.common.beginTime'/>")) return false;
            if (!checkNotNull(addstaffer.endTime,"<bean:message bundle='pccye' key='sys.common.endTime'/>")) return false;
            //if(compdate(addstaffer.startT.value,addstaffer.endT.value)) return false;
              return true;
            }
    	//²éÑ¯
    	function query(){
    	var f =document.forms[0];
	    if(checkForm(f)){
    		document.forms[0].action = "../operatorStatistic.do?method=toOperatorStatisticList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    		}
    	}
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
    
    
  </head>
  
  <body bgcolor="#eeeeee">
	
    <html:form action="/pcc/operatorStatistic" method="post">
      	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    <bean:message bundle="pccye" key="et.pcc.operatorStatistic"/>
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.operatorStatistic.operator"/></td>
		    <td class="tdbgcolorqueryleft" colspan="3">
		    <html:select property="operator">
            <html:option value="">
              <bean:message bundle="pccye" key="sys.pleaseselect" /></html:option>
              <html:optionsCollection name="personList" label="label" value="value" styleClass="input"/>
            </html:select>
<%--		    <html:text property="operator" styleClass="input"/>--%>
		    </td>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.callinNumEnd"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="callinNumEnd" styleClass="input"/></td>--%>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="beginTime" styleClass="input" readonly="true"/>
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(beginTime)"/></td>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="endTime" styleClass="input" readonly="true"/>
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endTime)"/></td>
		  </tr>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isPass"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">	    	--%>
<%--		    	<html:select property="isPass">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="sys.common.yes"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pcc" key="sys.common.no"/></html:option>--%>
<%--	        	</html:select>--%>
<%--		    </td>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isAvailable"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">--%>
<%--		        <html:select property="isAvailable">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="sys.common.yes"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pccye" key="sys.common.no"/></html:option>--%>
<%--	        	</html:select></td>--%>
<%--		  </tr>--%>
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle="pccye" key='sys.search'/>" onclick="query()"/>
<%--		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pccye' key='sys.add'/>" onclick="popUp('windows','../pcc/callinFirewall.do?method=toCallinFireWallLoad&type=insert',680,400)"/>--%>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
