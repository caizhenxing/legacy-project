package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface MarAnalysisService {
	
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
	/**
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б� ��ʱ�䵹�� ��ʾͷ����
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(int size);
	public List marketAnalysisInfoQuery2();
	
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
	
	/**
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б�
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery2(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ����idȡ��һ����ũ�г�������Ϣ��ϸ
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo2(String id);
	
	/**
	 * ��ӽ�ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis2(IBaseDTO dto);
	
	/**
	 * �޸Ľ�ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis2(IBaseDTO dto);
	
	/**
	 * ɾ����ũ�г�������Ϣ
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis2(IBaseDTO dto);
}
