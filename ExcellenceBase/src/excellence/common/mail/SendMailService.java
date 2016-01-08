package excellence.common.mail;

public interface SendMailService {

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getHostName();

	public abstract void setHostName(String hostName);

	public abstract String getEmail();

	public abstract void setEmail(String email);

	public abstract String getUser();

	public abstract void setUser(String user);

	public abstract void sendMail(SendMailBean smb);

}