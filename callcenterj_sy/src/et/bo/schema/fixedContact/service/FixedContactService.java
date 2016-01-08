/**
 * @(#)FixedContactService.java	 2008-06-10
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.schema.fixedContact.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>
 * �̶�����Ա����
 * </p>
 * @version 2008-06-10
 * @author ��Ĭ
 */

public interface FixedContactService
{
	/**
   * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�б����ݡ�
   * @param dto
   * ���ݴ������
   * @param pi
   * ҳ����Ϣ
   * @return ���ݵ�list
   */
	public List fixedContactQuery(IBaseDTO dto, PageInfo pi);

	/**
   * ��ѯ�����б�,����ȫ����¼��list�� ȡ�ò�ѯ�б����ݡ�
   * @param dto
   * ���ݴ������
   * @param pi
   * ҳ����Ϣ
   * @return ���ݵ�list
   */
	public List fixedContactAllQuery();

	/**
   * ��ѯ�����б�������� ȡ�ò�ѯ�б��������
   * @return �õ�list������
   */
	public int getFixedContactSize();

	/**
   * ���ݿͻ�ID��ѯ�����б�,���ظÿͻ��ġ����⡱��list�� ȡ�ò�ѯ�����б����ݡ�
   * @return �ط����ݵ�list
   */
	public List getQuestionList();

	/**
   * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
   * ȡ��ĳ�����ݵ���ϸ��Ϣ��
   * @param id
   * ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
   * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
   */
	public IBaseDTO getFixedContactInfo(String id);

	/**
   * ���ݵ绰����ȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
   * ���ݵ绰�����ѯ�û�լ�磬�칫�绰���ֻ���ȡ��ĳ�ͻ�����ϸ��Ϣ��
   * @param id
   * ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
   * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
   */
	public IBaseDTO getopenwinInfo(String tel);

	/**
   * �޸����ݡ� �޸�ĳ����¼�����ݡ�
   * @param dto
   * Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
   * @return boolean
   */
	public boolean updateFixedContact(IBaseDTO dto);

	/**
   * ɾ�����ݡ� ɾ��ĳ����¼��
   * @param id
   * Ҫɾ�����ݵı�ʶ
   */
	public void delFixedContact(String id);

	/**
   * ���ɾ����
   * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
   * @param id
   * Ҫ���ɾ�����ݵı�ʶ
   */
	public boolean isDelete(String id);

	/**
   * ������ݡ� �����ݿ������һ����¼��
   * @param dto
   * �����ݵ�excellence.framework.base.dto.IBaseDTO����
   */
	public void addFixedContact(IBaseDTO dto);

	/**
   * ȡ���¿ͻ���¼��ID
   * @return custID
   */
	public String getCustId();

	/**
   * �޸��ϴ���ͼƬ�ļ��������еı���·��.
   * @param id
   * the id
   * @param path
   * the path
   */
	public void savePicture(String path);
}
