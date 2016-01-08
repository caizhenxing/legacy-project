/**
 * 	@(#)LdapService.java   2006-9-9 ����06:27:26
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package excellence.common.ldap;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author ��һ��
 * @version 2006-9-9
 * @see
 */
public interface LdapService {

	/**
	 * ��ѯldap
	 * @param id
	 * @version 2006-9-9
	 * @return
	 */
	public IBaseDTO search(String id);
	
	/**
	 * ������Ϣ ldap
	 * @param
	 * @version 2006-9-9
	 * @return
	 */
	public void add(IBaseDTO dto);
	/**
	 * �޸���Ϣ ldap
	 * @param
	 * @version 2006-9-9
	 * @return
	 */
	public void update(IBaseDTO dto);
	/**
	 * ɾ����Ϣ ldap
	 * @param
	 * @version 2006-9-9
	 * @return
	 */
	public void delete(IBaseDTO dto);
	
	
}
