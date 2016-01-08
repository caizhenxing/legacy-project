//<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>
//<!--
function OpenWin(Url,name,Width,Height) {
//function OpenWin(Url,name,Width,Height,features) {
var Top = (screen.height -Height)/2;
var Left = (screen.width -Width)/2;
var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + ",resizable=0,scrollbars=1,status=0,location=0");
screenWin.focus();
}

//window.showModalDialog 
function OpenShowModalDialog(Url,name,Width,Height){
//function OpenWin(Url,name,Width,Height,features) {
var Top = (screen.height -Height)/2;
var Left = (screen.width -Width)/2;
var screenWin = window.showModalDialog(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + ",resizable=0,scrollbars=1,status=0,location=0");
screenWin.focus();
}
function checkTel(telNum)
{
	if(telNum==0)
	{
		return false;
	}

//验证电话号码手机号码，包含153，159号段
    if(telNum=="")
    {
        return false;
    }
    var phone=telNum;
    var p1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
    var me = false;
    if (p1.test(phone))me=true;
    if (!me){
        alert('对不起，您输入的电话号码有错误。区号和电话号码之间请用-分割');
        return false;
    }
    var mobile=telNum;
    var reg0 = /^13\d{5,9}$/;
    var reg1 = /^153\d{4,8}$/;
    var reg2 = /^159\d{4,8}$/;
    var reg3 = /^0\d{10,11}$/;
    var my = false;
    if (reg0.test(mobile))my=true;
    if (reg1.test(mobile))my=true;
    if (reg2.test(mobile))my=true;
    if (reg3.test(mobile))my=true;
    if (!my){
        alert('对不起，您输入的手机或小灵通号码有错误。');
        return false;
    }
    return true;
}
//?????????
function OpenScreenWin(Url,name) {
var Top =0;
var Left =0;
var Width=screen.width-10;
var Height=screen.height-50;
var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + ",menubar=0,resizable=1,scrollbars=1,status=0,location=0");
screenWin.focus();
//location=0,menubar=0,scrollbars=1,status=0,resizable=0,top="+top+",left="+left
}



function isLoginRignt(objId)
{
	var obj = document.getElementById(objId);
	if(obj)
	{
		var v = obj.value;
		if(v>0)
		{
			alert('111111');
		}
		else
		{
			alert('1212121212');
		}
	}
	else
	{
		alert('2222');
	}
}
function chksafe(a)
{ 
 return 1;
/* fibdn = new Array ("'" ,"\\", "?", ",", ";", "/");
 i=fibdn.length;
 j=a.length;
 for (ii=0;ii<i;ii++)
 { for (jj=0;jj<j;jj++)
  { temp1=a.charAt(jj);
   temp2=fibdn[ii];
   if (temp1==temp2)
   { return 0; }
  }
 }
 return 1;
*/ 
} 

function chkspc(a)
{
 var i=a.length;
 var j = 0;
 var k = 0;
 while (k<i)
 {
  if (a.charAt(k) != " ")
   j = j+1;
  k = k+1;
 }
 if (j==0)
 {
  return 0;
 }
 
 if (i!=j)
 { return 2; }
 else
 {
  return 1;
 }
}

