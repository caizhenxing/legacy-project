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
    
    <title><bean:message bundle="sys" key="sys.station.title"/></title>
    
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
        	if (!checkNotNull(addstaffer.deppersonname,"部门职务名称")) return false;
        	if (!checkNotNull(addstaffer.deplevel,"部门人员")) return false;
           return true;
        }
    	//添加
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "station.do?method=operStation&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "station.do?method=operStation&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "station.do?method=operStation&type=delete";
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
  
    <html:form action="/sys/station/station.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message bundle="sys" key="sys.station.stationload.operator"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.station.stationload.seldep"/></td>
		    <td class="tdbgcolorloadleft">
			    <html:select property="departmentid">		
	        	<html:option value="" ><bean:message bundle="sys" key="sys.station.stationload.pleaseselect"/></html:option>
	        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.station.stationload.depname"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="deppersonname" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.station.stationload.depperson"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="deplevel" styleClass="input" rows="5" cols="50"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.station.stationload.depdescribe"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="depdescribe" styleClass="input"/>
		    </td>
		  </tr>
		  
		  
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="delete">
		     <input name="btnDel" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.delete'/>" onclick="del()"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnUpdate" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.insert'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.cancel'/>" onclick="javascript:window.close();"/></td>
		  
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
