/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.markanainfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 焦点追踪
 * @author  荆玉琢
 * @version 1.0, 2008-04-09//
 * @see
 */
public interface MarkanainfoService {
	/**
	 * @describe 添加市场分析
	 * @param
	 * @return void
	 */ 
	public void addMarkanainfo(IBaseDTO dto);
	/**
	 * @describe 修改市场分析
	 * @param
	 * @return void
	 */ 
	public boolean updateMarkanainfo(IBaseDTO dto);
	/**
	 * @describe 删除市场分析
	 * @param
	 * @return void
	 */ 
	public void delMarkanainfo(String id);
	
	
	/**
	 * @describe 查询市场分析列表
	 * @param
	 * @return List
	 */ 
	public List markanainfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getMarkanainfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getMarkanainfo(String id);

	/**
	 * 状态转换
	 * @param state
	 * @return
	 */
	public String changeState(String state);
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);

}
