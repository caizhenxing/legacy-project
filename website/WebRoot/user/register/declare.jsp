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
    <script language="javascript">
    	//��ת��ע����Ϣҳ
    	function jump(){
    		document.forms[0].action = "../userOper/register.do?method=toRegister";
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/userOper/register" method="post">
    
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
	
	
		<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><table width="916" height="26" border="1" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="26" background="images/topbg.gif">&nbsp;&nbsp;&nbsp; FORUM&gt;&gt;��ӭ��ע��</td>
                  </tr>
                </table>
                  <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="916" border="1" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="26" background="images/biaoti1_03.jpg">ע�����</td>
                    </tr>
                    <tr>
                      <td><table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><table cellspacing="0" cellpadding="0" width="94%" align="center" border="0">
                            <tbody>
                              <tr>
                                <td><table width="916" border="0" align="center" cellpadding="0" cellspacing="1">
                                  <tbody>
                                    <tr>
                                      <td><div align="center"><strong>�������������</strong> </div></td>
                                      </tr>
                                    <tr>
                                      <td><strong>����ע��ǰ�����Ķ���̳Э��</strong><br />
                                        <span class="danzhi"><br />
                                          ��ӭ�����뱾վ��μӽ��������ۣ���վ��Ϊ������̳��Ϊά�����Ϲ������������ȶ��������Ծ������������<br />
                                          <br />
                                          һ���������ñ�վΣ�����Ұ�ȫ��й¶�������ܣ������ַ�������Ἧ��ĺ͹���ĺϷ�Ȩ�棬�������ñ�վ���������ƺʹ���������Ϣ�� <br />
                                          ��һ��ɿ�����ܡ��ƻ��ܷ��ͷ��ɡ���������ʵʩ�ģ�<br />
                                          ������ɿ���߸�������Ȩ���Ʒ���������ƶȵģ�<br />
                                          ������ɿ�����ѹ��ҡ��ƻ�����ͳһ�ģ�<br />
                                          ���ģ�ɿ�������ޡ��������ӣ��ƻ������Ž�ģ�<br />
                                          ���壩�������������ʵ��ɢ��ҥ�ԣ������������ģ�<br />
                                          ����������⽨���š����ࡢɫ�顢�Ĳ�����������ɱ���ֲ�����������ģ�<br />
                                          ���ߣ���Ȼ�������˻���������ʵ�̰����˵ģ����߽����������⹥���ģ�<br />
                                          ���ˣ��𺦹��һ��������ģ�<br />
                                          ���ţ�����Υ���ܷ��ͷ�����������ģ�<br />
                                          ��ʮ��������ҵ�����Ϊ�ġ�<br />
                                          <br />
                                          �����������أ����Լ������ۺ���Ϊ���� <br />
                                          <br />
                                          �����û���ע��涨<br />
                                          ��һ�������Ե��͹����쵼�˻��������˵���ʵ�������ֺš�����������ע�᣻<br />
                                          �����������Թ��һ�������������������ע�᣻<br />
                                          ����������ע�����������֮���������µ����֣�Ҳ��Ҫʹ���������Ա�������Ƶ����֣�<br />
                                          ���ģ�����ע�᲻������������֮������<br />
                                          ���壩����ע���ײ������塢�����������֮������</span></td>
                                      </tr>
                                    </tbody>
                                  </table></td>
                              </tr>
                              </tbody>
                            </table></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  <table width="916" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><div align="center">
                        <table width="50" height="20" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td>
                            <div align="center">
                            <input type="button" class="bginput" value=" �� ͬ �� " id="agreeb" onclick="jump()"/>
                            </div>
                            </td>
                          </tr>
                          </table>
                        <label></label>
                      </div></td>
                    </tr>
                </table></td>
              </tr>
          </table>
	
	
	
	
	<%--  �������  --%>
	        </td>
	      </tr>
	      
	    </table>
	    </td>
	  </tr>
	</table>
		
  	</html:form>
  </body>
</html:html>
