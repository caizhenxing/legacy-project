/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressList.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>AddressListService</code> is interface
 * @author 　叶浦亮
 * @version 1.0, //
 * @see
 * @see
 */
public interface AddressListService {
    /**
	 * @describe　录入通讯录信息
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addAddressListInfo(IBaseDTO dto);
    /**
	 * @describe 修改通讯录信息
	 * @param
	 * @return
	 * 
	 */  
    public boolean updateAddressListInfo(IBaseDTO dto);
    /**
	 * @describe 删除通讯录信息
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteAddressListInfo(IBaseDTO dto);
    /**
	 * @describe 得到条数
	 * @param
	 * @return
	 * 
	 */    
    public int getAddressListSize();
    /**
	 * @describe 根据条件查询通讯录信息
	 * @param
	 * @return
	 * 
	 */    
    public List findAddressListInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe 查询公司通讯录信息
	 * @param
	 * @return
	 * 
	 */    
    public List findCompanyAddressListInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe 根据id查询信息load
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getAddressListInfo(String id);
    /**
	 * @describe 取得通讯录表的人员列表
	 * @param
	 * @return
	 * 
	 */
    public List getBoxList();
}
