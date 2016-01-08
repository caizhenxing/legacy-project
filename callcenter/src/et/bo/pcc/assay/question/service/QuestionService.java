/**
 * 	@(#)QuestionService.java   Oct 11, 2006 1:21:27 PM
 *	 。 
 *	 
 */
package et.bo.pcc.assay.question.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public interface QuestionService {
	
	/**
	 * 查询座席监控信息
	 * @param dto 类型 IBaseDTO 座席信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List questionIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getQuestionSize();
	
	/**
	 * 得到问题信息
	 * @param id 类型 String 问题编号
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getQuestionInfo(String id);

}
