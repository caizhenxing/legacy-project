/**
 * 	@(#)PhoneService.java   Oct 12, 2006 11:01:19 AM
 *	 。 
 *	 
 */
package et.bo.pcc.assay.phone.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 12, 2006
 * @see
 */
public interface PhoneService {
	
	/**
	 * 查询电话信息
	 * @param dto 类型 IBaseDTO 电话信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List phoneIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getPhoneSize();
	
	/**
	 * 得到问题信息
	 * @param id 类型 String 问题编号
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getPhoneInfo(String id);
}
