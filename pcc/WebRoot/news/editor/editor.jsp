<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312"%>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<title>HTML���߱༭��</title>
<link rel="STYLESHEET" type="text/css" href="../css/edit.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0">
</head>

<body bgcolor="#F0F0F0" STYLE="margin:0pt;padding:0pt" onload="load()">
<div class="yToolbar"> 
  <div class="TBHandle"> </div>
  <div class="Btn" TITLE="ȫ��ѡ��" LANGUAGE="javascript" onclick="format('selectall')"> 
    <img class="Ico" src="../css/img4edit/selectall.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="ɾ��" LANGUAGE="javascript" onclick="format('delete')"> 
    <img class="Ico" src="../css/img4edit/del.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="����" LANGUAGE="javascript" onclick="format('cut')"> <img class="Ico" src="../css/img4edit/cut.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="Btn" TITLE="����" LANGUAGE="javascript" onclick="format('copy')"> 
    <img class="Ico" src="../css/img4edit/copy.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="ճ��" LANGUAGE="javascript" onclick="format('paste')"> 
    <img class="Ico" src="../css/img4edit/paste.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="����" LANGUAGE="javascript" onclick="format('undo')"> 
    <img class="Ico" src="../css/img4edit/undo.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="�ָ�" LANGUAGE="javascript" onclick="format('redo')"> 
    <img class="Ico" src="../css/img4edit/redo.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="������" LANGUAGE="javascript" onclick="InsertTable()"> 
    <img class="Ico" src="../css/img4edit/table.gif" WIDTH="18" HEIGHT="18"> </div>
  <!--arrow add(by zhangfeng)
  <div class="BtnMenu" TITLE="������" LANGUAGE="javascript">
    <img class="Ico" src="../css/img4edit/arrow.gif" WIDTH="5" HEIGHT="18"> </div>
    end-->
  <div class="Btn" TITLE="������Ŀ��" LANGUAGE="javascript" onclick="FIELDSET()"> <img class="Ico" src="../css/img4edit/fieldset.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="Btn" TITLE="������ҳ" LANGUAGE="javascript" onclick="iframe()"> <img class="Ico" src="../css/img4edit/htm.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="Btn" TITLE="����ͼƬ" LANGUAGE="javascript" onclick="pic()"> <img class="Ico" src="../css/img4edit/img.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="TBSep"></div>
<!--   <iframe class="TBGen" style="top:2px" ID="UploadFiles" src="#" frameborder=0 scrolling=no width="250" height="25"></iframe> -->
  <div class="Btn" TITLE="����flash��ý���ļ�" LANGUAGE="javascript" onclick="swf()"> 
    <img class="Ico" src="../css/img4edit/flash.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="������Ƶ�ļ���֧�ָ�ʽΪ��avi��wmv��asf" LANGUAGE="javascript" onclick="wmv()"> 
    <img class="Ico" src="../css/img4edit/wmv.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����RealPlay�ļ���֧�ָ�ʽΪ��rm��ra��ram" LANGUAGE="javascript" onclick="rm()"> 
    <img class="Ico" src="../css/img4edit/rm.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
    <div class="Btn" TITLE="���볬������" LANGUAGE="javascript" onclick="UserDialog('CreateLink')"> 
    <img class="Ico" src="../css/img4edit/url.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="ȡ����������" LANGUAGE="javascript" onclick="UserDialog('unLink')"> 
    <img class="Ico" src="../css/img4edit/nourl.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="������ͨˮƽ��" LANGUAGE="javascript" onclick="format('InsertHorizontalRule')"> 
    <img class="Ico" src="../css/img4edit/line.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="��������ˮƽ��" LANGUAGE="javascript" onclick="hr()"> <img class="Ico" src="../css/img4edit/sline.gif" WIDTH="18" HEIGHT="18"> 
  </div>
</div>

