/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.asset.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface AssetOperService {

public boolean insert(IBaseDTO dto);
	
	public boolean update(IBaseDTO dto);
	
	public boolean delete(String id);
	
	public List<IBaseDTO> list(IBaseDTO dto ,PageInfo pi);
	
	public int listSize(IBaseDTO dto ,PageInfo pi);
	
	public IBaseDTO load(String id);
	
	public boolean existOperCode(String operCode);
	
	public List listLVBatch();
}
