var Frw=140;
var Frh=50;
var Frs=1; 
var Hid=true;

document.writeln('<Div id=Clock scrolling="no" style="border:0px solid #EEEEEE ;position: absolute; width: '+Frw+'; height: '+Frh+'; z-index: 200; filter :\'progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#AAAAAA,strength='+Frs+')\' ;display: none"></Div>');

function TimeIsTrue(srcObj) {
	 var inputValue1 = srcObj.value;
	 if (inputValue1 == "") return true;
	 var lsTime = inputValue1;
	 var loTime = lsTime.split(":");
	 if (loTime.length!=2) return false;
	 var liHours = parseFloat(loTime[0]);
	 var liMinutes = parseFloat(loTime[1]);
	 if ((loTime[0].length>2)||(loTime[1].length>2)) return false;
	 if (isNaN(liHours)||isNaN(liMinutes)) return false;
	 if ((liHours<0)||(liHours>=24)) return false;
	 if ((liMinutes>=60)||(liMinutes<0)) return false;
	 return !isNaN(Date.UTC(liHours,liMinutes));
}

function TimeOnBlur(srcObj) {
	if (!TimeIsTrue(srcObj)) {
		alert(""+timeErr+"");
	 	srcObj.focus();
	 	srcObj.select();
	}
}

function textOnSelect(thisText) {
	thisText.select();
}

function InputValue1(InputBox,hours,minutes) {
    if (hours < 10) { 
    	hours = '0' + hours;
    }
    if (minutes < 10) {
    	minutes = '0' + minutes;
    }
  	InputBox.value= hours + ':' + minutes
  	HiddenClock();
}

function Nextminutes(InputBox,hours,minutes) {
    minutes = minutes-1;
    if (minutes < 0)
    {
        minutes = 59;
        hours = hours-1;
        if (hours < 0)
            hours = 23;
    }
  	Hid=false;
  	ShowClock(InputBox,hours,minutes)
}

function Forwardminutes(InputBox,hours,minutes) {
    minutes = minutes+1;
    if (minutes > 59)
    {
        minutes = 0;
        hours = hours + 1;
        if (hours > 23)
            hours = 0;
    }
  	Hid=false;
  	ShowClock(InputBox,hours,minutes)
}

function Nexthours(InputBox,hours,minutes) {
    hours = hours-1;
    if (hours < 1)
        hours = 23;
  	Hid=false;
  	ShowClock(InputBox,hours,minutes)
}

function Forwardhours(InputBox,hours,minutes) {
    hours = hours + 1;
    if (hours > 23)
        hours = 0;
  	Hid=false;
  	ShowClock(InputBox,hours,minutes)
}

function OpenTime(where) {
 	GetTimes(where);
 	clo_hideElementAll(document.all.Clock);
}

function GetTimes(where) {
    Hid=false;
    var Box_Name=where.name;
    var Box_value=where.value;
    if (Box_value != "") {
	    loDate = Box_value.split(":");
	    H = parseFloat(loDate[0]);
	    M = parseFloat(loDate[1]);
	    ShowClock(where,H,M);
    }  else  {
	    time = new Date();
	    h = time.getHours();
	    m = time.getMinutes();
	    ShowClock(where,h,m);
  }
}

function HiddenClock() {
    document.all.Clock.style.display="none";
    clo_ShowElement();
}

function CloseClock() {
	if (Hid){
    	document.all.Clock.style.display="none";
    	clo_ShowElement();
    	}
	Hid=true;
}

