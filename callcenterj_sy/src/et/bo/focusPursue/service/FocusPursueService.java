/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.focusPursue.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 焦点追踪
 * @author  荆玉琢
 * @version 1.0, 2008-04-08//
 * @see
 */
public interface FocusPursueService {
	/**
	 * @describe 添加焦点追踪
	 * @param
	 * @return void
	 */ 
	public void addFocusPursue(IBaseDTO dto);
	/**
	 * @describe 修改焦点追踪
	 * @param
	 * @return void
	 */ 
	public boolean updateFocusPursue(IBaseDTO dto);
	/**
	 * @describe 删除焦点追踪
	 * @param
	 * @return void
	 */ 
	public void delFocusPursue(String id);
	
	
	/**
	 * @describe 查询焦点追踪列表
	 * @param
	 * @return List
	 */ 
	public List focusPursueQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getFocusPursueSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getFocusPursue(String id);
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	
}
