package com.ll.smsbeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import com.ll.smsbeans.cmpp3.CmppActiveTestBuilder;
import com.ll.smsbeans.cmpp3.CmppActiveTestRespBuilder;
import com.ll.smsbeans.cmpp3.CmppConnectBuilder;
import com.ll.smsbeans.cmpp3.CmppConnectResp;
import com.ll.smsbeans.cmpp3.CmppPacket;
import com.ll.smsbeans.cmpp3.CmppPacketCommon;
import com.ll.smsbeans.cmpp3.CmppTerminateBuilder;
import com.ll.smsbeans.cmpp3.CmppTerminateRespBuilder;
import com.ll.smsbeans.log.LogCommon;

/**
 * ConnectionBean 是smsbeans中主要的bean.
 * <p>
 * ConnectionBean 主要是和服务器建立认证连接，所有其它的bean都是通过它和服务器通信的。
 * <p>
 * ConnectionBean提供一些“标准”的于服务器通信的接口，例如send方法，同时在内部自动处理一些服务
 * 器端的消息的回服例如ActiveTest等，并且，在运行过程中处理数据包的发送失败，保持链路联结。
 * <p>
 * 所有其它的beans通过ConnectionBean发送数据，和通过监听由ConnectionBean发布的received消息接收过滤数据
 
 * <p>
 * @see MessengerBean
 * @see IQBean
 * @see RosterBean
 * 
 
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 
 * @version $Revision: 1.0 $
 */
public class ConnectionBean implements Serializable, PacketListenerRegistrar
{
	/**
	*
	* Timer为定时器类
	* 为ConnectBean提供定时服务，通过定时调用 主要用于回复包的时间记数，以及保持链路测试包的定时发送。 
	* 有两个定时器：
	* 1 秒定时器	
	* 	用于回复包的时间记数，每秒激活一次ConnectionBean.onTimer.
	* 2 保持链路测试包定时器
	* 	用于保持链路测试包的记时，根据常数ACTIVETIME的值激活ConnectBeantion.onActiveConnect。
	* 
	* @see com.ll.smsbeans.ConnectionBean#onTimer()
	* @see ConnectionBean#onActiveConnect()
	* @see ConnectionBean#ACTIVETIME
	*<p>
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	 */
	public class Timer extends Thread
	{
		// 保持链路测试包的记时
		private long activeSec = 0;
		//定时器是否有效
		private boolean enableTimer = true;
		//保持链路测试的定时器是否有效
		private boolean enableActiveTimer = true;

		private boolean timerRun = true;

		/**
		 * 保持链路测试的定时器复位
		 */
		public void resetActiveTime()
		{
			activeSec = System.currentTimeMillis();
		}

		/**
		* @see java.lang.Runnable#run()
		*/
		public void run()
		{

			while (timerRun)
			{
				try
				{
					sleep(1000);
					if (enableTimer)
						ConnectionBean.this.onTimer();

					if (System.currentTimeMillis() - activeSec
						>= ACTIVETIME * 1000)
					{
						resetActiveTime();
						if (enableActiveTimer)
							ConnectionBean.this.onActiveConnect();

					}
				} catch (InterruptedException e)
				{
					return;

				} catch (Exception e)
				{
					return;
				}
			}
		}

		/**
		 * 定时器状态
		 * @return 定时器状态
		 */
		public boolean isEnableActiveTimer()
		{
			return enableActiveTimer;
		}

		/**
		 * 保持链路测试的定时器状态
		 * @return 保持链路测试的定时器状态
		 */
		public boolean isEnableTimer()
		{
			return enableTimer;
		}

		/**
		 * 设置保持链路测试的定时器状态
		 * @param enable 设置保持链路测试的定时器状态
		 */
		public void setEnableActiveTimer(boolean enable)
		{
			enableActiveTimer = enable;
		}

		/**
		 * 设置定时器状态
		 * @param enable 设置定时器状态
		 */
		public void setEnableTimer(boolean enable)
		{
			enableTimer = enable;
		}

		/* （非 Javadoc）
		 * @see java.lang.Thread#interrupt()
		 */
		public void interrupt()
		{

			timerRun = false;
			super.interrupt();
		}

