<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
        //检查
    	function checkForm(addstaffer){
<%--    		if (!checkNotNull(addstaffer.name,"用户名")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.password,"密码")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.repassword,"重复密码")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.question,"密码问题")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.answer,"问题答案")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.groomuser,"推荐人")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.email,"电子邮件")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.val,"验证码")) return false;--%>
<%--    		if (addstaffer.password.value !=addstaffer.repassword.value)--%>
<%--            {--%>
<%--            	alert("您两次输入的密码不一致！");--%>
<%--            	addstaffer.password.focus();--%>
<%--            	return false;--%>
<%--            }--%>
    		return true;
    	}
    	//添加
    	function add(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../format.do?method=operFormat&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//修改
    	function update(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../format.do?method=operFormat&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//删除
<%--    	function delete(){--%>
<%--    		var f =document.forms[0];--%>
<%--    		if(checkForm(f)){--%>
<%--    		document.forms[0].action = "../format.do?method=operFormat&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
      	//
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
				//返回页面
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee" onunload="toback()">
    <logic:notEmpty name="operSign">
	   <script>window.close();alert("<bean:write name='operSign'/>");window.close();</script>
	</logic:notEmpty>
    <html:form action="/news/format" method="post">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload">
		    <logic:equal name="type" value="detail">
		     详细
		    </logic:equal>
		    <logic:equal name="type" value="insert">
		     样式添加
		    </logic:equal>
		    <logic:equal name="type" value="update">
		     样式修改
		    </logic:equal>
		    <logic:equal name="type" value="delete">
		     样式删除
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright" width="30%">样式描述:</td>
		    <td class="tdbgcolorqueryleft"><html:text property="styleDescribe" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">文章数目:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:text property="newsNum" styleClass="input" size="5"></html:text>
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">如果为空，则显示所有文章</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">显示类型:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="showStyle" styleClass="input">		
	        		<html:option value="1" >文章标题列表</html:option>
	        		<html:option value="2" >文章标题+部分内容</html:option>
<%--	        		<html:option value="3" >小图片+文章标题</html:option>--%>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright" width="30%">显示内容:</td>
		    <td class="tdbgcolorqueryleft">
            <p>
<%--             <input type="checkbox" name="showProperty" disabled value="true" <logic:equal name='formatInfo' property='showProperty' value="true" scope="request">checked</logic:equal> />--%>
             <html:multibox property="articleProperty" value="1"></html:multibox>  
               文章属性 
             <html:multibox property="author" value="1"></html:multibox>           
               作者&nbsp;&nbsp;&nbsp;&nbsp;
             <html:multibox property="clickTimes" value="1"></html:multibox>   
               点击次数<br>
             <html:multibox property="updatetime" value="1"></html:multibox>  
               更新时间
             <html:multibox property="isHot" value="1"></html:multibox>  
               热点标志
             <html:multibox property="showMore" value="1"></html:multibox>  
             “更多……”字样
<%--             <input type="checkbox" name="author" disabled value="true" <logic:equal name='formatInfo' property='author' value="true" scope="request">checked</logic:equal> />--%>
<%--              作 者</p>--%>
<%--             <p>      <input type="checkbox" name="clickNum" disabled value="true" <logic:equal name='formatInfo' property='clickNum' value="true" scope="request">checked</logic:equal> />--%>
<%--               点击率--%>
<%--                 <input type="checkbox" name="showDate" disabled value="true" <logic:equal name='formatInfo' property='showDate' value="true" scope="request">checked</logic:equal> />--%>
<%--               更新时间--%>
<%--               <input type="checkbox" name="showMore" disabled value="true" <logic:equal name='formatInfo' property='showMore' value="true" scope="request">checked</logic:equal> /> --%>
<%--           “更多……”字样             </p>--%>
</td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">标题最多字符数：:</td>  
		    <td class="tdbgcolorqueryleft">
		      <div align="left">   
		        <html:text property="titleCharNum" size="5" styleClass="input"></html:text>
                &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">如果为空，则显示完整标题。字母算一个字符汉字算两个字符。</font></div>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">标题样式:</td>
		    <td class="tdbgcolorqueryleft">	    	
		       <html:select property="titleCharColor" styleClass="input">
		         <html:option value="">颜色</html:option>	
                 <html:option value="#000000" style="background-color:#000000"></html:option>
                 <html:option value="#FFFFFF" style="background-color:#FFFFFF"></html:option>
                 <html:option value="#008000" style="background-color:#008000"></html:option>
                 <html:option value="#800000" style="background-color:#800000"></html:option>
                 <html:option value="#808000" style="background-color:#808000"></html:option>
                 <html:option value="#000080" style="background-color:#000080"></html:option>
                 <html:option value="#800080" style="background-color:#800080"></html:option>
                 <html:option value="#808080" style="background-color:#808080"></html:option>
                 <html:option value="#FFFF00" style="background-color:#FFFF00"></html:option>
                 <html:option value="#00FF00" style="background-color:#00FF00"></html:option>
                 <html:option value="#00FFFF" style="background-color:#00FFFF"></html:option>
                 <html:option value="#FF00FF" style="background-color:#FF00FF"></html:option>
                 <html:option value="#FF0000" style="background-color:#FF0000"></html:option>
                 <html:option value="#0000FF" style="background-color:#0000FF"></html:option>
                 <html:option value="#008080" style="background-color:#008080"></html:option>
               </html:select>
<%--                &nbsp;&nbsp;字 型：--%>
<%--                <html:select property="titleCharFont" styleClass="input">		--%>
<%--	        		<html:option value="1" >粗体</html:option>--%>
<%--	        		<html:option value="2" >斜体</html:option>--%>
<%--	        		<html:option value="3" >粗+斜</html:option>--%>
<%--	        	</html:select>--%>
                &nbsp;&nbsp;字大小：
          <html:select property="titleCharSize" styleClass="input">
             <html:option value="12px">12</html:option>
             <html:option value="14px">14</html:option>
		     <html:option value="16px">16</html:option>
		     <html:option value="18px">18</html:option>
		     <html:option value="24px">24</html:option>
		     <html:option value="36px">36</html:option>
		     <html:option value="xx-small">极小</html:option>
		     <html:option value="x-small">特小</html:option>
		     <html:option value="small">小</html:option>
		     <html:option value="medium">中</html:option>
		     <html:option value="large">大</html:option>
		     <html:option value="x-large">特大</html:option>
           </html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">文章内容字符数:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:text property="contentCharNum" styleClass="input" size="5"></html:text>
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">只有当显示类型设为“标题+内容”时才有效</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">文章属性:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:multibox property="hotArticle" value="1"></html:multibox>  
                  热门文章
                <html:multibox property="tuijianArticle" value="1"></html:multibox>  
                  推荐文章
                &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">如果都不选，将显示所有文章</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">日期范围:</td>
		    <td class="tdbgcolorqueryleft">只显示最近	    	
		    	<html:text property="dateRange" styleClass="input" size="5"></html:text>天内的文章
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">如果为空，则显示所有天数的文章</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">排序字段:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="paixuField" styleClass="input">		
	        		<html:option value="1" >文章Id</html:option>
	        		<html:option value="2" >更新时间</html:option>
	        		<html:option value="3" >点击次数</html:option>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright">排序方法:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="paixuMethod" styleClass="input">		
	        		<html:option value="desc">降序</html:option>
	        		<html:option value="asc" >升序</html:option>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="type" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="添加" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="type" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="修改" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="type" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="删除" onclick="delete()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="关闭" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	    </table>
    </html:form>
  </body>
</html:html>
