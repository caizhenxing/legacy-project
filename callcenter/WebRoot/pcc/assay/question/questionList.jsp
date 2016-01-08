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
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/pcc/assay/question.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.question.questionlist.tagtype"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.question.questionlist.tagquestion"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.question.questionlist.taginfo"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.question.questionlist.operator"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <bean:write name="c" property="taginfo" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="quinfo" filter="true"/>
            </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="content" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message bundle='pcc' key='et.pcc.assay.question.questionlist.moreinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('question.do?method=toQuestionLoad&type=see&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="3">
				<page:page name="questionpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
