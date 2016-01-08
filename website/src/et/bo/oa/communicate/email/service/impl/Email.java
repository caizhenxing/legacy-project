package et.bo.oa.communicate.email.service.impl;

import java.util.ArrayList;

public class Email {
	private int mailMessageNumber;		//�ʼ�Ψһ��ʾ
	private String mailTitle;			//�ʼ�����
	private String mailFrom;			//�ʼ�����
	private String mailTo;				//���͵�(��Sendʱʹ��)
	private String mailSendDate;		//����ʱ��
	private String mailContent;			//�ʼ�����
	private ArrayList<MailAttach> mailAttach;		//�ʼ�����
	private boolean isDelete = false;	//��ʾ�Ƿ�ɾ��
	
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
