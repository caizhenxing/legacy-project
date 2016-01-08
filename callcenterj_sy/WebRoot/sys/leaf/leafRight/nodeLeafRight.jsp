<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'nodeLeafRight.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="./images/css/styleA.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		td .rightImg{
			cursor:hand;
		}
	</style>
	<script language="javascript" src="./js/tools.js"></script>
  	<script language="javascript">
  		var clickNum=0;
  		function changeImgAll()
  		{
  		    v2tree();
  			var imgs = document.getElementsByClassName('rightImg');
  	
  			for(var i=0; i<imgs.length; i++)
  			{
  				var img = imgs[i];
  				var imgL = getLast(img.src);
  				var imgF = getFirst(img.src);
  			
  				if(clickNum%2==0)
  				{
  					img.src=imgF+"/"+'leafNoRight.gif';
  				}
  				else
  				{
  					img.src=imgF+"/"+'leafHasRight.gif';
  				}
  			}
  			clickNum++;
  		}
  		function v2tree()
  		{
  			var tree = document.getElementById('treeId').value;
  			document.getElementById('gTreeId').value=tree;
  		}
  		
  		function changeImg(img)
  		{
  			v2tree();
  			var imgL = getLast(img.src);
  			var imgF = getFirst(img.src);
  			if(imgL=='leafNoRight.gif')
  			{
  				img.src=imgF+'/'+'leafHasRight.gif';
  			}
  			else
  			{
  				img.src=imgF+'/'+'leafNoRight.gif';
  			}
  		}
  		
  		function getLast(str)
  		{
  			var index = str.lastIndexOf("/")
  			if(index != -1)
  			{
  				return str.substring(index+1);
  			}
  			return str;
  		}
  		function getFirst(str)
  		{
  			var index = str.lastIndexOf("/")
  			if(index != -1)
  			{
  				return str.substring(0,index);
  			}
  			return str;
  		}
  		
  		function v2LeafRightIds()
  		{
  			var imgs = document.getElementsByClassName('rightImg');
  			var rs = '';
  			for(var i=0; i<imgs.length; i++)
  			{
  				var img = imgs[i];
  				var imgL = getLast(img.src);
  				if(imgL=='leafHasRight.gif')
  				{
  					if(i==0)
  					{
  						rs = img.id;
  					}
  					else
  					{
  						rs = rs + "|"+img.id;
  					}
  				}
  			}
  			if(document.getElementById('gTreeId').value!=0)
  			{
  				document.getElementById('leafRightIds').value=rs;
  				document.getElementById('grantForm').submit();
  			}
  			else
  			{
  				alert('没有选泽任何授权节点 禁止提交');
  			}
  		}
  		//function none(){};
  	</script>
  </head>
  
  <body><br>
    <html:form action="/sys/leafRight/leafRight.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
  			String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
  		  <%
  		 	if(i==0)
  		 	{
  		  %>
  		  <tr class="tdbgpiclist">
		    <td>节点类型<br></td>
		    <td>别名<br></td>
		    <td>是否删除<br></td>
		    <td>备注<br></td>
		    <td>操作<br></td>
		  </tr>
		  <tr class="tdbgcolorlist2">
		    <td colspan="5"><input type="hidden" id="treeId" value="<bean:write name="c" property="treeId" filter="true"/>" /><a href="javascript:none;" onclick="changeImgAll()"><bean:write name="c" property="treeName" filter="true"/></a><br></td>
		  </tr>
  		  <% 
  		  	}
  		  %>
		  <tr>
		    <td ><bean:write name="c" property="type" filter="true"/><br></td>
		    <td ><bean:write name="c" property="nickName" filter="true"/><br></td>
		    <td ><logic:equal name="c" property="deleteMark" value="0">是</logic:equal><logic:notEqual name="c" property="delMark" value="0">否</logic:notEqual><br></td>
		    <td ><bean:write name="c" property="remark" filter="true"/></td>
		    <td >
		    <img class="rightImg" alt="授权" id="<bean:write name="c" property="id" filter="true"/>" src="./images/leafNoRight.gif" onclick="changeImg(this)" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		</table>
    </html:form>
   
    <html:form action="/sys/leafRight/leafRight.do?method=grantLeafRights" method="post" styleId="grantForm">
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1">
    	<tr>
    	<td align="right">
    	<input type="hidden" id="gTreeId" name="treeId" />
    	<input type="hidden" id="gLeafRightIds" name="leafRightIds" />
		<input type="button" onclick="v2LeafRightIds()" value="授权" />
		</td>
		</tr>
		</table>
    </html:form>
  </body>
</html>
