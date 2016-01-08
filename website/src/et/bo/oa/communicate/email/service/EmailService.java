/**
 * @(#)EmailService.java	1.0 06/08/28
 *
 *	 。 
 *	 
 *
 */
package et.bo.oa.communicate.email.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * 邮件收发接口类
 * @author  张峰
 * @author  赵一非
 * @version 1.0, 06/08/28
 * @see	    PageInfo
 * @see		IBaseDTO
 */
public interface EmailService {
	
	/**
	 * 保存邮件到草稿箱
	 * @param dto 类型 IBaseDTO 邮件信息
	 * @param adjunctList 类型 List 附件列表
	 * @return 类型 boolean 如果保存成功 返回ture，反之返回false
	 * 
	 */
	public boolean saveEmailToDraft(IBaseDTO dto,List adjunctList);
	
	/**
	 * 保存到发件箱,转发邮件信息,回复邮件信息
	 * @param dto 类型 IBaseDTO 邮件信息
	 * @param adjunctList 类型 List 附件列表
	 * @param mailType 类型 String 邮件类型
	 * @return 类型 boolean 如果保存成功 返回ture，反之返回false
	 */
	public boolean saveEmailToAddresser(IBaseDTO dto,List adjunctList,String mailType);
	
	/**
	 * 删除邮件信息到垃圾箱
	 * @param selectIt 类型 String[] 选择邮件列表信息
	 * @return 类型 boolean 修改删除标志位 返回ture，反之返回false
	 */
	public boolean delEmailToDustbin(String[] selectIt);
	
	/**
	 * 永久删除邮件信息
	 * @param selectIt 类型 String[] 选择邮件列表信息
	 * @return 类型 boolean 如果永久删除成功 返回ture，反之返回false
	 */
	public boolean delEmailForever(String[] selectIt);
	
	/**
	 * 查询邮件列表信息(包括收件箱，发件箱,已发邮件,草稿箱,垃圾箱)
	 * @param dto 类型 IBaseDTO 邮件信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @param mailboxType 类型 String 邮箱类型
	 * @return 类型 List 返回邮件列表信息
	 */
	public List emailListIndex(IBaseDTO dto,PageInfo pageInfo,String mailboxType);
	public int getEmailIndexSize();
	
	/**
	 * 根据邮件id得到对应的邮件信息
	 * @param id 类型 String 邮件信息
	 * @return 类型 IBaseDTO 邮件列表信息
	 */
	public IBaseDTO getInEmailInfo(String id);
	
	/**
	 * 得到邮件列表信息
	 * @param
	 * @version Sep 8, 2006
	 * @return
	 */
	public List getEmailBoxList(String userkey);
	
	/**
	 * IBaseDTO
	 * @param
	 * @version Sep 12, 2006
	 * @return
	 */
	public List<IBaseDTO> userList();

}
