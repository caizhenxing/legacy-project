/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.Date;

/**
 * @author zhang feng
 * 
 */
public class AgentBean {
	// talk��idֵ
	private String talkId = "";

	// ���������ߺ�(�����˿ں�)
	private String pbxPort = "";

	// ��ϯ״̬ ��ʼ�������Ҷϵ绰��ת��IVR��
	private String agentState = "";

	// ��ʼ����ʱ��
	private Date ringBegintime = null;

	// ��ʼ��ͨʱ��
	private Date touchBegintime = null;

	// �ȴ�ʱ��
	private String waitTime = "";

	// ����ͨ��ʱ��
	private Date touchEndtime = null;

	// ��������ʱ��
	private String touchKeeptime = "";

	// �ĸ��ڽӵĵ绰
	private String touchPost = "";

	// ¼��·��
	private String recordPath = "";

	// �˿�����
	private String postType = "";

	// Ӧ����
	private String respondent = "";

	// Ӧ��������(0 agent 1 expert)
	private String respondentType = "";

	// ��ͨʱ��
	private String keepTime = "";

	// ��ϯ��
	private String agentNum = "";

	// �绰����
	private String phoneNum = "";

	// ת�ӿ�ʼʱ��
	private Date displaceBegintime = null;

	// ת�ӱ���ʱ��
	private String displcaeKeeptime = "";

	// �������� (2 ����ͨ�� 3 ����ͨ��)
	private String processType = "";

	// ת���˿ڣ����ĸ��˿�ת��ȥ��
	private String outPort = "";

	public String getOutPort() {
		return outPort;
	}

	public void setOutPort(String outPort) {
		this.outPort = outPort;
	}

	public String getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}

	public String getKeepTime() {
		return keepTime;
	}

	public void setKeepTime(String keepTime) {
		this.keepTime = keepTime;
	}

	public String getPbxPort() {
		return pbxPort;
	}

	public void setPbxPort(String pbxPort) {
		this.pbxPort = pbxPort;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public String getAgentState() {
		return agentState;
	}

	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}

	public String getDisplcaeKeeptime() {
		return displcaeKeeptime;
	}

	public void setDisplcaeKeeptime(String displcaeKeeptime) {
		this.displcaeKeeptime = displcaeKeeptime;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getRespondentType() {
		return respondentType;
	}

	public void setRespondentType(String respondentType) {
		this.respondentType = respondentType;
	}

	public String getTalkId() {
		return talkId;
	}

	public void setTalkId(String talkId) {
		this.talkId = talkId;
	}

	public String getTouchKeeptime() {
		return touchKeeptime;
	}

	public void setTouchKeeptime(String touchKeeptime) {
		this.touchKeeptime = touchKeeptime;
	}

	public String getTouchPost() {
		return touchPost;
	}

	public void setTouchPost(String touchPost) {
		this.touchPost = touchPost;
	}

	public Date getDisplaceBegintime() {
		return displaceBegintime;
	}

	public void setDisplaceBegintime(Date displaceBegintime) {
		this.displaceBegintime = displaceBegintime;
	}

	public Date getRingBegintime() {
		return ringBegintime;
	}

	public void setRingBegintime(Date ringBegintime) {
		this.ringBegintime = ringBegintime;
	}

	public Date getTouchBegintime() {
		return touchBegintime;
	}

	public void setTouchBegintime(Date touchBegintime) {
		this.touchBegintime = touchBegintime;
	}

	public Date getTouchEndtime() {
		return touchEndtime;
	}

	public void setTouchEndtime(Date touchEndtime) {
		this.touchEndtime = touchEndtime;
	}
}
