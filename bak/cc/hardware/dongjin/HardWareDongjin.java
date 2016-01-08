/**
 * 	@(#)HardWareDongjin.java   2006-11-20 下午05:24:46
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.cc.hardware.dongjin;

 /**
 * @author ddddd
 * @version 2006-11-20
 * @see
 */
public class HardWareDongjin {

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
	private native long LoadDRV();
	
	/**
	 * 关闭驱动程序
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native void FreeDRV();
	
	/**
	 * 初始化电话卡的硬件并为每一个通道分配语音缓冲区。
	 * @param wUsedCh 工作的通道总数
	 * @param wFileBufLen 驱动中为每个通道分配的语音内存大小，1024的整数倍
	 * @version 2006-11-20
	 * @return 0 成功
	 * @return -1 LoadDRV没有成功，导致本函数调用失败
	 * @return -2 分配缓冲区失败
	 */
	private native long EnableCard(String wUsedCh,String wFileBufLen);
	
	/**
	 * 关闭电话卡硬件，释放缓冲区。程序结束（正常非正常退出）调用
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native void DisableCard();
	
	/**
	 * 获得系统配置的有关信息
	 * @param TmpIni 结构TC_INI_TYPE String[9]
	 * @see TcIniType
	 * @version 2006-11-20
	 * @return
	 */
	private native void GetSysInfo(String[] TmpIni);
	
	/**
	 * 获得系统更多配置
	 * @param TmpMore 结构TC_INI_TYPE_MORE String[12] 
	 * @see TcIniTypeMore
	 * @version 2006-11-20
	 * @return
	 */
	private native void GetSysInfoMore(String[] TmpMore);
	
	/**
	 * 总的可用的通道数
	 * @param
	 * @version 2006-11-20
	 * @return
	 */
	private native String CheckValidCh();
	
	/**
	 * 检测通道类型
	 * @deprecated
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空 
	 */
	private native String CheckChType(String wChnlNo);
	
	/**
	 * 检测通道类型
	 * @param wChnlNo 通道号
	 * @version 2006-11-20
	 * @return 0 内线
	 * @return 1 外线
	 * @return 2 悬空
	 * @return 3 录音模块
	 */
	private native String CheckChTypeNew(String wChnlNo);
	
	/**
	 * 判断该卡是否支持Caller-ID功能 D161A PCI卡将返回1
	 * @param
	 * @version 2006-11-21
	 * @return 1支持
	 * @return 0不支持
	 */
	private native boolean IsSupportCallerID();
	
	/**
	 * 设置压缩比率。调用本函数后，录放音均以该压缩比率进行。
	 * @param wPackRate压缩比率 0 64k bits/s  1 32k bits/s
	 * 
	 * @version 2006-11-21
	 * @return
	 */
	private native void SetPackRate(String wPackRate);
	
	/**
	 * 维持文件录放音的持续进行，需要在处理函数的大循环中调用
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void PUSH_PLAY();
	
	/**
	 * 设定要检测的挂机忙音的参数。国标中的0.7秒写为 SetBusyPara(700);
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void SetBusyPara(String BusyLen);
	
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
	private native void SetDialPara(String RingBackl,String RingBack0, String BusyLen,String RingTimes);
	
	/**
	 * 读取D161A PCI卡的序列号。惟一编号。
	 * 惟一编号。在函数LoadDRV之后，EnableCard之前读取。
	 * @param wCardNo 卡号
	 * @version 2006-11-21
	 * @return 编号
	 */
	private native long NewReadPass(String wCardNo);
	
	/**
	 * 设定某通道工作参数
	 * @param wChnlNo 通道号
	 * @param cbWorkMode 选择要设定的工作模式
	 * @param CbModeVal 参数值
	 * @version 2006-11-21
	 * @return
	 */
	private native void D_SetWorkMode(String wChnlNo,char cbWorkMode,char CbModeVal);
	
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
	private native boolean RingDetect(String wChnlNo);
	
