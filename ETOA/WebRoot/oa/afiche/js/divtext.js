/*-------------------------------------------------------------*
 *DivText的主脚本，其中定义了在DivText中使用的全局变量和函数
 *必须在HTML文档的开始部分引入该脚本文件,代码如下:
 * <script language="JavaScript" src="mypath/DIVText/js/divtext.js">
 * Design By Firefox 2006年1月 mcw@chsegang.com.cn MSN: lcsoft@public.ytptt.sd.cn
 * HomePage: http://firefox.ourspower.com/
 *-------------------------------------------------------------*/
//被选中的HTML文本和相应的对象
var g_Range=null;

/*------------------------公用函数，供用户调用----------------------------------*/
/**********************************************************************************
 * 备注：这里提供了三个格式化界面的函数，分别用于格式化工具栏、主编辑区和底栏
 * 为了简化操作，这里采用CSS进行定义，要使用软件自带的CSS样式之外的样式，
 * 请编辑/css目录下的css文件，然后使用下面的函数将CSS样式应用于DIVText*
**********************************************************************************/
//将样式应用于主编辑去
function ftMainDiv(cssid)
{
	var obj=document.getElementById(maindiv);
	obj.className=cssid;
}
//将样式应用于主工具栏
function ftMainSpan(cssid)
{
	var obj=document.getElementById(mainspan);
	obj.className=cssid;
}

//将样式应用于底工具栏
function ftFootSpan(cssid)
{
	var obj=document.getElementById(footspan);
	obj.className=cssid;
}

//输出格式化文本,
function RichTextOut()
{
	var str="";
	if(chkES()==false){
		str=document.getElementById(maindiv).innerHTML;
	}
	else{
		str=document.getElementById(editTextid).value;
	}
	return str;
}

//输入格式化文本
function RichTextIn(str)
{
	if(chkES()==false){
		document.getElementById(maindiv).innerHTML=str;
	}
	else{
		document.getElementById(editTextid).value=str;
	}
}

