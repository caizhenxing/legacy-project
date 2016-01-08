/**
 * 沈阳卓越科技有限公司
 * 2008-4-28
 */
package et.bo.callcenter.serversocket.panel.bean;

import java.util.Date;

import et.bo.common.mylog.MyLogPbx;
import excellence.common.util.time.TimeUtil;

/**
 * @author 王文权
 * 这是一个javabean 类记录座席端电话状态
 */
public class AgentStateBean {
	public AgentStateBean()
	{
		//System.out.println(new java.util.Date()+"##############################################");
	}
	
	private String callId = "";
	//座席号
	private String agentNum = "";
	//振铃 0:代表无状态,1:有状态 
	private String alertingCall = "";
	//摘机 0:代表无状态,1:有状态 
	private String offhook = "";
	//挂机 0:代表无状态,1:有状态 
	private String onhook = "";
	
	//振铃时间 alertingTime
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
