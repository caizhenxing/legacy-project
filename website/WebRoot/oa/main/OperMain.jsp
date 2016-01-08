<%@ page language="java" pageEncoding="gb2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>
      首页
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2">
      <tr>
        <td width="60%" valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif"/></td>
                          <td width="565" height="20" align="left" class="td2">
                            新闻
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../mainOper.do?method=toMoreNews">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top" class="tdbgpicmain">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <logic:iterate id="c" name="newsList">
                        <tr>
                          <td align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
                          <td class="tdbgpicmain">
                            <a href="../../news/opernews.do?method=toNewsInfo&id=<bean:write name='c' property='id' filter='true'/>"><bean:write name="c" property="title" filter="true"/></a>
                          </td>
                          <td align="center" class="tdbgpicmain">
                            <bean:write name="c" property="author" filter="true"/>
                          </td>
                          <td align="center" class="tdbgpicmain">
                            <bean:write name="c" property="newsTime" filter="true"/>
                          </td>
                        </tr>
                      </logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="84%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="16%" class="tdbgcolorloadbuttom">
                            <a href="../mainOper.do?method=toMoreNews">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td width="40%" valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            公告
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../mainOper.do?method=toMoreAfiche">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top" class="tdbgpicmain">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <logic:iterate id="c" name="afficheList">
                        <tr>
                          <td width="8%" align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>papa.gif" width="15" height="13"></td>
                          <td width="92%" class="tdbgpicmain">
                            <a href="../operaffiche.do?method=toAficheInfo&id=<bean:write name='c' property='id' filter='true'/>"><bean:write name="c" property="title" filter="true"/></a>
                          </td>
                        </tr>
                      </logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="82%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="18%" class="tdbgcolorloadbuttom">
                            <a href="../mainOper.do?method=toMoreAfiche">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            代办事宜
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../../flow.do?method=list">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<logic:iterate id="c" name="workList">
                        <tr>
                          <td align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
                          <td class="tdbgpicmain">
                            <html:link action="/flow.do?method=load" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',480,400)" target="windows" >
                            <bean:write name="c" property="name"/>
                            </html:link>
                          </td>
                          <td align="center" class="tdbgpicmain">
                          </td>
                          <td align="center" class="tdbgpicmain">
                             <bean:write name="c" property="time"/>
                          </td>
                        </tr>
                        </logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="84%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="16%" class="tdbgcolorloadbuttom">
                            <a href="../../flow.do?method=list">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            邮件
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../mainOper.do?method=toMoreEmail">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <logic:iterate id="c" name="emailList">
                        <tr>
                          <td width="8%" align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>papa.gif" width="15" height="13"></td>
                          <td width="92%" class="tdbgpicmain">
                            <a href="../mainOper.do?method=toEmailInfo&id=<bean:write name='c' property='id' filter='true'/>">
                            <bean:write name="c" property="emailTitle"/>
                            </a>
                          </td>
                        </tr>
                        </logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="82%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="18%" class="tdbgcolorloadbuttom">
                            <a href="../mainOper.do?method=toMoreEmail" class="tdbgcolorloadbuttom">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            工作计划
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../mainOper.do?method=toMorePlan">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <logic:iterate id="c" name="planList">
                        <tr>
                          <td  align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
                          <td  class="tdbgpicmain">
                            <html:link action="/workmission.do?method=changemission&type=mi" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',880,300)" target="windows">
                            <bean:write name="c" property="name"/>
							</html:link>
                          </td>
                          <td  align="center" class="tdbgpicmain">
                            (<bean:write name="c" property="beginTime"/>--<bean:write name="c" property="endTime"/>)
                          </td>
                        </tr>
						</logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="82%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="18%" class="tdbgcolorloadbuttom">
                            <a href="../mainOper.do?method=toMorePlan" class="tdbgcolorloadbuttom">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            外出状态
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" valign="middle">
                          <a href="../mainOper.do?method=getAllOutStateList">
                          <img src="<bean:write name='imagesinsession'/>most.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <logic:iterate id="c" name="outStateList">
                        <tr>
                          <td align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
                          <td class="tdbgpicmain">
                            <bean:write name="c" property="name" filter="true"/>
                          </td>
                          <td align="center" class="tdbgpicmain">
                            <bean:write name="c" property="startDate" filter="true"/>
                          </td>
                          <td align="center" class="tdbgpicmain">
                            <logic:equal name="c" property="absenceType" value="1">请假</logic:equal>
                            <logic:equal name="c" property="absenceType" value="2">外出</logic:equal>
                            <logic:equal name="c" property="absenceType" value="3">出差</logic:equal>
                          </td>
                        </tr>
						</logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="82%" align="right" class="tdbgcolorloadbuttom">&nbsp;
                            
                          </td>
                          <td width="18%" class="tdbgcolorloadbuttom">
                            <a href="../mainOper.do?method=getAllOutStateList" class="tdbgcolorloadbuttom">更多…</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        <td>
      </tr>
    </table>
  </body>
</html:html>