		/* （非 Javadoc）
		 * @see java.lang.Thread#start()
		 */
		public synchronized void start()
		{
			reset();
			super.start();
		}

		private void reset()
		{

			enableTimer = true;
			enableActiveTimer = true;
			timerRun = true;
			resetActiveTime();
		}

	}
	/**
	*PacketStreamMonitor 通过响应数据报发送和接收事件，维护ConnectionBean的连接。
	*  
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	 */
	public class PacketStreamMonitor implements PacketListener
	{

		/**
		 * 主要工作：
		 * 1、 将接收到的回复包从发送队列移出。
		 * 2、 判断是否登陆成功。
		 * 3、 自动处理中断包和中断回复包。
		 * 4、 处理链路测试包。  
		 * @see com.ll.smsbeans.PacketListener#receivedPacket(com.ll.smsbeans.PacketEvent)
		 */
		public void receivedPacket(PacketEvent pe)
		{
			_log.info(pe.getPacket().toString());

			if (((CmppPacket) pe.getPacket()).isResponse())
			{

				SendPacketState sps = sendPacketList.get(pe.getPacket());
				if (sps != null)
				{
					sps.setState(SendPacketState.STATE_RESPONSED);
					sps.setRespPacket(pe.getPacket());
				}
				//sendPacketList.remove( pe.getPacket());

			}

			if (status.getValue() == ConnectionEvent.EState.CONNECTING)
			{

				Packet p = pe.getPacket();
				if (p != null)
				{

					if (p instanceof CmppPacket)
					{
						CmppPacket cp = (CmppPacket) p;

						if (cp.getCommandId()
							== CmppPacketCommon.ID_CMPP_CONNECT_RESP)
							//  0x80000001
						{

							sendPacketList.remove(cp);
							CmppConnectResp ccr = (CmppConnectResp) cp;
							if (ccr.getStatus() == 0)
							{

								setConnectionState(
									ConnectionEvent.STATE_CONNECTED,
									ConnectionEvent.REASON_CLIENT_INITIATED);

								mTimer = new Timer();
								mTimer.start();

							} else
							{
								disconnected(
									ConnectionEvent.REASON_SERVER_INITIATED);
							}

						}
					}

				}

				return;

			}

			if (status.getValue() == ConnectionEvent.EState.CONNECTED)
			{
				Packet p = pe.getPacket();
				if (p != null)
				{
					if (p instanceof CmppPacket)
					{
						CmppPacket cp = (CmppPacket) p;
						switch (cp.getCommandId())
						{
							case CmppPacketCommon.ID_CMPP_TERMINATE_RESP :
								//0x80000002
								sendPacketList.remove(pe.getPacket());
								disconnected(
									ConnectionEvent.REASON_CLIENT_INITIATED);
								break;

							case CmppPacketCommon.ID_CMPP_TERMINATE :
								//0x00000002

								CmppTerminateRespBuilder ctrb =
									new CmppTerminateRespBuilder();
								ctrb.setSequenceId(cp.getSequenceId());
								send(ctrb.builder());

								break;
							case CmppPacketCommon.ID_CMPP_ACTIVE_TEST :
								//0x00000008

								CmppActiveTestRespBuilder catrb =
									new CmppActiveTestRespBuilder();
								catrb.setSequenceId(cp.getSequenceId());
								send(catrb.builder());
								mTimer.resetActiveTime();
								break;
							case CmppPacketCommon.ID_CMPP_ACTIVE_TEST_RESP :
								//0x80000008
								sendPacketList.remove(cp);
								break;
							default :
								break;
						}

					}

				}

			}

			_log.log(
				LogCommon.DEBUG_LEVEL,
				"sendPacketList=" + sendPacketList.size());
		}

		/**
		 * 主要工作：处理服务器端发送来的中断包。
		 *  
		 * @see com.ll.smsbeans.PacketListener#sentPacket(com.ll.smsbeans.PacketEvent)
		 */
		public void sentPacket(PacketEvent pe)
		{

			_log.info(pe.getPacket().toString());

			if (status.getValue() == ConnectionEvent.EState.CONNECTED)
			{
				Packet p = pe.getPacket();
				if (p != null)
				{
					if (p instanceof CmppPacket)
					{
						CmppPacket cp = (CmppPacket) p;

						if (cp.getCommandId()
							== CmppPacketCommon.ID_CMPP_TERMINATE_RESP)
							//0x80000002
							disconnected(
								ConnectionEvent.REASON_SERVER_INITIATED);
					}

				}

			}
		}

