/**
 * 	@(#)IDongjinVoice.java   2007-1-4 ����04:35:17
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2007-1-4
 * @see
 */
public interface IDongjinVoice {

	/**
	 * 
	 * 
	 * �µ��ź�����⺯��
	 * 
	 * 
	 * 
	 */
	/**
	 * ��ʼ��
	 * @param wPara ȱʡ0
	 * @version 2006-12-22
	 * @return 1 �ɹ�
	 * @return 0 ʧ��
	 */
	public abstract int sigInit(int wPara);
	/**
	 * ��ĳͨ�����йһ�æ�����
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return 1 ��⵽æ��
	 * @return 0 û��æ��
	 */
	public abstract int sigCheckBusy(int wChNo);
	/**
	 * ���æ����⻺�������ڲ�����
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return
	 */
	public abstract void sigResetCheck(int wChNo);
	/**
	 * ��ʼĳͨ����������
	 * @param wChNo ͨ����
	 * @param DialNum ��������
	 * @param PreDialNum ǰ������
	 * @param wMode �������ģʽ
	 * @version 2007-1-4
	 * @return 1 �ɹ�
	 * @return 0 ʧ��
	 */
	public abstract int sigStartDial(int wChNo,String dialNum,String preDialNum,int wMode);
	/**
	 * ����ź�������
	 * @param wChNo ͨ����
	 * @param nCadenceType �ź������� 1 æ����2 ������
	 * @version 2007-1-4
	 * @return �ź�������
	 */
	public abstract int sigGetCadenceCount(int wChNo,int nCadenceType);
	/**
	 * ��sigStartDial��������֮��ѭ������ά�ֲ��ż����������
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return
	 */
	public abstract int sigCheckDial(int wChNo);
	
	
}
