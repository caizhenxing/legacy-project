/*
 * @(#)CustinfoService.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package et.bo.custinfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>�ͻ�����</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public interface CustinfoService {
	
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */	
	public List userQuery(String sql);
	/**
	 * ��ѯ�����б�,���ؼ�¼(ר)��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoExpertAllQuery();
	
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * ��ѯ�����б�,����ȫ����¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoAllQuery();
	/**
	 * ��ѯ�����б��������
	 * ȡ�ò�ѯ�б��������
	 * @return �õ�list������
	 */
	public int getCustinfoSize(); 
	/**
	 * ���ݿͻ�ID��ѯ�����б�,���ظÿͻ��ġ����⡱��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @return �ط����ݵ�list
	 */
	public List getQuestionList();
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getCustinfoInfo(String id);
	/**
	 * ���ݵ绰����ȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ���ݵ绰�����ѯ�û�լ�磬�칫�绰���ֻ���ȡ��ĳ�ͻ�����ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getopenwinInfo(String tel);
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCustinfo(IBaseDTO dto);
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delCustinfo(String id);
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id);
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addCustinfo(IBaseDTO dto);
	/**
	 * ȡ���¿ͻ���¼��ID
	 * @return custID
	 */
	public String getCustId();
	
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List phoneQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ��ѯ�����б��������
	 * ȡ�ò�ѯ�б��������
	 * @return �õ�list������
	 */
	public int getPhoneSize(); 


}