/*--------------处理鼠标事件------------------------------------------*/
//鼠标在页面上单击
var docclick=function doc_onclick()
{
		//获取被单击的对象
		var obj=event.srcElement;
		//隐藏格式化的层
		hid_format_div(obj);
	
	if(chkES()==false){
		//在编辑区单击
		if(document.getElementById(maindiv).contains(obj)==true){
			var oRange=document.selection.createRange();
			g_Range=oRange;		//保存当前选中区对象
			if(oRange!=null){
				testBIU(oRange);
			}//testBIU...
		}else if(document.getElementById(mainspan).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){//在格式设置区单击
			//格式化
			switch(obj.id){
				case "bold":
					setBIU("Bold");
					viewselection();
				break;
				case "italic":
					setBIU("Italic");
					viewselection();
				break;
				case "underline":
					setBIU("Underline");
					viewselection();
				break;
				case "aleft":
					setJust("JustifyLeft");
					viewselection();
				break;
				case "center":
					setJust("JustifyCenter");
					viewselection();
				break;
				case "aright":
					setJust("JustifyRight");
					viewselection();
				break;
				case "numberlist":
					setJust("InsertOrderedList");
					viewselection();
				break;
				case "itemlist":
					setJust("InsertUnorderedList");
					viewselection();
				break;
				case "cut":
					setBIU("Cut");
					viewselection();
				break;
				case "copy":
					setBIU("Copy");
					viewselection();
				break;
				case "paste":
					setJust("Paste");
					viewselection();
				break;
				case "line":
					setJust("InsertHorizontalRule");
					viewselection();
				break;
				case "fgcolor":
					var obj=document.getElementById(fgColorid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "frcolor":
					var obj=document.getElementById(frColorid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "ftname":
					var obj=document.getElementById(ftNameid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "ftsize":
					var obj=document.getElementById(ftSizeid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "paragraph":
					var obj=document.getElementById(Paragraphid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "smile":
					var obj=document.getElementById(Smileid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
				case "picture":
					var obj=document.getElementById(Pictureid);
					obj.style.top=document.getElementById(maindiv).offsetTop+3;
					obj.style.left=event.clientX;
					obj.style.visibility="visible";
				break;
			}//end switch
		}else if(document.getElementById(fgColorid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){	//在背景颜色对话框单击
			setColor("BackColor",obj.id);
			viewselection();
		}else if(document.getElementById(frColorid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){ //在字体颜色对话框中单击
			setColor("ForeColor",obj.id);
			viewselection();
		}else if(document.getElementById(ftNameid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){	//单击字体选择框
			setFont("FontName",obj.id);
			viewselection();
		}else if(document.getElementById(ftSizeid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){	//单击字体大小选择框
			setFont("FontSize",obj.id);
			viewselection();
		}else if(document.getElementById(Paragraphid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){  //设置段落格式
			setFont("FormatBlock",obj.id);
			viewselection();
		}else if(document.getElementById(Smileid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){		//单击表情图片
			setFont("InsertImage",abspath+"img/msn/"+obj.id+".gif");
			viewselection();
		}else if(obj.id=="dt_btn" && document.getElementById("dt_text").value!=""){
			setFont("InsertImage",document.getElementById("dt_text").value);
			viewselection();
		}else if(obj.id=="dt_check"){
			showhideText(obj.checked);
		}//end if
	}else{
		if(obj.id=="dt_check"){
			showhideText(obj.checked);
		}//end if
	}
}

/*-----------处理Span按钮/颜色对话框按钮/字体字号对话框的格式--------------------------------------*/
//鼠标移动到字体对话框上的时候
//突出显示当前选择的字体
var ftnmouseover=function ftname_div_mouseover()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="LABEL" && obj.id!=""){
		obj.style.border="1px solid gray";
	}
}

//当鼠标移出所选择的字体
//恢复正常显示
var ftnmouseout=function ftname_div_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="LABEL" && obj.id!=""){
		obj.style.border="1px none gray";
	}
}

//鼠标移动到颜色对话框上的时候，
//突出显示当前的颜色块
var colormouseover=function color_div_mouseover()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.border="1px solid gray";
	}
}

//鼠标移出颜色对话框相关色块的时候，
//恢复色块的正常显示
var colormouseout=function color_div_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.border="0.4px none gray";
	}
}

//鼠标移动到Span上的时候
//将格式化按钮突出显示
var spanmouseover=function span_mouseover()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.borderTop="1px none #000000";
		obj.style.borderLeft="1px none #000000";
		obj.style.borderRight="1px solid black";
		obj.style.borderBottom="1px solid black";
	}
}

//鼠标移出Span上的元素的时候
//突出显示的按钮恢复正常
var spanmouseout=function span_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG"){
		obj.style.border="1px none #000000";
	}
}

//鼠标在Span上的元素的时候
//改变显示按钮的外观
var spanmousedown=function span_mousedown()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.borderTop="1px solid black";
		obj.style.borderLeft="1px solid black";
		obj.style.borderRight="1px none #000000";
		obj.style.borderBottom="1px none #000000";
	}
}

//鼠标在Span上释放
//改变按钮外观并调用格式化程序
var spanmouseup=function span_mouseup()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		//改变外观
		obj.style.borderTop="1px none #000000";
		obj.style.borderLeft="1px none #000000";
		obj.style.borderRight="1px solid black";
		obj.style.borderBottom="1px solid black";
	}
}

/*--------------以下是格式化代码-----------------------------------------------------------------*/
//设置粗体 斜体 下划线
function setBIU(cmdname)
{
	var oRange=document.selection.createRange();
	if(oRange!=null){
		var testContain=document.getElementById(maindiv).contains(oRange.parentElement());
		if(testContain==true){
			oRange.execCommand(cmdname);
		}
	}
}

