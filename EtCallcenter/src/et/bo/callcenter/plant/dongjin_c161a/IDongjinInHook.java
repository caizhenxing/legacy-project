/**
 * 	@(#)IDongjinInHook.java   2006-12-22 上午10:38:30
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinInHook {

	/**
	 * 
	 * 内线振铃控制及摘挂机检测
	 * 
	 * 
	 */
	
	/**
	 * 控制信号音的播放。本函数实质内存循环放音来实现。其中：
	 * 拨号音的时间长度为响0.75秒，停3秒
	 * 忙音一，响0.35秒，停0.35秒
	 * 忙音二，响0.7秒，停0.7秒
	 * @param wChnlNo 通道号
	 * @param SigType 信号音类型 SIG_STOP 停止拨信号音，SIG_DIALTONE 拨号音，SIG_BUSY1 忙音1，SIG_BUSY2 忙音2，SIG_RINGBACK 回铃音。
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void startPlaySignal(int wChnlNo,int sigType); 
	
	/**
	 * 某一通道开始挂机检测；当某通道摘机后，可以调用本函数。该函数只对内线通道有效。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void startHangUpDetect(int wChnlNo);
	
	/**
	 * 检测某一通道的挂机状态；该函数需要在调用StartHangUpDetect之后使用。如果你需要检测拍叉簧,请使用本函数。
	 * 另外，有的电话机在摘机时，会有抖动。如果使用函数RingDetect来检测其摘机和挂机，可能会出现刚摘机就断线的情况，因此，调用本函数来避免这种情况。
	 * 该函数只对内线通道有效
	 * @param  wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int hangUpDetect(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。调用本函数后，本通道所在连接的电话机将会不停振铃，直到调用函数FeedPower才会停止
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedRing(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。响铃0.75秒，停3秒。若要停止调用函数FeedPower
	 * 在本通道正在振铃情况下，检测摘机必须使用OffHookDetect，不能调用RingDetect。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedRealRing(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈电，同时停止馈铃流
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedPower(int wChnlNo);
	
	/**
	 * 检测某一路内线通道的摘机状态，当调用FeedRealRing函数开始一个断续铃流后，请调用本函数来检测摘机状态。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return true 已摘机
	 * @return false 未摘机
	 */
	public abstract boolean offHookDetect(int wChnlNo);
	
	/**
	 * 将lpFileName读取到内部缓冲区，在lpFileName中应含有一段信号音，系统将会使用该段声音产生拨号音、忙音、回铃音等信号音。在系统内部有一个缺省的信号音，用户也可以录制喜欢的信号音，然后用本函数来替换缺省信号音。
	 * @param lpFileName 信号音文件名
	 * @version 2006-11-22
	 * @return true 成功
	 * @return false 读取失败
	 */
	public abstract boolean readGenerateSigBuf(String lpFileName);
	
	/**
	 * 维持断续振铃及信号音的函数；请在程序大循环中调用。
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void feedSigFunc();
	
	/**
	 * 某通道启动一个计时器
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void startTimer(int wChnlNo,int clockType);
	
	/**
	 * 返回计时器启动到现在的时间，单位0.01秒。
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return 计时器启动到现在的时间，单位0.01秒
	 */
	public abstract int elapseTime(int wChnlNo,int clockType);
	
}
