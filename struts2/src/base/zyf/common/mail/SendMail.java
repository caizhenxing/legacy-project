/**
 * 
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-11下午06:00:51
 * 包名：base.zyf.common.mail
 * 文件名：SendMail.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class SendMail implements SendMailService {
	private String hostName ;
	private String email ;
	private String user;
	private String password;
	
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#getHostName()
	 */
	public String getHostName() {
		return hostName;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#setHostName(java.lang.String)
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#getEmail()
	 */
	public String getEmail() {
		return email;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#getUser()
	 */
	public String getUser() {
		return user;
	}
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#setUser(java.lang.String)
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	
	/* (non-Javadoc)
	 * @see base.zyf.common.mail.SendMailService#sendMail(base.zyf.common.mail.SendMailBean)
	 */
	public void sendMail(SendMailBean smb)
	{
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(this.hostName);
		email.setAuthentication(this.user,this.password);
		
		try {
			for(String toAddr : smb.getToAddrs())
			{
				email.addTo(toAddr);
			}
			for(String cc : smb.getCcs())
			{
				email.addCc(cc);
			}
			for(String bcc : smb.getBccs())
			{
				email.addBcc(bcc);
			}
			email.setFrom(smb.getFromAddr());
			email.setSubject(smb.getSubject());
			email.setMsg(smb.getMsg());
			if(smb.getAttachments() != null)
			{
			//添加附件
			for(EmailAttachment attachment : smb.getAttachments() )
			{
				email.attach(attachment);
			}
			}
			//发送邮件
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
