/*
 * @(#)QusetionService.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package et.bo.question.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>�������</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public interface QuestionService {
	/**
	 * ��������ID��ѯ�����б�,���ء��طá���list�� 
	 * 
	 * @return �ط����ݵ�list
	 */
	public List getCallbackList();
	/**
	 * ��ѯ���С�024007009���ķ����¼�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List questionSpecialQuery(String content);
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List questionQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getQuestionSize(); 
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getQuestionInfo(String id);
	/**
	 * �޸Ļط����ݡ� 
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto);
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateQuestion(IBaseDTO dto);
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delQuestion(String id);
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
	public void addQuestion(IBaseDTO dto);
	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * ���ҷ�������ID
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public String addQuestionAndGetId(IBaseDTO dto, String ring_begintime, String userId, String isout, String cust_tel) ;

	/**
	 * �õ�ר�����
	 */
	public List<LabelValueBean> getExpertsList();
}
