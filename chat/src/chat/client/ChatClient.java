package chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import chat.protocol.ChatDisplayProtocol;
import chat.protocol.ChatTransportProtocol;

public class ChatClient {
	static String host="192.168.2.81";
	static int port=3000;
	private List<String> stopUsers=new ArrayList<String>();
	private SocketChannel sc=null;
	private String name=null;
	class Message
	{
		String head="<html><body><table>";
		StringBuffer message=new StringBuffer();
		String tail="</table></body></html>";
		String getMessage()
		{
			return head+message.toString()+tail;
		}
		void putMessage(String s)
		{
			message.append(s);
		}
	}
	Message privateM=new Message();
	Message publicM=new Message();
	public ChatClient()
	{
		InetSocketAddress socketAddress = new InetSocketAddress(host,
				port);
		try {
			sc=SocketChannel.open(socketAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMessage(String receiver,String font,String color,String felling,String message,String messageType)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_MESSAGE);
		ctp.setChatType(messageType);
		ctp.setReceiverName(receiver);
		ctp.setSenderName(name);
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		sb.append(font);
		sb.append("]");
		sb.append("[");
		sb.append(color);
		sb.append("]");
		sb.append("[");
		sb.append(felling);
		sb.append("]");
		sb.append(message);
		ctp.setContent(sb.toString());
		String content=ctp.getTransportContent();
		writeSocket(content);
	}
	public void changeRoom(String room)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_CHAGNE_ROOM);
		ctp.setNewRoom(room);
		writeSocket(ctp.getTransportContent());
		
	}
	public void changeName(String name)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_CHAGNE_NAME);
		ctp.setNewName(name);
		writeSocket(ctp.getTransportContent());
	}
	public void alarm(String content)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_ALARM);
		ctp.setContent(content);
		writeSocket(ctp.getTransportContent());
	}
	public void changeState(String state)
	{
		ChatTransportProtocol ctp=new ChatTransportProtocol();
		ctp.setActionType(ctp.ACTION_TYPE_SET_STATE);
		ctp.setUserState(state);
		writeSocket(ctp.getTransportContent());
	}
	private void writeSocket(String src)
	{
		ByteBuffer bb=ByteBuffer.allocate(src.length());
		bb.put(src.getBytes());
		try {
			sc.write(bb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getPrivateMessage()
	{
		return privateM.getMessage();
	}
	public String getPublicMessage()
	{
		return publicM.getMessage();
	}
	public void receiveMessage()
	{
		try {
			Selector selector=Selector.open();
			SelectionKey selectionkey=sc.register(selector,SelectionKey.OP_READ);
			Set<SelectionKey> sks=selector.keys();
			Iterator<SelectionKey> i=sks.iterator();
			while(i.hasNext())
			{
				SelectionKey sk=i.next();
				SocketChannel sch=(SocketChannel)sk.channel();
				ByteBuffer bb=ByteBuffer.allocate(ChatTransportProtocol.BufferSize);
				sch.read(bb);
				ChatDisplayProtocol cdp=new ChatDisplayProtocol(new ChatTransportProtocol(new String(bb.array())));
				cdp.setStopUsers(this.stopUsers);
				if(cdp.getDisplayType().equals(ChatTransportProtocol.CHAT_TYPE_PRIVATE))
					privateM.putMessage(cdp.getDisplayString());
				else
					publicM.putMessage(cdp.getDisplayString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop(String userId) {
		// TODO Auto-generated method stub
		stopUsers.add(userId);
	}
	public void unstop(String userId)
	{
		stopUsers.remove(userId);
	}
}