function ShowClock(InputBox,thisHours,thisMinutes) {
    var NowHours=(thisHours == null? 23:thisHours);
    var NowMinutes=(thisMinutes == null? 0:thisMinutes);
    var Box_Name='document.all.'+InputBox.name;
    var FrameContent;
    var Frl,Frt,Winw,Winh;
	/*显示位置*/
	Winw=document.body.offsetWidth;
	Winh=document.body.offsetHeight;
	y1=document.body.scrollTop;
	x=InputBox.getBoundingClientRect().left-2;
	y=InputBox.getBoundingClientRect().bottom+y1;
	if (((x+Frw+Frs)>Winw)&&((Frw+Frs)<Winw)) {
  		x=Winw-Frw-Frs;
  	}
	if (((y1+Winh+Frh+Frs)>2*Winh)&&((Frh+Frs)<Winh)) {
  		y=2*(Winh-Frh-Frs);
	}
	document.all.Clock.style.display="";
	document.all.Clock.style.left=x;
	document.all.Clock.style.top=y;
	//显示内容
	FrameContent="<table border='0' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>";
	FrameContent+="<tr>";
	FrameContent+="<td rowspan='2' width=31 vAlign=middle align='center'>";
	FrameContent+=NowHours;
	FrameContent+=""+hours+"";
	FrameContent+="</td>\n";
	FrameContent+="<td title="+jia+">";
	FrameContent+="<font class=updownStyle style='cursor:hand' onclick=\"Forwardhours (document.all."+InputBox.name+","+NowHours+","+NowMinutes+")\">▲</font>";
	FrameContent+="</td>\n";
	FrameContent+="<td rowspan='2' vAlign=middle align='center' width=31>";
	FrameContent+=NowMinutes;
	FrameContent+=""+minutes+"";
	FrameContent+="</td>\n";
	FrameContent+="<td title="+jia+">";
	FrameContent+="<font class=updownStyle style='cursor:hand' onclick=\"Forwardminutes (document.all."+InputBox.name+","+NowHours+","+NowMinutes+")\">▲</font>";
	FrameContent+="</td>\n";
	FrameContent+="</tr>";
	FrameContent+="<tr class=clockStyle>";
	FrameContent+="<td title="+jian+">";
	FrameContent+="<font class=updownStyle style='cursor:hand' onclick=\"Nexthours (document.all."+InputBox.name+","+NowHours+","+NowMinutes+")\">▼</font>";
	FrameContent+="</td>\n";
	FrameContent+="<td title="+jian+">";
	FrameContent+="<font class=updownStyle style='cursor:hand' onclick=\"Nextminutes (document.all."+InputBox.name+","+NowHours+","+NowMinutes+")\">▼</font>";
	FrameContent+="</td>\n";
	FrameContent+="</tr>";
	FrameContent+="</table>\n";
	FrameContent+="<table cellpadding='0' cellspacing='0' border='0' bgcolor='#FFFFFF'>";
	FrameContent+="<tr>\n";
	FrameContent+="<td width=59 align=right title="+queding+">";
	FrameContent+="<font size=2 color=#FF0000  style='cursor:hand' onclick=\"InputValue1(document.all."+InputBox.name+","+NowHours+","+NowMinutes+")\">√</font>";
	FrameContent+="</td>\n";
	FrameContent+="<td width=15>";
	FrameContent+="</td>\n";
	FrameContent+="<td title="+guanbi+">";
	FrameContent+="<font size=2 color=#FF0000 style='cursor:hand' onclick=\"HiddenClock()\">×</font>";
	FrameContent+="</td>\n";
	FrameContent+="<td width=5>";
	FrameContent+="</td>\n";
	FrameContent+="</tr>\n";
	FrameContent+="</table>";
	document.all.Clock.innerHTML=FrameContent;
	document.all.Clock.style.display="";
}
var HideElementTemp = new Array();
//点击菜单时，调用此的函数,菜单对象
function clo_hideElementAll(obj){ 
        clo_HideElement("SELECT",obj);
        clo_HideElement("iframe",obj);
}
function clo_HideElement(strElementTagName,obj){
try{
    var showDivElement = obj;
    var cloendarDiv = obj;
    var intDivLeft = clo_GetOffsetLeft(showDivElement);
    var intDivTop = clo_GetOffsetTop(showDivElement);//+showDivElement.offsetHeight;
    //HideElementTemp=new Array()
    for(i=0;i<window.document.all.tags(strElementTagName).length; i++){
 var objTemp = window.document.all.tags(strElementTagName)[i];
 if(!objTemp||!objTemp.offsetParent)
     continue;
 var intObjLeft=clo_GetOffsetLeft(objTemp);
 var intObjTop=clo_GetOffsetTop(objTemp);
 if(((intObjLeft+objTemp.clientWidth)>intDivLeft)&&
    (intObjLeft<intDivLeft+cloendarDiv.style.posWidth)&&
    (intObjTop+objTemp.clientHeight>intDivTop)&&
    (intObjTop<intDivTop+cloendarDiv.style.posHeight+showDivElement.offsetHeight)){
     //var intTempIndex=HideElementTemp.length;//已经有的长度
  //save elementTagName is stutas
     //HideElementTemp[intTempIndex]=new Array(objTemp,objTemp.style.visibility);
     HideElementTemp[HideElementTemp.length]=objTemp
     objTemp.style.visibility="hidden";
        }
    }
}catch(e){}
}

function clo_ShowElement(){
    var i;
    for(i=0;i<HideElementTemp.length; i++){
 var objTemp = HideElementTemp[i]
 if(!objTemp||!objTemp.offsetParent)
     continue;
 objTemp.style.visibility='';
    }
    HideElementTemp=new Array();
}
function clo_GetOffsetLeft(src){
    var set=0;
    if(src && src.name!="divMain"){
        if (src.offsetParent){
           set+=src.offsetLeft+clo_GetOffsetLeft(src.offsetParent);
 }
 if(src.tagName.toUpperCase()!="BODY"){
     var x=parseInt(src.scrollLeft,10);
     if(!isNaN(x))
            set-=x;
 }
    }
    return set;
}

function clo_GetOffsetTop(src){
    var set=0;
    if(src && src.name!="divMain"){
        if (src.offsetParent){
            set+=src.offsetTop+clo_GetOffsetTop(src.offsetParent);
   }
 if(src.tagName.toUpperCase()!="BODY"){
     var y=parseInt(src.scrollTop,10);
     if(!isNaN(y))
  set-=y;
 }
    }
    return set;
}
document.onclick = CloseClock;

