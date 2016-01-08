/**
 * 	@(#)TcIniType.java   2006-11-27 上午09:24:00
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.cc.hardware.dongjin;



 /**
 * @author ddddd
 * @version 2006-11-27
 * @see
 */
public class TcIniType {

	/**
	 * 卡的总数
	 */
	public String wCardNo;
	
	/**
	 * 卡的类型
	 */
	public String wCardType;
	/**
	 * 卡之间是否连通
	 */
	public String wConnect;
	/**
	 * 卡断号
	 */
	public String wIRQ;
	/**
	 * 卡的安装目录
	 */
	public char[] cbDir;
	/**
	 * 每块卡的地址
	 */
	public String[] wAddress;
	/**
	 * 软件主版本号
	 */
	public String wMajorVer;
	/**
	 * 软件辅版本号
	 */
	public String wMinorVer;
	/**
	 * 通道类型
	 */
	public String[] wChType;
	
	public void setPara(String[] p)
	{
		
	}
}
