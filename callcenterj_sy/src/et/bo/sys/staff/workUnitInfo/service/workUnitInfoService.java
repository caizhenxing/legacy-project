/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.staff.workUnitInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 公司信息操作
 * @author  荆玉琢
 * @version 1.0, 2008-01-29//
 * @see
 */
public interface workUnitInfoService {
	/**
	 * @describe 添加公司信息
	 * @param
	 * @return void
	 */ 
	public void addWorkUnitInfo(IBaseDTO dto);
	/**
	 * @describe 修改公司信息
	 * @param
	 * @return void
	 */ 
	public boolean updateWorkUnitInfo(IBaseDTO dto);
	/**
	 * @describe 删除公司信息
	 * @param
	 * @return void
	 */ 
	public void delWorkUnitInfo(String id);
	
	
	/**
	 * @describe 查询公司信息列表
	 * @param
	 * @return List
	 */ 
	public List workUnitInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getWorkUnitInfoSize();
	
	
	/**
	 * @describe 根据Id取得公司信息
	 * @param
	 * @return dto(类型)
	 */ 
	public IBaseDTO getWorkUnitInfoInfo(String id);
	
	
}
