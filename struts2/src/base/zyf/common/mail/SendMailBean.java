/**
 * 
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-11下午06:14:48
 * 包名：base.zyf.common.mail
 * 文件名：SendMailBean.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailAttachment;
/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class SendMailBean {
	private List<String> toAddrs = new ArrayList<String>();
	private List<String> ccs = new ArrayList<String>();
	private List<String> bccs = new ArrayList<String>();
	
	private String subject;
	private String msg;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private String fromAddr;
	private List<EmailAttachment> attachments;
	
	public List<String> getCcs() {
		return ccs;
	}
	public void setCcs(List<String> ccs) {
		this.ccs = ccs;
	}
	public List<String> getBccs() {
		return bccs;
	}
	public void setBccs(List<String> bccs) {
		this.bccs = bccs;
	}

	public List<String> getToAddrs() {
		return toAddrs;
	}
	public void setToAddrs(List<String> toAddrs) {
		this.toAddrs = toAddrs;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public List<EmailAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addToAddr(String addr)
	{
		if(toAddrs == null)
		{
			toAddrs = new ArrayList<String>();
		}
		String[] tos = StringUtils.split(addr, ";");
		for(int i=0;i<tos.length;i++)
		{
			toAddrs.add(tos[i]);
		}
	}
	public void addCc(String cc)
	{
		if(ccs == null)
		{
			ccs = new ArrayList<String>();
		}
		String[] ccss = StringUtils.split(cc, ";");
		for(int i=0;i<ccss.length;i++)
		{
			bccs.add(ccss[i]);
		}
		
	}
	public void addBccs(String bcc)
	{
		if(bccs == null)
		{
			bccs = new ArrayList<String>();
		}
		String[] bccss = StringUtils.split(bcc, ";");
		for(int i=0;i<bccss.length;i++)
		{
			bccs.add(bccss[i]);
		}
	}
	public void addAttachments(String path)
	{
		if(this.attachments == null)
		{
			this.attachments = new ArrayList<EmailAttachment>();
		}
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(path);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		this.attachments.add(attachment);
	}
	public void addAttachments(String path, String name, String url, 
			String disposition, String description)
	{
		if(this.attachments == null)
		{
			this.attachments = new ArrayList<EmailAttachment>();
		}
		EmailAttachment attachment = new EmailAttachment();
		if(StringUtils.isNotBlank(path))
		{
		attachment.setPath(path);
		}
		if(StringUtils.isNotBlank(description))
		{
		attachment.setDescription(description);
		}
		if(StringUtils.isNotBlank(name))
		{
		attachment.setName(name);
		}
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		this.attachments.add(attachment);
	}
}
