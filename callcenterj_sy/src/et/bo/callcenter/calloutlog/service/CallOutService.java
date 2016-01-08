/**
 * 
 */
package et.bo.callcenter.calloutlog.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public interface CallOutService {
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
	 * 查询外呼日志详细
	 * @param calloutlogId
	 * @return
	 */
	public IBaseDTO getInfo(String calloutlogId);
}