<div class="yToolbar"> 
  <div class="TBHandle"> </div>
  <select ID="formatSelect" class="TBGen" onchange="format('FormatBlock',this[this.selectedIndex].value);this.selectedIndex=0">
    <option selected>�����ʽ</option>
    <option value="&lt;P&gt;">��ͨ</option>
    <option value="&lt;PRE&gt;">�ѱ��Ÿ�ʽ</option>
    <option value="&lt;H1&gt;">����һ</option>
    <option value="&lt;H2&gt;">�����</option>
    <option value="&lt;H3&gt;">������</option>
    <option value="&lt;H4&gt;">������</option>
    <option value="&lt;H5&gt;">������</option>
    <option value="&lt;H6&gt;">������</option>
    <option value="&lt;H7&gt;">������</option>
  </select>
  <select id="specialtype" class="TBGen" onchange="specialtype(this[this.selectedIndex].value);this.selectedIndex=0">
    <option selected>�����ʽ</option>
    <option value="SUP">�ϱ�</option>
    <option value="SUB">�±�</option>
    <option value="DEL">ɾ����</option>
    <option value="BLINK">��˸</option>
    <option value="BIG">��������</option>
    <option value="SMALL">��С����</option>
  </select>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="�����" name="Justify" LANGUAGE="javascript" onclick="format('justifyleft')"> 
    <img class="Ico" src="../css/img4edit/aleft.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����" name="Justify" LANGUAGE="javascript" onclick="format('justifycenter')"> 
    <img class="Ico" src="../css/img4edit/acenter.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="�Ҷ���" name="Justify" LANGUAGE="javascript" onclick="format('justifyright')"> 
    <img class="Ico" src="../css/img4edit/aright.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="���" LANGUAGE="javascript" onclick="format('insertorderedlist')"> 
    <img class="Ico" src="../css/img4edit/num.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="��Ŀ����" LANGUAGE="javascript" onclick="format('insertunorderedlist')"> 
    <img class="Ico" src="../css/img4edit/list.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����������" LANGUAGE="javascript" onclick="format('outdent')"> 
    <img class="Ico" src="../css/img4edit/outdent.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����������" LANGUAGE="javascript" onclick="format('indent')"> 
    <img class="Ico" src="../css/img4edit/indent.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="ɾ�����ָ�ʽ" LANGUAGE="javascript" onclick="format('RemoveFormat')"> 
    <img class="Ico" src="../css/img4edit/clear.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����"	LANGUAGE="javascript" onclick="save()"> <img class="Ico" src="../css/img4edit/save.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="TBSep"></div>
<!--   <div class="Btn" TITLE="����flash��ý���ļ�" LANGUAGE="javascript" onclick="swf()"> 
    <img class="Ico" src="../css/img4edit/flash.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="������Ƶ�ļ���֧�ָ�ʽΪ��avi��wmv��asf" LANGUAGE="javascript" onclick="wmv()"> 
    <img class="Ico" src="../css/img4edit/wmv.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="����RealPlay�ļ���֧�ָ�ʽΪ��rm��ra��ram" LANGUAGE="javascript" onclick="rm()"> 
    <img class="Ico" src="../css/img4edit/rm.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div> -->
  <div class="Btn" TITLE="�鿴����" LANGUAGE="javascript" onclick="help()"> <img class="Ico" src="../css/img4edit/help.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="Btn" TITLE="��&rarr;��" LANGUAGE="javascript" onclick="s2t();"><img class="Ico" src="../css/img4edit/st.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="��&rarr;��" LANGUAGE="javascript" onclick="t2s();"><img class="Ico" src="../css/img4edit/ts.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="TBSep"></div>
