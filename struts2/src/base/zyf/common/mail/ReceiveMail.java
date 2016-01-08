/**
 * 
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-11下午03:55:04
 * 包名：base.zyf.common.mail
 * 文件名：ReceiveMail.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class ReceiveMail implements ReceiveMailService {

	private Session session ;   
	private URLName urln ;   
	private Store store ;
	private String saveAttachmentPath ;
	public String getSaveAttachmentPath() {
		return saveAttachmentPath;
	}
	public void setSaveAttachmentPath(String saveAttachmentPath) {
		this.saveAttachmentPath = saveAttachmentPath;
	}
	private Folder inbox ; 
	
	private String smtp ;
	private boolean smtpAuth = true;
	private String pop3 ;
	private int pop3Port = 110;
	private String user ;
	private String password;
	private Properties props ;
	private String delete = "N";
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	private String imap ;
	
	private int imapPort = 143;
	
	
	
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getImap()
	 */
	public String getImap() {
		return imap;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setImap(java.lang.String)
	 */
	public void setImap(String imap) {
		this.imap = imap;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getImapPort()
	 */
	public int getImapPort() {
		return imapPort;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setImapPort(int)
	 */
	public void setImapPort(int imapPort) {
		this.imapPort = imapPort;
	}
	public ReceiveMail()
	{
		props = System.getProperties();
	}
	public ReceiveMail(Properties props)
	{
		this.props = props;  
        
	}
	public ReceiveMail(String smtp, String pop3, boolean smtpAuth, int pop3Port,
			 String user, String password)
	{
		
		
		
		this.password = password;
		this.smtp = smtp;
		this.smtpAuth = smtpAuth;
		this.pop3 = pop3;
		this.pop3Port = pop3Port;
		this.user = user;
		props = System.getProperties();
		props.put(MAIL_SMTP_HOST, this.smtp);   
        props.put(MAIL_SMTP_AUTH, Boolean.toString(this.smtpAuth));   
        
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#connect()
	 */
	public boolean connect()
	{
		session = Session.getDefaultInstance(props, null);   
        urln = new URLName(POP3_STR, this.pop3, this.pop3Port, null,   
                this.user, this.password);   
        
		try {
			store = session.getStore(urln);
			store.connect();
			this.inbox = store.getFolder("INBOX");  
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   
		return true;
        
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#imapConnect()
	 */
	public boolean imapConnect()
	{
		session = Session.getDefaultInstance(props, null);   
        urln = new URLName(IMAP_STR, this.imap, this.imapPort, null,   
                this.user, this.password);   
        
		try {
			store = session.getStore(urln);
			store.connect();
			this.inbox = store.getFolder("INBOX");  
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   
		return true;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#close()
	 */
	public boolean close()
	{
		try {
			store.close();
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#closeFolder(boolean)
	 */
	public boolean closeFolder(boolean expunge)
	{
		try {
			this.inbox.close(expunge);
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getMailTotalCount()
	 */
	public int getMailTotalCount()
	{
		int count = -1;
		try {
			inbox.open(Folder.READ_WRITE);
			count = inbox.getMessageCount();
			inbox.close(false);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getMailNewlCount()
	 */
	public int getMailNewlCount()
	{
		int count = -1;
		try {
			inbox.open(Folder.READ_WRITE);
			count = inbox.getNewMessageCount();
			inbox.close(false);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return count;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getUnreadMailCount()
	 */
	public int getUnreadMailCount()
	{
		int count = -1;
		try {
			inbox.open(Folder.READ_WRITE);
			count = inbox.getUnreadMessageCount();
			inbox.close(false);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return count;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getDeleteMailCount()
	 */
	public int getDeleteMailCount()
	{
		int count = -1;
		try {
			inbox.open(Folder.READ_WRITE);
			count = inbox.getDeletedMessageCount();
			inbox.close(false);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return count;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getMailList(int, int)
	 */
	public List<MailBean> getMailList(int start, int end)
	{
		List<MailBean> result = new ArrayList<MailBean>();
		Message message[];
		try {
			inbox.open(Folder.READ_WRITE);
			message = inbox.getMessages(start, end);
			MailBean mb;
			for (int i = 0; i < message.length; i++) {   
				mb = new MailBean((MimeMessage) message[i]);
				if(this.delete.equals("Y"))
				{
				((MimeMessage) message[i]).setFlag(Flags.Flag.DELETED, true);
				}
				result.add(mb);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getSmtp()
	 */
	public String getSmtp() {
		return smtp;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setSmtp(java.lang.String)
	 */
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#isSmtpAuth()
	 */
	public boolean isSmtpAuth() {
		return smtpAuth;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setSmtpAuth(boolean)
	 */
	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getPop3()
	 */
	public String getPop3() {
		return pop3;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setPop3(java.lang.String)
	 */
	public void setPop3(String pop3) {
		this.pop3 = pop3;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getPop3Port()
	 */
	public int getPop3Port() {
		return pop3Port;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setPop3Port(int)
	 */
	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getUser()
	 */
	public String getUser() {
		return user;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setUser(java.lang.String)
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.ReceiveMailService#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
