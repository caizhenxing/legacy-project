/**
 * 	@(#)HardWareDongjin.java   2006-11-20 ����05:24:46
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.cc.hardware.dongjin;

 /**
 * @author ddddd
 * @version 2006-11-20
 * @see
 */
public class HardWareDongjin {

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
	private native long LoadDRV();
	
	/**
	 * �ر���������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native void FreeDRV();
	
	/**
	 * ��ʼ���绰����Ӳ����Ϊÿһ��ͨ������������������
	 * @param wUsedCh ������ͨ������
	 * @param wFileBufLen ������Ϊÿ��ͨ������������ڴ��С��1024��������
	 * @version 2006-11-20
	 * @return 0 �ɹ�
	 * @return -1 LoadDRVû�гɹ������±���������ʧ��
	 * @return -2 ���仺����ʧ��
	 */
	private native long EnableCard(String wUsedCh,String wFileBufLen);
	
	/**
	 * �رյ绰��Ӳ�����ͷŻ���������������������������˳�������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native void DisableCard();
	
	/**
	 * ���ϵͳ���õ��й���Ϣ
	 * @param TmpIni �ṹTC_INI_TYPE String[9]
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return
	 */
	private native void GetSysInfo(String[] TmpIni);
	
	/**
	 * ���ϵͳ��������
	 * @param TmpMore �ṹTC_INI_TYPE_MORE String[12] 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return
	 */
	private native void GetSysInfoMore(String[] TmpMore);
	
	/**
	 * �ܵĿ��õ�ͨ����
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native String CheckValidCh();
	
	/**
	 * ���ͨ������
	 * @deprecated
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ���� 
	 */
	private native String CheckChType(String wChnlNo);
	
	/**
	 * ���ͨ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ����
	 * @return 3 ¼��ģ��
	 */
	private native String CheckChTypeNew(String wChnlNo);
	
	/**
	 * �жϸÿ��Ƿ�֧��Caller-ID���� D161A PCI��������1
	 * @param
	 * @version 2006-11-21
	 * @return 1֧��
	 * @return 0��֧��
	 */
	private native boolean IsSupportCallerID();
	
	/**
	 * ����ѹ�����ʡ����ñ�������¼�������Ը�ѹ�����ʽ��С�
	 * @param wPackRateѹ������ 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	private native void SetPackRate(String wPackRate);
	
	/**
	 * ά���ļ�¼�����ĳ������У���Ҫ�ڴ������Ĵ�ѭ���е���
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void PUSH_PLAY();
	
	/**
	 * �趨Ҫ���Ĺһ�æ���Ĳ����������е�0.7��дΪ SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void SetBusyPara(String BusyLen);
	
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
	private native void SetDialPara(String RingBackl,String RingBack0, String BusyLen,String RingTimes);
	
	/**
	 * ��ȡD161A PCI�������кš�Ωһ��š�
	 * Ωһ��š��ں���LoadDRV֮��EnableCard֮ǰ��ȡ��
	 * @param wCardNo ����
	 * @version 2006-11-21
	 * @return ���
	 */
	private native long NewReadPass(String wCardNo);
	
	/**
	 * �趨ĳͨ����������
	 * @param wChnlNo ͨ����
	 * @param cbWorkMode ѡ��Ҫ�趨�Ĺ���ģʽ
	 * @param CbModeVal ����ֵ
	 * @version 2006-11-21
	 * @return
	 */
	private native void D_SetWorkMode(String wChnlNo,char cbWorkMode,char CbModeVal);
	
	/**
	 * 
	 * ���弰ժ�һ�����
	 * 
	 * 
	 * 
	 */
	
	/**
	 * ��������Ƿ��������źŻ������Ƿ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ����������
	 * @return false �������һ�
	 * 
	 */
	private native boolean RingDetect(String wChnlNo);
	
	/**
	 * ����������������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void OffHook(String wChnlNo);
	
	/**
	 * ���߹һ����������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void HangUp(String wChnlNo);
	
	/**
	 * 
	 * 
	 * ��������
	 * 
	 */
	
	
	
