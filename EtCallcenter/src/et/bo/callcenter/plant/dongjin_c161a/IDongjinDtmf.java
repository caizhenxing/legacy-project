/**
 * 	@(#)IDongjinDtmfImpl.java   2006-12-22 ����10:12:49
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinDtmf {

	/**
	 * 
	 * 
	 * ���롢���š��ź�����⺯��
	 * 
	 */
	
	/**
	 * ���ϵͳ��DTMF������������ڻ���������DTMF������ֵ�����ᶪʧ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void initDtmfBuf(int wChnlNo);
	
	/**
	 * ȡ��ͨ���յ�DTMF���룬����ڻ���������DTMF���������ñ����������������һ��DTMF������
	 * ͬʱ���ð����ӻ�������ȥ�������������û���յ��κ�DTMF����������-1��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return -1 û��DTMF
	 * @return 1~9 1~9��
	 * @return 10 0��
	 * @return 11 *��
	 * @return 12 #��
	 * @return 13 A��
	 * @return 14 B��
	 * @return 15 C��
	 * @return 0 D��
	 */
	public abstract short getDtmfCode(int wChnlNo);
	
	/**
	 * �鿴ָ��ͨ���Ƿ���DTMF���������յ�һ����Ч��DTMF�����󣬱���������true��
	 * �����������Ὣ�������ڲ�����������ȥ��������ȥ�ð���������GetDtmfCode��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ��DTMF����
	 * @return false û��DTMF����
	 */
	public abstract boolean dtmfHit(int wChnlNo);
	
	/**
	 * ����DTMF�����ţ�����,����ʾ�ڲ���ʱ����ʱ0.5�롣����ÿ��DTMFΪ125���룬���ҲΪ125���롣
	 * ��Ҫ��;ֹͣ���ţ�����StopPlay����Ⲧ���Ƿ���ɣ�����CheckSendEnd����������DTMF���ʣ�ʹ��NewSendDtmfBuf��
	 * һ�ο��Է���DTMF���32��������DTMF�����Ƿ��������Բ��ϵ���PUSH_PLAY
	 * @param wChnlNo ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void sendDtmfBuf(int wChnlNo,String dialNum);
	
	/**
	 * ���ĳ·����DTMF�Ƿ����
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ������ϣ����Լ���ź���
	 * @return false δ�������
	 */
	public abstract boolean checkSendEnd(int wChnlNo);
	
	/**
	 * ���÷���DTMF�����ʡ����Ҫ��NewSendDTMFBuf������DTMF�룬�ڳ�ʼ��ʱ������ñ�������һ����ñ�������EnableCard֮��
	 * @param ToneLen DTMF��ʱ�䳤�ȣ����룩������ܳ���125
	 * @param SilenceLen ������ȣ����룩������ܳ���125
	 * @version 2006-11-21
	 * @return 0 �ɹ�
	 * @return 1 ʧ��
	 */
	public abstract int setSendPara(int toneLen,int silenceLen);
	
	/**
	 * SendDtmfBuf���ƣ�����������SetSendPara�йء���󳤶���EnableCard�йء�
	 * @param wChnlNo ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void newSendDtmfBuf(int wChnlNo,String dialNum);
	
	/**
	 * ������NewSendDtmfBuf������ʼ����DTMF����������鷢���Ƿ���ϡ�
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return 1 ���
	 * @return 0 δ��
	 */
	public abstract boolean newCheckSendEnd(int wChnlNo);
	
	/**
	 * ĳ·��ʼ�µ��ź�����⡣һ����ժ����ҶϺ󣬵��ñ�������ʼ���ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void startSigCheck(int wChnlNo);
	
	/**
	 * ֹͣĳ·�ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void stopSigCheck(int wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo ͨ����
	 * @param wMode �ź�������
	 * @version 2006-11-21
	 * @return
	 */
	public abstract int readCheckResult(int wChnlNo,int wMode);
	
	/**
	 * ͨ��ΪReadCheckResult������ͨ��
	 * @param
	 * @version 2006-11-21
	 * @return ��ǰ�������æ������
	 */
	public abstract int readBusyCount();
	
	/**
	 * ���ͨ�����ԣ�ͨ�����Ⲧ�ţ�ժ������ʱһ����ú�����¼���ԣ����ź󣬼�⼫�Ըı�Ϊ�Է�ժ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public abstract boolean checkPolarity(int wChnlNo);
	
	/**
	 * �����·�ľ������
	 * @param wChnlNo ͨ����
	 * @param wCheckNum �����ź�����������ЧֵΪ1~511
	 * @version 2006-11-21
	 * @return -1 �ź����������еĸ���������wCheckNum��
	 * @return 0~wCheckNum wCheckNum���ź��������У�1�ĸ��� 
	 */
	public abstract int checkSilence(int wChnlNo,int wCheckNum);
	
}
