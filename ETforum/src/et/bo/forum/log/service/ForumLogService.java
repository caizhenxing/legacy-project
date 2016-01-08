/**
 * 	@(#)ForumLogService.java   2007-1-8 ����09:45:24
 *	 �� 
 *	 
 */
package et.bo.forum.log.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Ҷ����
 * @version 2007-1-8
 * @see
 */
public interface ForumLogService {
	/**
	 * ��־���
	 * @param userId �û�id, moduleName ģ����, action ����, flag ��־λ
	 * @version 2007-1-8
	 * @return
	 */
	public void addLog(String userId, String moduleName, String action, String ip, String flag);
	/**
	 * ��־��ѯ
	 * @param
	 * @version 2007-1-8
	 * @return
	 */
	public List logList(IBaseDTO dto, PageInfo pageInfo);
	public int getSize();
   
}
