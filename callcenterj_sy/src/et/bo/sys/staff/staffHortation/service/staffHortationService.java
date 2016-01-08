/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.staff.staffHortation.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 职工奖罚
 * @author  荆玉琢
 * @version 1.0, 2008-01-30//
 * @see
 */
public interface staffHortationService {
	/**
	 * @describe 添加职工奖罚
	 * @param
	 * @return void
	 */ 
	public void addStaffHortation(IBaseDTO dto,String id);
	/**
	 * @describe 修改职工奖罚
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffHortation(IBaseDTO dto);
	/**
	 * @describe 删除职工奖罚
	 * @param
	 * @return void
	 */ 
	public void delStaffHortation(String id);
	
	
	/**
	 * @describe 查询职工奖罚列表
	 * @param
	 * @return List
	 */ 
	public List StaffHortationQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getStaffHortationSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffHortationInfo(String id);
	

}
