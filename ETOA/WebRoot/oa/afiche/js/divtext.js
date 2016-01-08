/*-------------------------------------------------------------*
 *DivText�����ű������ж�������DivText��ʹ�õ�ȫ�ֱ����ͺ���
 *������HTML�ĵ��Ŀ�ʼ��������ýű��ļ�,��������:
 * <script language="JavaScript" src="mypath/DIVText/js/divtext.js">
 * Design By Firefox 2006��1�� mcw@chsegang.com.cn MSN: lcsoft@public.ytptt.sd.cn
 * HomePage: http://firefox.ourspower.com/
 *-------------------------------------------------------------*/
//��ѡ�е�HTML�ı�����Ӧ�Ķ���
var g_Range=null;

/*------------------------���ú��������û�����----------------------------------*/
/**********************************************************************************
 * ��ע�������ṩ��������ʽ������ĺ������ֱ����ڸ�ʽ�������������༭���͵���
 * Ϊ�˼򻯲������������CSS���ж��壬Ҫʹ������Դ���CSS��ʽ֮�����ʽ��
 * ��༭/cssĿ¼�µ�css�ļ���Ȼ��ʹ������ĺ�����CSS��ʽӦ����DIVText*
**********************************************************************************/
//����ʽӦ�������༭ȥ
function ftMainDiv(cssid)
{
	var obj=document.getElementById(maindiv);
	obj.className=cssid;
}
//����ʽӦ������������
function ftMainSpan(cssid)
{
	var obj=document.getElementById(mainspan);
	obj.className=cssid;
}

//����ʽӦ���ڵ׹�����
function ftFootSpan(cssid)
{
	var obj=document.getElementById(footspan);
	obj.className=cssid;
}

//�����ʽ���ı�,
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

//�����ʽ���ı�
function RichTextIn(str)
{
	if(chkES()==false){
		document.getElementById(maindiv).innerHTML=str;
	}
	else{
		document.getElementById(editTextid).value=str;
	}
}

/*--------------��������¼�------------------------------------------*/
//�����ҳ���ϵ���
var docclick=function doc_onclick()
{
		//��ȡ�������Ķ���
		var obj=event.srcElement;
		//���ظ�ʽ���Ĳ�
		hid_format_div(obj);
	
	if(chkES()==false){
		//�ڱ༭������
		if(document.getElementById(maindiv).contains(obj)==true){
			var oRange=document.selection.createRange();
			g_Range=oRange;		//���浱ǰѡ��������
			if(oRange!=null){
				testBIU(oRange);
			}//testBIU...
		}else if(document.getElementById(mainspan).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){//�ڸ�ʽ����������
			//��ʽ��
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
		}else if(document.getElementById(fgColorid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){	//�ڱ�����ɫ�Ի��򵥻�
			setColor("BackColor",obj.id);
			viewselection();
		}else if(document.getElementById(frColorid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){ //��������ɫ�Ի����е���
			setColor("ForeColor",obj.id);
			viewselection();
		}else if(document.getElementById(ftNameid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){	//��������ѡ���
			setFont("FontName",obj.id);
			viewselection();
		}else if(document.getElementById(ftSizeid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){	//���������Сѡ���
			setFont("FontSize",obj.id);
			viewselection();
		}else if(document.getElementById(Paragraphid).contains(obj)==true && obj.tagName=="LABEL" && obj.id!=""){  //���ö����ʽ
			setFont("FormatBlock",obj.id);
			viewselection();
		}else if(document.getElementById(Smileid).contains(obj)==true && obj.tagName=="IMG" && obj.id!=""){		//��������ͼƬ
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

/*-----------����Span��ť/��ɫ�Ի���ť/�����ֺŶԻ���ĸ�ʽ--------------------------------------*/
//����ƶ�������Ի����ϵ�ʱ��
//ͻ����ʾ��ǰѡ�������
var ftnmouseover=function ftname_div_mouseover()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="LABEL" && obj.id!=""){
		obj.style.border="1px solid gray";
	}
}

//������Ƴ���ѡ�������
//�ָ�������ʾ
var ftnmouseout=function ftname_div_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="LABEL" && obj.id!=""){
		obj.style.border="1px none gray";
	}
}

//����ƶ�����ɫ�Ի����ϵ�ʱ��
//ͻ����ʾ��ǰ����ɫ��
var colormouseover=function color_div_mouseover()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.border="1px solid gray";
	}
}

//����Ƴ���ɫ�Ի������ɫ���ʱ��
//�ָ�ɫ���������ʾ
var colormouseout=function color_div_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		obj.style.border="0.4px none gray";
	}
}

//����ƶ���Span�ϵ�ʱ��
//����ʽ����ťͻ����ʾ
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

