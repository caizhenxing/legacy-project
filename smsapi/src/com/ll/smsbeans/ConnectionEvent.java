package com.ll.smsbeans;

import java.util.EventObject;

/**
 *  <code>ConnectionEvent</code> 为当连接状态（ConnectionBean，断线、在线、连接中、连接失败）被改变的时候，
 * 所产生的事件描述。
 *<p>
 * <code>ConnectionEvent</code> 的主要属性为source，标记当前的事件发出者，主要用于处理有多个连接的时候。
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class ConnectionEvent extends EventObject
{
	public final static EState STATE_UNKNOWN = new EState(EState.NOT_SET);
	public final static EState STATE_DISCONNECTED =
		new EState(EState.DISCONNECTED);
	public final static EState STATE_CONNECTING = new EState(EState.CONNECTING);
	public final static EState STATE_CONNECTED = new EState(EState.CONNECTED);

	public final static EReason REASON_UNKNOWN = new EReason(EReason.NOT_SET);
	public final static EReason REASON_CLIENT_INITIATED =
		new EReason(EReason.CLIENT_INITIATED);
	public final static EReason REASON_SERVER_INITIATED =
		new EReason(EReason.SERVER_INITIATED);
	public final static EReason REASON_IO_ERROR = new EReason(EReason.IO_ERROR);

	/**
	 * EState 是一个枚举类，为当前连接到服务器的状态。
	 *
	* @author  listlike <a href="mailto:listlike@hotmail.com">
 	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*/
	
	public static final class EState
	{
		/* EState 枚举值常数 */
		public final static int NOT_SET = -1;
		public final static int DISCONNECTED = 0;
		public final static int CONNECTING = 1;
		public final static int CONNECTED = 2;

		/** 保存当前值 - 默认值为 'not set' */
		private int value = NOT_SET;

		/**
		 * 建立 <code>EState</code> 实例.
		 *
		 * @param newVal an <code>int</code> 值，当前的连接状态。
		 * @exception IllegalArgumentException 状态值不正确
		 */
		protected EState(int newVal) throws IllegalArgumentException
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
				case DISCONNECTED :
					return "DISCONNECTED";
				case CONNECTING :
					return "CONNECTING";
				case CONNECTED :
					return "CONNECTED";
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
	
	/**
	* EReason 是一个枚举类，当前服务器连接状态改变的原因。
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*/
	
	public final static class EReason
	{
		/* EReason 枚举的值  */
		public static final int NOT_SET = -1;
		public static final int CLIENT_INITIATED = 0;
		public static final int SERVER_INITIATED = 1;
		public static final int IO_ERROR = 2;
		
		/** 保存当前值  */
		private int value;

		/**
		* 建立 <code>EReason</code> 实例.
		*
		* @param reason an <code>int</code> 值，改变的原因。
	    * @exception IllegalArgumentException 状态值不正确
		*/
		protected EReason(int reason) throws IllegalArgumentException
		{
			if ((reason < -1) || (reason > 2))
				throw new IllegalArgumentException("Invalid Enumeration Value");
			value = reason;
		}
		/**
		 * <code>getValue</code> 返回当前枚举类型响应的值.
		 *
		 * @return  <code>int</code> 当前枚举类型响应的值
		 */
		public int getValue()
		{
			return value;
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
				case CLIENT_INITIATED :
					return "CLIENT_INITIATED";
				case SERVER_INITIATED :
					return "SERVER_INITIATED";
				case IO_ERROR :
					return "IO_ERROR";
				default :
					return "Internal Error";
			}
		}
	}

	private EState state, oldState;
	private EReason reason;

	/**
	 * 建立一个新的<code>ConnectionEvent</code> 实例.这个方法因该仅仅被ConnectionBean调用
	 *
	 * @param source an <code>Object</code> 当前的ConnectionBean；
	 * @param state an <code>Estate</code> 新的状态
	 * @param oldState an <code>EState</code> 老的状态
	 * @param reason an <code>Estate</code> 改变原因
	 */
	public ConnectionEvent(
		Object source,
		EState state,
		EState oldState,
		EReason reason)
	{
		super(source);
		this.state = state;
		this.oldState = oldState;
		this.reason = reason;
	}

	/**
	 * <code>getState</code> 当这个事件发生后，连接所处的状态。
	 *
	 * @return an <code>EState</code> 当前的状态
	 */
	public EState getState()
	{
		return state;
	}

	/**
	 * <code>getOldState</code> 当这个事件发生前，连接所处的状态。
	 *
	 * @return an <code>EState</code> 老的状态
	 */
	public EState getOldState()
	{
		return oldState;
	}

	/**
	 * <code>getReason</code> 返回状态改变的原因, 包括这些原因：连接失败 ，客户端请求断开，服务器请求断开。
	 *
	 * @return an <code>EReason</code> 改变原因
	 */
	public EReason getReason()
	{
		return reason;
	}
}
