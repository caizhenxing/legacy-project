<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    
<%--    <script language="javascript">--%>
<%--    	//跳转到发帖页--%>
<%--    	function toSendPost(){--%>
<%--    		document.forms[0].action = "../postOper/oper.do?method=toSendPosts&itemid=<bean:write name='itemid'/>";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    </script>--%>

  </head>
  
  <body bgcolor="#eeeeee">
  <%-- jps include 头 --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- 加 --%>

<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
<%--   加到这里    --%>


<jsp:include flush="true" page="../common/navigation.jsp"></jsp:include>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
<%----%>
<%--把代码加到这--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="36" colspan="3" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">主题</div></td>
<%--                <td width="130" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">作者</div></td>--%>
<%--                <td width="70" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">回复</div></td>--%>
                <td width="120" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">时间</div></td>
                <td width="100" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">操作</div></td>
              </tr>
            <logic:iterate id="c" name="postlist" indexId="i">
            <tr>
                <td td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>
                <td td width="50"><div align="center"><img src="../../images/emotion/28.gif" width="19" height="19" /></div></td>
                <td  height="36" class="s_headline1"><div align="left"> &nbsp;<img src="../../images/forum/y09.gif" width="10" height="10" /> <a href="text-moren.html"><bean:write name="c" property="collName" filter="true"/></a></div></td>
<%--                <td class="s_headline1"><div align="center">一休</div></td>--%>
<%--                <td class="s_headline1"><div align="center">10</div></td>--%>
                <td  width="120" class="s_headline1"><div align="center"><bean:write name="c" property="collTime" filter="true" format="yyyy-MM-dd HH:mm:ss"/></div></td>
                <td  width="100" class="s_headline1"><div align="center"><a href="../postQuery.do?method=toDelMySavePost&id=<bean:write name='c' property='id'/>">删除</a></div></td>
            </tr>
            </logic:iterate>
</table>
		    <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="638" background="../../images/forum/di.jpg">
              </td>
                <td width="278" background="../../images/forum/di.jpg">
                 <table width="330" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="../../images/forum/di.jpg" colspan="6">
                      <div align="center">               	  
						  <page:page name="mySavePostpageTurning" style="first"/>				   
					  </div>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
          </table>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
<%----%>




<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>

<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
<%--加--%>
<%--    <html:form action="/forum/forumList" method="post">--%>
    	
<%--		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tdbgcolor2">--%>
<%--          <tr>--%>
<%--            <td width="74%" align="left">--%>
<%--              当前位置->我的收藏--%>
<%--            </td>--%>
<%--          </tr>--%>
<%--        </table>--%>
<%--		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">--%>
<%--		<tr class="tdbgpiclist" align="center">--%>
<%--		<td width="5%" >&nbsp;</td>--%>
<%--		<td width="56%" align="center" >标题</td>--%>
<%--		<td width="13%" >时间</td>--%>
<%--		<td width="13%" >操作</td>--%>
<%--		</tr>--%>
<%--		<logic:iterate id="c" name="postlist" indexId="i">--%>
<%--		<tr class="row" onMouseOver="this.className='row1'" onMouseOut="this.className='row'">--%>
<%--		<td class=tdbgcolorlist2 align="center"><img src="red_forum.gif" alt="" /></td>--%>
<%--		<td class=tdbgcolorlist2 align="left">--%>
<%--		<span class="bold"><bean:write name="c" property="collName" filter="true"/></span>--%>
<%--		<span class="todayposts">s</span>--%>
<%--		<br><span class="smalltxt"></span></td>--%>
<%--		<td class=tdbgcolorlist2  align="center"><bean:write name="c" property="collTime" filter="true" format="yyyy-MM-dd HH:mm:ss"/></td>--%>
<%--		<td class=tdbgcolorlist2  align="center"><a href="../postQuery.do?method=toDelMySavePost&id=<bean:write name='c' property='id'/>">删除</td>--%>
<%--		</tr>--%>
<%--		</logic:iterate>--%>
<%--		<tr>--%>
<%--				    <td colspan="5">--%>
<%--						<page:page name="mySavePostpageTurning" style="first"/>				    </td>--%>
<%--		  </tr>--%>
<%--		</table>--%>
<%--  </html:form>--%>
  </body>
</html:html>