</div>
<div class="yToolbar"> 
  <div class="TBHandle"> </div>
  <select id="FontName" class="TBGen" onchange="format('fontname',this[this.selectedIndex].value);this.selectedIndex=0">
    <option selected>����</option>
    <option value="����">����</option>
    <option value="����">����</option>
    <option value="����_GB2312">����</option>
    <option value="����_GB2312">����</option>
    <option value="����">����</option>
    <option value="��Բ">��Բ</option>
    <option value="Arial">Arial</option>
    <option value="Arial Black">Arial Black</option>
    <option value="Arial Narrow">Arial Narrow</option>
    <option value="Brush Script	MT">Brush Script MT</option>
    <option value="Century Gothic">Century Gothic</option>
    <option value="Comic Sans MS">Comic Sans MS</option>
    <option value="Courier">Courier</option>
    <option value="Courier New">Courier New</option>
    <option value="MS Sans Serif">MS Sans Serif</option>
    <option value="Script">Script</option>
    <option value="System">System</option>
    <option value="Times New Roman">Times New Roman</option>
    <option value="Verdana">Verdana</option>
    <option value="Wide	Latin">Wide Latin</option>
    <option value="Wingdings">Wingdings</option>
  </select>
  <select id="FontSize" class="TBGen" onchange="format('fontsize',this[this.selectedIndex].value);this.selectedIndex=0">
    <option selected>�ֺ�</option>
    <option value="7">һ��</option>
    <option value="6">����</option>
    <option value="5">����</option>
    <option value="4">�ĺ�</option>
    <option value="3">���</option>
    <option value="2">����</option>
    <option value="1">�ߺ�</option>
  </select>
  <div class="TBSep"></div>
  <div class="Btn" TITLE="������ɫ" LANGUAGE="javascript" onclick="foreColor()"> <img class="Ico" src="../css/img4edit/fgcolor.gif" WIDTH="18" HEIGHT="18"> 
  </div>
  <div class="Btn" TITLE="�Ӵ�" LANGUAGE="javascript" onclick="format('bold')"> 
    <img class="Ico" src="../css/img4edit/bold.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="б��" LANGUAGE="javascript" onclick="format('italic')"> 
    <img class="Ico" src="../css/img4edit/italic.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="�»���" LANGUAGE="javascript" onclick="format('underline')"> 
    <img class="Ico" src="../css/img4edit/underline.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="�ϱ�" LANGUAGE="javascript" onclick="format('superscript')"> 
    <img class="Ico" src="../css/img4edit/sup.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="�±�" LANGUAGE="javascript" onclick="format('subscript')"> 
    <img class="Ico" src="../css/img4edit/sub.gif" WIDTH="18" HEIGHT="18"> </div>
  
  <!--add by zhangfeng(ϵͳ��ǰʱ��)-->
  <div class="Btn" TITLE="����ϵͳ��ǰ����" LANGUAGE="javascript" onclick="nowdate()">
  	<img class="Ico" src="../css/img4edit/date.gif" WIDTH="18" HEIGHT="18">
  </div>
  <div class="Btn" TITLE="����ϵͳ��ǰʱ��" LANGUAGE="javascript" onclick="nowtime()">
  	<img class="Ico" src="../css/img4edit/time.gif" WIDTH="18" HEIGHT="18">
  </div>
  <div class="TBSep"></div>
  <!--zf edit end-->
<!--   <div class="Btn" TITLE="���볬������" LANGUAGE="javascript" onclick="UserDialog('CreateLink')"> 
    <img class="Ico" src="../css/img4edit/url.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="ȡ����������" LANGUAGE="javascript" onclick="UserDialog('unLink')"> 
    <img class="Ico" src="../css/img4edit/nourl.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="������ͨˮƽ��" LANGUAGE="javascript" onclick="format('InsertHorizontalRule')"> 
    <img class="Ico" src="../css/img4edit/line.gif" WIDTH="18" HEIGHT="18"> </div>
  <div class="Btn" TITLE="��������ˮƽ��" LANGUAGE="javascript" onclick="hr()"> <img class="Ico" src="../../css/img4edit/sline.gif" WIDTH="18" HEIGHT="18"> 
  </div> -->
  <div class="TBGen" title="�鿴HTMLԴ����"> 
    <input id="EditMode" onclick="setMode(this.checked)" type="checkbox">
    �鿴HTMLԴ����</div>
</div>

<iframe class="HtmlEdit" ID="HtmlEdit" MARGINHEIGHT="1" MARGINWIDTH="1" width="100%" height="320"> 
</iframe>
<script type="text/javascript">
SEP_PADDING = 5
HANDLE_PADDING = 7

var yToolbars =	new Array();
var YInitialized = false;
var bLoad=false
var pureText=true
var bodyTag="<head><style type=\"text/css\">body {font-size:	9pt}</style><meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"></head><body bgcolor=\"#FFFFFF\" MONOSPACE>"
var bTextMode=false

public_description=new Editor

function document.onreadystatechange(){
  if (YInitialized) return;
  YInitialized = true;

  var i, s, curr;

  for (i=0; i<document.body.all.length;	i++)
  {
    curr=document.body.all[i];
    if (curr.className == "yToolbar")
    {
      InitTB(curr);
      yToolbars[yToolbars.length] = curr;
    }
  }

  DoLayout();
  window.onresize = DoLayout;

  HtmlEdit.document.open();
  HtmlEdit.document.write(bodyTag);
  HtmlEdit.document.close();
  HtmlEdit.document.designMode="On";
}

function InitBtn(btn)
{
  btn.onmouseover = BtnMouseOver;
  btn.onmouseout = BtnMouseOut;
  btn.onmousedown = BtnMouseDown;
  btn.onmouseup	= BtnMouseUp;
  btn.ondragstart = YCancelEvent;
  btn.onselectstart = YCancelEvent;
  btn.onselect = YCancelEvent;
  btn.YUSERONCLICK = btn.onclick;
  btn.onclick =	YCancelEvent;
  btn.YINITIALIZED = true;
  return true;
}

