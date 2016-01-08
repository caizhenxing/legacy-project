package et.bo.screen.service;

import java.util.ArrayList;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface ScreenDoctorService {
	
	/**
	 * ����idȡ��һ����ũ�г�������Ϣ��ϸ
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo(String id);
	
	/**
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б�
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	public int getMarAnalysisInfoSize();
	
	/**
	 * ��ӽ�ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis(IBaseDTO dto);
	
	/**
	 * �޸Ľ�ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis(IBaseDTO dto);
	
	/**
	 * ɾ����ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis(IBaseDTO dto);

	public List<DynaBeanDTO> getHealthcareList(int size);
	public List<DynaBeanDTO> getPreventionList(int size);
	
	public List<DynaBeanDTO> getScreenList(int size);
}
