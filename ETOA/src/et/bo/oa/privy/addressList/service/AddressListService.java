/**
 * @(#)EmailService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.oa.privy.addressList.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>AddressListService</code> is interface
 * @author ��Ҷ����
 * @version 1.0, //
 * @see
 * @see
 */
public interface AddressListService {
    /**
	 * @describe��¼��ͨѶ¼��Ϣ
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addAddressListInfo(IBaseDTO dto);
    /**
	 * @describe �޸�ͨѶ¼��Ϣ
	 * @param
	 * @return
	 * 
	 */  
    public boolean updateAddressListInfo(IBaseDTO dto);
    /**
	 * @describe ɾ��ͨѶ¼��Ϣ
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteAddressListInfo(IBaseDTO dto);
    /**
	 * @describe �õ�����
	 * @param
	 * @return
	 * 
	 */    
    public int getAddressListSize();
    /**
	 * @describe ����������ѯͨѶ¼��Ϣ
	 * @param
	 * @return
	 * 
	 */    
    public List findAddressListInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe ��ѯ��˾ͨѶ¼��Ϣ
	 * @param
	 * @return
	 * 
	 */    
    public List findCompanyAddressListInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe ����id��ѯ��Ϣload
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getAddressListInfo(String id);
    /**
	 * @describe ȡ��ͨѶ¼�����Ա�б�
	 * @param
	 * @return
	 * 
	 */
    public List getBoxList();
}
