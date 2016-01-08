package et.bo.custinfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface PhoneinfoService
{
	/**
   * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�б����ݡ�
   * @param dto
   * ���ݴ������
   * @param pi
   * ҳ����Ϣ
   * @return ���ݵ�list
   */
	public List phoneinfoQuery(IBaseDTO dto, PageInfo pi);
	public List phoneinfoQuery2(IBaseDTO dto, PageInfo pi);
	
	/**
	 * �õ��绰�б����Ϣ
	 * @return
	 */
	public int phoneSize();

	/**
   * ��ѯ�����б�,����ȫ����¼��list�� ȡ�ò�ѯ�б����ݡ�
   * @param dto
   * ���ݴ������
   * @param pi
   * ҳ����Ϣ
   * @return ���ݵ�list
   */
	public List phoneinfoAllQuery();

	/**
   * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
   * ȡ��ĳ�����ݵ���ϸ��Ϣ��
   * @param id
   * ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
   * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
   */
	public IBaseDTO getPhoneinfo(String id);

	/**
   * �޸����ݡ� �޸�ĳ����¼�����ݡ�
   * @param dto
   * Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
   * @return boolean
   */
	public boolean updatePhoneinfo(IBaseDTO dto);
	public boolean updateLinkManinfo(IBaseDTO dto);

	/**
   * ɾ�����ݡ� ɾ��ĳ����¼��
   * @param id
   * Ҫɾ�����ݵı�ʶ
   */
	public void delPhoneinfo(String id);

	/**
   * ������ݡ� �����ݿ������һ����¼��
   * @param dto
   * �����ݵ�excellence.framework.base.dto.IBaseDTO����
   */
	public void addPhoneinfo(IBaseDTO dto);
	public void addLinkManinfo(IBaseDTO dto);
	
	/**
	 * ר�������Ϣ
	 * @param expertType
	 * @return
	 */
	public String getExpertList(String expertType);
	
	/**
	 * ����Ա�������
	 * @param excelPath
	 * @return
	 */
	public boolean addCountPhoneInfo(String excelPath);
	
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */
	public List userQuery(String sql);

}