	/**
	 * ָ��ͨ����ʼ��ͨ�ڴ�������������ĳ��ȴ���ϵͳ�������ĳ���ʱ����Ҫ����PUSH_PLAY��ά��¼���ĳ���
	 * @param wChnlNo ͨ����
	 * @param PlayBuf ������������ַ
	 * @param dwStartPos �ڻ������е�ƫ��
	 * @param dwPlayLen �����ĳ���
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartPlay(String wChnlNo,String PlayBuf,String dwStartPos,String dwPlayLen);
	
	/**
	 * ָ��ͨ��ְͣ�ڴ����������������ֹͣ�ڴ���ͨ�������ڴ������������ڴ�ѭ��������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopPlay(String wChnlNo);
	
	/**
	 * ���ָ��ͨ�������Ƿ���������������������ڴ���ͨ�������ڴ������������ڴ�ѭ���������ļ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return false δ����
	 * @return true ����
	 */
	private native boolean CheckPlayEnd(String wChnlNo);
	
	/**
	 * ��ʼ�ļ�������ֹͣ�÷�ʽ����һ��Ҫ��StopPlayFile���������Ƿ��������CheckPlayEnd������
	 * �ļ������ڱ������������ڴ�ѭ��������Ȼ�󣬲��ϵĸ��»�������PUSH_PLAY�����ĵ��ã��ܹ���֤�Է����������ĸ��£��Ӷ��ﵽ�����������ϡ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @param StartPos ��������ʼλ��
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean StartPlayFile(String wChnlNo,String FileName,String StartPos);
	
	/**
	 * ��������ָ��ͨ��ֹͣ�ļ������������ú���StartPlayFile��ʼ�ķ����������ñ�������ֹͣ���������ܹر������ļ���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopPlayFile(String wChnlNo);
	
	/**
	 * ��ʼ�����ļ�������ÿ��ʼһ���µĶ��ļ�����ǰ���ô˺���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void RsetIndexPlayFile(String wChnlNo);
	
	/**
	 * ���Ӷ��ļ������ķ����ļ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @version 2006-11-21
	 * @return true �ɹ�
	 * @return false ʧ��
	 */
	private native boolean AddIndexPlayFile(String wChnlNo,String FileName);
	
	/**
	 * ��ʼһ�����ļ������������øú����ɹ��󣬱���ѭ������CheckIndexPlayFile�������������Ƿ������
	 * ��ά�����ļ���������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean StartIndexPlayFile(String wChnlNo);
	
	/**
	 * �����ļ������Ƿ��������ά�����ļ������������ԡ������ж��ļ�����ʱ��������ñ��������Ա�֤���ļ������������ԡ�
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean CheckIndexPlayFile(String ChnlNo);
	
	/**
	 * ֹͣ���ļ��������ú���ָֹͣ��ͨ�����ļ�����������ʹ��StartIndexPlayFile������ʼ�Ķ��ļ�����������ʱһ�����ñ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopIndexPlayFile(String wChnlNo);
	
	/**
	 * ��ʼ�������ڴ����
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void ResetIndex();
	
	/**
	 * ���������ڴ�����Ǽ���
	 * @param VocBuf ָ��Ҫ�Ǽǵ�����������ָ�롣
	 * @param dwVocLen ��������
	 * @version 2006-11-21
	 * @return true �Ǽǳɹ�
	 * @return false �Ǽ�ʧ��
	 */
	private native boolean SetIndex(String VocBuf,String dwVocLen);
	
	/**
	 * ��ʼһ���ڴ�����������
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartPlayIndex(String wChnlNo,String[] pIndexTable,String wIndexLen);
	
	/**
	 * 
	 * ¼������
	 * 
	 * 
	 */
	
	
	/**
	 * ��ʼ�ļ�¼����ֹͣ�÷�ʽ¼����һ��Ҫ��StopRecordFile�����¼���Ƿ��������CheckRecordEnd������
	 * �ļ�¼���ڱ�����������ѭ���ڴ�¼����Ȼ�󣬲��ϵĸ��»�������PUSH_PLAY�����ĵ����ܹ���֤¼�������ߣ��Ӷ��ﵽ¼���Ľ��С�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @param dwRecordLen �¼������
	 * @version 2006-11-21
	 * @return true �ɹ�
	 * @return false ���ļ�ʧ��
	 */
	private native boolean StartRecordFile(String wChnlNo,String FileName,String dwRecordLen);
	
