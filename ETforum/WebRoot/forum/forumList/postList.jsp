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
    <script language="javascript" src="../../js/form.js"></script>
    <script language="javascript">
    	//检查
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.title,"帖子标题")) return false;
    		if (!checkNotNull(addstaffer.content,"帖子内容")) return false;
    		return true;
    	}
    	//跳转到发帖页
    	function toSendPost(){
    		document.forms[0].action = "../postOper/oper.do?method=toSendPosts&itemid=<bean:write name='itemid'/>";
    		document.forms[0].submit();
    	}
    	//发帖
    	function sendPost(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../postOper/oper.do?method=sendPosts&itemid=<bean:write name='itemid'/>";
    		document.forms[0].submit();
    		}
    	}
    	//清除
    	function clear(){
    		document.forms[0].title.value="";
    		document.forms[0].content.value="";
    	}
    	
    </script>

  </head>
  
  <body bgcolor="#eeeeee" onLoad="clear()">
  <html:form action="/forum/postOper/oper" method="post">
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



<%----%>
<%--把代码加到这--%>

         <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="26" background="../../images/forum/biaoti1_03.jpg">&nbsp;&nbsp;&nbsp; <span class="STYLE1">【Forum&gt;&gt;默认版块】 </span></td>
              </tr>
          </table>
          
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
              </tr>
          </table>
          
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="140"><img src="../../images/forum/fabiao.gif" width="130" height="29" border="0" onClick="toSendPost()"/></td>
                <td width="140"></td>
                <td>&nbsp;</td>
                <td width="350"></td>
              </tr>
          </table>

	<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="600" height="36" colspan="3" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">主题</div></td>
                <td width="50"  background="../../images/forum/di.jpg" class="s_headline2"><div align="center">作者</div></td>
                <td width="50"  background="../../images/forum/di.jpg" class="s_headline2"><div align="center">回复</div></td>
                <td width="50"  background="../../images/forum/di.jpg" class="s_headline2"><div align="center">点击</div></td>
                <td width="120" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">最后发表</div></td>
              </tr>
<%--            <tr>--%>
<%--              <td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>--%>
<%--                <td width="50"><div align="center"><img src="../../images/emotion/3.gif" width="19" height="19" /></div></td>--%>
<%--                <td height="36"><div align="left">&nbsp;<img src="../../images/forum/y09.gif" width="10" height="10" /> <a href="text-moren.html">AAAAAAAAAAAAAAAAAAAA</a></div></td>--%>
<%--                <td><div align="center">11</div></td>--%>
<%--                <td><div align="center">10</div></td>--%>
<%--                <td><div align="center">15</div></td>--%>
<%--                <td><div align="center">2006-12-6</div></td>--%>
<%--              </tr>--%>
<%--            <tr>--%>
<%--              <td height="50" colspan="7"><div align="center" class="STYLE1">论 坛 主 题</div></td>--%>
<%--            </tr>
        <%--logic--%>
		<logic:iterate id="c" name="postlist" indexId="i">
            <tr>
                <td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>
                <td width="50"><div align="center"><img src="../../images/emotion/8.gif" width="19" height="19" /></div></td>
                <td width="500" height="36" class="s_headline1"><div align="left"> &nbsp;<img src="../../images/forum/y09.gif" width="10" height="10" />
                <a href="../forumList.do?method=toAnswerPostList&itemid=<bean:write name='itemid'/>&postid=<bean:write name='c' property='id'/>"><bean:write name="c" property="title" filter="true"/></a>
                </div></td>
                <td width="50" class="s_headline1"><div align="center">
                  <a href="../userInfo.do?method=toUserInfoLoad&userId=<bean:write name='c' property='userkey'/>">
		            <bean:write name="c" property="userkey" filter="true"/>
		          </a>
                </div>
                </td>
                <td width="50" class="s_headline1"><div align="center">20</div></td>
                <td width="50" class="s_headline1"><div align="center">500</div></td>
                <td width="120" class="s_headline1"><div align="center"><bean:write name='c' property='postAt' format="yyyy-MM-dd HH:mm:ss"/></div></td>
              </tr>
        </logic:iterate>       
	</table>

<%--          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">--%>
<%--            <tr>--%>
<%--              <td>&nbsp;</td>--%>
<%--              </tr>--%>
<%--          </table>--%>
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="638" background="../../images/forum/di.jpg">
              </td>
                <td width="278" background="../../images/forum/di.jpg">
                 <table width="330" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="../../images/forum/di.jpg" colspan="6">
                      <div align="center">               	  
						  <page:page name="postpageTurning" style="second"/>				   
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
          
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
              </tr>
          </table>
          
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="26" background="../../images/forum/di.jpg" class="STYLE1">&nbsp; 快速回复</td>
              </tr>
            <tr>
              <td><table width="916" border="1" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="220"><table width="220" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>标题</td>
                          </tr>
                    <tr>
                      <td>文章内容</td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox" value="checkbox" />
                        HTML代码不可用</label></td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox2" value="checkbox" />
                        关闭BMB Code </label></td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox3" value="checkbox" />
                        关闭表情图片 </label></td>
                          </tr>
                    <tr>
                      <td>&nbsp;</td>
                          </tr>
                    <tr>
                      <td>上传附件：开启 </td>
                          </tr>
                    <tr>
                      <td>最大字节：512000字节</td>
                          </tr>
                    <tr>
                      <td>最少发贴：0篇</td>
                          </tr>
                    </table></td>
                      <td width="658"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td></td>
                                  <td><label>
                                  <%--         标题                           --%>
                                  &nbsp;&nbsp;&nbsp;<html:text property="title"/>
                                  </label></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                                </tr>
                            </table></td>
                          </tr>
                        <tr>
                          <td width="2%"><label>
                            <div align="center"></div>
                            </label></td>
                            <td width="98%">
                            <%--              帖子内容              --%>
                            <html:textarea property="content" rows="10" cols="80"/>
                            </td>
                          </tr>
                        <tr>
                          <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../images/forum/fa.gif" alt="1" width="100" height="20" onClick="sendPost()"/>&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" name="btnreset" value="重置"></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
          </table>
          
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
              </tr>
          </table>


<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--加--%>
 
  </html:form>
  </body>
</html:html>
