/**
 * 
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.List;

/**
 * 座席员面板
 * 
 * @author 张锋
 * 
 */
public class AgentPanel {
	// 座席号
	private String agentNum = "";

	// 座席状态(当前座席员正在接电话,空闲等状态)
	private String agentState = "";

	// 座席用户名
	private String agentName = "";

	// 座席登入时间
	private String agentLoginTime = "";

	// 此座席日咨询量
	private String dayNum = "";

	// 栏目咨询量列表
	private List lamReferNum;

	// 服务总时间
	private String serviceAllTime = "";

	// 当前排队数
	private String waitCalledNum = "";

	// 来电记录
	private String callingStore = "";

	// 在线主持人列表
	private List onlinePreside;

	// 在线专家列表
	private List onlineExpert;

	// 登陆状态(登入,注销)
	private String loginState = "";

	// 是否震铃
	private String isRing = "";

	// 是否从震铃到摘机动作发生
	private String isoffHook = "";

	// 是否从摘机到挂机动作发生
	private String isonHook = "";

	// 临时离开状态(暂停,开通)
	private String signState = "";

	// 开通时所需输入密码
	private String passwordState = "";

	// 外线接入后等待听音乐或者继续进行通话(等待,继续)
	private String waitState = "";

	// 外线呼入后,未接听时震铃状态(接听,挂断)
	private String telState = "";

	// 由人工转入自动语音功能
	private String toIVR = "";

	// 电话接起后,转给内线其它座席员或者转给外线
	private String turnState = "";

	// 电话本功能状态
	private String telBookState = "";

	// 座席员外呼状态(外呼,禁呼)
	private String outCallState = "";

	// 收听录音按钮状态
	private String listenWavState = "";

	// 三方通话按钮状态
	private String threeCallState = "";

	// 三方通话开始呼叫
	private String threeCalling = "";

	// 三方通话开始通话
	private String threeCallbegin = "";

	// 强拆
	private String qiangchai = "";

	// 强插
	private String qiangcha = "";

	// 座席挂
	private String agentHold = "";

	// 专家挂
	private String expertHold = "";

	// 三方挂
	private String threeHold = "";

	// 超时按钮状态
	private String superTimeState = "";

	// 电话会议按钮状态
	private String phoneConfenceState = "";

	// 结束延时状态
	private String endSuperTimeState = "";

	// 用户评价按钮状态
	private String userEvaluateState = "";

	// 转接内线
	private String bZhuanniexian = "";

	// 转接外线
	private String bZhuanjiewaixian = "";

	// 等待，继续
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
