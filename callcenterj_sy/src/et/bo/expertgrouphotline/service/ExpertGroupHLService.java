package et.bo.expertgrouphotline.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface ExpertGroupHLService {

	/**
	 * ����һ��ר����Ϣ��¼
	 * @param dto
	 */
	public void addExperGroupHLinfo(IBaseDTO dto);

	/**
	 * ɾ��һ��ר����Ϣ
	 * @param id
	 */
	public void delExperGroupHLinfo(String id);

	/**
	 * �޸�һ��ר����Ϣ
	 * @param dto
	 * @return boolean
	 */
	public boolean updateExpertGroupHLinfo(IBaseDTO dto);

	/**
	 * ��ѯ����������ר����Ϣ
	 * @param dto
	 * @param pi
	 * @return list
	 */
	public List getExperGroupHLinfoList(IBaseDTO dto, PageInfo pi);
	/**
	 * �õ�����Ļ�� ����ר���ŵ��б�
	 * @param type ũ�� ����
	 * @return List<DynaBeanDTO>
	 */
	public List<DynaBeanDTO> getScreenExpertList(String type);
	/**
	 * 
	 * @return
	 */
	public int getRecordSize();

	/**
	 * ����id�����ض�ר����Ϣ
	 * @param id
	 * @return IBaseDTO
	 */
	public IBaseDTO getExpertHotLineById(String id);
	
	public List screenList();

}