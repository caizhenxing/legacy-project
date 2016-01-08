/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.caseinfo.generalCaseinfo.service;

import java.util.List;

import et.po.OperCaseinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe ��������
 * @author ������
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface GeneralCaseinfoService
{
	/**
   * @describe �����ͨ��������
   * @param
   * @return void
   */
	public void addGeneralCaseinfo(IBaseDTO dto);
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
   * @describe �޸���ͨ��������
   * @param
   * @return void
   */
	public boolean updateGeneralCaseinfo(IBaseDTO dto);

	/**
   * @describe ɾ����ͨ��������
   * @param
   * @return void
   */
	public void delGeneralCaseinfo(String id);

	/**
   * @describe ��ѯ��ͨ���������б�
   * @param
   * @return List
   */
	public List generalCaseinfoQuery(IBaseDTO dto, PageInfo pi);
	/**
   * @describe ��ѯ��ͨ���������б�for screen state='����'
   * @param
   * @return List
   */
	public List generalCaseinfoQuery(int size,String state);

	/**
   * @describe ȡ�ò�ѯ����
   * @param
   * @return int
   */

	public int getGeneralCaseinfoSize();

	/**
   * @describe ����Idȡ����Ϣ
   * @param
   * @return dto(user����)
   */
	public IBaseDTO getGeneralCaseinfo(String id);

	/**
   * @describe �޸���Ƭ
   * @param
   * @return
   */
	public void updatePhoto(String id, String path);

	/**
   * @describe �޸���Ƶ
   * @param
   * @return
   */
	public void updateVideo(String id, String path);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	
	/**
	 * �õ�����Ļ��Ϣ���б�
	 * @return
	 */
	public List screenList();
	
}
