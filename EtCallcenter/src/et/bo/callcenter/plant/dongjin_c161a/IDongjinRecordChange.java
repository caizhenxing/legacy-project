/**
 * 	@(#)IDongjinRecordChangeImpl.java   2006-12-22 ����03:54:46
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinRecordChange {
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
	public abstract int pcmtoWave(String pcmFileName,String waveFileName);
	
	/**
	 * ��wave�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param WaveFileName ת�����ļ�
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	public abstract int wavetoPcm(String waveFileName,String pcmFileName);
	
	/**
	 * ��4λ8K������ADPCM�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ8K������ADPCM�ļ� 
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	public abstract int adtoPcm(String adpcmFileName,String pcmFileName);
	
	/**
	 * ��A-law PCm�ļ� ת��Ϊ4λ8K������ADPCM�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ8K������ADPCM�ļ� 
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	public abstract int pcmtoAd(String pcmFileName,String adpcmFileName);
	
	/**
	 * ��4λ6K������Dialogic�ļ� ת��ΪA-law PCm�ļ�
	 * @param PcmFileName A-law PCm�ļ�
	 * @param AdpcmFileName 4λ6K������Dialogic�ļ�
	 * @version 2006-11-22
	 * @return -1 ��Դ�ļ�����
	 * @return -2 ��Ŀ���ļ�����
	 * @return 1 �ɹ�
	 */
	public abstract int ad6ktoPcm(String adpcmFileName,String pcmFileName);
	
}
