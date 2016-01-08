/**
 * 	@(#)OperOuterEmail.java   Aug 30, 2006 4:07:30 PM
 *	 �� 
 *	 
 */
package et.bo.oa.communicate.email.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sun.misc.BASE64Decoder;
import et.po.InemailInfo;
import excellence.common.util.Constants;

/**
 * �����ʼ���ȡ
 * 
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public class OperOuterEmail {

	private Folder inbox;

	private Store store;

	// �������������ַ
	private String SERVER_PATH = Constants
			.getProperty("outemail_save_in_local_path");

	private String OUTEMAIL_UPLOAD_PATH = Constants
			.getProperty("outemail_upload_out_localpath");

	// �ļ�·��
	public static List pathList = new ArrayList();

	/**
	 * ͨ��smtp�����������ʼ�
	 * 
	 * @param smtpAddress
	 *            ���� String smtp��ַ(��:smtp.163.com)
	 * @param user
	 *            ���� String smtp�����û���
	 * @param password
	 *            ���� String smtp��������
	 * @param sendUserAddress
	 *            ���� String �������ʼ���ַ
	 * @param sendUserName
	 *            ���� String ����������
	 * @param emailTitle
	 *            ���� String �������ʼ���ַ
	 * @param emailInfo
	 *            ���� String ��Ҫ����������Ϣ(��:lgstar888@163.com)
	 * @version Aug 30, 2006
	 * @return void
	 */
	public void sendOutEmail(String smtpAddress, String user, String password,
			String sendUserAddress, String sendUserName, String emailTitle,
			String emailInfo, List adjunctList) throws Exception {

		Email email = new Email();
		email.setMailTitle(emailTitle);// ����
		email.setMailContent(emailInfo);// ����
		email.setMailTo(sendUserAddress);// ���͵�
		email.setMailFrom(sendUserName);// ������

		ArrayList<MailAttach> arrayList = new ArrayList<MailAttach>();
		if (adjunctList != null) {
			Iterator it = adjunctList.iterator();
			while (it.hasNext()) {
				MailAttach attach = new MailAttach();
				String name = it.next().toString();
				attach.setAttachFilePath(OUTEMAIL_UPLOAD_PATH + name);
				arrayList.add(attach);
			}
			// arrayList = (ArrayList<MailAttach>) adjunctList;

		}
		email.setMailAttach(arrayList);

		SendOutEmail sendmail = new SendOutEmail(smtpAddress, user, password);
		sendmail.sendMail(email);

	}

	/**
	 * ͨ��pop�����������ʼ�
	 * 
	 * @param popHost
	 *            ���� String pop�˿���
	 * @param user
	 *            ���� String �û���
	 * @param password
	 *            ���� String ����
	 * @version Aug 30, 2006
	 * @return void
	 */
	public List getOutEmail(String popHost, String user, String password)
			throws Exception {
		List l = new ArrayList();

		Properties props = new Properties();
		props.put("mail.pop3.host", popHost);
		Session session = Session.getInstance(props);
		store = session.getStore("pop3");
		store.connect(popHost, user, password);

		inbox = store.getDefaultFolder().getFolder("INBOX");
		// inbox.open(Folder.READ_ONLY);
		inbox.open(Folder.READ_WRITE);

		Message[] msg = inbox.getMessages();

		FetchProfile profile = new FetchProfile();
		profile.add(FetchProfile.Item.ENVELOPE);

		inbox.fetch(msg, profile);

		for (int i = 0; i < msg.length; i++) {

			InemailInfo inemailInfo = new InemailInfo();
			// ����ɾ����־
			msg[i].setFlag(Flags.Flag.DELETED, true);
			// �ı��ʼ�
			if (msg[i].isMimeType("text/*")) {
				// this.handleText(msg[i]);
				inemailInfo.setEmailTitle(msg[i].getSubject());
				inemailInfo.setTakeUser(msg[i].getFrom()[0].toString());
				inemailInfo.setCreateTime(msg[i].getSentDate());
				inemailInfo.setEmailInfo(msg[i].getContent().toString());
				// System.out.println("msg.getcontent.tostring()====
				// "+msg[i].getContent().toString());
				l.add(inemailInfo);
			}
			// ������
			else {
				String disposition;
				BodyPart part;

				Multipart mp = (Multipart) msg[i].getContent();
				int mpCount = mp.getCount();
				for (int m = 0; m < mpCount; m++) {
					// 
					// System.out.println("�ʼ�����:" +
					// msg.getFrom()[0].toString());
					// 

					inemailInfo.setEmailTitle(msg[i].getSubject());
					inemailInfo.setTakeUser(msg[i].getFrom()[0].toString());
					inemailInfo.setCreateTime(msg[i].getSentDate());

					part = mp.getBodyPart(m);
					disposition = part.getDisposition();
					if (disposition != null
							&& disposition.equals(part.ATTACHMENT)) {
						// �Ƿ��и���
						saveAttach(part);
					} else {
						// 
						inemailInfo.setEmailInfo(part.getContent().toString());
					}
				}
				l.add(inemailInfo);
			}
		}
		this.close();

		return l;
	}

	// �����ı��ʼ�
	// public void handleText(Message msg) throws Exception {
	// 
	// 
	// 
	// 
	// }

	// public void handleMultipart(Message msg) throws Exception {
	// String disposition;
	// BodyPart part;
	//
	// Multipart mp = (Multipart) msg.getContent();
	// int mpCount = mp.getCount();
	// for (int m = 0; m < mpCount; m++) {
	// 
	// 
	// 
	//
	// part = mp.getBodyPart(m);
	// disposition = part.getDisposition();
	// if (disposition != null && disposition.equals(part.ATTACHMENT)) {
	// // �Ƿ��и���
	// saveAttach(part);
	// } else {
	// 
	// }
	// }
	// }

	/**
	 * ��������Ϣ�����ļ�ָ��·��
	 */
	private void saveAttach(BodyPart part) throws Exception {
		String temp = part.getFileName();
		String s = temp.substring(11, temp.indexOf("?=") - 1);

		// �ļ�һ�㶼������base64����,�����ǽ���
		String filename = this.base64Decoder(s);
		// 
		pathList.add(filename);

		filename = filename.substring(0, filename.length() - 1);

		InputStream in = part.getInputStream();
		FileOutputStream writer = new FileOutputStream(new File(SERVER_PATH
				+ filename));
		byte[] content = new byte[255];
		int read = 0;
		while ((read = in.read(content)) != -1) {
			writer.write(content);
		}
		writer.close();
		in.close();
	}

	// base64����
	private String base64Decoder(String s) throws Exception {
		BASE64Decoder decode = new BASE64Decoder();
		byte[] b = decode.decodeBuffer(s);
		return (new String(b));
	}

	// �ر�����
	public void close() throws Exception {
		if (inbox != null) {
			inbox.close(true);
			// inbox.close(false);
		}

		if (store != null) {
			store.close();
		}
	}

	/**
	 * ͨ��smtp�����������ʼ�
	 * 
	 * @param smtpAddress
	 *            ���� String smtp��ַ(��:smtp.163.com)
	 * @param user
	 *            ���� String smtp�����û���
	 * @param password
	 *            ���� String smtp��������
	 * @param sendUserAddress
	 *            ���� String �������ʼ���ַ
	 * @param sendUserName
	 *            ���� String ����������
	 * @param emailTitle
	 *            ���� String �������ʼ���ַ
	 * @param emailInfo
	 *            ���� String ��Ҫ����������Ϣ(��:lgstar888@163.com)
	 * @version Aug 30, 2006
	 * @return void
	 */
	public void answerOutEmail(String smtpAddress, String user,
			String password, String sendUserAddress, String sendUserName,
			String emailTitle, String emailInfo) throws Exception {

		Properties props = new Properties();

		props.put("mail.smtp.host", smtpAddress);
		props.put("mail.smtp.auth", "true");
		// ��ûỰ
		Session session = Session.getInstance(props);

		session.setDebug(true);

		// �����˵�ַ
		InternetAddress from = new InternetAddress(sendUserAddress);

		InternetAddress to = new InternetAddress(sendUserName);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(from);
		message.addRecipient(Message.RecipientType.TO, to);
		message.setSentDate(new Date());
		message.setSubject(emailTitle);
		message.setText(emailInfo);
		message.saveChanges();

		Transport transport = session.getTransport("smtp");
		transport.connect(smtpAddress, user, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	/**
	 * ���Է�������
	 * 
	 * @param
	 * @version Sep 8, 2006
	 * @return
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		OperOuterEmail operOuterEmail = new OperOuterEmail();
		// List l = new ArrayList();
		// l.add("C:\\test.rar");
		// operOuterEmail.sendOutEmail("smtp.163.com", "lgstar888", "bystar888",
		// "yepuliang@hotmail.com", "yepuliang@hotmail.com", "����������", "����������",
		// l);

		// Email email = new Email();
		// email.setMailTitle("�ǺǺ�1");// ����
		// email.setMailContent("�ǺǺ�2");// ����
		// email.setMailTo("zhaoyifei1@tom.com");// ���͵�
		// email.setMailFrom("lgstar888@163.com");// ������
		//
		// ArrayList<MailAttach> arrayList = new ArrayList<MailAttach>();
		// email.setMailAttach(arrayList);
		//
		// SendOutEmail sendmail = new SendOutEmail("smtp.163.com", "lgstar888",
		// "bystar888");
		// sendmail.sendMail(email);
		//		

		// List l = new ArrayList();
		// l = operOuterEmail.getOutEmail("pop.163.com", "lgstar888",
		// "bystar888");
		// Iterator it = l.iterator();
		// while (it.hasNext()) {
		// InemailInfo inemailInfo = (InemailInfo) it.next();
		// 
		// 
		// 
		// 
		// }

		// Iterator it1 = pathList.iterator();
		// while (it1.hasNext()) {
		// 
		// }

		// List l = null;
		//		
		// if (l!=null) {
		// Iterator it = l.iterator();
		// while(it.hasNext()){
		// 
		// }
		// }else{
		//
		// 
		// }
	}

}
