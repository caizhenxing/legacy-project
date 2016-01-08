/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressListSort.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>AddressListService</code> is interface
 * @author 　叶浦亮
 * @version 1.0, //
 * @see
 * @see
 */
public interface AddressListSortService {
    /**
	 * @describe　录入通讯录信息
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe 修改通讯录类别信息
	 * @param
	 * @return
	 * 
	 */  
    public boolean updateAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe 删除通讯录类别信息
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe 得到条数
	 * @param
	 * @return
	 * 
	 */    
    public int getAddressListSortSize();
    /**
	 * @describe 根据条件查询通讯录类别信息
	 * @param
	 * @return
	 * 
	 */    
    public List findAddressListSortInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe 根据id查询信息load
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getAddressListSortInfo(String id);
    /**
	 * @describe 判断是否有同名类别
	 * @param
	 * @return
	 * 
	 */
    public boolean isHaveSameName (IBaseDTO dto);
    /**
	 * @describe 取得类别LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
    public  List<LabelValueBean> getLabelList(String userId, String sign);
    /**
	 * @describe 根据类别Id取得类别名
	 * @param
	 * @return   类别名
	 * 
	 */
    public String getSortNameById (String Id);

}
