<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css">
<%--body{margin:0px; text-align:center;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');}--%>
.table_title,.talbe_conton{ border-collapse:collapse; width:98%;}
.table_title td,.talbe_conton td{font:24px "����";text-align:center;padding:5px;border:#0066b3 1px solid;font-weight:bold;}
.table_title td{ FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#0066b3', endColorStr='#37d3fc', gradientType='0'); color:#FFFFFF;}
.talbe_conton{background-color:#FFFFFF;}
.odd{background-color:#eafaff;}
</style>
</head>

<%--<body style="text-align:center; background:url(images/i_bj.gif) top left repeat-x;">--%>
<body>
<html:form action="/inquiry" method="post">
<marquee direction="up" width="100%" height="530" SCROLLAMOUNT="4" >

	<table class="talbe_conton" align="center">
		
		<tr class="odd">
			<td style="width: 15%;">����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�⡿</td>
			<td style="width: 85%; text-align:left;"><bean:write name="inquiryForm" property="reportTopic" filter="false"/></td>
		</tr>
	
		
		<tr class="odd">
			<td>����&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;�⡿</td>
			<td style="text-align:left;"><bean:write name="inquiryForm" property="reportTopic2" filter="false"/></td>
		</tr>
		<tr>
		  <td>��׫&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;�ˡ� </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportCopywriter" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;�֡�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportKeyword" filter="false"/></td>
	  </tr>
		<tr>
		  <td>��ժ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Ҫ�� </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportAbstract" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;��&nbsp;&nbsp;��&nbsp;�ġ�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportContent" filter="false"/></td>
	  </tr>
		<tr>
		  <td>����&nbsp;��&nbsp;&nbsp;��&nbsp;�ۡ�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportReview" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;��&nbsp;&nbsp;��&nbsp;�⡿</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="topic" filter="false"/></td>
	  </tr>
		<tr>
		  <td>����&nbsp;��&nbsp;&nbsp;Ŀ&nbsp;�ġ�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="aim" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;��&nbsp;&nbsp;ʱ&nbsp;�䡿</td>
		  <td style="text-align:left;">
			<bean:write name="inquiryForm" property="beginTime" filter="false"/>
    		����
    		<bean:write name="inquiryForm" property="endTime" filter="false"/>
		 </td>
	  </tr>
		<tr>
		  <td>����&nbsp;��&nbsp;&nbsp;��&nbsp;����</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportSwatch" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >������&nbsp;&nbsp;��Ч�ʡ�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportEfficiency" filter="false"/></td>
	  </tr>
		<tr>
		  <td >������&nbsp;&nbsp;��֯�ߡ�</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="organizers" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;��&nbsp;&nbsp;��&nbsp;Ա�� </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="actors" filter="false"/></td>
	  </tr>
		<tr >
		  <td >�����鷢�������</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="organiztion" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע�� </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportRemark" filter="false"/></td>
	  </tr>
	</table>

	</marquee>
</html:form>
</body>
</html>