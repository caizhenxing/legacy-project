/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhang feng
 * 
 */
public class CtiBean {

	// 主表的key值
	private String mainKey = "";

	// 初始端口
	private String beginPort = "";

	// 开始的交换机端口号
	private String beginIvrPort = "";

	// 端口类型(TRUNK中继,USER用户)
	private String postType = "";

	// 开始时间
	private Date ringBeginTime = null;

	// 接续结束时间
	private Date processEndTime = null;

	// 接续保持时间
	private String processKeepTime = "";

	// 接续类型
	private String processType = "";

	// 监控运行状态，运行到IVR中，进入IVR转座席，正在通话等
	private String runState = "";

	// 来电号码
	private String inCommingPhoneNum = "";

	// 正在使用中的端口
	private String usingPort = "";

	// 自动语音服务状态
	private List<IvrBean> ivrBean = new ArrayList<IvrBean>();

	// 座席状态
	private List<AgentBean> ageBean = new ArrayList<AgentBean>();

	// 传真状态
	private List<FaxBean> faxBean = new ArrayList<FaxBean>();

	// 录音状态
	private List<RecordBean> recordBean = new ArrayList<RecordBean>();

	// 会议状态
	private List<ConfBean> confBean = new ArrayList<ConfBean>();

	public List<AgentBean> getAgeBean() {
		return ageBean;
	}

	public void setAgeBean(List<AgentBean> ageBean) {
		this.ageBean = ageBean;
	}

	public List<ConfBean> getConfBean() {
		return confBean;
	}

	public void setConfBean(List<ConfBean> confBean) {
		this.confBean = confBean;
	}

	public List<FaxBean> getFaxBean() {
		return faxBean;
	}

	public void setFaxBean(List<FaxBean> faxBean) {
		this.faxBean = faxBean;
	}

	public List<IvrBean> getIvrBean() {
		return ivrBean;
	}

	public void setIvrBean(List<IvrBean> ivrBean) {
		this.ivrBean = ivrBean;
	}

	public List<RecordBean> getRecordBean() {
		return recordBean;
	}

	public void setRecordBean(List<RecordBean> recordBean) {
		this.recordBean = recordBean;
	}

	public String getBeginPort() {
		return beginPort;
	}

	public void setBeginPort(String beginPort) {
		this.beginPort = beginPort;
	}

	public String getRunState() {
		return runState;
	}

	public void setRunState(String runState) {
		this.runState = runState;
	}

	public String getInCommingPhoneNum() {
		return inCommingPhoneNum;
	}

	public void setInCommingPhoneNum(String inCommingPhoneNum) {
		this.inCommingPhoneNum = inCommingPhoneNum;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getProcessKeepTime() {
		return processKeepTime;
	}

	public void setProcessKeepTime(String processKeepTime) {
		this.processKeepTime = processKeepTime;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public Date getProcessEndTime() {
		return processEndTime;
	}

	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}

	public Date getRingBeginTime() {
		return ringBeginTime;
	}

	public void setRingBeginTime(Date ringBeginTime) {
		this.ringBeginTime = ringBeginTime;
	}

	public String getUsingPort() {
		return usingPort;
	}

	public void setUsingPort(String usingPort) {
		this.usingPort = usingPort;
	}

	public String getBeginIvrPort() {
		return beginIvrPort;
	}

	public void setBeginIvrPort(String beginIvrPort) {
		this.beginIvrPort = beginIvrPort;
	}
}
