/**
 * 	@(#)TcIniTypeMore.java   2006-11-27 ����09:38:00
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

 /**
 * @author zhaoyifei
 * @version 2006-11-27
 * @see
 */
public class TcIniTypeMore {

	/**
	 * ��ռ�õĹ����ڴ��ַ
	 */
	public int wMemAddr;
	/**
	 * ��������
	 */
	public int wCardNum;
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
	public int wChnlNum;
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
	public void setpara(String[] p)
	{
		this.wMemAddr=Integer.parseInt(p[0]);
		this.wCardNum=Integer.parseInt(p[1]);
		this.cbCardType=p[2].getBytes();
		this.cbCardNeiWai=p[3].getBytes();
		this.wChnlNum=Integer.parseInt(p[4]);
		this.cbChType=p[5].getBytes();
		this.cbChnlCardNo=p[6].getBytes();
		this.cbChnlInternal=p[7].getBytes();
		this.cbConnectChnl=p[8].getBytes();
		this.cbConnectStream=p[9].getBytes();
		this.cbDtmfModeVal=p[10].getBytes();
		this.cbIsSupportCallerID=p[11].getBytes();
	}
	public TcIniTypeMore(String[] p)
	{
		super();
		this.setpara(p);
	}
}