		/**
		 * 处理中断包发送失败
		 * @see com.ll.smsbeans.PacketListener#sendFailed(com.ll.smsbeans.PacketEvent)
		 */
		public void sendFailed(PacketEvent pe)
		{

			_log.info(pe.getPacket().toString());

			if (!((CmppPacket) pe.getPacket()).isResponse())
			{

				SendPacketState sps = sendPacketList.get(pe.getPacket());
				if (sps != null)
				{
					sps.setState(SendPacketState.STATE_FAILED);

				}
				//sendPacketList.remove( pe.getPacket());

			}

			if ((((CmppPacket) pe.getPacket()).getCommandId()
				== CmppPacketCommon.ID_CMPP_ACTIVE_TEST) // 0x00000008
				| (((CmppPacket) pe.getPacket()).getCommandId()
					== CmppPacketCommon.ID_CMPP_TERMINATE)) // 0x00000002
			{
				sendPacketList.remove(pe.getPacket());
				disconnected(ConnectionEvent.REASON_IO_ERROR);

			}
		}

	}

	/**
	 * 链路测试的时间间隔为3分钟（180秒）。
	 */
	public final static int ACTIVETIME = 30;

	/**
	 * 发送包的超时时间为60秒。
	 */
	public final static int OVERTIME = 10;
	/**
	 * 重复发包的次数
	 */
	private final static int RESENDCOUNT = 3;

	private String sourceAddr;
	private String password;

	private IdBuilder idBuilder;

	/**
	 * 定时器
	 */
	private Timer mTimer;

	/**
	 * 缓存没有接收到回复包的发送包，并且记录每个包的发包时间和发包次数
	 */
	private SendPackStateList sendPacketList = SendPackStateList.getInstance();

	/** 当前连接的状态*/
	private ConnectionEvent.EState status;

	/** 所有实现连接事件监听接口的对象列表 */
	private final Vector connectionListeners = new Vector();

	/** 所有实现数据包的对象列表 */
	private final Vector packetListeners = new Vector();

	/** 用于连接的Socket */
	private Socket socket = null;

	private InputStreamInterface isi = new InputStreamInterface();

	private OutputStreamInterface osi = new OutputStreamInterface();

	/** 输入数据流线程，用于解析数据包，并且发送接收数据包事件 */
	private InputStreamHandler input = null;

	/** 输出数据留线程，用于发送数据包，并且发送发送数据包事件 */
	private OutputStreamHandler output = null;

	/**
	 * 默认服务器端口
	 */
	private int DEFAULT_PORT = 7891;

	static Logger _log;

	/**
	*用于输出线程回调ConnectionBean的方法的接口类
	*  
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	 */
	public class OutputStreamInterface
	{
		public void sent(Packet p)
		{

			ConnectionBean.this.sent(p);
		}

		public void sendFailed(Packet p)
		{
			ConnectionBean.this.sendFailed(p);
		}

		public void unexpectedThreadDeath(Exception e)
		{
			ConnectionBean.this.onOutputDeath(e);
		}
	}

	/**
	*用于输入线程回调ConnectionBean的方法的接口类
	*  
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	 */

	public class InputStreamInterface
	{
		public void received(Packet p)
		{

			ConnectionBean.this.received(p);
		}

		public void unexpectedThreadDeath(Exception e)
		{
			ConnectionBean.this.onInputDeath(e);
		}
	}

	/**
	 * 构造函数
	 */
	public ConnectionBean(String sourceAddr, String password)
	{
		this.sourceAddr = sourceAddr;
		this.password = password;

		idBuilder = IdBuilder.getInstance();
		status = ConnectionEvent.STATE_DISCONNECTED;
		output = null;
		input = null;

		mTimer = null;

		addPacketListener(new ConnectionBean.PacketStreamMonitor());
	}

