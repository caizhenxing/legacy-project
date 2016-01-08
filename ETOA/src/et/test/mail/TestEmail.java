package et.test.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

public class TestEmail extends TestCase {

	Folder inbox;

	Store store;

	 public void testSendMail() throws MessagingException{
	 // Properties props = new Properties();
	 // Session session = Session.getInstance(props,null);
	 //
	 // MimeMessage message = new MimeMessage(session);
	 // message.setContent("Hello","text/plain");
	 // message.setText("hello");
	 // message.setSubject("First");
	 //
	 // Address address = new InternetAddress("lgstar888@163.com");
	 //Address address = new InternetAddress("lgstar888@163.com","zhangfeng");
		        
	 String host = "smtp.163.com";
	 String user = "lgstar888";
	 String password = "bystar888";
	 //String from = "lgstar888@163.com";
	 //String to = "lgstar888@163.com";
		        
	 //Properties props = System.getProperties();
	 Properties props = new Properties();
		        
	 props.put("mail.smtp.host",host);
	 props.put("mail.smtp.auth","true");
	 Session session = Session.getInstance(props);
		        
	 session.setDebug(true);
		        
	 //�����˵�ַ
	 InternetAddress from = new InternetAddress("zhaoyifei@tom.com");
		        
	 InternetAddress to = new InternetAddress("lgstar888");
		        
	 MimeMessage message = new MimeMessage(session);
	 message.setFrom(from);
	 message.addRecipient(Message.RecipientType.TO,to);
	 message.setSentDate(new Date());
	 message.setSubject("Hello JavaMail");
	 message.setText("Welcome to JavaMail");
	 message.saveChanges();
		        
	 Transport transport = session.getTransport("smtp");
	 transport.connect(host,user,password);
	 transport.sendMessage(message,message.getAllRecipients());
	 transport.close();
	 //Transport.send(message);
	 }

