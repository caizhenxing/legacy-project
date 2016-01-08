package ocelot.common.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


 /**
 * @author ’‘“ª∑«
 * @version 2007-1-15
 * @see
 */
public class SocketServer {

	private Map<String,SocketChannel> sockets=new  ConcurrentHashMap<String,SocketChannel>();
	private Queue<MsgInfo> events=new ConcurrentLinkedQueue<MsgInfo>();
	private Queue<MsgInfo> commands=new ConcurrentLinkedQueue<MsgInfo>();
	
	private int port=12000;
	private int receiveThreadNum=0;
	private int sendThreadNum=0;
	
	public SocketServer()
	{
		super();
	}
	
	/**
	 * init
	 * @param
	 * @version 2006-10-15
	 * @return
	 *//*
	public void setConnectThreadNum(int threads)
	{
		this.connectThreeadNum=threads;			
	}
	*//**
	 * init
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public void setReceiveThreadNum(int threads)
	{
		this.receiveThreadNum=threads;
		
	}
	/**
	 * init 
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public void setSendThreadNum(int threads)
	{
		this.sendThreadNum=threads;
	}
	/**
	 * thread safe
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public void setCommand(MsgInfo cmd)
	{
		commands.add(cmd);
	}
	/**
	 * thread safe
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public void setCommand(String msg,String ip)
	{
		commands.add(new MsgInfo(ip,msg));
	}
	/**
	 * thread safe
	 * @param
	 * @version 2006-10-15
	 * @return
	 */
	public MsgInfo getEvent()
	{
		if(events.isEmpty())
			return null;
		return events.poll();
	}
	public Map<String,SocketChannel> getSockets()
	{
		return sockets;
	}
	public void begin() throws IOException
	{
		this.initConnectionListener();
		if(this.receiveThreadNum+this.sendThreadNum<1)
		{
			
			while(true)
			{
				this.connectionListener();
				this.sendMsg();
			}
		}
	}
	private void sendMsg() 
	{
		int a=commands.size();
		while (!commands.isEmpty()) {
			MsgInfo mi = commands.poll();
			
			String msg = mi.getMsg();
			ByteBuffer bu = ByteBuffer.allocate(msg.getBytes().length);
			bu.put(msg.getBytes());
			bu.flip();
			if (mi.getIp() == null || mi.getIp().equals("")) {
				Iterator<String> i = sockets.keySet().iterator();
				while (i.hasNext()) {
					SocketChannel sc = sockets.get(i.next());
					try {
						sc.write(bu);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
			}
			SocketChannel sc = sockets.get(mi.getIp());
			if (sc == null)
				continue;
			try {
				sc.write(bu);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
	private ServerSocketChannel serverChannel=null;
	private Selector selector=null;
	//private Selector selectorK=null;
	private void initConnectionListener() throws IOException 
	{
		serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress(port));
		serverChannel.configureBlocking(false);
		selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		//selectorK=Selector.open();
		
	}	
	/*private void receiveListener()
	{
		try {
			selectorK.select();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Set<SelectionKey> waitConns = selectorK.selectedKeys();
		Iterator<SelectionKey> i = waitConns.iterator();
		while (i.hasNext()) {
			SelectionKey key = i.next();
			i.remove();
			
			
		}
	}*/
	private void connectionListener() 
	{
		try {
			selector.selectNow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Set<SelectionKey> waitConns = selector.selectedKeys();
		Iterator<SelectionKey> i = waitConns.iterator();
		while (i.hasNext()) {
			SelectionKey key = i.next();
			i.remove();
			
			if (key.isAcceptable()) {
				ServerSocketChannel server = (ServerSocketChannel) key.channel();
				try {
					SocketChannel client = server.accept();
					String ip = client.socket().getInetAddress()
							.getHostAddress();
					client.configureBlocking(false);
					client.register(selector, SelectionKey.OP_READ);
					
					sockets.put(ip, client);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
			if (key.isReadable()) {
				SocketChannel client = (SocketChannel) key.channel();
				StringBuilder sb = new StringBuilder();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				String ip=null;
				try {
					ip = client.socket().getInetAddress().getHostAddress();
					
					while (client.read(buffer)>0)
					{
						buffer.flip();
						byte[] temp=new byte[buffer.limit()];
						buffer.get(temp);
						//String s=new String(buffer.);
						sb.append(new String(temp));
						buffer.clear();
					}
					events.add(new MsgInfo(ip, sb.toString()));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					key.cancel();
					sockets.remove(ip);
				}
			}
		}
	}
	
	public static void main(String[] arg0)
	{
		SocketServer ss=new SocketServer();
		try {
			ss.initConnectionListener();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			ss.setCommand("OORING:0,1;OOSERI:1111111111,333333333;","192.168.1.3");
			
			while(ss.sockets.isEmpty())
			{
				ss.connectionListener();
				
			}
			ss.sendMsg();
		
	}
	
	
}
