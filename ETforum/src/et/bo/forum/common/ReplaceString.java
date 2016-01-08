/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.forum.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类ReplaceString.java的实现描述：TODO 类实现描述 
 *
 * @author         terry
 * @version        1.0
 * Date            2005-10-19
 * @see            java.lang.Class
 * History:
 *    <author>     <time>      <version>      <desc>
 */
public class ReplaceString {
	private int MaxWidth=500;
	private int MaxHeight=500;
	//private String path = com.hl.love.url.UrlParseXml.getApacheUrl("bbs");
	public String ReplaceStr(String source, String flag)
	{
		String str =source;		
		if(!"html".equalsIgnoreCase(flag))
		{
			
			Translate transl =new Translate();
			
			str =transl.inHTML(str);
			
		}
		
		str =replaceFormate(str);
		
		//str =ReplaceFace(str);
		
		str =ReplaceBIU(str);
		str =ReplaceAlign(str);
		str =ReplaceFontSize(str);
		str =ReplaceImg(str);
		str =ReplaceColor(str);
		str =ReplaceFont(str);
		str =ReplaceEdit(str);
		str =ReplaceFly(str);
		str =ReplaceMove(str);
		str =ReplaceQuote(str);
		str =ReplaceRm(str);
		str =ReplaceWmv(str);
		str =ReplaceSwf(str);
		return str;
	}
	
//	private String path ="http://bbs.9i5i.com/emot/";
	/**
	 * 替换表情
	 */
//	private String ReplaceFace(String source)
//	{
//		String regEx="\\[em=[0-9]+\\]";//匹配所有""形式的字符串
//		//暂存source
//		String str=source;
//		Pattern p=Pattern.compile(regEx);
//        Matcher m=p.matcher(str);
//        ArrayList arrlist =new ArrayList();
//        
//        while(m.find())
//        {
//       	 //获得子串
//       	 int start =m.start();
//       	 int end =m.end();
//       	 String substr =str.substring(start,end);
//       	 //获得数字
//       	 String tmpstr =FindNum(substr);
//       	 arrlist.add(tmpstr);
//        }
//        Iterator itor =arrlist.iterator();
//        while(itor.hasNext())
//        {
//       	 String rtx =itor.next().toString();
//       	 str =str.replaceAll("\\[em="+rtx+"\\]","<img src="+path+"emot/em"+rtx+".gif />");
//        }
//
//        return str;
//       
//	}
	public String ReplaceT(String source)
	{
//		String regEx="\\[t=[0-9]+,[0-9]+\\].+?\\[/t\\]";
		String regEx="http://.+/?";
		String str=source;
		Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        while(m.find())
        {
        	int start=m.start();
        	int end =m.end();
        	String substr =source.substring(start,end);
        	System.out.println("substr \n"+substr);
        	List sl =FindNum(substr,"");
        	int width =380;
        	int height =30;
        	int widthF=380;
        	int heightF=70;
//        	System.out.println("size "+sl.size());
//        	for(int i=0;i<sl.size();i++)
//        	{
//        		System.out.println(sl.get(i));
//        	}
        	if(sl!=null && sl.size()>=2)
        	{
        		widthF =new Integer((String)sl.get(0));
        		heightF =new Integer((String)sl.get(1));
//        		System.out.println(widthF);
//        		System.out.println(heightF);
        		if(widthF<500 && widthF>0)
        			width =widthF;
        		if(heightF<500 && heightF>0)
        			height=heightF;
//        		System.out.println(width);
//        		System.out.println(height);
        		
        	}
//        	System.out.println("here is mediaplay "+width+"  "+height);
//        	System.out.println("111111111111 "+sl.toString());
//        	System.out.println(substr);
        	String mediaString =getMedia(substr);
//        	System.out.println(mediaString);
//        	String objectString ="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"+width+"' height='"+height+"'><param name='movie' value='"+mediaString+"'> <param name='quality' value='high'><embed src='"+mediaString+"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"+width+"' height='"+height+"'></embed></object>";
//        	String objectString ="<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width="+width+" height="+height+"><PARAM NAME=SRC VALUE="+mediaString+"><PARAM NAME=CONSOLE VALUE=Clip1><PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=0></OBJECT><br><OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA width="+width+" height=32 id=video2><PARAM NAME=SRC VALUE=rtsp://211.93.137.51:554/resource/政治理论/新时期、新阶段保持党员先进性的基本要求.rm><PARAM NAME=AUTOSTART VALUE=1><PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE=Clip1></OBJECT>";
        	String objectString ="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width="+width+" height="+height+"><param name=movie value="+mediaString+"><param name=quality value=high><embed src="+mediaString+" pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width="+width+" height="+height+"></embed></object>";
//        	System.out.println(objectString);
        	str =source.replaceAll("\\[swf="+widthF+","+heightF+"\\]"+mediaString+"\\[/swf\\]",objectString);
        	
        }
		
		return str;
	}
	public String ReplaceSwf(String source)
	{
		String regEx="\\[swf=[0-9]+,[0-9]+\\].+\\[/swf\\]";
		String str=source;
		Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        while(m.find())
        {
        	int start=m.start();
        	int end =m.end();
        	String substr =source.substring(start,end);
        	List sl =FindNum(substr,"");
        	int width =380;
        	int height =30;
        	int widthF=380;
        	int heightF=70;
//        	System.out.println("size "+sl.size());
//        	for(int i=0;i<sl.size();i++)
//        	{
//        		System.out.println(sl.get(i));
//        	}
        	if(sl!=null && sl.size()>=2)
        	{
        		widthF =new Integer((String)sl.get(0));
        		heightF =new Integer((String)sl.get(1));
//        		System.out.println(widthF);
//        		System.out.println(heightF);
        		if(widthF<500 && widthF>0)
        			width =widthF;
        		if(heightF<500 && heightF>0)
        			height=heightF;
//        		System.out.println(width);
//        		System.out.println(height);
        		
        	}
//        	System.out.println("here is mediaplay "+width+"  "+height);
//        	System.out.println("111111111111 "+sl.toString());
//        	System.out.println(substr);
        	String mediaString =getMedia(substr);
//        	System.out.println(mediaString);
//        	String objectString ="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"+width+"' height='"+height+"'><param name='movie' value='"+mediaString+"'> <param name='quality' value='high'><embed src='"+mediaString+"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"+width+"' height='"+height+"'></embed></object>";
//        	String objectString ="<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width="+width+" height="+height+"><PARAM NAME=SRC VALUE="+mediaString+"><PARAM NAME=CONSOLE VALUE=Clip1><PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=0></OBJECT><br><OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA width="+width+" height=32 id=video2><PARAM NAME=SRC VALUE=rtsp://211.93.137.51:554/resource/政治理论/新时期、新阶段保持党员先进性的基本要求.rm><PARAM NAME=AUTOSTART VALUE=1><PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE=Clip1></OBJECT>";
        	String objectString ="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width="+width+" height="+height+"><param name=movie value="+mediaString+"><param name=quality value=high><embed src="+mediaString+" pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width="+width+" height="+height+"></embed></object>";
//        	System.out.println(objectString);
        	str =source.replaceAll("\\[swf="+widthF+","+heightF+"\\]"+mediaString+"\\[/swf\\]",objectString);
        	
        }
		
		return str;
	}
	public String ReplaceWmv(String source)
	{
		String regEx="\\[mp=[0-9]+,[0-9]+\\].+\\[/mp\\]";
		String str=source;
		Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        while(m.find())
        {
        	int start=m.start();
        	int end =m.end();
        	String substr =source.substring(start,end);
        	List sl =FindNum(substr,"");
        	int width =380;
        	int height =30;
        	int widthF=380;
        	int heightF=70;
//        	System.out.println("size "+sl.size());
//        	for(int i=0;i<sl.size();i++)
//        	{
//        		System.out.println(sl.get(i));
//        	}
        	if(sl!=null && sl.size()>=2)
        	{
        		widthF =new Integer((String)sl.get(0));
        		heightF =new Integer((String)sl.get(1));
//        		System.out.println(widthF);
//        		System.out.println(heightF);
        		if(widthF<500 && widthF>0)
        			width =widthF;
        		if(heightF<500 && heightF>0)
        			height=heightF;
//        		System.out.println(width);
//        		System.out.println(height);
        		
        	}
//        	System.out.println("here is mediaplay "+width+"  "+height);
//        	System.out.println("111111111111 "+sl.toString());
//        	System.out.println(substr);
        	String mediaString =getMedia(substr);
//        	System.out.println(mediaString);
        	String objectString ="<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' width="+width+" height="+height+"><param name=Filename value="+mediaString+"><param name='BufferingTime' value='5'><param name='AutoSize' value='-1'><param name='AnimationAtStart' value='-1'><param name='AllowChangeDisplaySize' value='-1'><param name='ShowPositionControls' value='0'><param name='TransparentAtStart' value='1'><param name='ShowStatusBar' value='1'></object>";
//        	String objectString ="<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width="+width+" height="+height+"><PARAM NAME=SRC VALUE="+mediaString+"><PARAM NAME=CONSOLE VALUE=Clip1><PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=0></OBJECT><br><OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA width="+width+" height=32 id=video2><PARAM NAME=SRC VALUE=rtsp://211.93.137.51:554/resource/政治理论/新时期、新阶段保持党员先进性的基本要求.rm><PARAM NAME=AUTOSTART VALUE=1><PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE=Clip1></OBJECT>";
//        	System.out.println(objectString);
        	str =source.replaceAll("\\[mp="+widthF+","+heightF+"\\]"+mediaString+"\\[/mp\\]",objectString);
        	
        }
		
		return str;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	public String ReplaceRm(String source)
	{
		String regEx="\\[rm=[0-9]+,[0-9]+\\].+\\[/rm\\]";
		String str=source;
		Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        while(m.find())
        {
        	int start=m.start();
        	int end =m.end();
        	String substr =source.substring(start,end);
        	List sl =FindNum(substr,"");
        	int width =380;
        	int height =30;
        	int widthF=380;
        	int heightF=70;
//        	System.out.println("size "+sl.size());
//        	for(int i=0;i<sl.size();i++)
//        	{
//        		System.out.println(sl.get(i));
//        	}
        	if(sl!=null && sl.size()>=2)
        	{
        		widthF =new Integer((String)sl.get(0));
        		heightF =new Integer((String)sl.get(1));
//        		System.out.println(widthF);
//        		System.out.println(heightF);
        		if(widthF<500 && widthF>0)
        			width =widthF;
        		if(heightF<500 && heightF>0)
        			height=heightF;
//        		System.out.println(width);
//        		System.out.println(height);
        		
        	}
//        	System.out.println(width+"  "+height);
//        	System.out.println("111111111111 "+sl.toString());
//        	System.out.println(substr);
        	String mediaString =getMedia(substr);
//        	System.out.println(mediaString);
        	String objectString ="<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+width+" height="+height+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+mediaString+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+width+" height=40><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>";
//        	String objectString ="<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width="+width+" height="+height+"><PARAM NAME=SRC VALUE="+mediaString+"><PARAM NAME=CONSOLE VALUE=Clip1><PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=0></OBJECT><br><OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA width="+width+" height=32 id=video2><PARAM NAME=SRC VALUE=rtsp://211.93.137.51:554/resource/政治理论/新时期、新阶段保持党员先进性的基本要求.rm><PARAM NAME=AUTOSTART VALUE=1><PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE=Clip1></OBJECT>";
//        	System.out.println(objectString);
        	str =source.replaceAll("\\[rm="+widthF+","+heightF+"\\]"+mediaString+"\\[/rm\\]",objectString);
        	
        }
		
		return str;
	}
	private String getMedia(String source)
	{
		String regEx="\\].+\\[";
		String str =source;
		Pattern p =Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		if(m.find())
		{
			int start =m.start()+1;
			int end =m.end()-1;
			String substr =source.substring(start,end);
//			System.out.println(substr);
			str =substr;
		}		
		return str;
	}
	private String ReplaceColor(String source)
	{
		String str =source;
		String regEx ="\\[color=#[0-9A-Fa-f]{6}\\]";
		Pattern p =Pattern.compile(regEx);
		Matcher m =p.matcher(str);
		ArrayList arrlist =new ArrayList();
		
		while(m.find())
		{
			int start =m.start();
			int end =m.end();
			String substr =str.substring(start,end);
//			System.out.println(substr);
			String tmpstr =FindColor(substr);
			
			arrlist.add(tmpstr);
		}
		Iterator itor =arrlist.iterator();
		while(itor.hasNext())
		{
			String rtx =itor.next().toString();
			str =str.replaceAll("\\[color=#"+rtx+"\\]","<font color=#"+rtx+">");
		}
		str =str.replaceAll("\\[/color\\]","</font>");
		return str;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	private String ReplaceFontSize(String source)
	{
		String regEx ="\\[size=[0-9]\\]";
		String str =source;
		Pattern p =Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		ArrayList arrlist =new ArrayList();
		
		while(m.find())
        {
       	 //获得子串
       	 int start =m.start();
       	 int end =m.end();
       	 String substr =str.substring(start,end);
       	 //获得数字
       	 String tmpstr =FindNum(substr);
       	 arrlist.add(tmpstr);
        }
        Iterator itor =arrlist.iterator();
        while(itor.hasNext())
        {
       	 String rtx =itor.next().toString();
       	 str =str.replaceAll("\\[size="+rtx+"\\]","<font size="+rtx+">");
        }
		str =str.replaceAll("\\[/size\\]","</font>");
		return str;
	}
	/**
	 * 替换字体
	 * @param source
	 * @return
	 */
	private String ReplaceFont(String source)	
	{
		String regEx ="\\[face=[\u4e00-\u9fa5a-zA-Z]+\\]";
		String str =source;
		Pattern p =Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		ArrayList arrlist =new ArrayList();
		
		while(m.find())
        {
	       	 //获得子串
	       	 int start =m.start();
	       	 int end =m.end();
	       	 String substr =str.substring(start,end);
	       	 //获得数字
	       	 String tmpstr =FindFont(substr);
	       	 System.out.println(tmpstr);
	       	 arrlist.add(tmpstr);
        }
        Iterator itor =arrlist.iterator();
        while(itor.hasNext())
        {
	       	 String rtx =itor.next().toString();
	       	 str =str.replaceAll("\\[face="+rtx+"\\]","<font face="+rtx+">");
        }
		str =str.replaceAll("\\[/face\\]","</font>");
		return str;
	}
	
	private String ReplaceImg(String source)
	{
		String str =source;
		str =str.replaceAll("\\[img\\]","<img src=");
		str =str.replaceAll("\\[\\/img\\]"," border=\"0\" onload=\"if(this.width>screen.width*0.42) {this.width=screen.width*0.42;}\"/>");
		return str;
	}
	
	
	/**
	 * 替换B I U
	 * @param source
	 * @return
	 */
	private String ReplaceBIU(String source)
	{
		String str=source;
		str =str.replaceAll("\\[b\\]","<b>");
		str =str.replaceAll("\\[\\/b\\]","</b>");
		str =str.replaceAll("\\[i\\]","<i>");
		str =str.replaceAll("\\[\\/i\\]","</i>");
		str =str.replaceAll("\\[u\\]","<u>");
		str =str.replaceAll("\\[\\/u\\]","</u>");
		
		return str;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	private String ReplaceFly(String source)
	{
		String str=source;
		str =str.replaceAll("\\[fly\\]","<marquee behavior=\"alternate\">");
		str =str.replaceAll("\\[\\/fly\\]","</marquee>");
		return str;
	}
	private String ReplaceMove(String source)
	{
		String str =source;
		str =str.replaceAll("\\[move\\]","<marquee>");
		str =str.replaceAll("\\[\\/move\\]","</marquee>");
		return str;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	private String ReplaceQuote(String source)
	{
		String str=source;
		str =str.replaceAll("\\[quote\\]","<div class=quote>");
		str =str.replaceAll("\\[\\/quote\\]","</div>");
		return str;
	}
	
	/**
	 * 替换修改标签
	 * @param source
	 * @return
	 */
	private String ReplaceEdit(String source)
	{
		String str =source;
		str =str.replaceAll("\\[edit\\]","<br><div align=right><font color=#000066>");
		str =str.replaceAll("\\[\\/edit\\]","</font></div>");
		return str;
	}
	private String ReplaceAlign(String source)
	{
		String str =source;
		str =str.replaceAll("\\[align=center\\]","<div align='center'>");
		str =str.replaceAll("\\[\\/align\\]","</div>");
		return str;
	}
	
	/**
	 * find number in the sou
	 * @param sou
	 * @return
	 */
	private static String FindNum(String sou)
	{
		String regEx="[0-9]+";
		Pattern p =Pattern.compile(regEx);
		Matcher m =p.matcher(sou);
		if(m.find())
		{
			int start =m.start();
			int end =m.end();
			sou =sou.substring(start,end);
		}
		return sou;
	}
	private List FindNum(String sou,String a)
	{
		String regEx="[0-9]+";
		Pattern p =Pattern.compile(regEx);
		Matcher m =p.matcher(sou);
		List sl =new ArrayList();
		while(m.find())
		{
			int start =m.start();
			int end =m.end();
			String tmpString =sou.substring(start,end);
			sl.add(tmpString);
		}
		return sl;
	}
	private static String FindColor(String sou)
	{
		String regEx="#[0-9a-fA-F]{6}";
		Pattern p =Pattern.compile(regEx);
		Matcher m =p.matcher(sou);
		if(m.find())
		{
			int start =m.start()+1;
			int end =m.end();
			sou =sou.substring(start,end);
		}
		return sou;
	}
	private static String FindFont(String sou)
	{
		String regEx ="=[\u4e00-\u9fa5a-zA-Z]+";
		Pattern p =Pattern.compile(regEx);
		Matcher m =p.matcher(sou);
		if(m.find())
		{
			int start =m.start()+1;
			int end =m.end();
			sou =sou.substring(start,end);
		}
		return sou;
	}
	
	public String replaceNull(Object obj)
	{
		int tmp =0;
		if(obj != null)
		{
			return obj.toString();
		}
		return tmp+"";
	}
	
	public String replaceStringNull(Object obj)
	{
		String tmp ="";
		if(obj !=null)
		{
			return obj.toString();
		}
		return tmp;
	}
	public String replaceStringNull(Object obj, String img)
	{
		String tmp ="http://reg.9i5i.com/images/photo.jpg";
		if(obj !=null)
		{
			return obj.toString();
		}
		return tmp;
	}
	public String replaceFormate(String sou)
	{
		String dos =sou.replaceAll("\r","<br>");
//		dos =dos.replaceAll(" ","&nbsp;");
		return dos;
	}
	
	public static void main(String[] args) 
	{

		String str=" g[face=eee]gggggg[face=新宋体]文字[/face] ggghttp://111.com/ g http://222.com/ g[mp=400,500]ffffeffff[/mp]gggg[t=380,70]eeeffeeee[/t]g[t=380,70]gggeeggg[/t][color=#B8860B]ffffffff[/color]ff[size=6]eeeeeeeeee[/size]fff[em=02] abb [em=11] baaa [em=33] ban[size=3]an [em=22] ah[img]http://news.sina.com.cn[/img]h[b]aa[/b]na";
//		String str ="[b]fffffffffffff[/b]";

		ReplaceString rs =new ReplaceString();
		System.out.println("rrrrrrrrrrrrr \n"+rs.ReplaceT(str));
		
//             String regEx="\\[size=[0-9]+\\]";//匹配所有""形式的字符串
//             System.out.println(str =rs.ReplaceColor(str));
//             System.out.println(rs.ReplaceBIU(str));
//             System.out.println(rs.ReplaceFont(str));
/*		String str1=null;
		try
		{
             str1 =rs.ReplaceStr("","");
		}catch(Throwable t)
		{
			t.printStackTrace();
		}
             System.out.println(str1+"1");
             
/*
             String str=" [em=22] abb [em=11] baaa [em=33] banan [em=22] ahhaana";
             Pattern p=Pattern.compile(regEx);
             Matcher m=p.matcher(str);
             
             ArrayList arrlist =new ArrayList();
//             int i =0;
             while(m.find())
             {
            	 //获得子串
            	 int start =m.start();
//            	 System.out.println("start "+start);
            	 int end =m.end();
            	 String substr =str.substring(start,end);
            	 //获得数字
            	 String tmpstr =FindNum(substr);
            	 arrlist.add(tmpstr);
//            	 System.out.println(str.replaceAll("\\[em="+tmpstr+"\\]","<a href="+tmpstr+".gif>"));
//            	 System.out.println(tmpstr);
//            	 System.out.println(substr);
//            	 System.out.println(i);
            	 
//            	 System.out.println(i);
             }
             Iterator itor =arrlist.iterator();
             while(itor.hasNext())
             {
            	 String rtx =itor.next().toString();
            	 str =str.replaceAll("\\[em="+rtx+"\\]","<a href="+rtx+".gif>");
             }
             System.out.println(str);


            

//             System.out.println(s);

*/
	}

}