//????chkemail
//??????????Email Address
//????????????
//????0???  1?? 
function showDate(objId,ryId)
{
	document.getElementById(ryId).innerHTML = parent.document.getElementById("yhbh").value;
	
	var obj = document.getElementById(objId);
	var d = new Date();
	obj.innerHTML = d.getFullYear()+'年'+(d.getMonth()+1)+'月'+d.getDate()+'日'+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
}
function chkemail(a)
{ var i=a.length;
 var temp = a.indexOf('@');
 var tempd = a.indexOf('.');
 if (temp > 1) {
  if ((i-temp) > 3){
   
    if ((i-tempd)>0){
     return 1;
    }
   
  }
 }
 return 0;
}//opt1 ??     opt2   ??
//?opt2?1???num?????
//?opt1?1???num?????
//??1?????0????
function chknbr(num,opt1,opt2)
{
 var i=num.length;
 var staus;
//staus????.???
 status=0;
 if ((opt2!=1) && (num.charAt(0)=='-'))
 {
  //alert("You have enter a invalid number.");
  return 0;
 
 }
//??????.???
 if (num.charAt(i-1)=='.')
 {
  //alert("You have enter a invalid number.");
  return 0;
 }

 for (j=0;j<i;j++)
 {
  if (num.charAt(j)=='.')
  {
   status++;
  }
  if (status>1) 
  {
  //alert("You have enter a invalid number.");
  return 0;  
  }
  if (num.charAt(j)<'0' || num.charAt(j)>'9' )
  {
   if (((opt1==0) || (num.charAt(j)!='.')) && (j!=0)) 
   {
    //alert("You have enter a invalid number.");
    return 0;
   }
  }
 }
 return 1;
}
//????chkdate
//????????????
//????????????
//????0?????  1????

function showAtElement(showId,showElId) 
{
		 var el = document.getElementById(showId);
		 var showEl = document.getElementById(showElId);
     var p = getAbsolutePos(el);
     this.showAt(p.x, p.y + el.offsetHeight,showEl);
}
function getAbsolutePos(el) 
{ 
		var r = { x: el.offsetLeft, y: el.offsetTop };
		if (el.offsetParent) 
     {
					var tmp = getAbsolutePos(el.offsetParent);
					r.x += tmp.x;
					r.y += tmp.y;
		}
		return r;
}
 function showAt(x, y,showEl) 
 {
 		var oDiv = showEl;
		var s = oDiv.style;
		s.left = x + "px";
		s.top = y + "px";
		s.display = "block";
}
function changeColorForTbl(tblId)
{
	var _t=document.getElementById(tblId);
	var rows = _t.rows;
	for(var ri=1; ri<rows.length; ri++)
	{
		//rows[ri].onmouseover="this.style.backgroundColor='red'";
		/*
		var cells = rows[ri].cells;
		for(var rj=0; rj<cells.length; rj++)
		{
			var bgImg = "";
			//cells[rj].onclick=function(){alert(this.background)};//#fbfffb
			cells[rj].onmouseover=function (){bgImg = this.background;this.background="";this.bgColor="#dff2dc";};
			cells[rj].onmouseout=function (){this.bgColor="#fcfef8",this.background=bgImg;};
		}
		*/
		var row = rows[ri];
		if(row.tdImg)
		{
			row.onmouseover=function()
			{
				//alert(this.tdImg);
				var cells = this.cells;
				
				for(var i=0; i<cells.length; i++)
				{
					if(i==0)
					{
						row.tdImg = cells[i].background;
					}
					cells[i].background="";cells[i].bgColor="#dff2dc";
				}	
				
			};
			row.onmouseout=function()
			{
				var cells = this.cells;
				for(var i=0; i<cells.length; i++)
				{
					//alert(11);
					//cell[i].bgColor="#dff2dc";
					//alert(this.tdImg+":"+row.tdImg);
					//cells[i].background=row.tdImg;
					cells[i].setAttribute("background",row.tdImg);
					//cells[i].setAttribute("bgColor","#dff2dc");
				}
			};
		}
	}
}
function chkdate(datestr)
{
 var lthdatestr
 if (datestr != "")
  lthdatestr= datestr.length ;
 else
  lthdatestr=0;
  
 var tmpy="";
 var tmpm="";
 var tmpd="";
 //var datestr;
 var status;
 status=0;
 if ( lthdatestr== 0)
  return 0


 for (i=0;i<lthdatestr;i++)
 { if (datestr.charAt(i)== '-')
  {
   status++;
  }
  if (status>2)
  {
   //alert("Invalid format of date!");
   return 0;
  }
  if ((status==0) && (datestr.charAt(i)!='-'))
  {
   tmpy=tmpy+datestr.charAt(i)
  }
  if ((status==1) && (datestr.charAt(i)!='-'))
  {
   tmpm=tmpm+datestr.charAt(i)
  }
  if ((status==2) && (datestr.charAt(i)!='-'))
  {
   tmpd=tmpd+datestr.charAt(i)
  }

 }
 year=new String (tmpy);
 month=new String (tmpm);
 day=new String (tmpd)
 //tempdate= new String (year+month+day);
 //alert(tempdate);
 if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2))
 {
  //alert("Invalid format of date!");
  return 0;
 }
 if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )
 {
  //alert ("Invalid month or day!");
  return 0;
 }
 if (!((year % 4)==0) && (month==2) && (day==29))
 {
  //alert ("This is not a leap year!");
  return 0;
 }
 if ((month<=7) && ((month % 2)==0) && (day>=31))
 {
  //alert ("This month is a small month!");
  return 0;
 
 }
 if ((month>=8) && ((month % 2)==1) && (day>=31))
 {
  //alert ("This month is a small month!");
  return 0;
 }
 if ((month==2) && (day==30))
 {
  //alert("The Febryary never has this day!");
  return 0;
 }
 
 return 1;
}
//????fucPWDchk
//?????????????????
//????????????
//????0??? 1????????? 


