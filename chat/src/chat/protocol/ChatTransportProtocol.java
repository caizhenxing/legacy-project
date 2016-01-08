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
	 * ��������:
	 * "1"---��Ϣ(������-�ͻ���˫��)
	 * "2"---����(��������ύ����)
	 * "3"---������(��������ύ����)
	 * "4"---����״̬(��������ύ����)
	 * "5"---����(��������ύ����)
	 * 
	 */
	private String actionType="";
	/**
	 * �������ͣ�
	 */
	public static String ACTION_TYPE_MESSAGE="1";
	public static String ACTION_TYPE_CHAGNE_NAME="2";
	public static String ACTION_TYPE_CHAGNE_ROOM="3";
	public static String ACTION_TYPE_SET_STATE="4";
	public static String ACTION_TYPE_ALARM="5";
	/**
	 * ��Ϣ������
	 */
	private String senderName="";
	/**
	 * ��Ϣ������
	 */
	private String receiverName="";
	/**
	 * ����
	 */
	private String content="";
	/**
	 * ����
	 */
	private String newName="";
	/**
	 * ������
	 * 
	 */
	private String newRoom="";
	/**
	 * ��Ϣ���ͣ�
	 * "1"---���Ļ�
	 * "2"---����
	 * "3"---ϵͳ�㲥
	 * "4"---�û��б�
	 * "5"---�����б�
	 * "6"---ϵͳ����
	 * "7"---ϵͳ��ʾ
	 * 
	 */
	private String chatType="";
	/**
	 * ��Ϣ����
	 */
	public static String CHAT_TYPE_PRIVATE="1";
	public static String CHAT_TYPE_PUBLIC="2";
	public static String CHAT_TYPE_BROADCAST="3";
	public static String CHAT_TYPE_USER_LIST="4";
	public static String CHAT_TYPE_ROOM_LIST="5";
	
	public static String CHAT_TYPE_SYSTEM_WARN="6";
	public static String CHAT_TYPE_SYSTEM_CUE="7";
	/**
	 * �û�״̬
	 * "1"---����
	 * "2"---æµ
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
	 * ���ܣ���Ҫ���ܵĻ�����ɶԳ���
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