	/**
	 * 外线提机，对于内线，不起作用
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void OffHook(String wChnlNo);
	
	/**
	 * 外线挂机，对于内线，不起作用
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void HangUp(String wChnlNo);
	
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
	private native void StartPlay(String wChnlNo,String PlayBuf,String dwStartPos,String dwPlayLen);
	
	/**
	 * 指定通道停职内存放音，本函数可以停止内存普通放音，内存索引放音，内存循环放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopPlay(String wChnlNo);
	
	/**
	 * 检查指定通道放音是否结束。本函数可以用于内存普通放音，内存索引放音，内存循环放音和文件放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return false 未结束
	 * @return true 结束
	 */
	private native boolean CheckPlayEnd(String wChnlNo);
	
	/**
	 * 开始文件放音。停止该方式放音一定要用StopPlayFile。检查放音是否结束，用CheckPlayEnd函数。
	 * 文件放音在本质上是利用内存循环放音，然后，不断的更新缓冲区。PUSH_PLAY函数的调用，能够保证对放音缓冲区的更新，从而达到放音连续不断。
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @param StartPos 放音的起始位置
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean StartPlayFile(String wChnlNo,String FileName,String StartPos);
	
	/**
	 * 本函数对指定通道停止文件放音。对于用函数StartPlayFile开始的放音，必须用本函数来停止，这样才能关闭语音文件。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopPlayFile(String wChnlNo);
	
	/**
	 * 初始化多文件放音。每开始一个新的多文件放音前调用此函数
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void RsetIndexPlayFile(String wChnlNo);
	
	/**
	 * 增加多文件放音的放音文件
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @version 2006-11-21
	 * @return true 成功
	 * @return false 失败
	 */
	private native boolean AddIndexPlayFile(String wChnlNo,String FileName);
	
	/**
	 * 开始一个多文件放音。当调用该函数成功后，必须循环调用CheckIndexPlayFile函数来检测放音是否结束，
	 * 并维护多文件放音的连续进行
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean StartIndexPlayFile(String wChnlNo);
	
	/**
	 * 检查多文件放音是否结束，并维护多文件放音的连续性。当进行多文件放音时，必须调用本函数，以保证多文件放音的连续性。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean CheckIndexPlayFile(String ChnlNo);
	
	/**
	 * 停止多文件放音。该函数停止指定通道多文件放音，对于使用StartIndexPlayFile函数开始的多文件放音，结束时一定调用本函数。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopIndexPlayFile(String wChnlNo);
	
	/**
	 * 初始化索引内存放音
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void ResetIndex();
	
	/**
	 * 设置索引内存放音登记项
	 * @param VocBuf 指向要登记的语音缓冲区指针。
	 * @param dwVocLen 语音长度
	 * @version 2006-11-21
	 * @return true 登记成功
	 * @return false 登记失败
	 */
	private native boolean SetIndex(String VocBuf,String dwVocLen);
	
	/**
	 * 开始一个内存索引放音。
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartPlayIndex(String wChnlNo,String[] pIndexTable,String wIndexLen);
	
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
	private native boolean StartRecordFile(String wChnlNo,String FileName,String dwRecordLen);
	
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
	private native boolean StartRecordFileNew(String wChnlNo,String FileName,String dwRecordLen,String dwRecordStartPos);
	
	/**
	 * 检查指定通道录音是否结束（缓冲区已满）
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 成功结束
	 * @return false 未结束
	 */
	private native boolean CheckRecordEnd(String wChnlNo);
	
	/**
	 * 该函数停止指定通道的文件录音，对于StartRecordFile函数启动的录音，一定要有本函数停止。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopRecordFile(String wChnlNo);
	
	
	
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
	private native void InitDtmfBuf(String wChnlNo);
	
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
	private native short GetDtmfCode(String wChnlNo);
	
	/**
	 * 查看指定通道是否有DTMF按键，当收到一个有效的DTMF按键后，本函数返回true。
	 * 本函数并不会将按键从内部缓冲区中移去。若想移去该按键，调用GetDtmfCode。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 有DTMF按键
	 * @return false 没有DTMF按键
	 */
	private native boolean DtmfHit(String wChnlNo);
	
	/**
	 * 发送DTMF（拨号）。“,”表示在拨号时，延时0.5秒。发送每个DTMF为125毫秒，间隔也为125毫秒。
	 * 若要中途停止拨号，调用StopPlay，检测拨号是否完成，调用CheckSendEnd，调整发送DTMF速率，使用NewSendDtmfBuf。
	 * 一次可以发送DTMF最多32个。发送DTMF本质是放音，所以不断调用PUSH_PLAY
	 * @param wChnlNo 通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	private native void SendDtmfBuf(String wChnlNo,String DialNum);
	
	/**
	 * 检测某路发送DTMF是否完毕
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 发送完毕，可以检测信号音
	 * @return false 未发送完毕
	 */
	private native boolean CheckSendEnd(String wChnlNo);
	
