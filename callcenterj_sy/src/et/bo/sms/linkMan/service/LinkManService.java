/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sms.linkMan.service;

import java.sql.SQLException;
import java.util.List;


import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 业务模块接口
 * @author chengang
 */
public interface LinkManService {
	public int getLinkManSize();
	
	/**
	 * 查询业务信息
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List linkManQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 删除业务信息
	 * @param selectIt
	 * @return
	 */
	public boolean delLinkMan(String[] selectIt);
	
	/**
	 * 永久删除业务信息
	 * @param selectIt
	 * @return
	 */
	public boolean delLinkManForever(String[] selectIt);
	
	/**
	 * 获得业务详细信息
	 * @param id
	 * @return
	 */
	public IBaseDTO getLinkManInfo(String id);
	
	/**
	 * 添加业务信息
	 * @param dto
	 * @return
	 */
	public boolean addLinkMan(IBaseDTO dto);
	
	/**
	 * 修改业务信息
	 * @param dto
	 * @param id
	 * @return
	 */
	public boolean updateLinkMan(IBaseDTO dto, String id);
	
	/**
	 * 删除业务信息
	 * @param id
	 * @return
	 */
	public boolean delLinkMan(String id);
	
	public List getLinkGroupList();
}
