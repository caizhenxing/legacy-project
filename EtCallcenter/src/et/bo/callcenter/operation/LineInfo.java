/**
 * 	@(#)LineInfo.java   2007-1-15 下午03:46:09
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class LineInfo {

	
	public enum LineType{
		IN_LINE,//内线
		OUT_LINE,//外线
		RECORD_LINE,//录音
		EMPTY_LINE//悬空
	};
	
	public enum LineState{
		L_FREE,//空闲
		
		//L_CONNECT,//接通 外线振铃后摘机，外线拨号后接通
		L_CONFERENCE,//会议
		L_LINK,//通话
		
		L_SENDDTMF,//发码
		L_PLAY,//放音状态
		L_PLAY_SIG,//特殊放音，信号音
		L_PLAYINDEX,//索引放音状态
		L_RECORD,//录音
		
		U_RING,//内线振铃
		U_OFFHOOK,//内线摘机
		
		T_CONNECT,//外线振铃后摘机，外线拨号后接通
		T_OFFHOOK,//外线摘机
		T_RING,//外线振铃
		
		};
	/**
	 * 任务bean
	 */
	private AbsMissionBean mb=null;	
	/**
	 * line号码，物理号
	 */
	private int lineNum;
	/**
	 * 逻辑号
	 */
	private int LineLogicNum;
	/**
	 * 类型
	 */
	private LineType lineType;
	/**
	 * 状态
	 */
	private LineState lineState;
	/**
	 * dtmf码缓冲区
	 */
	private StringBuilder dtmf=new StringBuilder();
	/**
	 * fsk缓冲区
	 */
	private String callId;
	/**
	 * 子状态
	 */
	
	private String subState="";
	public String getSubState() {
		return subState;
	}
	public void setSubState(String subState) {
		this.subState = subState;
	}
	/**
	 * @return the callId
	 */
	public String getCallId() {
		return callId;
	}
	/**
	 * @param callId the callId to set
	 */
	public void setCallId(String callId) {
		this.callId = callId;
	}
	/**
	 * @return the dtmf
	 */
	public char[] getDtmf() {
		return dtmf.toString().toCharArray();
	}
	/**
	 * @param dtmf the dtmf to set
	 */
	public void setDtmf(char[] dtmf) {
		this.dtmf.append(dtmf);
	}
	/**
	 * @return the lineLogicNum
	 */
	public int getLineLogicNum() {
		return LineLogicNum;
	}
	/**
	 * @param lineLogicNum the lineLogicNum to set
	 */
	public void setLineLogicNum(int lineLogicNum) {
		LineLogicNum = lineLogicNum;
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
	/**
	 * @return the lineState
	 */
	public LineState getLineState() {
		return lineState;
	}
	/**
	 * @param lineState the lineState to set
	 */
	public void setLineState(LineState lineState) {
		this.lineState = lineState;
	}
	/**
	 * @return the lineType
	 */
	public LineType getLineType() {
		return lineType;
	}
	/**
	 * @param lineType the lineType to set
	 */
	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}
	public void addDtmf(char d)
	{
		dtmf.append(d);
	}
	public void clearDtmf()
	{
		dtmf=new StringBuilder();
	}
	public static void main(String[] arg0)
	{
		LineInfo li=new LineInfo();
		li.addDtmf('a');
	}
	/**
	 * @return the mb
	 */
	public AbsMissionBean getMb() {
		return mb;
	}
	/**
	 * @param mb the mb to set
	 */
	public void setMb(AbsMissionBean mb) {
		this.mb = mb;
	}
}
