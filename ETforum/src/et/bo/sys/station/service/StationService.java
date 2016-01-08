/**
 * 	@(#)StationService.java   Sep 2, 2006 2:37:38 PM
 *	 �� 
 *	 
 */
package et.bo.sys.station.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.dto.IBaseDTO;

/**
 * ��λ��Ϣά��
 * @author zhang
 * @version Sep 2, 2006
 * @see
 */
public interface StationService {
	
	/**
	 * ��λ��Ϣ���
	 * @param dto ���� IBaseDTO ��λ��Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean addStationBox(IBaseDTO dto);
	
	/**
	 * ��λ��Ϣ�޸�
	 * @param dto ���� IBaseDTO ��λ��Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean updateStationBox(IBaseDTO dto);
	
	/**
	 * ��λ��Ϣɾ��
	 * @param dto ���� IBaseDTO ��λ��Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean delStationBox(IBaseDTO dto);
	
	/**
	 * ��ѯ��λ��Ϣ
	 * @param dto ���� IBaseDTO ��λ��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @param mailboxType ���� String ��������
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List StationIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getStationSize();
	
	/**
	 * ��ѯָ����λ��ϸ��Ϣ
	 * @param id ���� String ��λ���
	 * @version Sep 4, 2006
	 * @return
	 */
	public IBaseDTO getStationInfo(String id);
	
	/**
	 * ��ѯ��λ�����ϸ��Ϣ
	 * @param station_class ���� String ��λ���
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List StationList(String station_class);
	
	/**
	 * ���ز�����
	 * @param
	 * @version Sep 4, 2006
	 * @return
	 */
	public TreeControlI loadDepartments();
}
