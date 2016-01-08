/**
 * @(#)CallinFirewallService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.pcc.callinFirewall.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe �������ǽ�ӿ�
 * @author  Ҷ����
 * @version 1.0, 2006-10-08//
 * @see
 */
public interface CallinFirewallService {
    /**
	 * @describe ���ӹ���
	 * @param
	 * @return void
	 */
	public void addRule(IBaseDTO dto);
	/**
	 * @describe �޸Ĺ���
	 * @param
	 * @return void
	 */
	public void updateRule(IBaseDTO dto);
	/**
	 * @describe ɾ������
	 * @param
	 * @return void
	 */
	public void delRule(IBaseDTO dto);
	/**
	 * @describe ��������Idȡ�ù�����Ϣ
	 * @param
	 * @return DynaBeanDTO
	 */
	public IBaseDTO getRuleInfo(String id);
	/**
	 * @describe ��ѯ����
	 * @param
	 * @return List
	 */
	public List ruleQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe �õ���������
	 * @param
	 * @return int
	 */
	public int getRuleSize();
	/**
	 * @describe ��ѯ��������Ƿ��ڵ绰��������
	 * @param String phoneNum(�绰����)
	 * @return boolean �ڵ绰�������ڷ���ture ���ڷ���false
	 */
	public boolean IfInBlacklist(String phoneNum);
	/**
	 * @describe �ж�¼������Ƿ����
	 * @param  String phoneNum(�绰����)
	 * @return boolean ����Ϊtrue ������Ϊfalse
	 */
	public boolean ifHaveSameNum(String phoneNum);
}