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
  </head>
  
  <style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href=".zhi" rel="stylesheet" type="text/css" />
<link href="css/styleA.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
.STYLE8 {
	color: #FF6600;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	font-weight: bold;
}
-->
</style>

  <body bgcolor="#eeeeee">
  <html:form action="/forum/userInfo" method="post">
  <%-- jps include ͷ --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- �� --%>

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
<%--   �ӵ�����    --%>



<%----%>
<%--�Ѵ���ӵ���--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
          <tr>
            <td colspan="4" background="../../images/forum/di.jpg">&nbsp;</td>
            </tr>
          <tr>
            <td height="40" colspan="4"><div align="center" class="font-big STYLE8"><bean:write name="userInfo" property="id" filter="ture"/></div></td>
            </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
            </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 1.�������� </td>
            </tr>
          <tr>
            <td width="137">&nbsp;�Ա�</td>
            <td width="379">&nbsp;��������</td>
            <td width="124">&nbsp;ע��ʱ�䣺</td>
            <td width="276">&nbsp;2006-10-10&nbsp; 10��13 </td>
          </tr>
          <tr>
            <td>&nbsp;�ܹ�����</td>
            <td>&nbsp;����44&nbsp;&nbsp; ��ɾ���⣺0&nbsp;&nbsp;&nbsp; ��ɾ�ظ���0&nbsp;&nbsp; ����������0 <br />
              &nbsp;��󷢱�ʱ�䣺2006-12-12 10��13 </td>
            <td>&nbsp;���ʣ�</td>
            <td>&nbsp;������ʱ�䣺2006-12-12 10��13<br />
              &nbsp;����¼ʱ�䣺2006-12-12 10��13</td>
          </tr>
          <tr>
            <td>&nbsp;��Ф��</td>
            <td>&nbsp;δ��</td>
            <td>&nbsp;������</td>
            <td>&nbsp;δ�� </td>
          </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 2.�������� </td>
            </tr>
          <tr>
            <td>&nbsp;ͷ�Σ�</td>
            <td>&nbsp;û��</td>
            <td>&nbsp;����</td>
            <td>&nbsp;��������[����](̳����</td>
          </tr>
          <tr>
            <td>&nbsp;����/��ħ��/�ȼ���</td>
            <td>&nbsp;6����/248��ħ��/44</td>
            <td>&nbsp;���ɣ�</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 3.��ϵ���� </td>
            </tr>
          <tr>
            <td>&nbsp;��ҳ��</td>
            <td>&nbsp;û��</td>
            <td>&nbsp;���䣺</td>
            <td>&nbsp;�û���Ը����</td>
          </tr>
          <tr>
            <td>&nbsp;QQ��</td>
            <td>&nbsp;û��</td>
            <td>&nbsp;���ԣ�</td>
            <td>&nbsp;δ��</td>
          </tr>
          <tr>
            <td>&nbsp;ICQ:</td>
            <td>&nbsp;û��</td>
            <td>&nbsp;MSN��</td>
            <td>&nbsp;û��</td>
          </tr>
          <tr>
            <td colspan="4" background="../../images/forum/topbg.gif">&nbsp;&nbsp; 4.�������� </td>
            </tr>
          <tr>
            <td>&nbsp;���Ҽ�飺</td>
            <td colspan="3">&nbsp;</td>
            </tr>
          <tr>
            <td>&nbsp;ǩ����</td>
            <td colspan="3">&nbsp;</td>
            </tr>
          <tr>
            <td colspan="4"><div align="center">��ض�����<img src="../../images/forum/mybbs.gif" width="18" height="18" />������Ϣ <img src="../../images/forum/whos_online.gif" width="30" height="30" /> ���������ҷ������� <img src="../../images/forum/mybbs.gif" width="18" height="18" />�����Һ���¼</div></td>
            </tr>
          <tr>
            <td colspan="4" background="../../images/forum/di.jpg"><div align="center"><a href="/ETforum/forum/forumList.do?method=toForumList&moduleId=1">��̳��ҳ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a onclick="history.back()">����ǰҳ</a> </div>
              <div align="left"></div></td>
            </tr>
        </table>
<%----%>




<%--  �������  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--��--%>
</html:form>
  </body>
</html:html>
