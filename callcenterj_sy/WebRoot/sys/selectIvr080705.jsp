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
    
    <title>IVR�ļ�ѡ��2</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//��ͨ��ivr
		function goParent(value)
		{
			//txtChangeToIvr btnChangeToIvr
			opener.document.getElementById('txtChangeToIvr').value=value;
			opener.document.getElementById('btnChangeToIvr').click();
			window.close();
		}
		//����������ҵ��
		function goMultiIVR()
		{
			//txtS_ivrtype txtOperationType
			var operationV = getRadioValue('operationType');
			var s_ivrtypeV = document.getElementById('s_ivrtype').value;
			if(s_ivrtypeV == 0)
			{
				alert("��ѡ��ҵ������!");
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
                    <td class="wenzi1">����������ҵ�񣨵㲥�����ơ��˶�ҵ�񣩣�<input type="button" value="ȷ��" onclick="goMultiIVR()" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="31" align="left" class="mt5"><table width="0" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="wenzi1">�Ƿ����һ������һ��</td>
                    <td><input name="operationType" type="radio" class="dx" value="��ϯתIVR����������ddt.vds" /></td>
                    <td class="mt8">��</td>
                    <td><input name="operationType" type="radio" class="dx" value="��ϯתIVR����������.vds" checked="true" /></td>
                    <td class="mt8">��</td>
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
		  out.println("<a href=\"javascript:goParent('start.vds')\">����</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('ũ��Ʒ�г�.vds')\">ũ��Ʒ�г�</a>");
	  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('ҽ�ƽ���.vds')\">ҽ�ƽ���</a>");
	  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('��ֳҵ����.vds')\">��ֳҵ����</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('��ֲҵ����.vds')\">��ֲҵ����</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('�۸�����.vds')\">�۸�����</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('������Ϣ.vds')\">������Ϣ</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('ר��.vds')\">ר��</a>");
		  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('ConfRoom.vds')\">�������</a>");
			  out.print("</td></tr>");
		 out.println("</table></td>");
		out.println("</tr>");
		
			out.println("<tr>");
		 out.println("<td class=\"mt2\"><table width=\"0\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		  out.print("<tr><td class=\"wenzi1\">");
		out.println("<a href=\"javascript:goParent('record.vds')\">������������</a>");
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
