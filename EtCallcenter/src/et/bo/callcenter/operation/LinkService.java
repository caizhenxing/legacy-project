/**
 * 	@(#)LinkService.java   2007-1-15 下午04:33:51
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface LinkService {

	/**
	 * 两路电话连接
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setLink(LineService ls1,LineService ls2);
	/**
	 * 两路电话拆线
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract LinkInfo clearLink(LineService ls);
	/**
	 * 单向连通
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setListen(LineService ls1,LineService ls2);
	
	/**
	 * 三方通话
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setlinkThree(LineService ls1,LineService ls2,LineService ls3);
	
	/**
	 * 通话录音
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public abstract void setRecord(LineService ls);
	
	
	public abstract boolean contain(LineService ls);
}