//����Ƴ�Span�ϵ�Ԫ�ص�ʱ��
//ͻ����ʾ�İ�ť�ָ�����
var spanmouseout=function span_mouseout()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG"){
		obj.style.border="1px none #000000";
	}
}

//�����Span�ϵ�Ԫ�ص�ʱ��
//�ı���ʾ��ť�����
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

//�����Span���ͷ�
//�ı䰴ť��۲����ø�ʽ������
var spanmouseup=function span_mouseup()
{
	var obj=event.srcElement;
	if(obj!=null && obj.tagName=="IMG" && obj.id!=""){
		//�ı����
		obj.style.borderTop="1px none #000000";
		obj.style.borderLeft="1px none #000000";
		obj.style.borderRight="1px solid black";
		obj.style.borderBottom="1px solid black";
	}
}

/*--------------�����Ǹ�ʽ������-----------------------------------------------------------------*/
//���ô��� б�� �»���
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

//���ö���λ��
function setJust(cmdname)
{
	if(g_Range!=null){
		var testContain=document.getElementById(maindiv).contains(g_Range.parentElement());
		if(testContain==true){
			g_Range.execCommand(cmdname);
		}
	}
}

//�������壬�ֺ�
function setFont(cmdname,value)
{
	if(g_Range!=null){
		var testContain=document.getElementById(maindiv).contains(g_Range.parentElement());
		if(testContain==true){
			g_Range.execCommand(cmdname,false,value);
		}
	}
}

//������ɫ
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

//��ʾ���������ı���
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

/*---------------�����Ǽ���ʽ������----------------------------------------------------*/
//��ⱻѡ�е������Ƿ������˴��� б����»���
//�Լ��ö����Ƿ���С�����롢�Ҷ���
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

/*--------------------------------����---------------------------------------------------*/
//���ظ�ʽ��DIV
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
	//ר�Ŵ���ͼƬURL�Ի���
	var obj=document.getElementById(Pictureid);
	if(obj.contains(eventobj)!=true || eventobj.id=="dt_btn"){
		obj.style.visibility="hidden";
	}
}

//��ʾ��ǰѡ������
function viewselection()
{
	document.getElementById(maindiv).focus();
	if(g_Range!=null){
		g_Range.select();
		testBIU(g_Range);
	}
}

//ȷ���༭״̬
function chkES()
{
	var vstate=document.getElementById("dt_check").checked;
	return vstate;
}

/*-------------------��ʽ����DIV ��ʽ����ťSPAN  ��ɫ�Ի��� �����ֺŶԻ���----------------*/
//�������༭��
function createMainDiv(obj)
{
	obj.contentEditable="true";		//����Ϊ�ɱ༭
	obj.className="notepaper";		//��ʼ����ʽ��
	obj.zIndex=1;
	obj.innerHTML="<DIV></DIV>";	
}

//������ʽ��SPAN��
function createFormatSpan(obj)
{
	var i=0;
	var Imglist=new Array();
	
	//����
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/paragraph.gif";
	Imglist[i].id="paragraph";
	Imglist[i].title="���ö����ʽ";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//����������
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/ftname.gif";
	Imglist[i].id="ftname";
	Imglist[i].title="ѡ������";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//�����С������
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/ftsize.gif";
	Imglist[i].id="ftsize";
	Imglist[i].title="ѡ���ֺ�";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
		
	//�ָ���
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//����
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/bold.gif";
	Imglist[i].id="bold";
	Imglist[i].title="����";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//б��
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/italic.gif";
	Imglist[i].id="italic";
	Imglist[i].title="б��";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//�»���
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/underline.gif";
	Imglist[i].id="underline";
	Imglist[i].title="�»���";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//�ָ���
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//���Ҷ��������
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/aleft.gif";
	Imglist[i].id="aleft";
	Imglist[i].title="�����";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/center.gif";
	Imglist[i].id="center";
	Imglist[i].title="����";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/aaright.gif";
	Imglist[i].id="aright";
	Imglist[i].title="�Ҷ���";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";Imglist[i].style.width="16px";
	i++;
	
	//�ָ���
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//������ɫ��������ɫ
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/fgcolor.gif";
	Imglist[i].id="fgcolor";
	Imglist[i].title="������ɫ";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/frcolor.gif";
	Imglist[i].id="frcolor";
	Imglist[i].title="������ɫ";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//����
	Imglist[i]=document.createElement("<hr>");
	Imglist[i].style.display ="inline"
	i++;
	
	//���С����ƺ�ճ��
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/cut.gif";
	Imglist[i].id="cut";
	Imglist[i].title="����";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/copy.gif";
	Imglist[i].id="copy";
	Imglist[i].title="����";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/paste.gif";
	Imglist[i].id="paste";
	Imglist[i].title="ճ��";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	//����б����Ŀ�����б�
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/numberlist.gif";
	Imglist[i].id="numberlist";
	Imglist[i].title="����б�";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/itemlist.gif";
	Imglist[i].id="itemlist";
	Imglist[i].title="��Ŀ�����б�";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/hr.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//������ߡ�������ź�ͼƬ
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/line.gif";
	Imglist[i].id="line";
	Imglist[i].title="�������";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/smile.gif";
	Imglist[i].id="smile";
	Imglist[i].title="����������";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;

	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/blank.gif";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	Imglist[i]=new Image;
	Imglist[i].src=abspath+"img/picture.gif";
	Imglist[i].id="picture";
	Imglist[i].title="����ͼƬ";
	Imglist[i].style.position="relative";Imglist[i].style.top="2px";Imglist[i].style.height="16px";
	i++;
	
	//��Ԫ�ز���SPAN
	for(i=0;i<Imglist.length;i++){
		obj.insertAdjacentElement("beforeEnd",Imglist[i]);
	}
	
	//�����ʽ����ť���ĸ�ʽ
	obj.className="spancss";
	
	//�����ʽ����ť�����¼�
	obj.onmouseover=spanmouseover;
	obj.onmouseout=spanmouseout;
	obj.onmousedown=spanmousedown;
	obj.onmouseup=spanmouseup;
}