	/**
	 * �������Ƕ��ļ�¼������StartRecordFile�Ĳ��䣬��ʵ�ϣ�������������ȫ���溯��StartRecordFile���乤����ʽ
	 * ��dwRecordStartPos��0ʱ�����ú���StartRecordFile�������������ļ�¼����
	 * ��FileName������ʱ�����ú���StartRecordFile�������������ļ���¼����
	 * ��dwRecordStartPos���ļ����ȣ����ļ�β����ʼ¼������ˣ������Ҫ��һ���ļ���β������¼��������dwRecordStartPos��0xFFFFFFFFL��
	 * ��dwRecordStartPosС���ļ�����ʱ����dwRecordStartPosλ�ÿ�ʼ¼����
	 * ¼��������dwRecordLenȷ��
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @param dwRecordLen �¼������
	 * @param dwRecordStartPos ¼���Ŀ�ʼλ��
	 * @version 2006-11-21
	 * @return true �ɹ�
	 * @return false ���ļ�ʧ��
	 */
	private native boolean StartRecordFileNew(String wChnlNo,String FileName,String dwRecordLen,String dwRecordStartPos);
	
	/**
	 * ���ָ��ͨ��¼���Ƿ������������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true �ɹ�����
	 * @return false δ����
	 */
	private native boolean CheckRecordEnd(String wChnlNo);
	
	/**
	 * �ú���ָֹͣ��ͨ�����ļ�¼��������StartRecordFile����������¼����һ��Ҫ�б�����ֹͣ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopRecordFile(String wChnlNo);
	
	
	
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
	private native void InitDtmfBuf(String wChnlNo);
	
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
	private native short GetDtmfCode(String wChnlNo);
	
	/**
	 * �鿴ָ��ͨ���Ƿ���DTMF���������յ�һ����Ч��DTMF�����󣬱���������true��
	 * �����������Ὣ�������ڲ�����������ȥ��������ȥ�ð���������GetDtmfCode��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ��DTMF����
	 * @return false û��DTMF����
	 */
	private native boolean DtmfHit(String wChnlNo);
	
	/**
	 * ����DTMF�����ţ�����,����ʾ�ڲ���ʱ����ʱ0.5�롣����ÿ��DTMFΪ125���룬���ҲΪ125���롣
	 * ��Ҫ��;ֹͣ���ţ�����StopPlay����Ⲧ���Ƿ���ɣ�����CheckSendEnd����������DTMF���ʣ�ʹ��NewSendDtmfBuf��
	 * һ�ο��Է���DTMF���32��������DTMF�����Ƿ��������Բ��ϵ���PUSH_PLAY
	 * @param wChnlNo ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	private native void SendDtmfBuf(String wChnlNo,String DialNum);
	
	/**
	 * ���ĳ·����DTMF�Ƿ����
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ������ϣ����Լ���ź���
	 * @return false δ�������
	 */
	private native boolean CheckSendEnd(String wChnlNo);
	
	/**
	 * ���÷���DTMF�����ʡ����Ҫ��NewSendDTMFBuf������DTMF�룬�ڳ�ʼ��ʱ������ñ�������һ����ñ�������EnableCard֮��
	 * @param ToneLen DTMF��ʱ�䳤�ȣ����룩������ܳ���125
	 * @param SilenceLen ������ȣ����룩������ܳ���125
	 * @version 2006-11-21
	 * @return 0 �ɹ�
	 * @return 1 ʧ��
	 */
	private native int SetSendPara(int ToneLen,int SilenceLen);
	
	/**
	 * SendDtmfBuf���ƣ�����������SetSendPara�йء���󳤶���EnableCard�йء�
	 * @param ChannelNo  ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	private native void NewSendDtmfBuf(int ChannelNo,String DialNum);
	
	/**
	 * ������NewSendDtmfBuf������ʼ����DTMF����������鷢���Ƿ���ϡ�
	 * @param ChannelNo  ͨ����
	 * @version 2006-11-21
	 * @return 1 ���
	 * @return 0 δ��
	 */
	private native int NewCheckSendEnd(int ChannelNo);
	
	/**
	 * ĳ·��ʼ�µ��ź�����⡣һ����ժ����ҶϺ󣬵��ñ�������ʼ���ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartSigCheck(String wChnlNo);
	
	/**
	 * ֹͣĳ·�ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopSigCheck(String wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo ͨ����
	 * @param wMode �ź�������
	 * @version 2006-11-21
	 * @return
	 */
	private native String ReadCheckResult(String wChnlNo,String wMode);
	
