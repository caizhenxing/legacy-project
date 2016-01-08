
package com.ll.smsbeans;


import java.util.EventObject;

/**
 *  <code>PacketEvent</code> 是connection当有数据包发送或接收时候发出的事件。 
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class PacketEvent extends EventObject
{
	/** 与事件有关的包 */
	private Packet packet = null;

	/** 
	 * 建立一个没有相关包的包事件。
	 * @param source 广播事件的对象。
	 */
	public PacketEvent(Object source)
	{
		super(source);
	}

	/** 
	 * 建立一个有相关包的包事件。
	 * @param source 广播事件的对象。
	 * @param packet 与事件有关的包。
	 */
	public PacketEvent(Object source, Packet packet)
	{
		this(source);
		this.packet = packet;
	}

	/**
	 * <code>getPacket</code> 返回和事件相关的包。
	 *
	 * @return a <code>Packet</code>  和事件相关的包，如果没有返回null。
	 */
	public Packet getPacket()
	{
		return packet;
	}
}
