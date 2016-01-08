/**
 * 	@(#)Dongjin.java   2006-11-20 下午05:24:46
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;


 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public class Dongjin {
	static String dllname="etdongjin";
	static {
		
	}
	private static Dongjin dongjin=null;
	static Dongjin DongjinFactory(String library)
	{
		if(dongjin==null)
			dongjin=new Dongjin(library);
		return dongjin;
	}
	private Dongjin(String library)
	{
		System.loadLibrary(library);
	}
	private Dongjin()
	{
		
	}

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
	native int LoadDRV();
	
	/**
	 * 关闭驱动程序
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native void FreeDRV();
	
	/**
	 * 初始化电话卡的硬件并为每一个通道分配语音缓冲区。
	 * @param wUsedCh 工作的通道总数
	 * @param wFileBufLen 驱动中为每个通道分配的语音内存大小，1024的整数倍
	 * @version 2006-11-20
	 * @return 0 成功
	 * @return -1 LoadDRV没有成功，导致本函数调用失败
	 * @return -2 分配缓冲区失败
	 */
	native int EnableCard(int wUsedCh,int wFileBufLen);
	
	/**
	 * 关闭电话卡硬件，释放缓冲区。程序结束（正常非正常退出）调用
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native void DisableCard();
	
	/**
	 * 获得系统配置的有关信息
	 * @param 
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return TmpIni 结构TC_INI_TYPE String[9]，TcIniType
	 */
	native String[] GetSysInfo();
	
	/**
	 * 获得系统更多配置
	 * @param 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return TmpMore 结构TC_INI_TYPE_MORE String[12] TcIniTypeMore
	 */
	native String[] GetSysInfoMore();
	
	/**
	 * 总的可用的通道数
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	native int CheckValidCh();
	
	/**
	 * 检测通道类型
	 * @deprecated
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空 
	 */
	native int CheckChType(int wChnlNo);
	
	/**
	 * 检测通道类型
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空
	 * @return 3 录音模块
	 */
	native int CheckChTypeNew(int wChnlNo);
	
	/**
	 * 判断该卡是否支持Caller-ID功能 D161A PCI卡将返回1
	 * @param
	 * @version 2006-11-21
	 * @return 1支持
	 * @return 0不支持
	 */
	native boolean IsSupportCallerID();
	
	/**
	 * 设置压缩比率。调用本函数后，录放音均以该压缩比率进行。
	 * @param wPackRate压缩比率 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	native void SetPackRate(int wPackRate);
	
	/**
	 * 维持文件录放音的持续进行，需要在处理函数的大循环中调用
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void PUSH_PLAY();
	
	/**
	 * 设定要检测的挂机忙音的参数。国标中的0.7秒写为 SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void SetBusyPara(int BusyLen);
	
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
	native void SetDialPara(int RingBack1,int RingBack0, int BusyLen,int RingTimes);
	
	/**
	 * 读取D161A PCI卡的序列号。惟一编号。
	 * 惟一编号。在函数LoadDRV之后，EnableCard之前读取。
	 * @param wCardNo 卡号
	 * @version 2006-11-21
	 * @return 编号
	 */
	native int NewReadPass(int wCardNo);
	
	/**
	 * 设定某通道工作参数
	 * @param wChnlNo 通道号
	 * @param cbWorkMode 选择要设定的工作模式
	 * @param CbModeVal 参数值
	 * @version 2006-11-21
	 * @return
	 */
	native void D_SetWorkMode(int wChnlNo,char cbWorkMode,char cbModeVal);
	
	/**
	 * 
	 * 振铃及摘挂机函数
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 检测外线是否有振铃信号或内线是否有提机
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 有振铃或提机
	 * @return false 无振铃或挂机
	 * 
	 */
	native boolean RingDetect(int wChnlNo);
	
	/**
	 * 外线提机，对于内线，不起作用
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void OffHook(int wChnlNo);
	
	/**
	 * 外线挂机，对于内线，不起作用
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void HangUp(int wChnlNo);
	
	/**
	 * 
	 * 
	 * 放音函数
	 * 
	 */
	
	
	
	/**
	 * 指定通道开始普通内存放音。当放音的长度大于系统缓冲区的长度时，需要函数PUSH_PLAY来维持录音的持续
	 * @param wChnlNo 通道号
	 * @param PlayBuf 语音缓冲区地址
	 * @param dwStartPos 在缓冲区中的偏移
	 * @param dwPlayLen 放音的长度
	 * @version 2006-11-21
	 * @return
	 */
	native void StartPlay(int wChnlNo,String PlayBuf,int dwStartPos,int dwPlayLen);
	
	/**
	 * 指定通道停职内存放音，本函数可以停止内存普通放音，内存索引放音，内存循环放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StopPlay(int wChnlNo);
	
	/**
	 * 检查指定通道放音是否结束。本函数可以用于内存普通放音，内存索引放音，内存循环放音和文件放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return false 未结束
	 * @return true 结束
	 */
	native boolean CheckPlayEnd(int wChnlNo);
	
	/**
	 * 开始文件放音。停止该方式放音一定要用StopPlayFile。检查放音是否结束，用CheckPlayEnd函数。
	 * 文件放音在本质上是利用内存循环放音，然后，不断的更新缓冲区。PUSH_PLAY函数的调用，能够保证对放音缓冲区的更新，从而达到放音连续不断。
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @param StartPos 放音的起始位置
	 * @version 2006-11-21
	 * @return
	 */
	native boolean StartPlayFile(int wChnlNo,String FileName,int StartPos);
	
	/**
	 * 本函数对指定通道停止文件放音。对于用函数StartPlayFile开始的放音，必须用本函数来停止，这样才能关闭语音文件。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StopPlayFile(int wChnlNo);
	
	/**
	 * 初始化多文件放音。每开始一个新的多文件放音前调用此函数
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void RsetIndexPlayFile(int wChnlNo);
	
	/**
	 * 增加多文件放音的放音文件
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @version 2006-11-21
	 * @return true 成功
	 * @return false 失败
	 */
	native boolean AddIndexPlayFile(int wChnlNo,String FileName);
	
	/**
	 * 开始一个多文件放音。当调用该函数成功后，必须循环调用CheckIndexPlayFile函数来检测放音是否结束，
	 * 并维护多文件放音的连续进行
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native boolean StartIndexPlayFile(int wChnlNo);
	
	/**
	 * 检查多文件放音是否结束，并维护多文件放音的连续性。当进行多文件放音时，必须调用本函数，以保证多文件放音的连续性。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native boolean CheckIndexPlayFile(int wChnlNo);
	
	/**
	 * 停止多文件放音。该函数停止指定通道多文件放音，对于使用StartIndexPlayFile函数开始的多文件放音，结束时一定调用本函数。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StopIndexPlayFile(int wChnlNo);
	
	/**
	 * 初始化索引内存放音
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void ResetIndex();
	
	/**
	 * 设置索引内存放音登记项
	 * @param VocBuf 指向要登记的语音缓冲区指针。
	 * @param dwVocLen 语音长度
	 * @version 2006-11-21
	 * @return true 登记成功
	 * @return false 登记失败
	 */
	native boolean SetIndex(String VocBuf,int dwVocLen);
	
	/**
	 * 开始一个内存索引放音。
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	native void StartPlayIndex(int wChnlNo,int[] pIndexTable,int wIndexLen);
	
	/**
	 * 
	 * 录音函数
	 * 
	 * 
	 */
	
	
	/**
	 * 开始文件录音。停止该方式录音，一定要有StopRecordFile。检查录音是否结束，用CheckRecordEnd函数。
	 * 文件录音在本质上是利用循环内存录音，然后，不断的更新缓冲区。PUSH_PLAY函数的调用能够保证录音被移走，从而达到录音的进行。
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @param dwRecordLen 最长录音长度
	 * @version 2006-11-21
	 * @return true 成功
	 * @return false 打开文件失败
	 */
	native boolean StartRecordFile(int wChnlNo,String FileName,int dwRecordLen);
	
	/**
	 * 本函数是对文件录音函数StartRecordFile的补充，事实上，本函数可以完全代替函数StartRecordFile。其工作方式
	 * 当dwRecordStartPos＝0时，调用函数StartRecordFile，即：创建新文件录音。
	 * 当FileName不存在时，调用函数StartRecordFile，即：创建新文件来录音。
	 * 当dwRecordStartPos＞文件长度，从文件尾部开始录音；因此，如果需要从一个文件的尾部继续录音，可令dwRecordStartPos＝0xFFFFFFFFL；
	 * 若dwRecordStartPos小于文件长度时，从dwRecordStartPos位置开始录音；
	 * 录音长度由dwRecordLen确定
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @param dwRecordLen 最长录音长度
	 * @param dwRecordStartPos 录音的开始位置
	 * @version 2006-11-21
	 * @return true 成功
	 * @return false 打开文件失败
	 */
	native boolean StartRecordFileNew(int wChnlNo,String FileName,int dwRecordLen,int dwRecordStartPos);
	
	/**
	 * 检查指定通道录音是否结束（缓冲区已满）
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 成功结束
	 * @return false 未结束
	 */
	native boolean CheckRecordEnd(int wChnlNo);
	
	/**
	 * 该函数停止指定通道的文件录音，对于StartRecordFile函数启动的录音，一定要有本函数停止。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StopRecordFile(int wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * 收码、拨号、信号音检测函数
	 * 
	 */
	
	/**
	 * 清空系统的DTMF缓冲区，如果在缓冲区中有DTMF按键的值，将会丢失。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void InitDtmfBuf(int wChnlNo);
	
	/**
	 * 取该通道收到DTMF编码，如果在缓冲区中有DTMF按键，调用本函数将返回最早的一个DTMF按键，
	 * 同时将该按键从缓冲区移去。如果缓冲区中没有收到任何DTMF按键，返回-1。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return -1 没有DTMF
	 * @return 1~9 1~9键
	 * @return 10 0键
	 * @return 11 *键
	 * @return 12 #键
	 * @return 13 A键
	 * @return 14 B键
	 * @return 15 C键
	 * @return 0 D键
	 */
	native short GetDtmfCode(int wChnlNo);
	
	/**
	 * 查看指定通道是否有DTMF按键，当收到一个有效的DTMF按键后，本函数返回true。
	 * 本函数并不会将按键从内部缓冲区中移去。若想移去该按键，调用GetDtmfCode。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 有DTMF按键
	 * @return false 没有DTMF按键
	 */
	native boolean DtmfHit(int wChnlNo);
	
	/**
	 * 发送DTMF（拨号）。“,”表示在拨号时，延时0.5秒。发送每个DTMF为125毫秒，间隔也为125毫秒。
	 * 若要中途停止拨号，调用StopPlay，检测拨号是否完成，调用CheckSendEnd，调整发送DTMF速率，使用NewSendDtmfBuf。
	 * 一次可以发送DTMF最多32个。发送DTMF本质是放音，所以不断调用PUSH_PLAY
	 * @param wChnlNo 通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	native void SendDtmfBuf(int wChnlNo,String DialNum);
	
	/**
	 * 检测某路发送DTMF是否完毕
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 发送完毕，可以检测信号音
	 * @return false 未发送完毕
	 */
	native boolean CheckSendEnd(int wChnlNo);
	
	/**
	 * 设置发送DTMF码速率。如果要用NewSendDTMFBuf来发送DTMF码，在初始化时必须调用本函数。一般调用本函数在EnableCard之后。
	 * @param ToneLen DTMF码时间长度（毫秒），最大不能超过125
	 * @param SilenceLen 间隔长度（毫秒），最大不能超过125
	 * @version 2006-11-21
	 * @return 0 成功
	 * @return 1 失败
	 */
	native int SetSendPara(int ToneLen,int SilenceLen);
	
	/**
	 * SendDtmfBuf类似，发送速率与SetSendPara有关。最大长度与EnableCard有关。
	 * @param wChnlNo 通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	native void NewSendDtmfBuf(int wChnlNo,String DialNum);
	
	/**
	 * 对于用NewSendDtmfBuf函数开始发送DTMF，本函数检查发送是否完毕。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return 1 完毕
	 * @return 0 未完
	 */
	native int NewCheckSendEnd(int wChnlNo);
	
	/**
	 * 某路开始新的信号音检测。一般在摘机或挂断后，调用本函数开始新信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StartSigCheck(int wChnlNo);
	
	/**
	 * 停止某路信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native void StopSigCheck(int wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo 通道号
	 * @param wMode 信号音类型
	 * @version 2006-11-21
	 * @return
	 */
	native int ReadCheckResult(int wChnlNo,int wMode);
	
	/**
	 * 通道为ReadCheckResult函数的通道
	 * @param
	 * @version 2006-11-21
	 * @return 当前最大连续忙音个数
	 */
	native int ReadBusyCount();
	
	/**
	 * 检测通道极性，通道向外拨号，摘机，延时一秒调用函数记录极性，拨号后，检测极性改变为对方摘机
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	native boolean CheckPolarity(int wChnlNo);
	
	/**
	 * 检测线路的静音情况
	 * @param wChnlNo 通道号
	 * @param wCheckNum 检测的信号音个数，有效值为1~511
	 * @version 2006-11-21
	 * @return -1 信号音缓冲区中的个数还不足wCheckNum个
	 * @return 0~wCheckNum wCheckNum个信号音采样中，1的个数 
	 */
	native int CheckSilence(int wChnlNo,int wCheckNum);
	
	
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
	native void StartPlaySignal(int wChnlNo,int SigType); 
	
	/**
	 * 某一通道开始挂机检测；当某通道摘机后，可以调用本函数。该函数只对内线通道有效。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void StartHangUpDetect(int wChnlNo);
	
	/**
	 * 检测某一通道的挂机状态；该函数需要在调用StartHangUpDetect之后使用。如果你需要检测拍叉簧,请使用本函数。
	 * 另外，有的电话机在摘机时，会有抖动。如果使用函数RingDetect来检测其摘机和挂机，可能会出现刚摘机就断线的情况，因此，调用本函数来避免这种情况。
	 * 该函数只对内线通道有效
	 * @param  wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native int HangUpDetect(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。调用本函数后，本通道所在连接的电话机将会不停振铃，直到调用函数FeedPower才会停止
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedRing(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。响铃0.75秒，停3秒。若要停止调用函数FeedPower
	 * 在本通道正在振铃情况下，检测摘机必须使用OffHookDetect，不能调用RingDetect。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedRealRing(int wChnlNo);
	
	/**
	 * 对某一路内线通道馈电，同时停止馈铃流
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedPower(int wChnlNo);
	
	/**
	 * 检测某一路内线通道的摘机状态，当调用FeedRealRing函数开始一个断续铃流后，请调用本函数来检测摘机状态。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return true 已摘机
	 * @return false 未摘机
	 */
	native boolean OffHookDetect(int wChnlNo);
	
	/**
	 * 将lpFileName读取到内部缓冲区，在lpFileName中应含有一段信号音，系统将会使用该段声音产生拨号音、忙音、回铃音等信号音。在系统内部有一个缺省的信号音，用户也可以录制喜欢的信号音，然后用本函数来替换缺省信号音。
	 * @param lpFileName 信号音文件名
	 * @version 2006-11-22
	 * @return true 成功
	 * @return false 读取失败
	 */
	native boolean ReadGenerateSigBuf(String lpFileName);
	
	/**
	 * 维持断续振铃及信号音的函数；请在程序大循环中调用。
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void FeedSigFunc();
	
	/**
	 * 某通道启动一个计时器
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	native void StartTimer(int wChnlNo,int ClockType);
	
	/**
	 * 返回计时器启动到现在的时间，单位0.01秒。
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return 计时器启动到现在的时间，单位0.01秒
	 */
	native int ElapseTime(int wChnlNo,int ClockType);
	
	
	/**
	 * 
	 * 连通函数
	 * 
	 */
	
	/**
	 * 两路连通
	 * 两路通道正在连通时，如果对某一通道放音或停止放音，将会变成单项连通。调用本函数之前必须停止放音。
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 */
	native int SetLink(int wOne,int wAnother);
	
	/**
	 * 两路拆除连接
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 */
	native int ClearLink(int wOne,int wAnother);
	
	/**
	 * 两路单项连通。调用该函数后，实现单项连通；通道one可以听到another声音，但another听不到one的声音。
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 */
	native int LinkOneToAnother(int wOne,int wAnother);
	
	/**
	 * 拆除单项连通
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 */
	native int ClearOneFromAnother(int wOne,int wAnother);
	
	/**
	 * 三方连通，由单项连通实现，所以声音比较小。
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @param wThree 三路
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 * @return -6 三路超出范围
	 * @return -7 三路正在放音
	 */
	native int LinkThree(int wOne,int wTwo,int wThree);
	
	/**
	 * 拆除三方连通
	 * @param wOne 一路（0～127）
	 * @param wAnother 二路（0～127）
	 * @param wThree 三路
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 一路超出范围
	 * @return -2 二路超出范围
	 * @return -3 一路二路不在一个卡上，卡之间没有连线
	 * @return -4 一路正在放音
	 * @return -5 二路正在放音
	 * @return -6 三路超出范围
	 * @return -7 三路正在放音
	 */
	native int ClearThree(int wOne,int wTwo,int wThree);
	
	
	/**
	 * 
	 * 收主叫号码的有关函数
	 * 
	 * 以下是收FSK
	 * 主要用到
	 * ResetCallerIDBuffer
	 * GetCallerIDRawStr
	 * 两个函数即可，其他函数不做说明了
	 */
	
	/**
	 * 初始化某路Caller-ID缓冲区
	 * @param  wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void ResetCallerIDBuffer(int wChnlNo);
	
	/**
	 * 获得Call-ID的内容，IDStr由用户分配，128字节绝对安全。当返回3或4时，收到。单一格式或符合格式，都能接收。
	 * 检测有振铃后，调用ResetCallerIDBuffer，当本函数返回3或4时，摘机。需要定时器，超时收不到FSK摘机
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return IDRawStr 接收到主叫号码信息
	 * @return 0 未收到信息
	 * @return 1 正在接收头信息
	 * @return 2 正在接收id号码
	 * @return 3 接收完毕，校验正确
	 * @return 4 接收完毕，校验错误
	 */
	native String GetCallerIDRawStr(int wChnlNo);
	
	/**
	 * 接收主叫号码信息
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native String GetCallerIDStr(int wChnlNo);
	
	
	/**
	 * 
	 * 语音格式转换函数
	 * DJCVT.dll
	 * 
	 */
	
	/**
	 * 把A-law PCm文件转换为wave文件
	 * @param PcmFileName A-law PCm文件
	 * @param WaveFileName 转换后文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	native int PcmtoWave(String PcmFileName,String WaveFileName);
	
	/**
	 * 把wave文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param WaveFileName 转换后文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	native int WavetoPcm(String WaveFileName,String PcmFileName);
	
	/**
	 * 把4位8K采样的ADPCM文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位8K采样的ADPCM文件 
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	native int AdtoPcm(String AdpcmFileName,String PcmFileName);
	
	/**
	 * 把A-law PCm文件 转换为4位8K采样的ADPCM文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位8K采样的ADPCM文件 
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	native int PcmtoAd(String PcmFileName,String AdpcmFileName);
	
	/**
	 * 把4位6K采样的Dialogic文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位6K采样的Dialogic文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	native int Ad6ktoPcm(String AdpcmFileName,String PcmFileName);
	
	
	/**
	 * 
	 * 其他函数
	 * 
	 */
	
	/**
	 * 调整音量 初始化0db
	 * @param wChnl 通道号
	 * @param cMode 0：录音音量，1:放音音量
	 * @param cVolAdjust 音量幅度
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1 超出范围 -20db～20db
	 */
	native int D_AdjustVocVol(int wChnl,char cMode,char cVolAdjust);
	
	/**
	 * 功能同上，vc以外开发语言用
	 * @deprecated
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D_AdjustVocVol_ForVB(int wChnl,int cMode,int cVolAdjust);
	
	/**
	 * 检测录音模块摘挂机，只对录音模块有效
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return 0 挂机
	 * @return 1 摘机
	 */
	native boolean DRec_OffHookDetect(int wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * 内存录音和回声抑制
	 * 
	 */
	
	/**
	 * 设置录音数据刷新的大小，缺省1024，
	 * @param wSize 刷新数据大小，512的整倍数，不能大于系统语音缓冲区的一半。
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return -1在EnableCard之前调用了本函数
	 * @return -2 不是512的整倍数
	 * @return -3 太大
	 * @return -4 系统缓冲区的大小无法整除wSize
	 * @return -5 不是D161A
	 */
	native int VR_SetRefreshSize(int wSize);
	
	/**
	 * 开始内存录音
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_StartRecord(int wChnlNo);
	
	/**
	 * 停止内存录音
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_StopRecord(int wChnlNo);
	
	/**
	 * 得到录音数据
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return 缓冲区数据
	 */
	native String VR_GetRecordData(int wChnlNo);
	
	/**
	 * 是否对通道启动回声抑制功能
	 * @param wChnlNo 通道号
	 * @param cbEnableFlag 0:关闭，1:启动
	 * @param wParam1 0
	 * @param wParam2 0
	 * @version 2006-11-22
	 * @return
	 */
	native void VR_SetEcrMode(int wChnl,byte cbEnableFlag,int wParam1,int wParam2);
	
	
	
	/**
	 * 
	 * 
	 * 收发FSK
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_InitForFsk(int Mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_ResetFskBuffer(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_CheckSendFSKEnd(int vocID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSK(int trunkID,byte[] pInfo,int wSize,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_GetFSK(int trunkID,byte[] pInfo,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_StopSend(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native void DJFsk_Release();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSKA(int trunkID,byte[] pInfo,int wSize,int Mode,int MarkNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int DJFsk_SendFSKBit(int trunkID,byte[] pInfo,int wSize,int Mode);
	
	
	
	
	/**
	 * 
	 * 过机系统函数
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D160PCI_GetTimeSlot(int wD160AChnl);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D160PCI_ConnectFromTS(int wD160Achnl,int wChnlTS);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	native int D160PCI_DisconnectTS(int wD160Achnl);
	
	
	
	
	/**
	 * 
	 * 
	 * 会议实现
	 * 
	 * 
	 * 
	 */
	/**
	 * 将一个通道加入会议
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 */
	native int AddChnl(int ConfNo,int ChannelNo,int ChnlAtte,int NoiseSupp);
	
	/**
	 * 将一个通道从会议中去掉
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 根据ChannelNo找到资源非法
	 */
	native int SubChnl(int ConfNo,int ChannekNo);
	
	/**
	 * 加入一个通道到会议，只是听，不能说
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 */
	native int AddListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * 去掉一个听会议的通道
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 根据ChannelNo找到资源非法
	 */
	native int SubListenChnk(int ConfNo,int ChannelNo);
	
	/**
	 * 初始化
	 * @param
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 不是D161A
	 * @return 2 在INI中，Connect必须是1
	 * @return 3 已经使用模拟会议卡，初始化成功
	 */
	native int DConf_EnableConfCard();
	
	/**
	 * 禁止会议功能
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	native void DConf_DisableConfCard();
	
	/**
	 * 将一个通道的录音转变为对会议录音
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 * @return 4 不是使用D161A内置会议功能
	 */
	native int DConf_AddRecListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * 去掉一个对会议的录音，回复对通道录音
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 * @return 4 不是使用D161A内置会议功能
	 */
	native int DConf_SubRecListenChnl(int ConfNo,int ChannelNo);
	
	/**
	 * 得到当前可以用会议资源数。
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	native int DConf_GetResNumber();
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	 * 新的信号音检测函数
	 * 
	 * 
	 * 
	 */
	/**
	 * 初始化
	 * @param wPara 缺省0
	 * @version 2006-12-22
	 * @return 1 成功
	 * @return 0 失败
	 */
	native int sigInit(int wPara);
	/**
	 * 对某通道进行挂机忙音检测
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return 1 检测到忙音
	 * @return 0 没有忙音
	 */
	native int sigCheckBusy(int wChNo);
	/**
	 * 清空忙音检测缓冲区及内部计数
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return
	 */
	native void sigResetCheck(int wChNo);
	/**
	 * 开始某通道呼出过程
	 * @param wChNo 通道号
	 * @param DialNum 呼出号码
	 * @param PreDialNum 前导号码
	 * @param wMode 呼出检测模式
	 * @version 2007-1-4
	 * @return 1 成功
	 * @return 0 失败
	 */
	native int sigStartDial(int wChNo,String DialNum,String PreDialNum,int wMode);
	/**
	 * 检测信号音个数
	 * @param wChNo 通道号
	 * @param nCadenceType 信号音类型 1 忙音，2 回铃音
	 * @version 2007-1-4
	 * @return 信号音个数
	 */
	native int sigGetCadenceCount(int wChNo,int nCadenceType);
	/**
	 * 在sigStartDial启动拨号之后循环调用维持拨号检测呼出结果。
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return
	 */
	native int sigCheckDial(int wChNo);
	
	
	
	public static void main(String[] arg0)
	{
		Dongjin dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}
}
