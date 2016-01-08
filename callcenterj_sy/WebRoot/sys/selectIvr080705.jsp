<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="./../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cssinsession = styleLocation;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>IVR文件选择2</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//普通的ivr
		function goParent(value)
		{
			//txtChangeToIvr btnChangeToIvr
			opener.document.getElementById('txtChangeToIvr').value=value;
			opener.document.getElementById('btnChangeToIvr').click();
			window.close();
		}
		//多语音播报业务
		function goMultiIVR()
		{
			//txtS_ivrtype txtOperationType
			var operationV = getRadioValue('operationType');
			var s_ivrtypeV = document.getElementById('s_ivrtype').value;
			if(s_ivrtypeV == 0)
			{
				alert("请选择业务类型!");
				return;
			}
			
			opener.document.getElementById('txtS_ivrtype').value=s_ivrtypeV;
			opener.document.getElementById('txtOperationType').value=operationV;
			opener.document.getElementById('btnMultiIVR').click();
			window.close();
		}
		function getRadioValue(radioName)
		{
			 var obj=document.getElementsByName(radioName);
			 for(var i=0;i<obj.length;i++)
			 {
				  if(obj[i].checked)
				  {
				   	return obj[i].value;
				  }
			 }
		}
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath %>style/<%=cssinsession %>/ivrFileSelect/css/css.css" rel="stylesheet" type="text/css" />
	<link href="./../style/<%=cssinsession %>/ivrFileSelect/css/css.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	<!--
	body {
		margin-left: 1px;
		margin-top: 1px;
		margin-right: 1px;
		margin-bottom: 1px;
	}
	-->
	</style>
	
	<style type="text/css">
	<!--
	a:link {
		color: #FFFFFF;
		text-decoration: none;
	}
	a:visited {
		color: #FFFFFF;
		text-decoration: none;
	}
	a:hover {
		color: #FFFFFF;
		text-decoration: none;
	}
	a:active {
		color: #3399CC;
		text-decoration: none;
	}
	-->
	</style>
  </head>
  
  <body>
   <html:form action="sys/playVoice.do" method="post">
    <table width="118" height="98" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><table width="572" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="572" height="93" class="mt1">&nbsp;</td>
      </tr>
      <tr>
        <td><table width="572" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="215" class="mt3">&nbsp;</td>
            <td width="10" rowspan="19" class="mt4">&nbsp;</td>
            <td width="347" rowspan="19" align="center"><table width="348" height="468" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="mt5">&nbsp;</td>
              </tr>
              <tr>
                <td height="13" align="left" class="mt6"><table width="0" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="wenzi1">多语音播报业务（点播、订制、退定业务）：<input type="button" value="确定" onclick="goMultiIVR()" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="31" align="left" class="mt5"><table width="0" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="wenzi1">是否带上一条、下一条</td>
                    <td><input name="operationType" type="radio" class="dx" value="座席转IVR多语音播报ddt.vds" /></td>
                    <td class="mt8">是</td>
                    <td><input name="operationType" type="radio" class="dx" value="座席转IVR多语音播报.vds" checked="true" /></td>
                    <td class="mt8">否</td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="377" align="center" class="mt7"><table width="319" height="97" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="left">
					<html:select size="25" styleClass="xl" property="s_ivrtype">
						<html:optionsCollection
							name="lvList"
							label="value"
							value="label"
						/>
					</html:select>		
				</td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td class="mt5">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
<% 
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		  out.println("<a href=\"javascript:goParent('start.vds')\">导语</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('农产品市场.vds')\">农产品市场</a>");
	  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('医疗健康.vds')\">医疗健康</a>");
	  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('养殖业生产.vds')\">养殖业生产</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('种植业生产.vds')\">种植业生产</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('价格行情.vds')\">价格行情</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('供求信息.vds')\">供求信息</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('专题.vds')\">专题</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('ConfRoom.vds')\">加入会议</a>");
			  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('record.vds')\">语音留言信箱</a>");
			  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
%>
          <tr>
            <td class="mt2">&nbsp;</td>
          </tr>
          <tr>
            <td class="mt2">&nbsp;</td>
          </tr>
          <tr>
            <td class="mt2">&nbsp;</td>
          </tr>
          <tr>
            <td class="mt3">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td class="mt9">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<iframe name="yuyinFrame" src="./sys/playVoice/voiceChange.jsp" width="572" height="500" />
  </body>
</html>
