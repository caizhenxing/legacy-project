<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>审核操作</title>
<script type="text/javascript">
	
	var url = "flow.do?method=toFlowOper&type=";
	//添加
   	function add(){  
   		document.forms[0].action = url + "insert";
		document.forms[0].submit();
   	}
   	//修改
   	function update(){
   		document.forms[0].action = url + "update";
		document.forms[0].submit();
   	}
   	//删除
   	function del(){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
   	
   	function link(){
		var type_id_obj = document.forms[0].type_id;
		var type_id = type_id_obj.options[type_id_obj.selectedIndex].value;
		var entry_id = document.forms[0].entry_id.value;
		var link = "";
		if(isNaN(entry_id)){
			var entry_id_str = entry_id.substring(0,entry_id.length-11);
			entry_id_str = entry_id_str.toLowerCase();		//转成小写
			
			if(entry_id_str == "oper_caseinfo"){			//如果是案例库
				if(type_id == "oper_caseinfo_putong"){		//普通案例
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_FocusCase"){//焦点
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_HZCase"){	//会诊
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}else if(type_id == "oper_caseinfo_effectCase"){//效果
					link = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=detail&id="+entry_id;
				}
				
			}else if(entry_id_str == "oper_priceinfo"){		//价格库
				link = "../operpriceinfo.do?method=toOperPriceinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_sadinfo"){		//供求库
				link = "../sad.do?method=toSadLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_medicinfo"){		//医疗库
				link = "../medical/medicinfo.do?method=toMedicinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_book_medicinfo"){//预约医疗库
				link = "../medical/bookMedicinfo.do?method=toBookMedicinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_corpinfo"){		//企业库
				link = "../operCorpinfo.do?method=toOperCorpinfoLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_focusinfo"){		//焦点追踪库
				link = "../focusPursue.do?method=toFocusPursueLoad&type=detail&id="+entry_id;
			}else if(entry_id_str == "oper_markanainfo"){	//市场分析库
				link = "../markanainfo.do?method=toMarkanainfoLoad&type=detail&id="+entry_id;
			}

			link = "[ <a href="+link+" target=_blank>查看</a> ]";
		}else{
			link = "测试数据";
		}
		//在ID后边插上链接
		document.getElementById("link").innerHTML = link;
   	}
	
</script>
<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="link()">
<html:form action="/flow/flow" method="post">

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="flow_id"/>
    <td class="labelStyle">所属资料库</td>
    <td class="valueStyle">
      <html:select property="type_id" styleClass="selectStyle">
		<html:option value="oper_caseinfo_putong">普通案例库</html:option>
		<html:option value="oper_caseinfo_FocusCase">焦点案例库</html:option>
		<html:option value="oper_caseinfo_HZCase">会诊案例库</html:option>
		<html:option value="oper_caseinfo_effectCase">效果案例库</html:option>
		<html:option value="oper_priceinfo">农产品价格库</html:option>
		<html:option value="oper_sadinfo">农产品供求库</html:option>
		<html:option value="oper_medicinfo">医疗信息库</html:option>
		<html:option value="oper_book_medicinfo">预约医疗信息库</html:option>
		<html:option value="oper_corpinfo">企业信息库</html:option>
		<html:option value="oper_focusinfo">焦点追踪库</html:option>
		<html:option value="oper_markanainfo">市场分析库</html:option>
      </html:select>
    </td>
    <td class="labelStyle">资料库ID</td>
    <td class="valueStyle"><html:text property="entry_id" styleClass="readTextStyle"/>
    <span id="link"></span>
    </td>
  </tr>
  <tr><% String date = (String)request.getAttribute("date"); %>
    <td class="labelStyle">提交人ID</td>
    <td class="valueStyle"><html:text property="submit_id" styleClass="readTextStyle" readonly="true"/></td>
    <td class="labelStyle">提交时间</td>
    <td class="valueStyle">
    <html:text property="submit_time" onclick = "openCal('flow','submit_time',false);" styleClass="readTextStyle" value="<%= date %>"/>
    <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('flow','submit_time',false);">
    </td>
  </tr>
  <tr>
    <td class="labelStyle">受理人ID</td>
    <td class="valueStyle"><html:text property="accept_id" styleClass="readTextStyle" readonly="true"/></td>
    <td class="labelStyle">受理时间</td>
    <td class="valueStyle">
    <html:text property="handle_time" onclick = "openCal('flow','handle_time',false);" styleClass="readTextStyle" value="<%= date %>"/>
    <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('flow','handle_time',false);">
    </td>
  </tr>
  <tr>
    <td class="labelStyle">当前状态</td>
    <td colspan="3" class="valueStyle">

	    <html:select property="dict_flow_state" styleClass="selectStyle" onchange="getAccid(this.options[this.selectedIndex].value);">
		  <html:options collection="typelist" property="value" labelProperty="label"/>
		</html:select>
		<span id = accids></span>
（只有状态和备注是可以修改的）
    </td>
  </tr>
  <tr>
    <td class="labelStyle">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3" styleClass="writeTextStyle"/></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" 添 加 " onClick="add()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "  class="buttonStyle">
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" 修 改 " onClick="update()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "  class="buttonStyle">
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" 删 除 " onClick="del()"  class="buttonStyle">
&nbsp;&nbsp;&nbsp;&nbsp;	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"  class="buttonStyle">
&nbsp;&nbsp;&nbsp;    </td>
  </tr>
</table>

</html:form>
</body>
</html>
<script>

	function getAccid(v){
		sendRequest("../focusPursue/getAccid.jsp", "state="+v);
	}

	var XMLHttpReq = false;
 	//创建XMLHttpRequest对象       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//发送请求函数
	function sendRequest(url,value) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("accids").innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>
