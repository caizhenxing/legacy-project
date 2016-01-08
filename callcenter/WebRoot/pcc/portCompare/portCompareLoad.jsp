<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

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
    
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript" src="../../js/clockCN.js"></script>
    <script language="javascript" src="../../js/clock.js"></script>
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.physicsPort,"<bean:message bundle='pccye' key='et.pcc.portCompare.physicsPort'/>")) return false;
            if (!checkNotNull(addstaffer.ip,"<bean:message bundle='pccye' key='et.pcc.portCompare.ip'/>")) return false;
            //if (!checkNotNull(addstaffer.isAvailable,"<bean:message bundle='pccye' key='et.pcc.callinFirewall.isAvailable'/>")) return false;
<%--            if (!checkIntegerRange(addstaffer.age,"年龄",10,60)) return false;--%>
<%--            if (!checkNotNull(addstaffer.birth,"出生日期")) return false;--%>
<%--            if (!checkNotNull(addstaffer.code,"身份证号")) return false;--%>
<%--            if (!checkLength2(addstaffer.code, "身份证号", 18, 15)) return false;--%>
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=delete";
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
  
  <body onunload="toback()" bgcolor="#eeeeee">
    <logic:notEmpty name="operSign">
	<script>
	alert("<bean:message bundle='pccye' name='operSign'/>"); window.close();
	</script>
	</logic:notEmpty>
  
    <html:form action="/pcc/portCompare" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload">
		    <logic:equal name="opertype" value="detail">
		     <bean:message bundle="pccye" key="sys.common.detail"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="insert">
		     <bean:message bundle="pccye" key="sys.add"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <bean:message bundle="pccye" key="sys.update"/>
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <bean:message bundle="pccye" key="sys.del"/>
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright" width="30%"><bean:message bundle="pccye" key="et.pcc.portCompare.physicsPort"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="physicsPort" styleClass="input"/>
		    <html:hidden property="id"/></td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.portCompare.logicPort"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="logicPort" styleClass="input"/></td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.portCompare.ip"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="ip" styleClass="input"/></td>
		  </tr>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="beginTime" styleClass="input" readonly="true"/>--%>
<%--		    <input type="button" value="<bean:message bundle="pcc" key='sys.common.time'/>" onclick="OpenTime(document.all.beginTime);"/></td>--%>
<%--		  </tr>--%>
<%--		  <tr>  --%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="endTime" styleClass="input" readonly="true"/>--%>
<%--		    <input type="button" value="<bean:message bundle="pccye" key='sys.common.time'/>" onclick="OpenTime(document.all.endTime);"/></td>--%>
<%--		  </tr>--%>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isPass"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">	    	--%>
<%--		    	<html:select property="isPass">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.pass"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.unpass"/></html:option>--%>
<%--	        	</html:select>--%>
<%--		    </td>--%>
<%--		  </tr>--%>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isAvailable"/></td>  --%>
<%--		    <td class="tdbgcolorqueryleft">--%>
<%--		        <html:select property="isAvailable">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.able"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.disable"/></html:option>--%>
<%--	        	</html:select></td>--%>
<%--		  </tr>--%>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.del'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='pccye' key='sys.close'/>" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
