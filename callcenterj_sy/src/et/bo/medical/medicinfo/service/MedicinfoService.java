/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.medical.medicinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 普通医疗信息
 * @author  荆玉琢
 * @version 1.0, 2008-04-7//
 * @see
 */
public interface MedicinfoService {
	/**
	 * @describe 根据专家类别获取专家名称列表
	 * @param expertType
	 * @return
	 */
	public List getExpertNameList(String expertType);
	/**
	 * @describe 添加普通医疗信息
	 * @param
	 * @return void
	 */ 
	public void addMedicinfo(IBaseDTO dto);
	/**
	 * @describe 修改普通医疗信息
	 * @param
	 * @return void
	 */ 
	public boolean updateMedicinfo(IBaseDTO dto);
	/**
	 * @describe 删除普通医疗信息
	 * @param
	 * @return void
	 */ 
	public void delMedicinfo(String id);
	
	
	/**
	 * @describe 查询普通医疗信息列表
	 * @param
	 * @return List
	 */ 
	public List medicinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getMedicinfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getMedicinfo(String id);
	
	public List getExpertList();

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
	/**
	 * 获得受理工号列表
	 */
	public List userQuery();
}
