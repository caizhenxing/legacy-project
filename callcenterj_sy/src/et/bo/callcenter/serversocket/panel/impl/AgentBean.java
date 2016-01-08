/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.Date;

/**
 * @author zhang feng
 * 
 */
public class AgentBean {
	// talk的id值
	private String talkId = "";

	// 交换机内线号(接听端口号)
	private String pbxPort = "";

	// 座席状态 开始接听，挂断电话，转入IVR等
	private String agentState = "";

	// 开始振铃时间
	private Date ringBegintime = null;

	// 开始接通时间
	private Date touchBegintime = null;

	// 等待时长
	private String waitTime = "";

	// 结束通话时长
	private Date touchEndtime = null;

	// 接续保持时长
	private String touchKeeptime = "";

	// 哪个口接的电话
	private String touchPost = "";

	// 录音路径
	private String recordPath = "";

	// 端口类型
	private String postType = "";

	// 应答人
	private String respondent = "";

	// 应答人类型(0 agent 1 expert)
	private String respondentType = "";

	// 接通时长
	private String keepTime = "";

	// 座席号
	private String agentNum = "";

	// 电话号码
	private String phoneNum = "";

	// 转接开始时间
	private Date displaceBegintime = null;

	// 转接保持时间
	private String displcaeKeeptime = "";

	// 接续类型 (2 两方通话 3 三方通话)
	private String processType = "";

	// 转出端口，从哪个端口转出去的
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
