/**
 * ����׿Խ�Ƽ����޹�˾
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

	// �����keyֵ
	private String mainKey = "";

	// ��ʼ�˿�
	private String beginPort = "";

	// ��ʼ�Ľ������˿ں�
	private String beginIvrPort = "";

	// �˿�����(TRUNK�м�,USER�û�)
	private String postType = "";

	// ��ʼʱ��
	private Date ringBeginTime = null;

	// ��������ʱ��
	private Date processEndTime = null;

	// ��������ʱ��
	private String processKeepTime = "";

	// ��������
	private String processType = "";

	// �������״̬�����е�IVR�У�����IVRת��ϯ������ͨ����
	private String runState = "";

	// �������
	private String inCommingPhoneNum = "";

	// ����ʹ���еĶ˿�
	private String usingPort = "";

	// �Զ���������״̬
	private List<IvrBean> ivrBean = new ArrayList<IvrBean>();

	// ��ϯ״̬
	private List<AgentBean> ageBean = new ArrayList<AgentBean>();

	// ����״̬
	private List<FaxBean> faxBean = new ArrayList<FaxBean>();

	// ¼��״̬
	private List<RecordBean> recordBean = new ArrayList<RecordBean>();

	// ����״̬
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
