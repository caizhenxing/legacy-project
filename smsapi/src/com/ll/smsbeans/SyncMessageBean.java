package com.ll.smsbeans;

import java.io.Serializable;
import java.util.logging.Logger;

import com.ll.smsbeans.cmpp3.CmppCancelBuilder;
import com.ll.smsbeans.cmpp3.CmppCancelResp;
import com.ll.smsbeans.cmpp3.CmppPacket;
import com.ll.smsbeans.cmpp3.CmppPacketCommon;
import com.ll.smsbeans.cmpp3.CmppQueryBuilder;
import com.ll.smsbeans.cmpp3.CmppQueryResp;
import com.ll.smsbeans.cmpp3.CmppSubmitBuilder;
import com.ll.smsbeans.cmpp3.CmppSubmitResp;

/**
 * 
 * <code>SyncMessengerBean</code> 通过MessengerBean采用同步方式发送sub mit,query,cancel数据包.
 *  Connection为已经连接的ConnectBean,<code>SyncMessengerBean</code>必须指定ConnectBean。
 * <p>
 * 
 * 原计划写这个类是为了让人们比较方便的使用，可是实际上使用的时候不一定很方便。
 * 特别要需要注意的是，对于sendSubmit来说，需要记录返回的msgId，以便用于cancel Submit,和根据registerDelivery 查询
 * 短消息状态。不能在这个类中实现，要不在使用的时候修改这个类，或者另外使用一个PacketListener记录msgId。
 * 这个类实现的即丑陋，又不太好用，在这里弄出来完全是为类演示一下关于这个类库的使用方法。
 * 在使用的时候需要单独的将它放在一个线程中运行，以免造成事件阻塞。
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class SyncMessageBean implements Serializable, PacketListener
{

	static Logger _log;

	/** 必须的连接bean */
	private ConnectionBean connection = null;

	private MessageBean messageBean = null;

	/**
	 * 建立一个 <code>MessengerBean</code> 实例,并且注册一个ConnectionBean。
	 *
	 * @param connection  <code>ConnectionBean</code> 
	 */
	public SyncMessageBean(ConnectionBean connection)
	{
		setConnection(connection);
	}

	/**
	 * <code>setConnection</code> 在建立对象后，设置一个ConnectionBean
	 *
	 * @param connection a <code>ConnectionBean</code> 
	 */
	public void setConnection(ConnectionBean connection)
	{
		this.connection = connection;
		messageBean = new MessageBean(connection);
		messageBean.addPacketListener(this);
	}

	/**
	 * <code>getConnection</code>返回被这个对象使用的<code>ConnectionBean</code>。
	 *
	 * @return <code>ConnectionBean</code> 
	 */
	public ConnectionBean getConnection()
	{
		return connection;
	}

	/**
	 * 发送一个Submit，当其回复包返回后，根据返回包返回是否成功发送这个包。
	 * 
	 * @param preMessage 需要发送的submit对象。
	 * @param msgContent 需要发送的内容。
	 * @return 是否成功，0 成功,-1 发送失败，>1同submitResp错误定义。
	 */
	public int sendSubmit(CmppSubmitBuilder preMessage, String msgContent)
	{

		//preMessage.setSequenceId(IdBuilder.getInstance().getSequenceId());

		byte[] sb = new byte[0];

		try
		{

			switch (preMessage.getMsgFmt())
			{
				case CmppPacketCommon.MSG_TYPE_ASCII :
					sb = msgContent.getBytes("ASCII");
					break;
				case CmppPacketCommon.MSG_TYPE_BINARY :
					sb = msgContent.getBytes();
					break;
				case CmppPacketCommon.MSG_TYPE_CHINESE :
					sb = msgContent.getBytes("GBK");
					break;
				case CmppPacketCommon.MSG_TYPE_UCS2 :
					sb = msgContent.getBytes("UnicodeBigUnmarked");
					break;
				case CmppPacketCommon.MSG_TYPE_WRITECARD :
					sb = msgContent.getBytes();
					break;
				default :
					sb = msgContent.getBytes("ASCII");
					break;

			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return sendSubmit(preMessage, sb);

	}

	/**
	* 发送一个Cancel，当其回复包返回后，根据返回包返回是否成功发送这个包。
	 * 
	 * @param CmppCancelBuilder 需要发送的Cancel对象。
	 * @return 是否成功，0 成功,-1 发送失败，>1同CancelResp错误定义。
	 */
	public int sendCancel(CmppCancelBuilder ccb)
	{
		int ErrCode = 0;
		CmppCancelResp ccr = null;
		//		ccb.CreateNextSequenceId();
		Packet sendPacket = ccb.builder();
		messageBean.sendMessage(sendPacket);
		Packet rp = (CmppCancelResp) syncPacketResp(sendPacket);
		if (rp != null)
		{
			if (rp instanceof CmppCancelResp)
			{
				ccr = (CmppCancelResp) rp;
				ErrCode = ccr.getSuccessId();
			} else
				ErrCode = -1;

		} else
		{
			ErrCode = -1;
		}
		return ErrCode;
	}

	private Packet syncPacketResp(Packet sendPacket)
	{
		Packet respPack = null;

		int respPacketCommandId =
			0x80000000 | (0xff & ((CmppPacket) sendPacket).getCommandId());
		while (respPack == null)
		{

			try
			{
				wait(1000);
			} catch (Exception e)
			{
			}

			//判断是否超时！！！
			SendPacketState sps =
				SendPackStateList.getInstance().get(sendPacket);
			if (sps != null)
			{
				if (System.currentTimeMillis() - sps.getSendTime() > 120000)
				{

					break;
				}

			}

			//判断是否受到正确的回复包

			sps = SendPackStateList.getInstance().get(sendPacket);
			if (sps != null)
			{
				if (sps.getState().getValue()
					== SendPacketState.STATE_RESPONSED.getValue())
				{
					Packet rp = sps.getRespPacket();
					if (rp instanceof CmppPacket)
					{

						if (((CmppPacket) rp).getCommandId()
							== respPacketCommandId)
						{
							respPack = sps.getRespPacket();

							break;

						} else
							break;

					} else
						break;

				}

				if (sps.getState().getValue()
					== SendPacketState.STATE_FAILED.getValue())
					break;
			};
		}

		SendPackStateList.getInstance().remove(sendPacket);
		return respPack;
	}

	/**
	 * 发送一个Query，当其回复包返回后，根据返回包返回是否成功发送这个包。
	 * @param cqb 查询包
	 * @return  返回查询结果，如果为null失败。
	 */

	public CmppQueryResp sendQuery(CmppQueryBuilder cqb)
	{
		int ErrCode = 0;
		CmppQueryResp cqr = null;

		Packet sendPacket = cqb.builder();
		messageBean.sendMessage(sendPacket);

		return (CmppQueryResp) syncPacketResp(sendPacket);
	}

	public int sendSubmit(CmppSubmitBuilder preMessage, byte[] messgae)
	{

		int ErrCode = 0;

		int msgMaxCount =
			preMessage.getMsgFmt() == CmppPacketCommon.MSG_TYPE_ASCII
				? 159
				: 140;

		int packetCount = messgae.length / msgMaxCount;

		packetCount =
			messgae.length % msgMaxCount == 0 ? packetCount : packetCount + 1;

		Packet[] packetList = new Packet[packetCount];

		int currPoint = 0;
		int packetIndex = 0;

		while (true)
		{

			int curLen =
				(currPoint + msgMaxCount) < messgae.length
					? msgMaxCount
					: messgae.length - currPoint;

			byte[] msgByte = new byte[curLen];

			for (int i = 0; i < curLen; i++)
			{
				msgByte[i] = messgae[currPoint + i];
			}

			//			preMessage.CreateNextSequenceId();

			preMessage.setMsgContent(msgByte);

			packetList[packetIndex] = preMessage.builder();

			messageBean.sendMessage(packetList[packetIndex]);
			packetIndex++;

			currPoint += msgMaxCount;
			if (currPoint >= messgae.length)
				break;

		}

		if (packetIndex != packetCount)
		{
			ErrCode = -1;
			return ErrCode;
		}

		Packet respPacket;
		for (int i = 0; i < packetList.length; i++)
		{
			respPacket = syncPacketResp(packetList[i]);
			if (respPacket == null)
			{
				ErrCode = -1;
				break;
			}
			ErrCode = ((CmppSubmitResp) respPacket).getResult();
			if (ErrCode != 0)
				break;
		}
		
		
		for (int i = 0; i < packetList.length; i++)
		{

			SendPackStateList.getInstance().remove(packetList[i]);

		}
		return ErrCode;
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketListener#receivedPacket(com.ll.smsbeans.PacketEvent)
	 */
	public void receivedPacket(PacketEvent pe)
	{

		notify();
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketListener#sendFailed(com.ll.smsbeans.PacketEvent)
	 */
	public void sendFailed(PacketEvent pe)
	{
		notify();
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketListener#sentPacket(com.ll.smsbeans.PacketEvent)
	 */
	public void sentPacket(PacketEvent pe)
	{
		//notify();	
	}

	static {
		_log = Logger.getLogger("com.ll.smsbeans.SyncMessageBean");
	}

}
