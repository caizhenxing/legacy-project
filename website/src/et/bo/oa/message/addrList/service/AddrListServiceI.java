package et.bo.oa.message.addrList.service;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p> ͨѶ¼���� Interface </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-11
 * 
 */
public interface AddrListServiceI {

	/**
	 * <p> ���ͨѶ¼���� </p>
	 * @param departId������Id
	 * @param pageInfo����ҳ��Ϣ��ʵ��
	 * @return��ͨѶ¼����
	 */
	public Object[] getAddrList(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> ���ͨѶ¼��ϸ��Ϣ </p>
	 * @param employeeId��Ա��Id
	 * @return����ϸ��Ϣ����
	 */
	public Object[] getAddrInfo(String employeeId);
	
	/**
	 * <p> ��ü�¼�� </p>
	 * @return����ѯ��¼��
	 */
	public int AddrListSize();
}
