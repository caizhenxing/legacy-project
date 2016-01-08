/*
 * 创建日期 2004-9-16
 *
 * 
 */
package com.ll.smsbeans.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.logging.Logger;

import com.ll.smsbeans.ConnectionAdapter;
import com.ll.smsbeans.ConnectionBean;
import com.ll.smsbeans.DeliverBean;
import com.ll.smsbeans.IdBuilder;
import com.ll.smsbeans.PacketEvent;
import com.ll.smsbeans.PacketListener;
import com.ll.smsbeans.SyncMessageBean;
import com.ll.smsbeans.cmpp3.CmppDeliver;
import com.ll.smsbeans.cmpp3.CmppSubmitBuilder;
import com.ll.smsbeans.log.LogCommon;

/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class MessageBeanTest
{
	/**
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	*/
	public class DeliverPacketListener implements PacketListener
	{

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.PacketListener#receivedPacket(com.ll.smsbeans.PacketEvent)
		 */
		public void receivedPacket(PacketEvent pe)
		{
			_log.log(LogCommon.DEBUG_LEVEL, "接收到的包！！！" + pe.getPacket());

			MessageBeanTest.this.db.sendDeliverResp(
				(CmppDeliver) pe.getPacket(),
				0);

		}

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.PacketListener#sentPacket(com.ll.smsbeans.PacketEvent)
		 */
		public void sentPacket(PacketEvent pe)
		{

			_log.log(LogCommon.DEBUG_LEVEL, "发送成功的包！！" + pe.getPacket());

		}

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.PacketListener#sendFailed(com.ll.smsbeans.PacketEvent)
		 */
		public void sendFailed(PacketEvent pe)
		{
			_log.log(LogCommon.DEBUG_LEVEL, "发送失败的包！！" + pe.getPacket());
		}

	}
	/**
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	*/
	public class MessageBeanThread extends Thread
	{

		SyncMessageBean mb;

		public MessageBeanThread(SyncMessageBean mb)
		{
			this.mb = mb;
		}

		/* （非 Javadoc）
		 * @see java.lang.Runnable#run()
		 */
		public void run()
		{

			while (true)
			{

				CmppSubmitBuilder cm = new CmppSubmitBuilder();
				IdBuilder.getInstance().getMessageId();
				cm.setMsgId(IdBuilder.getInstance().getMessageId());
				cm.setPkTotal(1);
				cm.setPkNumber(1);
				cm.setRegisterDelivery(0);
				cm.setMsgLevel(1);
				//cm.setServiceId("01850");
				cm.setFeeUserType(0);
				//cm.setFeeTermId("");

				cm.setFeeTermType(0);
				cm.setTpPid(0);
				cm.setTpUdhi(0);
				cm.setMsgFmt(15);
				cm.setMsgSrc("901234");
				cm.setFeeType("01");
				cm.setFeeCode("000000");
				cm.setAtTime("0000");
				cm.setValidTime("55555");
				cm.setSrcTermId("901234");
				cm.setDstTermType(0);
				cm.addDstTermId("13803882229");
				cm.addDstTermId("13803884444");

				cm.addDstTermId("13803881234");

				cm.setLinkId("");
				int r;
				r =
					mb.sendSubmit(
						cm,
						"这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234");

				if (r == 0)
					_log.info("发送Submit包成功");
				else
					_log.info("发送Submit包失败result=" + r);
				cm.setMsgId(IdBuilder.getInstance().getMessageId());
				r =
					mb.sendSubmit(
						cm,
						"这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234这个是测试!!abc1234");
				if (r == 0)
					_log.info("发送Submit包成功");
				else
					_log.info("发送Submit包失败result=" + r);
				//				CmppQueryBuilder cqb = new CmppQueryBuilder();
				//				cqb.setQueryCode("test");
				//				cqb.setQueryType(CmppQuery.QUERY_TYPE_TOTAL);
				//				cqb.setReserve("");
				//				cqb.setTime("aaaaa");
				//
				//				CmppQueryResp cqr;
				//				cqr = mb.sendQuery(cqb);
				//
				//				System.err.println(cqr);
				//
				//				CmppCancelBuilder ccb = new CmppCancelBuilder();
				//				ccb.setMsgId(0x0102030405060708L);
				//
				//				System.err.println(
				//					"mb.sendCancel( ccb) =" + mb.sendCancel(ccb));

				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException e)
				{
					// TODO 自动生成 catch 块
					e.printStackTrace();
					return;
				}

			}
		}

		/* （非 Javadoc）
		 * @see java.lang.Thread#start()
		 */
		public synchronized void start()
		{

			super.start();
		}

	}
	/**
	*
	* @author  listlike <a href="mailto:listlike@hotmail.com">
	*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
	*
	*/
	public class MyConnectionAdapter extends ConnectionAdapter
	{

		/* （非 Javadoc）
		* @see com.ll.smsbeans.ConnectionAdapter#connected()
		*/
		public void connected()
		{

			super.connected();

			mbt1 = new MessageBeanThread(mb1);
			mbt2 = new MessageBeanThread(mb1);
			mbt1.start();
			mbt2.start();

		}

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.ConnectionAdapter#connectFailed()
		 */
		public void connectFailed()
		{

			super.connectFailed();

			mbt1 = null;
			mbt2 = null;

			if (!isEnd)
				reConnectServer();

		}

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.ConnectionAdapter#connecting()
		 */
		public void connecting()
		{

			super.connecting();
		}

		/* （非 Javadoc）
		 * @see com.ll.smsbeans.ConnectionAdapter#disconnected()
		 */
		public void disconnected()
		{

			super.disconnected();
			if (mbt1 != null)
				mbt1.interrupt();
			if (mbt2 != null)
				mbt2.interrupt();

			mbt1 = null;
			mbt2 = null;

			if (!isEnd)
				reConnectServer();
		}

	}

	private InetAddress addr;
	private ConnectionBean cb;
	private SyncMessageBean mb1;

	private MessageBeanThread mbt1;
	private MessageBeanThread mbt2;
	private DeliverBean db;

	private boolean isEnd;

	private Logger _log = Logger.getLogger("com.ll.smsbeans.MessageBeanTest");

	public MessageBeanTest()
	{
		cb = new ConnectionBean("901234", "1234");
		cb.addConnectionListener(new MyConnectionAdapter());

		db = new DeliverBean(cb);
		db.addPacketListener(new DeliverPacketListener());

		mb1 = new SyncMessageBean(cb);

		mbt1 = null;
		mbt2 = null;

		isEnd = false;
	}

	public void reConnectServer()
	{
		int flag;
		do
		{
			try
			{

				Thread.sleep(10000L);
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			flag = connectServer();
		} while (flag != 0);

	}

	public int connectServer()
	{

		try
		{
			cb.connect(addr = InetAddress.getByName("localhost")); //连接地址
		} catch (java.net.UnknownHostException e)
		{ //from InetAddress.getByName()
			java.lang.System.err.println("DNS error finding your server:");
			java.lang.System.err.println(e.toString());

			//TODO
			return 1;

		} catch (java.io.IOException e)
		{ //from connect
			java.lang.System.err.println(
				"IO error while attempting to connect to server:");
			java.lang.System.err.println(e.toString());

			//TODO 
			return 1;

		}

		return 0;
	}

	public void DisconnectServer()
	{
		//mbt1.stop();
		//mbt2.stop();
		isEnd = true;
		cb.disconnect();
	}
	public static void main(String[] args)
	{

		LogCommon.logInit();
		MessageBeanTest mbs = new MessageBeanTest();

		if (mbs.connectServer() != 0)
			mbs.reConnectServer();

		String commLine = null;
		BufferedReader commRead =
			new BufferedReader(new InputStreamReader(System.in));

		do
		{
			try
			{

				commLine = (commRead.readLine()).trim().toLowerCase();
				if (commLine.equals("exit"))
					break;

			} catch (Exception e)
			{
				break;
			}

		} while (true);

		mbs.DisconnectServer();
		return;
	}

}
