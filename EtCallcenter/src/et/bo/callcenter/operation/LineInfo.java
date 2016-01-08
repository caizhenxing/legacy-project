/**
 * 	@(#)LineInfo.java   2007-1-15 ����03:46:09
 *	 �� 
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
		IN_LINE,//����
		OUT_LINE,//����
		RECORD_LINE,//¼��
		EMPTY_LINE//����
	};
	
	public enum LineState{
		L_FREE,//����
		
		//L_CONNECT,//��ͨ ���������ժ�������߲��ź��ͨ
		L_CONFERENCE,//����
		L_LINK,//ͨ��
		
		L_SENDDTMF,//����
		L_PLAY,//����״̬
		L_PLAY_SIG,//����������ź���
		L_PLAYINDEX,//��������״̬
		L_RECORD,//¼��
		
		U_RING,//��������
		U_OFFHOOK,//����ժ��
		
		T_CONNECT,//���������ժ�������߲��ź��ͨ
		T_OFFHOOK,//����ժ��
		T_RING,//��������
		
		};
	/**
	 * ����bean
	 */
	private AbsMissionBean mb=null;	
	/**
	 * line���룬�����
	 */
	private int lineNum;
	/**
	 * �߼���
	 */
	private int LineLogicNum;
	/**
	 * ����
	 */
	private LineType lineType;
	/**
	 * ״̬
	 */
	private LineState lineState;
	/**
	 * dtmf�뻺����
	 */
	private StringBuilder dtmf=new StringBuilder();
	/**
	 * fsk������
	 */
	private String callId;
	/**
	 * ��״̬
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
