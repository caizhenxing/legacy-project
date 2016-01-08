/**
 * 	@(#)SendOutEmail.java   Sep 13, 2006 12:56:23 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.email.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author zhang
 * @version Sep 13, 2006
 * @see
 */
public class SendOutEmail {

	String host = "";// smtp主机

	String username = "";

	String password = "";

	String to = "";// 收件人

	String from = "";// 发件人

	String filename = "";// 附件文件名

	String subject = "";// 邮件主题

	String content = "";// 邮件正文

	List<String> file = new ArrayList<String>();// 附件文件集合
	
	public SendOutEmail(String smtpServer, String username, String password) 
	{
		this.host = smtpServer;
		this.username = username;
		this.password = password;
	}

	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"GB2312"), "GB2312", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	private void attachfile(ArrayList<MailAttach> mailAttach) {
		MailAttach attach = null;
		if (0 != mailAttach.size()) {
			for (int i = 0; i < mailAttach.size(); i++) {
				attach = mailAttach.get(i);
				if (attach.getAttachFilePath() != null) {
					file.add(attach.getAttachFilePath());
					attach = null;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public boolean sendMail(Email email) throws IOException {
		this.subject = email.getMailTitle();
		this.content = email.getMailContent();
		this.from = email.getMailFrom();
		this.to = email.getMailTo();
		this.attachfile(email.getMailAttach());

		// 构造mail session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			subject = transferChinese(subject);
			msg.setSubject(subject);
			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(content);
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);
			// 向Multipart添加附件
			Iterator efile = file.iterator();
			while (efile.hasNext()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.next().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				// mbpFile.setFileName(fds.getName());
				// mbpFile.setFileName(new String(fds.getName().getBytes(),
				// "gb2312")); //防止附件标题是乱码
				mbpFile.setFileName(MimeUtility.encodeText(fds.getName(),
						"gb2312", "B"));
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);
			}
			file.clear();
			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			// 发送邮件
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
		return true;
	}
}
