/**
 * 
 */
package et.bo.callcenter.cclog.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public interface TelQueryService {
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */	
	public List userQuery(String sql);
	/**
   * @describe 查询电话列表
   * @param
   * @return List
   */
	public List telQuery(IBaseDTO dto, PageInfo pi);
	
	public int getSize();
	
	public List getTel(IBaseDTO dto, PageInfo pi,String pageState);
	/**
	 * 创建电话量统计图的xml
	 *
	 */
	public void createXml();
}
