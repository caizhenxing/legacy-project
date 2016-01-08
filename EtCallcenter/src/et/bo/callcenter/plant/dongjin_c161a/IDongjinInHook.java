/**
 * 	@(#)IDongjinInHook.java   2006-12-22 ����10:38:30
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinInHook {

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
	public abstract void startPlaySignal(int wChnlNo,int sigType); 
	
	/**
	 * ĳһͨ����ʼ�һ���⣻��ĳͨ��ժ���󣬿��Ե��ñ��������ú���ֻ������ͨ����Ч��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void startHangUpDetect(int wChnlNo);
	
	/**
	 * ���ĳһͨ���Ĺһ�״̬���ú�����Ҫ�ڵ���StartHangUpDetect֮��ʹ�á��������Ҫ����Ĳ��,��ʹ�ñ�������
	 * ���⣬�еĵ绰����ժ��ʱ�����ж��������ʹ�ú���RingDetect�������ժ���͹һ������ܻ���ָ�ժ���Ͷ��ߵ��������ˣ����ñ��������������������
	 * �ú���ֻ������ͨ����Ч
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int hangUpDetect(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����������������ñ������󣬱�ͨ���������ӵĵ绰�����᲻ͣ���壬ֱ�����ú���FeedPower�Ż�ֹͣ
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedRing(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ������������������0.75�룬ͣ3�롣��Ҫֹͣ���ú���FeedPower
	 * �ڱ�ͨ��������������£����ժ������ʹ��OffHookDetect�����ܵ���RingDetect��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedRealRing(int wChnlNo);
	
	/**
	 * ��ĳһ·����ͨ�����磬ͬʱֹͣ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedPower(int wChnlNo);
	
	/**
	 * ���ĳһ·����ͨ����ժ��״̬��������FeedRealRing������ʼһ����������������ñ����������ժ��״̬��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return true ��ժ��
	 * @return false δժ��
	 */
	public abstract boolean offHookDetect(int wChnlNo);
	
	/**
	 * ��lpFileName��ȡ���ڲ�����������lpFileName��Ӧ����һ���ź�����ϵͳ����ʹ�øö�����������������æ�������������ź�������ϵͳ�ڲ���һ��ȱʡ���ź������û�Ҳ����¼��ϲ�����ź�����Ȼ���ñ��������滻ȱʡ�ź�����
	 * @param lpFileName �ź����ļ���
	 * @version 2006-11-22
	 * @return true �ɹ�
	 * @return false ��ȡʧ��
	 */
	public abstract boolean readGenerateSigBuf(String lpFileName);
	
	/**
	 * ά�ֶ������弰�ź����ĺ��������ڳ����ѭ���е��á�
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedSigFunc();
	
	/**
	 * ĳͨ������һ����ʱ��
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void startTimer(int wChnlNo,int clockType);
	
	/**
	 * ���ؼ�ʱ�����������ڵ�ʱ�䣬��λ0.01�롣
	 * @param wChnlNo ͨ����
	 * @param ClockType ��ʱ����(3~9)
	 * @version 2006-11-22
	 * @return ��ʱ�����������ڵ�ʱ�䣬��λ0.01��
	 */
	public abstract int elapseTime(int wChnlNo,int clockType);
	
}
