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
  
  <body bgcolor="#eeeeee">
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
<jsp:include flush="true" page="../common/navigation.jsp"></jsp:include>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
<jsp:include flush="true" page="panel.jsp"></jsp:include>          
<%--�Ѵ���ӵ���--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td valign="middle"><div align="center">                ����,��ӭ���������壬�����ṩ��ص������趨����̳��ݹ��ܵ�  <br />
                <table width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
                  <tr>
                    <td colspan="2" align="left" background="../../images/forum/zhubiaoti_03.jpg"> &nbsp;������Ϣ</td>
                    </tr>
                  <tr>
                    <td width="149" align="left">&nbsp;�û�����/�ǳ�</td>
                    <td width="745" align="left">&nbsp; <bean:write name="userInfo" property="id"/>/<bean:write name="userInfo" property="name"/> </td>
                  </tr>
                  <tr>
                    <td align="left">&nbsp;����</td>
                    <td align="left">&nbsp;<bean:write name="userInfo" property="point"/></td>
                  </tr>
                  <tr>
                    <td align="left">&nbsp;ͷ��</td>
                    <td align="left">&nbsp;</td>
                  </tr>
                  <tr>
                    <td align="left">&nbsp;�ȼ�</td>
                    <td align="left">&nbsp;<bean:write name="userInfo" property="userLevel"/></td>
                  </tr>
                </table>
              </div></td>
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
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
  </body>
</html:html>
