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
  
  <body bgcolor="#eeeeee" onLoad="clear()">
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
<jsp:include flush="true" page="../controlPanel/panel.jsp"></jsp:include>    
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>      
<%--�Ѵ���ӵ���--%>
<table width="916" border="1" align="center" cellpadding="4" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td colspan="6" background="../../images/forum/di.jpg"><div align="center">�� ϵ �� �� ��</div></td>
              </tr>
<%--            <tr>--%>
<%--              <td colspan="6" valign="middle"> ����û�[�û���]--%>
<%--                <label>--%>
<%--                <input name="textfield" type="text" size="12" />--%>
<%--                <input name="btnadd" type="button" value="���" size="6" />--%>
<%--                </label></td>--%>
<%--              </tr>--%>
             <logic:iterate id="c" name="userlist" indexId="i"> 
            <tr>
              <td width="60"><div align="center"><img src="../../images/forum/0.gif" width="19" height="19" /></div></td>
              <td width="485">&nbsp;
                <bean:write name="c" property="friendName" filter="true"/></td>
<%--              <td width="72">&nbsp; ���ź���</td>--%>
<%--              <td width="54">&nbsp; [��Ѷ]</td>--%>
              <td width="276">&nbsp; ���ʱ�䣺
                <bean:write name="c" property="addDate" format="yyyy-MM-dd HH:mm:ss"/></td>
              <td width="53">
              [<a href="../userInfo.do?method=toDelUser&friendId=<bean:write name='c' property='friendId'/>">
		       ɾ��
		      </a>]              </td>
            </tr>
            </logic:iterate>
<%--            <tr>--%>
<%--              <td colspan="6">&nbsp;��ǰ��1ҳ ��1�� ǰҳ ��ҳ ����--%>
<%--                <label>--%>
<%--                <input name="textfield3" type="text" size="4" />--%>
<%--                ҳ --%>
<%--                <input name="textfield4" type="text" value="ȷ��" size="6" />--%>
<%--                ����û��б�</label></td>--%>
<%--              </tr>--%>
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
						  <page:page name="userInfopageTurning" style="first"/>				   
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