function fucPWDchk(str)
{
  var strSource ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var ch;
  var i;
  var temp;
  
  for (i=0;i<=(str.length-1);i++)
  {
  
    ch = str.charAt(i);
    temp = strSource.indexOf(ch);
    if (temp==-1) 
    {
     return 0;
    }
  }
  if (strSource.indexOf(ch)==-1)
  {
    return 0;
  }
  else
  {
    return 1;
  } 
}

function trim(str)
{     while (str.charAt(0)==" ")
          {str=str.substr(1);}      
     while (str.charAt(str.length-1)==" ")
         {str=str.substr(0,str.length-1);}
     return(str);
}

//????fucCheckNUM
//????????????
//???????????
//????1?????0?????

function fucCheckNUM(NUM)
{
 var i,j,strTemp;
 strTemp="0123456789";
 if ( NUM.length== 0)
  return 0
 for (i=0;i<NUM.length;i++)
 {
  j=strTemp.indexOf(NUM.charAt(i)); 
  if (j==-1)
  {
  //?????????
   return 0;
  }
 }
 //?????
 return 1;
}
//????fucCheckTEL
//??????????????
//????????????
//????1?????0????

function fucCheckTEL(TEL)
{
 var i,j,strTemp;
 strTemp="0123456789-()# ";
 for (i=0;i<TEL.length;i++)
 {
  j=strTemp.indexOf(TEL.charAt(i)); 
  if (j==-1)
  {
  //????????
   return 0;
  }
 }
 //????
 return 1;
}

//????fucCheckLength
//?????????????
//????????????
//???????

function fucCheckLength(strTemp)
{
 var i,sum;
 sum=0;
 for(i=0;i<strTemp.length;i++)
 {
  if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255))
   sum=sum+1;
  else
   sum=sum+2;
 }
 return sum;
}

function ObjectMouseOver(TagName,ClassName) {
	document.all(TagName).className=ClassName;
}

function ObjectMouseOut(TagName,SelectClassName,ClassName) {
	if(document.all(TagName).getAttribute("SELECT")=="1"){
		document.all(TagName).className=SelectClassName;
	}
	else{
		document.all(TagName).className=ClassName;
	}
}

function ChangeAttribValue(TagName,AttribValue,SelectClass,ClassName) {
for(var i=0;i<document.all.length;i++){
	if(document.all[i].getAttribute("SELECT")=="1"){
		document.all[i].setAttribute("SELECT","0");
		document.all[i].className=ClassName;
	}
}
document.all(TagName).setAttribute("SELECT",AttribValue);
document.all(TagName).className=SelectClass;
}

