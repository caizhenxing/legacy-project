<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ taglib uri="/WEB-INF/right.tld" prefix="right" %>

<%
	String userkey = "";
	if(session.getAttribute("userkey")!=null){
		userkey = session.getAttribute("userkey").toString();
	}
	request.setAttribute("usercheck",userkey);
%>

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
    	//���
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.title,"���ӱ���")) return false;
    		if (!checkNotNull(addstaffer.content,"��������")) return false;
    		return true;
    	}
    	//��ת������ҳ
    	function toSendPost(){
    		document.forms[0].action = "../postOper/oper.do?method=toSendPosts&itemid=<bean:write name='itemid'/>";
    		document.forms[0].submit();
    	}
    	//��ת������ҳ
    	function toAnswerPost(){
    		document.forms[0].action = "../postOper/oper.do?method=toAnswerPosts&itemid=<bean:write name='itemid'/>&postid=<bean:write name='postid'/>";
    		document.forms[0].submit();
    	}
    	//����
    	function answerPost(){
    	    var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../postOper/oper.do?method=answerPosts&itemid=<bean:write name='itemid'/>&postid=<bean:write name='postid'/>";
    		document.forms[0].submit();
    		}
    	}
    	//���
    	function clear(){
    		document.forms[0].title.value="";
    		document.forms[0].content.value="";
    	}
    </script>

  </head>
  
  <body bgcolor="#eeeeee" onLoad="clear()">
  <html:form action="/forum/postOper/oper" method="post">
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
	<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td width="742" background="../../images/forum/biaoti1_03.jpg">&nbsp;&nbsp;&nbsp; <span class="STYLE1">��Forum&gt;&gt;Ĭ�ϰ��&gt;&gt;123456 �� </span></td>
	                <td width="168" background="../../images/forum/biaoti1_03.jpg"></td>
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
	                <td width="142"><img src="../../images/forum/huifu.gif" width="130" height="28" border="0" onClick="toAnswerPost()"/></td>
	                <td width="384">&nbsp;</td>
	                <td width="450"><table width="100" border="1" align="right" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td>
	                    <!-- ��ʾȨ�� -->
	                    <right:totalright id="postid"/>
	                    </td>
	                  </tr>
	                </table></td>
	              </tr>
	</table>
<%--�ظ��б�--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="../../images/forum/nabiaoti_03.jpg">���±���<bean:write name='posttitle'/></td>
    </tr>
</table>
<logic:iterate id="c" name="answerlist" indexId="ind">
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="155" align="left" valign="top"><p>&nbsp;</p>
                  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="danzhi"><bean:write name='c' property='name'/> </td>
                    </tr>
                    <tr>
                      <td class="danzhi"></td>
                    </tr>
                    <tr>
                      <td class="danzhi">&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="danzhi">��Ϣ</td>
                    </tr>
                    <tr>
                      <td class="danzhi">����δ��</td>
                    </tr>
                    <tr>
                      <td class="danzhi">����8��</td>
                    </tr>
                    <tr>
                      <td class="danzhi">����0��</td>
                    </tr>
                    <tr>
                      <td class="danzhi">����1��</td>
                    </tr>
                    <tr>
                      <td class="danzhi">����125��ħ��</td>
                    </tr>
                    <tr>
                      <td class="danzhi">ע��<bean:write name='c' property='regtime' format="yyyy-MM-dd"/></td>
                    </tr>
                </table></td>
                <td width="755" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/topbg.gif">
                  <tr>
                    <td class="s_headline2">
                      <img src="dot_01.gif" width="3" height="6" />
                      <right:right beanName="c" propertyId="id" propertyAuthor="userkey"/>
                      <right:floorNum page="answerpageTurning" num="ind"/>
                      </td>
                      </tr>
                </table>
                <table width="100%" height="208" border="0" cellpadding="0" cellspacing="0" background="images/topbg.gif">
                  <tr>
                    <td valign="top">
                    	<bean:write name="c" property="content" filter="false" />                    </td>
                    </tr>
                </table></td>
              </tr>
              <tr>
              <td background="../../images/forum/nabiaoti_03.jpg">2006-12-05&nbsp; 14��46 </td>
                <td background="../../images/forum/nabiaoti_03.jpg"><bean:write name='c' property='ip'/></td>
            </tr>
          </table>
</logic:iterate>
<%----%>




<%--  �������  --%>        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--��--%>
<%--    <html:form action="/forum/postOper/oper" method="post">--%>
    	
		
      	
        <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tdbgcolor2">
			<tr>
				    <td colspan="2">
						<page:page name="answerpageTurning" style="first"/>
					</td>
		    </tr>
        </table>
        
        
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
              </tr>
          </table>
          
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="26" background="../../images/forum/di.jpg" class="STYLE1">&nbsp; ���ٻظ�</td>
              </tr>
            <tr>
              <td><table width="916" border="1" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="220"><table width="220" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>����</td>
                          </tr>
                    <tr>
                      <td>��������</td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox" value="checkbox" />
                        HTML���벻����</label></td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox2" value="checkbox" />
                        �ر�BMB Code </label></td>
                          </tr>
                    <tr>
                      <td><label>
                        <input type="checkbox" name="checkbox3" value="checkbox" />
                        �رձ���ͼƬ </label></td>
                          </tr>
                    <tr>
                      <td>&nbsp;</td>
                          </tr>
                    <tr>
                      <td>�ϴ����������� </td>
                          </tr>
                    <tr>
                      <td>����ֽڣ�512000�ֽ�</td>
                          </tr>
                    <tr>
                      <td>���ٷ�����0ƪ</td>
                          </tr>
                    </table></td>
                      <td width="658"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td></td>
                                  <td><label>
                                  <%--         ����                           --%>
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
                            <%--              ��������              --%>
                            <html:textarea property="content" rows="10" cols="80"/>
                            </td>
                          </tr>
                        <tr>
                          <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../images/forum/fa.gif" alt="1" width="100" height="20" onClick="answerPost()"/>&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" name="btnreset" value="����"></td>
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

  </html:form>
  </body>
</html:html>
