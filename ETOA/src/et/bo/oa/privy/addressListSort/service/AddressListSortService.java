/**
 * @(#)EmailService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.oa.privy.addressListSort.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>AddressListService</code> is interface
 * @author ��Ҷ����
 * @version 1.0, //
 * @see
 * @see
 */
public interface AddressListSortService {
    /**
	 * @describe��¼��ͨѶ¼��Ϣ
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe �޸�ͨѶ¼�����Ϣ
	 * @param
	 * @return
	 * 
	 */  
    public boolean updateAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe ɾ��ͨѶ¼�����Ϣ
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteAddressListSortInfo(IBaseDTO dto);
    /**
	 * @describe �õ�����
	 * @param
	 * @return
	 * 
	 */    
    public int getAddressListSortSize();
    /**
	 * @describe ����������ѯͨѶ¼�����Ϣ
	 * @param
	 * @return
	 * 
	 */    
    public List findAddressListSortInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe ����id��ѯ��Ϣload
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getAddressListSortInfo(String id);
    /**
	 * @describe �ж��Ƿ���ͬ�����
	 * @param
	 * @return
	 * 
	 */
    public boolean isHaveSameName (IBaseDTO dto);
    /**
	 * @describe ȡ�����LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
    public  List<LabelValueBean> getLabelList(String userId, String sign);
    /**
	 * @describe �������Idȡ�������
	 * @param
	 * @return   �����
	 * 
	 */
    public String getSortNameById (String Id);

}