function ChangeImg(TagID,AttribValue,NewImg,OldImg) {
for(var i=0;i<document.all.length;i++){
	if (document.all[i].tagName=="IMG")
	{
		if(document.all[i].getAttribute("SELECT")==AttribValue){
			document.all[i].setAttribute("SELECT","");
			document.all[i].src=OldImg;
		}
	}
}
document.all(TagID).setAttribute("SELECT",AttribValue);
document.all(TagID).src=NewImg;
}

function CheckTopFrameUrl(urls){
	if(top.location==this.location)
	{
		top.location=urls;
	}

}

//***********??????.*********************
tPopWait=50;//??tWait????????
tPopShow=5000;//??tShow???????
showPopStep=20;
popOpacity=99;

//***************??????*****************
sPop=null;
curShow=null;
tFadeOut=null;
tFadeIn=null;
tFadeWaiting=null;

document.write("<style type='text/css'id='defaultPopStyle'>");
document.write(".cPopText {  background-color: #FFFFE7;color:#000000; border: 1px #000000 solid;font-color:; font-size: 9pt; padding-right: 4px; padding-left: 4px; height: 20px; padding-top: 2px; padding-bottom: 2px; filter: Alpha(Opacity=0)}");
document.write("</style>");
document.write("<div id='dypopLayer' style='position:absolute;z-index:1000;' class='cPopText'></div>");

function showPopupText(){
var o=event.srcElement;
	MouseX=event.x;
	MouseY=event.y;
	if(o.alt!=null && o.alt!=""){o.dypop=o.alt;o.alt=""};
    if(o.title!=null && o.title!=""){o.dypop=o.title;o.title=""};
	if(o.dypop!=sPop) {
			sPop=o.dypop;
			clearTimeout(curShow);
			clearTimeout(tFadeOut);
			clearTimeout(tFadeIn);
			clearTimeout(tFadeWaiting);	
			if(sPop==null || sPop=="") {
				dypopLayer.innerHTML="";
				dypopLayer.style.filter="Alpha()";
				dypopLayer.filters.Alpha.opacity=0;	
				}
			else {
				if(o.dyclass!=null) popStyle=o.dyclass 
					else popStyle="cPopText";
				curShow=setTimeout("showIt()",tPopWait);
			}
			
	}
}

function showIt(){
		dypopLayer.className=popStyle;
		dypopLayer.innerHTML=sPop;
		popWidth=dypopLayer.clientWidth;
		popHeight=dypopLayer.clientHeight;
		if(MouseX+12+popWidth>document.body.clientWidth) popLeftAdjust=-popWidth-24
			else popLeftAdjust=0;
		if(MouseY+12+popHeight>document.body.clientHeight) popTopAdjust=-popHeight-24
			else popTopAdjust=0;
		dypopLayer.style.left=MouseX+12+document.body.scrollLeft+popLeftAdjust;
		dypopLayer.style.top=MouseY+12+document.body.scrollTop+popTopAdjust;
		dypopLayer.style.filter="Alpha(Opacity=0)";
		fadeOut();
}

function fadeOut(){
	if(dypopLayer.filters.Alpha.opacity<popOpacity) {
		dypopLayer.filters.Alpha.opacity+=showPopStep;
		tFadeOut=setTimeout("fadeOut()",1);
		}
		else {
			dypopLayer.filters.Alpha.opacity=popOpacity;
			tFadeWaiting=setTimeout("fadeIn()",tPopShow);
			}
}

function fadeIn(){
	if(dypopLayer.filters.Alpha.opacity>0) {
		dypopLayer.filters.Alpha.opacity-=1;
		tFadeIn=setTimeout("fadeIn()",1);
		}
}
//document.onmouseover=showPopupText;

