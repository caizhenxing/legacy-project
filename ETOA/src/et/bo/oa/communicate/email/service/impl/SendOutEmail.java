/**
 * 	@(#)SendOutEmail.java   Sep 13, 2006 12:56:23 PM
 *	 �� 
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

	String host = "";// smtp����

	String username = "";

	String password = "";

	String to = "";// �ռ���

	String from = "";// ������

	String filename = "";// �����ļ���

	String subject = "";// �ʼ�����

	String content = "";// �ʼ�����

	List<String> file = new ArrayList<String>();// �����ļ�����
	
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

		// ����mail session
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
			// ����MimeMessage ���趨������ֵ
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			subject = transferChinese(subject);
			msg.setSubject(subject);
			// ����Multipart
			Multipart mp = new MimeMultipart();
			// ��Multipart�������
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(content);
			// ��MimeMessage��ӣ�Multipart�������ģ�
			mp.addBodyPart(mbpContent);
			// ��Multipart��Ӹ���
			Iterator efile = file.iterator();
			while (efile.hasNext()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.next().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				// mbpFile.setFileName(fds.getName());
				// mbpFile.setFileName(new String(fds.getName().getBytes(),
				// "gb2312")); //��ֹ��������������
				mbpFile.setFileName(MimeUtility.encodeText(fds.getName(),
						"gb2312", "B"));
				// ��MimeMessage��ӣ�Multipart��������
				mp.addBodyPart(mbpFile);
			}
			file.clear();
			// ��Multipart���MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			// �����ʼ�
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
