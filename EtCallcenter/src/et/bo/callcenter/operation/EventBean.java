/**
 * 	@(#)EventBean.java   2007-1-17 下午04:06:24
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.HashMap;
import java.util.Map;

 /**
 * @author zhaoyifei
 * @version 2007-1-17
 * @see
 */
public class EventBean {

	public enum EventType
	{
		T_RING,//外线振铃
		T_SIG,//信号音，多种类型，具体看参数
		T_POLARITY,//极性反转
		T_DIAL,//外线拨号
		T_CONNECT,//外线拨通
		T_HANGUP,//外线拨出的电话挂机
		
		U_OFFHOOK,//内线提机
		U_HANGUP,//内线挂机
		
		L_RECEDTMF,//有dtmf
		L_SENDDTMFEND,//发送完毕
		L_RECEFSK,//有fsk
		L_PLAYEND,//放音结束
		
		U_HOOKFLASH,//拍叉
		U_OFFHOOK_BYRING,//应答
		
		L_TIMEOUT,//超时
		
		L_MISSION//任务
		
	};
	public enum SigType
	{
		S_BUSY,//忙音
		
		S_NOBODY,//振铃若干次后没人接听
		S_NOSIGNAL,//没有信号音
		S_NODIALTONE//没有拨号音
	};
	/**
	 * 端口号
	 */
	private int lineNum;
	/**
	 * 事件类型
	 */
	private EventType et;
	/**
	 * 信号音类型
	 */
	private SigType st;
	/**
	 * 附带参数
	 */
	private Map<String,Object> params;
	/**
	 * @return the et
	 */
	public EventType getEt() {
		return et;
	}
	/**
	 * @param et the et to set
	 */
	public void setEt(EventType et) {
		this.et = et;
	}
	/**
	 * @return the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}
	/**
	 * @param lineNum the lineNum to set
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public Object getParam(String key)
	{
		if(params==null)
			return null;
		if(params.containsKey(key))
			return params.get(key);
		return null;
	}
	public void setParam(String key,Object value)
	{
		if(params==null)
			params=new HashMap<String,Object>();
		params.put(key, value);
	}
	/**
	 * @return the st
	 */
	public SigType getSt() {
		return st;
	}
	/**
	 * @param st the st to set
	 */
	public void setSt(SigType st) {
		this.st = st;
	}
}
