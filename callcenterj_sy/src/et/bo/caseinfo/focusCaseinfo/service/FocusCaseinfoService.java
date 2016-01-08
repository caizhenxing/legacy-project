/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.caseinfo.focusCaseinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 焦点案例库信息
 * @author 荆玉琢
 * @version 1.0, 2008-04-0２//
 * @see
 */
public interface FocusCaseinfoService
{
	/**
   * @describe 添加焦点案例库信息
   * @param
   * @return void
   */
	public void addFocusCaseinfo(IBaseDTO dto);
	/**
	 * 获得专家列表
	 * @param sql
	 * @return List
	 */
	public List exportQuery(String sql);
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);
	/**
   * @describe 修改焦点案例库信息
   * @param
   * @return void
   */
	public boolean updateFocusCaseinfo(IBaseDTO dto);

	/**
   * @describe 删除焦点案例库信息
   * @param
   * @return void
   */
	public void delFocusCaseinfo(String id);

	/**
   * @describe 查询焦点案例库信息列表
   * @param
   * @return List
   */
	public List focusCaseinfoQuery(IBaseDTO dto, PageInfo pi);

	/**
   * @describe 取得查询条数
   * @param
   * @return int
   */

	public int getFocusCaseinfoSize();

	/**
   * @describe 根据Id取得信息
   * @param
   * @return dto(user类型)
   */
	public IBaseDTO getFocusCaseinfo(String id);

	/**
   * @describe 修改照片
   * @param
   * @return
   */
	public void updatePhoto(String id, String path);

	/**
	 * 修改视频.
	 * 
	 * @param id the id
	 * @param path the path
	 */
	public void updateVideo(String id, String path);

	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	
}
