package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface CustomMadeService {
	
	/**
	 * 根据id取得一条定制服务信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getCustomMadeInfo(String id);
	
	/**
	 * 根据查询条件返回定制服务信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List customMadeInfoQuery(IBaseDTO dto, PageInfo pi);
	public List customMadeInfoQuery2();
	
	public int getCustomMadeInfoSize();
	
	/**
	 * 添加定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean addCustomMade(IBaseDTO dto);
	
	/**
	 * 修改定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean updateCustomMade(IBaseDTO dto);
	
	/**
	 * 删除定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean deleteCustomMade(IBaseDTO dto);
}
