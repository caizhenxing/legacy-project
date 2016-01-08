/**
 * 	@(#)IDongjinDtmfImpl.java   2006-12-22 上午10:12:49
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinDtmf {

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
	public abstract void initDtmfBuf(int wChnlNo);
	
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
	public abstract short getDtmfCode(int wChnlNo);
	
	/**
	 * 查看指定通道是否有DTMF按键，当收到一个有效的DTMF按键后，本函数返回true。
	 * 本函数并不会将按键从内部缓冲区中移去。若想移去该按键，调用GetDtmfCode。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 有DTMF按键
	 * @return false 没有DTMF按键
	 */
	public abstract boolean dtmfHit(int wChnlNo);
	
	/**
	 * 发送DTMF（拨号）。“,”表示在拨号时，延时0.5秒。发送每个DTMF为125毫秒，间隔也为125毫秒。
	 * 若要中途停止拨号，调用StopPlay，检测拨号是否完成，调用CheckSendEnd，调整发送DTMF速率，使用NewSendDtmfBuf。
	 * 一次可以发送DTMF最多32个。发送DTMF本质是放音，所以不断调用PUSH_PLAY
	 * @param wChnlNo 通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void sendDtmfBuf(int wChnlNo,String dialNum);
	
	/**
	 * 检测某路发送DTMF是否完毕
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return true 发送完毕，可以检测信号音
	 * @return false 未发送完毕
	 */
	public abstract boolean checkSendEnd(int wChnlNo);
	
	/**
	 * 设置发送DTMF码速率。如果要用NewSendDTMFBuf来发送DTMF码，在初始化时必须调用本函数。一般调用本函数在EnableCard之后。
	 * @param ToneLen DTMF码时间长度（毫秒），最大不能超过125
	 * @param SilenceLen 间隔长度（毫秒），最大不能超过125
	 * @version 2006-11-21
	 * @return 0 成功
	 * @return 1 失败
	 */
	public abstract int setSendPara(int toneLen,int silenceLen);
	
	/**
	 * SendDtmfBuf类似，发送速率与SetSendPara有关。最大长度与EnableCard有关。
	 * @param wChnlNo 通道号
	 * @param DialNum 拨号的字符串，有效值“0”～“9”，“*”，“#”，“,”，“ABCD”
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void newSendDtmfBuf(int wChnlNo,String dialNum);
	
	/**
	 * 对于用NewSendDtmfBuf函数开始发送DTMF，本函数检查发送是否完毕。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return 1 完毕
	 * @return 0 未完
	 */
	public abstract boolean newCheckSendEnd(int wChnlNo);
	
	/**
	 * 某路开始新的信号音检测。一般在摘机或挂断后，调用本函数开始新信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void startSigCheck(int wChnlNo);
	
	/**
	 * 停止某路信号音检测
	 * @param  wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public abstract void stopSigCheck(int wChnlNo);
	
	/**
	 * 
	 * @param wChnlNo 通道号
	 * @param wMode 信号音类型
	 * @version 2006-11-21
	 * @return
	 */
	public abstract int readCheckResult(int wChnlNo,int wMode);
	
	/**
	 * 通道为ReadCheckResult函数的通道
	 * @param
	 * @version 2006-11-21
	 * @return 当前最大连续忙音个数
	 */
	public abstract int readBusyCount();
	
	/**
	 * 检测通道极性，通道向外拨号，摘机，延时一秒调用函数记录极性，拨号后，检测极性改变为对方摘机
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public abstract boolean checkPolarity(int wChnlNo);
	
	/**
	 * 检测线路的静音情况
	 * @param wChnlNo 通道号
	 * @param wCheckNum 检测的信号音个数，有效值为1~511
	 * @version 2006-11-21
	 * @return -1 信号音缓冲区中的个数还不足wCheckNum个
	 * @return 0~wCheckNum wCheckNum个信号音采样中，1的个数 
	 */
	public abstract int checkSilence(int wChnlNo,int wCheckNum);
	
}
