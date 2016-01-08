/**
 * 	@(#)TcIniTypeMore.java   2006-11-27 上午09:38:00
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.cc.hardware.dongjin;

 /**
 * @author ddddd
 * @version 2006-11-27
 * @see
 */
public class TcIniTypeMore {

	/**
	 * 卡占用的共享内存地址
	 */
	public String wMemAddr;
	/**
	 * 卡的总数
	 */
	public String wCardNum;
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
	public String wChnlNum;
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
}
