/*
 * �������� 2004-12-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
/*
 * �ļ�����HotmailDemo.java
 * ����ʱ�䣺2004-9-14
 * �����ߣ�liudong
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
	 * Hotmail�ʼ����շ���
	 * @author liudong
	 */
	public class HotmailDemo 
	{
		public static void main(String[] args) {
			receive();
		}

		/**
		 * �ʼ�����
		 */
		protected static void receive() 
		{
			try 
			{
				Properties prop = new Properties();
				Session ses = Session.getInstance(prop);
				//ʹ��JDAVMail Provider
				Store store = ses.getStore("davmail");
				//����ָ����������ַ
				store.connect(null, "����ʺ�","����");
				if (store.isConnected()) 
				{
					Folder inbox = store.getFolder("INBOX");
					if (inbox.exists()) 
					{
						inbox.open(Folder.READ_ONLY);
						int nCount = inbox.getMessageCount();
						System.out.println("Inbox contains " + nCount + " messages");
						// ������ʾ�ռ����е�ÿ���ʼ�
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
		 * �ʼ�����
		 */
		protected static void send() 
		{
			try 
			{
				Properties prop = new Properties();
				//�ʼ������ߵ�ַ
				prop.setProperty("mail.davmail.from","abc@hotmail.com");
				Session ses = Session.getInstance(prop);
				//���JDAVMail���ʼ�����ʵ��
				Transport transport = ses.getTransport("davmail_xmit");
				//���ӵ�Hotmail�����������滻Ϊ�Լ����û����Ϳ���
				transport.connect(null, "�û���","����");

				// ׼��Ҫ���͵��ʼ�
				MimeMessage txMsg = new MimeMessage(ses);
				txMsg.setSubject("This is the subject");

				//�ʼ������ߵ�ַ
				InternetAddress addrFrom = new InternetAddress("abc@hotmail.com");
				txMsg.setFrom(addrFrom);

				//�ʼ������ߵ�ַ
				InternetAddress addrTo = new InternetAddress("cdef@hotmail.com", "cdef");
				txMsg.addRecipient(Message.RecipientType.TO, addrTo);

				//�ʼ�����
				txMsg.setText("Hello world !");
				txMsg.setSentDate(new Date());

				//�����ʼ�
				transport.sendMessage(txMsg, txMsg.getAllRecipients());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

