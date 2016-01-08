package et.bo.oa.message.addrList.service;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p> 通讯录操作 Interface </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-11
 * 
 */
public interface AddrListServiceI {

	/**
	 * <p> 获得通讯录集合 </p>
	 * @param departId：部门Id
	 * @param pageInfo：分页信息类实例
	 * @return：通讯录集合
	 */
	public Object[] getAddrList(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> 获得通讯录详细信息 </p>
	 * @param employeeId：员工Id
	 * @return：详细信息集合
	 */
	public Object[] getAddrInfo(String employeeId);
	
	/**
	 * <p> 获得记录数 </p>
	 * @return：查询记录数
	 */
	public int AddrListSize();
}
