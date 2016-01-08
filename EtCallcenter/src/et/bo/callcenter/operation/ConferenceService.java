/**
 * 	@(#)ConferenceService.java   2007-1-15 下午04:32:30
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface ConferenceService {

	/**
	 * 增加会议成员
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addLine(int i,LineService ls);
	/**
	 * 减少会议成员
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subLine(int i,LineService ls);
	/**
	 * 增加会议听众
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addListen(int i,LineService ls);
	/**
	 * 减少会议听众
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subListen(int i,LineService ls);
	/**
	 * 增加会议录音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addRecord(int i,LineService ls,String file);
	/**
	 * 去掉会议录音
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subRecord(int i,LineService ls);
}
