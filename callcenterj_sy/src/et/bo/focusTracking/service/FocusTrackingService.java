/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.focusTracking.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 焦点追踪
 * @author  
 * @version 1.0, 2009-03-12//
 * @see
 */
public interface FocusTrackingService {
	/**
	 * @describe 添加焦点追踪
	 * @param
	 * @return void
	 */ 
	public void addFocusTracking(IBaseDTO dto);
	/**
	 * @describe 修改焦点追踪
	 * @param
	 * @return void
	 */ 
	public void updateFocusTracking(IBaseDTO dto);
	/**
	 * @describe 删除焦点追踪
	 * @param
	 * @return void
	 */ 
	public void delFocusTracking(String id);
	
	
	/**
	 * @describe 查询焦点追踪列表
	 * @param
	 * @return List
	 */ 
	public List focusTrackingQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getFocusTrackingSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getFocusTracking(String id);
	
	public List screenList() ;
}
