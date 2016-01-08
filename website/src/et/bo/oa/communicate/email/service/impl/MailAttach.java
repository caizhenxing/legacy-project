/**
 * 	@(#)MailAttach.java   Sep 13, 2006 1:14:13 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.email.service.impl;

public class MailAttach
{
	private int attachSize;
	private String attachFileName;
	private String attachFilePath;	//这是Send时用到的
	
	public String getAttachFileName() {
		return attachFileName;
	}
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	public int getAttachSize() {
		return attachSize;
	}
	public void setAttachSize(int attachSize) {
		this.attachSize = attachSize;
	}
	public String getAttachFilePath() {
		return attachFilePath;
	}
	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}
	
}