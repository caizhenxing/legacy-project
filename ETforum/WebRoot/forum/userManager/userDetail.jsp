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
    <html:form action="/forum/userManager" method="post">
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
<%----%>
<table width="916" border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="#CCCCCC">
          <tr>
            <td colspan="2" background="../../images/forum/di.jpg" align="center">&nbsp; �鿴�û�����</td>
          </tr>
          <tr>
            <td width="330" colspan="2">&nbsp;
            <bean:write name="dto" property="id"/>
          </td></tr>
          <tr>
            <td>&nbsp; �ǳƣ�</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="name"/>
                </div>
            </label></td>
          </tr>
          <tr><td>
                 &nbsp; ע��ʱ�䣺</td>
            <td width="580"><label>
              <div align="left">
                <bean:write name="dto" property="registerDate" format="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; �ϴε�½ʱ�䣺</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="lastLogin" format="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ע��IP��</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="registerIp"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ���䣺</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="email"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; QQ���룺</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="qq"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; �û�ͷ�Σ� </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="userLevel"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; �������� </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="sendPostNum"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; �������� </td>
            <td><label>
              <div align="left">
               <bean:write name="dto" property="answerPostNum"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ����: </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="point"/>
                </div>
            </label></td>
          </tr>
<%--          <tr>--%>
<%--            <td>&nbsp; ���������ڣ� </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="endSendPost" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; ������û�е�¼��̳�� </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="noLoginDate" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; ���һ�ε�¼IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="newlyIp" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; ע��ʱIP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="ip" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; �û��飺 </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <select name="select">--%>
<%--                  <option>̳��</option>--%>
<%--                  <option>����̳��</option>--%>
<%--                  <option>������</option>--%>
<%--                  <option>����</option>--%>
<%--                  <option>��ͨ�û�</option>--%>
<%--                  <option>��֤�û�</option>--%>
<%--                  <option>��ֹ����</option>--%>
<%--                  <option>�ο�</option>--%>
<%--                </select>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
          <tr>
            <td height="30" colspan="2"><label>
              <div align="left">
                <input type="button" name="Submit" value="������һҳ" onclick="history.back()"/>
                
<%--                &nbsp; --%>
<%--                <html:reset value="���"/>--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit3" value="��̳����" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit4" value="��̳�ʼ�" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit5" value="�û�����" />--%>
              </div>
              </label></td>
            </tr>
        </table>



<%--  �������  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--��--%>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
    </html:form>
  </body>
</html:html>