function InitTB(y)
{
  y.TBWidth = 0;

  if (!	PopulateTB(y)) return false;

  y.style.posWidth = y.TBWidth;

  return true;
}


function YCancelEvent()
{
  event.returnValue=false;
  event.cancelBubble=true;
  return false;
}

function PopulateTB(y)
{
  var i, elements, element;

  elements = y.children;
  for (i=0; i<elements.length; i++) {
    element = elements[i];
    if (element.tagName	== "SCRIPT" || element.tagName == "!") continue;

    switch (element.className) {
      case "Btn":
        if (element.YINITIALIZED == null)	{
          if (! InitBtn(element))
          return false;
        }
        element.style.posLeft = y.TBWidth;
        y.TBWidth	+= element.offsetWidth + 1;
        break;

      case "TBGen":
        element.style.posLeft = y.TBWidth;
        y.TBWidth	+= element.offsetWidth + 1;
        break;

      case "TBSep":
        element.style.posLeft = y.TBWidth	+ 2;
        y.TBWidth	+= SEP_PADDING;
        break;

      case "TBHandle":
        element.style.posLeft = 2;
        y.TBWidth	+= element.offsetWidth + HANDLE_PADDING;
        break;

      default:
        return false;
      }
  }

  y.TBWidth += 1;
  return true;
}

function DebugObject(obj)
{
  var msg = "";
  for (var i in	TB) {
    ans=prompt(i+"="+TB[i]+"\n");
    if (! ans) break;
  }
}

function LayoutTBs()
{
  NumTBs = yToolbars.length;

  if (NumTBs ==	0) return;

  var i;
  var ScrWid = (document.body.offsetWidth) - 6;
  var TotalLen = ScrWid;
  for (i = 0 ; i < NumTBs ; i++) {
    TB = yToolbars[i];
    if (TB.TBWidth > TotalLen) TotalLen	= TB.TBWidth;
  }

  var PrevTB;
  var LastStart	= 0;
  var RelTop = 0;
  var LastWid, CurrWid;
  var TB = yToolbars[0];
  TB.style.posTop = 0;
  TB.style.posLeft = 0;

  var Start = TB.TBWidth;
  for (i = 1 ; i < yToolbars.length ; i++) {
    PrevTB = TB;
    TB = yToolbars[i];
    CurrWid = TB.TBWidth;

    if ((Start + CurrWid) > ScrWid) {
      Start = 0;
      LastWid =	TotalLen - LastStart;
    }
    else {
       LastWid =	PrevTB.TBWidth;
       RelTop -=	TB.offsetHeight;
    }

    TB.style.posTop = RelTop;
    TB.style.posLeft = Start;
    PrevTB.style.width = LastWid;

    LastStart =	Start;
    Start += CurrWid;
  }

  TB.style.width = TotalLen - LastStart;

  i--;
  TB = yToolbars[i];
  var TBInd = TB.sourceIndex;
  var A	= TB.document.all;
  var item;
  for (i in A) {
    item = A.item(i);
    if (! item)	continue;
    if (! item.style) continue;
    if (item.sourceIndex <= TBInd) continue;
    if (item.style.position == "absolute") continue;
    item.style.posTop =	RelTop;
  }
}

function DoLayout()
{
  LayoutTBs();
}

function BtnMouseOver()
{
  if (event.srcElement.tagName != "IMG") return	false;
  var image = event.srcElement;
  var element =	image.parentElement;

  if (image.className == "Ico")	element.className = "BtnMouseOverUp";
  else if (image.className == "IcoDown") element.className = "BtnMouseOverDown";

  event.cancelBubble = true;
}

function BtnMouseOut()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;
  yRaisedElement = null;

  element.className = "Btn";
  image.className = "Ico";

  event.cancelBubble = true;
}

function BtnMouseDown()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    event.returnValue=false;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;

  element.className = "BtnMouseOverDown";
  image.className = "IcoDown";

  event.cancelBubble = true;
  event.returnValue=false;
  return false;
}

function BtnMouseUp()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;

  if (element.YUSERONCLICK) eval(element.YUSERONCLICK +	"anonymous()");

  element.className = "BtnMouseOverUp";
  image.className = "Ico";

  event.cancelBubble = true;
  return false;
}