//设置对齐位置
function setJust(cmdname)
{
	if(g_Range!=null){
		var testContain=document.getElementById(maindiv).contains(g_Range.parentElement());
		if(testContain==true){
			g_Range.execCommand(cmdname);
		}
	}
}

//设置字体，字号
function setFont(cmdname,value)
{
	if(g_Range!=null){
		var testContain=document.getElementById(maindiv).contains(g_Range.parentElement());
		if(testContain==true){
			g_Range.execCommand(cmdname,false,value);
		}
	}
}

//设置颜色
function setColor(cmdname,value)
{
	var oRange=document.selection.createRange();
	if(oRange!=null){
		var testContain=document.getElementById(maindiv).contains(oRange.parentElement());
		if(testContain==true){
			oRange.execCommand(cmdname,false,value);
		}
	}
}

//显示或者隐藏文本框
function showhideText(chk)
{
	var objDiv=document.getElementById(maindiv);
	var objText=document.getElementById(editTextid);
	var objSpan=document.getElementById(mainspan);
	
	if(chk==true){
		var str=objDiv.innerHTML;
		objDiv.style.visibility="hidden";
		objText.style.top=objDiv.offsetTop;
		objText.style.left=objDiv.offsetLeft;
		objText.style.width=objDiv.offsetWidth;
		objText.style.height=objDiv.offsetHeight;
		objText.value=str;
		objText.style.display="";
		
	}
	else if(chk==false){
		var str=objText.value;
		objDiv.style.visibility="visible";
		objDiv.innerHTML=str;
		objText.style.display="none";
	}
}

/*---------------以下是检测格式并回显----------------------------------------------------*/
//检测被选中的区域是否设置了粗体 斜体或下划线
//以及该段落是否居中、左对齐、右对齐
function testBIU(oRange)
{
	var arr_cmdname=new Array();
	var arr_imgid=new Array();
	arr_cmdname[0]="Bold";
	arr_cmdname[1]="Italic";
	arr_cmdname[2]="Underline";
	arr_cmdname[3]="JustifyLeft";
	arr_cmdname[4]="JustifyCenter";
	arr_cmdname[5]="JustifyRight";
	arr_imgid[0]="bold";
	arr_imgid[1]="italic";
	arr_imgid[2]="underline";
	arr_imgid[3]="aleft";
	arr_imgid[4]="center";
	arr_imgid[5]="aright";
	
	for(i=0;i<6;i++){
		var ret=oRange.queryCommandValue(arr_cmdname[i]);
		var obj=document.getElementById(arr_imgid[i]);
		if(obj!=null){
			if(ret==true){
				obj.style.borderTop="1px solid black";
				obj.style.borderLeft="1px solid black";
				obj.style.borderRight="1px none #000000";
				obj.style.borderBottom="1px none #000000";
			}
			else{
				obj.style.border="1px none #000000";
			}
		}
	}
}

/*--------------------------------杂项---------------------------------------------------*/
//隐藏格式化DIV
function hid_format_div(eventobj)
{
	var obj=new Array();
	obj[0]=document.getElementById(fgColorid);
	obj[1]=document.getElementById(frColorid);
	obj[2]=document.getElementById(ftNameid);
	obj[3]=document.getElementById(ftSizeid);
	obj[4]=document.getElementById(Paragraphid);
	obj[5]=document.getElementById(Smileid);
	for(i=0;i<obj.length;i++){
		obj[i].style.visibility="hidden";
	}
	//专门处理图片URL对话框
	var obj=document.getElementById(Pictureid);
	if(obj.contains(eventobj)!=true || eventobj.id=="dt_btn"){
		obj.style.visibility="hidden";
	}
}

//显示当前选中区域
function viewselection()
{
	document.getElementById(maindiv).focus();
	if(g_Range!=null){
		g_Range.select();
		testBIU(g_Range);
	}
}

//确定编辑状态
function chkES()
{
	var vstate=document.getElementById("dt_check").checked;
	return vstate;
}

