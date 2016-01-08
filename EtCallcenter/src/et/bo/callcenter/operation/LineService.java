/**
 * 	@(#)LineService.java   2007-1-15 下午04:03:10
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.List;
import java.util.Queue;

 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface LineService {
	
	/**
	 * 文件放音
	 * @param file 文件名
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startPlayFile(String file);
	
	/**
	 * 停止放音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopPlayFile();
	
	/**
	 * 开始索引放音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startIndexPlayFile(List<String> files);
	
	/**
	 * 停止索引放音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopIndexPlayFile();
	
	/**
	 * 开始文件录音
	 * @param 
	 * @version 2007-1-16
	 * @return
	 */
	public boolean startRecordFile(String file);
	
	
	/**
	 * 结束录音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void stopRecordFile();
	
	/**
	 * 收dtmf
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void receiveDtmf();
	
	/**
	 * 清空dtmf
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void clearDtmf();
	
	/**
	 * 收fsk
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	public void receiveFsk();
	
	/**
	 * 发送dtmf
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public void sendDtmf(String dtmf);
	
	
	
	/**
	 * 监听端口信息
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public Queue listener();
	
	
	public int getLineNum();
	
	/**
	 * 放信号音
	 * @param sigType 信号音类型
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void startPlaySignal(int sigType);
	/**
	 * 振铃 标准振铃
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedRealRing();
	/**
	 * 振铃 自定义振铃
	 * @param ring 振铃 interval 间隔 毫秒单位
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedRealRing(int ring,int interval);
	/**
	 * 停铃
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void feedPower();
	
	/**
	 * 摘机
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void offHook();
	/**
	 * 挂断
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void hangUp();
	
	/**
	 * 外线拨号
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public abstract void dial(String dail,String pre);
	
	/**
	 * @return the li
	 */
	public LineInfo getLi();
	/**
	 * @param li the li to set
	 */
	public void setLi(LineInfo li);
	
	/**
	 * 停止放音
	 * @param
	 * @version 2007-1-30
	 * @return
	 */
	public void stopPaly();
	
	/**
	 * 开始计时，如果超过时限，产生超时事件
	 * @param hms 单位100ms
	 */
	public void startTime(int hms);
	
	/**
	 * 停止计时
	 *
	 */
	public void endTime();
}
