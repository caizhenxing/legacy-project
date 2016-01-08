package et.bo.oa.assissant.asset.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface AssetService {

	public boolean insert(IBaseDTO dto);
	
	public boolean update(IBaseDTO dto);
	
	public boolean delete(String id);
	
	public List<IBaseDTO> list(IBaseDTO dto ,PageInfo pi);
	
	public int listSize(IBaseDTO dto ,PageInfo pi);
	
	public IBaseDTO load(String id);
	/**
	 * 
	 * @param deficiencyAsset
	 * @return
	 */
	public int deficiency(String deficiencyAsset);
	/**
	 * 
	 * @param AssetOperId
	 * @return
	 */
	public int howMany(String AssetOperId);
	/**
	 * 
	 * @param id
	 * @return
	 */
	
	public IBaseDTO getOperDtoByInfoId(String id);
	
	public boolean exist(String id);
}