	/**
	 * ͨ��ΪReadCheckResult������ͨ��
	 * @param
	 * @version 2006-11-21
	 * @return ��ǰ�������æ������
	 */
	private native String ReadBusyCount();
	
	/**
	 * ���ͨ�����ԣ�ͨ�����Ⲧ�ţ�ժ������ʱһ����ú�����¼���ԣ����ź󣬼�⼫�Ըı�Ϊ�Է�ժ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean CheckPolarity(String wChnlNo);
	
	/**
	 * �����·�ľ������
	 * @param wChnlNo ͨ����
	 * @param wCheckNum �����ź�����������ЧֵΪ1~511
	 * @version 2006-11-21
	 * @return -1 �ź����������еĸ���������wCheckNum��
	 * @return 0~wCheckNum wCheckNum���ź��������У�1�ĸ��� 
	 */
	private native long CheckSilence(String wChnlNo,String wCheckNum);
	
	
	/**
	 * 
	 * ����������Ƽ�ժ�һ����
	 * 
	 * 
	 */
	
	/**
	 * �����ź����Ĳ��š�������ʵ���ڴ�ѭ��������ʵ�֡����У�
	 * ��������ʱ�䳤��Ϊ��0.75�룬ͣ3��
	 * æ��һ����0.35�룬ͣ0.35��
	 * æ��������0.7�룬ͣ0.7��
	 * @param wChnlNo ͨ����
	 * @param SigType �ź������� SIG_STOP ֹͣ���ź�����SIG_DIALTONE ��������SIG_BUSY1 æ��1��SIG_BUSY2 æ��2��SIG_RINGBACK ��������
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartPlaySignal(String wChnlNo,String SigType); 
	
	/**
	 * ĳһͨ����ʼ�һ���⣻��ĳͨ��ժ���󣬿��Ե��ñ��������ú���ֻ������ͨ����Ч��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native void StartHangUpDetect(String wChnlNo);
	
	/**
	 * ���ĳһͨ���Ĺһ�״̬���ú�����Ҫ�ڵ���StartHangUpDetect֮��ʹ�á��������Ҫ����Ĳ��,��ʹ�ñ�������
	 * ���⣬�еĵ绰����ժ��ʱ�����ж��������ʹ�ú���RingDetect�������ժ���͹һ������ܻ���ָ�ժ���Ͷ��ߵ��������ˣ����ñ��������������������
	 * �ú���ֻ������ͨ����Ч
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native String HangUpDetect(String wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����������������ñ������󣬱�ͨ���������ӵĵ绰�����᲻ͣ���壬ֱ�����ú���FeedPower�Ż�ֹͣ
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedRing(String wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ������������������0.75�룬ͣ3�롣��Ҫֹͣ���ú���FeedPower
	 * �ڱ�ͨ��������������£����ժ������ʹ��OffHookDetect�����ܵ���RingDetect��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedRealRing(String wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����磬ͬʱֹͣ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedPower(String wChnlNo);
	
	/**
	 * ���ĳһ·����ͨ����ժ��״̬��������FeedRealRing������ʼһ����������������ñ����������ժ��״̬��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return true ��ժ��
	 * @return false δժ��
	 */
	private native boolean OffHookDetect(String wChnlNo);
	
	/**
	 * ��lpFileName��ȡ���ڲ�����������lpFileName��Ӧ����һ���ź�����ϵͳ����ʹ�øö�����������������æ�������������ź�������ϵͳ�ڲ���һ��ȱʡ���ź������û�Ҳ����¼��ϲ�����ź�����Ȼ���ñ��������滻ȱʡ�ź�����
	 * @param lpFileName �ź����ļ���
	 * @version 2006-11-22
	 * @return true �ɹ�
	 * @return false ��ȡʧ��
	 */
	private native boolean ReadGenerateSigBuf(String lpFileName);
	
	/**
	 * ά�ֶ������弰�ź����ĺ��������ڳ����ѭ���е��á�
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedSigFunc();
	
	/**
	 * ĳͨ������һ����ʱ��
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	private native void StartTimer(String wChnlNo,String ClockType);
	
	/**
	 * ���ؼ�ʱ�����������ڵ�ʱ�䣬��λ0.01�롣
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return ��ʱ�����������ڵ�ʱ�䣬��λ0.01��
	 */
	private native long ElapseTime(String wChnlNo,String ClockType);
	
	
	/**
	 * 
	 * ��ͨ����
	 * 
	 */
	
