/**
 * 
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.List;

/**
 * ��ϯԱ���
 * 
 * @author �ŷ�
 * 
 */
public class AgentPanel {
	// ��ϯ��
	private String agentNum = "";

	// ��ϯ״̬(��ǰ��ϯԱ���ڽӵ绰,���е�״̬)
	private String agentState = "";

	// ��ϯ�û���
	private String agentName = "";

	// ��ϯ����ʱ��
	private String agentLoginTime = "";

	// ����ϯ����ѯ��
	private String dayNum = "";

	// ��Ŀ��ѯ���б�
	private List lamReferNum;

	// ������ʱ��
	private String serviceAllTime = "";

	// ��ǰ�Ŷ���
	private String waitCalledNum = "";

	// �����¼
	private String callingStore = "";

	// �����������б�
	private List onlinePreside;

	// ����ר���б�
	private List onlineExpert;

	// ��½״̬(����,ע��)
	private String loginState = "";

	// �Ƿ�����
	private String isRing = "";

	// �Ƿ�����嵽ժ����������
	private String isoffHook = "";

	// �Ƿ��ժ�����һ���������
	private String isonHook = "";

	// ��ʱ�뿪״̬(��ͣ,��ͨ)
	private String signState = "";

	// ��ͨʱ������������
	private String passwordState = "";

	// ���߽����ȴ������ֻ��߼�������ͨ��(�ȴ�,����)
	private String waitState = "";

	// ���ߺ����,δ����ʱ����״̬(����,�Ҷ�)
	private String telState = "";

	// ���˹�ת���Զ���������
	private String toIVR = "";

	// �绰�����,ת������������ϯԱ����ת������
	private String turnState = "";

	// �绰������״̬
	private String telBookState = "";

	// ��ϯԱ���״̬(���,����)
	private String outCallState = "";

	// ����¼����ť״̬
	private String listenWavState = "";

	// ����ͨ����ť״̬
	private String threeCallState = "";

	// ����ͨ����ʼ����
	private String threeCalling = "";

	// ����ͨ����ʼͨ��
	private String threeCallbegin = "";

	// ǿ��
	private String qiangchai = "";

	// ǿ��
	private String qiangcha = "";

	// ��ϯ��
	private String agentHold = "";

	// ר�ҹ�
	private String expertHold = "";

	// ������
	private String threeHold = "";

	// ��ʱ��ť״̬
	private String superTimeState = "";

	// �绰���鰴ť״̬
	private String phoneConfenceState = "";

	// ������ʱ״̬
	private String endSuperTimeState = "";

	// �û����۰�ť״̬
	private String userEvaluateState = "";

	// ת������
	private String bZhuanniexian = "";

	// ת������
	private String bZhuanjiewaixian = "";

	// �ȴ�������
	private String bWaitAndContinue = "";

	public String getBZhuanniexian() {
		return bZhuanniexian;
	}

	public void setBZhuanniexian(String zhuanniexian) {
		bZhuanniexian = zhuanniexian;
	}

	public String getAgentLoginTime() {
		return agentLoginTime;
	}

	public void setAgentLoginTime(String agentLoginTime) {
		this.agentLoginTime = agentLoginTime;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}