//���������ĺ���
function createFootSpan(obj)
{
	obj.className="footspancss";
	str="<input type='checkbox' id='dt_check' value='1'><font size='2' face='����'>��ʾ/����HTML����</font>";
	obj.innerHTML=str;
}
//�����������öԻ���
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
	//��������������Ŀ
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<div>'>");
	arr_span[0].innerHTML="����";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<ADDRESS>'>");
	arr_span[1].innerHTML="<ADDRESS>��ַ</ADDRESS>";
	arr_span[2]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H1>'>");
	arr_span[2].innerHTML="<H1>����1</H1>";	
	arr_span[3]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H2>'>");
	arr_span[3].innerHTML="<H2>����2</H2>";	
	arr_span[4]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px' id='<H3>'>");
	arr_span[4].innerHTML="<H3>����3</H3>";	
	for(i=0;i<arr_span.length;i++){
		oPara.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//���������Сѡ��Ի���
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
	//���������Сѡ���
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px;font-size:xx-small' id='1'>");
	arr_span[0].innerHTML="һ��";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:x-small' id='2'>");
	arr_span[1].innerHTML="����";
	arr_span[2]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:small' id='3'>");
	arr_span[2].innerHTML="����";
	arr_span[3]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:medium' id='4'>");
	arr_span[3].innerHTML="�ĺ�";
	arr_span[4]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:large' id='5'>");
	arr_span[4].innerHTML="���";
	arr_span[5]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:x-large' id='6'>");
	arr_span[5].innerHTML="����";
	arr_span[5]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-size:xx-large' id='7'>");
	arr_span[5].innerHTML="�ߺ�";
	for(i=0;i<arr_span.length;i++){
		oftSize.insertAdjacentElement("beforeEnd",arr_span[i]);
	}
}

//������������ѡ��Ի���
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
	//��������SPAN
	var arr_span=new Array();
	arr_span[0]=document.createElement("<LABEL style='position:relative;top:2px;left:2px;width:128px;font-family:����' id='����'>");
	arr_span[0].innerHTML="����";	
	arr_span[1]=document.createElement("<LABEL style='position:relative;top:4px;left:2px;width:128px;font-family:����' id='����'>");
	arr_span[1].innerHTML="����";
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

//������ɫѡ��Ի���
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
	//������ɫspan
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

//����������ŶԻ���
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
	
	//��������б�
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

//����ͼƬURL�Ի���
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
	
	//�Ի���
	var str="";
	str=str+"<table width='74' align='center'>";
	str=str+"<tr>";
	str=str+"<td colspan='3'><font size='2' face='����'>������Ҫ����ͼƬ��URL��</font></td>";
	str=str+"</tr><tr>";
	str=str+"<td width='7%'>&nbsp;</td>";
	str=str+"<td width='73%'><input name='dt_text' type='text' id='dt_text' size='30'></td>";
    str=str+"<td width='20%'><input name='dt_btn' type='button' id='dt_btn' value='ȷ��'></td>"; 
    str=str+"</tr></table>";
	oPicture.innerHTML=str;
}

//�����༭��
function createEditText(vid)
{
	document.write("<textarea id='"+vid+"'></textarea>");
	var obj=document.getElementById(vid);
	obj.style.position="absolute";
	obj.style.top=0;obj.style.left=0;
	obj.style.zIndex=2;
	obj.style.display="none";
}