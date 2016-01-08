/*
 * @(#)FlowService.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */


package et.bo.flow.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;


/**
 * The Interface FlowService.
 * 
 * @author QiYue
 */
public interface FlowService {
	
	/**
	 * 根据传参判断审核表里是否存在该审核记录。
	 * 根据传入的选择状态，判断操作情形。
	 * 如果存在则返回当前记录状态，如果不存在，则添加审核记录。.
	 * 
	 * @param type_id 表名标识
	 * @param id 用于识别记录的字段名
	 * @param state 传入选择状态
	 * @param subid 提交人ID
	 * @param subToid 提交给谁进行处理
	 * @return the string
	 */
//	public void addOrUpdateFlow(String type_id, String id, String state, String subid);
	public void addOrUpdateFlow(String type_id, String id, String nowstate, String subid,String autding);
	/**
	 * 添加数据。
	 * 向数据库中添加一条记录。.
	 * 
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addFlow(IBaseDTO dto);

	/**
	 * 删除数据。
	 * 删除某条记录。.
	 * 
	 * @param id 要删除数据的标识
	 */
	public void delFlow(String id);
	
	/**
	 * 标记删除。
	 * 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。.
	 * 
	 * @param id 要标记删除数据的标识
	 * 
	 * @return true, if checks if is delete
	 */
	public boolean isDelete(String id); 
	
	/**
	 * 修改数据。
	 * 修改某条记录的内容。.
	 * 
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * 
	 * @return boolean
	 */
	public boolean updateFlow(IBaseDTO dto);
	
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询列表数据。.
	 * 
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * 
	 * @return 数据的list
	 */
	public List flowQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 查询数据列表的条数。
	 * 取得查询列表的条数。.
	 * 
	 * @return 得到list的条数
	 */
	public int getFlowSize();
	
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * 
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * 
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getFlowInfo(String id);
	
	/**
	 * Gets the user list.
	 * 
	 * @return the list< label value bean>
	 */
	public List<LabelValueBean> getUserList();
	
	/**
	 * zhangfeng add
	 * 判断记录是否已经读过
	 * @return 
	 */
	String isRead(String operId);
	
	/**
	 * zhang feng add
	 * 记录是否已经读过，或者说是记录是否已经修改过
	 * @param operId
	 */
	void modifyRead(String operId);
}