	/**
	 * 设置发送DTMF码速率。如果要用NewSendDTMFBuf来发送DTMF码，在初始化时必须调用本函数。一般调用本函数在EnableCard之后。
	 * @param ToneLen DTMF码时间长度（毫秒），最大不能超过125
	 * @param SilenceLen 间隔长度（毫秒），最大不能超过125
	 * @version 2006-11-21
	 * @return 0 成功
	 * @return 1 失败
	 */
	private native int SetSendPara(int ToneLen,int SilenceLen);
	
	/**
	 * SendDtmfBuf类似，发送速率与SetSendPara有关。最大长度与EnableCard有关。
	 * @param ChannelNo  通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	private native void NewSendDtmfBuf(int ChannelNo,String DialNum);
	
	/**
	 * 对于用NewSendDtmfBuf函数开始发送DTMF，本函数检查发送是否完毕。
	 * @param ChannelNo  通道号
	 * @version 2006-11-21
	 * @return 1 完毕
	 * @return 0 未完
	 */
	private native int NewCheckSendEnd(int ChannelNo);
	
	/**
	 * 某路开始新的信号音检测。一般在摘机或挂断后，调用本函数开始新信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StartSigCheck(String wChnlNo);
	
	/**
	 * 停止某路信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native void StopSigCheck(String wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo 通道号
	 * @param wMode 信号音类型
	 * @version 2006-11-21
	 * @return
	 */
	private native String ReadCheckResult(String wChnlNo,String wMode);
	
	/**
	 * 通道为ReadCheckResult函数的通道
	 * @param
	 * @version 2006-11-21
	 * @return 当前最大连续忙音个数
	 */
	private native String ReadBusyCount();
	
	/**
	 * 检测通道极性，通道向外拨号，摘机，延时一秒调用函数记录极性，拨号后，检测极性改变为对方摘机
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	private native boolean CheckPolarity(String wChnlNo);
	
	/**
	 * 检测线路的静音情况
	 * @param wChnlNo 通道号
	 * @param wCheckNum 检测的信号音个数，有效值为1~511
	 * @version 2006-11-21
	 * @return -1 信号音缓冲区中的个数还不足wCheckNum个
	 * @return 0~wCheckNum wCheckNum个信号音采样中，1的个数 
	 */
	private native long CheckSilence(String wChnlNo,String wCheckNum);
	
	
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
	private native void StartPlaySignal(String wChnlNo,String SigType); 
	
	/**
	 * 某一通道开始挂机检测；当某通道摘机后，可以调用本函数。该函数只对内线通道有效。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	private native void StartHangUpDetect(String wChnlNo);
	
	/**
	 * 检测某一通道的挂机状态；该函数需要在调用StartHangUpDetect之后使用。如果你需要检测拍叉簧,请使用本函数。
	 * 另外，有的电话机在摘机时，会有抖动。如果使用函数RingDetect来检测其摘机和挂机，可能会出现刚摘机就断线的情况，因此，调用本函数来避免这种情况。
	 * 该函数只对内线通道有效
	 * @param  wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	private native String HangUpDetect(String wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。调用本函数后，本通道所在连接的电话机将会不停振铃，直到调用函数FeedPower才会停止
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedRing(String wChnlNo);
	
	/**
	 * 对某一路内线通道馈连续铃流。响铃0.75秒，停3秒。若要停止调用函数FeedPower
	 * 在本通道正在振铃情况下，检测摘机必须使用OffHookDetect，不能调用RingDetect。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedRealRing(String wChnlNo);
	
	/**
	 * 对某一路内线通道馈电，同时停止馈铃流
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedPower(String wChnlNo);
	
	/**
	 * 检测某一路内线通道的摘机状态，当调用FeedRealRing函数开始一个断续铃流后，请调用本函数来检测摘机状态。
	 * @param wChnlNo 通道号
	 * @version 2006-11-22
	 * @return true 已摘机
	 * @return false 未摘机
	 */
	private native boolean OffHookDetect(String wChnlNo);
	