function ChoiceObj(obj,types)
{
var x=event.screenX;
var y=event.screenY;
var result=window.showModalDialog('/choice.ejf?easyJWebCommand='+types,'Choice',"dialogLeft:"+x+"px;dialogTop:"+y+"px;dialogWidth:195px;dialogHeight:400px;help:no;status:no");
//result ??????title;sn;?????
//a->td->input
var title="",sn="";
if(result!=""){
var s=result.split(";");
title=s[0];
sn=s[1]
}
obj.parentElement.children[0].value=title;
obj.parentElement.children[1].value=sn;
}
function getCalendar(obj)
{
		//alert(arguments[0]);
    if (arguments.length==0){
	  //???????onmousedown?????????,?????  modified by qiuchun
	  if (event.srcElement){
	    var pchild = event.srcElement;
	    if (pchild.type && pchild.type.toLowerCase() == "text")
	      arguments[0] = pchild.id;
	  }
	}
	var x=event.screenX;
	var y=event.screenY;
	//alert(arguments[0]);
	var result=window.showModalDialog('./include/Calendar.htm','Calendar',"dialogLeft:"+x+"px;dialogTop:"+y+"px;dialogWidth:195px;dialogHeight:200px;help:no;status:no");
	//alert(arguments[0]);
	if(result!=null)
	arguments[0].value=result;
		//eval(arguments[0]+".value=result");		
	return false;
}
function ChoiceOpUser(obj)
{
var x=event.screenX;
var y=event.screenY;
var result=window.showModalDialog('/Choice-OpUser.do','ChoiceOpuser',"dialogLeft:"+x+"px;dialogTop:"+y+"px;dialogWidth:120px;dialogHeight:230px;help:no;status:no");
//result ???????
obj.parentElement.children[0].value=result;
//obj.parentElement.children[1].value=sn;
}
function changeObjIdBackGroundSkin(objId,basePath,imgName)
{
	var skin = getCookieSkin();
	var obj = document.getElementById(objId);
	obj.background=basePath + "/images/"+skin+"/"+imgName;
}
function goCS()
{
	var is = document.getElementsByTagName("img");
	//alert(imgs+imgs.length);
	var skin = getCookieSkin();
	var reg = /skin\d\d/;
	for(var i=0; i<is.length; i++)
	{
		var s = is[i].src;
		
		//var a = src;
		s = s.replace(reg,skin);
		//alert(a+" | "+src);
		is[i].src = s;
		//images/skin01/dian05.gif
	}
	var ts = document.getElementsByTagName("td");
	for(var i=0; i<ts.length; i++)
	{
		if(ts[i].background)
		{
			var b = ts[i].background;
			//var a = b;
			b = b.replace(reg,skin);
			//alert(a+"|"+b);
			ts[i].background=b;
		}
	}
	var tbs = document.getElementById("bg_tytiao");
	if(tbs)
	{
		if(tbs.background)
		{
			var a = tbs.background;
			a = a.replace(reg,skin);
			tbs.background = a;
		}
	}
	var by = document.body;
	if(by.background)
	{
		var a = by.background;
		a = a.replace(reg,skin);
		by.background=a;
	}
}
function changeImgSrcSkin(objId,basePath,imgName)
{
	var skin = getCookieSkin();
	var obj = document.getElementById(objId);
	obj.src=basePath + "/images/"+skin+"/"+imgName;
}

function CheckElements(the)
{
        
	for(var x=0;x<the.elements.length;x++){
		if(!CheckFieldLength(the.elements[x]))
		{
			the.elements[x].focus();
			return false;
		}
	}
	return true;

}
function checkNotNull(obj,msg)
{
if(obj.value=="")
{
alert(msg+"?????");
if(obj.type!="hidden")obj.focus();
return false;
}
return true;
}
function checkLength(obj,length,msg)
{
if(!checkNotNull(obj,msg))return false;
if(obj.value.length()>length)
{
alert(msg+"????????"+length);
obj.focus();
return false;
}
return true;
}

