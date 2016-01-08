package et.bo.oa.communicate.email.service.impl;

import java.util.ArrayList;

public class Email {
	private int mailMessageNumber;		//邮件唯一标示
	private String mailTitle;			//邮件主题
	private String mailFrom;			//邮件作者
	private String mailTo;				//发送到(在Send时使用)
	private String mailSendDate;		//发送时间
	private String mailContent;			//邮件正文
	private ArrayList<MailAttach> mailAttach;		//邮件附件
	private boolean isDelete = false;	//表示是否删除
	
	public ArrayList getMailAttach() {
		return mailAttach;
	}
	public void setMailAttach(ArrayList<MailAttach> mailAttach) {
		this.mailAttach = mailAttach;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailSendDate() {
		return mailSendDate;
	}
	public void setMailSendDate(String mailSendDate) {
		this.mailSendDate = mailSendDate;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public int getMailMessageNumber() {
		return mailMessageNumber;
	}
	public void setMailMessageNumber(int mailMessageNumber) {
		this.mailMessageNumber = mailMessageNumber;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

}
