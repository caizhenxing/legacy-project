/**
 * 	@(#)SearchImpl.java   Oct 30, 2006 5:54:17 PM
 *	 。 
 *	 
 */
package et.bo.pcc.phonesearch.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 30, 2006
 * @see
 */
public interface SearchService {
	
	 /**
	 * @describe 添加查询信息条件
	 * @param
	 * @return void
	 */
	public void addSearchInfo(IBaseDTO dto);
	
	/**
	 * 修改信息
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void upSearchInfo(IBaseDTO dto);
	
	/**
	 * 删除信息
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void delSearchInfo(IBaseDTO dto);
	
	/**
	 * @describe 查询呼叫中心日志
	 * @param
	 * @return List  Cclog类型
	 */
	public List phoneSearch(IBaseDTO dto, PageInfo pi);
    /**
	 * @describe 得到日志条数
	 * @param
	 * @return int
	 * 
	 */    
    public int getPhoneSearchSize();
    /**
	 * @describe 得到日志详细信息
	 * @param
	 * @return IBaseDTO 日志dto
	 */
    public IBaseDTO getPhoneSearch(String id);
    
}
