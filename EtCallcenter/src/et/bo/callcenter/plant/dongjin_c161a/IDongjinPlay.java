/**
 * 	@(#)IDongjinPlayImpl.java   2006-12-21 ����04:56:45
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinPlay {

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
	public void startPlay(int wChnlNo,String playBuf,int dwStartPos,int dwPlayLen);
	
	/**
	 * ָ��ͨ��ְͣ�ڴ����������������ֹͣ�ڴ���ͨ�������ڴ������������ڴ�ѭ��������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void stopPlay(int wChnlNo);
	
	/**
	 * ���ָ��ͨ�������Ƿ���������������������ڴ���ͨ�������ڴ������������ڴ�ѭ���������ļ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return false δ����
	 * @return true ����
	 */
	public boolean checkPlayEnd(int wChnlNo);
	
	/**
	 * ��ʼ�ļ�������ֹͣ�÷�ʽ����һ��Ҫ��StopPlayFile���������Ƿ��������CheckPlayEnd������
	 * �ļ������ڱ������������ڴ�ѭ��������Ȼ�󣬲��ϵĸ��»�������PUSH_PLAY�����ĵ��ã��ܹ���֤�Է����������ĸ��£��Ӷ��ﵽ�����������ϡ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @param StartPos ��������ʼλ��
	 * @version 2006-11-21
	 * @return
	 */
	public boolean startPlayFile(int wChnlNo,String fileName,int startPos);
	
	/**
	 * ��������ָ��ͨ��ֹͣ�ļ������������ú���StartPlayFile��ʼ�ķ����������ñ�������ֹͣ���������ܹر������ļ���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void stopPlayFile(int wChnlNo);
	
	/**
	 * ��ʼ�����ļ�������ÿ��ʼһ���µĶ��ļ�����ǰ���ô˺���
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void rsetIndexPlayFile(int wChnlNo);
	
	/**
	 * ���Ӷ��ļ������ķ����ļ�
	 * @param wChnlNo ͨ����
	 * @param FileName �ļ���
	 * @version 2006-11-21
	 * @return true �ɹ�
	 * @return false ʧ��
	 */
	public boolean addIndexPlayFile(int wChnlNo,String fileName);
	
	/**
	 * ��ʼһ�����ļ������������øú����ɹ��󣬱���ѭ������CheckIndexPlayFile�������������Ƿ������
	 * ��ά�����ļ���������������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public boolean startIndexPlayFile(int wChnlNo);
	
	/**
	 * �����ļ������Ƿ��������ά�����ļ������������ԡ������ж��ļ�����ʱ��������ñ��������Ա�֤���ļ������������ԡ�
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public boolean checkIndexPlayFile(int wChnlNo);
	
	/**
	 * ֹͣ���ļ��������ú���ָֹͣ��ͨ�����ļ�����������ʹ��StartIndexPlayFile������ʼ�Ķ��ļ�����������ʱһ�����ñ�������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void stopIndexPlayFile(int wChnlNo);
	
	/**
	 * ��ʼ�������ڴ����
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void resetIndex();
	
	/**
	 * ���������ڴ�����Ǽ���
	 * @param VocBuf ָ��Ҫ�Ǽǵ�����������ָ�롣
	 * @param dwVocLen ��������
	 * @version 2006-11-21
	 * @return true �Ǽǳɹ�
	 * @return false �Ǽ�ʧ��
	 */
	public boolean setIndex(String vocBuf,int dwVocLen);
	
	/**
	 * ��ʼһ���ڴ�����������
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void startPlayIndex(int wChnlNo,int[] pIndexTable,int wIndexLen);
	
}
