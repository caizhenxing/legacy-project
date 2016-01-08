/**
 * 	@(#)LineService.java   2007-1-15 ����04:03:10
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.List;
import java.util.Queue;

 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface LineService {
	
	/**
	 * �ļ�����
	 * @param file �ļ���
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startPlayFile(String file);
	
	/**
	 * ֹͣ����
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopPlayFile();
	
	/**
	 * ��ʼ��������
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startIndexPlayFile(List<String> files);
	
	/**
	 * ֹͣ��������
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopIndexPlayFile();
	
	/**
	 * ��ʼ�ļ�¼��
	 * @param 
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startRecordFile(String file);
	
	
	/**
	 * ����¼��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopRecordFile();
	
	/**
	 * ��dtmf
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void receiveDtmf();
	
	/**
	 * ���dtmf
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void clearDtmf();
	
	/**
	 * ��fsk
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void receiveFsk();
	
	/**
	 * ����dtmf
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void sendDtmf(String dtmf);
	
	
	
	/**
	 * �����˿���Ϣ
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public Queue listener();
	
	
	public int getLineNum();
	
	/**
	 * ���ź���
	 * @param sigType �ź�������
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void startPlaySignal(int sigType);
	/**
	 * ���� ��׼����
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedRealRing();
	/**
	 * ���� �Զ�������
	 * @param ring ���� interval ��� ���뵥λ
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedRealRing(int ring,int interval);
	/**
	 * ͣ��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedPower();
	
	/**
	 * ժ��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void offHook();
	/**
	 * �Ҷ�
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void hangUp();
	
	/**
	 * ���߲���
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public abstract void dial(String dail,String pre);
	
	/**
	 * @return the li
	 */
	public LineInfo getLi();
	/**
	 * @param li the li to set
	 */
	public void setLi(LineInfo li);
	
	/**
	 * ֹͣ����
	 * @param
	 * @version 2007-1-30
	 * @return
	 */
	public void stopPaly();
	
	/**
	 * ��ʼ��ʱ���������ʱ�ޣ�������ʱ�¼�
	 * @param hms ��λ100ms
	 */
	public void startTime(int hms);
	
	/**
	 * ֹͣ��ʱ
	 *
	 */
	public void endTime();
}
