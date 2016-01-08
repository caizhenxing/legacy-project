/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.linkman.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 企业库信
 * @author  荆玉琢
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface LinkmanService {
	/**
	 * @describe 添加联络员
	 * @param
	 * @return void
	 */ 
	public void addOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe 修改联络员
	 * @param
	 * @return void
	 */ 
	public boolean updateOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe 删除联络员
	 * @param
	 * @return void
	 */ 
	public void delOperCorpinfo(String id);
	
	
	/**
	 * @describe 查询联络员列表
	 * @param
	 * @return List
	 */ 
	public List linkmanQuery(IBaseDTO dto, PageInfo pi,int pageState);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getLinkmanSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getOperCorpinfo(String id);
	
}
