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
    	//跳转到注册信息页
    	function jump(){
    		document.forms[0].action = "../userOper/register.do?method=toRegister";
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/userOper/register" method="post">
    
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
	
	
		<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><table width="916" height="26" border="1" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="26" background="images/topbg.gif">&nbsp;&nbsp;&nbsp; FORUM&gt;&gt;欢迎您注册</td>
                  </tr>
                </table>
                  <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="916" border="1" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="26" background="images/biaoti1_03.jpg">注册程序</td>
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
                                      <td><div align="center"><strong>服务条款和声明</strong> </div></td>
                                      </tr>
                                    <tr>
                                      <td><strong>继续注册前请先阅读论坛协议</strong><br />
                                        <span class="danzhi"><br />
                                          欢迎您加入本站点参加交流和讨论，本站点为公共论坛，为维护网上公共秩序和社会稳定，请您自觉遵守以下条款：<br />
                                          <br />
                                          一、不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本站制作、复制和传播下列信息： <br />
                                          （一）煽动抗拒、破坏宪法和法律、行政法规实施的；<br />
                                          （二）煽动颠覆国家政权，推翻社会主义制度的；<br />
                                          （三）煽动分裂国家、破坏国家统一的；<br />
                                          （四）煽动民族仇恨、民族歧视，破坏民族团结的；<br />
                                          （五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br />
                                          （六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；<br />
                                          （七）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；<br />
                                          （八）损害国家机关信誉的；<br />
                                          （九）其他违反宪法和法律行政法规的；<br />
                                          （十）进行商业广告行为的。<br />
                                          <br />
                                          二、互相尊重，对自己的言论和行为负责。 <br />
                                          <br />
                                          三、用户名注册规定<br />
                                          （一）请勿以党和国家领导人或其他名人的真实姓名、字号、艺名、笔名注册；<br />
                                          （二）请勿以国家机构或其他机构的名称注册；<br />
                                          （三）请勿注册和其他网友之名相近、相仿的名字，也不要使用与管理人员过于相似的名字；<br />
                                          （四）请勿注册不文明、不健康之笔名；<br />
                                          （五）请勿注册易产生歧义、引起他人误解之笔名；</span></td>
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
                            <input type="button" class="bginput" value=" 我 同 意 " id="agreeb" onclick="jump()"/>
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
	
	
	
	
	<%--  这里结束  --%>
	        </td>
	      </tr>
	      
	    </table>
	    </td>
	  </tr>
	</table>
		
  	</html:form>
  </body>
</html:html>
