<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../js/form.js"></script>
    <script language="javascript">
    	
    	//检查
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.title,"帖子标题")) return false;
    		if (!checkNotNull(addstaffer.content,"帖子内容")) return false;
    		return true;
    	}
    	//回帖
    	function answerPost(){
    	    var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "oper.do?method=answerPosts&itemid=<bean:write name='itemid'/>&postid=<bean:write name='postid'/>";
    		document.forms[0].submit();
    		}
    	}
    </script>
    
<script language="JavaScript">
  var userAgent = navigator.userAgent.toLowerCase();
  var is_ie     = userAgent.indexOf('msie') != -1;
  var text = "";
  var AddTxt = "";
  var theform;
  helpstat = false;
  stprompt = true;
  basic = false;
  var word="文字";
  function init() {

    theform=document.forumPostBean;
  }
  function DoTitle(addTitle) {
    var revisedTitle;
    var currentTitle = document.forumPostBean.title.value;
    revisedTitle = currentTitle+addTitle;
    document.forumPostBean.title.value=revisedTitle;
    document.forumPostBean.title.focus();
    return;
  }
  function thelp(swtch){
   if (swtch == 1){ basic = false; stprompt = false; helpstat = true;} else if (swtch == 0) {helpstat = false;stprompt = false;basic = true;} else if (swtch == 2) {helpstat = false;basic = false;stprompt = true;}
  }
  function getActiveText() {
    setfocus();
    if (!is_ie || (is_ie && !document.selection)) {
       return false;
    }
    var sel = document.selection;
    var rng = sel.createRange();
    rng.colapse;
    if (rng != null && (sel.type == "Text" || sel.type == "None")) {
       text = rng.text;
    }
    if (theform.content.createTextRange) {
       theform.content.caretPos = rng.duplicate();
    }
    return true;
  }
  function setfocus() {
    
	theform.content.focus();
	
  }
  //function AddText(NewCode) {theform.content.value+=NewCode}
  function AddText(NewCode) {
    if (typeof(theform.content.createTextRange) != "undefined" && theform.content.caretPos) {
      var caretPos = theform.content.caretPos;
      caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? NewCode + ' ' : NewCode;
    } else {
      theform.content.value += NewCode;
    }
    setfocus();
    getActiveText();
    AddTxt = "";
  }
  function lycode(lycode, optioncompiled, prompttip, prompttext) {
    if(optioncompiled!='') {
      optioncompiled="="+optioncompiled;
    }
    // lets call this when they try and use lycode rather than on change
    getActiveText();
    if (text) {
       // its IE to the rescue
       if (text.substring(0, lycode.length + 2 ) == "[" + lycode + "]" && text.substring(text.length - lycode.length - 3, text.length) == "[/" + lycode + "]") {
         AddTxt = text.substring(lycode.length + 2, text.length - lycode.length - 3);
       } else {
         AddTxt = "[" + lycode + optioncompiled + "]" + text + "[/" + lycode + "]";
       }
       AddText(AddTxt);
    } else if (theform.content.selectionEnd && (theform.content.selectionEnd - theform.content.selectionStart > 0)) {
      // its mozilla and we'll need to re-write entire text
      var start_selection = theform.content.selectionStart;
      var end_selection = theform.content.selectionEnd;
      if (end_selection <= 2) {
        end_selection = theform.content.textLength;
      }

      // fetch everything from start of text area to selection start
      var start = (theform.content.value).substring(0, start_selection);
      // fetch everything from start of selection to end of selection
      var middle = (theform.content.value).substring(start_selection, end_selection);
      // fetch everything from end of selection to end of text area
      var end = (theform.content.value).substring(end_selection, theform.content.textLength);

      if (middle.substring(0, lycode.length + 2 ) == "[" + lycode + "]" && middle.substring(middle.length - lycode.length - 3, middle.length) == "[/" + lycode + "]") {
        middle = middle.substring(lycode.length + 2, middle.length - lycode.length - 3);
      } else {
        middle = "[" + lycode + optioncompiled + "]" + middle + "[/" + lycode + "]";
      }

      theform.content.value = start + middle + end;
    }
    else {
      if(prompttip=='') {
         AddTxt = "[" + lycode + optioncompiled + "]"+prompttext+"[/" + lycode + "] ";
      } else {
        var inserttext = prompt(prompttip, prompttext);
        if (inserttext != null) {
          AddTxt = "[" + lycode + optioncompiled + "]" + inserttext + "[/" + lycode + "] ";
        }
      }
      AddText(AddTxt);
    }
    setfocus();
    return false;
  }
  function smilie(smilietext) { 
  	
	  getActiveText();
    var AddSmilie = " " + smilietext + " ";
    AddText(AddSmilie);
  }
  function n_display(t_id) {
    t_id.style.display="";
  }
  function h_display(t_id) {
    t_id.style.display="none";
  }
  function displayViewNumber() {
    var viewMode=document.forumPostBean.viewMode;
    var t_id=document.forumPostBean.viewNumber;
    if(viewMode.options[viewMode.selectedIndex].value==4 || viewMode.options[viewMode.selectedIndex].value==5 || viewMode.options[viewMode.selectedIndex].value==6) {
      n_display(t_id);
    }
    else {
      h_display(t_id);
    }
  }
  function setAccessary(accessary) {
    theform.accessary.value=accessary;
    theform.updateAttachment.value=accessary;
  }
  function setAccessary2(accessary) {
    theform.accessary.value=accessary;
  }
  function votecondition(t_id,displayValue) {
    t_id.style.display=displayValue;
  }