/*-------------------格式化主DIV 格式化按钮SPAN  颜色对话框 字体字号对话框----------------*/
//创建主编辑区
function createMainDiv(obj)
{
	obj.contentEditable="true";		//设置为可编辑
	obj.className="notepaper";		//初始化样式表
	obj.zIndex=1;
	obj.innerHTML="<DIV></DIV>";	
}

//创建格式化SPAN栏
function createFormatSpan(obj)
{
	var i=0;
	var Imglist=new Array();
	
	//段落
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/paragraph.gif";
	Imglist[i].id="paragraph";
	Imglist[i].title="设置段落格式";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//字体下拉框
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/ftname.gif";
	Imglist[i].id="ftname";
	Imglist[i].title="选择字体";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//字体大小下拉框
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/ftsize.gif";
	Imglist[i].id="ftsize";
	Imglist[i].title="选择字号";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
		
	//分割线
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//粗体
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/bold.gif";
	Imglist[i].id="bold";
	Imglist[i].title="粗体";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//斜体
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/italic.gif";
	Imglist[i].id="italic";
	Imglist[i].title="斜体";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//下划线
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/underline.gif";
	Imglist[i].id="underline";
	Imglist[i].title="下划线";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//分割线
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//左右对齐与居中
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/aleft.gif";
	Imglist[i].id="aleft";
	Imglist[i].title="左对齐";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/center.gif";
	Imglist[i].id="center";
	Imglist[i].title="居中";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/aaright.gif";
	Imglist[i].id="aright";
	Imglist[i].title="右对齐";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//分割线
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//背景颜色与字体颜色
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/fgcolor.gif";
	Imglist[i].id="fgcolor";
	Imglist[i].title="背景颜色";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/frcolor.gif";
	Imglist[i].id="frcolor";
	Imglist[i].title="字体颜色";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//换行
	Imglist[i]=document.createElement("<hr>");
	Imglist[i].style.display ="inline"
	i++;
	
	//剪切、复制和粘贴
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/cut.gif";
	Imglist[i].id="cut";
	Imglist[i].title="剪切";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/copy.gif";
	Imglist[i].id="copy";
	Imglist[i].title="复制";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/paste.gif";
	Imglist[i].id="paste";
	Imglist[i].title="粘贴";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	//编号列表和项目符号列表
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/numberlist.gif";
	Imglist[i].id="numberlist";
	Imglist[i].title="编号列表";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/itemlist.gif";
	Imglist[i].id="itemlist";
	Imglist[i].title="项目符号列表";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//插入横线、表情符号和图片
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/line.gif";
	Imglist[i].id="line";
	Imglist[i].title="插入横线";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/smile.gif";
	Imglist[i].id="smile";
	Imglist[i].title="插入表情符号";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/picture.gif";
	Imglist[i].id="picture";
	Imglist[i].title="插入图片";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//将元素插入SPAN
	for(i=0;i<Imglist.length;i++){
		obj.insertAdjacentElement("beforeEnd",Imglist[i]);
	}
	
	//处理格式化按钮区的格式
	obj.className="spancss";
	
	//处理格式化按钮区的事件
	obj.onmouseover=spanmouseover;
	obj.onmouseout=spanmouseout;
	obj.onmousedown=spanmousedown;
	obj.onmouseup=spanmouseup;
}

