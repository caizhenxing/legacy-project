/**
 * 	@(#)TelephoneLine.java   2007-1-4 ����02:09:22
 *	 �� 
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
		CH_FREE,//����
		CH_RECEIVEID,//��������
		CH_WAITSECONDRING,//�ȴ��ڶ�������
		CH_WELCOME,//�����Ƕ����绰������ʾϵͳ
		CH_ACCOUNT,//������8λ�ʺ�
		CH_ACCOUNT1,//������8λ�ʺ�
		CH_PASSWORD,//������6λ����
		CH_PASSWORD1,//������6λ����
		CH_SELECT,//ѡ����
		CH_SELECT1,//ѡ����
		CH_PLAYRESULT,//���Ų�ѯ���
		CH_RECORDFILE,//¼������
		CH_PLAYRECORD,//��������
		CH_OFFHOOK,//����ժ��
		CH_WAITUSERONHOOK//�ȴ����߹һ�
		};
		public enum TeleLineType{
			IN_LINE,//����
			OUT_LINE,//����
			RECORD_LINE,//¼��
			EMPTY_LINE//����
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
