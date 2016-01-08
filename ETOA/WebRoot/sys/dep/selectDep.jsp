<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="gb2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ taglib uri="/WEB-INF/newtree.tld" prefix="newtree" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title><bean:message bundle="sys" key="sys.dep.selectEmployee"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />

	<script language="JavaScript" type="text/JavaScript">
	  function moveOption(oFromList, oToList, bAll) {
	    var nFromLen = oFromList.options.length;
	    var nToLen = oToList.options.length;
	    var i = 0;
	    while (nFromLen > 0) {
	      if (oFromList.options[i].selected || bAll) {
	        oToList[nToLen++] = new Option(oFromList.options[i].text, oFromList.options[i].value);
	        oFromList.options[i] = null;
	      } else {
	        i++;
	      }
	      nFromLen--;
	    }
	    sortOption(oToList);
	   
	  }
	  
	  
	  
<%--	后添加的做查找用的  --%>
	  function userselect()
	  {
	  	document.forms[0].action+="?method=userselect";
      	document.forms[0].submit();
	  }
	  
	  
	  
	  
	  
	  function compare(a,b) {
	    if (a.text < b.text)
	      return -1;
	    else if (a.text > b.text)
	      return 1;
	    return 0;
	  }
	  function sortOption(oList) {
	    if (oList.options.length > 1) {
	      var optionList = new Array();
	      for (var i=0; i < oList.options.length; i++) {
	        optionList.push(oList.options[i]);
	      }
	      optionList.sort(compare);
	      oList.length = 0;
	      for (var i=0; i < optionList.length; i++) {
	        oList.options[i] = optionList[i];
	      }
	    }
	  }
	  function motionstud(oFromList, bAll) {
	    var i=oFromList.selectedIndex;
	    var str=new String(oFromList.options[i].text);
	    var strv=new String(oFromList.options[i].value)
	    if(bAll==true) {
	      oFromList.options[i].text=oFromList.options[i-1].text;
	      oFromList.options[i].value=oFromList.options[i-1].value;
	      oFromList.options[i-1].text=str;
	      oFromList.options[i-1].value=strv;
	    } else {
	      oFromList.options[i].text=oFromList.options[i+1].text;
	      oFromList.options[i].value=oFromList.options[i+1].value;
	      oFromList.options[i+1].text=str;
	      oFromList.options[i+1].value=strv;
	    }
	  }
	  function changstud(oFromList, oToList)
	  {
	    var nFromLen = oFromList.options.length;
	    var nToLen = oToList.options.length;
	    for (var i=0; i < nFromLen; i++)
	    {
	      oFromList.options[i].selected=true;
	    }
	    for (var j=0; j < nToLen; j++)
	    {
	      oToList.options[j].selected=true;
	    }
	  }
	  function getvalue(oToList){
	  	var par = window.dialogArguments;
	  	var nToLen = oToList.options.length;
	  	var str = "";
	  	 var array = new Array(nToLen);
	  	 for (var j=0; j < nToLen; j++)
	     {
	       oToList.options[j].selected=true;
	       if(j==nToLen-1){
	       	str = str + oToList.options[j].value;
	       }else{
	        str = str + oToList.options[j].value + ",";
	       }
	     }
	     par.value = str;
	     window.close();
	  }
	  
	  //选择部门
	  function query() {
        document.forms[0].action = "";
        document.forms[0].submit();
      }
      function addd(userl,ll)
      {
	    var nToLen = userl.options.length;
	    var str = "";
	  	 var array = new Array(nToLen);
	  	 for (var j=0; j < nToLen; j++)
	     {
	       "userTwo".options[j].selected=true;
	       if(j==nToLen-1){
	       	str = str + userl.options[j].value;
	       }else{
	        str = str + userl.options[j].value + ",";
	       }
	     }
	     ll.value=str;
      }
      
      function addall()
      {
      	document.forms[0].action+="?method=addall";
      	document.forms[0].submit();
      }
      function add1(aa)
      {
      	
      	document.forms[0].action+="?method=add&select="+aa.value;
      	document.forms[0].submit();
      }
      function suball()
      {
      	document.forms[0].action+="?method=suball";
      	document.forms[0].submit();
      }
      function sub(aa)
      {
       
      	document.forms[0].action+="?method=sub&select="+aa.value;
      	document.forms[0].submit();
      }
	</script>

</head>

<body>

	<html:form action="/sys/dep.do" method="post">
		<input type="hidden" name="ll"/>
		
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
			<tr>
				<td colspan="4" align="center">姓名查询:<html:text property="selectName"/>&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="" onclick="userselect()" value="查询"/> <br></td>
			</tr>
			<tr>
				<td colspan="4"><bean:message bundle="sys" key="sys.dep.selectEmployee.list"/></td>
			</tr>
			
			<tr>
			
				<td class="tdbgcolorloadright">
						<select name="userFirst" multiple id="userFirst" style="HEIGHT: 176px; WIDTH: 150px"  ondblclick="add1(userFirst)">
							<logic:iterate name="userList" id="userInfo">
							<option value="<bean:write name="userInfo" property="value"/>">
								<bean:write name="userInfo" property="label"/>
							</option>
							</logic:iterate>
						</select>
				</td>
				<td class="tdbgcolorloadleft" >
					
						<p align="center">
							<input type="button" value="&lt;&lt;" onClick="javascript:suball()" />
						</p>
						<p align="center">
							<input type="button" value="&lt;-" onClick="javascript:sub(userTwo)" />
						</p>
						<p align="center">
							<input type="button" value="-&gt;" onClick="javascript:add1(userFirst)" />
						</p>
						<p align="center">
							<input type="button" value="&gt;&gt;" onClick="javascript:addall()" />
						</p>
					
				</td>
				<td class="tdbgcolorloadleft">
						<select name="userTwo" multiple id="userTwo" style="HEIGHT: 176px; WIDTH: 150px" ondblclick="sub(userTwo)">
						<logic:iterate name="userList2" id="userInfo2">
							<option value="<bean:write name="userInfo2" property="value"/>">
								<bean:write name="userInfo2" property="label"/>
							</option>
							</logic:iterate>
						</select>
				</td>
			</tr>
			<tr>
				<td colspan="4"  class="tdbgcolorloadbuttom">
						<html:button property="selectB"  onclick="getvalue(userTwo)" ><bean:message bundle="sys" key="sys.submit"/></html:button>
				</td>
			</tr>
		</table>

	</html:form>
</body>
</html:html>
