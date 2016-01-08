/**
 * 	@(#)TcIniTypeMore.java   2006-11-27 上午09:38:00
 *	 。 
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
	 * 卡占用的共享内存地址
	 */
	public int wMemAddr;
	/**
	 * 卡的总数
	 */
	public int wCardNum;
	/**
	 * 卡的类型，也表示该卡的通道总数。目前可能的卡的类型有：16、8
	 */
	public byte[] cbCardType;
	/**
	 * 该卡是中继卡（外线）还是用户卡（内线） 
	 * user 0
	 * trunk 1
	 */
	public byte[] cbCardNeiWai;
	/**
	 * 卡上的通道总数
	 */
	public int wChnlNum;
	/**
	 * 该通道的类型
	 */
	public byte[] cbChType;
	/**
	 * 该通道所在的卡号
	 */
	public byte[] cbChnlCardNo;
	/**
	 * 该通道在卡内的通道号
	 */
	public byte[] cbChnlInternal;
	/**
	 * 内部用
	 */
	public byte[] cbConnectChnl;
	/**
	 * 内部用
	 */
	public byte[] cbConnectStream;
	/**
	 * 内部用
	 */
	public byte[] cbDtmfModeVal;
	/**
	 * 该通道是否支持Call-ID，
	 * 1表示支持
	 * D161A卡上的通道支持
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