	/**
	 * 将lpFileName读取到内部缓冲区，在lpFileName中应含有一段信号音，系统将会使用该段声音产生拨号音、忙音、回铃音等信号音。在系统内部有一个缺省的信号音，用户也可以录制喜欢的信号音，然后用本函数来替换缺省信号音。
	 * @param lpFileName 信号音文件名
	 * @version 2006-11-22
	 * @return true 成功
	 * @return false 读取失败
	 */
	private native boolean ReadGenerateSigBuf(String lpFileName);
	
	/**
	 * 维持断续振铃及信号音的函数；请在程序大循环中调用。
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void FeedSigFunc();
	
	/**
	 * 某通道启动一个计时器
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return
	 */
	private native void StartTimer(String wChnlNo,String ClockType);
	
	/**
	 * 返回计时器启动到现在的时间，单位0.01秒。
	 * @param wChnlNo 通道号
	 * @param ClockType 计时器号(3~9)
	 * @version 2006-11-22
	 * @return 计时器启动到现在的时间，单位0.01秒
	 */
	private native long ElapseTime(String wChnlNo,String ClockType);
	
	
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
	private native long SetLink(String wOne,String wAnother);
	
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
	private native long ClearLink(String wOne,String wAnother);
	
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
	private native long LinkOneToAnother(String wOne,String wAnother);
	
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
	private native long ClearOneFromAnother(String wOne,String wAnother);
	
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
	private native long LinkThree(String wOne,String wTwo,String wThree);
	
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
	private native long ClearThree(String wOne,String wTwo,String wThree);
	
	
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
	private native void ResetCallerIDBuffer(String wChnlNo);
	
	/**
	 * 获得Call-ID的内容，IDStr由用户分配，128字节绝对安全。当返回3或4时，收到。单一格式或符合格式，都能接收。
	 * 检测有振铃后，调用ResetCallerIDBuffer，当本函数返回3或4时，摘机。需要定时器，超时收不到FSK摘机
	 * @param wChnlNo 通道号
	 * @param IDRawStr 接收到主叫号码信息
	 * @version 2006-11-22
	 * @return 0 未收到信息
	 * @return 1 正在接收头信息
	 * @return 2 正在接收id号码
	 * @return 3 接收完毕，校验正确
	 * @return 4 接收完毕，校验错误
	 */
	private native String GetCallerIDRawStr(String wChnlNo,String IDRawStr);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String GetCallerIDStr(String wChnlNo,String[] IDStr);
	
	
	/**
	 * 
	 * 语音格式转换函数
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int PcmtoWave(String PcmFileName,String WaveFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int WavetoPcm(String WaveFileName,String PcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int AdtoPcm(String AdpcmFileName,String PcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int PcmtoAd(String PcmFileName,String AdpcmFileName);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int Ad6ktoPcm(String AdpcmFileName,String PcmFileName);
	
	
	/**
	 * 
	 * 其他函数
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int D_AdjustVocVol(String wChnl,char cMode,char cVolAdjust);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int D_AdjustVocVol_ForVB(String wChnl,int cMode,int cVolAdjust);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native boolean DRec_OffHookDetect(String wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * 内存录音和回声抑制
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int VR_SetRefreshSize(String wSize);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_StartRecord(String wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_StopRecord(String wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int VR_GetRecordData(String wChnlNo,String pBuffer);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void VR_SetEcrMode(String wChnl,byte cbEnableFlag,String wParam1,String wParam2);
	
	
	
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
	private native int DJFsk_InitForFsk(int Mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_ResetFskBuffer(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_CheckSendFSKEnd(int vocID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSK(int trunkID,String pInfo,String wSize,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_GetFSK(int trunkID,String pInfo,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_StopSend(int trunkID,int Mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native void DJFsk_Release();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSKA(int trunkID,String pInfo,String wSize,int Mode,int MarkNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native int DJFsk_SendFSKBit(int trunkID,String pInfo,String wSize,int Mode);
	
	
	
	
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
	private native String D160PCI_GetTimeSlot(String wD160AChnl);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String D160PCI_ConnectFromTS(String wD160Achnl,String wChnlTS);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	private native String D160PCI_DisconnectTS(String wD160Achnl);
}
