<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<jsp:directive.page import="et.bo.newsInfo.service.NewsInfoService"/>
<jsp:directive.page import="et.bo.newsInfo.GetNewsInfo"/>

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
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="800" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/banner-01.jpg" width="15" height="65" /></td>
        <td><img src="images/banner_02.jpg" width="166" height="65" /></td>
        <td><img src="images/banner_03.jpg" width="304" height="65" /></td>
        <td><img src="images/banner_04.jpg" width="365" height="65" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="800" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="73"><img src="images/left-white.jpg" width="73" height="592" /></td>
        <td width="777" valign="top"><table width="777" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2"><img src="images/main.jpg" width="777" height="42" /></td>
            </tr>
          <tr valign="top">
            <td width="152"><table width="152" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="images/menu-left-1.jpg" width="152" height="26" /></td>
              </tr>
              <tr>
                <td><table width="152" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="125" valign="middle" bgcolor="#EBEBEB"><table width="98%" border="0" align="center" cellpadding="1" cellspacing="2">
                      <tr>
                        <td class="STYLE1"><div align="right">用户名：</div></td>
                        <td><label>
                          <input name="textfield" type="text" class="STYLE1" size="10" />
                        </label></td>
                      </tr>
                      <tr>
                        <td class="STYLE1"><div align="right">密&nbsp; 码： </div></td>
                        <td><label>
                        <input name="textfield2" type="password" class="STYLE1" size="10" />
                        </label></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td><img src="images/login.gif" width="63" height="18" /></td>
                        <td><img src="images/enter.gif" width="67" height="20" /></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><table width="152" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top"><table width="152" height="83" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td valign="middle" background="images/tell.jpg"><table width="89%" border="0" align="center" cellpadding="0" cellspacing="1">
                          <tr>
                            <td class="STYLE1"><div align="right">公司电话：</div></td>
                            <td class="STYLE1">024-22511711</td>
                          </tr>
                          <tr>
                            <td class="STYLE1">&nbsp;</td>
                            <td class="STYLE1">024-22521055</td>
                          </tr>
                          <tr>
                            <td class="STYLE1"><div align="right">公司传真：</div></td>
                            <td class="STYLE1">024-22513977</td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="images/top-left.jpg" width="152" height="16" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="152" height="53" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td valign="middle" background="images/fang-1.jpg"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="44%">&nbsp;</td>
                        <td width="56%"><div align="center"><span class="STYLE4">企业资质</span></div></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><table width="152" height="61" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td valign="middle" background="images/fang-2.jpg"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="44%">&nbsp;</td>
                        <td width="56%"><div align="center"><span class="STYLE4">企业资质</span></div></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="152" height="60" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="top">
                    <td valign="middle" background="images/fang-3.jpg"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="44%">&nbsp;</td>
                        <td width="56%"><div align="center"><span class="STYLE4">企业资质</span></div></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="images/bottom-left.jpg" width="152" height="12" /></td>
              </tr>
              <tr>
                <td><table width="152" height="38" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td background="images/menu-diaocha.jpg">&nbsp; <span class="STYLE5">在线调查 </span></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><table width="152" height="118" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td valign="top" background="images/diaocha-di.jpg" class="STYLE1">请问您是如何知本网站的？<br />
                      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="18%" class="STYLE1"><label>
                            <input type="radio" name="radiobutton" value="radiobutton" />
                          </label></td>
                          <td width="82%" class="STYLE1">&nbsp;电视媒体</td>
                        </tr>
                        <tr>
                          <td class="STYLE1"><input type="radio" name="radiobutton" value="radiobutton" /></td>
                          <td class="STYLE1">&nbsp;报纸杂志</td>
                        </tr>
                        <tr>
                          <td class="STYLE1"><input type="radio" name="radiobutton" value="radiobutton" /></td>
                          <td class="STYLE1">&nbsp;广告传宣</td>
                        </tr>
                        <tr>
                          <td class="STYLE1"><input type="radio" name="radiobutton" value="radiobutton" /></td>
                          <td class="STYLE1">&nbsp;网络搜索</td>
                        </tr>
                        <tr>
                          <td class="STYLE1"><input type="radio" name="radiobutton" value="radiobutton" /></td>
                          <td class="STYLE1">&nbsp;朋友介绍</td>
                        </tr>
                        <tr>
                          <td class="STYLE1"><input type="radio" name="radiobutton" value="radiobutton" /></td>
                          <td class="STYLE1">&nbsp;其它</td>
                        </tr>
                      </table></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
            <td width="625" valign="top"><table width="625" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="625" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="images/pic1.jpg" width="123" height="151" /></td>
                    <td valign="top"><img src="images/pic2.jpg" width="125" height="151" /></td>
                    <td valign="top"><img src="images/pic3.jpg" width="126" height="151" /></td>
                    <td valign="top"><img src="images/pic4.jpg" width="175" height="151" /></td>
                    <td valign="top"><img src="images/pic5.jpg" width="76" height="151" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><table width="625" border="0" cellspacing="0" cellpadding="0">
                  <tr valign="top">
                    <td><table width="595" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td valign="top"><table width="595" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="260" valign="top"><table width="260" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td><img src="images/menu-right-1.jpg" width="260" height="24" /></td>
                              </tr>
                              <tr>
                                <td><img src="images/top-1.jpg" width="260" height="18" /></td>
                              </tr>
                              <tr>
                                <td valign="top"><table width="260" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td valign="top" background="images/mid-1.jpg"><table width="92%" border="0" align="right" cellpadding="0" cellspacing="2">
                                      <tr>
                                        <td><img src="images/picture-1.gif" width="53" height="54" /></td>
                                        <td><img src="images/line-lan1.gif" width="7" height="65" /></td>
                                        <td valign="top" class="STYLE1">
