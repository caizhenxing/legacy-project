/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sms.linkMan.service;

import java.sql.SQLException;
import java.util.List;


import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * ҵ��ģ��ӿ�
 * @author chengang
 */
public interface LinkManService {
	public int getLinkManSize();
	
	/**
	 * ��ѯҵ����Ϣ
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List linkManQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ɾ��ҵ����Ϣ
	 * @param selectIt
	 * @return
	 */
	public boolean delLinkMan(String[] selectIt);
	
	/**
	 * ����ɾ��ҵ����Ϣ
	 * @param selectIt
	 * @return
	 */
	public boolean delLinkManForever(String[] selectIt);
	
	/**
	 * ���ҵ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public IBaseDTO getLinkManInfo(String id);
	
	/**
	 * ���ҵ����Ϣ
	 * @param dto
	 * @return
	 */
	public boolean addLinkMan(IBaseDTO dto);
	
	/**
	 * �޸�ҵ����Ϣ
	 * @param dto
	 * @param id
	 * @return
	 */
	public boolean updateLinkMan(IBaseDTO dto, String id);
	
	/**
	 * ɾ��ҵ����Ϣ
	 * @param id
	 * @return
	 */
	public boolean delLinkMan(String id);
	
	public List getLinkGroupList();
}
