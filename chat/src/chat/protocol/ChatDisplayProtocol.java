package chat.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author yifei zhao
 * 聊天室内容显示协议，替换标签。图片标签，html标签，内容标签。
 *
 */
public class ChatDisplayProtocol {
	static String userFont="华文中宋";
	static String userColor="#CC0033";
	static String fellingFont="华文中宋";
	static String fellingColor="#33FFFF";
	static String broadcastFont="";
	static String broadcastColor="";
	static String warnFont="";
	static String warnColor="";
	static String cueFont="";
	static String cueColor="";
	
	public ChatDisplayProtocol()
	{
		super();
	}
	public ChatDisplayProtocol(ChatTransportProtocol ctp)
	{
		this.ctp=ctp;
	}

	
	private ChatTransportProtocol ctp=null;
	/**
	 * 聊天的人
	 */
	private String master;
	/**
	 * 图片标签替换对应map
	 */
	private Map<String, String> displaceImage=new HashMap<String,String>();
	
	/**
	 * 阻塞用户
	 */
	private List<String> stopUsers=new ArrayList<String>();


	public Map<String,String> getDisplaceImage() {
		return displaceImage;
	}


	public void setDisplaceImage(Map<String,String> displaceImage) {
		this.displaceImage = displaceImage;
	}


	public String getMaster() {
		return master;
	}


	public void setMaster(String master) {
		this.master = master;
	}
	/**
	 * 设置传入内容
	 */
	public void setTransportContent(ChatTransportProtocol ctp)
	{
		this.ctp=ctp;
	}
	/**
	 * 返回格式化的内容
	 */
	public String getDisplayString()
	{
		if(!ctp.getActionType().equals(ctp.ACTION_TYPE_MESSAGE))
		return null;
		StringBuffer con=new StringBuffer();
		String actionType=ctp.getActionType();
		String messageType=ctp.getChatType();
		if(!actionType.equals(ctp.ACTION_TYPE_MESSAGE))
		{
			return null;
		}
		if(messageType.equals(ctp.CHAT_TYPE_PRIVATE)||messageType.equals(ctp.CHAT_TYPE_PUBLIC))
		{
		/*
		 * [font][color][felling]这些是聊天内容
		 */
		String font;
		String color;
		String felling;
		String content=ctp.getContent();
		int begin=content.indexOf('[');
		int end=content.indexOf(']');
		font=content.substring(begin+1,end);
		content=content.substring(end+1);
		begin=content.indexOf('[');
		end=content.indexOf(']');
		color=content.substring(begin+1,end);
		content=content.substring(end+1);
		begin=content.indexOf('[');
		end=content.indexOf(']');
		felling=content.substring(begin+1,end);
		content=content.substring(end+1);
		con.append(formatUser("",felling));
		con.append(replaceImage(formatFontColor(font,color,content)));
		}
		if(messageType.equals(ctp.CHAT_TYPE_BROADCAST))
		{
			String content=ctp.getContent();
			con.append(formatFontColor(broadcastFont,broadcastColor,content));
		}
		if(messageType.equals(ctp.CHAT_TYPE_SYSTEM_CUE))
		{
			String content=ctp.getContent();
			con.append(formatFontColor(cueFont,cueColor,content));
		}
		if(messageType.equals(ctp.CHAT_TYPE_SYSTEM_WARN))
		{
			String content=ctp.getContent();
			con.append(formatFontColor(warnFont,warnColor,content));
		
		}
		return con.toString();
	}
	
	/**
	 * 返回内容类型
	 * @return
	 */
	public String getDisplayType()
	{
		return ctp.getChatType();
	}
	private String formatUser(String user,String felling)
	{
		//<u><font face="华文中宋" color="#CC0033"></font></u>
		StringBuffer sb=new StringBuffer();
		sb.append("<u><font face=\"");
		sb.append(userFont);
		sb.append("\" color=\"");
		sb.append(userColor);
		sb.append("\">");
		if(user.equals(ctp.getSenderName()))
			sb.append("你");
		else
			sb.append(ctp.getSenderName());
		sb.append("</font></u>");
		sb.append("对");
		sb.append("<u><font face=\"");
		sb.append(userFont);
		sb.append("\" color=\"");
		sb.append(userColor);
		sb.append("\">");
		if(user.equals(ctp.getReceiverName()))
			sb.append("你");
		else
			sb.append(ctp.getReceiverName());
		sb.append("</font></u>");
		sb.append("<font face=\"");
		sb.append(fellingFont);
		sb.append("\" color=\"");
		sb.append(fellingColor);
		sb.append("\">");
		sb.append(felling);
		sb.append("</font>");
		sb.append("说：");
		return sb.toString();
	}
	private String formatFontColor(String font,String color,String content)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("<font face=\"");
		sb.append(font);
		sb.append("\" color=\"");
		sb.append(color);
		sb.append("\">");
		sb.append(content);
		sb.append("</font>");
		
		return sb.toString();
	}
	private String replaceImage(String source)
	{
		Set keys=displaceImage.keySet();
		Iterator i=keys.iterator();
		while(i.hasNext())
		{
			String replace=(String)i.next();
			source=source.replaceAll(replace,displaceImage.get(replace));
		}
		return source;
	}
	public static void main(String[] arg0)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ChatDisplayProtocol cdp=new ChatDisplayProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_MESSAGE);
		ctp.setChatType(ctp.CHAT_TYPE_PRIVATE);
		ctp.setReceiverName("赵一飞");
		ctp.setSenderName("赵一非");
		ctp.setContent("[华文中宋][#CC0033][高兴的]啊啊[:a][:b][:c]");
		cdp.setTransportContent(ctp);
		cdp.displaceImage.put("\\[:a\\]","<img src=\"http://192.168.2.81/a.gif\"/>");
		cdp.displaceImage.put("\\[:b\\]","<img src=\"http://192.168.2.81/b.gif\"/>");
		cdp.displaceImage.put("\\[:c\\]","<img src=\"http://192.168.2.81/c.gif\"/>");
		System.out.println(cdp.getDisplayString());
		//System.out.println(cdp.replaceImage("[:a]---[:b]"));
		//System.out.println(new String("[a]:bbbbb").replaceAll("\\[a\\]","cc"));
	}
	public List<String> getStopUsers() {
		return stopUsers;
	}
	public void setStopUsers(List<String> stopUsers) {
		this.stopUsers = stopUsers;
	}
}
