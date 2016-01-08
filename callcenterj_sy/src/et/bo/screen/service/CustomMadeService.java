package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface CustomMadeService {
	
	/**
	 * ����idȡ��һ�����Ʒ�����Ϣ��ϸ
	 * @param id
	 * @return
	 */
	public IBaseDTO getCustomMadeInfo(String id);
	
	/**
	 * ���ݲ�ѯ�������ض��Ʒ�����Ϣ�б�
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List customMadeInfoQuery(IBaseDTO dto, PageInfo pi);
	public List customMadeInfoQuery2();
	
	public int getCustomMadeInfoSize();
	
	/**
	 * ��Ӷ��Ʒ�����Ϣ
	 * @param dto
	 * @return
	 */
	public boolean addCustomMade(IBaseDTO dto);
	
	/**
	 * �޸Ķ��Ʒ�����Ϣ
	 * @param dto
	 * @return
	 */
	public boolean updateCustomMade(IBaseDTO dto);
	
	/**
	 * ɾ�����Ʒ�����Ϣ
	 * @param dto
	 * @return
	 */
	public boolean deleteCustomMade(IBaseDTO dto);
}
