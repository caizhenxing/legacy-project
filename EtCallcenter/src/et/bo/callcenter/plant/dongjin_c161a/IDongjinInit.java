/**
 * 	@(#)IDongjinInit.java   2006-12-21 ����04:45:32
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniType;
import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniTypeMore;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinInit {

	/**
	 * ��ʼ������
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * ��ʼ����������
	 * @param
	 * @version 2006-11-20
	 * @return 0 �ɹ�
	 * @return -1 ���豸�����������
	 * @return -2�ڶ�ȡTC08-v.ini�ļ�ʱ����������
	 * @return -3 ini�ļ�������ʵ�ʵ�Ӳ����һ��ʱ����������
	 */
	public int loadDRV();
	
	/**
	 * �ر���������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public void freeDRV();
	
	/**
	 * ��ʼ���绰����Ӳ����Ϊÿһ��ͨ������������������
	 * @param wUsedCh ������ͨ������
	 * @param wFileBufLen ������Ϊÿ��ͨ������������ڴ��С��1024��������
	 * @version 2006-11-20
	 * @return 0 �ɹ�
	 * @return -1 LoadDRVû�гɹ������±���������ʧ��
	 * @return -2 ���仺����ʧ��
	 */
	public int enableCard(int wUsedCh,int wFileBufLen);
	
	/**
	 * �رյ绰��Ӳ�����ͷŻ���������������������������˳�������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public void disableCard();
	
	/**
	 * ���ϵͳ���õ��й���Ϣ
	 * @param 
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return TcIniType
	 */
	public TcIniType getSysInfo();
	
	/**
	 * ���ϵͳ��������
	 * @param 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return TmpMore �ṹTC_INI_TYPE_MORE String[12] TcIniTypeMore
	 */
	public TcIniTypeMore getSysInfoMore();
	
	/**
	 * �ܵĿ��õ�ͨ����
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public int checkValidCh();
	
	/**
	 * ���ͨ������
	 * @deprecated
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ���� 
	 */
	public int checkChType(int wChnlNo);
	
	/**
	 * ���ͨ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ����
	 * @return 3 ¼��ģ��
	 */
	public int checkChTypeNew(int wChnlNo);
	
	/**
	 * �жϸÿ��Ƿ�֧��Caller-ID���� D161A PCI��������1
	 * @param
	 * @version 2006-11-21
	 * @return 1֧��
	 * @return 0��֧��
	 */
	public boolean isSupportCallerID();
	
	/**
	 * ����ѹ�����ʡ����ñ�������¼�������Ը�ѹ�����ʽ��С�
	 * @param wPackRateѹ������ 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	public void setPackRate(int wPackRate);
	
	/**
	 * ά���ļ�¼�����ĳ������У���Ҫ�ڴ������Ĵ�ѭ���е���
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void pushPlay();
	
	/**
	 * �趨Ҫ���Ĺһ�æ���Ĳ����������е�0.7��дΪ SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void setBusyPara(int BusyLen);
	
	/**
	 * �趨�����Ժ�Ҫ�����ź�������������涨������Ϊ��1�룬ͣ4�룬æ��0.35��
	 * дΪ SetDialPara(1000,4000,350,7);
	 * @param RingBackl ��������������ʱ�䳤�ȣ���λ���롣
	 * @param RingBack0 ������������֮����ʱ�䳤�ȣ���λ���롣
	 * @param BusyLen �Է�ռ��ʱ���ص�æ���źŵ�ʱ�䳤��
	 * @param RingTimes һ��������ٴ���Ϊ�����˽���
	 * @version 2006-11-21
	 * @return
	 */
	public void setDialPara(int RingBackl,int RingBack0, int BusyLen,int RingTimes);
	
	/**
	 * ��ȡD161A PCI�������кš�Ωһ��š�
	 * Ωһ��š��ں���LoadDRV֮��EnableCard֮ǰ��ȡ��
	 * @param wCardNo ����
	 * @version 2006-11-21
	 * @return ���
	 */
	public int newReadPass(int wCardNo);
	
	/**
	 * �趨ĳͨ����������
	 * @param wChnlNo ͨ����
	 * @param cbWorkMode ѡ��Ҫ�趨�Ĺ���ģʽ
	 * @param CbModeVal ����ֵ
	 * @version 2006-11-21
	 * @return
	 */
	public void dSetWorkMode(int wChnlNo,char cbWorkMode,char CbModeVal);
	
}
