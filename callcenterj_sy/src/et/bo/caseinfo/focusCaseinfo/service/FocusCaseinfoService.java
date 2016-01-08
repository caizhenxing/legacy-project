/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.caseinfo.focusCaseinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe ���㰸������Ϣ
 * @author ������
 * @version 1.0, 2008-04-0��//
 * @see
 */
public interface FocusCaseinfoService
{
	/**
   * @describe ��ӽ��㰸������Ϣ
   * @param
   * @return void
   */
	public void addFocusCaseinfo(IBaseDTO dto);
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
   * @describe �޸Ľ��㰸������Ϣ
   * @param
   * @return void
   */
	public boolean updateFocusCaseinfo(IBaseDTO dto);

	/**
   * @describe ɾ�����㰸������Ϣ
   * @param
   * @return void
   */
	public void delFocusCaseinfo(String id);

	/**
   * @describe ��ѯ���㰸������Ϣ�б�
   * @param
   * @return List
   */
	public List focusCaseinfoQuery(IBaseDTO dto, PageInfo pi);

	/**
   * @describe ȡ�ò�ѯ����
   * @param
   * @return int
   */

	public int getFocusCaseinfoSize();

	/**
   * @describe ����Idȡ����Ϣ
   * @param
   * @return dto(user����)
   */
	public IBaseDTO getFocusCaseinfo(String id);

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
