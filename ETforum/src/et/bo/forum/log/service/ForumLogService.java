/**
 * 	@(#)ForumLogService.java   2007-1-8 上午09:45:24
 *	 。 
 *	 
 */
package et.bo.forum.log.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 叶浦亮
 * @version 2007-1-8
 * @see
 */
public interface ForumLogService {
	/**
	 * 日志添加
	 * @param userId 用户id, moduleName 模块名, action 动作, flag 标志位
	 * @version 2007-1-8
	 * @return
	 */
	public void addLog(String userId, String moduleName, String action, String ip, String flag);
	/**
	 * 日志查询
	 * @param
	 * @version 2007-1-8
	 * @return
	 */
	public List logList(IBaseDTO dto, PageInfo pageInfo);
	public int getSize();
   
}
