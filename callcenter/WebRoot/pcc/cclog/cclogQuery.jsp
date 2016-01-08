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
<%--    <script language="JavaScript" src="../../js/calendar.js"></script>--%>
    <script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../cclog.do?method=toCclogList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
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
	
    <html:form action="/pcc/cclog" method="post">
      	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    <bean:message bundle="pccye" key="et.pcc.cclog"/>
		    </td>
		  </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.cclog.phoneNum"/></td>
		    <td class="tdbgcolorqueryleft" ><html:text property="phoneNum" styleClass="input"/></td>
		    <td class="tdbgcolorqueryright">警号</td>
		    <td class="tdbgcolorqueryleft"><html:text property="fuzzNo" styleClass="input"/></td>
		    <td class="tdbgcolorqueryright">部门</td>
		    <td class="tdbgcolorqueryleft">
                <html:select property="department">
							<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:options collection="dl" property="value" labelProperty="label" />
				</html:select>
            </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="beginTime" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(beginTime)"/></td>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="endTime" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endTime)"/></td>
		  	<td class="tdbgcolorqueryright">是否有效</td>
            <td class="tdbgcolorqueryleft">
            	<html:select property="isvalidin">		
	        		<html:option value=""><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
	        		<html:optionsCollection name="errorphone" label="label" value="value"/>
	        	</html:select>
            </td>
		  </tr> 
		  <tr>
		    <td class="tdbgcolorqueryright">
		    查询类别</td>
		    <td class="tdbgcolorqueryleft">
		    	<html:select property="taginfo">		
	        		<html:option value=""><bean:message  key="sys.pselect"/></html:option>
	        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
	        	</html:select>  
		    </td>
		    <td class="tdbgcolorqueryright"></td>
		    <td class="tdbgcolorqueryleft"></td>
		  	<td class="tdbgcolorqueryright"></td>
            <td class="tdbgcolorqueryleft"> </td>
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
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pcc" key="et.pcc.callinFirewall.isAvailable"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">--%>
<%--		        <html:select property="isAvailable">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pcc" key="sys.common.yes"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pcc" key="sys.common.no"/></html:option>--%>
<%--	        	</html:select></td>--%>
<%--		  </tr>--%>
		  <tr>
		    <td colspan="6" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle="pccye" key='sys.search'/>" onclick="query()"/>
<%--		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pccye' key='sys.add'/>" onclick="popUp('windows','../pcc/callinFirewall.do?method=toCallinFireWallLoad&type=insert',680,400)"/>--%>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
