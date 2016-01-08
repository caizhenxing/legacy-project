/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.staff.staffBasic.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 用户操作测试
 * @author  荆玉琢
 * @version 1.0, 2007-04-05//
 * @see
 */
public interface staffBasicService {
	/**
	 * @describe 添加新网址
	 * @param
	 * @return void
	 */ 
	public void addStaffBasic(IBaseDTO dto);
	/**
	 * @describe 修改网址
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffBasic(IBaseDTO dto);
	/**
	 * @describe 删除网址
	 * @param
	 * @return void
	 */ 
	public void delStaffBasic(String id);
	
	
	/**
	 * @describe 查询网址列表
	 * @param
	 * @return List
	 */ 
	public List staffBasicQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getStaffBasicSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getStaffBasicInfo(String id);
	
	/**
	 * @describe 返回公司信息
	 * @param
	 * @return list
	 */ 
	public List getWorkUnitInfo();
	
	
	/**
	 * @describe 返回部门信息
	 * @param
	 * @return list
	 */ 
	public List getdepartmentList();
	
	
	/**
	 * @describe 返回职务信息
	 * @param
	 * @return list
	 */ 
	public List getDutyList();
}