//创建底栏的函数
function createFootSpan(obj)
{
	obj.className="footspancss";
	str="<input type='checkbox' id='dt_check' value='1'><font size='2' face='宋体'>显示/隐藏HTML代码</font>";
	obj.innerHTML=str;
}
//创建段落设置对话框
function createParaDiv(vid)
{
	document.write("<DIV align='center' id='"+vid+"'></DIV>");
	var oPara=document.getElementById(vid);
	oPara.style.position="absolute";
	oPara.style.border="1px solid black";
	oPara.style.top=0;oPara.style.left=0;oPara.style.width="134px";oPara.style.height="130px";
	oPara.style.zIndex=2;
	oPara.style.backgroundColor="white";
	oPara.style.visibility="hidden";
	oPara.onmouseover=ftnmouseover;
	oPara.onmouseout=ftnmouseout;
	//创建段落设置项目
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<div>'>");
	arr_span[0].innerHTML="正常";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<ADDRESS>'>");
	arr_span[1].innerHTML="<ADDRESS>地址</ADDRESS>";
	arr_span[2]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H1>'>");
	arr_span[2].innerHTML="<H1>段落1</H1>";	
	arr_span[3]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H2>'>");
	arr_span[3].innerHTML="<H2>段落2</H2>";	
	arr_span[4]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H3>'>");
	arr_span[4].innerHTML="<H3>段落3</H3>";	
	for(i=0;i<arr_span.length;i++){
		oPara.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//创建字体大小选择对话框
function createFontsizeDiv(vid)
{
	document.write("<DIV align='center' id='"+vid+"'></DIV>");
	var oftSize=document.getElementById(vid);
	oftSize.style.position="absolute";
	oftSize.style.border="1px solid black";
	oftSize.style.top=0;oftSize.style.left=0;oftSize.style.width="134px";oftSize.style.height="130px";
	oftSize.style.zIndex=2;
	oftSize.style.backgroundColor="white";
	oftSize.style.visibility="hidden";
	oftSize.onmouseover=ftnmouseover;
	oftSize.onmouseout=ftnmouseout;
	//创建字体大小选择框
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px;font-size:xx-small' id='1'>");
	arr_span[0].innerHTML="一号";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:x-small' id='2'>");
	arr_span[1].innerHTML="二号";
	arr_span[2]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:small' id='3'>");
	arr_span[2].innerHTML="三号";
	arr_span[3]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:medium' id='4'>");
	arr_span[3].innerHTML="四号";
	arr_span[4]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:large' id='5'>");
	arr_span[4].innerHTML="五号";
	arr_span[5]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:x-large' id='6'>");
	arr_span[5].innerHTML="六号";
	arr_span[5]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:xx-large' id='7'>");
	arr_span[5].innerHTML="七号";
	for(i=0;i<arr_span.length;i++){
		oftSize.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//创建字体名称选择对话框
function createFontnameDiv(vid)
{
	document.write("<DIV align='center' id='"+vid+"'></DIV>");
	var oftName=document.getElementById(vid);
	oftName.style.position="absolute";
	oftName.style.border="1px solid black";
	oftName.style.top=0;oftName.style.left=0;oftName.style.width="134px";oftName.style.height="130px";
	oftName.style.zIndex=2;
	oftName.style.backgroundColor="white";
	oftName.style.visibility="hidden";
	oftName.onmouseover=ftnmouseover;
	oftName.onmouseout=ftnmouseout;
	//构造字体SPAN
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px;font-family:宋体' id='宋体'>");
	arr_span[0].innerHTML="宋体";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:黑体' id='黑体'>");
	arr_span[1].innerHTML="黑体";
	arr_span[2]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:Arial' id='Arial'>");
	arr_span[2].innerHTML="Arial";
	arr_span[3]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:Arial Black' id='Arial Black'>");
	arr_span[3].innerHTML="Arial Black";
	arr_span[4]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:Times New Roman' id='Times New Roman'>");
	arr_span[4].innerHTML="Times New Roman";
	arr_span[5]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:Tahoma' id='Tahoma'>");
	arr_span[5].innerHTML="Tahoma";
	for(i=0;i<arr_span.length;i++){
		oftName.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//创建颜色选择对话框
function createColorDiv(vid)
{
	document.write("<DIV id='"+vid+"'></DIV>");
	var ofgColor=document.getElementById(vid);
	ofgColor.style.position="absolute";
	ofgColor.style.border="1px solid black";
	ofgColor.style.top=0; ofgColor.style.left=0; ofgColor.style.width="123px"; ofgColor.style.height="78px";
	ofgColor.style.zIndex=2;
	ofgColor.style.backgroundColor="white";
	ofgColor.style.visibility="hidden";
	ofgColor.onmouseover=colormouseover;
	ofgColor.onmouseout=colormouseout;
	//构造颜色span
	var arr_span=new Array();
	arr_span[0]=document.createElement("<IMG style='position:absolute;left:2px;top:2px;width:12px;height:12px;background-color:#000000' id='#000000' title='#000000' >");
	arr_span[1]=document.createElement("<IMG style='position:absolute;left:16px;top:2px;width:12px;height:12px;background-color:#993300' id='#993300' title='#993300'>");
	arr_span[2]=document.createElement("<IMG style='position:absolute;left:30px;top:2px;width:12px;height:12px;background-color:#333300' id='#333300' title='#333300'>");
	arr_span[3]=document.createElement("<IMG style='position:absolute;left:44px;top:2px;width:12px;height:12px;background-color:#003300' id='#003300' title='#003300'>");
	arr_span[4]=document.createElement("<IMG style='position:absolute;left:58px;top:2px;width:12px;height:12px;background-color:#003366' id='#003366' title='#003366'>");
	arr_span[5]=document.createElement("<IMG style='position:absolute;left:72px;top:2px;width:12px;height:12px;background-color:#000080' id='#000080' title='#000080'>");
	arr_span[6]=document.createElement("<IMG style='position:absolute;left:86px;top:2px;width:12px;height:12px;background-color:#333399' id='#333399' title='#333399'>");
	arr_span[7]=document.createElement("<IMG style='position:absolute;left:100px;top:2px;width:12px;height:12px;background-color:#333333' id='#333333' title='#333333'>");
	arr_span[8]=document.createElement("<br>");
	arr_span[9]=document.createElement("<IMG style='position:absolute;left:2px;top:16px;width:12px;height:12px;background-color:#800000' id='#800000' title='#800000'>");
	arr_span[10]=document.createElement("<IMG style='position:absolute;left:16px;top:16px;width:12px;height:12px;background-color:#ff6600' id='#ff6600' title='#ff6600'>");
	arr_span[11]=document.createElement("<IMG style='position:absolute;left:30px;top:16px;width:12px;height:12px;background-color:#808000' id='#808000' title='#808000'>");
	arr_span[12]=document.createElement("<IMG style='position:absolute;left:44px;top:16px;width:12px;height:12px;background-color:#008000' id='#008000' title='#008000'>");
	arr_span[13]=document.createElement("<IMG style='position:absolute;left:58px;top:16px;width:12px;height:12px;background-color:#008080' id='#008080' title='#008080'>");
	arr_span[14]=document.createElement("<IMG style='position:absolute;left:72px;top:16px;width:12px;height:12px;background-color:#0000ff' id='#0000ff' title='#0000ff'>");
	arr_span[15]=document.createElement("<IMG style='position:absolute;left:86px;top:16px;width:12px;height:12px;background-color:#666699' id='#666699' title='#666699'>");
	arr_span[16]=document.createElement("<IMG style='position:absolute;left:100px;top:16px;width:12px;height:12px;background-color:#808080' id='#808080' title='#808080'>");
	arr_span[17]=document.createElement("<br>");
	arr_span[18]=document.createElement("<IMG style='position:absolute;left:2px;top:30px;width:12px;height:12px;background-color:#ff0000' id='#ff0000' title='#ff0000'>");
	arr_span[19]=document.createElement("<IMG style='position:absolute;left:16px;top:30px;width:12px;height:12px;background-color:#ff9900' id='#ff9900' title='#ff9900'>");
	arr_span[20]=document.createElement("<IMG style='position:absolute;left:30px;top:30px;width:12px;height:12px;background-color:#99cc00' id='#99cc00' title='#99cc00'>");
	arr_span[21]=document.createElement("<IMG style='position:absolute;left:44px;top:30px;width:12px;height:12px;background-color:#339966' id='#339966' title='$339966'>");
	arr_span[22]=document.createElement("<IMG style='position:absolute;left:58px;top:30px;width:12px;height:12px;background-color:#33cccc' id='#33cccc' title='#33cccc'>");
	arr_span[23]=document.createElement("<IMG style='position:absolute;left:72px;top:30px;width:12px;height:12px;background-color:#3366ff' id='#3366ff' title='#3366ff'>");
	arr_span[24]=document.createElement("<IMG style='position:absolute;left:86px;top:30px;width:12px;height:12px;background-color:#800080' id='#800080' title='#800080'>");
	arr_span[25]=document.createElement("<IMG style='position:absolute;left:100px;top:30px;width:12px;height:12px;background-color:#999999' id='#999999' title='#999999'>");
	arr_span[26]=document.createElement("<br>");
	arr_span[27]=document.createElement("<IMG style='position:absolute;left:2px;top:44px;width:12px;height:12px;background-color:#ff00ff' id='#ff00ff' title='#ff00ff'>");
	arr_span[28]=document.createElement("<IMG style='position:absolute;left:16px;top:44px;width:12px;height:12px;background-color:#ffcc00' id='#ffcc00' title='#ffcc00'>");
	arr_span[29]=document.createElement("<IMG style='position:absolute;left:30px;top:44px;width:12px;height:12px;background-color:#ffff00' id='#ffff00' title='#ffff00'>");
	arr_span[30]=document.createElement("<IMG style='position:absolute;left:44px;top:44px;width:12px;height:12px;background-color:#00ff00' id='#00ff00' title='#00ff00'>");
	arr_span[31]=document.createElement("<IMG style='position:absolute;left:58px;top:44px;width:12px;height:12px;background-color:#00ffff' id='#00ffff' title='#00ffff'>");
	arr_span[32]=document.createElement("<IMG style='position:absolute;left:72px;top:44px;width:12px;height:12px;background-color:#00ccff' id='#00ccff' title='#00ccff'>");
	arr_span[33]=document.createElement("<IMG style='position:absolute;left:86px;top:44px;width:12px;height:12px;background-color:#993366' id='#993366' title='#993366'>");
	arr_span[34]=document.createElement("<IMG style='position:absolute;left:100px;top:44px;width:12px;height:12px;background-color:#c0c0c0' id='#c0c0c0' title='#c0c0c0'>");
	arr_span[35]=document.createElement("<br>");
	arr_span[36]=document.createElement("<IMG style='position:absolute;left:2px;top:58px;width:12px;height:12px;background-color:#ff99cc' id='#ff99cc' title='#ff99cc'>");
	arr_span[37]=document.createElement("<IMG style='position:absolute;left:16px;top:58px;width:12px;height:12px;background-color:#ffcc99' id='#ffcc99' title='#ffcc99'>");
	arr_span[38]=document.createElement("<IMG style='position:absolute;left:30px;top:58px;width:12px;height:12px;background-color:#ffff99' id='#ffff99' title='#ffff99'>");
	arr_span[39]=document.createElement("<IMG style='position:absolute;left:44px;top:58px;width:12px;height:12px;background-color:#ccffcc' id='#ccffcc' title='#ccffcc'>");
	arr_span[40]=document.createElement("<IMG style='position:absolute;left:58px;top:58px;width:12px;height:12px;background-color:#ccffff' id='#ccffff' title='#ccffff'>");
	arr_span[41]=document.createElement("<IMG style='position:absolute;left:72px;top:58px;width:12px;height:12px;background-color:#99ccff' id='#99ccff' title='#99ccff'>");
	arr_span[42]=document.createElement("<IMG style='position:absolute;left:86px;top:58px;width:12px;height:12px;background-color:#cc99ff' id='#cc99ff' title='#cc99ff'>");
	arr_span[43]=document.createElement("<IMG style='position:absolute;left:100px;top:58px;width:12px;height:12px;background-color:#ffffff' id='#ffffff' title='#ffffff'>");
	for(i=0;i<arr_span.length;i++){
		ofgColor.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//创建表情符号对话框
function createSmileDiv(vid)
{
	document.write("<DIV id='"+vid+"'></DIV>");
	var oSmile=document.getElementById(vid);
	oSmile.style.position="absolute";
	oSmile.style.border="1px solid black";
	oSmile.style.top=0; oSmile.style.left=0; oSmile.style.width="170px"; oSmile.style.height="110px";
	oSmile.style.zIndex=2;
	oSmile.style.backgroundColor="white";
	oSmile.style.visibility="hidden";
	
	//表情符号列表
	var str="";
	str=str+"<table width='27' border='1' align='center' bordercolor='#00CCFF'>";
	str=str+"<tr>";
	str=str+"<td><img src='"+abspath+"img/msn/1.gif' width='19' height='19' id='1'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/2.gif' width='19' height='19' id='2'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/3.gif' width='19' height='19' id='3'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/4.gif' width='19' height='19' id='4'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/5.gif' width='19' height='19' id='5'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/6.gif' width='19' height='19' id='6'></td>";
	str=str+"</tr>";
	str=str+"<tr>";
	str=str+"<td><img src='"+abspath+"img/msn/7.gif' width='19' height='19' id='7'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/8.gif' width='19' height='19' id='8'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/9.gif' width='19' height='19' id='9'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/10.gif' width='19' height='19' id='19'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/11.gif' width='19' height='19' id='11'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/12.gif' width='19' height='19' id='12'></td>";
	str=str+"</tr>";
	str=str+"<tr>";
	str=str+"<td><img src='"+abspath+"img/msn/13.gif' width='19' height='19' id='13'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/14.gif' width='19' height='19' id='14'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/15.gif' width='19' height='19' id='15'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/16.gif' width='19' height='19' id='16'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/17.gif' width='19' height='19' id='17'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/18.gif' width='19' height='19' id='18'></td>";
	str=str+"</tr>";
	str=str+"<tr>";
	str=str+"<td><img src='"+abspath+"img/msn/19.gif' width='19' height='19' id='19'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/20.gif' width='19' height='19' id='20'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/21.gif' width='19' height='19' id='21'></td>";
	str=str+"<td><img src='"+abspath+"img/msn/22.gif' width='19' height='19' id='22'></td>";
	str=str+"<td>&nbsp;</td>";
	str=str+"<td>&nbsp;</td>";
	str=str+"</tr>";
	str=str+"</table>";	
	oSmile.innerHTML=str;
}

//创建图片URL对话框
function createPictureDiv(vid)
{
	document.write("<DIV id='"+vid+"'></DIV>");
	var oPicture=document.getElementById(vid);
	oPicture.style.position="absolute";
	oPicture.style.border="1px solid black";
	oPicture.style.top=0; oPicture.style.left=0; oPicture.style.width="290px"; oPicture.style.height="60px";
	oPicture.style.zIndex=2;
	oPicture.style.backgroundColor="white";
	oPicture.style.visibility="hidden";
	
	//对话框
	var str="";
	str=str+"<table width='74' align='center'>";
	str=str+"<tr>";
	str=str+"<td colspan='3'><font size='2' face='宋体'>请输入要插入图片的URL：</font></td>";
	str=str+"</tr><tr>";
	str=str+"<td width='7%'>&nbsp;</td>";
	str=str+"<td width='73%'><input name='dt_text' type='text' id='dt_text' size='30'></td>";
    str=str+"<td width='20%'><input name='dt_btn' type='button' id='dt_btn' value='确定'></td>"; 
    str=str+"</tr></table>";
	oPicture.innerHTML=str;
}

//创建编辑栏
function createEditText(vid)
{
	document.write("<textarea id='"+vid+"'></textarea>");
	var obj=document.getElementById(vid);
	obj.style.position="absolute";
	obj.style.top=0;obj.style.left=0;
	obj.style.zIndex=2;
	obj.style.display="none";
}