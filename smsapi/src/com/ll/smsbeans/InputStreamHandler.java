package com.ll.smsbeans;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ll.smsbeans.cmpp3.CmppPacketParser;
import com.ll.smsbeans.log.LogCommon;

/**
 * 建立一个输入线程，并且将在这个线程中将接收到的数据包通过 <code>CmppPacketParser</code>
 * 拆包。
 * 
 * 其实不用一定在这个线程中拆包，在ConnetionBean中作这个操作也是可以的，好处就是能够处理更多的拆包事件，
 * 坏出就是ConnetionBean看起来不够优美了，呵呵。
 */
public final class InputStreamHandler extends Thread
{
	/** 输入流 */
	private InputStream in = null;

	/** 父对象 （ConnectionBean object）, 用于处理消息回调 */
	private ConnectionBean.InputStreamInterface isi = null;
	private CmppPacketParser cpp;

	private boolean parsingDone = false;

	static Logger _log;

	/**
	 * 建立 <code>InputStreamHandler</code> 实例。
	 *
	 * @param in  <code>InputStream</code> 服务器连接的输入流
	 */
	public InputStreamHandler(ConnectionBean.InputStreamInterface isi)
	{
		this.isi = isi;
		cpp = new CmppPacketParser();

	}

	/**
	* 设置器连接的输入流。
	*
	* @param in  <code>InputStream</code> 服务器连接的输入流
	*/
	public void setInputStream(InputStream in)
	{
		this.in = in;
	}

	/**
	 * 线程的主要部分，接收数据，拆包处理。
	 */
	public final void run()
	{

		if (in == null)
			throw new RuntimeException(
				"attempting to start InputStreamHandler without input to "
					+ "monitor");

		while (!parsingDone)
		{
			try
			{

				byte[] mybytes = new byte[4];

				in.read(mybytes);
				int packetlen =
					(0xff & mybytes[0])
						<< 24 | (0xff & mybytes[1])
						<< 16 | (0xff & mybytes[2])
						<< 8 | 0xff & mybytes[3];

				if (packetlen <= 0)
					throw new IOException("connect error!");

				byte[] databytes = new byte[packetlen - 4];

				int readLen = in.read(databytes);
				if (readLen != packetlen - 4)
					throw new IOException("connect error!");

				if(_log.isLoggable(LogCommon.DEBUG_LEVEL))
					_log.log(LogCommon.DEBUG_LEVEL,LogCommon.getLogBin("Received Data",mybytes, databytes));
				Packet p = cpp.parser(databytes);
				if (p != null)
					received(p);

			} catch (IOException e)
			{
				if (parsingDone == true)
					return;

				isi.unexpectedThreadDeath(e);
			} catch (Exception e)
			{
				_log.severe(e.getMessage());
				if (parsingDone == true)
					return;
				isi.unexpectedThreadDeath(e);
			}

		}

		_log.warning("InputStream: parsing done");
		parsingDone = true;
	}

	/**
	 * <code>received</code> 当InputStreamHandler接收到数据包后，这个方法回调ConnetionBean向其它对象广播接收事件，
	 *
	 * @param p a <code>Packet</code> which was just received and
	 * recognised.
	 */
	public void received(Packet p)
	{

		isi.received(p);
	}

	/**
	 * 终止运行。
	 *
	 */
	public void shutdown()
	{
		_log.fine("InputStream: shutdown");
		parsingDone = true;
		this.interrupt();
		_log.fine("InputStream: interrupted");
	}

	static {
		_log = Logger.getLogger("com.ll.smsbeans.InputStreamHandler");
	}

}
