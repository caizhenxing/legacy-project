/**
 * 	@(#)TelephoneLine.java   2007-1-4 下午02:09:22
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.test.impl;

 /**
 * @author zhaoyifei
 * @version 2007-1-4
 * @see
 */
public class TelephoneLine {
	public enum TeleLineState{
		CH_FREE,//空闲
		CH_RECEIVEID,//外线收码
		CH_WAITSECONDRING,//等待第二次振铃
		CH_WELCOME,//这里是东进电话银行演示系统
		CH_ACCOUNT,//请输入8位帐号
		CH_ACCOUNT1,//请输入8位帐号
		CH_PASSWORD,//请输入6位密码
		CH_PASSWORD1,//请输入6位密码
		CH_SELECT,//选择功能
		CH_SELECT1,//选择功能
		CH_PLAYRESULT,//播放查询结果
		CH_RECORDFILE,//录制留言
		CH_PLAYRECORD,//播放留言
		CH_OFFHOOK,//内线摘机
		CH_WAITUSERONHOOK//等待内线挂机
		};
		public enum TeleLineType{
			IN_LINE,//内线
			OUT_LINE,//外线
			RECORD_LINE,//录音
			EMPTY_LINE//悬空
		};
	private TeleLineType type;
	private TeleLineState state;
	private String callId;
	private char[] dtmf;
	private int timeElapse;
	private int line;
	public TelephoneLine(){
		dtmf=new char[32];
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public char[] getDtmf() {
		return dtmf;
	}
	public char getDtmf(int i) {
		return dtmf[i];
	}
	public void setDtmf(char dtmf,int i) {
		this.dtmf[i]=dtmf;
	}
	public void setDtmf(char[] dtmf) {
		this.dtmf=dtmf;
	}
	public int getTimeElapse() {
		return timeElapse;
	}
	public void setTimeElapse(int timeElapse) {
		this.timeElapse = timeElapse;
	}
	public TeleLineState getState() {
		return state;
	}
	public void setState(TeleLineState state) {
		this.state = state;
	}
	public TeleLineType getType() {
		return type;
	}
	public void setType(TeleLineType type) {
		this.type = type;
	}
	
}
