/**
 * 	InquiryCardService.java   2008-4-2 下午02:56:11
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service;

import excellence.framework.base.dto.IBaseDTO;

/**
 * 执行调查主题所属问题的查询、修改、删除操作
 * 实现类et.bo.service.impl.InquiryCardServiceImpl
 * @author 梁云锋
 *
 */
public interface InquiryCardService {
	/**
	 * 根据问题ID取得问题信息
	 * @param id 问题ID
	 * @return
	 */
	public IBaseDTO getInquiryCardInfo(String id);
	/**
	 * 修改一个问题信息
	 * @param dto 需要修改的问题信息对象
	 */
	public void update(IBaseDTO dto);
	/**
	 * 删除一个问题信息
	 * @param dto 要删除的问题信息对象
	 */
	public void delete(IBaseDTO dto);
}