function getEl(sTag,start)
{
  while	((start!=null) && (start.tagName!=sTag)) start = start.parentElement;
  return start;
}

function cleanHtml()
{
  var fonts = HtmlEdit.document.body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML	= curr.innerHTML;
  }
}

function getPureHtml()
{
  var str = "";
  var paras = HtmlEdit.document.body.all.tags("P");
  if (paras.length > 0)	{
    for	(var i=paras.length-1; i >= 0; i--) str	= paras[i].innerHTML + "\n" + str;
  }
  else {
    str	= HtmlEdit.document.body.innerHTML;
  }
  return str;
}


function Editor()
{
  this.put_HtmlMode=setMode;
  this.put_value=putText;
  this.get_value=getText;
}

function getText()
{
  if (bTextMode)
    return HtmlEdit.document.body.innerText;
  else
  {
    cleanHtml();
    cleanHtml();
    return HtmlEdit.document.body.innerHTML;
  }
}

function putText(v)
{
  if (bTextMode)
    HtmlEdit.document.body.innerText = v;
  else
    HtmlEdit.document.body.innerHTML = v;
}

function UserDialog(what)
{
  if (!validateMode()) return;

  HtmlEdit.document.execCommand(what, true);

  pureText = false;
  HtmlEdit.focus();
}

function validateMode()
{
  if (!	bTextMode) return true;
  alert("��ȡ�����鿴HTMLԴ���롱ѡ�Ȼ����ʹ��ϵͳ�༭����!");
  HtmlEdit.focus();
  return false;
}

function format(what,opt)
{
  if (!validateMode()) return;
  if (opt=="removeFormat")
  {
    what=opt;
    opt=null;
  }

  if (opt==null) HtmlEdit.document.execCommand(what);
  else HtmlEdit.document.execCommand(what,"",opt);

  pureText = false;
  HtmlEdit.focus();
}

function setMode(newMode)
{
  var cont;
  bTextMode = newMode;
  if (bTextMode) {
    cleanHtml();
    cleanHtml();

    cont=HtmlEdit.document.body.innerHTML;
    HtmlEdit.document.body.innerText=cont;
  }
  else {
    cont=HtmlEdit.document.body.innerText;
    HtmlEdit.document.body.innerHTML=cont;
  }
  HtmlEdit.focus();
}

function foreColor()
{
  if (!	validateMode())	return;
  var arr = showModalDialog("./selcolor.jsp", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0");
  if (arr != null) format('forecolor', arr);
  else HtmlEdit.focus();
}

function InsertTable()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./table.jsp", "", "dialogWidth:300pt;dialogHeight:236pt;help:0;status:0");

  if (arr != null){
	range.pasteHTML(arr);
  }
  HtmlEdit.focus();
}


function pic()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./pic.jsp", "", "dialogWidth:35em; dialogHeight:20em; status:0;help:0");  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  f=ss[5];
  g=ss[6];
  h=ss[7];
  i=ss[8];
  
  var str1;
str1="<img src='"+a+"' alt='"+b+"'"
if(d.value!=null && d.value!='')str1=str1+"width='"+d+"'"
if(e.value!=null && e.value!='')str1=str1+"height='"+e+"' "
str1=str1+" border='"+i+"' align='"+h+"' vspace='"+f+"' hspace='"+g+"'  style='"+c+"'"
str1=str1+">"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function swf()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./flash.jsp", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0"); 
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width="+row+" height="+col+"><param name=movie value="+path+"><param name=quality value=high><embed src="+path+" pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width="+row+" height="+col+"></embed></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function hr()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./hr.jsp", "", "dialogWidth:30em; dialogHeight:12em; status:0;help:0"); 
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  var str1;
str1="<hr"
str1=str1+" color='"+a+"'"
str1=str1+" size="+b+"'"
str1=str1+" "+c+""
str1=str1+" align="+d+""
str1=str1+" width="+e
str1=str1+">"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function FIELDSET()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./fieldset.jsp", "", "dialogWidth:25em; dialogHeight:10em; status:0;help:0");
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  var str1;
str1="<FIELDSET "
str1=str1+"align="+a+""
str1=str1+" style='"
if(c.value!='')str1=str1+"color:"+c+";"
if(d.value!='')str1=str1+"background-color:"+d+";"
str1=str1+"'><Legend"
str1=str1+" align="+b+""
str1=str1+">����</Legend>����</FIELDSET>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function iframe()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./iframe.jsp", "", "dialogWidth:30em; dialogHeight:13em; status:0;help:0");  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  f=ss[5];
  g=ss[6];
  var str1;
