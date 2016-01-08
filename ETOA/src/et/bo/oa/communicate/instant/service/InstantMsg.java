package et.bo.oa.communicate.instant.service;

import java.util.Date;

public class InstantMsg {

	private Date sendTime=null;
	private String sendUser=null;
	private String contents=null;
	private String receiveUser=null;
	private boolean receive=false;
	public InstantMsg()
	{
		
	}
	public InstantMsg(InstantMsg im)
	{
		this.sendTime=im.getSendTime();
		this.sendUser=im.getSendUser();
		this.contents=im.getContents();
		this.receive=im.isReceive();
		this.receiveUser=im.getReceiveUser();
	}
	public boolean isReceive() {
		return receive;
	}
	public void setReceive(boolean receive) {
		this.receive = receive;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
}
