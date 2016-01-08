/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.staff.staffParentInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 职工亲属操作
 * @author  荆玉琢
 * @version 1.0, 2008-01-31//
 * @see
 */
public interface staffParentInfoService {
	/**
	 * @describe 添加职工亲属
	 * @param
	 * @return void
	 */ 
	public void addStaffParentInfo(IBaseDTO dto,String id);
	/**
	 * @describe 修改职工亲属
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffParentInfo(IBaseDTO dto);
	/**
	 * @describe 删除职工亲属
	 * @param
	 * @return void
	 */ 
	public void delStaffParentInfo(String id);
	
	
	/**
	 * @describe 查询职工亲属列表
	 * @param
	 * @return List
	 */ 
	public List staffParentInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getStaffParentInfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffParentInfoInfo(String id);
	

}
