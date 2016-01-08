package com.ll.smsbeans;

/**
 * <code>PacketListenerRegistrar</code> 用于对象注册PacketListener的接口。
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 */


public interface PacketListenerRegistrar
{
	/**
	 * <code>addPacketListener</code> 让你注册一个响应包事件的 <code>
	 * PacketListener</code> 。
	 *
	 * @param l <code>PacketListener</code> 需要注册的PacketListener。
	 */
	void addPacketListener(PacketListener l);

	/**
	 * <code>delPacketListener</code> 取消注册一个响应包事件的 <code>
	 * PacketListener</code> 。
	 *
	 * @param l <code>PacketListener</code> 需要取消注册的PacketListener。
	 */
	void delPacketListener(PacketListener l);
}
