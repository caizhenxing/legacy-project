package et.bo.police.callcenter.server;

import java.util.Date;

public class CallcenterInfo {
	private String clientId;
	private String tagIo;
	private String phonenum;
	private Date curtime;
	private String fileLink;
	private String content;
	private boolean validate;
	private boolean excuteResult;
	private String remark;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCurtime() {
		return curtime;
	}
	public void setCurtime(Date curtime) {
		this.curtime = curtime;
	}
	public boolean isExcuteResult() {
		return excuteResult;
	}
	public void setExcuteResult(boolean excuteResult) {
		this.excuteResult = excuteResult;
	}
	public String getFileLink() {
		return fileLink;
	}
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTagIo() {
		return tagIo;
	}
	public void setTagIo(String tagIo) {
		this.tagIo = tagIo;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}
