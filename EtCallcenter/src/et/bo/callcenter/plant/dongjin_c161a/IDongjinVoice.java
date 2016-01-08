/**
 * 	@(#)IDongjinVoice.java   2007-1-4 下午04:35:17
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2007-1-4
 * @see
 */
public interface IDongjinVoice {

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
	public abstract int sigInit(int wPara);
	/**
	 * 对某通道进行挂机忙音检测
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return 1 检测到忙音
	 * @return 0 没有忙音
	 */
	public abstract int sigCheckBusy(int wChNo);
	/**
	 * 清空忙音检测缓冲区及内部计数
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return
	 */
	public abstract void sigResetCheck(int wChNo);
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
	public abstract int sigStartDial(int wChNo,String dialNum,String preDialNum,int wMode);
	/**
	 * 检测信号音个数
	 * @param wChNo 通道号
	 * @param nCadenceType 信号音类型 1 忙音，2 回铃音
	 * @version 2007-1-4
	 * @return 信号音个数
	 */
	public abstract int sigGetCadenceCount(int wChNo,int nCadenceType);
	/**
	 * 在sigStartDial启动拨号之后循环调用维持拨号检测呼出结果。
	 * @param wChNo 通道号
	 * @version 2007-1-4
	 * @return
	 */
	public abstract int sigCheckDial(int wChNo);
	
	
}
