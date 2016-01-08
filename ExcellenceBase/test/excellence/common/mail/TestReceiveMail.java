/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-17下午03:03:15
 * 包名：excellence.common.mail
 * 文件名：TestReceiveMail.java
 * 制作者：Administrator
 * @version 1.0
 */
package excellence.common.mail;

import java.util.List;

import javax.mail.Flags;
import javax.mail.MessagingException;

import junit.framework.TestCase;

/**
 * 
 * @author Administrator
 * @version 1.0
 */
public class TestReceiveMail extends TestCase {

	/**
	 * Test method for {@link excellence.common.mail.ReceiveMail#getMailTotalCount()}.
	 */
	public void testGetMailTotalCount() {
		ReceiveMailService rm = new ReceiveMail();
		rm.setPassword("zhaoyifei");
		rm.setPop3("mail.sy-et.com");
		rm.setSmtp("mail.sy-et.com");
		rm.setUser("zhaoyf@sy-et.com");
		rm.connect();
		System.out.println(rm.getMailTotalCount());
		
	}

	/**
	 * Test method for {@link excellence.common.mail.ReceiveMail#getMailNewlCount()}.
	 */
	public void testGetMailNewlCount() {
		ReceiveMailService rm = new ReceiveMail();
		rm.setPassword("zhaoyifei");
		//rm.setPop3("mail.sy-et.com");
		rm.setImap("mail.sy-et.com");
		rm.setSmtp("mail.sy-et.com");
		rm.setUser("zhaoyf@sy-et.com");
		rm.imapConnect();
		System.out.println(rm.getMailNewlCount());
	}
	/**
	 * Test method for {@link excellence.common.mail.ReceiveMail#getUnreadMailCount()}.
	 */
	public void testGetUnreadMailCount() {
		ReceiveMailService rm = new ReceiveMail();
		//rm.setPop3("mail.sy-et.com");
		rm.setImap("mail.sy-et.com");
		rm.setSmtp("mail.sy-et.com");
		rm.setUser("zhaoyf@sy-et.com");
		rm.imapConnect();
		System.out.println(rm.getUnreadMailCount());
	}
	/**
	 * Test method for {@link excellence.common.mail.ReceiveMail#getMailList(int, int)}.
	 * @throws MessagingException 
	 */
	public void testGetMailList() throws MessagingException {
		ReceiveMailService rm = new ReceiveMail();
		rm.setPassword("zhaoyifei");
		rm.setPop3("mail.sy-et.com");
		rm.setSmtp("mail.sy-et.com");
		rm.setUser("zhaoyf@sy-et.com");
		rm.connect();
		List<MailBean> l = rm.getMailList(1, 5);
		for(MailBean mb:l)
		{
			System.out.println(mb.getMimeMessage().getMessageID());
			System.out.println(mb.getMimeMessage().getContentID());
			System.out.println(mb.getMimeMessage().getMessageNumber());
		}
		rm.closeFolder(true);
		rm.close();
		//System.out.println(rm.getMailList(1, 5).size());
	}

}
