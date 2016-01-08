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
		//删除指定
		function delIt(articleid){
			if(confirm("确定要彻底删除此文章吗？一旦删除将不能恢复！")){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=del&articleid="+articleid;
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		//删除选定的文章
		function delSelectIt(){
			if(confirm("确定要彻底删除选中的文章吗？一旦删除将不能恢复！")){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=delselect";
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		//清空回收站
		function delAll(){
			if(confirm("确定要清空回收站？一旦清空将不能恢复！")){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=delall";
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		//还原选定
		function revSelectIt(){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=revselect";
				document.forms[0].submit();
		}
		//还原所有
		function revAll(){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=revall";
				document.forms[0].submit();	
		}
    </script>
  </head>
  
  <body>
    <logic:notEmpty name="idus_state">
	<script>alert("<bean:write name='idus_state'/>");</script>
	</logic:notEmpty>
    <html:form action="/news/opernews.do" method="post">
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    	<tr>
		    <td colspan="2" class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    <bean:message key="agrofront.news.article.recyclelist.recycletitle"/>
		    </td>
		  </tr>
		</table>
    
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.recyclelist.select"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.recyclelist.title"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.recyclelist.author"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.recyclelist.updatetime"/></td>
		    <td class="tdbgpiclist"><bean:message key="agrofront.news.article.recyclelist.state"/></td>
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
		    <td class="<%=style%>"><bean:write name="c" property="author" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="updatetime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="state" filter="true"/></td>
		    <td align="center" class="<%=style%>">
		    <html:link action="/news/opernews.do?method=operRecycleInfo&oper=rev" paramId="id" paramName="c" paramProperty="articleid">
		    <bean:message key="agrofront.news.article.recyclelist.recove"/>
		    </html:link>
		    /&nbsp;
		    <a onclick="delIt('<bean:write name='c' property='articleid' filter='true'/>')">
		    <bean:message key="agrofront.news.article.recyclelist.delete"/>
		    </a>	      	    
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="6" class="tdbgcolorlist1">
		    <bean:message key="agrofront.news.article.recyclelist.operator"/>
		    <a href="javascript:selectAll()"><bean:message key="agrofront.news.article.recyclelist.selectornot"/></a>
		    <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
		    <input type="button" name="btnRec" value="<bean:message key='agrofront.news.article.recyclelist.delselect'/>" onclick="delSelectIt()"/>
		    <input type="button" name="btnRec" value="<bean:message key='agrofront.news.article.recyclelist.cleanall'/>" onclick="delAll()"/>
		    <input type="button" name="btnRec" value="<bean:message key='agrofront.news.article.recyclelist.revselect'/>" onclick="revSelectIt()"/>
		    <input type="button" name="btnRec" value="<bean:message key='agrofront.news.article.recyclelist.recall'/>" onclick="revAll()"/>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="6" align="right">
				<page:page name="agropageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
