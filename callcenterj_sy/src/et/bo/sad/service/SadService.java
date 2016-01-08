/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sad.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 价格供求
 * @author  荆玉琢
 * @version 1.0, 2008-03-27//
 * @see
 */
public interface SadService {
	/**
	 * @describe 添加市场供求
	 * @param
	 * @return void
	 */ 
	public void addSad(IBaseDTO dto);
	/**
	 * @describe 修改市场供求
	 * @param
	 * @return void
	 */ 
	public boolean updateSad(IBaseDTO dto);
	/**
	 * @describe 删除市场供求
	 * @param
	 * @return void
	 */ 
	public void delSad(String id);
	
	
	/**
	 * @describe 查询市场供求列表
	 * @param
	 * @return List
	 */ 
	public List sadQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe 查询市场供求列表
	 * @param
	 * @return List
	 */ 
	public List sadInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getSadSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getSadInfo(String id);
	
    /**
     * @describe 修改照片
     * @param
     * @return
     */
    public void updatePhoto(String id,String path);
	
    /**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	/**
	 * 获得受理工号列表
	 */
	public List userQuery();
	/**
	 * 大屏幕数据列表
	 * @return
	 */
	public List screenList();
}
