/**
 * 	@(#)EventBean.java   2007-1-17 ����04:06:24
 *	 �� 
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
		T_RING,//��������
		T_SIG,//�ź������������ͣ����忴����
		T_POLARITY,//���Է�ת
		T_DIAL,//���߲���
		T_CONNECT,//���߲�ͨ
		T_HANGUP,//���߲����ĵ绰�һ�
		
		U_OFFHOOK,//�������
		U_HANGUP,//���߹һ�
		
		L_RECEDTMF,//��dtmf
		L_SENDDTMFEND,//�������
		L_RECEFSK,//��fsk
		L_PLAYEND,//��������
		
		U_HOOKFLASH,//�Ĳ�
		U_OFFHOOK_BYRING,//Ӧ��
		
		L_TIMEOUT,//��ʱ
		
		L_MISSION//����
		
	};
	public enum SigType
	{
		S_BUSY,//æ��
		
		S_NOBODY,//�������ɴκ�û�˽���
		S_NOSIGNAL,//û���ź���
		S_NODIALTONE//û�в�����
	};
	/**
	 * �˿ں�
	 */
	private int lineNum;
	/**
	 * �¼�����
	 */
	private EventType et;
	/**
	 * �ź�������
	 */
	private SigType st;
	/**
	 * ��������
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
