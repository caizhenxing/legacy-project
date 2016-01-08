package com.ll.smsbeans;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * 这个类是ConnectionListener接口的一个简单实现。
 * 这个类对连接事件进行了简单的处理。
 * 可以通过继承这个类的方法容易的实现对连接事件的处理，而不用关注后台的情况。
 *
 *<p>
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class ConnectionAdapter implements ConnectionListener, Serializable
{	
	
	static Logger _log;
	/**
	 * <code>connectionChanged</code> 接收连接状态的改变事件，将事件及时转发到响应的方法
	 *
	 * @param evt a <code>ConnectionEvent</code> 连接事件
	 */
	public void connectionChanged(ConnectionEvent evt)
	{
		switch (evt.getState().getValue())
		{
			case ConnectionEvent.EState.DISCONNECTED :
				if (evt.getOldState() == ConnectionEvent.STATE_CONNECTING)
					connectFailed(evt);
				else
					disconnected(evt);
				break;
			case ConnectionEvent.EState.CONNECTING :
				connecting(evt);
				break;
			case ConnectionEvent.EState.CONNECTED :
				connected(evt);
				break;
			default :
				throw new RuntimeException("Illegal Connection state imput to ConnectionAdapter");
		}
	}

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	  */
	public void connected(ConnectionEvent evt)
	{

		connected();
		;
	}

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	 */
	public void disconnected(ConnectionEvent evt)
	{
		disconnected();
	}
	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	  */
	public void connecting(ConnectionEvent evt)
	{
		connecting();
	}
	/** @deprecated  代替  {@link #connectionChanged(ConnectionEvent)},
	  */
	public void connectFailed(ConnectionEvent evt)
	{
		connectFailed();
	};

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	 */
	public void connected()
	{
		_log.info("与服务器成功连接！！！");
	}

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	  */
	public void disconnected()
	{
		_log.info("与服务器连接已断开！！！");
	}

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	  */
	public void connecting()
	{
		_log.info("正在连接服务器！！！");
	}

	/** @deprecated  代替 {@link #connectionChanged(ConnectionEvent)},
	  */
	public void connectFailed()
	{
		_log.info("与服务器连接失败！！！");
	}
	
	static {
		_log = Logger.getLogger("com.ll.smsbeans.ConnectionAdapter");
		
	}
}
