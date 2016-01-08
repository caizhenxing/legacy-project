/**
 * 	@(#)IDongjinRecord.java   2006-12-21 ����05:00:20
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinRecord {

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
	public boolean startRecordFile(int wChnlNo,String fileName,int dwRecordLen);
	
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
	public boolean startRecordFileNew(int wChnlNo,String fileName,int dwRecordLen,int dwRecordStartPos);
	
	/**
	 * ���ָ��ͨ��¼���Ƿ������������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true �ɹ�����
	 * @return false δ����
	 */
	public boolean checkRecordEnd(int wChnlNo);
	
	/**
	 * �ú���ָֹͣ��ͨ�����ļ�¼��������StartRecordFile����������¼����һ��Ҫ�б�����ֹͣ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void stopRecordFile(int wChnlNo);
	
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
	public abstract int dadjustVocVol(int wChnl,int cMode,int cVolAdjust);
	
	/**
	 * ���¼��ģ��ժ�һ���ֻ��¼��ģ����Ч
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return 0 �һ�
	 * @return 1 ժ��
	 */
	public abstract boolean dRecOffHookDetect(int wChnlNo);
	
	
	
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
	public abstract int vRSetRefreshSize(int wSize);
	
	/**
	 * ��ʼ�ڴ�¼��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void vRStartRecord(int wChnlNo);
	
	/**
	 * ֹͣ�ڴ�¼��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void vRStopRecord(int wChnlNo);
	
	/**
	 * �õ�¼������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return ����������
	 */
	public abstract String vRGetRecordData(int wChnlNo);
	
	/**
	 * �Ƿ��ͨ�������������ƹ���
	 * @param wChnlNo ͨ����
	 * @param cbEnableFlag 0:�رգ�1:����
	 * @param wParam1 0
	 * @param wParam2 0
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void vRSetEcrMode(int wChnl,byte cbEnableFlag,int wParam1,int wParam2);
	
}
