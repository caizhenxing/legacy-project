/**
 * 	@(#)TcIniTypeMore.java   2006-11-27 ����09:38:00
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.cc.hardware.dongjin;

 /**
 * @author ddddd
 * @version 2006-11-27
 * @see
 */
public class TcIniTypeMore {

	/**
	 * ��ռ�õĹ����ڴ��ַ
	 */
	public String wMemAddr;
	/**
	 * ��������
	 */
	public String wCardNum;
	/**
	 * �������ͣ�Ҳ��ʾ�ÿ���ͨ��������Ŀǰ���ܵĿ��������У�16��8
	 */
	public byte[] cbCardType;
	/**
	 * �ÿ����м̿������ߣ������û��������ߣ� 
	 * user 0
	 * trunk 1
	 */
	public byte[] cbCardNeiWai;
	/**
	 * ���ϵ�ͨ������
	 */
	public String wChnlNum;
	/**
	 * ��ͨ��������
	 */
	public byte[] cbChType;
	/**
	 * ��ͨ�����ڵĿ���
	 */
	public byte[] cbChnlCardNo;
	/**
	 * ��ͨ���ڿ��ڵ�ͨ����
	 */
	public byte[] cbChnlInternal;
	/**
	 * �ڲ���
	 */
	public byte[] cbConnectChnl;
	/**
	 * �ڲ���
	 */
	public byte[] cbConnectStream;
	/**
	 * �ڲ���
	 */
	public byte[] cbDtmfModeVal;
	/**
	 * ��ͨ���Ƿ�֧��Call-ID��
	 * 1��ʾ֧��
	 * D161A���ϵ�ͨ��֧��
	 */
	public byte[] cbIsSupportCallerID;
}
