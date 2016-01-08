/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-28
 */
package et.bo.callcenter.serversocket.panel.bean;

import java.util.Date;

import et.bo.common.mylog.MyLogPbx;
import excellence.common.util.time.TimeUtil;

/**
 * @author ����Ȩ
 * ����һ��javabean ���¼��ϯ�˵绰״̬
 */
public class AgentStateBean {
	public AgentStateBean()
	{
		//System.out.println(new java.util.Date()+"##############################################");
	}
	
	private String callId = "";
	//��ϯ��
	private String agentNum = "";
	//���� 0:������״̬,1:��״̬ 
	private String alertingCall = "";
	//ժ�� 0:������״̬,1:��״̬ 
	private String offhook = "";
	//�һ� 0:������״̬,1:��״̬ 
	private String onhook = "";
	
	//����ʱ�� alertingTime
	private Date alertingTime = null;
	private Date offhookTime = null;
	private Date onhookTime = null;
	
	public String getAgentNum() {
		return agentNum;
	}
	public void setAgentNum(String agentNum) {
		//System.out.println("##################################AgentNum is :"+agentNum+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
		this.agentNum = agentNum;
	}
	public String getAlertingCall() {
		return alertingCall;
	}
	public void setAlertingCall(String alertingCall) {
		this.alertingCall = alertingCall;
		//System.out.println("##################################alertingCall is :"+alertingCall+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
		if("1".equals(this.alertingCall))
		{
			MyLogPbx.info(this.getAgentNum()+"##################################alertingCall is :"+alertingCall+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
			alertingTime = new Date();
		}
	}
	public String getOffhook() {
		return offhook;
	}
	public void setOffhook(String offhook) {
		//System.out.println("##################################offhook is :"+offhook+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
		this.offhook = offhook;
		if("1".equals(this.offhook))
		{
			MyLogPbx.info(this.getAgentNum()+"##################################offhook is :"+offhook+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
			offhookTime = new Date();
		}
	}
	public String getOnhook() {
		return onhook;
	}
	public void setOnhook(String onhook) {
		
		this.onhook = onhook;
		if("1".equals(this.onhook))
		{
			MyLogPbx.info(this.getAgentNum()+"##################################onhook is :"+onhook+"@@@@@@"+TimeUtil.getTheTimeStr(new Date()));
			this.setAlertingCall("0");
			this.setOffhook("0");
			onhookTime = new Date();
		}
	}
	public Date getAlertingTime() {
		return alertingTime;
	}
	/*
	public void setAlertingTime(Date alertingTime) {
		this.alertingTime = alertingTime;
	}
	*/
	public Date getOffhookTime() {
		return offhookTime;
	}
	/*
	public void setOffhookTime(Date offhookTime) {
		this.offhookTime = offhookTime;
	}
	*/
	public Date getOnhookTime() {
		return onhookTime;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	
	/*
	public void setOnhookTime(Date onhookTime) {
		this.onhookTime = onhookTime;
	}
	*/

	
}
