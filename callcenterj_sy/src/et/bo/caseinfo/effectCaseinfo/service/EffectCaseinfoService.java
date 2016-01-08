/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.caseinfo.effectCaseinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe Ч����������
 * @author ������
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface EffectCaseinfoService
{
	/**
   * @describe ���Ч����������
   * @param
   * @return void
   */
	public void addEffectCaseinfo(IBaseDTO dto);
	/**
	 * ���ר���б�
	 * @param sql
	 * @return List
	 */
	public List exportQuery(String sql);
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);
	/**
   * @describe �޸�Ч����������
   * @param
   * @return void
   */
	public boolean updateEffectCaseinfo(IBaseDTO dto);

	/**
   * @describe ɾ��Ч����������
   * @param
   * @return void
   */
	public void delEffectCaseinfo(String id);

	/**
   * @describe ��ѯЧ�����������б�
   * @param
   * @return List
   */
	public List effectCaseinfoQuery(IBaseDTO dto, PageInfo pi);

	/**
   * @describe ȡ�ò�ѯ����
   * @param
   * @return int
   */

	public int getEffectCaseinfoSize();

	/**
   * @describe ����Idȡ����Ϣ
   * @param
   * @return dto(user����)
   */
	public IBaseDTO getEffectCaseinfo(String id);

	/**
   * @describe �޸���Ƭ
   * @param
   * @return
   */
	public void updatePhoto(String id, String path);

	/**
	 * �޸���Ƶ.
	 * 
	 * @param id the id
	 * @param path the path
	 */
	public void updateVideo(String id, String path);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);

}
