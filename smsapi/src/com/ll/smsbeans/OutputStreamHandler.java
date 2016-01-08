package com.ll.smsbeans;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ll.smsbeans.log.LogCommon;

/**
 *	建立一个输出线程。为发送的数据建立一个支持线程的队列，使数据包的发送可以支持多线程。
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 */

public final class OutputStreamHandler extends Thread
{
	/** 输出流 */
	private OutputStream out;

	/** 父连接，用于在包发送成功后发出成功事件 */
	private ConnectionBean.OutputStreamInterface osi;

	/** 发送包队列 */
	private final LockingQueue output = new LockingQueue();

	/** 是否立即终止线程的运行 */
	private boolean keepRunning = true;

	static Logger _log;
	/**
	 *建立一个新的 <code>OutputStreamHandler</code> 实例。
	 *
	 * @param out  <code>OutputStream</code> 输出流
	 */
	public OutputStreamHandler(ConnectionBean.OutputStreamInterface osi)
	{
		this.osi = osi;
	}

	/**
	*设置输出流。
	*
	* @param out  <code>OutputStream</code> 输出流
	*/
	public void setOutputStream(OutputStream out)
	{
		this.out = out;
	}

	/**
	 * 把需要发送包加入发送队列。
	 *
	 * @param p Packet 发送包。
	 */
	public final void send(Packet p)
	{
		if (keepRunning)
			output.put(p);
		else
			osi.sendFailed(p);
	}

	/**
	 * <code>shutdown</code> 通知线程结束运行。
	 */
	public void shutdown()
	{
		_log.fine("OutputStream: shutdown");
		keepRunning = false;
		output.putLast(null);

		_log.fine("OutputStream: closing queue");
	}

	/**
	 * 线程终止处理方法。
	 * @param ex <code>Exception</code> 造成中止的异常。
	 * @param p <code>Packet</code> 当前发送的包。
	 */

	public void handleThreadDeath(Exception ex, Packet p)
	{
		_log.warning("OutputStream: thread death");
		if (p != null)
			osi.sendFailed(p);
		Object pkt = output.getLast();
		while (pkt != null)
		{
			osi.sendFailed((Packet) pkt);
			pkt = output.getLast();
		}
		osi.unexpectedThreadDeath(ex);
	}

	/**
	 * 线程终止处理方法。
	 * @param ex <code>Exception</code> 造成中止的异常。
	 */
	public void handleThreadDeath(Exception ex)
	{
		handleThreadDeath(ex, null);
	}

	/**
	 * 线程循环发送发送队列中的数据包。
	 */
	public final void run()
	{
		if (out == null)
			throw new RuntimeException("starting output thread without any IO set up to use");
		Packet p = null;
		while (keepRunning)
		{
			try
			{
				p = (Packet) output.get();

			} catch (final InterruptedException e)
			{
				_log.severe("OutputStream: interrupted");
			}

			if (p != null)
			{
				try
				{
					byte[] data = p.getContent();
					int len = data.length + 4;
					byte[] mybytes = new byte[4];

					mybytes[3] = (byte) (0xff & len);
					mybytes[2] = (byte) ((0xff00 & len) >> 8);
					mybytes[1] = (byte) ((0xff0000 & len) >> 16);
					mybytes[0] = (byte) ((0xff000000 & len) >> 24);

					out.write(mybytes);
					out.write(data);
					out.flush();
					if (_log.isLoggable(LogCommon.DEBUG_LEVEL))
						_log.log(
							LogCommon.DEBUG_LEVEL,
							LogCommon.getLogBin("Sent Data", mybytes, data));
				} catch (IOException e)
				{

					//错误处理
					handleThreadDeath(e, p);
					return;
				}
				//发送成功消息
				osi.sent(p);
			}
		}

		_log.warning("OutputStream: stopped");
	}

	/**
	*线程安全的发送队列，这个队列没有大小没有限制。 
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	*/
	private final class LockingQueue
	{
		private LinkedList m_queue = new LinkedList();
		private boolean m_closed = false;
		private boolean m_reject = false;
		private int m_waiting = 0;

		/**
		* 加入对象
		* @param item <code>Object</code> 加入队列的对象。
		*/
		public synchronized final void put(Object item)
		{
			if (m_closed || m_reject)
				return;
			m_queue.addLast(item);
			notify(); //#notify
		}

		/**
		* 加入最后一个发送对象，加入后对列自动关闭，不能再加入对象。
		* 
		* @param item <code>Object</code> 加入队列的对象。
		*/

		public synchronized final void putLast(Object item)
		{
			put(item);
			m_reject = true;
		}

		/**
		 * 获取对象
		 *  
		 * @param timeout 超时设置。
		 * @return  <code>Object</code> 获取的对象
		 * @throws InterruptedException 
		 */
		public synchronized final Object get(long timeout)
			throws InterruptedException
		{
			long _expire = System.currentTimeMillis() + timeout;
			if (m_closed)
				return null;
			try
			{
				if (m_queue.size() <= 0)
				{
					++m_waiting;
					while (m_queue.size() <= 0)
					{
						wait(timeout);

						if (timeout > 0
							&& System.currentTimeMillis() > _expire)
						{
							--m_waiting;
							throw new InterruptedException("LockingQueue : timeout to dequeue");
						}

						if (m_closed)
						{
							--m_waiting;
							return null;
						}
					}
					--m_waiting;
				}

				Object head = m_queue.removeFirst();

				if (m_queue.size() == 0 && m_reject)
					close(); // 移出最后一个对象，关闭队列。

				return head;
			} catch (NoSuchElementException e) // 会发生吗？
			{
				throw new Error("LockingQueue: internal error");
			}
		}
		/**
		 * 获得最后对象
		 * @return 最后对象
		 */
		public final Object getLast()
		{
			if (m_queue.size() <= 0)
				return null;
			else
				return m_queue.removeFirst();
		}

		/**
		 * 获取对象
		 *  
		 * @return  <code>Object</code> 获取的对象
		 * @throws InterruptedException 
		 */
		public synchronized final Object get() throws InterruptedException
		{
			return get(0);
		}
		/**
		 * 队列是否为空
		 * @return 队列是否为空
		 */
		public final boolean isEmpty()
		{
			return m_queue.size() <= 0;
		}

		/**
		* 等待的线程数。
		* @return 队列的大小
		*/
		public final int size()
		{
			return m_waiting;
		}

		/**
		 * 关闭队列
		 */
		public synchronized void close()
		{
			m_closed = true;
			m_queue = null;
			notifyAll();
		}
	}
	/* （非 Javadoc）
	 * @see java.lang.Thread#start()
	 */
	public synchronized void start()
	{
		// TODO 自动生成方法存根
		keepRunning = true;
		super.start();
	}

	static {
		_log = Logger.getLogger("com.ll.smsbeans.OutputStreamHandler");
	}

}