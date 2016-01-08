/* ��    ����et.bo.event.service
 * �� �� ����EventService.java
 * ע��ʱ�䣺2008-7-21 13:50:33
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.event.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * The Interface EventService.
 * 
 * @author NieYuan
 */
public interface EventService {
	

	/**
	 * Adds the event.
	 * 
	 * @param dto the dto
	 */
	public void addEvent(IBaseDTO dto);


	/**
	 * Del event.
	 * 
	 * @param id the id
	 */
	public void delEvent(String id);
	

	/**
	 * Update event.
	 * 
	 * @param dto the dto
	 * 
	 * @return true, if successful
	 */
	public boolean updateEvent(IBaseDTO dto);
	

	/**
	 * Event query.
	 * 
	 * @param dto the dto
	 * @param pi the pi
	 * 
	 * @return the list
	 */
	public List eventQuery(IBaseDTO dto, PageInfo pi);

	/**
	 * Gets the event size.
	 * 
	 * @return the int
	 */
	public int getEventSize();

	/**
	 * Gets the event info.
	 * 
	 * @param id the id
	 * 
	 * @return the i base dto
	 */
	public IBaseDTO getEventInfo(String id);
	
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);

}
