package et.bo.oa.assissant.conference.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface ConferenceService {

	public boolean insertC(IBaseDTO dto);
	
	public boolean updateC(IBaseDTO dto);
	
	public boolean deleteC(String id) ;
	
	public IBaseDTO loadC(String id);
	
	public List searchC(IBaseDTO dto ,PageInfo pi);
	
	public List searchEC(IBaseDTO dto ,PageInfo pi);
	
	public int searchCSize(IBaseDTO dto,PageInfo pi);
	
	public int searchECSize(IBaseDTO dto,PageInfo pi);
	/**
	 * <p>设置工作流id</p>
	 * @param id 会议id
	 * @param fid 工作流id
	 * @return
	 */
	public boolean setWF(String id ,String fid);
	/**
	 * 设置会议文档
	 * @param id
	 * @param documentId
	 * @return
	 */
	public boolean setDocument(String id, String documentId);
	
	public boolean examine(String id, IBaseDTO dto );
	
	public boolean endOfConference(String did , IBaseDTO dto);
}