	// /**
	// * <p>�����ʼ�</p>
	// *
	// * @param info:�����ʼ�
	// *
	// * @return:
	// * @throws Exception
	// * @throws
	// */
	//
	// public void testEmailList() throws Exception{
	//
	//	        
	// String host = "pop.163.com";
	// String user = "lgstar888";
	// String password = "bystar888";
	//	        
	// Properties props = new Properties();
	// props.put("mail.pop3.host",host);
	// Session session = Session.getInstance(props);
	// store = session.getStore("pop3");
	// store.connect(host,user,password);
	//	        
	// inbox = store.getDefaultFolder().getFolder("INBOX");
	// inbox.open(Folder.READ_ONLY);
	//	        
	// Message[] msg = inbox.getMessages();
	//	        
	// FetchProfile profile = new FetchProfile();
	// profile.add(FetchProfile.Item.ENVELOPE);
	// inbox.fetch(msg,profile);
	//	        
	// for (int i = 0; i < msg.length; i++) {
	// if (msg[i].isMimeType("text/*")) {
	// this.handleText(msg[i]);
	// }else{
	// this.handleMultipart(msg[i]);
	// }
	// }
	// this.close();
	// }
	//	    
	// //�����κ�һ���ʼ�����Ҫ�ķ���
	// private void handle(Message msg) throws Exception{
	// 
	// System.out.println(
	// "�ʼ�����:"+msg.getFrom()[0].toString());
	// 
	// }
	//	    
	// //�����ı��ʼ�
	// public void handleText(Message msg) throws Exception{
	// this.handle(msg);
	// 
	// }
	//	    
	// public void handleMultipart(Message msg) throws Exception{
	// String disposition;
	// BodyPart part;
	//	        
	// Multipart mp = (Multipart)msg.getContent();
	// int mpCount = mp.getCount();
	// for (int m = 0;m<mpCount;m++) {
	// this.handle(msg);
	//	            
	// part = mp.getBodyPart(m);
	// disposition = part.getDisposition();
	// if (disposition!=null&&disposition.equals(part.ATTACHMENT)) {
	// //�Ƿ��и���
	// }else{
	// 
	// }
	// }
	// }
	//	    
	// private void saveAttach(BodyPart part) throws Exception{
	// String temp = part.getFileName();
	// String s = temp.substring(11,temp.indexOf("?=")-1);
	//	        
	// //�ļ�һ�㶼������base64����,�����ǽ���
	// String filename = this.base64Decoder(s);
	// 
	//	        
	// InputStream in = part.getInputStream();
	// FileOutputStream writer = new FileOutputStream(new File(filename));
	// byte[] content = new byte[255];
	// int read = 0;
	// while((read=in.read(content))!=-1){
	// writer.write(content);
	// }
	// writer.close();
	// in.close();
	// }
	//	    
	// //base64����
	// private String base64Decoder(String s) throws Exception{
	// BASE64Decoder decode = new BASE64Decoder();
	// byte[] b = decode.decodeBuffer(s);
	// return(new String(b));
	// }
	//	    
	// //�ر�����
	// public void close() throws Exception{
	// if (inbox!=null) {
	// inbox.close(false);
	// }
	//	        
	// if (store!=null) {
	// store.close();
	// }
	// }

//	public void testEmailList() throws Exception {
//
//		String host = "pop.163.com";
//		String user = "lgstar888";
//		String password = "bystar888";
//
//		Properties props = new Properties();
//		props.put("mail.pop3.host", host);
//		Session session = Session.getInstance(props);
//		store = session.getStore("pop3");
//		store.connect(host, user, password);
//
//		inbox = store.getDefaultFolder().getFolder("INBOX");
//		inbox.open(Folder.READ_ONLY);
//
//		Message[] msg = inbox.getMessages();
//
//		FetchProfile profile = new FetchProfile();
//		profile.add(FetchProfile.Item.ENVELOPE);
//		inbox.fetch(msg, profile);
//
//		for (int i = 0; i < msg.length; i++) {
//			if (msg[i].isMimeType("text/*")) {
//				this.handleText(msg[i]);
//			} else {
//				this.handleMultipart(msg[i]);
//			}
//		}
//		this.close();
//	}
//
//	// �����κ�һ���ʼ�����Ҫ�ķ���
//	private void handle(Message msg) throws Exception {
//		
//		
//		
//	}
//
//	// �����ı��ʼ�
//	public void handleText(Message msg) throws Exception {
//		this.handle(msg);
//		
//	}
//
//	public void handleMultipart(Message msg) throws Exception {
//		String disposition;
//		BodyPart part;
//
//		Multipart mp = (Multipart) msg.getContent();
//		int mpCount = mp.getCount();
//		for (int m = 0; m < mpCount; m++) {
//			this.handle(msg);
//
//			part = mp.getBodyPart(m);
//			disposition = part.getDisposition();
//			if (disposition != null && disposition.equals(part.ATTACHMENT)) {
//				// �Ƿ��и���
//				saveAttach(part);
//			} else {
//				
//				
//			}
//		}
//	}
//
//	private void saveAttach(BodyPart part) throws Exception {
//		String temp = part.getFileName();
//		// �õ�δ������ĸ�������
//		String s = temp.substring(11, temp.indexOf("?=") - 1);
//		// ȥ��header��footer
//
//		// �ļ���һ�㶼������base64����,�����ǽ���
//		String fileName = this.base64Decoder(s);
//		
//
//		InputStream in = part.getInputStream();
//		FileOutputStream writer = new FileOutputStream(new File(fileName));
//		byte[] content = new byte[255];
//		int read = 0;
//		while ((read = in.read(content)) != -1) {
//			writer.write(content);
//		}
//		writer.close();
//		in.close();
//	}
//
//	// base64����
//	private String base64Decoder(String s) throws Exception {
//		BASE64Decoder decode = new BASE64Decoder();
//		byte[] b = decode.decodeBuffer(s);
//		return (new String(b));
//	}
//
//	// �ر�����
//	public void close() throws Exception {
//		if (inbox != null) {
//			inbox.close(false);
//		}
//
//		if (store != null) {
//			store.close();
//		}
//	}

}
