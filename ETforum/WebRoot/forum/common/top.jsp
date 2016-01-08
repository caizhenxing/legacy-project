<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ page import="et.bo.forum.useroper.register.service.impl.GetNickName" %>
<%--    <LINK href="/ETforum/forum/common/css/editor.css" rel=stylesheet   type="text/css" />--%>
<%--    <LINK href="/ETforum/forum/common/css/styles.css" rel=stylesheet   type="text/css" />--%>
<SCRIPT language="javascript">
//<![CDATA[ 
function openScript(url, width, height){
	var win = window.open(url,"openscript",'width=' + width + ',height=' + height + ',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}
function bbimg(o){
}
//]]>>
</SCRIPT>

<SCRIPT language="javascript" src="/ETforum/forum/common/css/sms.js" ></SCRIPT>

<%--<SCRIPT language="javascript" src="css/navigator.js"></SCRIPT>--%>
<SCRIPT language="javascript">
//<![CDATA[ 
var menuOffX=0	//菜单距连接文字最左端距离
var menuOffY=18	//菜单距连接文字顶端距离

var linkset=new Array() 
linkset[0]=''
linkset[0]+="<div class='menuitems'><a href='forums.php?forumid=15'>&nbsp;|-|- 33</a></div>"
var fo_shadows=new Array()

linkset[1]='<div class="menuitems"><a href="/ETforum/forum/postQuery.do?method=toBestNewPostList">查看新帖</a></div>';
linkset[1]+='<div class="menuitems"><a href="/ETforum/forum/postQuery.do?method=toSendPostRankList">发帖排行</a></div>'
<%--linkset[1]+='<div class="menuitems"><a href="userlist.php">会员列表</a></div>'--%>
<%--linkset[1]+='<div class="menuitems"><a href="plugins.php?p=tags">Tags</a></div>'--%>

linkset[2]=''
linkset[2]+='<div class="menuitems"><a href="chskin.php?skinname=test">默认主题</a></div>'

linkset[3]='<div class="menuitems"><a href="/ETforum/forum/userOper/register.do?method=toUpdate">编辑个人资料</a></div>'
<%--linkset[3]+='<div class="menuitems"><a href="messanger.php?job=receivebox">短讯</a></div>'--%>
linkset[3]+='<div class="menuitems"><a href="/ETforum/forum/postQuery.do?method=toMySavePostList">收藏夹</a></div>'
linkset[3]+='<div class="menuitems"><a href="/ETforum/forum/userInfo.do?method=toUserInfoList">联系人管理</a></div>'
<%--linkset[3]+='<div class="menuitems"><a href="usercp.php?act=active">邀请和激活</a></div>'--%>
<%--linkset[3]+='<div class="menuitems"><a href="misc.php?p=accounts">帐户绑定</a></div>'--%>


linkset[4]='<form action="search.php" method=post><div class="menuitems"> &nbsp; &nbsp; &nbsp; 快速搜索<input type=text name=keyword  size=20> <input type=hidden name="method" value="or"><input type=hidden name=forumid value="all"><input type=hidden name=method1 value="1"><input type=hidden name=method2 value="60"><input type=hidden name=searchfid value="forumid"><input type="submit" value="搜索"></form><div align=right><a href=search.php>高级搜索...</a></div></div>';
linkset[5]=''
linkset[5]+='<div class="menuitems"><a href="chskin.php?langname=big5">中文</a></div>'
linkset[5]+='<div class="menuitems"><a href="chskin.php?langname=eng">English</a></div>'
linkset[5]+='<div class="menuitems"><a href="chskin.php?langname=gb">简体中文</a></div>'
var menuleft_offset = -60;
var menutop_offset = 2;////No need to edit beyond here

var ie4=document.all;
var ns6=document.getElementById&&!document.all;
var ns4=document.layers;

function showmenu(e,vmenu,mod){
	if (!document.all&&!document.getElementById&&!document.layers)
		return;
	which=vmenu;
	clearhidemenu();
	ie_clearshadow();
<%--	alert("到了");--%>
	menuobj=ie4? document.all.popmenu:ie5? document.all.popmenu :ie6? document.all.popmenu: ns6? document.getElementById("popmenu") : ns4? document.popmenu : "";
<%--	alert("没到");--%>
	if(menuobj==null){
<%--	  alert("为空");--%>
	}
	menuobj.thestyle=(ie4||ns6)? menuobj.style : menuobj;
	
	if (ie4||ns6)
		menuobj.innerHTML=which;
	else{
		menuobj.document.write('<layer name="gui" style="background-color:#E6E6E6;width:165px" onmouseover="clearhidemenu()" onmouseout="hidemenu()">'+which+'</layer>');
		menuobj.document.close();
	}
	menuobj.contentwidth=(ie4||ns6)? menuobj.offsetWidth : menuobj.document.gui.document.style.width;
	menuobj.contentheight=(ie4||ns6)? menuobj.offsetHeight : menuobj.document.gui.document.style.height;
	
	eventX=ie4? event.clientX : ns6? e.clientX : e.x;
	eventY=ie4? event.clientY : ns6? e.clientY : e.y;
	
	var rightedge=ie4? document.documentElement.clientWidth-eventX : window.innerWidth-eventX;
	var bottomedge=ie4? document.documentElement.clientHeight-eventY : window.innerHeight-eventY;

	if (rightedge<menuobj.contentwidth)
		menuleft=ie4? document.documentElement.scrollLeft+eventX-menuobj.contentwidth+menuOffX : ns6? window.pageXOffset+eventX-menuobj.contentwidth : eventX-menuobj.contentwidth;
	else menuleft=ie4? ie_x(event.srcElement)+menuOffX : ns6? window.pageXOffset+eventX : eventX;
	

	if (bottomedge<menuobj.contentheight&&mod!=0)
		menutop=ie4? document.documentElement.scrollTop+eventY-menuobj.contentheight-event.offsetY+menuOffY-23 : ns6? window.pageYOffset+eventY-menuobj.contentheight-10 : eventY-menuobj.contentheight;
	else menutop=ie4? ie_y(event.srcElement)+menuOffY : ns6? window.pageYOffset+eventY+10 : eventY;
	
	menuleft = menuleft-menuleft_offset;
	menutop = menutop-menutop_offset;
	
	menuobj.thestyle.left = menuleft+"px";
	menuobj.thestyle.top = menutop+"px";
			
	menuobj.thestyle.visibility="visible";
	ie_dropshadow(menuobj,"#999999",3);
	return false;
}

function ie_y(e){  
	var t=e.offsetTop;  
	while(e=e.offsetParent){  
		t+=e.offsetTop;  
	}  
	return t;  
}  
function ie_x(e){  
	var l=e.offsetLeft;  
	while(e=e.offsetParent){  
		l+=e.offsetLeft;  
	}  
	return l;  
}  
function ie_dropshadow(el, color, size)
{
	var i;
	for (i=size; i>0; i--)
	{
		var rect = document.createElement('div');
		var rs = rect.style
		rs.position = 'absolute';
		rs.left = (el.style.posLeft + i) + 'px';
		rs.top = (el.style.posTop + i) + 'px';
		rs.width = el.offsetWidth + 'px';
		rs.height = el.offsetHeight + 'px';
		rs.zIndex = el.style.zIndex - i;
		rs.backgroundColor = color;
		var opacity = 1 - i / (i + 1);
		rs.filter = 'alpha(opacity=' + (100 * opacity) + ')';
		//el.insertAdjacentElement('afterEnd', rect);
		fo_shadows[fo_shadows.length] = rect;
	}
}
function ie_clearshadow()
{
	for(var i=0;i<fo_shadows.length;i++)
	{
		if (fo_shadows[i])
			fo_shadows[i].style.display="none"
	}
	fo_shadows=new Array();
}


function contains_ns6(a, b) {
	while (b.parentNode)
		if ((b = b.parentNode) == a)
			return true;
	return false;
}

function hidemenu(){
	if (window.menuobj)
		menuobj.thestyle.visibility=(ie4||ns6)? "hidden" : "hide";
	ie_clearshadow();
}

function dynamichide(e){
	if (ie4&&!menuobj.contains(e.toElement))
		hidemenu();
	else if (ns6&&e.currentTarget!= e.relatedTarget&& !contains_ns6(e.currentTarget, e.relatedTarget))
		hidemenu();
}

function delayhidemenu(){
	if (ie4||ns6||ns4)
		delayhide=setTimeout("hidemenu()",500);
}

function clearhidemenu(){
	if (window.delayhide)
		clearTimeout(delayhide)
}

function highlightmenu(e,state){
	if (document.all)
		source_el=event.srcElement;
	else if (document.getElementById)
		source_el=e.target;
	if (source_el.className=="menuitems"){
		source_el.id=(state=="on")? "mouseoverstyle" : "";
	}
	else{
		while(source_el.id!="popmenu"){
			source_el=document.getElementById? source_el.parentNode : source_el.parentElement;
			if (source_el.className=="menuitems"){
				source_el.id=(state=="on")? "mouseoverstyle" : "";
			}
		}
	}
}

//if (ie4||ns6)
//document.onclick=hidemenu
//]]>>
</SCRIPT>
<%
	String userkey = "";
	if(session.getAttribute("userkey")==null){
	}else{
		userkey = session.getAttribute("userkey").toString();
		userkey = GetNickName.getNickName(userkey);
	}
	
	request.setAttribute("usercheck",userkey);
%>
<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td><table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td width="208" height="58" ><div align="center"><img src="../../images/forum/logo.gif" width="208" height="58" /></div></td>
        <td width="758" height="58">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <table width="424" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="12" class="s_headline1"></td>
            <td width="90" class="s_headline1">            
            <logic:notEmpty name="usercheck">
		        	欢迎您光临<bean:write name="usercheck"/>
		        	<td><a href="/ETforum/forum/userOper/register.do?method=logout">注销</a>|</td>
		    </logic:notEmpty>
		    </td>
            <logic:empty name="usercheck">
            	<td><a href="/ETforum/forum/userOper/register.do?method=toLogin">登录</a>|</td>
            	<td><a href="/ETforum/forum/userOper/register.do?method=toDeclare">注册</a>|</td>
            </logic:empty>
            <td ><a href="/ETforum/forum/forumList.do?method=toForumList&moduleId=1">首页</a>|</td>
            <td ><a onmouseover=showmenu(event,linkset[3],0) onmouseout=delayhidemenu() href="/ETforum/forum/controlPanel.do?method=toMain">控制面板</a>|</td>
            <td ><a onmouseover=showmenu(event,linkset[1],0) onmouseout=delayhidemenu() href="/ETforum/forum/forumList.do?method=toForumList&moduleId=1#">论坛信息</a>|</td>
            <td ><a href="/ETforum/forum/search.do?method=toSearch"">搜索</a>|</td>
            <td ><a href="">帮助</a></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF"><table width="966" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="20">&nbsp;</td>
            <td><img src="../../images/forum/index1_03.jpg" width="953" height="114" align="left" /></td>
            <td width="30">&nbsp;</td>
          </tr>
        </table></td>
        </tr>
    </table></td>
  </tr>
</table>
<DIV class=menuskin id=popmenu 
onmouseover="clearhidemenu();highlightmenu(event,'on')" style="Z-INDEX: 100" 
onmouseout="highlightmenu(event,'off');dynamichide(event)"></DIV>

