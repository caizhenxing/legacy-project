/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.staff.staffLanguage.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 职工语言操作
 * @author  荆玉琢
 * @version 1.0, 2008-01-30//
 * @see
 */
public interface staffLanguageService {
	/**
	 * @describe 添加职工语言
	 * @param
	 * @return void
	 */ 
	public void addStaffLanguage(IBaseDTO dto,String id);
	/**
	 * @describe 修改职工语言
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffLanguage(IBaseDTO dto);
	/**
	 * @describe 删除职工语言
	 * @param
	 * @return void
	 */ 
	public void delStaffLanguage(String id);
	
	
	/**
	 * @describe 查询职工语言列表
	 * @param
	 * @return List
	 */ 
	public List staffLanguageQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getStaffLanguageSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffLanguageInfo(String id);
	

}
