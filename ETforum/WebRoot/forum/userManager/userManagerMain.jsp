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
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
        //选择所有
    	function selectAll() {
			for (var i=0;i<document.forms[0].chkId.length;i++) {
				var e=document.forms[0].chkId[i];
				e.checked=!e.checked;
			}
		}
		//删除选定的文章
		function delSelectIt(){
			if(confirm("确定要彻底删除选中的用户吗？一旦删除将不能恢复！")){
				document.forms[0].action = "../userManager.do?method=toDelUser&oper=delselect";
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		//搜索用户
		function query(){    
    	      document.forms[0].action = "../userManager.do?method=toUserMain";
    		  document.forms[0].submit();   
    	}
    	//搜索用户
		function pageclear(){
    	      document.forms[0].id="";
    	      document.forms[0].email="";
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
		//固顶
		function puttop(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=puttop&state=put&id="+articleid;
			document.forms[0].submit();
		}
		//解固
		function unputtop(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=puttop&state=unput&id="+articleid;
			document.forms[0].submit();
		}
		//设为推荐
		function putgroom(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=putpink&state=put&id="+articleid;
			document.forms[0].submit();
		}
		//取消推荐
		function unputgroom(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=putpink&state=unput&id="+articleid;
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee" onunload="pageclear()">
    <html:form action="/forum/userManager" method="post">
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
<table width="916" border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="#CCCCCC">
          <tr>
            <td colspan="2" background="../../images/forum/di.jpg">&nbsp; 用户列表</td>
            </tr>
          <tr>
            <td width="330" colspan="2">&nbsp;搜索用户
          </td>
          <tr><td>
                 &nbsp; 用户名：</td>
            <td width="580"><label>
              <div align="left">
                <html:text property="id" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; Eamil：</td>
            <td><label>
              <div align="left">
                <html:text property="email" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 注册日期早于（yyyy-mm-dd)：</td>
            <td><label>
              <div align="left">
                <html:text property="beginTime" styleClass="input" readonly="true"/>
                <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(beginTime)"/>
                </div>
                
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 注册日期晚于（yyyy-mm-dd)：</td>
            <td><label>
              <div align="left">
                <html:text property="endTime" styleClass="input" readonly="true"/>
                <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endTime)"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 积分小于： </td>
            <td><label>
              <div align="left">
                <html:text property="beginPoint" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 积分大于： </td>
            <td><label>
              <div align="left">
                <html:text property="endPoint" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 发贴数小于: </td>
            <td><label>
              <div align="left">
                <html:text property="beginSendPost" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 发贴数大于： </td>
            <td><label>
              <div align="left">
                <html:text property="endSendPost" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 多少天没有登录论坛： </td>
            <td><label>
              <div align="left">
                <html:text property="noLoginDate" styleClass="input"/>
                </div>
            </label></td>
          </tr>
<%--          <tr>--%>
<%--            <td>&nbsp; 最近一次登录IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="newlyIp" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 注册时IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="ip" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 用户组： </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <select name="select">--%>
<%--                  <option>坛主</option>--%>
<%--                  <option>超级坛主</option>--%>
<%--                  <option>区版主</option>--%>
<%--                  <option>版主</option>--%>
<%--                  <option>普通用户</option>--%>
<%--                  <option>认证用户</option>--%>
<%--                  <option>禁止发言</option>--%>
<%--                  <option>游客</option>--%>
<%--                </select>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
          <tr>
            <td height="30" colspan="2"><label>
              <div align="left">
                <input type="submit" name="Submit" value="搜索用户" onclick="query()"/>
                
<%--                &nbsp; --%>
                <html:reset value="清空"/>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit3" value="论坛短信" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit4" value="论坛邮件" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit5" value="用户奖罚" />--%>
              </div>
              </label></td>
            </tr>
        </table>
        <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
        </table>
<%----%>
<table width="916" border="1" cellpadding="0" align="center" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td><table width="916" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
                <tr>
                  <td background="../../images/forum/di.jpg"><div align="center">删除</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">用户组</div></td>
<%--                  <td background="../../images/forum/di.jpg"><div align="center">UID</div></td>--%>
                  <td background="../../images/forum/di.jpg"><div align="center">用户名</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">注册日期</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">发贴数</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">积分值</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">操作选项</div></td>
                </tr>
                <logic:iterate id="c" name="userlist" indexId="i"> 
                <tr>
                  <td><label>
                    <div align="center">
<%--                      <input type="checkbox" name="checkbox" value="checkbox" />--%>
                      <html:multibox property="chkId"><bean:write name="c" property="id" filter="true"/></html:multibox>
                      </div>
                  </label></td>
                  <td><div align="center"><bean:write name="c" property="id" filter="true"/></div></td>
<%--                  <td><div align="center">1</div></td>--%>
                  <td><div align="center"><bean:write name="c" property="name" filter="true"/></div></td>
                  <td><div align="center"><bean:write name="c" property="registerDate" filter="true" format="yyyy-MM-dd HH:mm:ss"/></div></td>
                  <td><div align="center"><bean:write name="c" property="sendPostNum" filter="true"/></div></td>
                  <td><div align="center"><bean:write name="c" property="answerPostNum" filter="true"/></div></td>
                  <td><div align="center">
                          <a href="../userManager.do?method=toLoadUser&userId=<bean:write name='c' property='id'/>">查看</a>
                      </div></td>
                </tr>
                </logic:iterate>
                <tr>
                  <td colspan="8">&nbsp; <label>
<%--                    <input type="submit" name="Submit6" value="全选" />--%>
                    <a href="javascript:selectAll()"><bean:message key="agrofront.news.article.articlelist.selectornot"/></a>
		            <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
                    <input type="button" name="btnRec" value="删除所选" onclick="delSelectIt()"/>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit7" value="反选" />--%>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit8" value="重置" />--%>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit9" value="删除用户" />--%>
                  </label>
                  </td>
                  </tr>
<%--                <tr>--%>
<%--                  <td height="35" colspan="8" background="images/topbg.gif">&nbsp;《[1]》当前第1页&nbsp; 共1页&nbsp; 跳到--%>
<%--                    <label>--%>
<%--                    <input name="textfield12" type="text" size="4" />--%>
<%--                    页 --%>
<%--                    <input type="submit" name="Submit10" value="确定 " />--%>
<%--                    [20/页]                                    &nbsp;&nbsp;                                  &nbsp;&nbsp;                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; 显示--%>
<%--                    <select name="select2">--%>
<%--                      <option>所有会员</option>--%>
<%--                    </select>--%>
<%--                    <input type="submit" name="Submit11" value="确定" />--%>
<%--                    </label></td>--%>
<%--                  </tr>--%>

              </table></td>
            </tr>
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
						  <page:page name="userListpageTurning" style="first"/>				   
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
<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--加--%>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
    </html:form>
  </body>
</html:html>
