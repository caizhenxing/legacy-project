/*
 * 创建日期 2004-12-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/*
 * 文件名：HotmailDemo.java
 * 创建时间：2004-9-14
 * 创建者：liudong
 */

	

	import java.util.Date;
	import java.util.Properties;

	import javax.mail.Folder;
	import javax.mail.Message;
	import javax.mail.Session;
	import javax.mail.Store;
	import javax.mail.Transport;
	import javax.mail.internet.InternetAddress;
	import javax.mail.internet.MimeMessage;

	/**
	 * Hotmail邮件的收发器
	 * @author liudong
	 */
	public class HotmailDemo 
	{
		public static void main(String[] args) {
			receive();
		}

		/**
		 * 邮件接收
		 */
		protected static void receive() 
		{
			try 
			{
				Properties prop = new Properties();
				Session ses = Session.getInstance(prop);
				//使用JDAVMail Provider
				Store store = ses.getStore("davmail");
				//无需指定服务器地址
				store.connect(null, "你的帐号","密码");
				if (store.isConnected()) 
				{
					Folder inbox = store.getFolder("INBOX");
					if (inbox.exists()) 
					{
						inbox.open(Folder.READ_ONLY);
						int nCount = inbox.getMessageCount();
						System.out.println("Inbox contains " + nCount + " messages");
						// 依次显示收件箱中的每封邮件
						for(int i=1;i<=nCount;i++)
						{
							MimeMessage msg = (MimeMessage) inbox.getMessage(i);
							System.out.println("Subject : " + msg.getSubject());
							System.out.println("From : " + msg.getFrom()[0].toString());
							System.out.println("Content type : " + msg.getContentType());
							System.out.println(msg.getContent());
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	

	/**
		 * 邮件发送
		 */
		protected static void send() 
		{
			try 
			{
				Properties prop = new Properties();
				//邮件发送者地址
				prop.setProperty("mail.davmail.from","abc@hotmail.com");
				Session ses = Session.getInstance(prop);
				//获得JDAVMail的邮件发送实例
				Transport transport = ses.getTransport("davmail_xmit");
				//连接到Hotmail服务器，请替换为自己的用户名和口令
				transport.connect(null, "用户名","口令");

				// 准备要发送的邮件
				MimeMessage txMsg = new MimeMessage(ses);
				txMsg.setSubject("This is the subject");

				//邮件发送者地址
				InternetAddress addrFrom = new InternetAddress("abc@hotmail.com");
				txMsg.setFrom(addrFrom);

				//邮件接收者地址
				InternetAddress addrTo = new InternetAddress("cdef@hotmail.com", "cdef");
				txMsg.addRecipient(Message.RecipientType.TO, addrTo);

				//邮件内容
				txMsg.setText("Hello world !");
				txMsg.setSentDate(new Date());

				//发送邮件
				transport.sendMessage(txMsg, txMsg.getAllRecipients());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

