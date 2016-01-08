
/**
  * modify by zhangfeng 
  * 2008-12-25
  **/
 
 // open a new page
 //win_name is the open page's name
 //loc is the open page's link
 //w is the open page's width
 //h is the open page's height
 //center(true or false) open to the middle position
function popUp(win_name, loc, w, h, menubar, center) {
	var NS = (document.layers) ? 1 : 0;
	var editorWin;
	if (w == null) {
		w = 500;
	}
	if (h == null) {
		h = 350;
	}
	if (menubar == null || menubar == false) {
		menubar = "";
	} else {
		menubar = "menubar,";
	}
	if (center == 0 || center == false) {
		center = "";
	} else {
		center = true;
	}
	if (NS) {
		w += 50;
	}
	if (center == true) {
		var sw = screen.width;
		var sh = screen.height;
		if (w > sw) {
			w = sw;
		}
		if (h > sh) {
			h = sh;
		}
		var curleft = (sw - w) / 2;
		var curtop = (sh - h - 100) / 2;
		if (curtop < 0) {
			curtop = (sh - h) / 2;
		}
		editorWin = window.open(loc, win_name, "resizable=no,scrollbars,width=" + w + ",height=" + h + ",left=" + curleft + ",top=" + curtop);
	} else {
		editorWin = window.open(loc, win_name, menubar + "resizable=no,scrollbars,width=" + w + ",height=" + h);
	}
	editorWin.focus(); //causing intermittent errors
}

//show the modal dialog
//w is the modal dialog's width
//h is the modal dialog's height
//dialogLeft is the modal dialog to the left width
//dialogTop is the modal dialog to the top height
//center is the modal dialog show the page in the window (the value is yes or no)
//resizable is the modal dialog change the big or small(the value is yes or no)
//status is the modal dialog show the state line (the value is yes or no)
//scroll is the modal dialog show the scroll line (the value is yes or no)
function popModalDialog(sUrl, w, h, dialogLeft, dialogTop, center, resizable, status, scroll) {
	var sFeatures = "";
	if (w == null) {
		sFeatures = "dialogWidth=500px;";
	} else {
		sFeatures = "dialogWidth=" + w + "px;";
	}
	if (h == null) {
		sFeatures = sFeatures + "dialogHeight=350px;";
	} else {
		sFeatures = sFeatures + "dialogHeight=" + h + "px;";
	}
	if (dialogLeft != null) {
		sFeatures = sFeatures + "dialogLeft=" + dialogLeft + "px;";
	}
	if (dialogTop != null) {
		sFeatures = sFeatures + "dialogTop=" + dialogTop + "px;";
	}
	if (center != null) {//the fall is yes
		sFeatures = sFeatures + "center=" + center;
	}
	if (resizable != null) {
		sFeatures = sFeatures + "resizable=" + resizable;
	}
	if (status != null) {
		sFeatures = sFeatures + "status=" + resizable;
	}
	if (scroll != null) {
		sFeatures = sFeatures + "scroll=" + resizable;
	}
	window.showModalDialog(sUrl,"",sFeatures);
}
//get the object by objid
function getObjById(id){
	return document.getElementById(id);
}
//format the date(the date is small)
Date.prototype.format = function(format){
var o = {
"M+" : this.getMonth()+1, //month

"d+" : this.getDate(), //day

"h+" : this.getHours(), //hour

"m+" : this.getMinutes(), //minute

"s+" : this.getSeconds(), //second

"q+" : Math.floor((this.getMonth()+3)/3), //quarter

"S" : this.getMilliseconds() //millisecond

}
if(/(y+)/.test(format))
format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
for(var k in o)
if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
return format;
}
