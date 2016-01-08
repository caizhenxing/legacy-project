
function isBlank(str) {
	var blankFlag = true;
	if (str.length == 0) return true;
	for (var i = 0; i < str.length; i++) {
		if ((str.charAt(i) != "") && (str.charAt(i) != " ")) {
			blankFlag = false;
			break;
		}
	}
	return blankFlag;
}

function checkNotNull(theField, fieldName) {
	
	if(isBlank(theField.value)){
		msg("error",1,250,"不可为空！","");
		//alert(fieldName + "不可为空！");
		theField.focus();
		return false;
	}

	return true;
}

function $(str){
	return document.getElementById(str);
}

function _(str){
	return document.getElementsByTagName(str);
}

function msg(boxtitle,boxtype,boxwidth,msg,url){
	$("msg_div_main").style.width = boxwidth;
	$("msg_div_main").style.left = (_("body")[0].clientWidth - boxwidth) / 2;
	$("msg_div_main").style.top  = (_("body")[0].clientHeight - 220) / 2;
	var msg_div_main_but_tmp = "<br /><br />"
				+ "<button class='msg_div_main_but' id='msg_div_main_but' "
				+ "onclick=\"msg_close_tmp_biyuan();" + url + "\">确 定</button>";
	switch(boxtype * 1){
		case 1:
			$("msg_div_main_content").innerHTML = msg + msg_div_main_but_tmp;
			//$("msg_div_main_but").focus();
		break;
		case 2:
			$("msg_div_main_content").innerHTML =  msg + msg_div_main_but_tmp
							 + "&nbsp;&nbsp;<button class='msg_div_main_but' "
							 + "onclick='msg_close_tmp_biyuan();'>取 消</button>";
			//$("msg_div_main_but").focus();
		break;
		case 3:
			$("msg_div_main_content").innerHTML =  msg;
		break;
		defualt:
			$("msg_div_main_content").innerHTML =  msg;
		break;
	}
	$("msg_div_main_title").innerHTML =  boxtitle;
	$("msg_div_all").style.zIndex  = 100;
	$("msg_div_main").style.zIndex = 200;
	$("msg_div_all").style.display = "";
	$("msg_div_main").style.display = "";
	$("msg_div_all").oncontextmenu = function(){
		return false;
	}
	$("msg_div_main").oncontextmenu = function(){
		return false;
	}
}
function msg_close_tmp_biyuan(){
	$('msg_div_all').style.display='none';
	$('msg_div_main').style.display='none';
}

//加入对话框移动代码
var msg_md = false,msg_mobj,msg_ox,msg_oy;
document.onmousedown = function(){
	if(typeof(event.srcElement.msg_canmove) == "undefined"){
		return;
	}
	if(event.srcElement.msg_canmove){
		msg_md = true;
		msg_mobj = $(event.srcElement.msg_forid);
		msg_ox = msg_mobj.offsetLeft - event.x;
		msg_oy = msg_mobj.offsetTop - event.y;
	}
}
document.onmouseup = function(){
	msg_md = false;
}
document.onmousemove = function(){
	if(msg_md){
		msg_mobj.style.left = event.x + msg_ox;
		msg_mobj.style.top  = event.y + msg_oy;
	}
}

document.writeln("<style type='text/css'>"
	+ "#msg_div_all {width:100%;height:100%;position:absolute;filter:Alpha(opacity=70);background:#EFEFEF;}"
	+ "#msg_div_main {position:absolute;}"
	+ "#msg_div_main_title {font-size:12px;color:#2C71AF;font-family:verdana;cursor:default;}"
	+ "#msg_div_main_content {font-size:14px;color:#2C71AF;padding-left:8px;}"
	+ ".msg_div_main_but {background:url(img/buttonbg.gif);width:65px;heigt:20px;border:none;padding-top:3px;font-size:12px;}"
	+ "</style>"
	+ "<div id='msg_div_all' style='display:none;'></div>"
	+ "<div id='msg_div_main' style='display:none;'>"
	+ "<table width='100%' height='29' border='0' cellspacing='0' cellpadding='0'>"
	+ "<tr>"
	+ "<td width='25'><img src='img/bg_01.gif' width='25' height='29' alt='' /></td><td background='img/bg_02.gif' width='3'></td>"
	+ "<td background='img/bg_02.gif' msg_canmove='true' msg_forid='msg_div_main' id='msg_div_main_title'></td>"
	+ "<td background='img/bg_02.gif' align='right' style='padding-top:4px'>"
	+ "<img src='img/bg_05.gif' width='21' height='21' alt='关闭' "
	+ "onMouseover=\"this.src='img/bg_13.gif'\" "
	+ "onMouseout=\"this.src='img/bg_05.gif'\" onMouseup='msg_close_tmp_biyuan();' "
	+ "onMousedown=\"this.src='img/bg_18.gif'\"></td>"
	+ "<td width='6'><img src='img/bg_06.gif' width='6' height='29' alt='' /></td>"
	+ "</tr>"
	+ "</table>"
	+ "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
	+ "<tr>"
	+ "<td width='3' background='img/bg_07.gif'></td>"
	+ "<td bgcolor='#F7F7F7' align='center'><br /><span id='msg_div_main_content'></span><br /><br /></td>"
	+ "<td width='3' background='img/bg_08.gif'></td>"
	+ "</tr>"
	+ "<tr>"
	+ "<td width='3' height='3'><img src='img/bg_09.gif' width='3' height='3' alt='' /></td>"
	+ "<td background='img/bg_11.gif'></td>"
	+ "<td width='3' height='3'><img src='img/bg_10.gif' width='3' height='3' /></td>"
	+ "</tr>"
	+ "</table>"
	+ "</div>");