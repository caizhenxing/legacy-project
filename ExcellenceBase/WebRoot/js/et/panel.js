// JavaScript Document
//用js写的弹出式的图层，可以插入其它界面

var prox;
var proy;
var proxc;
var proyc;
//传入的三个参数，第一个参数是要显示的图层的id，第二个参数是要显示图层的大小，第三个参数是要显示图层的高度
function show(id,x,y){/*--打开--*/
	clearInterval(prox);
	clearInterval(proy);
	clearInterval(proxc);
	clearInterval(proyc);
	var o = document.getElementById(id);
	o.style.display = "block";
	o.style.width = "10px";
	o.style.height = "10px"; 
	prox = setInterval(function(){openx(o,x,y)},10);
}

function closeed(id){/*--关闭--*/
	clearInterval(prox);
	clearInterval(proy);
	clearInterval(proxc);
	clearInterval(proyc);
	var o = document.getElementById(id);
	if(o.style.display == "block"){
		proyc = setInterval(function(){closey(o)},10);
	}
}

function openx(o,x,y){/*--打开的时候先打开x--*/
	var cx = parseInt(o.style.width);
	if(cx < x){
		o.style.width = (cx + Math.ceil((x-cx)/5)) +"px";
	}
	else{
		clearInterval(prox);
		proy = setInterval(function(){openy(o,y)},10);
	}
}

function openy(o,y){/*--然后打开y--*/
	var cy = parseInt(o.style.height);
	if(cy < y){
		o.style.height = (cy + Math.ceil((y-cy)/5)) +"px";
	}
	else{
		clearInterval(proy);
	}
}

function closey(o){/*--关闭的时候先关闭y--*/
	var cy = parseInt(o.style.height);
	if(cy > 0){
		o.style.height = (cy - Math.ceil(cy/5)) +"px";
	}
	else{
		clearInterval(proyc);
		proxc = setInterval(function(){closex(o)},10);
	}
}

function closex(o){/*--然后关闭x--*/
	var cx = parseInt(o.style.width);
	if(cx > 0){
		o.style.width = (cx - Math.ceil(cx/5)) +"px";
	}
	else{
		clearInterval(proxc);
		o.style.display = "none";
	}
}

/*-------------------------鼠标拖动---------------------*/
var od = document.getElementById("fd");
var dx,dy,mx,my,mouseD;
var odrag;
var isIE = document.all ? true : false;
document.onmousedown = function(e){
var e = e ? e : event;
if(e.button == (document.all ? 1 : 0))
{
	mouseD = true;
}
}
document.onmouseup = function(){
mouseD = false;
odrag = "";
if(isIE)
{
	od.releaseCapture();
	od.filters.alpha.opacity = 100;
}
else
{
	window.releaseEvents(od.MOUSEMOVE);
	od.style.opacity = 1;
}
}

	od.onmousedown = function(e){
	odrag = this;
	var e = e ? e : event;
	if(e.button == (document.all ? 1 : 0)){
		mx = e.clientX;
		my = e.clientY;
		od.style.left = od.offsetLeft + "px";
		od.style.top = od.offsetTop + "px";
	if(isIE){
		od.setCapture();
		od.filters.alpha.opacity = 50;
	}else{
		window.captureEvents(Event.MOUSEMOVE);
		od.style.opacity = 0.5;
	}
	} 
	}
	document.onmousemove = function(e){
	var e = e ? e : event;

if(mouseD==true && odrag)
{
	var mrx = e.clientX - mx;
	var mry = e.clientY - my;
	od.style.left = parseInt(od.style.left) +mrx + "px";
	od.style.top = parseInt(od.style.top) + mry + "px";
	mx = e.clientX;
	my = e.clientY;
}
}