/**
 * 	@(#)Dongjin.java   2006-11-20 ����05:24:46
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;


 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public class Dongjin {
	static String dllname="etdongjin";
	static {
		
	}
	private static Dongjin dongjin=null;
	static Dongjin DongjinFactory(String library)
	{
		if(dongjin==null)
			dongjin=new Dongjin(library);
		return dongjin;
	}
	private Dongjin(String library)
	{
		System.loadLibrary(library);
	}
	private Dongjin()
	{
		
	}

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
	native int LoadDRV();
	
	/**
	 * �ر���������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native void FreeDRV();
	
	/**
	 * ��ʼ���绰����Ӳ����Ϊÿһ��ͨ������������������
	 * @param wUsedCh ������ͨ������
	 * @param wFileBufLen ������Ϊÿ��ͨ������������ڴ��С��1024��������
	 * @version 2006-11-20
	 * @return 0 �ɹ�
	 * @return -1 LoadDRVû�гɹ������±���������ʧ��
	 * @return -2 ���仺����ʧ��
	 */
	native int EnableCard(int wUsedCh,int wFileBufLen);
	
	/**
	 * �رյ绰��Ӳ�����ͷŻ���������������������������˳�������
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native void DisableCard();
	
	/**
	 * ���ϵͳ���õ��й���Ϣ
	 * @param 
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return TmpIni �ṹTC_INI_TYPE String[9]��TcIniType
	 */
	native String[] GetSysInfo();
	
	/**
	 * ���ϵͳ��������
	 * @param 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return TmpMore �ṹTC_INI_TYPE_MORE String[12] TcIniTypeMore
	 */
	native String[] GetSysInfoMore();
	
	/**
	 * �ܵĿ��õ�ͨ����
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native int CheckValidCh();
	
	/**
	 * ���ͨ������
	 * @deprecated
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ���� 
	 */
	native int CheckChType(int wChnlNo);
	
	/**
	 * ���ͨ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-20
	 * @return 0 ����
	 * @return 1 ����
	 * @return 2 ����
	 * @return 3 ¼��ģ��
	 */
	native int CheckChTypeNew(int wChnlNo);
	
	/**
	 * �жϸÿ��Ƿ�֧��Caller-ID���� D161A PCI��������1
	 * @param
	 * @version 2006-11-21
	 * @return 1֧��
	 * @return 0��֧��
	 */
	native boolean IsSupportCallerID();
	
	/**
	 * ����ѹ�����ʡ����ñ�������¼�������Ը�ѹ�����ʽ��С�
	 * @param wPackRateѹ������ 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	native void SetPackRate(int wPackRate);
	
	/**
	 * ά���ļ�¼�����ĳ������У���Ҫ�ڴ������Ĵ�ѭ���е���
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void PUSH_PLAY();
	
	/**
	 * �趨Ҫ���Ĺһ�æ���Ĳ����������е�0.7��дΪ SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void SetBusyPara(int BusyLen);
	
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
	native void SetDialPara(int RingBack1,int RingBack0, int BusyLen,int RingTimes);
	
	/**
	 * ��ȡD161A PCI�������кš�Ωһ��š�
	 * Ωһ��š��ں���LoadDRV֮��EnableCard֮ǰ��ȡ��
	 * @param wCardNo ����
	 * @version 2006-11-21
	 * @return ���
	 */
	native int NewReadPass(int wCardNo);
	
	/**
	 * �趨ĳͨ����������
	 * @param wChnlNo ͨ����
	 * @param cbWorkMode ѡ��Ҫ�趨�Ĺ���ģʽ
	 * @param CbModeVal ����ֵ
	 * @version 2006-11-21
	 * @return
	 */
	native void D_SetWorkMode(int wChnlNo,char cbWorkMode,char cbModeVal);
	
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
	native boolean RingDetect(int wChnlNo);
	
	/**
	 * ����������������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void OffHook(int wChnlNo);
	
	/**
	 * ���߹һ����������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void HangUp(int wChnlNo);
	
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
	native void StartPlay(int wChnlNo,String PlayBuf,int dwStartPos,int dwPlayLen);
	
	/**
	 * ָ��ͨ��ְͣ�ڴ����������������ֹͣ�ڴ���ͨ�������ڴ������������ڴ�ѭ��������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StopPlay(int wChnlNo);
	
	/**
	 * ���ָ��ͨ�������Ƿ���������������������ڴ���ͨ�������ڴ������������ڴ�ѭ���������ļ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return false δ����
	 * @return true ����
	 */
	native boolean CheckPlayEnd(int wChnlNo);
	
	/**
	 * ��ʼ�ļ�������ֹͣ�÷�ʽ����һ��Ҫ��StopPlayFile���������Ƿ��������CheckPlayEnd������
	 * �ļ������ڱ������������ڴ�ѭ��������Ȼ�󣬲��ϵĸ��»�������PUSH_PLAY�����ĵ��ã��ܹ���֤�Է����������ĸ��£��Ӷ��ﵽ�����������ϡ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @param StartPos ��������ʼλ��
	 * @version 2006-11-21
	 * @return
	 */
	native boolean StartPlayFile(int wChnlNo,String FileName,int StartPos);
	
	/**
	 * ��������ָ��ͨ��ֹͣ�ļ������������ú���StartPlayFile��ʼ�ķ����������ñ�������ֹͣ���������ܹر������ļ���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StopPlayFile(int wChnlNo);
	
	/**
	 * ��ʼ�����ļ�������ÿ��ʼһ���µĶ��ļ�����ǰ���ô˺���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void RsetIndexPlayFile(int wChnlNo);
	
	/**
	 * ���Ӷ��ļ������ķ����ļ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @version 2006-11-21
	 * @return true �ɹ�
	 * @return false ʧ��
	 */
	native boolean AddIndexPlayFile(int wChnlNo,String FileName);
	
	/**
	 * ��ʼһ�����ļ������������øú����ɹ��󣬱���ѭ������CheckIndexPlayFile�������������Ƿ������
	 * ��ά�����ļ���������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native boolean StartIndexPlayFile(int wChnlNo);
	
	/**
	 * �����ļ������Ƿ��������ά�����ļ������������ԡ������ж��ļ�����ʱ��������ñ��������Ա�֤���ļ������������ԡ�
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native boolean CheckIndexPlayFile(int wChnlNo);
	
	/**
	 * ֹͣ���ļ��������ú���ָֹͣ��ͨ�����ļ�����������ʹ��StartIndexPlayFile������ʼ�Ķ��ļ�����������ʱһ�����ñ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StopIndexPlayFile(int wChnlNo);
	
	/**
	 * ��ʼ�������ڴ����
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void ResetIndex();
	
	/**
	 * ���������ڴ�����Ǽ���
	 * @param VocBuf ָ��Ҫ�Ǽǵ�����������ָ�롣
	 * @param dwVocLen ��������
	 * @version 2006-11-21
	 * @return true �Ǽǳɹ�
	 * @return false �Ǽ�ʧ��
	 */
	native boolean SetIndex(String VocBuf,int dwVocLen);
	
	/**
	 * ��ʼһ���ڴ�����������
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void StartPlayIndex(int wChnlNo,int[] pIndexTable,int wIndexLen);
	
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
	native boolean StartRecordFile(int wChnlNo,String FileName,int dwRecordLen);
	
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
	native boolean StartRecordFileNew(int wChnlNo,String FileName,int dwRecordLen,int dwRecordStartPos);
	
	/**
	 * ���ָ��ͨ��¼���Ƿ������������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true �ɹ�����
	 * @return false δ����
	 */
	native boolean CheckRecordEnd(int wChnlNo);
	
	/**
	 * �ú���ָֹͣ��ͨ�����ļ�¼��������StartRecordFile����������¼����һ��Ҫ�б�����ֹͣ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StopRecordFile(int wChnlNo);
	
	
	
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
	native void InitDtmfBuf(int wChnlNo);
	
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
	native short GetDtmfCode(int wChnlNo);
	
	/**
	 * �鿴ָ��ͨ���Ƿ���DTMF���������յ�һ����Ч��DTMF�����󣬱���������true��
	 * �����������Ὣ�������ڲ�����������ȥ��������ȥ�ð���������GetDtmfCode��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ��DTMF����
	 * @return false û��DTMF����
	 */
	native boolean DtmfHit(int wChnlNo);
	
	/**
	 * ����DTMF�����ţ�����,����ʾ�ڲ���ʱ����ʱ0.5�롣����ÿ��DTMFΪ125���룬���ҲΪ125���롣
	 * ��Ҫ��;ֹͣ���ţ�����StopPlay����Ⲧ���Ƿ���ɣ�����CheckSendEnd����������DTMF���ʣ�ʹ��NewSendDtmfBuf��
	 * һ�ο��Է���DTMF���32��������DTMF�����Ƿ��������Բ��ϵ���PUSH_PLAY
	 * @param wChnlNo ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	native void SendDtmfBuf(int wChnlNo,String DialNum);
	
	/**
	 * ���ĳ·����DTMF�Ƿ����
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ������ϣ����Լ���ź���
	 * @return false δ�������
	 */
	native boolean CheckSendEnd(int wChnlNo);
	
	/**
	 * ���÷���DTMF�����ʡ����Ҫ��NewSendDTMFBuf������DTMF�룬�ڳ�ʼ��ʱ������ñ�������һ����ñ�������EnableCard֮��
	 * @param ToneLen DTMF��ʱ�䳤�ȣ����룩������ܳ���125
	 * @param SilenceLen ������ȣ����룩������ܳ���125
	 * @version 2006-11-21
	 * @return 0 �ɹ�
	 * @return 1 ʧ��
	 */
	native int SetSendPara(int ToneLen,int SilenceLen);
	
	/**
	 * SendDtmfBuf���ƣ�����������SetSendPara�йء���󳤶���EnableCard�йء�
	 * @param wChnlNo ͨ����
	 * @param DialNum ���ŵ��ַ�������Чֵ��0������9������*������#������,������ABCD��
	 * @version 2006-11-21
	 * @return
	 */
	native void NewSendDtmfBuf(int wChnlNo,String DialNum);
	
	/**
	 * ������NewSendDtmfBuf������ʼ����DTMF����������鷢���Ƿ���ϡ�
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return 1 ���
	 * @return 0 δ��
	 */
	native int NewCheckSendEnd(int wChnlNo);
	
	/**
	 * ĳ·��ʼ�µ��ź�����⡣һ����ժ����ҶϺ󣬵��ñ�������ʼ���ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StartSigCheck(int wChnlNo);
	
	/**
	 * ֹͣĳ·�ź������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native void StopSigCheck(int wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo ͨ����
	 * @param wMode �ź�������
	 * @version 2006-11-21
	 * @return
	 */
	native int ReadCheckResult(int wChnlNo,int wMode);
	
	/**
	 * ͨ��ΪReadCheckResult������ͨ��
	 * @param
	 * @version 2006-11-21
	 * @return ��ǰ�������æ������
	 */
	native int ReadBusyCount();
	
	/**
	 * ���ͨ�����ԣ�ͨ�����Ⲧ�ţ�ժ������ʱһ����ú�����¼���ԣ����ź󣬼�⼫�Ըı�Ϊ�Է�ժ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	native boolean CheckPolarity(int wChnlNo);
	
	/**
	 * �����·�ľ������
	 * @param wChnlNo ͨ����
	 * @param wCheckNum �����ź�����������ЧֵΪ1~511
	 * @version 2006-11-21
	 * @return -1 �ź����������еĸ���������wCheckNum��
	 * @return 0~wCheckNum wCheckNum���ź��������У�1�ĸ��� 
	 */
	native int CheckSilence(int wChnlNo,int wCheckNum);
	
	
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
	native void StartPlaySignal(int wChnlNo,int SigType); 
	
	/**
	 * ĳһͨ����ʼ�һ���⣻��ĳͨ��ժ���󣬿��Ե��ñ��������ú���ֻ������ͨ����Ч��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void StartHangUpDetect(int wChnlNo);
	
	/**
	 * ���ĳһͨ���Ĺһ�״̬���ú�����Ҫ�ڵ���StartHangUpDetect֮��ʹ�á��������Ҫ����Ĳ��,��ʹ�ñ�������
	 * ���⣬�еĵ绰����ժ��ʱ�����ж��������ʹ�ú���RingDetect�������ժ���͹һ������ܻ���ָ�ժ���Ͷ��ߵ��������ˣ����ñ��������������������
	 * �ú���ֻ������ͨ����Ч
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native int HangUpDetect(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����������������ñ������󣬱�ͨ���������ӵĵ绰�����᲻ͣ���壬ֱ�����ú���FeedPower�Ż�ֹͣ
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedRing(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ������������������0.75�룬ͣ3�롣��Ҫֹͣ���ú���FeedPower
	 * �ڱ�ͨ��������������£����ժ������ʹ��OffHookDetect�����ܵ���RingDetect��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedRealRing(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����磬ͬʱֹͣ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedPower(int wChnlNo);
	
	/**
	 * ���ĳһ·����ͨ����ժ��״̬��������FeedRealRing������ʼһ����������������ñ����������ժ��״̬��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return true ��ժ��
	 * @return false δժ��
	 */
	native boolean OffHookDetect(int wChnlNo);
	
	/**
	 * ��lpFileName��ȡ���ڲ�����������lpFileName��Ӧ����һ���ź�����ϵͳ����ʹ�øö�����������������æ�������������ź�������ϵͳ�ڲ���һ��ȱʡ���ź������û�Ҳ����¼��ϲ�����ź�����Ȼ���ñ��������滻ȱʡ�ź�����
	 * @param lpFileName �ź����ļ���
	 * @version 2006-11-22
	 * @return true �ɹ�
	 * @return false ��ȡʧ��
	 */
	native boolean ReadGenerateSigBuf(String lpFileName);
	
	/**
	 * ά�ֶ������弰�ź����ĺ��������ڳ����ѭ���е��á�
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedSigFunc();
	
	/**
	 * ĳͨ������һ����ʱ��
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	native void StartTimer(int wChnlNo,int ClockType);
	
	/**
	 * ���ؼ�ʱ�����������ڵ�ʱ�䣬��λ0.01�롣
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return ��ʱ�����������ڵ�ʱ�䣬��λ0.01��
	 */
	native int ElapseTime(int wChnlNo,int ClockType);
	
	
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
	native int SetLink(int wOne,int wAnother);
	
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
	native int ClearLink(int wOne,int wAnother);
	
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
	native int LinkOneToAnother(int wOne,int wAnother);
	
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
	native int ClearOneFromAnother(int wOne,int wAnother);
	
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
	native int LinkThree(int wOne,int wTwo,int wThree);
	
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
	native int ClearThree(int wOne,int wTwo,int wThree);
	
	
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
	native void ResetCallerIDBuffer(int wChnlNo);
	
	/**
	 * ���Call-ID�����ݣ�IDStr���û����䣬128�ֽھ��԰�ȫ��������3��4ʱ���յ�����һ��ʽ����ϸ�ʽ�����ܽ��ա�
	 * ���������󣬵���ResetCallerIDBuffer��������������3��4ʱ��ժ������Ҫ��ʱ������ʱ�ղ���FSKժ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return IDRawStr ���յ����к�����Ϣ
	 * @return 0 δ�յ���Ϣ
	 * @return 1 ���ڽ���ͷ��Ϣ
	 * @return 2 ���ڽ���id����
	 * @return 3 ������ϣ�У����ȷ
	 * @return 4 ������ϣ�У�����
	 */
	native String GetCallerIDRawStr(int wChnlNo);
	
	/**
	 * �������к�����Ϣ
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native String GetCallerIDStr(int wChnlNo);
	
	
	/**
	 * 
	 * ������ʽת������
	 * DJCVT.dll
	 * 
	 */
	
	/**
	 * ��A-law PCm�ļ�ת��Ϊwave�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param WaveFileName ת�����ļ�
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	native int PcmtoWave(String PcmFileName,String WaveFileName);
	
	/**
	 * ��wave�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param WaveFileName ת�����ļ�
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	native int WavetoPcm(String WaveFileName,String PcmFileName);
	
	/**
	 * ��4λ8K������ADPCM�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ8K������ADPCM�ļ� 
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	native int AdtoPcm(String AdpcmFileName,String PcmFileName);
	
	/**
	 * ��A-law PCm�ļ� ת��Ϊ4λ8K������ADPCM�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ8K������ADPCM�ļ� 
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	native int PcmtoAd(String PcmFileName,String AdpcmFileName);
	
	/**
	 * ��4λ6K������Dialogic�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ6K������Dialogic�ļ�
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	native int Ad6ktoPcm(String AdpcmFileName,String PcmFileName);
	
	
	/**
	 * 
	 * ��������
	 * 
	 */
	
	/**
	 * �������� ��ʼ��0db
	 * @param wChnl ͨ����
	 * @param cMode 0��¼��������1:��������
	 * @param cVolAdjust ��������
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 ������Χ -20db��20db
	 */
	native int D_AdjustVocVol(int wChnl,char cMode,char cVolAdjust);
	
	/**
	 * ����ͬ�ϣ�vc���⿪��������
	 * @deprecated
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D_AdjustVocVol_ForVB(int wChnl,int cMode,int cVolAdjust);
	
	/**
	 * ���¼��ģ��ժ�һ���ֻ��¼��ģ����Ч
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return 0 �һ�
	 * @return 1 ժ��
	 */
	native boolean DRec_OffHookDetect(int wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * �ڴ�¼���ͻ�������
	 * 
	 */
	
	/**
	 * ����¼������ˢ�µĴ�С��ȱʡ1024��
	 * @param wSize ˢ�����ݴ�С��512�������������ܴ���ϵͳ������������һ�롣
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1��EnableCard֮ǰ�����˱�����
	 * @return -2 ����512��������
	 * @return -3 ̫��
	 * @return -4 ϵͳ�������Ĵ�С�޷�����wSize
	 * @return -5 ����D161A
	 */
	native int VR_SetRefreshSize(int wSize);
	
	/**
	 * ��ʼ�ڴ�¼��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_StartRecord(int wChnlNo);
	
	/**
	 * ֹͣ�ڴ�¼��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_StopRecord(int wChnlNo);
	
	/**
	 * �õ�¼������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return ����������
	 */
	native String VR_GetRecordData(int wChnlNo);
	
	/**
	 * �Ƿ��ͨ�������������ƹ���
	 * @param wChnlNo ͨ����
	 * @param cbEnableFlag 0:�رգ�1:����
	 * @param wParam1 0
	 * @param wParam2 0
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_SetEcrMode(int wChnl,byte cbEnableFlag,int wParam1,int wParam2);
	
	
	
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
	native int DJFsk_InitForFsk(int Mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_ResetFskBuffer(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_CheckSendFSKEnd(int vocID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSK(int trunkID,byte[] pInfo,int wSize,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_GetFSK(int trunkID,byte[] pInfo,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_StopSend(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_Release();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSKA(int trunkID,byte[] pInfo,int wSize,int Mode,int MarkNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSKBit(int trunkID,byte[] pInfo,int wSize,int Mode);
	
	
	
	
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
	native int D160PCI_GetTimeSlot(int wD160AChnl);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D160PCI_ConnectFromTS(int wD160Achnl,int wChnlTS);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D160PCI_DisconnectTS(int wD160Achnl);
	
	
	
	
	/**
	 * 
	 * 
	 * ����ʵ��
	 * 
	 * 
	 * 
	 */
	/**
	 * ��һ��ͨ���������
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @param ChnlAtte ���������-20db~20db
	 * @param NoiseSupp ����
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 û�п�����Դ
	 */
	native int AddChnl(int ConfNo,int ChannelNo,int ChnlAtte,int NoiseSupp);
	
	/**
	 * ��һ��ͨ���ӻ�����ȥ��
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @version 2006-12-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 ����ChannelNo�ҵ���Դ�Ƿ�
	 */
	native int SubChnl(int ConfNo,int ChannekNo);
	
	/**
	 * ����һ��ͨ�������飬ֻ����������˵
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @param ChnlAtte ���������-20db~20db
	 * @param NoiseSupp ����
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 û�п�����Դ
	 */
	native int AddListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * ȥ��һ���������ͨ��
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @version 2006-12-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 ����ChannelNo�ҵ���Դ�Ƿ�
	 */
	native int SubListenChnk(int ConfNo,int ChannelNo);
	
	/**
	 * ��ʼ��
	 * @param
	 * @version 2006-12-22
	 * @return 0 �ɹ�
	 * @return 1 ����D161A
	 * @return 2 ��INI�У�Connect������1
	 * @return 3 �Ѿ�ʹ��ģ����鿨����ʼ���ɹ�
	 */
	native int DConf_EnableConfCard();
	
	/**
	 * ��ֹ���鹦��
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	native void DConf_DisableConfCard();
	
	/**
	 * ��һ��ͨ����¼��ת��Ϊ�Ի���¼��
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @param ChnlAtte ���������-20db~20db
	 * @param NoiseSupp ����
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 û�п�����Դ
	 * @return 4 ����ʹ��D161A���û��鹦��
	 */
	native int DConf_AddRecListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * ȥ��һ���Ի����¼�����ظ���ͨ��¼��
	 * @param ConfNo ������ţ�1~10
	 * @param ChannelNo ͨ����
	 * @param ChnlAtte ���������-20db~20db
	 * @param NoiseSupp ����
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return 1 ConfNoԽ��
	 * @return 2 ChannelNoԽ��
	 * @return 3 û�п�����Դ
	 * @return 4 ����ʹ��D161A���û��鹦��
	 */
	native int DConf_SubRecListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * �õ���ǰ�����û�����Դ����
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	native int DConf_GetResNumber();
	
	
	
	
	
	
	
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
	native int sigInit(int wPara);
	/**
	 * ��ĳͨ�����йһ�æ�����
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return 1 ��⵽æ��
	 * @return 0 û��æ��
	 */
	native int sigCheckBusy(int wChNo);
	/**
	 * ���æ����⻺�������ڲ�����
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return
	 */
	native void sigResetCheck(int wChNo);
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
	native int sigStartDial(int wChNo,String DialNum,String PreDialNum,int wMode);
	/**
	 * ����ź�������
	 * @param wChNo ͨ����
	 * @param nCadenceType �ź������� 1 æ����2 ������
	 * @version 2007-1-4
	 * @return �ź�������
	 */
	native int sigGetCadenceCount(int wChNo,int nCadenceType);
	/**
	 * ��sigStartDial��������֮��ѭ������ά�ֲ��ż����������
	 * @param wChNo ͨ����
	 * @version 2007-1-4
	 * @return
	 */
	native int sigCheckDial(int wChNo);
	
	
	
	public static void main(String[] arg0)
	{
		Dongjin dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}
}
