/*
 * @(#)QusetionService.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */


package et.bo.question.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>问题管理</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public interface QuestionService {
	/**
	 * 根据问题ID查询数据列表,返回“回访”的list。 
	 * 
	 * @return 回访数据的list
	 */
	public List getCallbackList();
	/**
	 * 查询含有“024007009”的服务记录数据列表,返回记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List questionSpecialQuery(String content);
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List questionQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getQuestionSize(); 
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getQuestionInfo(String id);
	/**
	 * 修改回访数据。 
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto);
	/**
	 * 修改数据。
	 * 修改某条记录的内容。
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateQuestion(IBaseDTO dto);
	/**
	 * 删除数据。
	 * 删除某条记录。
	 * @param id 要删除数据的标识
	 */
	public void delQuestion(String id);
	/**
	 * 标记删除。
	 * 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
	 * @param id 要标记删除数据的标识
	 */
	public boolean isDelete(String id);
	/**
	 * 添加数据。
	 * 向数据库中添加一条记录。
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addQuestion(IBaseDTO dto);
	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 并且返回主键ID
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public String addQuestionAndGetId(IBaseDTO dto, String ring_begintime, String userId, String isout, String cust_tel) ;

	/**
	 * 得到专家类表
	 */
	public List<LabelValueBean> getExpertsList();
}