str1="<iframe src='"+a+"'"
str1+=" scrolling="+b+""
str1+=" frameborder="+c+""
if(d!='')str1+=" marginheight="+d
if(e!='')str1+=" marginwidth="+e
if(f!='')str1+=" width="+f
if(g!='')str1+=" height="+g
str1=str1+"></iframe>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function wmv()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./media.jsp", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' width="+row+" height="+col+"><param name=Filename value="+path+"><param name='BufferingTime' value='5'><param name='AutoSize' value='-1'><param name='AnimationAtStart' value='-1'><param name='AllowChangeDisplaySize' value='-1'><param name='ShowPositionControls' value='0'><param name='TransparentAtStart' value='1'><param name='ShowStatusBar' value='1'></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}


function rm()
{
  if (!	validateMode())	return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("./rm.jsp", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+row+" height="+col+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+path+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+row+" height=60><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function specialtype(Mark){
  if (!Error()) return;
  var sel,RangeType
  sel = HtmlEdit.document.selection.createRange();
  RangeType = HtmlEdit.document.selection.type;
  if (RangeType == "Text"){
    sel.pasteHTML("<" + Mark + ">" + sel.text + "</" + Mark + ">");
    sel.select();
  }
  HtmlEdit.focus();
}

function help()
{
  var arr = showModalDialog("./help.jsp", "", "dialogWidth:580px; dialogHeight:460px; status:0");
}

function save()
{
  if (bTextMode){
//�༭��Ƕ��������ҳʱʹ��������һ�䣨�뽫form1�ĳ���Ӧ������
	parent.smbackForm.briefintro.value=HtmlEdit.document.body.innerText;
  //parent.myform.Content.value=HtmlEdit.document.body.innerText;
//�����򿪱༭��ʱʹ��������һ�䣨�뽫form1�ĳ���Ӧ������  
//  self.opener.form1.content.value+=HtmlEdit.document.body.innerText;
  }
  else{
//�༭��Ƕ��������ҳʱʹ��������һ�䣨�뽫form1�ĳ���Ӧ������
	parent.smbackForm.briefintro.value = HtmlEdit.document.body.innerHTML;
  //parent.myform.Content.value=HtmlEdit.document.body.innerHTML;
//�����򿪱༭��ʱʹ��������һ�䣨�뽫form1�ĳ���Ӧ������  
//  self.opener.form1.content.value+=HtmlEdit.document.body.innerHTML;
  }
  HtmlEdit.focus();
  return false;
}
</script>
<script type="text/javascript" language="javascript">
function s2t()
{
	HtmlEdit.document.body.innerHTML=HtmlEdit.document.body.innerHTML.s2t();
	/*
	var objs = new Array();
	objs[0]=WBTB_Composition;
	objs[1]=document.all.Title;
	objs[2]=document.all.Summary;
	objs[3]=document.all.Author;
	objs[4]=document.all.Source;
	objs[0].document.body.innerHTML = objs[0].document.body.innerHTML.s2t();
	for (var i=1; i<objs.length; i++) {
		objs[i].value = objs[i].value.s2t();
	}
	*/
}
function t2s()
{
	HtmlEdit.document.body.innerHTML=HtmlEdit.document.body.innerHTML.t2s();
	/*
	var objs = new Array();
	objs[0]=WBTB_Composition;
	objs[1]=document.all.Title;
	objs[2]=document.all.Summary;
	objs[3]=document.all.Author;
	objs[4]=document.all.Source;
	objs[0].document.body.innerHTML = objs[0].document.body.innerHTML.t2s();
	for (var i=1; i<objs.length; i++) {
		objs[i].value = objs[i].value.t2s();
	}
	*/
}
//add the now date
function nowdate(){
	  if (!	validateMode())	return;
	  HtmlEdit.focus();
	  var range = HtmlEdit.document.selection.createRange();
	  var d = new Date();
	  var str1 = d.getYear()+"��"+((d.getMonth())+1)+"��"+d.getDate()+"��";
	  range.pasteHTML(str1);
	  HtmlEdit.focus();
}
//add the now time
function nowtime(){
	  if (!	validateMode())	return;
	  HtmlEdit.focus();
	  var range = HtmlEdit.document.selection.createRange();
	  var d = new Date();
	  var str1 = d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	  range.pasteHTML(str1);
	  HtmlEdit.focus();
}
</script>
</body>
</html>