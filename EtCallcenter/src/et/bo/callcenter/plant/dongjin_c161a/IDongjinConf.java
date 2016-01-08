/**
 * 	@(#)IDongjinConf.java   2006-12-22 ����04:24:19
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinConf {

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
	public abstract int addChnl(int confNo,int channelNo,int chnlAtte,int noiseSupp);
	
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
	public abstract int subChnl(int confNo,int channekNo);
	
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
	public abstract int addListenChnl(int confNo,int channelNo);
	
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
	public abstract int subListenChnk(int confNo,int channelNo);
	
	/**
	 * ��ʼ��
	 * @param
	 * @version 2006-12-22
	 * @return 0 �ɹ�
	 * @return 1 ����D161A
	 * @return 2 ��INI�У�Connect������1
	 * @return 3 �Ѿ�ʹ��ģ����鿨����ʼ���ɹ�
	 */
	public abstract int dConfEnableConfCard();
	
	/**
	 * ��ֹ���鹦��
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	public abstract void dConfDisableConfCard();
	
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
	public abstract int dConfAddRecListenChnl(int confNo,int channelNo);
	
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
	public abstract int dConfSubRecListenChnl(int confNo,int channelNo);
	
	/**
	 * �õ���ǰ�����û�����Դ����
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	public abstract int dConfGetResNumber();
	
}
