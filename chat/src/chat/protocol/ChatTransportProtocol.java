package chat.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ChatTransportProtocol {
	public static int BufferSize=1024;
	public ChatTransportProtocol()
	{
		super();
	}
	public ChatTransportProtocol(String xml)
	{
		this.setTransportContent(xml);
	}
	public ChatTransportProtocol(InputStream is)
	{
		BufferedReader in=new BufferedReader(new InputStreamReader(is));
		try {
			this.setTransportContent(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	/**
	 * 动作类型:
	 * "1"---消息(服务器-客户端双向)
	 * "2"---改名(向服务器提交申请)
	 * "3"---换房间(向服务器提交申请)
	 * "4"---设置状态(向服务器提交申请)
	 * "5"---报警(向服务器提交申请)
	 * 
	 */
	private String actionType="";
	/**
	 * 动作类型：
	 */
	public static String ACTION_TYPE_MESSAGE="1";
	public static String ACTION_TYPE_CHAGNE_NAME="2";
	public static String ACTION_TYPE_CHAGNE_ROOM="3";
	public static String ACTION_TYPE_SET_STATE="4";
	public static String ACTION_TYPE_ALARM="5";
	/**
	 * 信息发送者
	 */
	private String senderName="";
	/**
	 * 信息接收者
	 */
	private String receiverName="";
	/**
	 * 内容
	 */
	private String content="";
	/**
	 * 改名
	 */
	private String newName="";
	/**
	 * 换房间
	 * 
	 */
	private String newRoom="";
	/**
	 * 消息类型：
	 * "1"---悄悄话
	 * "2"---公开
	 * "3"---系统广播
	 * "4"---用户列表
	 * "5"---房间列表
	 * "6"---系统警告
	 * "7"---系统提示
	 * 
	 */
	private String chatType="";
	/**
	 * 消息类型
	 */
	public static String CHAT_TYPE_PRIVATE="1";
	public static String CHAT_TYPE_PUBLIC="2";
	public static String CHAT_TYPE_BROADCAST="3";
	public static String CHAT_TYPE_USER_LIST="4";
	public static String CHAT_TYPE_ROOM_LIST="5";
	
	public static String CHAT_TYPE_SYSTEM_WARN="6";
	public static String CHAT_TYPE_SYSTEM_CUE="7";
	/**
	 * 用户状态
	 * "1"---在线
	 * "2"---忙碌
	 */
	private String userState="";
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
		if(this.actionType==null)
			this.actionType="";
	}
	public String getChatType() {
		return chatType;
	}
	public void setChatType(String chatType) {
		this.chatType = chatType;
		if(this.chatType==null)
			this.chatType="";
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		if(this.content==null)
			this.content="";
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
		if(this.newName==null)
			this.newName="";
	}
	public String getNewRoom() {
		return newRoom;
	}
	public void setNewRoom(String newRoom) {
		this.newRoom = newRoom;
		if(this.newRoom==null)
			this.newRoom="";
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		if(this.receiverName==null)
			this.receiverName="";
	}
	public void setTransportContent(String contents)
	{
		Properties p=new Properties();
		try {
			InputStream  is=new StringBufferInputStream(inencrypt(contents));
			p.loadFromXML(is);
			is.close();
			this.actionType=p.getProperty("actionType");
			
			this.chatType=p.getProperty("chatType");
			this.content=p.getProperty("content");
			this.newName=p.getProperty("newName");
			this.newRoom=p.getProperty("newRoom");
			this.receiverName=p.getProperty("receiverName");
			this.senderName=p.getProperty("senderName");
			this.userState=p.getProperty("userState");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTransportContent()
	{
		String xml=null;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		Properties p=new Properties();
		p.setProperty("actionType",this.actionType);
		p.setProperty("chatType",chatType);
		p.setProperty("content",this.content);
		p.setProperty("newName",this.newName);
		p.setProperty("newRoom",this.newRoom);
		p.setProperty("receiverName",this.receiverName);
		p.setProperty("senderName",this.senderName);
		p.setProperty("userState",this.userState);
		try {
			p.storeToXML(bos,"");
			xml=bos.toString();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return encrypt(xml);
	}
	/**
	 * 加密，若要加密的话必须成对出现
	 * @param s
	 * @return
	 */
	private String encrypt(String s)
	{
		return s;
	}
	private String inencrypt(String cryptograph)
	{
		return cryptograph;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
}
