/**
 * 	@(#)MailBoxService.java   Aug 30, 2006 7:22:58 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.mailbox.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public interface MailBoxService {
	
	/**
	 * 邮箱信息添加
	 * @param dto 类型 IBaseDTO 邮箱信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean addMailBox(IBaseDTO dto);
	
	/**
	 * 邮箱信息修改
	 * @param dto 类型 IBaseDTO 邮箱信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean updateMailBox(IBaseDTO dto);
	
	/**
	 * 邮箱信息删除
	 * @param dto 类型 IBaseDTO 邮箱信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean delMailBox(IBaseDTO dto);
	
	/**
	 * 查询邮箱信息
	 * @param dto 类型 IBaseDTO 邮件信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @param mailboxType 类型 String 邮箱类型
	 * @return 类型 List 返回邮件列表信息
	 */
	public List emailBoxIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getEmailBoxSize();
	
	/**
	 * 得到邮件具体信息
	 * @param id 类型 String 邮件id
	 * @version Aug 30, 2006
	 * @return
	 */
	public IBaseDTO getEmailBox(String id);

}
