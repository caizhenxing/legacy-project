/* 包    名：et.bo.eventResult.service
 * 文 件 名：EventResultService.java
 * 注释时间：2008-7-22 13:17:58
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.eventResult.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * The Interface EventResultService.
 * 
 * @author NieYuan
 */
public interface EventResultService {

	/**
	 * Adds the event result.
	 * 
	 * @param dto the dto
	 */
	public void addEventResult(IBaseDTO dto);

	/**
	 * Del event result.
	 * 
	 * @param id the id
	 */
	public void delEventResult(String id);

	/**
	 * Update event result.
	 * 
	 * @param dto the dto
	 * 
	 * @return true, if successful
	 */
	public boolean updateEventResult(IBaseDTO dto);

	/**
	 * Event result query.
	 * 
	 * @param dto the dto
	 * @param pi the pi
	 * 
	 * @return the list
	 */
	public List eventResultQuery(IBaseDTO dto, PageInfo pi);

	/**
	 * Gets the event result size.
	 * 
	 * @return the int
	 */
	public int getEventResultSize();

	/**
	 * Gets the event result info.
	 * 
	 * @param id the id
	 * 
	 * @return the i base dto
	 */
	public IBaseDTO getEventResultInfo(String id);
	
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);
	
	/**
	 * 获得联络员列表
	 * @return
	 */
	public List<LabelValueBean> getUserList();

}
