/**
 * 	@(#)IDongjinInit.java   2006-12-21 下午04:45:32
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniType;
import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniTypeMore;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinInit {

	/**
	 * 初始化函数
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * 初始化驱动程序
	 * @param
	 * @version 2006-11-20
	 * @return 0 成功
	 * @return -1 打开设备驱动程序错误
	 * @return -2在读取TC08-v.ini文件时，发生错误
	 * @return -3 ini文件设置与实际的硬件不一致时，发生错误
	 */
	public int loadDRV();
	
	/**
	 * 关闭驱动程序
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public void freeDRV();
	
	/**
	 * 初始化电话卡的硬件并为每一个通道分配语音缓冲区。
	 * @param wUsedCh 工作的通道总数
	 * @param wFileBufLen 驱动中为每个通道分配的语音内存大小，1024的整数倍
	 * @version 2006-11-20
	 * @return 0 成功
	 * @return -1 LoadDRV没有成功，导致本函数调用失败
	 * @return -2 分配缓冲区失败
	 */
	public int enableCard(int wUsedCh,int wFileBufLen);
	
	/**
	 * 关闭电话卡硬件，释放缓冲区。程序结束（正常非正常退出）调用
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public void disableCard();
	
	/**
	 * 获得系统配置的有关信息
	 * @param 
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return TcIniType
	 */
	public TcIniType getSysInfo();
	
	/**
	 * 获得系统更多配置
	 * @param 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return TmpMore 结构TC_INI_TYPE_MORE String[12] TcIniTypeMore
	 */
	public TcIniTypeMore getSysInfoMore();
	
	/**
	 * 总的可用的通道数
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	public int checkValidCh();
	
	/**
	 * 检测通道类型
	 * @deprecated
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空 
	 */
	public int checkChType(int wChnlNo);
	
	/**
	 * 检测通道类型
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空
	 * @return 3 录音模块
	 */
	public int checkChTypeNew(int wChnlNo);
	
	/**
	 * 判断该卡是否支持Caller-ID功能 D161A PCI卡将返回1
	 * @param
	 * @version 2006-11-21
	 * @return 1支持
	 * @return 0不支持
	 */
	public boolean isSupportCallerID();
	
	/**
	 * 设置压缩比率。调用本函数后，录放音均以该压缩比率进行。
	 * @param wPackRate压缩比率 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	public void setPackRate(int wPackRate);
	
	/**
	 * 维持文件录放音的持续进行，需要在处理函数的大循环中调用
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void pushPlay();
	
	/**
	 * 设定要检测的挂机忙音的参数。国标中的0.7秒写为 SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void setBusyPara(int BusyLen);
	
	/**
	 * 设定拨号以后要检测的信号音参数。国标规定回铃音为响1秒，停4秒，忙音0.35秒
	 * 写为 SetDialPara(1000,4000,350,7);
	 * @param RingBackl 回铃音中响声的时间长度，单位毫秒。
	 * @param RingBack0 回铃音中两声之间间隔时间长度，单位毫秒。
	 * @param BusyLen 对方占线时返回的忙音信号的时间长度
	 * @param RingTimes 一共响铃多少次认为是无人接听
	 * @version 2006-11-21
	 * @return
	 */
	public void setDialPara(int RingBackl,int RingBack0, int BusyLen,int RingTimes);
	
	/**
	 * 读取D161A PCI卡的序列号。惟一编号。
	 * 惟一编号。在函数LoadDRV之后，EnableCard之前读取。
	 * @param wCardNo 卡号
	 * @version 2006-11-21
	 * @return 编号
	 */
	public int newReadPass(int wCardNo);
	
	/**
	 * 设定某通道工作参数
	 * @param wChnlNo 通道号
	 * @param cbWorkMode 选择要设定的工作模式
	 * @param CbModeVal 参数值
	 * @version 2006-11-21
	 * @return
	 */
	public void dSetWorkMode(int wChnlNo,char cbWorkMode,char CbModeVal);
	
}