<%--                                        <span class="STYLE3"><br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1</span>--%>
<%--                                      <table width="95%" height="30" border="0" cellpadding="0" cellspacing="0">--%>
<%--                                        <logic:iterate id="c" name="listTwo" indexId="i">--%>
<%--                                           <bean:write name="c" property="trtd" filter="false"/>--%>
<%--                                        </logic:iterate>--%>
<%--                                      </table>--%>
                                      <%
                                        String news = GetNewsInfo.getNews();
                                        out.print(news);
                                      %>
                                          </td>
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                                        <td><img src="images/line-lan2.gif" width="7" height="65" /></td>
                                        <td>
<%--                                        <span class="STYLE3">--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                          新闻快讯1新闻快讯1<br />--%>
<%--                                        </span></td>--%>
<%--                                        <td>&nbsp;--%>
                                      <table width="95%" height="30" border="0" cellpadding="0" cellspacing="0">
                                      <%--             新闻              --%>
                                        <logic:iterate id="c" name="listThree" indexId="i">
                                           <bean:write name="c" property="trtd" filter="false"/>
                                        </logic:iterate>
                                      </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td><div align="right"><img src="images/more.gif" width="42" height="12" /></div></td>
                                        <td>&nbsp;</td>
                                      </tr>
                                    </table>
                                      <p>&nbsp;</p>
                                      <p>&nbsp;</p>
                                      <p>&nbsp;</p></td>
                                  </tr>
                                </table></td>
                              </tr>
                              <tr>
                                <td><img src="images/bottom-1.jpg" width="260" height="20" /></td>
                              </tr>
                            </table></td>
                            <td width="25" background="images/right-kong.jpg">&nbsp;</td>
                            <td width="310" valign="top"><table width="310" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td><img src="images/menu-right-2.jpg" width="310" height="24" /></td>
                              </tr>
                              <tr>
                                <td><img src="images/top-2.jpg" width="310" height="18" /></td>
                              </tr>
                              <tr>
                                <td valign="top"><table width="310" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td valign="top" background="images/mid-2.jpg"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="21%" valign="top"><img src="images/picture-4.gif" width="52" height="53" /></td>
                                        <td width="79%" valign="top">
                                        <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <logic:iterate id="c" name="listFour" indexId="i">
                                           <bean:write name="c" property="trtd" filter="false"/>
                                        </logic:iterate>
                                          <tr>
                                            <td background="images/dianxian.gif"><img name="" src="" width="1" height="1" alt="" style="background-color: #666666" /></td>
                                          </tr>
                                        </table>  
                                                                                
                                          
                                          <span class="STYLE3"><br />
</span></td>
                                      </tr>
                                    </table>
                                      <br />
                                      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                          <td><div align="right"><img src="images/more.gif" width="42" height="12" /></div></td>
                                        </tr>
                                      </table>
                                      </td>
                                  </tr>
                                </table></td>
                              </tr>
                              <tr>
                                <td><img src="images/bottom-2.jpg" width="310" height="20" /></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td valign="top"><table width="595" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="18" background="images/right-kong.jpg">&nbsp;</td>
                            <td width="556"><img src="images/top-3.jpg" width="577" height="64" /></td>
                          </tr>
                          <tr>
                            <td width="18" background="images/right-kong.jpg">&nbsp;</td>
                            <td width="577" valign="top"><table width="577" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td valign="top" background="images/mid-3.jpg">
                                <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="21%"><div align="center"><img src="images/picture-3.gif" width="98" height="88" /></div></td>
                                    <td width="79%" valign="top">    
<%--                                      <table width="95%" height="30" border="0" cellpadding="0" cellspacing="0">--%>
<%--                                        <logic:iterate id="c" name="list" indexId="i">--%>
<%--                                           <bean:write name="c" property="trtd" filter="false"/>--%>
<%--                                        </logic:iterate>--%>
<%--                                      </table>--%>
                                      <%
                                        out.print(GetNewsInfo.getNews());
                                      %>
                                      <table width="95%" height="10" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                          <td width="8%"><br /></td>
                                          <td width="92%"><div align="right"><img src="images/more.gif" width="42" height="12" /></div></td>
                                        </tr>
                                      </table>
                                      <div align="right"></div></td>
                                  </tr>
                                </table>                                  </td>
                              </tr>
                            </table></td>
                          </tr>
                          <tr>
                            <td width="18" background="images/right-kong.jpg">&nbsp;</td>
                            <td><img src="images/bottom-3.jpg" width="577" height="32" /></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                    <td width="30" background="images/right-kong.jpg">&nbsp;</td>
                    </tr>
                </table></td>
              </tr>
              
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="800" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/menu-di.jpg" width="850" height="32" /></td>
      </tr>
      <tr>
        <td><table width="750" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#E2EDB6">
          <tr>
            <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="5">
              <tr>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
                <td><img src="images/picture-2.gif" width="49" height="55" /></td>
              </tr>
              <tr>
                <td height="20" class="STYLE3">图片1</td>
                <td height="20" class="STYLE3">图片2</td>
                <td height="20" class="STYLE3">图片3</td>
                <td height="20" class="STYLE3">图片4</td>
                <td height="20" class="STYLE3">图片5</td>
                <td height="20" class="STYLE3">图片6</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="36" valign="top" background="images/copyright.jpg"><span class="STYLE3">：<br />
          电话：024-22511711 22521055</span> </td>
      </tr>
    </table></td>
  </tr>
</table>
  </body>
</html:html>
