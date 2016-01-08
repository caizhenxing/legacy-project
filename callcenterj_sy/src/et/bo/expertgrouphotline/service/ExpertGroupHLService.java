package et.bo.expertgrouphotline.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface ExpertGroupHLService {

	/**
	 * 增加一条专家信息记录
	 * @param dto
	 */
	public void addExperGroupHLinfo(IBaseDTO dto);

	/**
	 * 删除一条专家信息
	 * @param id
	 */
	public void delExperGroupHLinfo(String id);

	/**
	 * 修改一条专家信息
	 * @param dto
	 * @return boolean
	 */
	public boolean updateExpertGroupHLinfo(IBaseDTO dto);

	/**
	 * 查询符合条件的专家信息
	 * @param dto
	 * @param pi
	 * @return list
	 */
	public List getExperGroupHLinfoList(IBaseDTO dto, PageInfo pi);
	/**
	 * 得到大屏幕下 热线专家团的列表
	 * @param type 农民 政府
	 * @return List<DynaBeanDTO>
	 */
	public List<DynaBeanDTO> getScreenExpertList(String type);
	/**
	 * 
	 * @return
	 */
	public int getRecordSize();

	/**
	 * 根据id查找特定专家信息
	 * @param id
	 * @return IBaseDTO
	 */
	public IBaseDTO getExpertHotLineById(String id);
	
	public List screenList();

}