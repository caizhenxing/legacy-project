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
    
    <%
    	String id = (String)request.getAttribute("policeid");
    	String fuzznum = (String)request.getAttribute("fuzznum");
    %>
    <script language="javascript" src="../../js/common.js"></script>
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <script language="javascript">
    	function clear(){
    		document.forms[0].taginfo.value="";
    		document.forms[0].quinfo.value="";
    		document.forms[0].content.value="";
    		document.forms[0].remark.value="";
    	}
        //选择所有
    	function selectAll() {
			for (var i=0;i<document.forms[0].chk.length;i++) {
				var e=document.forms[0].chk[i];
				e.checked=!e.checked;
			}
		}
		//执行操作
		function operit(){
			document.forms[0].action = "../phone.do?method=operFuzzInfo";
			document.forms[0].submit();
		}
		
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.taginfo,"查询类型")) return false;
        	if (!checkNotNull(addstaffer.quinfo,"查询问题")) return false;
        	if (!checkNotNull(addstaffer.content,"查询内容")) return false;
           return true;
        }
		//添加
		function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].action = "phone.do?method=toAddPoliceInfo&id=<%=id%>&fuzznum=<%=fuzznum%>";
    		document.forms[0].submit();
    		}
		}
    </script>
  </head>
  
  <body onload="clear()" bgcolor="#eeeeee">
    <html:form action="/pcc/phoneinfo/phone.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td colspan="5"><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.title"/></td>
		  </tr>
		  <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.taginfo"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.quinfo"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.content"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.remark"/></td>
		    <td></td>
		  </tr>
		  <tr>
		    <td valign="top">
			    <html:select property="taginfo">		
	        		<html:option value="0"><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
	        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
	        	</html:select>
		    </td>
		    <td><html:textarea property="quinfo" rows="8"/></td>
		    <td><html:textarea property="content" rows="8"/></td>
		    <td><html:textarea property="remark" rows="8"/></td>
		    <td valign="top">
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.add'/>" onclick="add()"/>
		    </td>
		  </tr>
		</table>
		<br/>
		<!-- 列表页信息 -->
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td colspan="4"><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.buttomtitle"/></td>
		  </tr>
		  <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.taginfo"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.quinfo"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policelist.remark"/></td>
		    <td></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td width="8%" class="<%=style%>"><bean:write name="c" property="taginfo" filter="true"/></td>
		    <td width="15%" class="<%=style%>"><bean:write name="c" property="quinfo" filter="true"/></td>
		    <td width="68%" class="<%=style%>"><bean:write name="c" property="remark" filter="true"/></td>
		    <td width="9%" class="<%=style%>">
		    <img alt="<bean:message bundle='pcc' key='et.pcc.assay.question.questionlist.moreinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('phone.do?method=toInfoLoad&type=see&tempid=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		</table>
		<html:hidden property="pid"/>
    </html:form>
  </body>
</html:html>
