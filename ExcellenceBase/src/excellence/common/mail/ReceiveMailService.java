package excellence.common.mail;

import java.util.List;

public interface ReceiveMailService {

	public abstract String getImap();

	public abstract void setImap(String imap);

	public abstract int getImapPort();

	public abstract void setImapPort(int imapPort);

	public final static String POP3_STR = "pop3";
	public final static String IMAP_STR = "imap";
	public final static String MAIL_SMTP_HOST = "mail.smtp.host";
	public final static String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public final static String INBOX_STR = "INBOX";

	public abstract boolean connect();

	public abstract boolean imapConnect();

	public abstract boolean close();

	public abstract boolean closeFolder(boolean expunge);

	public abstract int getMailTotalCount();

	public abstract int getMailNewlCount();

	public abstract int getUnreadMailCount();

	public abstract int getDeleteMailCount();

	public abstract List<MailBean> getMailList(int start, int end);

	public abstract String getSmtp();

	public abstract void setSmtp(String smtp);

	public abstract boolean isSmtpAuth();

	public abstract void setSmtpAuth(boolean smtpAuth);

	public abstract String getPop3();

	public abstract void setPop3(String pop3);

	public abstract int getPop3Port();

	public abstract void setPop3Port(int pop3Port);

	public abstract String getUser();

	public abstract void setUser(String user);

	public abstract String getPassword();

	public abstract void setPassword(String password);
	public abstract String getSaveAttachmentPath();
	public abstract void setSaveAttachmentPath(String saveAttachmentPath);
	public abstract String getDelete();
	public abstract void setDelete(String delete);

}