function checkDate(obj,msg)
{
if(!checkNotNull(obj,msg))return false;
var str=obj.value.length>10?obj.value.substring(0,10):obj.value;
if (!strDateTime(str)) 
{
alert(msg+"??????????(??:2005-1-1)");
obj.focus();
return false;
}
return true;
}
window.onload = function(){goCS();}
function checkNumber(obj,msg)
{
if(!checkNotNull(obj,msg))return false;
var tempNumber = new Number(obj.value); 
if (isNaN(tempNumber)) 
{
alert(msg+"??????????");
obj.focus();
return false;
}
return true;
}
function strDateTime(str) 
{ 
var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
if(r==null)return false; 
var d= new Date(r[1], r[3]-1, r[4]); 
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]); 
} 

function setSkin(skin)
{
	var skinType = skin;
	var strCookie=document.cookie.split("; ");
	for(var i=0; i<strCookie.length; i++)
	{
		var arr = strCookie[i].split("=");
		if("lytstlskin"==arr[0])
		{
			var date = new Date();
			date.setTime(date.getTime()-10000);
			document.cookie="lytstlskin=" + arr[1] + "; expires="+date.toGMTString();
		}
	}
	var regExp = /skin\d\d/;
	if(regExp.test(skinType))
	{
		//alert("skin pattern !!");
		var date = new Date();
		date.setTime(date.getTime()+10*24*3600*1000);
		document.cookie="lytstlskin="+skinType + "; path=/; expires="+date.toGMTString();
	}
	else
	{
		//alert("skin not pattern !!");
	}
}
/******************滚动条相关begin*****************/
// 说明：用 Javascript 获取滚动条位置等信息
// 来源 ：ThickBox 2.1  
// 整理 ：CodeBit.cn ( http://www.CodeBit.cn )  
function getScroll()  
{     
	var t, l, w, h;          
	if (document.documentElement && document.documentElement.scrollTop) 
	{         
		t = document.documentElement.scrollTop;         
		l = document.documentElement.scrollLeft;         
		w = document.documentElement.scrollWidth;         
		h = document.documentElement.scrollHeight;     
	} else if (document.body) 
	{         
		t = document.body.scrollTop;         
		l = document.body.scrollLeft;         
		w = document.body.scrollWidth;         
		h = document.body.scrollHeight;     
	}    
	//alert(t+":"+l); 
	return { "t" : t, "l" : l, "w" : w, "h" : h }; 
} 
//  获取当前文件名
function getFileName()
{
    var url = this.location.href;
    var pos = url.lastIndexOf("/");
    if(pos == -1)
        pos = url.lastIndexOf("\\");
    var filename = url.substr(pos+1);
    return filename;
}

function fnLoad()
{
    with(window.document.body)
    {
        addBehavior ("#default#userData");    // 使得body元素可以支持userdate
        load("scrollState" + getFileName());    // 获取以前保存在userdate中的状态
        scrollLeft = getAttribute("scrollLeft");    // 滚动条左位置
        scrollTop = getAttribute("scrollTop");
    }
}
function fnUnload()
{
    with(window.document.body)
    {
        setAttribute("scrollLeft",scrollLeft);
        setAttribute("scrollTop",scrollTop);
        save("scrollState" + getFileName());    
        // 防止受其他文件的userdate数据影响，所以将文件名加上了
        // userdate里的数据是不能跨目录访问的
    }
}
window.onload = fnLoad;
window.onunload = fnUnload;
/******************滚动条相关end*****************/
function getCookieSkin()
{
	var strCookie=document.cookie.split("; ");
	//alert(strCookie);
	var count = "empty";
	for(var i=0; i<strCookie.length; i++)
	{
		var arr = strCookie[i].split("=");
		if("lytstlskin"==arr[0])
		{
			return arr[1];
		}
	}
	return "skin01";
}
function strLongDateTime(str) 
{ 
var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
var r = str.match(reg); 
if(r==null)return false; 
var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]); 
} 

function onkeydownToNext()
{
	if (event.keyCode==13) {event.keyCode=9;}
}

function onkeydownToSubmit(formId)
{
	var oForm = document.getElementById(formId);
	//var oEvent = EventUtil.getEvent();
	if (event.keyCode==13) {oForm.submit();}
}




