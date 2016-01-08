<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<%--body{margin:0px; text-align:center;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');}--%>
.table_title,.talbe_conton{ border-collapse:collapse; width:98%;}
.table_title td,.talbe_conton td{font:24px "宋体";text-align:center;padding:5px;border:#0066b3 1px solid;font-weight:bold;}
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
			<td style="width: 15%;">【标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题】</td>
			<td style="width: 85%; text-align:left;"><bean:write name="inquiryForm" property="reportTopic" filter="false"/></td>
		</tr>
	
		
		<tr class="odd">
			<td>【副&nbsp;&nbsp;&nbsp;标&nbsp;&nbsp;&nbsp;题】</td>
			<td style="text-align:left;"><bean:write name="inquiryForm" property="reportTopic2" filter="false"/></td>
		</tr>
		<tr>
		  <td>【撰&nbsp;&nbsp;&nbsp;稿&nbsp;&nbsp;&nbsp;人】 </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportCopywriter" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【关&nbsp;&nbsp;&nbsp;键&nbsp;&nbsp;&nbsp;字】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportKeyword" filter="false"/></td>
	  </tr>
		<tr>
		  <td>【摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 要】 </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportAbstract" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【报&nbsp;告&nbsp;&nbsp;正&nbsp;文】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportContent" filter="false"/></td>
	  </tr>
		<tr>
		  <td>【报&nbsp;告&nbsp;&nbsp;评&nbsp;论】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportReview" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【调&nbsp;查&nbsp;&nbsp;主&nbsp;题】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="topic" filter="false"/></td>
	  </tr>
		<tr>
		  <td>【调&nbsp;查&nbsp;&nbsp;目&nbsp;的】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="aim" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【调&nbsp;查&nbsp;&nbsp;时&nbsp;间】</td>
		  <td style="text-align:left;">
			<bean:write name="inquiryForm" property="beginTime" filter="false"/>
    		——
    		<bean:write name="inquiryForm" property="endTime" filter="false"/>
		 </td>
	  </tr>
		<tr>
		  <td>【调&nbsp;查&nbsp;&nbsp;样&nbsp;本】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportSwatch" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【调查&nbsp;&nbsp;有效率】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportEfficiency" filter="false"/></td>
	  </tr>
		<tr>
		  <td >【调查&nbsp;&nbsp;组织者】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="organizers" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【调&nbsp;查&nbsp;&nbsp;人&nbsp;员】 </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="actors" filter="false"/></td>
	  </tr>
		<tr >
		  <td >【调查发起机构】</td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="organiztion" filter="false"/></td>
	  </tr>
		<tr class="odd">
		  <td >【备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注】 </td>
		  <td style="text-align:left;"><bean:write name="inquiryForm" property="reportRemark" filter="false"/></td>
	  </tr>
	</table>

	</marquee>
</html:form>
</body>
</html>