</script>
<script language="JavaScript">
       function smilieopen() {javascript:openScript('misc.do?action=showsmilies',800,600);}
       function viewlycode() {javascript:openScript('misc.do?action=lycode',800,600);}
       function rm() {
         if (helpstat) {
           alert("在线 RealPlayer 流式音/视频播放");
         }
         else if (basic) {
           lycode("rm","380,350","","");
         }
         else {
           lycode("rm","380,350","在线 RealPlayer 流式音/视频播放","http://");
         }
       }
       function mp() {
         if (helpstat) {
           alert("在线 Windows Media Player 音/视频播放\n播放 URL 地址\n用法：[mp=380,350] http://www.lybbs.net/lybbs/upload/test.mp3[/mp]\n　　　[mp=380,72] mms://www.lybbs.net/lybbs/upload/test.mp3[/mp]");
         }
         else if (basic) {
           lycode("mp","380,350","","");
         }
         else {
           lycode("mp","380,350","在线 Windows Media Player 音/视频播放","http://");
         }
       }
       function qt() {
         if (helpstat) {
           alert("在线 Quick Time 音/视频播放\n播放 URL 地址\n用法：[qt=380,72] http://www.lybbs.net/lybbs/upload/test.qt[/qt]");
         }
         else if (basic) {
           lycode("qt","380,72","","");
         }
         else {
           lycode("qt","380,72","在线 Quick Time 音/视频播放","http://");
         }
       }
       function email() {
         if (helpstat) {
           alert("Email 标记\n插入 Email 超级链接\n用法1: [email]horseye@sina.com[/email]\n用法2: [email=horseye@sina.com]teddy[/email]");
         }
         else if (basic) {
           lycode("email","","","");
         }
         else {
           lycode("email","","链接显示的文字.\n如果为空，那么将只显示你的 Email 地址","");
         }
       }
       function showsize(size) {
         if (helpstat) {alert("文字大小标记\n设置文字大小.\n可变范围 1 - 6.\n 1 为最小 6 为最大.\n用法: [size="+size+"]这是 "+size+" 文字[/size]");
         }
         else if (basic) {
           lycode("size",size,"","");
         }
         else {
           lycode("size",size,"大小",word);
         }
       }
       function bold() {
         if (helpstat) {alert("加粗标记\n使文本加粗.\n用法: [b]这是加粗的文字[/b]");
         }
         else if (basic) {
           lycode("b","","","");
         }
         else {
           lycode("b","","文字将被变粗.",word);
         }
       }
       function italicize() {
         if (helpstat) {
           alert("斜体标记\n使文本字体变为斜体.\n用法: [i]这是斜体字[/i]");
         } else if (basic) {
           lycode("i","","","");
         } else {
           lycode("i","","文字将变斜体",word);
         }
       }
       function underline() {
         if (helpstat) {
           alert("下划线标记\n给文字加下划线.\n用法: [u]要加下划线的文字[/u]");
         } else if (basic) {
           lycode("u","","","");
         } else {
           lycode("u","","下划线文字.",word);
         }
       }
       function quoteme() {
         if (helpstat){
           alert("引用标记\n引用一些文字.\n用法: [quote]引用内容[/quote]");
         }
         else if (basic) {
           lycode("quote","","","");
         } else {
           lycode("quote","","被引用的文字",word);
         }
       }
       function setsound() {
         if (helpstat) {
           alert("声音标记\n产生背景音乐.\n用法: [sound]音乐文件的地址[/sound]");
         } else if (basic) {
           lycode("sound","","","");
         } else {
           lycode("sound","","产生背景音乐.","http://");
         }
       }
       function showcolor(color) {
         if (helpstat) {
           alert("颜色标记\n设置文本颜色.  任何颜色名都可以被使用.\n用法: [color="+color+"]颜色要改变为"+color+"的文字[/color]");
         }
         else if (basic) {
           lycode("color",color,"","");
         } else {
           lycode("color",color,"选择的颜色是: "+color,word);
         }
       }
       function setfly() {
         if (helpstat){
           alert("飞翔标记\n使文字飞行.\n用法: [fly]文字为这样文字[/fly]");
         } else if (basic) {
           lycode("fly","","","");
         } else {
           lycode("fly","","飞翔文字",word);
         }
       }
       function move() {
         if (helpstat) {
           alert("移动标记\n使文字产生移动效果.\n用法: [move]要产生移动效果的文字[/move]");
         } else if (basic) {
           lycode("move","","","");
         } else {
           lycode("move","","要产生移动效果的文字",word);
         }
       }
       function center() {
         if (helpstat) {
           alert("对齐标记\n使用这个标记, 可以使文本左对齐、居中、右对齐.\n用法: [align=center|left|right]要对齐的文本[/align]");
         } else if (basic) {
           lycode("align","center","","");
         } else {
           lycode("align","center","对齐标记\n使用这个标记, 可以使文本左对齐、居中、右对齐.\n用法: [align=center|left|right]要对齐的文本[/align]",word);
         }
       }
       function hyperlink() {
         if (helpstat) {
           alert("超级链接标记\n插入一个超级链接标记\n使用方法: [url]http://www.lybbs.net[/url]\n或者: [url=http://www.lybbs.net]凌云论坛[/url]");
         } else if (basic) {
           lycode("url","","","");
         } else {
           lycode("url","","超级链接标记\n插入一个超级链接标记\n使用方法: [url]http://www.lybbs.net[/url]\n或者: [url=http://www.lybbs.net]凌云论坛[/url]",word);
         }
       }
       function image() {
         if (helpstat){
           alert("图片标记\n插入图片\n用法： [img]http://www.9i5i.com/logo.gif[/img]");
         } else if (basic) {
           lycode("img","","","");
         } else {
           lycode("img","","图片的 URL","http://");
         }
       }
       function showcode() {
         if (helpstat) {
           alert("代码标记\n使用代码标记，可以使你的程序代码里面的 html 等标志不会被破坏.\n使用方法:\n [code]这里是代码文字[/code]");
         } else if (basic) {
           lycode("code","","","");
         } else {
           lycode("code","","输入代码","");
         }
       }
       function list() {
         if (helpstat) {
           alert("列表标记\n建造一个文字或则数字列表.\nUSE: [list]\n[*]item1[/*]\n[*]item2[/*]\n[*]item3[/*]\n[/list]");
         } else if (basic) {
           lycode("list","","","\r[*]\r[*]\r[*]");
         } else {
           txt=prompt("列表类型\n输入 留空表示无序列表.","");               
           while ((txt!="")) {txt=prompt("错误!\n类型只能留空.",""); }
           if (txt!=null) {if (txt=="") {AddTxt="\r[list]\r\n";} else {AddTxt="\r[list="+txt+"]\r";} txt="1";
           while ((txt!="") && (txt!=null)) {txt=prompt("列表项\n空白表示结束列表",""); 
           if (txt!="") {AddTxt+="[*]"+txt+"[/*]\r"; }} AddTxt+="[/list]\r\n";AddText(AddTxt); }
         }
       }
       function showfont(font) {
         if (helpstat){
           alert("字体标记\n给文字设置字体.\n用法: [face="+font+"]改变文字字体为"+font+"[/face]");
         } else if (basic) {
           lycode("face",font,"","");
         } else {
           lycode("face",font,"要设置字体的文字"+font,word);
         }  
       }
       function setswf() {
         if (helpstat){
           alert("Flash 动画\n插入 Flash 动画.\n用法: [swf]Flash 文件的地址[/swf]");
         } else if (basic) {
           lycode("swf","","","");
         } else {
           lycode("swf","","Flash 文件的地址","http://");

         }  
       }
       function pic()
		{
		  var arr = showModalDialog("./pic.jsp", "", "dialogWidth:35em; dialogHeight:17em; status:0;help:0");  
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

		  theform.btnHid.value = str1;
		}
		
       function inputs(str){document.REPLIER.icon.value=str;}
      </SCRIPT>
	  
	     <script>
