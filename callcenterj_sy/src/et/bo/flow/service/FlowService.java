/*
 * @(#)FlowService.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package et.bo.flow.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;


/**
 * The Interface FlowService.
 * 
 * @author QiYue
 */
public interface FlowService {
	
	/**
	 * ���ݴ����ж���˱����Ƿ���ڸ���˼�¼��
	 * ���ݴ����ѡ��״̬���жϲ������Ρ�
	 * ��������򷵻ص�ǰ��¼״̬����������ڣ��������˼�¼��.
	 * 
	 * @param type_id ������ʶ
	 * @param id ����ʶ���¼���ֶ���
	 * @param state ����ѡ��״̬
	 * @param subid �ύ��ID
	 * @param subToid �ύ��˭���д���
	 * @return the string
	 */
//	public void addOrUpdateFlow(String type_id, String id, String state, String subid);
	public void addOrUpdateFlow(String type_id, String id, String nowstate, String subid,String autding);
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��.
	 * 
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addFlow(IBaseDTO dto);

	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��.
	 * 
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delFlow(String id);
	
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����.
	 * 
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 * 
	 * @return true, if checks if is delete
	 */
	public boolean isDelete(String id); 
	
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�.
	 * 
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * 
	 * @return boolean
	 */
	public boolean updateFlow(IBaseDTO dto);
	
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�б����ݡ�.
	 * 
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * 
	 * @return ���ݵ�list
	 */
	public List flowQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ��ѯ�����б��������
	 * ȡ�ò�ѯ�б��������.
	 * 
	 * @return �õ�list������
	 */
	public int getFlowSize();
	
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * 
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getFlowInfo(String id);
	
	/**
	 * Gets the user list.
	 * 
	 * @return the list< label value bean>
	 */
	public List<LabelValueBean> getUserList();
	
	/**
	 * zhangfeng add
	 * �жϼ�¼�Ƿ��Ѿ�����
	 * @return 
	 */
	String isRead(String operId);
	
	/**
	 * zhang feng add
	 * ��¼�Ƿ��Ѿ�����������˵�Ǽ�¼�Ƿ��Ѿ��޸Ĺ�
	 * @param operId
	 */
	void modifyRead(String operId);
}
