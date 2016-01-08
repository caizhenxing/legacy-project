/**
 * 	@(#) InqueryService.java 2008-4-1 下午01:13:09
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 执行调查主题维护的数据库操作
 * 
 * 实现类et.bo.inquiry.service.impl.InquiryServiceImpl
 * 
 * @author 梁云锋
 */
public interface InquiryService {
	/**
	 * 查询调查主题列表
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 符合查询条件的调查主题list
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto, PageInfo pi);

	/**
	 * 添加调查主题
	 * 
	 * @param dto
	 *            调查主题信息
	 */
	public void add(DynaActionFormDTO dto);

	/**
	 * 修改调查主题信息
	 * 
	 * @param dto
	 *            调查主题信息
	 */
	public void update(DynaActionFormDTO dto);

	/**
	 * 删除调查主题信息
	 * 
	 * @param dto
	 *            调查主题信息
	 */
	public void delete(DynaActionFormDTO dto);

	/**
	 * 查询当前有效的调查主题 即当前时间在调查主题的有效期内
	 * 
	 * @return 查询列表
	 */
	public List<DynaBeanDTO> filter();

	/**
	 * 获得当前数据列表的大小
	 * 
	 * @return 当前数据列表大小
	 */
	public int getInquirySize();

	/**
	 * 根据给定的ID获取调查主题的信息
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfo(String id);

	/**
	 * 根据主题ID获取该主题的调查卡信息列表
	 * 
	 * @param id 主题ID
	 * @return 调查卡信息列表
	 */
	public List<DynaBeanDTO> getCardInfo(String id);
	
	/**
	 * 根据主题ID获取该主题的调查卡结果的信息列表
	 * 
	 * @param id 主题ID
	 * @return 调查卡结果的信息列表
	 */
	public List<DynaBeanDTO> getCardInfoReport(String id);
	/**
	 * 根据给定的ID获取调查报告的信息
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfoReport(String id);
	
	/**
	 * 根据给定的ID获取调查报告的信息
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfoReportLast();
	
	/**
	 * 修改调查报告的信息
	 * 
	 * @param dto 调查报告的信息
	 */
	public void updateReport(DynaActionFormDTO dto);
	
	/**
	 * 删除报告的信息
	 * 
	 * @param dto 调查报告的信息
	 */
	public void deleteReport(DynaActionFormDTO dto);
	
	
	/**
	 * 根据调查报告ID获得问题信息ID
	 * 
	 * @param id 调查报告的ID
	 */
	public String getInquiryId(String id);
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
}