	/**
	 * 使用默认端口于指定的服务器建立连接。
	 *
	 * @param host <code>InetAddress</code>主机地址
	 * @exception java.io.IOException socket error in backend
	 */
	public void connect(InetAddress host) throws IOException
	{
		connect(host, host.getHostName(), DEFAULT_PORT);
	}

	/**
	 * 与指定的服务器端口建立连接。
	 *
	 * @param host <code>InetAddress</code> 主机地址
	 * @param port an <code>int</code> 端口号
	 * @exception java.io.IOException socket error in backend
	 */
	public synchronized void connect(InetAddress host, int port)
		throws IOException
	{
		//setConnecting();
		connect(host, host.getHostName(), port);
	}

	/**
	 * 与指定的服务器端口建立连接。
	 *
	 * @param host <code>InetAddress</code> 主机地址
	 * @param name <code>String</code> 主机名
	 * @param port an <code>int</code> 端口号
	 * @exception java.io.IOException socket error in backend
	 */
	public synchronized void connect(InetAddress host, String name, int port)
		throws IOException
	{
		setConnecting();
		Socket s = new Socket(host, port);
		connect(s);

	}

	/** 
	 * 开始和服务器通信,发送认证包。
	 *
	 * @param s <code>Socket</code> 已经建立连接socket。
	 */
	public synchronized void connect(Socket s) throws IOException
	{

		if (socket != null)
			disconnect();
		setConnecting();
		setSocket(s);
		connect(s.getInputStream(), s.getOutputStream());
	}

	/**
	 *开始和服务器通信,发送认证包。
	 *
	 * @param is <code>InputStream</code> 从服务器发来的数据输入流。
	 * @param os <code>OutputStream</code> 发送到服务器的数据输出流。
	 */
	private synchronized void connect(InputStream is, OutputStream os)
		throws IOException
	{

		output = new OutputStreamHandler(osi);
		output.setOutputStream(os);
		output.start();

		input = new InputStreamHandler(isi);
		input.setInputStream(is);
		input.start();

		//TODO 加入开始的包	

		SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		//	dateString="0913185441";

		CmppConnectBuilder ccb = new CmppConnectBuilder();

		ccb.setSourceAddr(sourceAddr);
		ccb.setSpPassword(password);

		ccb.setTimestamp(dateString);

		send(ccb.builder());

	}

	/**
	 * 设置当前的连接状态为STATE_CONNECTING。
	 */

	private void setConnecting()
	{
		sendPacketList.clear();
		if (status == ConnectionEvent.STATE_DISCONNECTED)
		{

			setConnectionState(
				ConnectionEvent.STATE_CONNECTING,
				ConnectionEvent.REASON_CLIENT_INITIATED);
			idBuilder.resetSequenceId();
		}
	}

	/**
	 * 设置socket.
	 * @param s <code>Socket</code> socket
	 */
	private synchronized void setSocket(Socket s)
	{
		socket = s;
	}

	/**
	 * 断开于服务器的连接，并且折纸断开状态，发送中断连接包。
	 */
	public synchronized void disconnect()
	{
		if (status.getValue() == ConnectionEvent.EState.CONNECTED)
		{
			CmppTerminateBuilder ctb = new CmppTerminateBuilder();
			//ct.setSequenceId(idBuilder.getSequenceId());
			//			ctb.CreateNextSequenceId();
			send(ctb.builder());
		} else
		{
			disconnected(ConnectionEvent.REASON_CLIENT_INITIATED);
		}
	}

	/**
	 * 断开于服务器的连接，设置断开状态。
	 * 
	 * @param reason <code>ConnectionEvent.EReason</code> 断开的原因
	 */
	private synchronized void disconnected(ConnectionEvent.EReason reason)
	{
		if (mTimer != null)
			mTimer.interrupt();
		mTimer = null;
		sendPacketList.clear();

		if (socket != null)
		{
			try
			{
				if (input != null)
					input.shutdown();
				if (output != null)
					output.shutdown();
				socket.close();
				try
				{
					input.join();
				} catch (InterruptedException e)
				{
				}
			} catch (IOException e)
			{
				/* do nothing */
			}
			socket = null;
		}

		input = null;
		output = null;

		setConnectionState(ConnectionEvent.STATE_DISCONNECTED, reason);
	}

