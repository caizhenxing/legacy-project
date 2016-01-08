package com.ll.smsbeans;

import java.util.Vector;
import java.util.Enumeration;
import java.io.Serializable;

/**
 * <code>PacketRebroadcaster</code>  用于指定的beans允许预定过滤接口的类。 
 * 通过使用这个类重写PacketListener的成员，可以根据需求过滤包。
 * <p>
 * 要点:
 * <ul><li>
 * 枚举list(the Vector)的过程中如果试图改变list(the Vector)的时候会失败。可以预测到多种方法使用这个类
 * ，为了保证正确我在枚举list的之前先clone这个list。我很希望知道还有没有别的解决办法。
 *
 * </li><li>
 * 在发送事件的时候所有异常处理什么也不做，如果返回处理器，有可能造成处理中断。
 * </li></ul>
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public abstract class PacketRebroadcaster
	implements PacketListener, Serializable, PacketListenerRegistrar
{
	/** Vector holding people who are listening for packet events */
	private Vector listeners = new Vector();

	/**
	 * <code>registerListener</code> 在ConnectionBean注册监听, 用于监听事件。
	 *
	 * @param cb a <code>ConnectionBean</code> 值
	 */
	public void registerListener(ConnectionBean cb)
	{
		cb.addPacketListener(this);
	}

	/**
	 * <code>unregisterListener</code> 取消注册监听，以后不再监听事件。
	 *
	 * @param cb a <code>ConnectionBean</code> 值
	 */
	public void unregisterListener(ConnectionBean cb)
	{
		cb.delPacketListener(this);
	}

	/**
	 * PacketListener的成员
	 *
	 * @param pe  <code>PacketEvent</code> 值
	 */
	public abstract void receivedPacket(PacketEvent pe);

	/**
	 * PacketListener的成员
	 *
	 * @param pe  <code>PacketEvent</code> 值
	 */
	public abstract void sentPacket(PacketEvent pe);

	/**
	 * PacketListener的成员
	 *
	 * @param pe  <code>PacketEvent</code> 值
	 */
	public abstract void sendFailed(PacketEvent pe);

	/**
	 * <code>addPacketListener</code> 注册一个监听我们过滤过的包广播的监听器。
	 *
	 * @param l  <code>PacketListener</code> 值
	 */
	public final void addPacketListener(PacketListener l)
	{
		listeners.addElement(l);
	}

	/**
	 * <code>delPacketListener</code>取消注册一个监听我们过滤过的包广播的监听器。
	 *
	 * @param l  <code>PacketListener</code> 值
	 */
	public final void delPacketListener(PacketListener l)
	{
		if (listeners.contains(l))
			listeners.removeElement(l);
	}

	/**
	 * <code>fireReceived</code> 当PacketListener接收到接收包后调用的方法。 
	 * 这个方法发送相同的报到所有注册到这个发射机的对象。
	 *
	 * @param p  <code>PacketEvent</code> 值
	 */
	protected final void fireReceived(PacketEvent p)
	{
		Vector broadcast = (Vector) listeners.clone();
		Enumeration e = broadcast.elements();
		while (e.hasMoreElements())
		{
			try
			{
				((PacketListener) e.nextElement()).receivedPacket(p);
			} catch (Throwable e1)
			{
			}
		}
	}

	/**
	 * <code>fireReceived</code> 当PacketListener接收到发送包后调用的方法。 
	 * 这个方法发送相同的报到所有注册到这个发射机的对象。
	 *
	 * @param p  <code>PacketEvent</code> 值
	 */
	protected final void fireSent(PacketEvent p)
	{
		Vector broadcast = (Vector) listeners.clone();
		Enumeration e = broadcast.elements();
		while (e.hasMoreElements())
		{
			try
			{
				((PacketListener) e.nextElement()).sentPacket(p);
			} catch (Throwable e1)
			{
			}
		}
	}

	/**
	 * <code>fireReceived</code> 当PacketListener接收到发送包后调用的方法。 
	 * 这个方法发送相同的报到所有注册到这个发射机的对象。
	 *
	 * @param p a <code>PacketEvent</code> 值
	 */
	protected final void fireSendFailed(PacketEvent p)
	{
		Vector broadcast = (Vector) listeners.clone();
		Enumeration e = broadcast.elements();
		while (e.hasMoreElements())
		{
			try
			{
				((PacketListener) e.nextElement()).sendFailed(p);
			} catch (Throwable e1)
			{
			}
		}
	}
	

}
