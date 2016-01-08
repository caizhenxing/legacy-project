/**
 * 沈阳卓越科技有限公司
 * 2008-4-7
 */
package et.bo.callcenter.appraise.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * @author chen gang
 *
 */
public interface AppraiseService {
	
	/**
	 * 查询评价列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List appQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 返回评价记录数目
	 * @return
	 */
	public int getAppSize();
	
	/**
	 * 获得受理工号列表
	 */
	public List userQuery();
}
