/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.caseinfo.generalCaseinfo.service;

import java.util.List;

import et.po.OperCaseinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 案例库信
 * @author 荆玉琢
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface GeneralCaseinfoService
{
	/**
   * @describe 添加普通案例库信
   * @param
   * @return void
   */
	public void addGeneralCaseinfo(IBaseDTO dto);
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
   * @describe 修改普通案例库信
   * @param
   * @return void
   */
	public boolean updateGeneralCaseinfo(IBaseDTO dto);

	/**
   * @describe 删除普通案例库信
   * @param
   * @return void
   */
	public void delGeneralCaseinfo(String id);

	/**
   * @describe 查询普通案例库信列表
   * @param
   * @return List
   */
	public List generalCaseinfoQuery(IBaseDTO dto, PageInfo pi);
	/**
   * @describe 查询普通案例库信列表for screen state='已审'
   * @param
   * @return List
   */
	public List generalCaseinfoQuery(int size,String state);

	/**
   * @describe 取得查询条数
   * @param
   * @return int
   */

	public int getGeneralCaseinfoSize();

	/**
   * @describe 根据Id取得信息
   * @param
   * @return dto(user类型)
   */
	public IBaseDTO getGeneralCaseinfo(String id);

	/**
   * @describe 修改照片
   * @param
   * @return
   */
	public void updatePhoto(String id, String path);

	/**
   * @describe 修改视频
   * @param
   * @return
   */
	public void updateVideo(String id, String path);
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	
	/**
	 * 得到大屏幕信息的列表
	 * @return
	 */
	public List screenList();
	
}
