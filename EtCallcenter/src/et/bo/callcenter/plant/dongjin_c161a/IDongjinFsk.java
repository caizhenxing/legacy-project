/**
 * 	@(#)IDongjinFsk.java   2006-12-22 上午10:42:00
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinFsk {

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
	public abstract void resetCallerIDBuffer(int wChnlNo);
	
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
	public abstract String getCallerIDRawStr(int wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract String getCallerIDStr(int wChnlNo);
	
	
	
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
	public abstract int dJFskInitForFsk(int mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskResetFskBuffer(int trunkID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskCheckSendFSKEnd(int vocID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSK(int trunkID,byte[] pInfo,int wSize,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskGetFSK(int trunkID,byte[] pInfo,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskStopSend(int trunkID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskRelease();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSKA(int trunkID,byte[] pInfo,int wSize,int mode,int markNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSKBit(int trunkID,byte[] pInfo,int wSize,int mode);
	
}
