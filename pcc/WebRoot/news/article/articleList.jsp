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
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
        //选择所有
    	function selectAll() {
			for (var i=0;i<document.forms[0].chk.length;i++) {
				var e=document.forms[0].chk[i];
				e.checked=!e.checked;
			}
		}
		//执行操作
		function operit(){
			document.forms[0].action = "../opernews.do?method=operArticleInfo";
			document.forms[0].submit();
		}
		//移动
		function moveall(){
			document.forms[0].action = "../opernews.do?method=moveAll";
			document.forms[0].submit();
		}
    </script>
  </head>
  
  <body>
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");</script>
	</logic:notEmpty>
    <html:form action="/news/opernews.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.articlelist.select"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.articlelist.title"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.articlelist.updatetime"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.articlelist.state"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.common.operater"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <html:multibox property="chk"><bean:write name="c" property="articleid" filter="true"/></html:multibox>
		    </td>
		    <td class="<%=style%>"><bean:write name="c" property="title" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="updatetime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="state" filter="true"/></td>
		    <td align="center" class="<%=style%>">
		    <html:link action="/news/opernews.do?method=toArticleLoad&type=update" paramId="id" paramName="c" paramProperty="articleid" onclick="window.open('','windows','750.700,scrollbars=yes')" target="windows">
		    <bean:message key="agrofront.common.update"/>
		    </html:link>
		    /
		    <html:link action="/news/opernews.do?method=toArticleLoad&type=delete" paramId="id" paramName="c" paramProperty="articleid" onclick="window.open('','windows','750.700,scrollbars=yes')" target="windows">
		    <bean:message key="agrofront.common.delete"/>
		    </html:link>
		    /
			<logic:equal name="c" property="ontop" value="0">
			<html:link action="/news/opernews.do?method=toArticleLoad&type=puttop&state=put" paramId="id" paramName="c" paramProperty="articleid">
			<bean:message key="agrofront.news.article.articlelist.puttop"/>
			</html:link>
			</logic:equal>
			<logic:equal name="c" property="ontop" value="1">
			<html:link action="/news/opernews.do?method=toArticleLoad&type=puttop&state=unput" paramId="id" paramName="c" paramProperty="articleid">
			<bean:message key="agrofront.news.article.articlelist.unputtop"/>
			</html:link>
			</logic:equal>
		    /
			<logic:equal name="c" property="elite" value="☆">
			<html:link action="/news/opernews.do?method=toArticleLoad&type=putpink&state=put" paramId="id" paramName="c" paramProperty="articleid">
			<bean:message key="agrofront.news.article.articlelist.putpink"/>
			</html:link>
			</logic:equal>
			<logic:equal name="c" property="elite" value="★(荐)">
			<html:link action="/news/opernews.do?method=toArticleLoad&type=putpink&state=unput" paramId="id" paramName="c" paramProperty="articleid">
			<bean:message key="agrofront.news.article.articlelist.delpink"/>
			</html:link>
			</logic:equal>		    	    
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4" class="tdbgcolorlist2">
		    <bean:message key="agrofront.news.article.articlelist.operator"/>
		    <a href="javascript:selectAll()"><bean:message key="agrofront.news.article.articlelist.selectornot"/></a>
		    <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
		    <html:radio property="operator" value="0"/><bean:message key="agrofront.news.article.articlelist.delete"/>
		    <html:radio property="operator" value="1"/><bean:message key="agrofront.news.article.articlelist.delrecord"/>
		    <html:radio property="operator" value="2"/><bean:message key="agrofront.news.article.articlelist.putpink"/>
		    <br/>
		    <html:radio property="operator" value="3"/><bean:message key="agrofront.news.article.articlelist.delpink"/>
		    <html:radio property="operator" value="4"/><bean:message key="agrofront.news.article.articlelist.puttop"/>
		    <html:radio property="operator" value="5"/><bean:message key="agrofront.news.article.articlelist.unputtop"/>
		    <input type="button" name="btnSubmit" value="<bean:message key='agrofront.news.article.articlelist.operit'/>" onclick="operit()"/>
		    </td>
	        <td class="tdbgcolorlist2">
	        <html:select property="classid">		
        	<html:option value="">请选择</html:option>
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
            <input type="button" name="btnMoveAll" value="<bean:message key='agrofront.news.article.articlelist.moveall'/>" onclick="moveall()"/></td>
		  </tr>
		  <tr>
		    <td colspan="5">
				<page:page name="agropageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