	/**
	 * ��·��ͨ
	 * ��·ͨ��������ͨʱ�������ĳһͨ��������ֹͣ�����������ɵ�����ͨ�����ñ�����֮ǰ����ֹͣ������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	private native long SetLink(String wOne,String wAnother);
	
	/**
	 * ��·�������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	private native long ClearLink(String wOne,String wAnother);
	
	/**
	 * ��·������ͨ�����øú�����ʵ�ֵ�����ͨ��ͨ��one��������another��������another������one��������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	private native long LinkOneToAnother(String wOne,String wAnother);
	
	/**
	 * ���������ͨ
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	private native long ClearOneFromAnother(String wOne,String wAnother);
	
	/**
	 * ������ͨ���ɵ�����ͨʵ�֣����������Ƚ�С��
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @param wThree ��·
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 * @return -6 ��·������Χ
	 * @return -7 ��·���ڷ���
	 */
	private native long LinkThree(String wOne,String wTwo,String wThree);
	
	/**
	 * ���������ͨ
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @param wThree ��·
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 * @return -6 ��·������Χ
	 * @return -7 ��·���ڷ���
	 */
	private native long ClearThree(String wOne,String wTwo,String wThree);
	
	
	/**
	 * 
	 * �����к�����йغ���
	 * 
	 * ��������FSK
	 * ��Ҫ�õ�
	 * ResetCallerIDBuffer
	 * GetCallerIDRawStr
	 * �����������ɣ�������������˵����
	 */
	
	/**
	 * ��ʼ��ĳ·Caller-ID������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	private native void ResetCallerIDBuffer(String wChnlNo);
	
	/**
	 * ���Call-ID�����ݣ�IDStr���û����䣬128�ֽھ��԰�ȫ��������3��4ʱ���յ�����һ��ʽ����ϸ�ʽ�����ܽ��ա�
	 * ���������󣬵���ResetCallerIDBuffer��������������3��4ʱ��ժ������Ҫ��ʱ������ʱ�ղ���FSKժ��
	 * @param wChnlNo ͨ����
	 * @param IDRawStr ���յ����к�����Ϣ
	 * @version 2006-11-22
	 * @return 0 δ�յ���Ϣ
	 * @return 1 ���ڽ���ͷ��Ϣ
	 * @return 2 ���ڽ���id����
	 * @return 3 ������ϣ�У����ȷ
	 * @return 4 ������ϣ�У�����
	 */
	private native String GetCallerIDRawStr(String wChnlNo,String IDRawStr);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String GetCallerIDStr(String wChnlNo,String[] IDStr);
	
	
	/**
	 * 
	 * ������ʽת������
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int PcmtoWave(String PcmFileName,String WaveFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int WavetoPcm(String WaveFileName,String PcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int AdtoPcm(String AdpcmFileName,String PcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int PcmtoAd(String PcmFileName,String AdpcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int Ad6ktoPcm(String AdpcmFileName,String PcmFileName);
	
	
	/**
	 * 
	 * ��������
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int D_AdjustVocVol(String wChnl,char cMode,char cVolAdjust);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int D_AdjustVocVol_ForVB(String wChnl,int cMode,int cVolAdjust);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native boolean DRec_OffHookDetect(String wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * �ڴ�¼���ͻ�������
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int VR_SetRefreshSize(String wSize);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_StartRecord(String wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_StopRecord(String wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int VR_GetRecordData(String wChnlNo,String pBuffer);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_SetEcrMode(String wChnl,byte cbEnableFlag,String wParam1,String wParam2);
	
	
	
	/**
	 * 
	 * 
	 * �շ�FSK
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_InitForFsk(int Mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_ResetFskBuffer(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_CheckSendFSKEnd(int vocID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSK(int trunkID,String pInfo,String wSize,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_GetFSK(int trunkID,String pInfo,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_StopSend(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_Release();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSKA(int trunkID,String pInfo,String wSize,int Mode,int MarkNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSKBit(int trunkID,String pInfo,String wSize,int Mode);
	
	
	
	
	/**
	 * 
	 * ����ϵͳ����
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String D160PCI_GetTimeSlot(String wD160AChnl);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String D160PCI_ConnectFromTS(String wD160Achnl,String wChnlTS);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String D160PCI_DisconnectTS(String wD160Achnl);
}
