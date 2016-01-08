/**
 * ChatRoom.java 06/06/21
 */
package chat.server;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chat.protocol.ChatTransportProtocol;
import excellence.common.util.regex.AnalyseString;

/**
 * 
 * @author yifei zhao
 * @version 1.0
 * ������
 */
public class ChatRoom extends Thread{

	private String roomName;
	private Map<String,ChatMember> members=new HashMap<String,ChatMember>();
	private String brief;
	private int limitNumber;
	private int number;
	private ChatServer cs=null;
	private Selector selector;
	private String[] keys;
	private List<String> alarms=new ArrayList<String>();
	public ChatRoom()
	{
		super();
		try {
			selector=Selector.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<String> getAlarms()
	{
		return alarms;
	}
	
	public ChatServer getCs() {
		return cs;
	}
	public void setCs(ChatServer cs) {
		this.cs = cs;
	}
	public int getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(int limitNumber) {
		this.limitNumber = limitNumber;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	/**
	 * ����û�
	 * @param cm
	 * @return
	 */
	public boolean addMember(ChatMember cm)
	{
		if(number>=limitNumber)
			return false;
		number++;
		members.put(cm.getName(),cm);
		
		try {
			cm.getChannel().register(selector,SelectionKey.OP_READ);
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		ChatTransportProtocol ctps=new ChatTransportProtocol();
		ctps.setActionType(ChatTransportProtocol.ACTION_TYPE_MESSAGE);
		ctps.setChatType(ChatTransportProtocol.CHAT_TYPE_SYSTEM_CUE);
		ctps.setContent("��ӭ����"+roomName+"����");
		sendMsg(ctps.getSenderName(),ctps.getTransportContent());
	
		return true;
	}
	/**
	 * �㲥 �������ݶ�����room���û�
	 * @param content
	 */
	public void broadcast(String content)
	{
		Iterator<String> i=members.keySet().iterator();
		try {
			ByteBuffer bb=ByteBuffer.allocate(content.length());
			bb.put(content.getBytes());
		while(i.hasNext())
		{
			ChatMember cm=members.get(i.next());
			cm.getChannel().write(bb);
			bb.flip();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �������ݸ�һЩ�û�
	 * @param users �û��б�
	 * @param content ��������
	 */
	public void broadcast(String[] users,String content)
	{
		try {
			ByteBuffer bb=ByteBuffer.allocate(content.length());
			bb.put(content.getBytes());
		for(int i=0,size=users.length;i<size;i++)
		{
			ChatMember cm=members.get(users[i]);
			if(cm==null)
				continue;
			cm.getChannel().write(bb);
			bb.flip();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �������ݸ��ض��û�
	 * @param user �û�
	 * @param content ��������
	 */
	public void sendMsg(String user,String content)
	{
		try {
			ByteBuffer bb=ByteBuffer.allocate(content.length());
			bb.put(content.getBytes());
		
			ChatMember cm=members.get(user);
			if(cm==null)
				return;
			cm.getChannel().write(bb);
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		acceptMsg();
		
	}
	public Map<String, ChatMember> getMembers() {
		return members;
	}
	/**
	 * ������Ϣ������
	 * ��Ҫ�߳�����
	 *
	 */
	public void acceptMsg()
	{
		try {
			 
			Set<SelectionKey> keys=selector.keys();
			Iterator<SelectionKey> ii=keys.iterator();
			while(ii.hasNext())
			{
				SelectionKey sk=ii.next();
				SocketChannel sc=(SocketChannel)sk.channel();
				ByteBuffer bb=ByteBuffer.allocate(ChatTransportProtocol.BufferSize);
				sc.read(bb);
				paserMsg(new String(bb.array()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**
	 * ���������������Ϣ
	 * @param content
	 * ��Ϣ
	 * ����
	 * ������
	 * ����״̬
	 * ����
	 */
	private void paserMsg(String content)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol(content);
		if(filtrate(ctp.getTransportContent(),keys))
		{
			ChatTransportProtocol ctps=new ChatTransportProtocol();
			ctps.setActionType(ChatTransportProtocol.ACTION_TYPE_MESSAGE);
			ctps.setChatType(ChatTransportProtocol.CHAT_TYPE_SYSTEM_CUE);
			ctps.setContent("�����дʣ���������");
			sendMsg(ctp.getSenderName(),ctps.getTransportContent());
		}
		if(ctp.getActionType().equals(ChatTransportProtocol.ACTION_TYPE_ALARM))
		{
			//������Ϣ
			alarms.add(ctp.getContent());
			//���ͳɹ���Ϣ
			ChatTransportProtocol ctps=new ChatTransportProtocol();
			ctps.setActionType(ChatTransportProtocol.ACTION_TYPE_MESSAGE);
			ctps.setChatType(ChatTransportProtocol.CHAT_TYPE_SYSTEM_CUE);
			ctps.setContent("�����ɹ�");
			sendMsg(ctp.getSenderName(),ctps.getTransportContent());
		}
		if(ctp.getActionType().equals(ChatTransportProtocol.ACTION_TYPE_CHAGNE_NAME))
		{
			//����
			ChatMember cm=members.get(ctp.getSenderName());
			cm.setName(ctp.getNewName());
			//���ͳɹ���Ϣ
			ChatTransportProtocol ctps=new ChatTransportProtocol();
			ctps.setActionType(ChatTransportProtocol.ACTION_TYPE_MESSAGE);
			ctps.setChatType(ChatTransportProtocol.CHAT_TYPE_SYSTEM_CUE);
			ctps.setContent("�����ɹ�");
			sendMsg(ctp.getSenderName(),ctps.getTransportContent());
		
		}
		if(ctp.getActionType().equals(ChatTransportProtocol.ACTION_TYPE_CHAGNE_ROOM))
		{
			//������
			String name=ctp.getSenderName();
			ChatMember cm=members.get(name);
			cs.addRoom(ctp.getNewRoom(),cm);
			members.remove(name);
			
		}
		if(ctp.getActionType().equals(ChatTransportProtocol.ACTION_TYPE_MESSAGE))
		{
			//��Ϣ
			String name=ctp.getSenderName();
			ChatMember cm=members.get(name);
			if(ctp.getChatType().equals(ChatTransportProtocol.CHAT_TYPE_PRIVATE))
			{
				String rname=ctp.getReceiverName();
				sendMsg(rname,ctp.getTransportContent());
			}
			else
				broadcast(ctp.getTransportContent());
			
		}
		if(ctp.getActionType().equals(ChatTransportProtocol.ACTION_TYPE_SET_STATE))
		{
			//����״̬
			ChatMember cm=members.get(ctp.getSenderName());
			cm.setState(ctp.getUserState());
			//���ͳɹ���Ϣ
			ChatTransportProtocol ctps=new ChatTransportProtocol();
			ctps.setActionType(ChatTransportProtocol.ACTION_TYPE_MESSAGE);
			ctps.setChatType(ChatTransportProtocol.CHAT_TYPE_SYSTEM_CUE);
			ctps.setContent("״̬���óɹ�");
			sendMsg(ctp.getSenderName(),ctps.getTransportContent());
		
		}
	}
	private boolean filtrate(String source,String[] keys)
	{
		return AnalyseString.contain(source,keys);
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
}