function checklength(theform){alert("您的文章目前有 "+theform.content.value.length+" 字节");}
function enable(btn){btn.filters.gray.enabled=0;}
function disable(btn){btn.filters.gray.enabled=1;}
   </script>
    
    
    
    
    

    <style type="text/css">
		<!--
		.STYLE1 {font-size: 14pt}
		-->
    </style>
  </head>
  
  <body bgcolor="#eeeeee" onload="init()">
    <html:form action="/forum/postOper/oper" method="post">
    

    <%-- jps include 头 --%>
  	<jsp:include flush="true" page="../common/top.jsp"></jsp:include>
	<%-- 加 --%>

	<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
	  <tr background="../../images/forum/nabiaoti_03.jpg">
	    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
		<%--   加到这里    --%>
		
		
		
	 <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td colspan="2" background="../../images/forum/biaoti1_03.jpg" class="STYLE1">直接回复文章--点击这里进入非可视化模式</td>
              </tr>
            <tr>
              <td class="danzhi">&nbsp; 文章标题： </td>
                <td><label>
                  <html:text property="title"/>
                </label></td>
              </tr>
            <tr>
              <td class="danzhi">&nbsp; 文章描述： </td>
                <td>
                回复帖子无需描述
                </td>
            </tr>
            <tr>
              <td valign="top"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="danzhi">&nbsp; 文章内容：</td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; <label>
                    <input type="checkbox" name="checkbox" value="checkbox" />
                    设置发贴选项</label></td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; <label>
                    <input type="checkbox" name="checkbox2" value="checkbox" />
                    HTML代码不可用</label></td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; BMB Code 可以 </td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; 点击这里 看使用方法 </td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; [img]--开启</td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; [flash]--开启 </td>
                  </tr>
                <tr>
                  <td class="danzhi">&nbsp; [size]--开启 </td>
                  </tr>
                <tr>
                  <td></td>
                  </tr>
                </table></td>
                <td>
                
                
                
                <!-- the send part -->
                &nbsp;<font color=#000000>字体:</font>
				<select onchange=showfont(this.options[this.selectedIndex].value) name=font>
				  <option value="宋体" selected>宋体</option>
				  <option value="楷体">楷体</option>
				  <option value="新宋体">新宋体</option>
				  <option value="黑体">黑体</option>
				  <option value="隶书">隶书</option>
				  <OPTION value=Arial>Arial</OPTION> 
				  <OPTION value=Georgia>Georgia</OPTION>
				  <OPTION value=Impact>Impact</OPTION>
				  <OPTION value=Tahoma>Tahoma</OPTION>
				  <OPTION value=Stencil>Stencil</OPTION>
				  <OPTION value=Verdana>Verdana</OPTION>
				</SELECT> 
				<font color=#000000>字号:</font>
				<SELECT onchange=showsize(this.options[this.selectedIndex].value) name=size>
				  <OPTION value=1>1</OPTION>
				  <OPTION value=2>2</OPTION>
				  <OPTION value=3 selected>3</OPTION>
				  <OPTION value=4>4</OPTION>
				  <OPTION value=5>5</OPTION>
				  <OPTION value=6>6</OPTION>
				</SELECT>
				<font color=#000000>颜色:</font>
				<SELECT onchange=showcolor(this.options[this.selectedIndex].value) name=color> 
				  <option style=background-color:#F0F8FF;color:#F0F8FF value=#F0F8FF>#F0F8FF</option>
				  <option style=background-color:#FAEBD7;color:#FAEBD7 value=#FAEBD7>#FAEBD7</option>
				  <option style=background-color:#00FFFF;color:#00FFFF value=#00FFFF>#00FFFF</option>
				  <option style=background-color:#7FFFD4;color:#7FFFD4 value=#7FFFD4>#7FFFD4</option>
				  <option style=background-color:#F0FFFF;color:#F0FFFF value=#F0FFFF>#F0FFFF</option>
				  <option style=background-color:#F5F5DC;color:#F5F5DC value=#F5F5DC>#F5F5DC</option>
				  <option style=background-color:#FFE4C4;color:#FFE4C4 value=#FFE4C4>#FFE4C4</option>
				  <option style=background-color:#000000;color:#000000 value=#000000>#000000</option>
				  <option style=background-color:#FFEBCD;color:#FFEBCD value=#FFEBCD>#FFEBCD</option>
				  <option style=background-color:#0000FF;color:#0000FF value=#0000FF>#0000FF</option>
				  <option style=background-color:#8A2BE2;color:#8A2BE2 value=#8A2BE2>#8A2BE2</option>
				  <option style=background-color:#A52A2A;color:#A52A2A value=#A52A2A>#A52A2A</option>
				  <option style=background-color:#DEB887;color:#DEB887 value=#DEB887>#DEB887</option>
				  <option style=background-color:#5F9EA0;color:#5F9EA0 value=#5F9EA0>#5F9EA0</option>
				  <option style=background-color:#7FFF00;color:#7FFF00 value=#7FFF00>#7FFF00</option>
				  <option style=background-color:#D2691E;color:#D2691E value=#D2691E>#D2691E</option>
				  <option style=background-color:#FF7F50;color:#FF7F50 value=#FF7F50>#FF7F50</option>
				  <option style=background-color:#6495ED;color:#6495ED value=#6495ED selected>#6495ED</option>
				  <option style=background-color:#FFF8DC;color:#FFF8DC value=#FFF8DC>#FFF8DC</option>
				  <option style=background-color:#DC143C;color:#DC143C value=#DC143C>#DC143C</option>
				  <option style=background-color:#00FFFF;color:#00FFFF value=#00FFFF>#00FFFF</option>
				  <option style=background-color:#00008B;color:#00008B value=#00008B>#00008B</option>
				  <option style=background-color:#008B8B;color:#008B8B value=#008B8B>#008B8B</option>
				  <option style=background-color:#B8860B;color:#B8860B value=#B8860B>#B8860B</option>
				  <option style=background-color:#A9A9A9;color:#A9A9A9 value=#A9A9A9>#A9A9A9</option>
				  <option style=background-color:#006400;color:#006400 value=#006400>#006400</option>
				  <option style=background-color:#BDB76B;color:#BDB76B value=#BDB76B>#BDB76B</option>
				  <option style=background-color:#8B008B;color:#8B008B value=#8B008B>#8B008B</option>
				  <option style=background-color:#556B2F;color:#556B2F value=#556B2F>#556B2F</option>
				  <option style=background-color:#FF8C00;color:#FF8C00 value=#FF8C00>#FF8C00</option>
				  <option style=background-color:#9932CC;color:#9932CC value=#9932CC>#9932CC</option>
				  <option style=background-color:#8B0000;color:#8B0000 value=#8B0000>#8B0000</option>
				  <option style=background-color:#E9967A;color:#E9967A value=#E9967A>#E9967A</option>
				  <option style=background-color:#8FBC8F;color:#8FBC8F value=#8FBC8F>#8FBC8F</option>
				  <option style=background-color:#483D8B;color:#483D8B value=#483D8B>#483D8B</option>
				  <option style=background-color:#2F4F4F;color:#2F4F4F value=#2F4F4F>#2F4F4F</option>
				  <option style=background-color:#00CED1;color:#00CED1 value=#00CED1>#00CED1</option>
				  <option style=background-color:#9400D3;color:#9400D3 value=#9400D3>#9400D3</option>
				  <option style=background-color:#FF1493;color:#FF1493 value=#FF1493>#FF1493</option>
				  <option style=background-color:#00BFFF;color:#00BFFF value=#00BFFF>#00BFFF</option>
				  <option style=background-color:#696969;color:#696969 value=#696969>#696969</option>
				  <option style=background-color:#1E90FF;color:#1E90FF value=#1E90FF>#1E90FF</option>
				  <option style=background-color:#B22222;color:#B22222 value=#B22222>#B22222</option>
				  <option style=background-color:#FFFAF0;color:#FFFAF0 value=#FFFAF0>#FFFAF0</option>
				  <option style=background-color:#228B22;color:#228B22 value=#228B22>#228B22</option>
				  <option style=background-color:#FF00FF;color:#FF00FF value=#FF00FF>#FF00FF</option>
				  <option style=background-color:#DCDCDC;color:#DCDCDC value=#DCDCDC>#DCDCDC</option>
				  <option style=background-color:#F8F8FF;color:#F8F8FF value=#F8F8FF>#F8F8FF</option>
				  <option style=background-color:#FFD700;color:#FFD700 value=#FFD700>#FFD700</option>
				  <option style=background-color:#DAA520;color:#DAA520 value=#DAA520>#DAA520</option>
				  <option style=background-color:#808080;color:#808080 value=#808080>#808080</option>
				  <option style=background-color:#008000;color:#008000 value=#008000>#008000</option>
				  <option style=background-color:#ADFF2F;color:#ADFF2F value=#ADFF2F>#ADFF2F</option>
				  <option style=background-color:#F0FFF0;color:#F0FFF0 value=#F0FFF0>#F0FFF0</option>
				  <option style=background-color:#FF69B4;color:#FF69B4 value=#FF69B4>#FF69B4</option>
				  <option style=background-color:#CD5C5C;color:#CD5C5C value=#CD5C5C>#CD5C5C</option>
				  <option style=background-color:#4B0082;color:#4B0082 value=#4B0082>#4B0082</option>
				  <option style=background-color:#FFFFF0;color:#FFFFF0 value=#FFFFF0>#FFFFF0</option>
				  <option style=background-color:#F0E68C;color:#F0E68C value=#F0E68C>#F0E68C</option>
				  <option style=background-color:#E6E6FA;color:#E6E6FA value=#E6E6FA>#E6E6FA</option>
				  <option style=background-color:#FFF0F5;color:#FFF0F5 value=#FFF0F5>#FFF0F5</option>
				  <option style=background-color:#7CFC00;color:#7CFC00 value=#7CFC00>#7CFC00</option>
				  <option style=background-color:#FFFACD;color:#FFFACD value=#FFFACD>#FFFACD</option>
				  <option style=background-color:#ADD8E6;color:#ADD8E6 value=#ADD8E6>#ADD8E6</option>
				  <option style=background-color:#F08080;color:#F08080 value=#F08080>#F08080</option>
				  <option style=background-color:#E0FFFF;color:#E0FFFF value=#E0FFFF>#E0FFFF</option>
				  <option style=background-color:#FAFAD2;color:#FAFAD2 value=#FAFAD2>#FAFAD2</option>
				  <option style=background-color:#90EE90;color:#90EE90 value=#90EE90>#90EE90</option>
				  <option style=background-color:#D3D3D3;color:#D3D3D3 value=#D3D3D3>#D3D3D3</option>
				  <option style=background-color:#FFB6C1;color:#FFB6C1 value=#FFB6C1>#FFB6C1</option>
				  <option style=background-color:#FFA07A;color:#FFA07A value=#FFA07A>#FFA07A</option>
				  <option style=background-color:#20B2AA;color:#20B2AA value=#20B2AA>#20B2AA</option>
				  <option style=background-color:#87CEFA;color:#87CEFA value=#87CEFA>#87CEFA</option>
				  <option style=background-color:#778899;color:#778899 value=#778899>#778899</option>
				  <option style=background-color:#B0C4DE;color:#B0C4DE value=#B0C4DE>#B0C4DE</option>
				  <option style=background-color:#FFFFE0;color:#FFFFE0 value=#FFFFE0>#FFFFE0</option>
				  <option style=background-color:#00FF00;color:#00FF00 value=#00FF00>#00FF00</option>
				  <option style=background-color:#32CD32;color:#32CD32 value=#32CD32>#32CD32</option>
				  <option style=background-color:#FAF0E6;color:#FAF0E6 value=#FAF0E6>#FAF0E6</option>
				  <option style=background-color:#FF00FF;color:#FF00FF value=#FF00FF>#FF00FF</option>
				  <option style=background-color:#800000;color:#800000 value=#800000>#800000</option>
				  <option style=background-color:#66CDAA;color:#66CDAA value=#66CDAA>#66CDAA</option>
				  <option style=background-color:#0000CD;color:#0000CD value=#0000CD>#0000CD</option>
				  <option style=background-color:#BA55D3;color:#BA55D3 value=#BA55D3>#BA55D3</option>
				  <option style=background-color:#9370DB;color:#9370DB value=#9370DB>#9370DB</option>
				  <option style=background-color:#3CB371;color:#3CB371 value=#3CB371>#3CB371</option>
				  <option style=background-color:#7B68EE;color:#7B68EE value=#7B68EE>#7B68EE</option>
				  <option style=background-color:#00FA9A;color:#00FA9A value=#00FA9A>#00FA9A</option>
				  <option style=background-color:#48D1CC;color:#48D1CC value=#48D1CC>#48D1CC</option>
				  <option style=background-color:#C71585;color:#C71585 value=#C71585>#C71585</option>
				  <option style=background-color:#191970;color:#191970 value=#191970>#191970</option>
				  <option style=background-color:#F5FFFA;color:#F5FFFA value=#F5FFFA>#F5FFFA</option>
				  <option style=background-color:#FFE4E1;color:#FFE4E1 value=#FFE4E1>#FFE4E1</option>
				  <option style=background-color:#FFE4B5;color:#FFE4B5 value=#FFE4B5>#FFE4B5</option>
				  <option style=background-color:#FFDEAD;color:#FFDEAD value=#FFDEAD>#FFDEAD</option>
				  <option style=background-color:#000080;color:#000080 value=#000080>#000080</option>
				  <option style=background-color:#FDF5E6;color:#FDF5E6 value=#FDF5E6>#FDF5E6</option>
				  <option style=background-color:#808000;color:#808000 value=#808000>#808000</option>
				  <option style=background-color:#6B8E23;color:#6B8E23 value=#6B8E23>#6B8E23</option>
				  <option style=background-color:#FFA500;color:#FFA500 value=#FFA500>#FFA500</option>
				  <option style=background-color:#FF4500;color:#FF4500 value=#FF4500>#FF4500</option>
				  <option style=background-color:#DA70D6;color:#DA70D6 value=#DA70D6>#DA70D6</option>
				  <option style=background-color:#EEE8AA;color:#EEE8AA value=#EEE8AA>#EEE8AA</option>
				  <option style=background-color:#98FB98;color:#98FB98 value=#98FB98>#98FB98</option>
				  <option style=background-color:#AFEEEE;color:#AFEEEE value=#AFEEEE>#AFEEEE</option>
				  <option style=background-color:#DB7093;color:#DB7093 value=#DB7093>#DB7093</option>
				  <option style=background-color:#FFEFD5;color:#FFEFD5 value=#FFEFD5>#FFEFD5</option>
				  <option style=background-color:#FFDAB9;color:#FFDAB9 value=#FFDAB9>#FFDAB9</option>
				  <option style=background-color:#CD853F;color:#CD853F value=#CD853F>#CD853F</option>
				  <option style=background-color:#FFC0CB;color:#FFC0CB value=#FFC0CB>#FFC0CB</option>
				  <option style=background-color:#DDA0DD;color:#DDA0DD value=#DDA0DD>#DDA0DD</option>
				  <option style=background-color:#B0E0E6;color:#B0E0E6 value=#B0E0E6>#B0E0E6</option>
				  <option style=background-color:#800080;color:#800080 value=#800080>#800080</option>
				  <option style=background-color:#FF0000;color:#FF0000 value=#FF0000>#FF0000</option>
				  <option style=background-color:#BC8F8F;color:#BC8F8F value=#BC8F8F>#BC8F8F</option>
				  <option style=background-color:#4169E1;color:#4169E1 value=#4169E1>#4169E1</option>
				  <option style=background-color:#8B4513;color:#8B4513 value=#8B4513>#8B4513</option>
				  <option style=background-color:#FA8072;color:#FA8072 value=#FA8072>#FA8072</option>
				  <option style=background-color:#F4A460;color:#F4A460 value=#F4A460>#F4A460</option>
				  <option style=background-color:#2E8B57;color:#2E8B57 value=#2E8B57>#2E8B57</option>
				  <option style=background-color:#FFF5EE;color:#FFF5EE value=#FFF5EE>#FFF5EE</option>
				  <option style=background-color:#A0522D;color:#A0522D value=#A0522D>#A0522D</option>
				  <option style=background-color:#C0C0C0;color:#C0C0C0 value=#C0C0C0>#C0C0C0</option>
				  <option style=background-color:#87CEEB;color:#87CEEB value=#87CEEB>#87CEEB</option>
				  <option style=background-color:#6A5ACD;color:#6A5ACD value=#6A5ACD>#6A5ACD</option>
				  <option style=background-color:#708090;color:#708090 value=#708090>#708090</option>
				  <option style=background-color:#FFFAFA;color:#FFFAFA value=#FFFAFA>#FFFAFA</option>
				  <option style=background-color:#00FF7F;color:#00FF7F value=#00FF7F>#00FF7F</option>
				  <option style=background-color:#4682B4;color:#4682B4 value=#4682B4>#4682B4</option>
				  <option style=background-color:#D2B48C;color:#D2B48C value=#D2B48C>#D2B48C</option>
				  <option style=background-color:#008080;color:#008080 value=#008080>#008080</option>
				  <option style=background-color:#D8BFD8;color:#D8BFD8 value=#D8BFD8>#D8BFD8</option>
				  <option style=background-color:#FF6347;color:#FF6347 value=#FF6347>#FF6347</option>
				  <option style=background-color:#40E0D0;color:#40E0D0 value=#40E0D0>#40E0D0</option>
				  <option style=background-color:#EE82EE;color:#EE82EE value=#EE82EE>#EE82EE</option>
				  <option style=background-color:#F5DEB3;color:#F5DEB3 value=#F5DEB3>#F5DEB3</option>
				  <option style=background-color:#FFFFFF;color:#FFFFFF value=#FFFFFF>#FFFFFF</option>
				  <option style=background-color:#F5F5F5;color:#F5F5F5 value=#F5F5F5>#F5F5F5</option>
				  <option style=background-color:#FFFF00;color:#FFFF00 value=#FFFF00>#FFFF00</option>
				  <option style=background-color:#9ACD32;color:#9ACD32 value=#9ACD32>#9ACD32</option>
				</SELECT>
					<br>
				   <img onclick=rm() src=../../images/forum/img/rm.gif class="gray" alt="插入 Real 音/视频" width=23 height=22>
				   <img onclick=mp() src=../../images/forum/img/wm.gif class="gray" alt="插入 WMP 音/视频" width=23 height=22>
				   <IMG onclick=image() height=22 alt="插入图片" src=../../images/forum/img/image.gif width=23>
				   <!--<img onclick=qt() src=images/qt.gif class="gray" alt="插入 QT 音/视频" width=23 height=22>-->
				   <!--<IMG onclick=viewlycode() height=22 alt="点这里查看 LyBBS 论坛所有的专用标签" src=images/help.gif width=23 class="gray" onmouseover="enable(this)" onmouseout="disable(this)">-->
				   <IMG onclick=bold() height=22 alt="粗体字" src=../../images/forum/img/bold.gif width=23>
				   <IMG onclick=italicize() height=22 alt="斜体字" src=../../images/forum/img/italicize.gif width=23>
				   <IMG onclick=underline() height=22 alt="下划线" src=../../images/forum/img/underline.gif width=23>
				   <IMG onclick=center() height=22 alt="居中" src=../../images/forum/img/center.gif width=23>
				   <!--<IMG onclick=hyperlink() height=22 alt="插入超级链接" src=images/url.gif width=23  class="gray" onmouseover="enable(this)" onmouseout="disable(this)">
				   <IMG onclick=email() height=22 alt="插入邮件地址" src=images/email.gif width=23 class="gray" onmouseover="enable(this)" onmouseout="disable(this)">-->				   
				   <!--<IMG onclick=setswf() height=22 alt="插入 Flash 动画" src=images/swf.gif width=23  class="gray" onmouseover="enable(this)" onmouseout="disable(this)">
				   <IMG onclick=setsound() height=22 alt="插入声音" src=images/sound.gif width=23  class="gray" onmouseover="enable(this)" onmouseout="disable(this)">
				   <IMG onclick=showcode() height=22 alt="插入代码" src=images/code.gif width=23  class="gray" onmouseover="enable(this)" onmouseout="disable(this)">
				   <IMG onclick=quoteme() height=22 alt="插入引用" src=images/quote.gif width=23  class="gray" onmouseover="enable(this)" onmouseout="disable(this)">-->
				   <IMG onclick=setfly() height=22 alt="飞行字" src=../../images/forum/img/fly.gif width=23>
				   <IMG onclick=move() height=22 alt="移动字" src=../../images/forum/img/move.gif width=23>
				   	
				   		<br>
							<!-- -->
				            <html:textarea property="content" rows="15" cols="55"/> 
				            <input type="hidden" name="btnHid">
							<!-- -->
							<br>
				模式:
				<input type="radio" name="mode" value="help" onClick="thelp(1)">
				帮助
				<input type="radio" name="mode" value="prompt" CHECKED onClick="thelp(2)">
				完全
				<input type="radio" name="mode" value="basic"  onClick="thelp(0)">
				基本&nbsp;&nbsp;&nbsp;&nbsp;
				&gt;&gt; <a href=javascript:checklength(document.forumPostBean);>查看文章长度</a>&lt;&lt;
				<br>
                <!-- end -->
                
                
                
                
                
                
                
                
                
                </td>
              </tr>
            <tr>
              <td>&nbsp;</td>
                <td>&nbsp;&nbsp; <label>
                  <input type="button" name="sendpost" value="回复" onclick="answerPost()">
                  &nbsp; 
                  <input type="button" name="Submit" value="预览">
                  &nbsp;
                  <input type="reset" name="clear" value="取消">
                </label></td>
              </tr>
            <tr>
              <td colspan="2" background="../../images/forum/di.jpg">&nbsp;</td>
              </tr>
          </table>
		
		
		
		
		<%--  这里结束  --%>
		        </td>
		      </tr>
		      
		    </table>
		    </td>
		  </tr>
		</table>
		
		
		<%--加--%>

  </html:form>
  </body>
</html:html>
