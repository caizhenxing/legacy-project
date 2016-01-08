package com.ll.smsbeans;

import java.io.Serializable;

import com.ll.smsbeans.cmpp3.CmppDeliver;
import com.ll.smsbeans.cmpp3.CmppDeliverResp;
import com.ll.smsbeans.cmpp3.CmppDeliverRespBuilder;
import com.ll.smsbeans.cmpp3.CmppPacket;
import com.ll.smsbeans.cmpp3.CmppPacketCommon;

/**
 * 
 * <code>DeliverBean</code> 通过Connection接收Delive和发送DeliveResp数据包，
 * 这个包不自动回复接收到的Deliver，需要使用者根据接收到的信息决定回复的值。
 *  Connection为已经连接的ConnectBean,<code>DeliverBean</code>必须指定。
 * 
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class DeliverBean implements Serializable, PacketListenerRegistrar
{

	/** 必须的连接bean */
	private ConnectionBean connection = null;

	/** 过滤发包，转发包 */
	private PacketRebroadcaster rebroadcaster = null;

	/**
	 * 通过这个类，实现定制的过滤，在这个类中只转发Deliver包。
	 */
	class DeliverListener extends PacketRebroadcaster
	{
		/**
		 * <code>receivedPacket</code> 过滤接收报
		 *
		 * @param p a <code>PacketEvent</code> 只
		 */
		public void receivedPacket(PacketEvent p)
		{

			if ((p.getPacket() instanceof Packet)
				&& (p.getSource() == connection))
			{
				if (p.getPacket() instanceof CmppPacket)
				{
					CmppPacket cp = (CmppPacket) p.getPacket();
					if (cp.getCommandId() == CmppPacketCommon.ID_CMPP_DELIVER)
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

					if (cp.getCommandId()
						== CmppPacketCommon.ID_CMPP_DELIVER_RESP)
					{
						fireSent(p);
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
				if (p.getPacket() instanceof CmppDeliverResp)
					fireSendFailed(p);
			}
		}

	}

	/** 建立一个新的 <code>DeliverBean</code> 实例。 */
	public DeliverBean()
	{
		rebroadcaster = new DeliverListener();
	}

	/**
	 * 建立一个新的 <code>DeliverBean</code> 实例, 设置连接对象。
	 *
	 * @param connection  <code>ConnectionBean</code> 当前对象的使用的连接
	 */
	public DeliverBean(ConnectionBean connection)
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
	 * <code>sendDeliverResp</code> 发送DeliverResp包
	 * @param deliver <code>CmppDeliver</code>需要回复的包。
	 * @param result 返回值。
	 */
	public void sendDeliverResp(CmppDeliver deliver,int result)
	{
		
		CmppDeliverRespBuilder cdrb=new CmppDeliverRespBuilder();
		
		cdrb.setSequenceId( deliver.getSequenceId());
		cdrb.setMsgId( deliver.getMsgId());
		cdrb.setResult(result); 
		connection.send(cdrb.builder());
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