	/**
	 * <code>addConnectionListener</code> 为连接状态改变的监听器列表增加一个
	 * <code>ConnectionListener</code> 。
	 *
	 * @param l  <code>ConnectionListener</code>。相同的ConnectionListener不能增加两次。
	 */
	public final synchronized void addConnectionListener(ConnectionListener l)
	{
		if (!connectionListeners.contains(l))
			connectionListeners.addElement(l);
	}

	/**
	 * <code>delConnectionListener</code>连接状态改变的监听器列表删除一个
	 * <code>ConnectionListener</code> 。 
	 *
	 * @param l  <code>ConnectionListener</code> 值
	 */
	public final synchronized void delConnectionListener(ConnectionListener l)
	{
		if (packetListeners.contains(l))
			connectionListeners.removeElement(l);
	}

	/**
	 * <code>addPacketListener</code> 为连接包监听器列表增加一个
	 * <code>PacketListener</code>，用于监听已经发送或者受到的包 。
	 *
	 * @param l  <code>PacketListener</code> 值
	 */
	public final synchronized void addPacketListener(PacketListener l)
	{
		if (!packetListeners.contains(l))
			packetListeners.addElement(l);

	}

	/**
	 * <code>addPacketListener</code> 为连接包监听器列表增加一个
	 * <code>PacketListener</code>。
	 * 
	 * @param l a <code>PacketListener</code> 值
	 */
	public final synchronized void delPacketListener(PacketListener l)
	{
		if (packetListeners.contains(l))
			packetListeners.removeElement(l);
	}

	/**
	 * <code>send</code> 所有发送包需要调用的方法。
	 * <code>PacketListener</code>监听到数据包是否发送成功。
	 * @param packet  <code>Packet</code> 发送到服务器的包。
	 */
	public void send(Packet packet)
	{
		if (!((CmppPacket) packet).isResponse())
		{
			sendPacketList.add(packet);

		}
		_log.log(
			LogCommon.DEBUG_LEVEL,
			"sendPacketList=" + ConnectionBean.this.sendPacketList.size());

		/**TODO 改变 */
		//	if (status.getValue() == status.CONNECTED)
		//			return;
		if (output != null)
			output.send(packet);
	}

	/**
	 * <code>received</code> 为接收到包发送通知的方法. 这个方法仅仅只被<code>InputStreamHandler</code>调用，不要在其它的地方使用这个方法。
	 *
	 * @param packet a <code>Packet</code> 接收包.
	 */
	private void received(Packet packet)
	{

		fireReceivedPacket(packet);
	}

	/**
	 * <code>received</code> 为发送包发送通知的方法. 这个方法仅仅只被<code>OutputStreamHandler</code>调用，不要在其它的地方使用这个方法。
	 *
	 * @param packet a <code>Packet</code> 发送包. 
	 *
	 */
	private void sent(Packet packet)
	{
		fireSendPacket(packet);
	}

	/**
	 * <code>sendFailed</code> 发送发送失败的通知， 这个方法仅仅只被<code>OutputStreamHandler</code>调用，不要在其它的地方使用这个方法。
	 *
	 * @param packet a <code>Packet</code> 发送包. 
	 */
	private void sendFailed(Packet packet)
	{
		fireUnsendPacket(packet);
	}

	/**
	 * 发送接收到数据包的事件，这个方法在<code>InputStreamHandler</code>调用。
	 *
	 * @param <code>Packet</code> 发送到所有监听的包。
	 */
	protected final void fireReceivedPacket(Packet packet)
	{
		try
		{
			// 建立一个广播事件。
			PacketEvent pe = new PacketEvent(this, packet);

			Vector broadcast = (Vector) packetListeners.clone();

			// 广播到所有的监听。
			for (Enumeration e = broadcast.elements(); e.hasMoreElements();)
			{
				try
				{
					((PacketListener) e.nextElement()).receivedPacket(pe);
				} catch (Throwable e1)
				{
				}
			}
		} catch (Throwable e)
		{
		}
	}

