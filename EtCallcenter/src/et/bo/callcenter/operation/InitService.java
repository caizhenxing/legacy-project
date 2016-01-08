/**
 * 	@(#)InitService.java   2007-1-22 下午03:17:42
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

 /**
 * @author zhaoyifei
 * @version 2007-1-22
 * @see
 */
public interface InitService {

	/**
	 * 初始化
	 * @param ip 初始化参数
	 * @version 2007-1-16
	 * @return
	 */
	public abstract boolean init(InitInfo ip);
	/**
	 * 结束收尾
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void free();
	/**
	 * 得到系统信息
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract SysInfo getSysInfo();
	
	/**
	 * 辅助函数，每100ms调用一次
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public abstract void assist();
}
