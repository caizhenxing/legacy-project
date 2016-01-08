
package com.ll.smsbeans;

import com.ll.smsbeans.cmpp3.CmppPacket;

/**
* 
*SendPacket作为在发送过程中记录所发送的包发送时间和重复发送的次数的类，
*用于判断包是否发送成功。
*
*<p>
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
 */
public class SendPacketState
{
	
	/**
	 * PState 是一个枚举类，为当前发送包的状态。
	 *
	 * @author  listlike <a href="mailto:listlike@hotmail.com">
 	 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	 */
	public static final class PState
	{
		/* PState 枚举值常数 */
		public final static int NOT_SET = -1;
		public final static int SENDED = 0;
		public final static int RESPONSED = 1;
		public final static int FAILED = 2;

		/**  */
		private int value = NOT_SET;

		/**
		 * 建立 <code>PState</code> 实例.
		 *
		 * @param newVal an <code>int</code> 值，当前的连接状态。
		 * @exception IllegalArgumentException 状态值不正确
		 */
		protected PState(int newVal) throws IllegalArgumentException
		{
			if ((newVal < -1) || (newVal > 2))
				throw new IllegalArgumentException("Invalid Enumeration Value");
			value = newVal;
		}

		/**
		 * 当前值的字符显示.
		 *
		 * @return a <code>String</code> 枚举值的名称
		 */
		public String toString()
		{

			switch (value)
			{
				case NOT_SET :
					return "NOT_SET";
				case SENDED :
					return "SENDED";
				case RESPONSED :
					return "RESPONSED";
				case FAILED :
					return "FAILED";
				default :
					return "Internal Error";
			}
		}

		/**
		 * <code>getValue</code> 得到当前的值。
		 *
		 * @return  <code>int</code> 当前的值。
		 */
		public int getValue()
		{
			return value;
		}

	}

	public final static PState STATE_FAILED = new PState(PState.FAILED);
	public final static PState STATE_SENDED = new PState(PState.SENDED);
	public final static PState STATE_RESPONSED =
		new PState(PState.RESPONSED);
	public final static PState STATE_UNKNOWN = new PState(PState.NOT_SET);

	private PState state;

	/**
	 *  发送的包
	 */
	private Packet sendPacket;

	/**
	 * 回复包
	 */
	private Packet respPacket;

	/**
	 *  重复发送次数
	 */
	private int SendNum;
	
	/**
	 * 发送时间
	 */
	private long SendTime;

	/**
	 * 构造函数
	 * 
	 * @param packet 发送的数据包
	 */
	public SendPacketState(Packet packet)
	{
		sendPacket = packet;
		this.SendNum = 0;
		this.SendTime = System.currentTimeMillis();
		state=new PState(PState.SENDED);
	}

	/**
	 * 增加重复发送的次数
	 *
	 */
	public void addSendNum()
	{
		SendNum++;
		SendTime = System.currentTimeMillis();
	}

	public boolean isOverTime()
	{
		return (System.currentTimeMillis() - SendTime)
			> (ConnectionBean.OVERTIME * 1000)
			? true
			: false;
	}

	/**
	 * 
	 * @return 当前发送的次数
	 */
	public int getSendNum()
	{
		return SendNum;
	}

	/**
	 * @return 当前已经发送的时间
	 */
	public long getSendTime()
	{
		return SendTime;
	}

	/**
	 * 
	 * @return 得到当前包的发送序列号，用于判断是否与恢复包相符
	 */
	public int getPacketSequenceId()
	{
		if (sendPacket instanceof CmppPacket)
		{
			CmppPacket cp = (CmppPacket) sendPacket;
			return cp.getSequenceId();
		} else
			return -1;
	}
	/**
	 * 重新构造equals，用于在容器中处理当前包
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof SendPacketState)
		{
			return ((SendPacketState) obj).getPacketSequenceId()
				== getPacketSequenceId();

		} else
			return false;

	}

	/**
	 * @return 发送包
	 */
	public Packet getSendPacket()
	{
		return sendPacket;
	}

	public Packet getRespPacket()
	{
		return respPacket;
	}

	/**
	 * @param packet
	 */
	public void setRespPacket(Packet packet)
	{
		respPacket = packet;
	}

	/**
	 * @return
	 */
	public PState getState()
	{
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(PState state)
	{
		this.state = state;
	}

}