	/**
	 * 广播已经发送数据包的事件，这个方法在<code>OutputStreamHandler</code>调用。
	 *
	 * @param <code>Packet</code> 发送到所有监听的包。
	 */
	protected final void fireSendPacket(Packet packet)
	{
		try
		{
			// 建立一个广播事件。
			PacketEvent pe = new PacketEvent(this, packet);

			Vector broadcast = (Vector) packetListeners.clone();

			// 广播到所有的监听。
			for (Enumeration e = broadcast.elements(); e.hasMoreElements();)
			{
				try
				{
					((PacketListener) e.nextElement()).sentPacket(pe);
				} catch (Throwable e1)
				{
				}
			}
		} catch (Throwable e)
		{
		}
	}

	/**
	 * 广播已经发送数据包失败的事件，这个方法在<code>OutputStreamHandler</code>调用。
	 *
	 * @param <code>Packet</code> 发送到所有监听的包。
	 */
	protected final void fireUnsendPacket(Packet packet)
	{
		try
		{
			// 建立一个广播事件。
			PacketEvent pe = new PacketEvent(this, packet);

			Vector broadcast = (Vector) packetListeners.clone();

			// 广播到所有的监听。
			for (Enumeration e = broadcast.elements(); e.hasMoreElements();)
			{
				try
				{
					((PacketListener) e.nextElement()).sendFailed(pe);
				} catch (Throwable e1)
				{
				}
			}
		} catch (Throwable e)
		{
		}
	}

	/**
	 * 响应OutputStreamHandler挂了.
	 */
	public void onOutputDeath(Exception e)
	{
		disconnected(ConnectionEvent.REASON_IO_ERROR);
		// throw new RuntimeException("Death of output thread: " + e.toString());
	}

	/**
	 *响应InputStreamHandler挂了.
	 */
	public void onInputDeath(Exception e)
	{
		disconnected(ConnectionEvent.REASON_IO_ERROR);
		// throw new RuntimeException("Death of input thread: " + e.toString());
	}

	/**
	 * 返回当前当前连接状态。
	 */
	public final ConnectionEvent.EState getConnectionState()
	{
		return status;
	}

	/**
	 *设置连接状态，广播状态改变的事件。
	 *
	 * @param state 新的状态。
	 * @param reason 改变状态的原因。
	 *
	 * @see ConnectionEvent.EState
	 */
	protected final void setConnectionState(
		ConnectionEvent.EState state,
		ConnectionEvent.EReason reason)
	{
		ConnectionEvent ce =
			new ConnectionEvent(this, state, getConnectionState(), reason);
		this.status = state;

		Vector broadcast = (Vector) connectionListeners.clone();
		Enumeration e = broadcast.elements();

		//广播事件
		while (e.hasMoreElements())
		{
			try
			{
				((ConnectionListener) e.nextElement()).connectionChanged(ce);
			} catch (Throwable e1)
			{
			}
		}
	}

	/**
	 * 响应定时器
	 *
	 */
	private void onTimer()
	{
		
		
		_log.log(LogCommon.DEBUG_LEVEL ,"onTimer!!!");
		mTimer.setEnableTimer(false);

		for (Iterator iter = sendPacketList.values().iterator();
			iter.hasNext();
			)
		{
			try
			{
				SendPacketState sp = (SendPacketState) iter.next();

				if (sp.isOverTime())
				{
					sp.addSendNum();
					_log.fine("overTime packet");
					if (sp.getSendNum() > RESENDCOUNT)
					{

						sendFailed(sp.getSendPacket());
					} else
					{
						//	output.send(sp.getPacket());
						sp.addSendNum();
					}

				}

			} catch (Throwable e1)
			{
			}
		}
		mTimer.setEnableTimer(true);
	}

	/**
	 * 响应测试链路
	 *
	 */
	private void onActiveConnect()
	{
		_log.fine("onActiveConnect!!!");
		mTimer.setEnableActiveTimer(false);

		CmppActiveTestBuilder catb = new CmppActiveTestBuilder();

		send(catb.builder());
		mTimer.setEnableActiveTimer(true);
	}

	/**
	 * 得到当前没有接收到回复包的发送包的数量。
	 * @return 发送包的数量
	 */
	public int getSendPackedNumber()
	{
		return sendPacketList.size();
	}

	static {
		_log = Logger.getLogger("com.ll.smsbeans.ConnectBean");

	}

}
