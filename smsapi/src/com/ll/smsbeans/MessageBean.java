package com.ll.smsbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import com.ll.smsbeans.cmpp3.CmppPacket;
import com.ll.smsbeans.cmpp3.CmppPacketCommon;

/**
 * 
 * <code>MessageBean</code> 通过Connection接收通过Connection发送submit,query,cancel数据包，接收它们的回复包。
 * 
 *  Connection为已经连接的ConnectBean,<code>DeliverBean</code>必须指定。
 * 
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class MessageBean implements Serializable, PacketListenerRegistrar
{

	/** 必须的连接bean */
	private ConnectionBean connection = null;

	/** 过滤发包，转发包 */
	private PacketRebroadcaster rebroadcaster = null;

	/**
	 * 通过这个类，实现定制的过滤，在这个类中只转发Deliver包。
	 */
	class MessageListener extends PacketRebroadcaster
	{
		/**
		 * <code>receivedPacket</code> 过滤接收报
		 *
		 * @param p a <code>PacketEvent</code> 值
		 */
		public void receivedPacket(PacketEvent p)
		{

			if ((p.getPacket() instanceof Packet)
				&& (p.getSource() == connection))
			{
				if (p.getPacket() instanceof CmppPacket)
				{
					CmppPacket cp = (CmppPacket) p.getPacket();

					if (cp.getCommandId()
						== CmppPacketCommon.ID_CMPP_SUBMIT_RESP
						|| cp.getCommandId()
							== CmppPacketCommon.ID_CMPP_QUERY_RESP
						|| cp.getCommandId()
							== CmppPacketCommon.ID_CMPP_CANCEL_RESP)
					{
						fireReceived(p);
					}

				}

			}

		}

		/**
		 * <code>sentPacket</code> 过滤发送包
		 *
		 * @param p  <code>PacketEvent</code> 值
		 */
		public void sentPacket(PacketEvent p)
		{
			if ((p.getPacket() instanceof Packet)
				&& (p.getSource() == connection))
			{
				if (p.getPacket() instanceof CmppPacket)
				{
					CmppPacket cp = (CmppPacket) p.getPacket();

					if (cp.getCommandId() == CmppPacketCommon.ID_CMPP_SUBMIT
						|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_QUERY
						|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_CANCEL)
					{
						fireReceived(p);
					}

				}

			}
		}

		/**
		 *
		 * <code>sendFailed</code> 过滤发送失败事件。
		 *
		 * @param p  <code>PacketEvent</code> 值
		 */
		public void sendFailed(PacketEvent p)
		{
			if ((p.getPacket() instanceof Packet)
				&& (p.getSource() == connection))
			{
				if (p.getPacket() instanceof CmppPacket)
				{
					CmppPacket cp = (CmppPacket) p.getPacket();

					if (cp.getCommandId() == CmppPacketCommon.ID_CMPP_SUBMIT
						|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_QUERY
						|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_CANCEL)
						fireReceived(p);
				}
			}
		}

	}

	/** 建立一个新的 <code>MessageBean</code> 实例。 */
	public MessageBean()
	{
		rebroadcaster = new MessageListener();
	}

	/**
	 * 建立一个新的 <code>MessageBean</code> 实例, 设置连接对象。
	 *
	 * @param connection  <code>ConnectionBean</code> 当前对象的使用的连接
	 */
	public MessageBean(ConnectionBean connection)
	{
		this();
		setConnection(connection);
	}

	/**
	 * <code>setConnection</code> 在对象建立后设置连接对象。
	 *
	 * @param connection  <code>ConnectionBean</code> 当前对象的使用的连接
	 */
	public void setConnection(ConnectionBean connection)
	{
		this.connection = connection;
		rebroadcaster.registerListener(connection);
	}

	/**
	 * <code>getConnection</code> 返回当前对象使用的连接。
	 *
	 * @return <code>ConnectionBean</code> 返回当前对象使用的连接
	 */
	public ConnectionBean getConnection()
	{
		return connection;
	}

	/**
	 * <code>sendsendMessage</code> 只发送submit,query,cancel数据包。
	 *
	 * @param message a <code>Message</code> 值
	 */
	public void sendMessage(Packet message)
	{

		if (message instanceof CmppPacket)
		{
			CmppPacket cp = (CmppPacket) message;

			if (cp.getCommandId() == CmppPacketCommon.ID_CMPP_SUBMIT
				|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_QUERY
				|| cp.getCommandId() == CmppPacketCommon.ID_CMPP_CANCEL)
				send(message);
		}

	}

	/**
		 * <code>send</code> 发送一个消息包,根据是否有发送窗口，决定是否延时。
		 *
		 * @param message  <code>Message</code> 消息包
		 */
	private void send(Packet message)
	{
		if (connection == null)
			throw new RuntimeException("MessengerBean not connected to a ConnectionBean object");

		Random rand = new Random(new Date().getTime());

		int times = 0;
		while (connection.getSendPackedNumber() > 13)
		{
			try
			{
				Thread.currentThread().sleep(500 + rand.nextInt(500));
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			if (times++ > 100)
				break;
		}

		connection.send(message);
	}

	/**
	 * <code>addPacketListener</code> 增加一个新的PacketListener。
	 *
	 * @param l  <code>PacketListener</code> 增加的PacketListener。
	 */
	public synchronized void addPacketListener(PacketListener l)
	{
		rebroadcaster.addPacketListener(l);
	}

	/**
	 * <code>delPacketListener</code> 删除一个PacketListener。
	 *
	 * @param l  <code>PacketListener</code> 被删除的PacketListener 
	 */
	public synchronized void delPacketListener(PacketListener l)
	{
		rebroadcaster.delPacketListener(l);
	}
}
