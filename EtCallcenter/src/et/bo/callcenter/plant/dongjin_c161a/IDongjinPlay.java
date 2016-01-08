/**
 * 	@(#)IDongjinPlayImpl.java   2006-12-21 下午04:56:45
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinPlay {

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
	public void startPlay(int wChnlNo,String playBuf,int dwStartPos,int dwPlayLen);
	
	/**
	 * 指定通道停职内存放音，本函数可以停止内存普通放音，内存索引放音，内存循环放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public void stopPlay(int wChnlNo);
	
	/**
	 * 检查指定通道放音是否结束。本函数可以用于内存普通放音，内存索引放音，内存循环放音和文件放音。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return false 未结束
	 * @return true 结束
	 */
	public boolean checkPlayEnd(int wChnlNo);
	
	/**
	 * 开始文件放音。停止该方式放音一定要用StopPlayFile。检查放音是否结束，用CheckPlayEnd函数。
	 * 文件放音在本质上是利用内存循环放音，然后，不断的更新缓冲区。PUSH_PLAY函数的调用，能够保证对放音缓冲区的更新，从而达到放音连续不断。
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @param StartPos 放音的起始位置
	 * @version 2006-11-21
	 * @return
	 */
	public boolean startPlayFile(int wChnlNo,String fileName,int startPos);
	
	/**
	 * 本函数对指定通道停止文件放音。对于用函数StartPlayFile开始的放音，必须用本函数来停止，这样才能关闭语音文件。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public void stopPlayFile(int wChnlNo);
	
	/**
	 * 初始化多文件放音。每开始一个新的多文件放音前调用此函数
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public void rsetIndexPlayFile(int wChnlNo);
	
	/**
	 * 增加多文件放音的放音文件
	 * @param wChnlNo 通道号
	 * @param FileName 文件名
	 * @version 2006-11-21
	 * @return true 成功
	 * @return false 失败
	 */
	public boolean addIndexPlayFile(int wChnlNo,String fileName);
	
	/**
	 * 开始一个多文件放音。当调用该函数成功后，必须循环调用CheckIndexPlayFile函数来检测放音是否结束，
	 * 并维护多文件放音的连续进行
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public boolean startIndexPlayFile(int wChnlNo);
	
	/**
	 * 检查多文件放音是否结束，并维护多文件放音的连续性。当进行多文件放音时，必须调用本函数，以保证多文件放音的连续性。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public boolean checkIndexPlayFile(int wChnlNo);
	
	/**
	 * 停止多文件放音。该函数停止指定通道多文件放音，对于使用StartIndexPlayFile函数开始的多文件放音，结束时一定调用本函数。
	 * @param wChnlNo 通道号
	 * @version 2006-11-21
	 * @return
	 */
	public void stopIndexPlayFile(int wChnlNo);
	
	/**
	 * 初始化索引内存放音
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void resetIndex();
	
	/**
	 * 设置索引内存放音登记项
	 * @param VocBuf 指向要登记的语音缓冲区指针。
	 * @param dwVocLen 语音长度
	 * @version 2006-11-21
	 * @return true 登记成功
	 * @return false 登记失败
	 */
	public boolean setIndex(String vocBuf,int dwVocLen);
	
	/**
	 * 开始一个内存索引放音。
	 * @param
	 * @version 2006-11-21
	 * @return
	 */
	public void startPlayIndex(int wChnlNo,int[] pIndexTable,int wIndexLen);
	
}