	public String getAgentState() {
		return agentState;
	}

	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}

	public String getCallingStore() {
		return callingStore;
	}

	public void setCallingStore(String callingStore) {
		this.callingStore = callingStore;
	}

	public String getDayNum() {
		return dayNum;
	}

	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}

	public String getEndSuperTimeState() {
		return endSuperTimeState;
	}

	public void setEndSuperTimeState(String endSuperTimeState) {
		this.endSuperTimeState = endSuperTimeState;
	}

	public List getLamReferNum() {
		return lamReferNum;
	}

	public void setLamReferNum(List lamReferNum) {
		this.lamReferNum = lamReferNum;
	}

	public String getListenWavState() {
		return listenWavState;
	}

	public void setListenWavState(String listenWavState) {
		this.listenWavState = listenWavState;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}

	public List getOnlineExpert() {
		return onlineExpert;
	}

	public void setOnlineExpert(List onlineExpert) {
		this.onlineExpert = onlineExpert;
	}

	public List getOnlinePreside() {
		return onlinePreside;
	}

	public void setOnlinePreside(List onlinePreside) {
		this.onlinePreside = onlinePreside;
	}

	public String getOutCallState() {
		return outCallState;
	}

	public void setOutCallState(String outCallState) {
		this.outCallState = outCallState;
	}

	public String getPasswordState() {
		return passwordState;
	}

	public void setPasswordState(String passwordState) {
		this.passwordState = passwordState;
	}

	public String getPhoneConfenceState() {
		return phoneConfenceState;
	}

	public void setPhoneConfenceState(String phoneConfenceState) {
		this.phoneConfenceState = phoneConfenceState;
	}

	public String getServiceAllTime() {
		return serviceAllTime;
	}

	public void setServiceAllTime(String serviceAllTime) {
		this.serviceAllTime = serviceAllTime;
	}

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getSuperTimeState() {
		return superTimeState;
	}

	public void setSuperTimeState(String superTimeState) {
		this.superTimeState = superTimeState;
	}

	public String getTelBookState() {
		return telBookState;
	}

	public void setTelBookState(String telBookState) {
		this.telBookState = telBookState;
	}

	public String getTelState() {
		return telState;
	}

	public void setTelState(String telState) {
		this.telState = telState;
	}

	public String getThreeCallState() {
		return threeCallState;
	}

	public void setThreeCallState(String threeCallState) {
		this.threeCallState = threeCallState;
	}

	public String getToIVR() {
		return toIVR;
	}

	public void setToIVR(String toIVR) {
		this.toIVR = toIVR;
	}

	public String getTurnState() {
		return turnState;
	}

	public void setTurnState(String turnState) {
		this.turnState = turnState;
	}

	public String getUserEvaluateState() {
		return userEvaluateState;
	}

	public void setUserEvaluateState(String userEvaluateState) {
		this.userEvaluateState = userEvaluateState;
	}

	public String getWaitCalledNum() {
		return waitCalledNum;
	}

	public void setWaitCalledNum(String waitCalledNum) {
		this.waitCalledNum = waitCalledNum;
	}

	public String getWaitState() {
		return waitState;
	}

	public void setWaitState(String waitState) {
		this.waitState = waitState;
	}

	public String getIsRing() {
		return isRing;
	}

	public void setIsRing(String isRing) {
		this.isRing = isRing;
	}

	public String getIsoffHook() {
		return isoffHook;
	}

	public void setIsoffHook(String isoffHook) {
		this.isoffHook = isoffHook;
	}

	public String getIsonHook() {
		return isonHook;
	}

	public void setIsonHook(String isonHook) {
		this.isonHook = isonHook;
	}

	public String getBZhuanjiewaixian() {
		return bZhuanjiewaixian;
	}

	public void setBZhuanjiewaixian(String zhuanjiewaixian) {
		bZhuanjiewaixian = zhuanjiewaixian;
	}

	public String getBWaitAndContinue() {
		return bWaitAndContinue;
	}

	public void setBWaitAndContinue(String waitAndContinue) {
		bWaitAndContinue = waitAndContinue;
	}

	public String getThreeCallbegin() {
		return threeCallbegin;
	}

	public void setThreeCallbegin(String threeCallbegin) {
		this.threeCallbegin = threeCallbegin;
	}

	public String getThreeCalling() {
		return threeCalling;
	}

	public void setThreeCalling(String threeCalling) {
		this.threeCalling = threeCalling;
	}

	public String getAgentHold() {
		return agentHold;
	}

	public void setAgentHold(String agentHold) {
		this.agentHold = agentHold;
	}

	public String getExpertHold() {
		return expertHold;
	}

	public void setExpertHold(String expertHold) {
		this.expertHold = expertHold;
	}

	public String getQiangcha() {
		return qiangcha;
	}

	public void setQiangcha(String qiangcha) {
		this.qiangcha = qiangcha;
	}

	public String getQiangchai() {
		return qiangchai;
	}

	public void setQiangchai(String qiangchai) {
		this.qiangchai = qiangchai;
	}

	public String getThreeHold() {
		return threeHold;
	}

	public void setThreeHold(String threeHold) {
		this.threeHold = threeHold;
	}
}
