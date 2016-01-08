/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.caseinfo.hzinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe ���ﰸ������
 * @author ������
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface HZService
{
	/**
   * @describe ��ӻ��ﰸ������
   * @param
   * @return void
   */
	public void addHZinfo(IBaseDTO dto);
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
   * @describe �޸Ļ��ﰸ������
   * @param
   * @return void
   */
	public boolean updateHZinfo(IBaseDTO dto);

	/**
   * @describe ɾ�����ﰸ������
   * @param
   * @return void
   */
	public void delHZinfo(String id);

	/**
   * @describe ��ѯ���ﰸ�������б�
   * @param
   * @return List
   */
	public List hzinfoQuery(IBaseDTO dto, PageInfo pi);

	/**
   * @describe ȡ�ò�ѯ����
   * @param
   * @return int
   */

	public int getHZinfoSize();

	/**
   * @describe ����Idȡ����Ϣ
   * @param
   * @return dto(user����)
   */
	public IBaseDTO getHZinfo(String id);

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
	
}
